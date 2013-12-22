package org.apache.tools.ant.lxt.pub;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.stringtemplate.v4.ST;

public class ExportSql4Data extends Task {
	private String driver;
	private String url;
	private String user;
	private String password;
	private String output;
	private String mode;
	
	static String MODE_INSERT = "insert";
	static String MODE_DELINSERT = "del_insert";
	static String MODE_DEL = "del";
	static String MODE_UPDATE = "update";
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	Vector<SQL> sqls = new Vector<SQL>();                                 

    public SQL createSQL() {                                
    	SQL sql = new SQL();
    	sqls.add(sql);
        return sql;
    }

    public class SQL {                                         
        public SQL() {}
        String sqlText;
        public void addText(String sql) {
        	sqlText = sql;
        }
    }
    
    Vector<FileSet> sqlfiles = new Vector<FileSet>();   
    
    public void addFileset(FileSet fileset) {
    	sqlfiles.add(fileset);
    }
    
    public void execute() {
    	validate();
    	
    	Connection conn = null;
        PrintWriter p = null;
        try {
        	Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            p = new PrintWriter(new FileWriter(output));
            if (sqls.size() > 0)
        		executeSql(conn, p);
        	else if (sqlfiles.size() > 0)
        		executeSQlFile(conn, p);
        } catch (SQLException e) {
			throw new BuildException(e);
		} catch (ClassNotFoundException e) {
			throw new BuildException(e);
		} catch (IOException e) {
			throw new BuildException(e);
		} finally {
            if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
				}
            if (p != null)
            	p.close();
        }
    	
    }

	private void executeSQlFile(Connection conn, PrintWriter p) throws IOException, SQLException {
		sqls.clear();
		for(Iterator<FileSet> itFSets = sqlfiles.iterator(); itFSets.hasNext(); ) {      
            FileSet fs = itFSets.next();
            DirectoryScanner ds = fs.getDirectoryScanner(getProject());         
            String[] includedFiles = ds.getIncludedFiles();
            for(int i=0; i<includedFiles.length; i++) {
                String filename = includedFiles[i].replace('\\','/');           
                filename = filename.substring(filename.lastIndexOf("/")+1);
                File file = new File(ds.getBasedir(), filename);
                if (file.exists() && file.isFile()) {
                	FileReader reader = new FileReader(file);
                	StringBuffer sql = new StringBuffer();
                	char data = (char)reader.read();
                	while(data != (char)-1) {
                	  if (data == ';') {
                		  if (sql.indexOf("select") != -1) {
                			  SQL sqlObj = new SQL();
                			  sqlObj.addText(sql.toString());
                			  sqls.add(sqlObj);//generateSQLStatements(conn, sql.toString(), p);
                		  }
                		  sql.delete(0, sql.length());
                	  }else {
                		  sql.append(data);
                	  }

                	  data = (char)reader.read();
                	}
                }
            }
        }
		executeSql(conn, p);
	}

	private void executeSql(Connection conn, PrintWriter p) throws SQLException, IOException {
		if (mode.equals(MODE_DELINSERT)) {
			for (int i=sqls.size()-1;i>=0;i--) {
				generateSQLStatements(MODE_DEL, conn, sqls.get(i).sqlText, p);
			}
			for (SQL sql : sqls) {
				generateSQLStatements(MODE_INSERT, conn, sql.sqlText, p);
	        }
		}
		else if (mode.equals(MODE_UPDATE)) {
			for (SQL sql : sqls) {
				generateSQLStatements(mode, conn, sql.sqlText, p);
	        }
		}
		else if (mode.equals(MODE_INSERT)) {
			for (SQL sql : sqls) {
				generateSQLStatements(mode, conn, sql.sqlText, p);
	        }
		}
	}
	
	void generateSQLStatements(String curmode, Connection conn, String sqlText, PrintWriter p) throws SQLException, IOException {
		String tableName = getTableName(sqlText);
		if (tableName == null) {
			log("Can not get the table name for: " + sqlText);
			throw new BuildException("Can not get the table name for: " + sqlText);
		}
		ArrayList<ArrayList<String>> dataGrid = getDataFromSql(conn, sqlText);
		if (curmode.equals(MODE_DEL)) {
			log("Generating Del statements for: " + sqlText);
			generateDelStatements(conn, dataGrid, p, tableName);
			log("Generating Del statements cont: " + String.valueOf(dataGrid.size()-1));
		}
		else if (mode.equals(MODE_UPDATE)) {
			log("Generating Update statements for: " + sqlText);
			generateUpdateStatements(conn, dataGrid, p, tableName);
			log("Generating Update statements cont: " + String.valueOf(dataGrid.size()-1));
		}
		else {
			log("Generating Insert statements for: " + sqlText);
			generateInsertStatements(dataGrid, p, tableName);
			log("Generating Insert statements cont: " + String.valueOf(dataGrid.size()-1));
		}
	}

	private void generateUpdateStatements(Connection conn, ArrayList<ArrayList<String>> dataGrid, PrintWriter p, String tableName) throws SQLException {
		String keyName = getPrimarykeyForTable(conn, tableName);
		ArrayList<String> columnNames = dataGrid.get(0);
		int keyIdx = columnNames.indexOf(keyName);
		
		ST st = new ST("update <table> set <vals:{v|<v.key> = <v.value>}; separator=\", \"> where <keyname> = <key>;");
		st.add("table", tableName);
		st.add("keyname", keyName);
		for (int i=1;i<dataGrid.size();i++) {
			if (st.getAttribute("vals") != null)
				st.remove("vals");
			if (st.getAttribute("key") != null)
				st.remove("key");
			for (int j=0;j<columnNames.size();j++)
				if (j != keyIdx)
					st.addAggr("vals.{key, value}", columnNames.get(j), dataGrid.get(i).get(j));
			
			st.add("key", dataGrid.get(i).get(keyIdx));
			String updatesql = st.render();
			p.println(updatesql);
		}
	}

	private void generateDelStatements(Connection conn, ArrayList<ArrayList<String>> dataGrid, PrintWriter p, String tableName) throws SQLException {
		String keyName = getPrimarykeyForTable(conn, tableName);
		ArrayList<String> columnNames = dataGrid.get(0);
		int keyIdx = columnNames.indexOf(keyName);
		ArrayList<String> keys = new ArrayList<String>();
		
		for (int i=1;i<dataGrid.size();i++) keys.add(dataGrid.get(i).get(keyIdx));
		ST st = new ST("<keys:{key | delete from <table> where <keyname> = <key>;\n }>");
		st.add("keys", keys);
		st.add("table", tableName);
		st.add("keyname", keyName);
		String delsql = st.render();
		p.print(delsql);
		//generateInsertStatements(dataGrid, p, tableName);
	}

	private String getPrimarykeyForTable(Connection conn, String tableName) throws SQLException {
		ResultSet rs = null;
	    DatabaseMetaData meta = conn.getMetaData();
	    
	    rs = meta.getPrimaryKeys(null, null, tableName.toUpperCase());
	    while (rs.next()) {
	      String columnName = rs.getString("COLUMN_NAME");
	      rs.close();
	      return columnName;
	    }

	    rs.close();
	    throw new BuildException("Can not get primary key for: "+tableName);
	}

	void generateInsertStatements(ArrayList<ArrayList<String>> dataGrid, PrintWriter p, String tableName) throws SQLException {
		ArrayList<String> columnNames = dataGrid.get(0);
		ST st = new ST("insert into <table> (<cols; separator=\", \">) values (<vals; separator=\", \">);");
		st.add("cols", columnNames);
		st.add("table", tableName);
		for (int i=1;i<dataGrid.size();i++) {
			if (st.getAttribute("vals") != null)
				st.remove("vals");
			st.add("vals", dataGrid.get(i));
			String insertsql = st.render();
			p.println(insertsql);
		}
	}

	private ArrayList<ArrayList<String>> getDataFromSql(Connection conn, String sqlText) throws SQLException, IOException {
		ArrayList<ArrayList<String>> dataGrid = new ArrayList<ArrayList<String>>();
		Statement stmt = null;
        ResultSet rs = null; 
        try {
			stmt = conn.createStatement();
	        rs = stmt.executeQuery(sqlText); 
	        ResultSetMetaData rsmd = rs.getMetaData();
	        int numColumns = rsmd.getColumnCount();
	        int[] columnTypes = new int[numColumns];
	        ArrayList<String> cloumnNames = new ArrayList<String>();
	        for (int i = 0; i < numColumns; i++) {
	            columnTypes[i] = rsmd.getColumnType(i + 1);
	            cloumnNames.add(rsmd.getColumnName(i + 1));
	        }
	        dataGrid.add(cloumnNames);
	        java.util.Date d = null;
	        Blob b = null;
	        while (rs.next()) {
	        	ArrayList<String> cloumnValues = new ArrayList<String>();
	            for (int i = 0; i < numColumns; i++) {
	                switch (columnTypes[i]) {
	                    case Types.BIGINT:
	                    case Types.BIT:
	                    case Types.BOOLEAN:
	                    case Types.DECIMAL:
	                    case Types.DOUBLE:
	                    case Types.FLOAT:
	                    case Types.INTEGER:
	                    case Types.SMALLINT:
	                    case Types.TINYINT:
	                        String v = rs.getString(i + 1);
	                        cloumnValues.add(v);
	                        break;
	
	                    case Types.DATE:
	                        d = rs.getDate(i + 1); 
	                    case Types.TIME:
	                        if (d == null) d = rs.getTime(i + 1);
	                    case Types.TIMESTAMP:
	                        if (d == null) d = rs.getTimestamp(i + 1);
	
	                        if (d == null) {
	                        	cloumnValues.add("null");
	                        }
	                        else {
	                        	cloumnValues.add("TO_DATE('" + dateFormat.format(d) + "', 'YYYY/MM/DD HH24:MI:SS')");
	                        }
	                        break;
	                    case Types.BLOB:
	                    	b = rs.getBlob(i + 1);
	                    	if (b != null) {
	                    		StringBuffer str = new StringBuffer();
	                    		InputStream in = b.getBinaryStream();
	                    		byte[] data = new byte[4096];
	                    		for (int n; (n = in.read(data)) != -1;) {
	                    			str.append(new String(data, 0, n));
	                    		}
	                    		cloumnValues.add("utl_raw.cast_to_raw('" + str.toString() + "')");
	                    	}else{
	                    		cloumnValues.add("EMPTY_BLOB()");
	                    	}
	                        break;
	                    default:
	                        v = rs.getString(i + 1);
	                        if (v != null) {
	                        	cloumnValues.add("'" + v.replaceAll("'", "''") + "'");
	                        }
	                        else {
	                        	cloumnValues.add("null");
	                        }
	                        break;
	                }
	            }
	            dataGrid.add(cloumnValues);
	        }
        }finally {
        	if (rs != null) rs.close();
        	if (stmt != null) stmt.close();
        }
		return dataGrid;
	}

	private String getTableName(String sqlText) {
		Pattern selectP = Pattern.compile("select\\s+(\\w+).[\\w\\*]+");
    	Matcher m = selectP.matcher(sqlText);
    	while(m.find())
    		return m.group(1);
        
    	Pattern fromP = Pattern.compile("\\s+from\\s+(\\w+)\\s+");
    	m = fromP.matcher(sqlText);
    	while(m.find())
    		return m.group(1);
    	
    	return null;
	}

	private void validate() {
		
	}
}

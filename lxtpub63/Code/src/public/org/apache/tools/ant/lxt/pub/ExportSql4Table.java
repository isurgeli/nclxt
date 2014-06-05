package org.apache.tools.ant.lxt.pub;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nc.itf.lxt.pub.set.SetUtils;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class ExportSql4Table extends Task {
	private String driver;
	private String url;
	private String user;
	private String password;
	private String output;
	
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

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	Vector<Table> tables = new Vector<Table>();                                 

    public Table createTable() {                                
    	Table table = new Table();
    	tables.add(table);
        return table;
    }

    public class Table {                                         
        public Table() {}
        String table;
        public void addText(String table) {
        	this.table = table;
        }
    }
    
    private void validate() {
	}
    
    public void execute() {
    	validate();
    	
    	Connection conn = null;
        PrintWriter p = null;
        try {
        	Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            p = new PrintWriter(new FileWriter(output));         
            executeObjectDDL(conn, p);
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

	private void executeObjectDDL(Connection conn, PrintWriter p) throws SQLException {
		ArrayList<ArrayList<String>> tablenames = getAllTableNames(conn);
        
        for (Table tableDef : tables) {
        	ArrayList<String> targetTableNames = new ArrayList<String>();
        	ArrayList<String> targetViewNames = new ArrayList<String>();
        	Pattern tp = Pattern.compile(tableDef.table);
        	for (int i=0;i<tablenames.size();i++) {
        		Matcher m = tp.matcher(tablenames.get(i).get(0));
            	while(m.find())
            		if (tablenames.get(i).get(1).toUpperCase().equals("TABLE"))
            			targetTableNames.add(tablenames.get(i).get(0));
            		else
            			targetViewNames.add(tablenames.get(i).get(0));
        	}
        	if (targetTableNames.size()>0)
        		executeTableDLL(conn, p, targetTableNames);
        	
        	if (targetViewNames.size()>0)
        		executeViewDLL(conn, p, targetViewNames);
        }
	}

	private void executeViewDLL(Connection conn, PrintWriter p, ArrayList<String> targetViewNames) throws SQLException {
		String views = SetUtils.concatString(',', targetViewNames);
		log("Generate DDL for "+targetViewNames.size()+" views : " + views);
		
		Statement stmt = null;
        ResultSet rs = null; 
        try {
			stmt = conn.createStatement();
			for (String table : targetViewNames) {
				rs = stmt.executeQuery("SELECT dbms_metadata.get_ddl( 'VIEW', '"+table+"', '' ) FROM dual");  
	        	while (rs.next()) {
	        		String ddl = rs.getString(1);
	        		ddl = ddl.replaceAll("(FORCE\\s+VIEW\\s+)\"\\w+\".", "$1");
	        		p.print(ddl);
	        		p.print(";");
	        	}
			}
        }finally {
        	if (rs != null) rs.close();
        	if (stmt != null) stmt.close();
        }
	}

	private void executeTableDLL(Connection conn, PrintWriter p, ArrayList<String> targetTableNames) throws SQLException {
		String tables = SetUtils.concatString(',', targetTableNames);
		log("Generate DDL for "+targetTableNames.size()+" tables : " + tables);
		
		Statement stmt = null;
        ResultSet rs = null; 
        try {
			stmt = conn.createStatement();
			for (String table : targetTableNames) {
				rs = stmt.executeQuery("SELECT dbms_metadata.get_ddl( 'TABLE', '"+table+"', '' ) FROM dual");  
	        	while (rs.next()) {
	        		String ddl = rs.getString(1);
	        		ddl = ddl.replaceAll("(CREATE\\s+TABLE\\s+)\"\\w+\".", "$1");
	        		int end = ddl.indexOf("USING INDEX");
	        		ddl = ddl.substring(0, end-1);
	        		ddl += ")";
	        		p.print(ddl);
	        		p.print(";");
	        	}
			}
        }finally {
        	if (rs != null) rs.close();
        	if (stmt != null) stmt.close();
        }
	}

	private ArrayList<ArrayList<String>> getAllTableNames(Connection conn)	throws SQLException {
		ArrayList<ArrayList<String>> tablenames = new ArrayList<ArrayList<String>>();
		Statement stmt = null;
        ResultSet rs = null; 
        try {
			stmt = conn.createStatement();
	        rs = stmt.executeQuery("select OBJECT_NAME, OBJECT_TYPE from USER_OBJECTS where object_type in ('TABLE', 'VIEW') order by OBJECT_TYPE, OBJECT_NAME");  
	        while (rs.next()) {
	        	ArrayList<String> tablename = new ArrayList<String>();
	        	tablename.add(rs.getString(1));
	        	tablename.add(rs.getString(2));
	        	tablenames.add(tablename);
	        }
        }finally {
        	if (rs != null) rs.close();
        	if (stmt != null) stmt.close();
        }
		return tablenames;
	}
}

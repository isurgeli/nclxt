package org.apache.tools.ant.lxt.pub;

import org.apache.tools.ant.BuildFileTest;

public class ExportSqlDataTest extends BuildFileTest {
	public ExportSqlDataTest(String s) {
        super(s);
    }

    public void setUp() {
        // initialize Ant
        configureProject(ExportSqlDataTest.class.getResource("build.xml").getPath());
    }

    public void testInsert() {
        executeTarget("insert");
    }
    
    public void testDelInsert() {
        executeTarget("del_insert");
    }
    
    public void testUpdate() {
        executeTarget("update");
    }
    
    public void testTable() {
        executeTarget("table");
    }
    
//    public void testRegx() {
//    	Pattern pa = Pattern.compile("select\\s+(\\w+).[\\w\\*]+");
//    	String sql = "select a.bb from ";
//    	Matcher m = pa.matcher(sql);
//    	
//    	while(m.find()){
//
//            System.out.print(m.group() + " ");//”√Ï∂´@»°∆•≈‰··ΩYπ˚
//
//            System.out.println(m.start() + "..." + m.end());
//        }
//    }
}

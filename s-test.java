import java.util.*;
import java.sql.*;


class test {

	static final String JDBC_DRIVER = "org.h2.Driver";  
   	static final String DB_URL = "jdbc:h2:~/test";

   	static final String USER = "jamiean";
   	static final String PASS = "jamiean";

	public static void main(String[] args) throws Exception {
		Random r = new Random();
		Vector<Double> vector = new Vector<Double>();
		for (int i = 0; i < 100000; i ++) {
			double j = r.nextGaussian();
			vector.add(j);

		}

		// test line
		// System.out.println(vector.get(0));
		// System.out.println(vector.get(1));

		try {
			Class.forName("org.h2.Driver");


      		Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
      		Statement stmt = conn.createStatement();

      		// create table
      		String create = "CREATE TABLE TEST (num DECIMAL);";
      		stmt.executeQuery(create);

 			// insert values
      		for (int i = 0; i < 100000; i ++) {
      			String insert = "INSERT INTO TEST VALUES (" + vector.get(i) + ");";
      			stmt.executeQuery(insert);
      		}

      		// Random sampling 10000 values and get SD
      		String getvalue = "SELECT STDDEV_POP(num) FROM (SELECT num FROM TEST ORDER BY RAND() limit 1000);";
      		ResultSet rst = stmt.executeQuery(getvalue);
      		double output = rst.getDouble(getvalue);
      		System.out.println(output);
      
      		// Drop tables
      		String drop = "DROP TABLE TEST;";
      		stmt.executeQuery(drop);

			// Close Connection
      		if (stmt!=null) conn.close();
			if (conn!=null) conn.close();
      		
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
		finally {

		}
	}



	
}
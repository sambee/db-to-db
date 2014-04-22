package sam.bee.database.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertToTableByJDBC {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
      Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");      
      Connection conn = DriverManager.getConnection("jdbc:ucanaccess://F:/1.mdb");
      Statement st = conn.createStatement();
      st.execute("insert into ams_work_file (id) values ('1222w') ");
      st.close();
      conn.close();
		
	}

}

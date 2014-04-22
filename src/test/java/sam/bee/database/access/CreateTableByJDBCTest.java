package sam.bee.database.access;

import java.sql.Connection;
import java.sql.DriverManager;
//imports from Jackcess Encrypt
//import com.healthmarketscience.jackcess.CryptCodecProvider;
import java.sql.SQLException;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class CreateTableByJDBCTest {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		Connection conn = DriverManager.getConnection("jdbc:ucanaccess://1.mdb;lockmdb=true;ignorecase=true");
		conn.close();
	}

}

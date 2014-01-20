package sam.bee.oa.sql.database.h2;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.Server;

import sam.bee.oa.sql.database.BaseDatabase;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class H2Database extends BaseDatabase{

	static Server server;
	public static void main(String[] args) throws SQLException, ClassNotFoundException{
//		  Class.forName("org.h2.Driver");
//	       
//	        // add application code here
//	        conn.close();
	   
		String jdbc = "jdbc:h2:tcp://localhost:9000/f:/test";
		String user = "sa";
		String password = "";
		H2Database  db = new H2Database(jdbc, user, password);
		db.closeCon();
	}
	
	public H2Database(String jdbc, String user, String password) throws SQLException{
		
		if(server==null){
			// start the TCP Server
			server = Server.createTcpServer(new String[] { "-tcpPort", "9000" }).start();
		}
		//System.out.println(jdbc);
		setConn(DriverManager.getConnection(jdbc, user, password));
	}
	
	@Override
	public synchronized void closeCon() {
		//System.out.println("Close h2 server.");		
		super.closeCon();
		// stop the TCP Server
		//server.stop();
	}
}

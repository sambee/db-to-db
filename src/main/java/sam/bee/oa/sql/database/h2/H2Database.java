package sam.bee.oa.sql.database.h2;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.Server;

import sam.bee.oa.sql.database.BaseDatabase;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class H2Database extends BaseDatabase{

	static Server server;
	private String type;
	public static void main(String[] args) throws SQLException, ClassNotFoundException{
//		  Class.forName("org.h2.Driver");
//	       
//	        // add application code here
//	        conn.close();
	   
		String jdbc = "jdbc:h2:tcp://localhost:9000/f:/test";
		String user = "sa";
		String password = "";
		H2Database  db = new H2Database(jdbc, user, password, "h2");
		db.closeCon();
	}
	
	public H2Database(String jdbc, String user, String password, String type) throws SQLException{
		
		if(server==null){
			// start the TCP Server
			server = Server.createTcpServer(new String[] { "-tcpPort", "9000" }).start();
		}
		//System.out.println(jdbc);
		setConn(DriverManager.getConnection(jdbc, user, password));
		this.type = type;
	}
	
	@Override
	public synchronized void closeCon() {		
		super.closeCon();
//		// stop the TCP Server		
//		try {
//			System.out.println("Closing h2 server.");		
//			server.stop();			
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		finally{
//			server = null;
//		}
//		System.out.println("Closed h2 server.");	
	}
}

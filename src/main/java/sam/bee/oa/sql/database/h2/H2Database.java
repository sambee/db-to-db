package sam.bee.oa.sql.database.h2;

import java.sql.SQLException;

import org.h2.tools.Server;

import sam.bee.oa.sql.database.BaseDatabase;
import sam.bee.oa.sql.database.DatabaseConfig;

/**
 * 
 * @see http://boonya.iteye.com/blog/1828240	
 *
 * H2Database.java
 *
 * @author Sam Wong
 *  QQ: 1557299538
 * @create: 2014年2月13日  
 * 
 * Modification
 * -------------------------------------------
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class H2Database extends BaseDatabase{

	static Server server;

//	public static void main(String[] args) throws SQLException, ClassNotFoundException{
//		  Class.forName("org.h2.Driver");
//	       
//	        // add application code here
//	        conn.close();
   
//		String jdbc = "jdbc:h2:tcp://localhost:9000/f:/test";
//		String user = "sa";
//		String password = "";
//		H2Database  db = new H2Database(jdbc, user, password, "h2");
//		db.closeCon();
//	}
	
	public H2Database(DatabaseConfig conn) throws SQLException, ClassNotFoundException{
		super(conn);
		if(server==null){
			// start the TCP Server
			server = Server.createTcpServer(new String[] { "-tcpPort", "9000" }).start();
		}
//		setConn(DriverManager.getConnection(conn.getJDBC(), conn.getUserName(), conn.getPassword()));
//		setType(type);
	}
	
//	@Override
//	public synchronized void closeCon() {
//		if(server.isRunning(true)){
			//System.out.println("TCP服务器正在运行......");
//			super.closeCon();
//		};
		
		
//		 if(server.isRunning(true)){  
//             System.out.println("TCP服务器正在运行......");  
//             server.startWebServer(DBConnection.getConnection());  
//         }  
//         server.shutdownTcpServer("tcp://192.168.8.33:9094", "boonya", true, false);//url,password,boolean,boolean  
//         server.stop();  
         
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
//	}
}

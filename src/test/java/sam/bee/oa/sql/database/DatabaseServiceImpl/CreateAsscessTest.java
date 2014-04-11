package sam.bee.oa.sql.database.DatabaseServiceImpl;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Database.FileFormat;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.junit.Test;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CreateAsscessTest {

	 private static Database createDatabase(String databaseName) throws IOException {
	     DatabaseBuilder builer =  new DatabaseBuilder(); 
		 return DatabaseBuilder.create(FileFormat.V2000, new File(databaseName));
	    }

	    private static TableBuilder createTable(String tableName) {
	        return new TableBuilder(tableName);
	    }

	    public static void addColumn(Database database, TableBuilder tableName, String columnName, Types sqlType) throws SQLException, IOException {
	        tableName.addColumn(new ColumnBuilder(columnName).setSQLType(Types.INTEGER).toColumn()).toTable(database);
	    }

	    public static void startDatabaseProcess() throws IOException, SQLException {
	        String databaseName = "d://employeedb.mdb"; 
	        Database database = createDatabase(databaseName);

	        String tableName = "Employee"; // Creating table
	        Table table = createTable(tableName)
	                .addColumn(new ColumnBuilder("Emp_Id").setSQLType(Types.INTEGER).toColumn())
	                .addColumn(new ColumnBuilder("Emp_Name").setSQLType(Types.VARCHAR).toColumn())
	                .addColumn(new ColumnBuilder("Emp_Employer").setSQLType(Types.VARCHAR).toColumn())
	                .toTable(database);

	        table.addRow(122875, "Sarath Kumar Sivan","Infosys Limited.");//Inserting values into the table
	    }

	    @Test
	    public void test1() throws IOException, SQLException, ClassNotFoundException {
	       // startDatabaseProcess();
//	    	String file = "d://employeedb.mdb";
//	    	createDatabase(file);
//	    	 Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//	    	 Connection con = DriverManager
//	    			 .getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + file, "", "");
//    	 
//	    	 Statement stm = con.createStatement();
//	    	 
//	    	 //counter,text,char,varchar,memo,short,long,single,double,datetime,logical,currency,oleobject
//	    	 stm.execute("Create TABLE Employee(ID varchar(10), name text(20))");
//	    	 con.close();
	    	 
	    	 
	    	String file = "F:\\meiluo\\mlams_2.0_dev_trunk\\server\\temp/1395984837270.mdb";
	    	 Connection con = DriverManager
			 .getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=" + file, "", "");

			 Statement stm = con.createStatement();
			 
			 con.close();
			 
			 System.out.println("111111111111111111111111111111111111111");
	    }
}

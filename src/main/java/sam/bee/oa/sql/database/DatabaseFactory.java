package sam.bee.oa.sql.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import sam.bee.oa.sql.database.h2.H2Database;


public class DatabaseFactory {

	public static BaseDatabase getDatabase(String type) throws SQLException, IOException {
		if ("mssql".equals(type)) {
			
			return new BaseDatabase(new DatabaseConnection(type).getConnection());
		}
		if ("h2".equals(type)) {
			DatabaseConnection c = new DatabaseConnection(type);			
			return new H2Database(c.getJDBC(), c.getUser(), c.getPassword());
		}
		return null;
	}
}

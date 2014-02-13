package sam.bee.oa.sql.database.DatabaseServiceImpl;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sam.bee.oa.author.AuthProxy;
import sam.bee.oa.author.TableDAOFactory;
import sam.bee.oa.author.TableDao;
import sam.bee.oa.sql.core.ServiceFactory;
import sam.bee.oa.sql.database.DatabaseService;
import sam.bee.oa.sql.database.model.PageModel;


public class GetMetasTest {
	String dbName = "mssql";

	@Test
	public void test() {
		DatabaseService service = (DatabaseService)ServiceFactory.getService(DatabaseService.class);
		service.getMetas(dbName,"system_users");
		
		
	}
	
}

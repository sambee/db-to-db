package sam.bee.oa.sql.database.DatabaseServiceImpl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import sam.bee.oa.author.AuthProxy;
import sam.bee.oa.author.TableDAOFactory;
import sam.bee.oa.author.TableDao;
import sam.bee.oa.sql.database.DatabaseFactory;
import sam.bee.oa.sql.database.DatabaseService;


public class GetMetasTest {


	@Test
	public void test() {
		DatabaseService service = (DatabaseService)DatabaseFactory.getService(DatabaseService.class);
		service.getMetas("system_users");
		
	}
	
}
package sam.bee.oa.author;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AuthTest {

	  public void doMethod(TableDao dao){  
	        dao.create();  
	        dao.query();  
	        dao.update();  
	        dao.delete();  
	    } 
		  
	  
	@Test
	//模拟有权限
    public void haveAuth(){  
        TableDao tDao = TableDAOFactory.getAuthInstance(new AuthProxy("张三"));  
        doMethod(tDao);  
    }  
    
    @Test
    //模拟无权限
    public void haveNoAuth(){  
        TableDao tDao = TableDAOFactory.getAuthInstance(new AuthProxy("李四"));  
        doMethod(tDao);  
    }

}

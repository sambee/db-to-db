package sam.bee.oa;

import static sambee.utils.ConfigUtils.loadConfig;

import java.io.File;
import java.util.Map;

import sam.bee.oa.db.IDatabaseAdapter;
import sam.bee.oa.sql.database.DatabaseFactory;

public class App {

	
	public static void main(String[] args) throws Exception {
		
		String arg0 = null;
		
		if(args==null||args.length==0){
			exit("Not found 'properties' argument");
			
		}
		
		echo("Arguments count:" + args.length);
		if(args.length >0){
			arg0 = args[args.length-1];
			File f = new File(arg0);
			if(!f.exists()){
				exit("Not found " + arg0 + " file");				
			}
		}
		
		echo("[Config File]" + arg0);
		
		Map<String,String> config = loadConfig(arg0, App.class.getName());
		if(config==null){
			exit("not found the "+App.class.getName() + " configuration.");
		}
		
		String handler = config.get("handler");
		if(handler==null){
			exit("not found the "+App.class.getName() + " handler configuration.");
		}
		
		Class obj = Class.forName(handler);
		IDatabaseAdapter r  = (IDatabaseAdapter)obj.newInstance();
		r.parse(new String[]{arg0});


	}
	
	public static void echo(String msg) {
		System.out.println(msg);
	}
	
	public static void exit(String msg) {
		echo(msg);
		System.exit(0);
	}
}

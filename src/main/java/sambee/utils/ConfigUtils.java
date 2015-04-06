package sambee.utils;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigUtils {

	public static Map<String,String> loadConfig(String propFile, String domain) throws FileNotFoundException, IOException{
		Properties prop = new Properties();
		prop.load(new FileInputStream(new File(propFile)));
		return loadConfig(prop, domain);
	}
	
	public static Map<String,String> loadConfig(Properties prop, String domain){
		Map<String,String>  map = new HashMap<String,String>();
		Enumeration<Object> enums = prop.keys();
		while (enums.hasMoreElements()) {
			Object key = enums.nextElement();
			String k = key.toString();
			if(k.startsWith(domain)){
				String k2 = k.substring(domain.length()+1); 
				map.put(k2, prop.getProperty(key.toString()));
			}			
		}
		
		return map;		
	}

    public static void saveConfig(String fileName, Map<String,String> data) throws IOException {
        Properties prop = new Properties();
        prop.putAll(data);
        prop.store(new FileOutputStream(new File(fileName)), "");
    }
	
}

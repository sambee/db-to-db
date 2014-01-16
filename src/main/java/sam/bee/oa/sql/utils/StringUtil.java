package sam.bee.oa.sql.utils;

public class StringUtil {

    public static boolean isEmpty(String s)
    {
    	return !isNotEmpty(s);
    }
    
    public static boolean isNotEmpty(String s){
    	return null != s && s.trim().length() > 0;
    } 
    
    public static String toJavaNaming(String name){
    	return name.substring(0,1).toUpperCase() + name.substring(1);
    }
}

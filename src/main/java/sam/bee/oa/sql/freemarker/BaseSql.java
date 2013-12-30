package sam.bee.oa.sql.freemarker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

import org.h2.util.IOUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public abstract class BaseSql {

	protected String sql(String filename, Map<String,?> params) throws ParaseException{
		 try {
			 return process(filename, params);		
		} catch (Exception e) {
			throw new ParaseException(e);
		}
		 
	}
	
	protected String process(String commandId, Map<String, ?> model) throws IOException, TemplateException, ParaseException{
		 Configuration config=new Configuration(); 
		 StringTemplateLoader sTmpLoader = new StringTemplateLoader();  
         sTmpLoader.putTemplate(commandId, getSqlTemplate(commandId));
         config.setTemplateLoader(sTmpLoader); 
         config.setDefaultEncoding("UTF-8");
         Template template = config.getTemplate(commandId);      
         StringWriter writer = new StringWriter();      
         template.process(model, writer); 
         return writer.toString();  
	}
	
	protected String getSqlTemplate(String commandId) throws ParaseException{
		try{
		InputStream is = BaseSql.class.getResourceAsStream(commandId);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		IOUtils.copyAndClose(is, os);
		return new String(os.toByteArray());
		}
		catch(IOException e){
			throw new ParaseException("Can not find sql " + commandId);
		}
	}
	
	
}

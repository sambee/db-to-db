package sam.bee.oa.sql.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class In implements TemplateMethodModelEx {
	
	public Object exec(List arguments) throws TemplateModelException {
		Object obj = arguments.get(0);
		
		if(obj instanceof List){
			List lst =(List)obj;
		}
		return "in()";
	}

}

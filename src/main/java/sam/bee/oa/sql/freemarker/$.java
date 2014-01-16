package sam.bee.oa.sql.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class $ implements TemplateMethodModel{

	List<Object> list;
	public $(List<Object> list){
		this.list = list;
		
	}
	public Object exec(List arguments) throws TemplateModelException {
		list.add(arguments.get(0));
		return "?";
	}

}

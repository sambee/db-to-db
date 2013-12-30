package sam.bee.oa.sql.freemarker;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class $ implements TemplateMethodModel{

	List<Object> args;
	public $(List<Object> args){
		this.args = args;
		
	}
	public Object exec(List arguments) throws TemplateModelException {
		args.add(arguments.get(0));
		return "?";
	}

}

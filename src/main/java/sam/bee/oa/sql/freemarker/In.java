package sam.bee.oa.sql.freemarker;

import java.util.List;

import freemarker.template.SimpleSequence;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class In implements TemplateMethodModelEx {
	
	List<Object> args;
	public In(List<Object> args){
		this.args = args;
	};
	
	
	public Object exec(List arguments) throws TemplateModelException {
		Object obj = arguments.get(0);
		StringBuffer sb = new StringBuffer("in(");
		if(obj instanceof SimpleSequence){
			SimpleSequence lst =(SimpleSequence)obj;
			
			for(int i=0;i<lst.size();i++){
				if(i==0){
					sb.append("?");	
				}
				else{
					sb.append(",?");	
				}
				args.add(lst.get(i));
			}
		}
		return sb.append(")").toString();
	}

}

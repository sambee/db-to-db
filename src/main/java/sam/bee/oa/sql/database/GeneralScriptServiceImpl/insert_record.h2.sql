INSERT INTO ${tableName}() (<#list fields as field><#if field_index != 0>,</#if>${field}</#list>) VALUES (<#list values as v><#if v_index != 0>,</#if><#if types[v_index]=='string'> '${v!"null"}'<#elseif types[v_index]=='number'> ${v!"null"}<#elseif types[v_index]=='datetime'> '${v?string("yyyy-MM-dd hh:mm:ss")!"null"}'<#else>null</#if></#list>)
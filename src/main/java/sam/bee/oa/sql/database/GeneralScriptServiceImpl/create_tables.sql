<#if tableName?? >
CREATE TABLE [${tableName}](
<#list fields as item>
<#if item_index != 0>,</#if> ${item.COL_NAME}  ${item.COL_TYPE} <#if item.COL_TYPE !="datetime">(<#if item.COL_LEN lt 1073741823 >${item.COL_LEN?string('#')}<#else>MAX</#if>)</#if> <#if item.COL_NULLABLE == 0 >NOT NULL</#if>  
</#list>
)
</#if>
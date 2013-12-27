select * from stocks
  WHERE 1=1 
  <#if param0??>
     AND stockId = ${param0}
  </#if>
  <#if param1??>
   	 AND stockId = ${param1}
  </#if>
   <#if param2??>
     AND stockId = ${param2}
  </#if>
   <#if inTest??>
	AND ${$in(inTest)}
  </#if>
      
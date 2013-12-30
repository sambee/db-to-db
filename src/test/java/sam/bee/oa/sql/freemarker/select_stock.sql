select * from stocks
  WHERE 1=1 
  <#if param0??>
     AND stockId = ${param0}
  </#if>
  <#if stockName??>
   	 AND stockName = ${$(stockName)}
  </#if>
   <#if stockId??>
     AND stockId = ${$(stockId)}
  </#if>
   <#if catories??>
	AND ${$in(catories)}
  </#if>
      
<#if errmsg??>
${errmsg}
<#else>
<#if exception.getMessage()??>
操作失败：${exception.getMessage()}
${exceptionStack}
<#else>
内部错误：${exception.toString()}
${exceptionStack}
</#if>
</#if>
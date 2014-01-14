<#if agent??>
<font color='blue'>${agent.name}</font>
品牌:<font color='blue'>${agent.brandid}</font>
级别：<font color='blue'>${agent.rankDesc}</font>
<br/>
帐号状态：<font color='blue'>${agent.statusDesc}</font>
钱包状态：<font color='blue'>${acct.statusDesc}</font>
<br/>
可用余额：<font color='blue'>${(acct.availableBalance/1000)?string.currency}</font>
<br/>
冻结余额：<font color='blue'>${(acct.frozenBalance/1000)?string.currency}</font>
<br/>
押金：<font color='blue'>${(acct.deposit)?string.currency}</font>
<#else>
<font color='red'>代理商不存在</font>
</#if>

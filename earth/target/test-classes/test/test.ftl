<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<title>无标题文档</title>  
<style>  
table.formdata{   
border:2px solid #000000;   
border-collapse:collapse;
font-family:Arial;
}   
  
table.formdata caption{   
text-align:left;   
padding-bottom:6px;   
}   
  
table.formdata tbody th{   
border:2px solid #000000;  
color:#000000px;   
text-align:left;
font-weight:normal; 
padding:2px 8px 2px 6px;   
margin:0px;   
}

table.formdata thead th{
border:2px solid #000000;
color:#000000px;   
text-align:left;
font-weight:bold;
padding:2px 8px 2px 6px;   
margin:0px;
}
</style>
</head>
<body onload="setToEditable('editable')">
<div><h1>大家好：</h3></div>
<br/>
<form method="post">
	<#assign x=financePaymentRecords?size>
	<#if x == 0>
		<table class="formdata">
		<caption>1.今天无回款。</caption>
		<br/>
		<thead></thead>
		<tbody></tbody>
	<#else>
	<table class="formdata"> 
    <caption>1.今天回款${financePaymentRecords?size}笔，共计${financeMoney}元</caption>  
    <thead>
    <tr id="title">
	<th>编号</th>
	<th>回款金额(元)</th>
	<th>回款时间</th>
	<th>房源</th>
	<th>地址</th>
	<th>应收款条目 </th>
	<th>应付金额(元)</th>
	<th>应付日期</th>
	<th>状态</th>
	<th>扣款时间</th>
	<th>扣款时间！=回款时间</th>
	<th>备注</th>
	</tr>
	</thead>
	<tbody>
		<#list financePaymentRecords as financePaymentRecord>
		<tr>
		<#setting date_format="yyyy-MM-dd"/>
		<th>${financePaymentRecord_index+1}</th>
		<th>${financePaymentRecord.payMoney}</th>
		<#if financePaymentRecord.customerPaymentTime?string("yyyy-MM-dd HH:mm:ss") =="2015-01-01 00:00:00">
		<th></th>
		<th>${financePaymentRecord.order.contract.house.community}</th>
		<th>${financePaymentRecord.order.contract.house.address}</th>
		<th>${financePaymentRecord.order.orderNo}</th>
		<th>${financePaymentRecord.order.totalRent}</th>
		<th>${financePaymentRecord.order.dueDate}</th>
		<th>已扣款</th>
		<th>${financePaymentRecord.paymentTime}</th>
		<th>Y</th>
		<th>缓冲处理</th>
		<#else>
		<th>${financePaymentRecord.customerPaymentTime?string("yyyy-MM-dd HH:mm:ss")}</th>
		<th>${financePaymentRecord.order.contract.house.community}</th>
		<th>${financePaymentRecord.order.contract.house.address}</th>
		<th>${financePaymentRecord.order.orderNo}</th>
		<th>${financePaymentRecord.order.totalRent}</th>
		<th>${financePaymentRecord.order.dueDate}</th>
		<th>已扣款</th>
		<th>${financePaymentRecord.paymentTime}</th>
		<#if financePaymentRecord.customerPaymentTime?string("yyyy-MM-dd") == financePaymentRecord.paymentTime?string("yyyy-MM-dd")>
		<th>N</th>
		<#else>
		<th>Y</th>
		</#if>
		<th></th>
		</#if>
		</tr>
		</#list>
		<tr>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th>合计</th>
		<th>${financeMoney}</th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		</tr>
    </tbody>     
    </table>  
	</#if>
</form>
<form method="post">
<#assign x=overTimeOrderList?size>
	<#if x == 0>
		<table class="formdata">
		<caption>1.今天无催收款。</caption>
		<br/>
		<thead></thead>
		<tbody></tbody>
	<#else>
    <table class="formdata">  
    <caption>2.需要逾期催收${overTimeOrderList?size}笔${nowMoney}元，请注意催收。详细清单如下:</caption>  
    <thead>
    <tr>
	<th>编号</th>
	<th>订单号</th>
	<th>租约号</th>
	<th>租客 </th>
	<th>应付日期 </th>
	<th>订单金额</th>
	<th>已支付</th>
	<th>订单状态</th>
	<th>逾期天数</th>
	</thead>
	</tr>
	<tbody>
		<#list overTimeOrderList as order>
		<tr>
		<th>${order_index+1}</th>
		<th>${order.orderNo}</th>
		<th>${order.contract.contractNo}</th>
		<th>${order.contract.customer.name}</th>
		<th>${order.dueDate}</th>
		<th>${order.totalRent}</th>
		<th>${order.paidRent!"0"}</th>
		<th>未支付</th>
		<th>${order.overDueDays}</th>
		</tr>
		</#list>
		<tr>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th>合计</th>
		<th>${nowMoney}</th>
		<th></th>
		<th></th>
		<th></th>
		</tr>
    </tbody>     
    </table>
    </#if> 
</form>
<form method="post">
	<#assign x=futureOrderList?size>
	<#if x == 0>
		<table class="formdata">
		<caption>1.未来七天无应收款清单。</caption>
		<br/>
		<thead></thead>
		<tbody></tbody>
	<#else>
    <table class="formdata">  
    <caption>3.未来7天内应收款清单共 ${futureOrderList?size}笔${futureMoney}元，详细清单如下：</caption>  
    <thead>
    <tr>
	<th>编号</th>
	<th>订单号</th>
	<th>租约号</th>
	<th>租客 </th>
	<th>应付日期 </th>
	<th>订单金额</th>
	<th>订单状态</th>
	<th>到期天数</th>
	</tr>
	</thead>
	<tbody>
		<#list futureOrderList as order>
		<tr>
		<th>${order_index+1}</th>
		<th>${order.orderNo}</th>
		<th>${order.contract.contractNo}</th>
		<th>${order.contract.customer.name}</th>
		<th>${order.dueDate}</th>
		<th>${order.totalRent}</th>
		<th>未支付</th>
		<th>${order.overDueDays}</th>
		</tr>
		</#list>
		<tr>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th>合计</th>
		<th>${futureMoney}</th>
		<th></th>
		<th></th>
		</tr>
    </tbody>     
    </table>
    </#if>
</form>
</body>
</html>
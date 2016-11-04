<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="script/template" id="cashflowItemTmpl">
  <td>{%= appArriveRecord.serialNo %}</td>
  <td>{%= appArriveRecord.accountSide === 'debit' ? appArriveRecord.payName : appArriveRecord.receiveName %}</td>
  <td>{%= appArriveRecord.accountSide === 'debit' ? appArriveRecord.payAcNo : appArriveRecord.receiveAcNo %}</td>
  <td class="amount">{%= appArriveRecord.amount.formatMoney(2, '') %}</td>
  <td class="happen-time">{%= new Date(appArriveRecord.time).format('yyyy-MM-dd HH:mm:ss') %}</td>
  <td>{%= appArriveRecord.summary %}</td>
  <td class="audit-cols">
    <span class='reconcile-status reconcile-{%= appArriveRecord.auditStatus.toLowerCase() %}'>
      {%= DebitCashFlowConst.AuditStatus[appArriveRecord.auditStatus] %}
    </span>
  </td>
  <td class="mark-type">
    {% if(appArriveRecord.fourthAccountCode){ %}
        {%= appArriveRecord.fourthAccountAlias %}
    {% }else if(appArriveRecord.thirdAccountCode){ %}
        {%= appArriveRecord.thirdAccountAlias %}
    {% }else if(appArriveRecord.secondAccountCode){ %}
        {%= appArriveRecord.secondAccountAlias %}
    {% }else if(appArriveRecord.firstAccountCode){ %}
        {%= appArriveRecord.firstAccountAlias %}
    {% }else if(appArriveRecord.markInfo.financialAccountName){ %}
        {%= appArriveRecord.markInfo.financialAccountAlias %}
    {% }else{ %}
        无
    {% } %}
  </td>
  <td class="operation-cols">
    {% if(appArriveRecord.auditStatus == 'CREATE') { %}
      <a href="" class="cancel-bill">取消</a>
    {% }else { %}
      <a href="" class="regulate-bill">调账</a>
    {% } %}
    <a href="javascript: void 0;" class="mark-bill">标记</a>
    <a href="javascript: void 0;" class="detail-bill">详情</a>
  </td>
  <td>
    <a class="expand-bill" href=""><i class="icon icon-expand"></i></a>
  </td>
</script>
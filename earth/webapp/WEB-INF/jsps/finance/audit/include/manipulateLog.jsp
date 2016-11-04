<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="script/template" id="manipultateLogTmpl">
  <div>
    <table class="vertical-thead col-1 col-3">
        <tr>
            <td>银行流水号</td>
            <td>{%= cashFlowDetail.cashFlowSeriralNo %}</td>
            <td>付款方账号</td>
            <td>{%= cashFlowDetail.payerAccount %}</td>
        </tr>
        <tr>
            <td>银行账户信息</td>
            <td>{%= cashFlowDetail.bankShortName %}</td>
            <td>流水发生时间</td>
            <td>{%= new Date(cashFlowDetail.time).format('yyyy-MM-dd HH:mm:ss') %}</td>
        </tr>
        <tr>
            <td>付款方名称</td>
            <td>{%= cashFlowDetail.payerName %}</td>
            <td>发生金额</td>
            <td>{%= (+cashFlowDetail.amount).formatMoney(2, '') %}元</td>
        </tr>
        <tr>
            <td>付款方开户行</td>
            <td>{%= cashFlowDetail.bankName %}</td>
            <td>银行备注</td>
            <td>{%= cashFlowDetail.summary %}</td>
        </tr>
        <tr>
            <td>对账状态</td>
            <td class="color-danger">{%= cashFlowDetail.auditStatusDesc %}</td>
            <td>{%= cashFlowDetail.billTypeDesc %}房源编号</td>
            <td>{%= cashFlowDetail.relatedHouseSourceNo %}</td>
        </tr>
        <tr>
            <td>{%= cashFlowDetail.billTypeDesc %}账单编号</td>
            <td width="31%">{%= cashFlowDetail.relatedBillNo %}</td>
            <td>{%= cashFlowDetail.billTypeDesc %}租约编号</td>
            <td width="31%">{%= cashFlowDetail.relatedContractNo %}</td>
        </tr>
        <tr>
          <td>收款方式</td>
          <td>{%= cashFlowDetail.paymentWay %}</td>
          <td></td>
          <td></td>
        </tr>
    </table>
    <br>
    <table id="logList">
        <thead>
            <tr>
                <td>操作时间</td>
                <td>操作内容</td>
                <td>操作员</td>
            </tr>
        </thead>
        <tbody>
            {% _.each(auditLog, function(item, index){ %}
              <tr class="log-item" style="{%= index != 0 && 'display:none'%}">
                  <td>{%= new Date(item.time).format('yyyy-MM-dd HH:mm:ss') %}</td>
                  <td>{%= item.journalVoucherOperation %}:{%= item.content  %}(账单号)</td>
                  <td>{%= item.operatorUserName %}</td>
              </tr>
            {% }); %}
            {% if(auditLog.length > 1) { %}
              <tr>
                  <td colspan="3" style="text-align:center;background: #eee;"><a href="#" class="fold in">展开</a></td>
              </tr>
            {% } %}
        </tbody>
    </table>
  </div>
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="script/template" id="billCardItemTmpl">
  <tr class="record-item" data-billing_plan_uuid="{%= item.billingPlanUuid %}" data-journal_voucher_uuid="{%= item.journalVoucherUuid %}">
    <td><a target="billingsearch" href="../billing-plan/index?appId={%= app.appId %}&billNo={%= item.showData.billingPlanNumber %}">{%= item.showData.billingPlanNumber %}</a></td>
    <td><div class="text-overflow" data-title="{%= item.showData.billingPlanName %}">{%= item.showData.billingPlanName %}</div></td>
    <td>{%= item.showData.subjectMatterSourceNo %}</td>
    <td>{%= new Date(item.showData.dueDate).format('yyyy/MM/dd') %}</td>
    <td>{%= item.showData.accountName %}</td>
    <td>{%= item.showData.accountNo %}</td>
    <td>{%= item.showData.billingPlanType %}</td>
    <td>{%= (+item.receivableAmount).toFixed(2) %}</td>
    <td class="paid-money">{%= (+item.currentSpecificAmount).toFixed(2) %}</td>
    <td class="reconcile-money">
        <input class="reconcile-money-input" type="text" data-subtractvalue="{%= item.subtractAmount.toFixed(2) %}" value="{%= typeof item.bookingAmount != 'undefined' ? item.bookingAmount.toFixed(2) : item.subtractAmount.toFixed(2) %}">
    </td>
    <td>
        <input type="checkbox" class="check-bill" checked>
    </td>
  </tr>
</script>

<script type="script/template" id="billCardTmpl">
  <td colspan="100">
    <div class="bill-card submited-card {%= mode %}-card">
      <h3 class="hd">
  		{%= mode == 'seem' ? '推荐账单': '匹配账单' %}
		<div class="pull-right">
      {% if(mode == 'seem') { %}
  			<a href="javascript: void 0;" class="add-bill">查找账单</a>
  			<a href="javascript: void 0;" class="show-create-bill">一键制单</a>
      {% } %}
		</div>
	  </h3>
      <div class="bd">
        <table class="bill-table suspect-table" border="0">
          <thead>
            <th width="150">账单编号</th>
            <th>账单名称</th>
            <th>自有物业编号</th>
            <th>应付日期</th>
            <th>付款方名称</th>
            <th>付款方账号</th>
            <th>账单类型</th>
            <th>应付金额</th>
            <th>已付金额</th>
            <th>{%= mode == 'seem' ? '对账金额': '本次到账金额' %}<i class="icon icon-help pull-right" data-title="账单状态为未支付时，默认值为账单应付金额；<br />账单状态为未付清时，默认值为应付金额—已付金额用户手工输入对帐金额不得大于默认值。"></i></th>
            {% if(mode == 'seem') { %}
              <th>操作</th>
            {% } %}
          </thead>
          <tbody class="record-list">
              {% _.each(records, function(item) { %}
                  <tr class="record-item" data-billing_plan_uuid="{%= item.billingPlanUuid %}" data-journal_voucher_uuid="{%= item.journalVoucherUuid %}">
                      {% if(mode != 'match' || item.journalVoucherStatus == 'VOUCHER_ISSUED') { %}
                          <td><a target="billingsearch" href="../billing-plan/index?appId={%= app.appId %}&billNo={%= item.showData.billingPlanNumber %}">{%= item.showData.billingPlanNumber %}</a></td>
                          <td><div class="text-overflow" data-title="{%= item.showData.billingPlanName %}">{%= item.showData.billingPlanName %}</div></td>
                          <td class="subjectMatterSourceNo">{%= item.showData.subjectMatterSourceNo %}</td>
                          <td>{%= new Date(item.showData.dueDate).format('yyyy/MM/dd') %}</td>
                          <td>{%= item.showData.accountName %}</td>
                          <td>{%= item.showData.accountNo %}</td>
                          <td>{%= item.showData.billingPlanType %}</td>
                          <td>{%= item.receivableAmount.toFixed(2) %}</td>
                          <td class="paid-money">{%= mode === 'match' ? item.settlementAmount.toFixed(2) : item.currentSpecificAmount.toFixed(2) %}</td>
                          {% if(mode == 'seem') { %}
                              <td class="reconcile-money">
                                  <input class="reconcile-money-input {%= typeof item.bookingAmount != 'undefined' ? '' : 'default' %}" type="text" data-subtractvalue="{%= item.subtractAmount.toFixed(2) %}" value="{%= typeof item.bookingAmount != 'undefined' ? item.bookingAmount.toFixed(2) : item.subtractAmount.toFixed(2) %}">
                              </td>
                              <td>
                                  <input type="checkbox" class="check-bill" {%= item.journalVoucherStatus == 'VOUCHER_ISSUED' ? 'checked' : '' %}>
                              </td>
                          {% }else if(mode == 'match') { %}
                              <td class="reconcile-money">{%=  item.bookingAmount.toFixed(2) %}</td>
                          {% } %}
                      {% } %}
                  </tr>
              {% }); %}
          </tbody>
        </table>
      </div>
      <div class="ft clearfix">
        {% if(mode == 'seem') %}
          <div class="pull-left">
          </div>
          <div class="pull-right">
            <span class="summary">
              合计对账金额：<span class="money">{%= totalDebitAmount %}</span>
            </span>
       			<span class="summary">
              剩余对账金额：<span class="balance-money">{%= (amount-totalDebitAmount).toFixed(2) %}</span>
            </span>
            <a href="javascript: void 0;" class="submit">提交</a>
          </div>
        {% else if(mode == 'match') { %}
          <div class="pull-right">
            <span class="summary">
              合计对账金额：<span class="money">{%= totalDebitAmount %}</span>
            </span>
          </div>
        {% } %}
      </div>
    </div>
  </td>
</script>
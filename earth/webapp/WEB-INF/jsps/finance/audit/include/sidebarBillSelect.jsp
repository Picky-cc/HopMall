<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="script/template" id="sidebarBillSelectTmpl">
  <div class="hd">
    {% if(type === 'account-list') { %}
      <a class="back">返回</a>
      <div class="title">选择订单</div>
    {% }else if(type === 'lease-list'){ %}
      <div class="title">搜索租约</div>
    {% } %}
    <a class="remove">关闭</a>
  </div>
  <div class="bd">
    {% if(type === 'lease-list') { %}
      <div class="wrapper">
        <input type="text" name="keyWord" class="form-control" placeholder="可输入承租人名称，相关自有编号，物业地址" value="{%= keyWord %}" autocomplete="off">
      </div>
    {% }%}

    <div id="results">
      {% if(list.length>0) { %}
        <ul class="list">
          {% _.each(list, function(item) { %}
            <li class="item">
              {% if(type === 'lease-list') { %}
                <a class="lease" 
                  data-businesscontractuuid="{%= item.businessContractUuid %}" 
                  data-sourcepropertyno="{%= item.sourcePropertyNo %}" 
                  data-title="{%= item.sourcePropertyNo + ' - ' + item.address %}">{%= _.compact([item.sourceContractNo, item.contractName]).join('-') %}</a>
              {% }else if(type === 'account-list') { %}
                <a class="account" data-billingplanuuid="{%= item.billingPlanUuid %}">{%= item.billingPlanName %}</a>
              {% } %}
            </li>
          {% }) %}
        </ul>
      {% }else { %}
        <p class="text-align-center">没有数据</p>
      {% } %}
    </div>

    {% if(type === 'account-list') { %}
      <p class="text-align-center">尚未录入目标账单？<a class="create" href="#">一键制单</a></p>
    {% } %}

  </div>
</script>
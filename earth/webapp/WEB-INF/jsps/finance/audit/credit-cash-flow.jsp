<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.zufangbao.sun.utils.DateUtils" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

  <fmt:setBundle basename="ApplicationMessage" />
  <fmt:setLocale value="zh_CN" />

  <%@ include file="/WEB-INF/include/meta.jsp"%>
  <%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
  <title>贷方流水对账 - 五维金融金融管理平台</title>

  <!--[if lt IE 8]>
    .bill-card .bill-table .reconcile-money .error:after {
      content: none;
    }
  <![endif]-->
  
  <%@ include file="/WEB-INF/jsps/finance/audit/include/cashflowItem.jsp"%>
  <%@ include file="/WEB-INF/jsps/finance/audit/include/billCard.jsp"%>
  <%@ include file="/WEB-INF/jsps/finance/audit/include/exportKingDeeData.jsp"%>
  <%@ include file="/WEB-INF/jsps/finance/audit/include/sidebarBillSelect.jsp"%>
  <%@ include file="/WEB-INF/jsps/finance/audit/include/manipulateLog.jsp"%>
  <%@ include file="/WEB-INF/jsps/finance/audit/include/markCashType.jsp"%>
  <%@ include file="/WEB-INF/jsps/finance/audit/include/showCreateBill.jsp"%>
  <%@ include file="/WEB-INF/jsps/finance/audit/include/addCashFlow.jsp"%>
 
  
</head>
<body>

  <%@ include file="/WEB-INF/include/header.jsp"%>
  
  <div class="web-g-main">
    <%@ include file="/WEB-INF/include/aside.jsp"%>
    <div data-commoncontent='true' class="content content-cashflow">
      <input type="hidden" value='${ chartOfAccount }' id="chartOfAccount" >
      <div class="scroller">
        <div class="lookup-params">
          <div class="inner clearfix">
            <span class="item">
              <input type="text" placeholder="关键字搜索" class="form-control real-value small" style="width: 150px" name="queryKeyWord" value="">
              <div class="auto-complete-list"></div>
            </span>

            <span class="item hide">
              <select name="appId" class="form-control real-value small" id="appIdSelect">
                <c:if test="${appList!= null and appList.size() >1 }">
	                <option value="">服务商名称</option>
	                <option value="">全部</option>
                </c:if>
                <c:forEach var="item" items="${appList}">
                	<option value="${item.appId}">${item.name}</option>
                </c:forEach>
              </select>
            </span>
            <span class="item hide">
              <select name="accountName" class="form-control real-value small account-info" id="accountName">
                <option value="">银行账户名</option>
                <option value="">全部</option>
                <c:forEach var="item" items="${accountList}">
                	<option data-id="${ item.id }" value="${ item.accountName}">${item.accountName}</option>
                </c:forEach>
              </select>
            </span>
            <span class="item">
              <select name="accountNo"class="form-control real-value small account-info" id="accountNo">
                <option value="">银行账户号</option>
              	<c:forEach var="item" items="${accountList}">
                	<option data-id="${ item.id }" data-attr='${item.attrJson}' value="${item.accountNo}">${item.accountShortName}</option>
                </c:forEach>
              </select>
            </span>
            <span class="item">
              <select name="auditStatusValue" class="form-control real-value small" id="">
                <option value="-1">对账状态</option>
                <option value="-1">全部</option>
                <c:forEach var="item" items="${auditStatusList}">
                	<option value="${ item.ordinal}"><fmt:message key="${ item.key}"></fmt:message></option>
                </c:forEach>
              </select>
            </span>
            <span class="item">
              <select name="financialAccountName" class="form-control real-value small account-info">
                <option value="">标记类型</option>
              </select>
            </span>
            <span class="item beginend-datepicker">
            	<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
            		<jsp:param value="group" name="type"/>
            	</jsp:include>
            </span>
            <span class="item">
              <input type="text" placeholder="流水金额" class="form-control real-value small" style="width: 115px" name="amountString" value="">
            </span>
            <button id="lookup" class="btn btn-primary">查询</button>
            <button id="addCashFlow" class="btn btn-primary" style="display:none;">添加现金流</button>
          </div>
        </div>
        <div class="table-area">
          <table class="data-list data-list-cashflow" data-action="${ctx}/finance//cash-flow-audit/query?accountSide=credit" data-autoload="true">
            <thead>
              <tr>
                <th>银行流水</th>
                <th>收款方名称</th>
                <th>收款方账号</th>
                <th>发生金额（元）</th>
                <th>发生时间</th>
                <th>银行备注</th>
                <th width="70">对账状态</th>
                <th width="70">标记类型</th>
                <th width="120">操作</th>
                <th width="60"><!-- 展开 --></th>
              </tr>
            </thead>
            <tbody>
            </tbody>
          </table>
        </div>
      </div>
      <div class="operations">
          <div class="lt pull-left">
              <span class="item">
                  <button class="btn" id="allExpand">全部展开</button>
              </span>
            <!--   <span class="item">
                  <button class="btn" id="exportKingDeeVoucher">导出金蝶凭证数据</button>
              </span> -->
              <span class="item">
                  <button class="btn" id="exportAppArriveRecord">导出现金流</button>
              </span>
          </div>
          <div class="lt pull-right">
                   <jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
		            <jsp:param value="page-control" name="type"/>
		            <jsp:param value="${size }" name="size"/>
		            <jsp:param value="${everyPageSize }" name="pageRecordNum"/>
		            <jsp:param value="true" name="advanced"/>
		          </jsp:include>
          </div>
      </div>
    </div>
  </div>

  <%@ include file="/WEB-INF/include/script-newlayout.jsp"%>

</body>
</html>

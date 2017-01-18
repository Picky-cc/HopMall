<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh_CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
	<title>银企直联流水 - 五维金融金融管理平台</title>
</head>
<body>

<%@ include file="/WEB-INF/include/header.jsp"%>
  
  <div class="web-g-main">
    <%@ include file="/WEB-INF/include/aside.jsp"%>
    <div data-commoncontent='true' class="content content-cashflow">
      <div class="scroller">
       <form method="GET" action="${ctx}/capital/directbank-cash-flow/search"> 
        <div class="lookup-params">
          <div class="inner clearfix">
	 			<span class="item beginend-datepicker">
	              <jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
	                <jsp:param value="group" name="type"/>
	                <jsp:param value="startDateString" name="paramName1"/>
	                <jsp:param value="endDateString" name="paramName2"/>
	                <jsp:param value="请输入起始日期" name="placeHolder1"/>
	                <jsp:param value="请输入终止日期" name="placeHolder2"/>
	                <jsp:param value="${directbankCashFlowQueryModel.startDateString }" name="paramValue1"/>
	                <jsp:param value="${directbankCashFlowQueryModel.endDateString }" name="paramValue2"/>
	              </jsp:include>
	            </span>
             <span class="item">
               <select name="accountSide" class="form-control real-value small"">
               	 <option value="">借贷</option>
                 <option value="2" <c:if test="${directbankCashFlowQueryModel.accountSide == 2 }">selected="selected"</c:if>>借</option>
                 <option value="1" <c:if test="${directbankCashFlowQueryModel.accountSide == 1 }">selected="selected"</c:if>>贷</option>
               </select>
             </span>     
             <span class="item">
               <select name="accountId" class="form-control apps">
               <!-- 	<option value="">账户</option> -->
                 <c:forEach var="item" items="${accountList}"
                   varStatus="status">
                   <option value="${item.id }" <c:if test="${directbankCashFlowQueryModel.accountId == item.id }">selected="selected"</c:if> >${item.markedAccountNo }</option>
                 </c:forEach>
               </select>
             </span>     
          
		<span class="item has-suffix">
          <input type="text" placeholder="对方账号" value="${directbankCashFlowQueryModel.recipAccNo }" class="form-control real-value" name="recipAccNo">
          <span class="glyphicon glyphicon-remove suffix clear-input"></span>
        </span>
        <span class="item has-suffix">
          <input type="text" placeholder="对方户名" value="${directbankCashFlowQueryModel.recipName}" class="form-control real-value" name="recipName">
          <span class="glyphicon glyphicon-remove suffix clear-input"></span>
        </span>
        <span class="item has-suffix">
          <input type="text" placeholder="摘要" value="${directbankCashFlowQueryModel.summary }" class="form-control real-value" name="summary">
          <span class="glyphicon glyphicon-remove suffix clear-input"></span>
        </span>
	     <span class="item">
             <button id="lookup" class="btn btn-primary"><i class="glyphicon glyphicon-search"></i>查询</button>
           </span>
	</div>
	 </div>
       </form>       

       <c:if test="${not empty infoMessage}">
            <div class="alert alert-success alert-dismissable alert-fade top-margin-10 text-align-center">
              <button type="button" class="close" data-dismiss="alert">&times;</button>
              <i class="glyphicon glyphicon-info-sign"></i>&nbsp;&nbsp;
              <fmt:message key="${infoMessage}" />
            </div>
       </c:if>
         <div class="table-area">
         <table class="data-list" style="overflow:auto">
              <thead>
                <tr>
                  <th>流水号</th>
                  <th>借贷标志</th>
                  <th>借方发生额</th>
                  <th>贷方发生额</th>
                  <th>余额</th>
                  <!-- <th>凭证号</th> -->
                  <th>对方账号</th>
                  <th>对方户名</th>
                  <th>对方开户号</th>
                  <th>入账时间</th>
                  <th>摘要</th>
                  <th>附言</th>
                </tr>
              </thead>
              <tbody>
              	<c:choose>
              		<c:when test="${flow_result.code == '1' }">
              			<tr>
              				<td colspan = "9">${flow_result.message }</td>
              			</tr>
              		</c:when>
              		<c:when test="${not empty flow_result.data.flowList}">
              			<c:forEach var="flow" items="${flow_result.data.flowList}">
		                  <tr>
							<td>${flow.serialNo }</td>		                    
		                    <td><c:choose>
		                        <c:when test="${not empty flow.drcrf and flow.drcrf eq 1}">贷</c:when>
		                        <c:otherwise>借</c:otherwise>
		                      </c:choose></td>
		                    <td><fmt:formatNumber value="${flow.creditAmount}" type="number" pattern="#0.00" /></td>
		                    <td><fmt:formatNumber value="${flow.debitAmount}" type="number" pattern="#0.00" /></td>
		                    <td><fmt:formatNumber value="${flow.balance}" type="number" pattern="#0.00" /></td>		                    
		                    <%-- <td>${flow.vouhNo}</td> --%>
		                    <td>${flow.recipAccNo}</td>
		                    <td>${flow.recipName}</td>
		                    <td>${flow.recipBkName }</td>
		                    <td><fmt:formatDate value="${flow.time}" pattern="yyyy/MM/dd HH:mm:ss" /></td>
		                    <td>${flow.summary}</td>
		                    <td>${flow.postScript}</td>

		                  </tr>
		                </c:forEach>
		                <tr>
		                	<td></td>
		                	<td>合计</td>
		                	<td>借:<fmt:formatNumber value="${flow_result.data.debitSum}" type="number" pattern="#0.00" /></td>
		                	<td>贷:<fmt:formatNumber value="${flow_result.data.creditSum}" type="number" pattern="#0.00" /></td>                       
		                	<td></td>
		                	<td></td>
		                	<td></td>
		                	<td></td>
		                	<td></td>
		                </tr>
		            </c:when>
              	</c:choose>
              	
                
              </tbody>
          </table>
         </div>

    </div>

  </div>
	</div>

	 <%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	<script src="${ctx.resource}/js/bootstrap-modal-trigger.js"></script>
	<script src="${ctx.resource}/js/bootstrap.validate.js"></script>

</body>
</html>

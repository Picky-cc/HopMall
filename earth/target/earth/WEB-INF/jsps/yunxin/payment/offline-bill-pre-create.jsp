<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh-CN" />

<input type="hidden" name="guaranteeRepaymentUuids" value='${guaranteeRepaymentUuids}' />
<table class="vertical-thead col-1 col-3">
	<tr>
		<td style="width: 100px;color: black;">还款方名称</td>
		<td><input type="text" name="payerAccountName" /></td>
		<td style="width: 15%;color: black;">支付机构</td>
		<td><input type="text" name="" /></td>
	</tr>
	<tr>
		<td style="color: black">还款方开户行</td>
		<td><input type="text" name="bankShowName" /></td>
		<td style="color: black">支付机构流水号</td>
		<td><input type="text" name="serialNo" /></td>
	</tr>
	<tr>
		<td style="color: black">还款方账户</td>
		<td><input type="text" name="payerAccountNo" /></td>
		<td style="color: black">发生时间</td>
		<td>
			<c:choose>
				<c:when test="${not empty date }">
					<fmt:formatDate value="${ date}" pattern="yyyy-MM-dd" />
				</c:when>
				<c:otherwise>
					<div class="input-group date pull-left ">
						<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
							<jsp:param value="single" name="type" />
							<jsp:param value="true" name="pickTime" />
							<jsp:param value="发生时间" name="placeHolder1" />
							<jsp:param value="tradeTimeString" name="paramName1" />
						</jsp:include>
					</div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr>
		<td style="color: black">发生金额</td>
		<td>
			<c:choose>
				<c:when test="${not empty totalAmount}">${totalAmount }元</c:when>
				<c:otherwise><input type="text" name="amount" /></c:otherwise>	
			</c:choose>
		</td>
		<td style="color: black">备注</td>
		<td><input type="text" style="width: 100%" name="comment" /></td>
	</tr>
</table>


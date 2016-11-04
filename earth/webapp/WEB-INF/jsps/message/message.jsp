<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh_CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>短信管理 - 五维金融金融管理平台</title>
</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div data-commoncontent='true' class="content">
			<div class="scroller">
				<div class="lookup-params">
					<form method="GET" action="${ctx}/message">
						<input type="hidden" name="filter" value="true"> <span
							class="item"> <label>发送状态：</label> <select
							name="allowedSendStatus" id="allowedSendStatus"
							class="form-control" required="required">
								<option value="-1"
									<c:if test="${ allowedSendStatus eq -1 }">selected</c:if>>所有</option>
								<option value="1"
									<c:if test="${ allowedSendStatus eq 1 }">selected</c:if>>已允许</option>
								<option value="0"
									<c:if test="${ allowedSendStatus eq 0 }">selected</c:if>>未允许</option>
						</select>
						</span> <span class="item"> <label>客户编号：</label><input type="text"
							class="form-control" name="clientId" value="${clientId}"
							placeholder="请输入客户编号">
						</span> <span class="item"> <select class="form-control"
							name="smsTemplateEnum">
								<option value="-1">所有短信</option>
								<c:forEach var="stEnum" items="${smsTemplateEnumList }">
									<option value="${stEnum.ordinal() }" ${stEnum.ordinal() eq smsTemplateEnum ? 'selected' : ''} >
										<fmt:message key="${stEnum.desc}"></fmt:message>
									</option>
								</c:forEach>
						</select>
						</span> <span class="item beginend-datepicker"> <jsp:include
								page="/WEB-INF/include/plugins/datetimepicker.jsp">
								<jsp:param value="group" name="type" />
								
								
								<jsp:param value="startDate" name="paramName1" />
								<jsp:param value="endDate" name="paramName2" />
								<jsp:param value="${startDate}" name="paramValue1" />
								<jsp:param value="${endDate}" name="paramValue2" />
								<jsp:param value="请输入创建起始日期" name="placeholder1" />
								<jsp:param value="请输入创建终止日期" name="placeholder2" />
							</jsp:include>
						</span> <span class="item">
							<button id="lookup" class="btn btn-primary">
								<i class="glyphicon glyphicon-search"></i>查询
							</button>
						</span>
					</form>
				</div>
				<div class="table-area">
					<table class="data-list">
						<thead>
							<tr>
								<th>序号</th>
								<th>客户编号</th>
								<th>短信类型</th>
								<th>短信内容</th>
								<th>合作商户号</th>
								<th>创建时间</th>
								<th>请求时间</th>
								<th>响应时间</th>
								<th>响应结果</th>
								<th>允许发送</th>
								<th>发送状态</th>
								<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
									<th>操作</th>
								</sec:authorize>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${smsQueneQueryModelList}"
								varStatus="status">
								<tr>
									<td nowrap>${status.index + 1}</td>
									<td>${item.smsQuene.clientId}</td>
									<td nowrap>${item.smsTemplate.title}</td>
									<td width="40%">${item.smsQuene.content}</td>
									<td nowrap>${item.smsQuene.platformCode}</td>
									<td>${item.smsQuene.createTime}</td>
									<td>${item.smsQuene.requestTime}</td>
									<td>${item.smsQuene.responseTime}</td>
									<td>${item.smsQuene.responseTxt}</td>
									<td><c:if
											test="${item.smsQuene.allowedSendStatus == true}">是</c:if> <c:if
											test="${item.smsQuene.allowedSendStatus == false}">否</c:if></td>
									<td><fmt:message key="${item.smsQuene.smsSendStatus.key}" /></td>
									<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
										<td nowrap><c:if
												test="${item.smsQuene.allowedSendStatus == false}">
												<a href="#" onclick="activate(${item.smsQuene.id})"
													class="hover-no-text-decoration">发送</a>
											</c:if> <c:if test="${item.smsQuene.smsSendStatus.ordinal == 2 }">
												<a href="#" onclick="reSend(${item.smsQuene.id})"
													class="hover-no-text-decoration">重发</a>
											</c:if></td>
									</sec:authorize>
							</c:forEach>
						</tbody>

					</table>
				</div>
			</div>
			<div class="operations lookup-params">
				<sec:authorize ifNotGranted="ROLE_TRUST_OBSERVER">
					<span class="item"> <label>自动发送：</label>
						<button id="allowedSendBtn"
							onclick="changeAllowSend(${allowedSendFlag})"
							class="btn btn-primary">
							<c:if test="${allowedSendFlag == true}">是（点击关闭）</c:if>
							<c:if test="${allowedSendFlag == false}">否（点击开启）</c:if>
						</button>
					</span>
					<span class="item">
						<button id="sendSuccSmsBtn" onclick="sendSuccSms()"
							class="btn btn-primary">发送所有未发送的成功短信</button>
					</span>
					<span class="item">
						<button id="deleteNotSuccSmsBtn" onclick="deleteNotSuccSms()"
							class="btn btn-primary">删除所有非成功未允许发送短信</button>
					</span>
				</sec:authorize>
				<span class="pull-right"> <c:choose>
						<c:when test="${not empty queryString}">
							<jsp:include page="/WEB-INF/include/page.jsp">
								<jsp:param value="message" name="url" />
								<jsp:param name="queryString" value="${queryString}" />
							</jsp:include>
						</c:when>
						<c:otherwise>
							<jsp:include page="/WEB-INF/include/page.jsp">
								<jsp:param value="message" name="url" />
							</jsp:include>
						</c:otherwise>
					</c:choose>
				</span>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	<script src="${ctx.resource}/js/bootstrap-modal-trigger.js"></script>
	<script src="${ctx.resource}/js/bootstrap.validate.js"></script>
	<script src="${ctx.resource}/js/bootstrap.validate.trigger.js"></script>

	<script type="text/javascript">
	function activate(smsQueneId){
		
		var r = confirm("是否确认激活允许发送！");
		if(r == true){
		$.ajax({
			type : 'POST',
			url : "${ctx}/message/activate",
			data : {
				'smsQueneId' : smsQueneId,
			},
			dataType : 'json',
			error : function() {
				alert('网络错误！稍后再试！');

			},
			success : function(data) {
				if (data.code == '0') {
					alert(data.message);
				} else {
					alert(data.message);
				}
				window.location.reload();

			}
		});
		}
	};
	function reSend(smsQueneId){
		var r = confirm("是否确认再次发送短信！");
		if(r == true){
			$.ajax({
				type : 'POST',
				url : "${ctx}/message/reSend",
				data : {
					'smsQueneId' : smsQueneId,
				},
				dataType : 'json',
				error : function() {
					alert('网络错误！稍后再试！');

				},
				success : function(data) {
					if (data.code == '0') {
						alert(data.message);
					} else {
						alert(data.message);
					}
					window.location.reload();
				}
			});
		}
	}
	function changeAllowSend(allowedSendFlag) {
		var r = confirm("是否确认改变自动允许发送状态！");
		if(r == true){
			$.ajax({
				type : 'POST',
				url : "${ctx}/message/changeAllowSend",
				data : {
					'allowedSendFlag' : allowedSendFlag,
				},
				dataType : 'json',
				error : function() {
					alert('网络错误！稍后再试！');

				},
				success : function(data) {
					if (data.code == '0') {
						alert(data.message);
					} else {
						alert(data.message);
					}
					window.location.reload();
				}
			});
		}
	}
	
	function sendSuccSms() {
		var r = confirm("是否确认一键发送所有未发送的成功扣款短信！");
		if(r == true){
			$.ajax({
				type : 'POST',
				url : "${ctx}/message/sendSuccSms",
				data : {
				},
				dataType : 'json',
				error : function() {
					alert('网络错误！稍后再试！');

				},
				success : function(data) {
					if (data.code == '0') {
						alert(data.message);
					} else {
						alert(data.message);
					}
					window.location.reload();
				}
			});
		}
	}
	
	function deleteNotSuccSms() {
		var r = confirm("是否确认删除所有非成功未允许发送短信！");
		if(r == true){
			$.ajax({
				type : 'POST',
				url : "${ctx}/message/deleteNotSuccSms",
				data : {
				},
				dataType : 'json',
				error : function() {
					alert('网络错误！稍后再试！');

				},
				success : function(data) {
					if (data.code == '0') {
						alert(data.message);
					} else {
						alert(data.message);
					}
					window.location.reload();
				}
			});
		}
	}
	
	</script>
</body>
</html>

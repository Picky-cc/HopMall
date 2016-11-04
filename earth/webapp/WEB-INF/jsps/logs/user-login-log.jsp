<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

  <fmt:setBundle basename="ApplicationMessage" />
  <fmt:setLocale value="zh_CN" />

  <%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
  <title>用户日志 - 五维金融金融管理平台</title>
</head>
<body>

  <%@ include file="/WEB-INF/include/header.jsp"%>
  
  <div class="web-g-main">

    <%@ include file="/WEB-INF/include/aside.jsp"%>
    <div class="content">
    	<div class="scroller">
			<div class="position-map">
				<div class="pull-left">
		            当前位置:
		            <span class="item current">用户日志</span>
				</div>
			</div>
	       <c:if test="${not empty infoMessage}">
	            <div class="alert alert-success alert-dismissable alert-fade top-margin-10 text-align-center">
	              <button type="button" class="close" data-dismiss="alert">&times;</button>
	              <i class="glyphicon glyphicon-info-sign"></i>&nbsp;&nbsp;
	              <fmt:message key="${infoMessage}" />
	            </div>
	       </c:if>
	
	       <div class="table-area">
	           <table class="data-list">
	              <thead>
	                <tr>
	                 <th>序号</th>
	                  <th>日志时间</th>
	                  <th>用户id</th>
	                  <th>用户事件</th>
	                  <th>事件内容</th>
	                  <th>登陆ip</th>
	                </tr>
	              </thead>
	              <tbody>
	                <c:forEach var="log" items="${logs}" varStatus = "status">
	                  <tr>                 
	                    <td>${status.index + 1 }</td>
	                    <td>${log.operateTime}</td>
	                    <td>${log.userId}</td>
	                    <td>${log.event}</td>
	                    <td>${log.content}</td>
	                    <td>${log.ip}</td>
	                    <%-- <td>
	                      <a href="${ctx}/logs/${order.id}/detail">详情</a>
	                    </td> --%>
	                  </tr>
	                </c:forEach>
	              </tbody>
	           </table>
	       </div>
    	</div>
    	<div class="operations">
			<c:choose>
				<c:when test="${not empty queryString}">
					<jsp:include page="/WEB-INF/include/page.jsp">
						<jsp:param value="logs/user-login-log" name="url" />
						<jsp:param name="queryString" value="${queryString}" />
					</jsp:include>
				</c:when>
				<c:otherwise>
					<jsp:include page="/WEB-INF/include/page.jsp">
						<jsp:param value="logs/user-login-log" name="url" />
					</jsp:include>
				</c:otherwise>
			</c:choose>
		</div>
    </div>

  </div>
 
<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
</body>
</html>

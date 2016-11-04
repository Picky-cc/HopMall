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
  <title>用户列表- 五维金融金融管理平台</title>
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
		            <span class="item current">用户列表</span>
				</div>
			</div>
	       <c:if test="${not empty infoMessage}">
	            <div class="alert alert-success alert-dismissable alert-fade top-margin-10 text-align-center">
	              <button type="button" class="close" data-dismiss="alert">&times;</button>
	              <i class="glyphicon glyphicon-info-sign"></i>&nbsp;&nbsp;
	              <fmt:message key="${infoMessage}" />
	            </div>
	       </c:if>
		
	       <div class="table-area table-responsive">
	           <table class="data-list  table table-striped table-hover">
	              <thead>
	                <tr>
	                 <th>用户id</th>
	                  <th>权限名称</th>
	                  <th>账户名</th>
	                  <th>持有者</th>
	                  <th>联系邮箱</th>
	                  <th>联系号码</th>
	                  <th>所属公司</th>
	                  <th>状态</th>
	                  <th>操作</th>
	                </tr>
	              </thead>
	              <tbody>
	                <c:forEach var="role" items="${roleList}" varStatus = "status">
	                  <tr>                 
	                    <td>${role.id}</td>
	                    <td>${role.authority}</td>
	                    <td>${role.name}</td>
	                    <td>${role.tUser.name}</td>
	                    <td>${role.tUser.email}</td>
	                    <td>${role.tUser.phone}</td>
	                    <td>${role.tUser.company.fullName}</td>
	                    <td>
	                    	<c:choose>
	                    	<c:when test="${role.thruDate==null}">正常</c:when>
	                    	<c:otherwise>冻结</c:otherwise>
	                    	</c:choose>
	                    </td>
	                    <td>
		                	<a href="${ctx}/edit-user-role/${role.id}">编辑</a>
		                    <c:choose>
			                    <c:when test="${role.thruDate==null}">
			                    	<a data-user-id="${role.id}" class="closeUserBtn" href="javascript:void('0')">关闭</a>
			                    </c:when>
			                    <c:otherwise>&nbsp;&nbsp;&nbsp;关闭</c:otherwise>
		                    </c:choose>
	                    </td>
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
	            <jsp:param value="show-user-list" name="url" />
	            <jsp:param name="queryString" value="${queryString}" />
	          </jsp:include>
	        </c:when>
	        <c:otherwise>
	          <jsp:include page="/WEB-INF/include/page.jsp">
	              <jsp:param value="show-user-list" name="url" />
	          </jsp:include>
	        </c:otherwise>
	      </c:choose>
        </div>
	</div>
  </div>

<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
<script type="text/javascript">
	$(function() {
		$(".closeUserBtn").click(function() {
			var userId = $(this).data("user-id");
			var flag = window.confirm('确定要删除么?');
			if(flag) {
				location.href = "${ctx}/delete-user-role/" + userId;
			}else {
				reload();
			}
		});
		
	});
</script>
</body>
</html>

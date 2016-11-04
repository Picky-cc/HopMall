<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div class="demo2do-green-navbar">
	<div class="container">
		<ul class="nav demo2do-green-nav">
			<c:forEach var="menu" items="${menus}">
				  
                 <sec:authorize access="hasResource('menus','${menu.id}')">
					<li ${menu.id == activeMenu ? 'class="open"' : ''}><a href="${ctx}/${menu.url}">${menu.name}</a></li>
				 </sec:authorize> 
			</c:forEach>
		</ul>
	</div>
</div>
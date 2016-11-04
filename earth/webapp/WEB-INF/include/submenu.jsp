<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<ul class="nav nav-pills nav-stacked demo2do-menu">
		<c:forEach var="menu" items="${submenus}">
			<c:if test="${menu.key == activeMenu}">
				  <sec:authorize access="hasResource('submenus', '${menu.id}')"> 
					<li class="menu-label"><div><i class="glyphicon glyphicon-${menu.icon} glyphicon-position-fix-2"></i>&nbsp;&nbsp;${menu.name}</div></li>
						<c:forEach var="submenu" items="${menu.submenus}">
							<sec:authorize access="hasResource('submenus', '${submenu.id}')"> 
								<li class="submenu ${submenu.id == activeSubmenu ? 'active' : ''}"><a href="${ctx}/${submenu.url}" title="${submenu.description}">${submenu.name}</a></li>
							</sec:authorize>  
						</c:forEach>
				   </sec:authorize> 
			</c:if>
		</c:forEach>
</ul>

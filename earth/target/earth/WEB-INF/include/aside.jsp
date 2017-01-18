<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="type"  value='<%=request.getParameter("type") %>' />
<c:forEach var="menu" items="${submenus}">
	<c:if test="${menu.key == activeMenu}">
		<c:set var="isDisplay" value="true"/>
	</c:if>
 </c:forEach>

<div class="aside-box">
	<c:if test="${not empty isDisplay && isDisplay}">
		<aside class="aside-nav">
		    <nav class="menu">
		        <c:forEach var="menu" items="${submenus}">
		            <c:if test="${menu.key == activeMenu}">
		                  <sec:authorize access="hasResource('submenus', '${menu.id}')"> 
		                        <c:forEach var="submenu" items="${menu.submenus}">
		                            <sec:authorize access="hasResource('submenus', '${submenu.id}')"> 
		                                <li class="menu-item ${not empty submenu.submenus ? 'has-submenu' : '' }  ${submenu.id == activeSubmenu ? 'z-active' : ''}">
		                                	<c:choose>
		                                		<c:when test="${not empty submenu.submenus }">
		                                			<a class="menu-link" href="javascript:void(0)" title="${submenu.description}">${submenu.name}</a>
			                                		<ul class="sub-menu">
			                                			<c:forEach var="secondSubMenu" items="${submenu.submenus }">
			                                				<sec:authorize access="hasResource('submenus', '${secondSubMenu.id}')">
				                                				<li class="sub-menu-item ${secondSubMenu.id ==activeSubmenu ? 'z-active' : ''}"><a href="${ctx}/${secondSubMenu.url}" title="${secondSubMenu.description}">${secondSubMenu.name}</a></li>
			                                				</sec:authorize>
			                                			</c:forEach>
			                                		</ul>
		                                		</c:when>
		                                		<c:otherwise>
		                                			<a class="menu-link" href="${ctx}/${submenu.url }" title="${submenu.description}">${submenu.name}</a>
		                                		</c:otherwise>
		                                	</c:choose>
		                                </li>
		                            </sec:authorize>  
		                        </c:forEach>
		                   </sec:authorize> 
		            </c:if>
		        </c:forEach>
		    </nav>

		</aside>
	</c:if>
	<div class="extend">
		<i class="icon"></i>
	</div>
</div>

<script>
	(function (initAsideFunc) {
		var existJquery = function () {
			if(typeof jQuery === 'function') {
				initAsideFunc(jQuery);
			}else {
				setTimeout(existJquery, 100);
			}
		};
		
		existJquery();
	})(function ($) {
		var $doc = $(document);
		var sessionS = sessionStorage;

		function init (el) {
			// if (sessionS && sessionS.getItem('ASIDE_IS_CLOSED') === 'true') {
			// 	el.addClass('closed');
			// }

			el.find('.sub-menu-item.z-active')
				.first()
				.parents('.menu-item')
				.addClass('z-active');

			createEvent(el);
		}

		function createEvent (el) {
			el.on('click', '.has-submenu .menu-link', function (e) {
				e.preventDefault();
				var par=$(e.target).parent('.menu-item'),
					submenu=par.find('.sub-menu');
				if(par.hasClass('z-active')) {
					submenu.slideUp(100, function () {
						par.removeClass('z-active');
					});
				}else {
					submenu.slideDown(100, function () {
						par.addClass('z-active');
					});
				}
			});

			el.on('click', '.extend', function() {
				el.toggleClass('closed');
				$doc.trigger('toggle.aside', el.hasClass('closed'));

				if (sessionS) {
					if (el.hasClass('closed')) {
						sessionS.setItem('ASIDE_IS_CLOSED', true);
					} else {
						sessionS.setItem('ASIDE_IS_CLOSED', false);
					}
				}
			});
		}

		init($('.aside-box'));

	});
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<sec:authentication property="principal" var="currentPrincipal" scope="page" />

<div class="web-g-hd">
    <div class="bd">
        <div class="logo pull-left">
            <i class="icon"></i>
        </div>

        <button type="button" class="hd-collapsed">
            <span class="glyphicon glyphicon-menu-hamburger"  aria-hidden="true"></span>
        </button>
        <div class="nav pull-left">
            <ul class="menu">
                <c:forEach var="menu" items="${menus}">
                <sec:authorize access="hasResource('menus','${menu.id}')">
                <li class='menu-item ${menu.id} ${menu.id == activeMenu ? "z-active" : ""}'>
                    <a href="${ctx}/${menu.url}">${menu.name}</a>
                </li>
                </sec:authorize> 
                </c:forEach>
            </ul>
        </div>

        <div class="profile pull-right">
            <div class="dropdown">
              <a class="dropdown-toggle" href="javascript: void 0;" data-toggle="dropdown">
                <span class="company-logo">
                    <img src="${ctx.resource}/images/company-logo/yunxin.png" alt="">
                </span>
                <span class="company-name">${currentPrincipal.username}</span>
                <span class="glyphicon"></span>
            </a>
            <ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
                <li class="item-dashboard"><a href="javascript:void 0;" class="btn-toggle-dashboard">任务单</a></li>
                <li class="item-logout"><a href="${ctx}/j_spring_security_logout">退出</a></li>
            </ul>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    (function (initHeaderFunc) {
        var existJquery = function () {
            if(typeof jQuery === 'function') {
                initHeaderFunc(jQuery);
            }else {
                setTimeout(existJquery, 100);
            }
        };
        
        existJquery();
    })(function ($) {
        var $el = $('.web-g-hd');
        var $logo = $el.find('.logo');
        var $profile = $el.find('.profile');
        var $nav = $el.find('.nav');
        var $triggerCollapseBtn = $el.find('.hd-collapsed');
        var nav_width = $nav.width();
        var responsive = function(){
            var width = $(window).width();
            var pullleft_width = $logo.width();
            var pullright_width = $profile.width();
            var mid_width = width - pullleft_width - pullright_width;
            if(nav_width >= mid_width - 20){
                $el.addClass('x-collapse');
                $nav.hide();
            } else {
                $el.removeClass('x-collapse');
                $nav.show();
            }
        };
        var timer = null;
        
        $(window).resize(function(){
            if (timer) {
                clearTimeout(timer);
                timer = null;
            }

            var timer = setTimeout(responsive, 100);
        });

        $triggerCollapseBtn.click(function(e) {
            e.preventDefault();
            $nav.toggle();
        });

        responsive();
    });
</script>


<jsp:include page="/WEB-INF/jsps/dashboard/dashboard.jsp">
    <jsp:param value="${param.show}" name="show"/>
    <jsp:param value="${param.disableToggle}" name="disableToggle"/>
</jsp:include>

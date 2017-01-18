<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul class="pagination pull-right no-top-margin no-bottom-margin">
	<c:if test="${page.prePage}"><li><a href="${ctx}/${param.url}?${empty param.queryString ? '' : param.queryString}${empty param.queryString ? '' : '&'}page=${page.currentPage - page.prePages - 1}">&laquo;</a></li></c:if>
	<c:if test="${page.prePages > 0}">
	 	<c:forEach begin="1" end="${page.prePages}" step="1" varStatus="i">
	  		<li><a href="${ctx}/${param.url}?${empty param.queryString ? '' : param.queryString}${empty param.queryString ? '' : '&'}page=${page.currentPage - page.prePages + i.index - 1}">${page.currentPage - page.prePages + i.index - 1}</a></li>	
	  	</c:forEach>
	</c:if>
	<c:if test="${page.currentPage != 1 or page.nextPages != 0}">
		<li class="active"><a href="javascript: void(0)">${page.currentPage}</a></li>
	</c:if>
	<c:if test="${page.nextPages > 0}">
	  	<c:forEach begin="1" end="${page.nextPages}" step="1" varStatus="i">
	  		<li><a href="${ctx}/${param.url}?${empty param.queryString ? '' : param.queryString}${empty param.queryString ? '' : '&'}page=${page.currentPage + i.index}">${page.currentPage + i.index}</a></li>	
		</c:forEach>
	</c:if>
  	<c:if test="${page.nextPage}"><li><a href="${ctx}/${param.url}?${empty param.queryString ? '' : param.queryString}${empty param.queryString ? '' : '&'}page=${page.currentPage + page.nextPages + 1}">&raquo;</a></li></c:if>
</ul>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ul class="pagination pull-right no-top-margin">
	<li><a href="javascript: void(0)">&nbsp;共&nbsp;${page.totalPage}&nbsp;页</a></li>
</ul>

<ul class="pagination pull-right no-top-margin right-margin-10">
	<c:if test="${page.prePage}"><li><a href="${ctx}/${param.url}?page=${page.currentPage - page.prePages - 1}">&laquo;&nbsp;上一页</a></li></c:if>
	<c:if test="${page.currentPage != 1 or page.nextPage}">
		<li class="active"><a href="javascript: void(0)">第&nbsp;${page.currentPage}&nbsp;页</a></li>
	</c:if>
  	<c:if test="${page.nextPage}"><li><a href="${ctx}/${param.url}?page=${page.currentPage + page.nextPages + 1}">下一页&nbsp;&raquo;</a></li></c:if>
</ul>

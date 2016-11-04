<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh-CN" />

<div class="modal-dialog demo2do-modal-dialog">

	<div class="modal-content">

		<div class="modal-header clearfix">
			<button type="button" class="close close-dialog" data-dismiss="modal">&times;</button>
			<h4 class="modal-title" id="dialoglabel">
				错误
			</h4>
		</div>
			${message }
		</div>
</div>

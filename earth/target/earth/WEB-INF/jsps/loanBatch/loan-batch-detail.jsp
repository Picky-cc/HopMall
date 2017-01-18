<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh-CN" />

<div class="modal-dialog demo2do-modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal">
				<span aria-hidden="true">&times;</span>
			</button>
			<h4 class="modal-title" id="dialoglabel">放款批次详情</h4>
		</div>
		<div>
			<jsp:include page="/WEB-INF/include/system-operate-log.jsp">
				<jsp:param value="${loanBatch.loanBatchUuid}" name="objectUuid" />
			</jsp:include>
		</div>
	</div>
</div>

<script>
	var P = window.Pagination;
	if(P && $) {
		P.find('.sys-log', $('.modal-dialog'));
	}
</script>

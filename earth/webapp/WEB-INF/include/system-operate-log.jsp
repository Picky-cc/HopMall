<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- 
    param:
    objectUuid: 日志查询用，数据uuid
    title: 标题
 --%>
<div class="sys-log">
	<div class="lookup-params hide">
		<input class="real-value" type="hidden" name="objectUuid" value="${param.objectUuid}">
	</div>
	<h5 class="hd">${empty param.title ? '操作日志' : param.title}</h5>
	<div class="bd">
		<table class="logs" data-action="${ctx}/system-operate-log/query">
			<thead>
				<tr>
					<th>序号</th>
					<th>操作发生时间</th>
					<th>操作员登录名</th>
					<th>操作内容</th>
				</tr>
			</thead>
			<tbody>
				<script class="template" type="script/template">
				    {% _.each(list, function(log, index) { %}
				        <tr>
							<td>{%= index + 1 %}</td>
							<td>{%= log.occurTime %}</td>
							<td>{%= log.operateName %}</td>
							<td>{%= log.recordContent %}</td>
				        </tr>
				    {% }); %}
				</script>
			</tbody>
		</table>
	</div>
	<div class="ft">
		<jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
            <jsp:param value="page-control" name="type"/>
            <jsp:param value="true" name="advanced"/>
            <jsp:param value="5" name="pageRecordNum"/>
        </jsp:include>
	</div>
</div>


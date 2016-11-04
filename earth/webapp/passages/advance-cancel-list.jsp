<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh_CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>代付撤销单列表 - 五维金融金融管理平台</title>
</head>
<body>
	<%@ include file="/WEB-INF/include/header.jsp"%>
	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="lookup-params">
					<div class="inner clearfix">
							<span class="item">
								<input type="text" class="form-control real-value" name="" value="" placeholder="通道流水号">
							</span>
							<span class="item">
								<select name="" class="form-control real-value">
									<option value="-1">放款通道</option>
								</select>
							</span>
							<span class="item beginend-datepicker">
								<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
									<jsp:param value="group" name="type" />
									<jsp:param value="false" name="calendarbtn" />
									<jsp:param value="true" name="clearbtn" />
									<jsp:param value="startDateString" name="paramName1" />
									<jsp:param value="endDateString" name="paramName2" />
									<jsp:param value="" name="paramValue1"/>
				             	   	<jsp:param value="" name="paramValue2"/>
									<jsp:param value="创建日期起始" name="placeHolder1" />
									<jsp:param value="创建日期终止" name="placeHolder2" />
								</jsp:include>
							</span>
							<span class="item">
								<input type="text" class="form-control real-value" name="" value="" placeholder="退回账户">
							</span>
							<span class="item">
								<button id="lookup" class="btn btn-primary">
									查询
								</button>
							</span>
					</div>
				</div>	
				<div class="table-area">
					<table class="data-list" data-action="" data-autoload="true">
						<thead>
							<tr>
								<th>通道流水号</th>
								<th>通道名称</th>
								<th>发生时间</th>
								<th>退回账户</th>
								<th>交易类型</th>
								<th>金额</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><a href="#">273CS838HJS</a></td>
								<td>银联代付</td>
								<td>2016-07-13 16:13:30.0</td>
								<td>6228480333333333333</td>
								<td>冲账</td>
								<td>3000</td>
							</tr>
							<!-- <script type="script/template" id="">
								
							</script> -->
						</tbody>
					</table>
				</div>				
			</div>

			<div class="operations">
				<jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
		            <jsp:param value="page-control" name="type"/>
		            <jsp:param value="true" name="advanced"/>
	            </jsp:include>
			</div>	
		</div>
	</div>
	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
</body>
</html>>
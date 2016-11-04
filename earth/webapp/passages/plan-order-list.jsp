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
<title>计划订单列表 - 五维金融金融管理平台</title>
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
								<select name="" class="form-control real-value">
									<option value="-1">信托合作项目</option>
								</select>
							</span>
							<span class="item">
								<input type="text" class="form-control real-value" name="" value="" placeholder="订单编号">
							</span>
							<span class="item">
								<input type="text" class="form-control real-value" name="" value="" placeholder="贷款合同编号">
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
									<jsp:param value="受理日期起始" name="placeHolder1" />
									<jsp:param value="受理日期终止" name="placeHolder2" />
								</jsp:include>
							</span>
							<span class="item">
								<select name="" class="form-control real-value">
									<option value="-1">订单状态</option>
								</select>
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
								<th>信托项目名称</th>
								<th>贷款合同编号</th>
								<th>订单编号</th>
								<th>计划放款金额</th>
								<th>实际放款金额</th>
								<th>放款策略类型</th>
								<th>受理时间</th>
								<th>订单状态</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>项目名称ddd</td>
								<td><a href="#">jfdsf4d4f4df4df-d59f6s</a></td>
								<td>dfasdf</td>
								<td>3000</td>
								<td>3000</td>
								<td>优先级策略</td>
								<td>2016-07-19</td>
								<td>已创建</td>
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
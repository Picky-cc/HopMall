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
<title>放款单列表 - 五维金融金融管理平台</title>
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
								<input type="text" class="form-control real-value" name="" value="" placeholder="放款编号">
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
									<jsp:param value="计划放款日期起始" name="placeHolder1" />
									<jsp:param value="计划放款日期终止" name="placeHolder2" />
								</jsp:include>
							</span>
							<span class="item">
								<input type="text" class="form-control real-value" name="" value="" placeholder="付款方账户名">
							</span>
							<span class="item">
								<input type="text" class="form-control real-value" name="" value="" placeholder="收款方账户名">
							</span>
							<span class="item">
								<select name="" class="form-control real-value">
									<option value="-1">放款状态</option>
								</select>
							</span>
							<span class="item">
								<button id="lookup" class="btn btn-primary">
									查询
								</button>
							</span>
							<span class="item">
					            <a href="#" class="pull-right btn btn-success">
					                批量放款
					            </a>
							</span>
					</div>
				</div>	
				<div class="table-area">
					<table class="data-list" data-action="" data-autoload="true">
						<thead>
							<tr>
								<th>放款编号</th>
								<th>合同编号</th>
								<th>计划放款日期</th>
								<th>放款金额</th>
								<th>付款方账户名</th>
								<th>收款方账户名</th>
								<th>放款状态</th>
								<th>备注</th>
								<th width="60">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>FK000001</td>
								<td><a href="#">jfdsf4d4f4df4df-d59f6s</a></td>
								<td>2016-07-19</td>
								<td>3000</td>
								<td>韩梅梅</td>
								<td>李雷</td>
								<td>已创建</td>
								<td></td>
								<td><a href="#">详情</a></td>
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
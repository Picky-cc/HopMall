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
<title>线上代付单列表 - 五维金融金融管理平台</title>
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
								<input type="text" class="form-control real-value" name="" value="" placeholder="代付单号">
							</span>
							<span class="item">
								<input type="text" class="form-control real-value" name="" value="" placeholder="放款编号">
							</span>
							<span class="item">
								<input type="text" class="form-control real-value" name="" value="" placeholder="付款方账户名">
							</span>
							<span class="item">
								<input type="text" class="form-control real-value" name="" value="" placeholder="收款方账户名">
							</span>
							<span class="item beginend-datepicker">
								<select name="" class="form-control real-value">
									<option value="-1">放款通道</option>
								</select>
							</span>
							<span class="item">
								<select name="" class="form-control real-value">
									<option value="-1">代付状态</option>
								</select>
							</span>
							<span class="item">
								<select name="" class="form-control real-value">
									<option value="-1">冲账状态</option>
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
								<th>代付单号</th>
								<th>放款编号</th>
								<th>付款方账户名</th>
								<th>收款方账户名</th>
								<th>放款金额</th>
								<th>放款通道</th>
								<th>状态变更时间</th>
								<th>付款状态</th>
								<th冲账状态></th>
								<th>备注</th>
								<th width="60">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><a href="#">df673Gjhdgf</a></td>
								<td>FK00000002</td>
								<td>韩梅梅</td>
								<td>李雷</td>
								<td>3000</td>
								<td>银联代付</td>
								<td>2016-17-13 16:30:30.0</td>
								<td>已创建</td>
								<td>未发生</td>
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
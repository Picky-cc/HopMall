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
<title>通道配置详情 - 五维金融金融管理平台</title>

</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">

		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content content-passage">
			<div class="scroller">
				<div class="position-map">
		          <div class="pull-left">
		            当前位置：
		            <span class="item"><a href="${ctx}/paymentchannel" class="deep-gray-color">通道管理</a> &gt;</span>
		            <span class="item current">通道详情</span>
		          </div>
		        </div>
		        <div class="wrapper">
		        	<div class="sketch">
		        		<div class="hd">
		        			<span class="title">${paymentChannelName}</span>
		        			<span>（24小时内交易成功率<span class="color-warning">${tradingSuccessRateIn24Hours}</span>）</span>
		        			<span class="pull-right">通道开通时间：<fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd" /></span>
		        		</div>
		        		<div class="bd">
		        			<div class="cols">
		        				<div class="col">
		        					<p>
			        					<fmt:formatNumber type='number' pattern='#,#00.00#' value='${totalDebitAmount}' />
		        					</p>
		        					<p>累计收款交易额</p>
		        				</div>
		        				<div class="col">
		        					<p>
		        						<fmt:formatNumber type='number' pattern='#,#00.00#' value='0' />
		        					</p>
		        					<p>累计收款通道费用</p>
		        				</div>
		        				<div class="col">
		        					<p>
		        						<fmt:formatNumber type='number' pattern='#,#00.00#' value='${totalCreditAmount}' />
		        					</p>
		        					<p>累计付款交易额</p>
		        				</div>
		        				<div class="col">
		        					<p>
		        						<fmt:formatNumber type='number' pattern='#,#00.00#' value='0' />
		        					</p>
		        					<p>累计付款通道费用</p>
		        				</div>
		        				<div class="col">
		        					<p>
		        						<fmt:formatNumber type='number' pattern='#,#00.00#' value='0' />
		        					</p>
		        					<p>累计未结费用</p>
		        				</div>
		        			</div>
		        		</div>
		        	</div>
		        	<div class="graph">
		        		<div class="row span2">
			        		<div class="item">
			        			<div class="hd">
			        				<i class="icon icon-circle"></i>
			        				<span>交易额趋势&nbsp;&nbsp;&nbsp;</span>
			        				<select class="form-control filter-turnover">
			        					<option value="7d">最近7天</option>
			        					<option value="6m">最近6个月</option>
			        				</select>
				        		</div>
			        			<div class="bd">
			        				<div id="drawContainer1" style="width: 100%; height: 300px;"></div>
			        			</div>
			        		</div>
			        		<div class="item">
			        			<div class="hd">
			        				<i class="icon icon-circle"></i>
			        				<span>通道费用趋势&nbsp;&nbsp;&nbsp;</span>
			        				<select class="form-control filter-channel">
			        					<option value="7d">最近7天</option>
			        					<option value="6m">最近6个月</option>
			        				</select>
				        		</div>
			        			<div class="bd">
			        				<div id="drawContainer2" style="height: 300px;"></div>
			        			</div>
			        		</div>
		        		</div>
		        	</div>
					<div class="table-layout-detail">
						<div class="block">
							<h5 class="hd">交易记录</h5>
							<div class="lookup-params">
								<div class="inner">
									<input type="hidden" value="${paymentChannelUuid}" name="paymentChannelUuid" class="real-value">
									<span class="item beginend-datepicker">
						              <jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
						                <jsp:param value="group" name="type"/>
						                <jsp:param value="false" name="calendarbtn"/>
						                <jsp:param value="true" name="clearbtn"/>
						                <jsp:param value="交易时间起始" name="placeHolder1"/>
						                <jsp:param value="交易时间终止" name="placeHolder2"/>
						                <jsp:param value="startDateString" name="paramName1"/>
						                <jsp:param value="endDateString" name="paramName2"/>
						              </jsp:include>
						            </span>
									<span class="item">
										<select name="transactionType" class="form-control real-value">
											<option value="">选择交易类型</option>
											<option value="1">付款</option>
											<option value="0">收款</option>
										</select>
									</span>
									<span class="item">
										<input name="transferApplicationNo" type="text" name="" class="form-control real-value" placeholder="系统流水号" style="width: 205px;">
									</span>
									<span class="item">
										<button id="lookup" class="btn btn-primary">
											查询
										</button>
									</span>
								</div>
							</div>
							<div class="bd">
								<table class="data-list" data-autoload="true" data-action="${ctx}/paymentchannel/config/transactionDetail/search">
									<thead>
										<tr>
											<th>
												<a href="sort" data-paramname="isAsc"></a>交易时间
											</th>
											<th>交易金额</th>
											<th>通道费用</th>
											<th>交易类型</th>
											<th>系统流水号</th>
										</tr>
									</thead>
									<tbody>
										<script type="script/template" id="tableFieldTmpl">
											{% _.each(list, function(item, index){ %}
												<tr>
													<td>{%= item.creatTime %}</td>
													<td>{%= item.amount %}</td>
													<td>{%= item.fee %}</td>
													<td>{%= item.transactionType %}</td>
													<td>{%= item.transferApplicationNo %}</td>
												</tr>
											{% }) %}
										</script>
									</tbody>
								</table>
							</div>
							<div class="ft">
								<button class="btn btn-default export" data-action="${ctx}/paymentchannel/config/transactionDetail/export">导出</button>
								
								<jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
						            <jsp:param value="page-control" name="type"/>
						            <jsp:param value="true" name="advanced"/>
						  			<jsp:param value="${everyPage }" name="pageRecordNum"/>
						        </jsp:include>
							</div>
						</div>
					</div>
		        </div>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>

</body>
</html>

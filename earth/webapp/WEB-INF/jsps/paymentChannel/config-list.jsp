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
<title>通道配置列表 - 五维金融金融管理平台</title>

</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">

		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="lookup-params">
					<div class="inner">
						<span class="item">
							<select name="gateway" class="form-control real-value">
								<option value="">选择网关</option>
								<c:forEach var="item" items="${gatewayList}">
									<option value="${item.ordinal }">
										<fmt:message key="${item.key}"/>
									</option>
								</c:forEach>
							</select>
						</span> 
						<span class="item">
							<select name="debitStatus"  class="form-control real-value">
								<option value="">收款状态</option>
								<c:forEach var="item" items="${debitStatusList}">
									<option value="${item.ordinal }">
										<fmt:message key="${item.key}"/>
									</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<select name="creditStatus"  class="form-control real-value">
								<option value="">付款状态</option>
								<c:forEach var="item" items="${creditStatusList}">
									<option value="${item.ordinal }">
										<fmt:message key="${item.key}"/>
									</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<select name="businessType"  class="form-control real-value">
								<option value="">业务类型</option>
								<c:forEach var="item" items="${businessTypeList}">
									<option value="${item.ordinal }">
										<fmt:message key="${item.key}"/>
									</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<input type="text" name="keyWord" class="form-control real-value" placeholder="商户号，信托计划，通道等关键词..." style="width: 205px;">
						</span>
						<span class="item">
							<button id="lookup" class="btn btn-primary">
								查询
							</button>
						</span>
					</div>
				</div>

				<div class="table-area">
					<table class="data-list" data-action="${ctx}/paymentchannel/config/search" data-autoload="true">
						<thead>
							<tr>
								<th>通道名称</th>
								<th>创建日期</th>
								<th>网关名称</th>
								<th>业务类型</th>
								<th>收款状态</th>
								<th>付款状态</th>
								<th>商户号</th>
								<th>信托合同</th>
								<th style="width: 60px">操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
								{% _.each(list, function(item, index) { %}
									<tr data-paymentchanneluuid='{%= item.paymentChannelUuid %}'
										data-prelatedfinancialcontractuuid='{%= item.relatedFinancialContractUuid %}'
										>
										<td>{%= item.paymentChannelName %}
											<a href="${ctx}/paymentchannel/config/{%= item.paymentChannelUuid %} ">
												{% if(item.configureProgress=='WAITING') { %}
													<span class="badge badge-dander">待配置</span>
												{%  } %}
											</a>
										</td>
										<td>{%= new Date(item.createTime).format('yyyy-MM-dd') %}</td>
										<td>{%= item.paymentInstitutionName %}</td>
										<td>
											{% if(item.businessType == 'SELF') { %}
												<span class="">自有</span>
											{% }else if(item.businessType == 'ENTRUST'){ %} 
												<span class="">委托</span>
											{% } %}
										</td>
										<td>
											{% if(item.debitChannelWorkingStatus == 'ON') { %}
												<span class="color-success">已启用</span>
											{% }else if(item.debitChannelWorkingStatus == 'OFF'){ %} 
												<span class="color-danger">已关闭</span>
											{% }else if(item.debitChannelWorkingStatus == 'NOTLINK'){ %} 
												<span class="color-danger">未对接</span>
											{% } %}
										</td>
										<td>
											{% if(item.creditChannelWorkingStatus == 'ON') { %}
												<span class="color-success">已启用</span>
											{% }else if(item.creditChannelWorkingStatus == 'OFF'){ %} 
												<span class="color-danger">已关闭</span>
											{% }else if(item.creditChannelWorkingStatus == 'NOTLINK'){ %} 
												<span class="color-danger">未对接</span>
											{% } %}
										</td>
										<td>{%= item.outlierChannelName %}</td>
										<td>{%= item.relatedFinancialContractName %}</td>
										<td>
											{% if(item.configureProgress=='WAITING'){ %}
												<a href="${ctx}/paymentchannel/config/{%= item.paymentChannelUuid %}">配置</a>
											{% }else { %}
												<a href="javascript:void(0)" class = "detailDialog">详情</a>
											{% } %}
										</td>
									</tr>
								{% }); %}
							</script>
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
	<script type="template/script" id="configDetailTmpl">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
		        <h4 class="modal-title" id="dialoglabel">{%= data.paymentChannelName %}</h4>
		      </div>
		      <div class="modal-body">
		        <div class="sketch">
		        	<span class="item">
		        		对接网关：<span>{%= data.paymentInstitutionNameMsg %}</span>
		        	</span>
		        	<span class="item">
		        		商户号：<span>{%= data.outlierChannelName %}</span>
		        	</span>
		        	<span class="item">
		        		信托计划：<span>{%= data.relatedFinancialContractName %}</span>
		        	</span>
		        </div>
		        <div class="detail">
		        	<div class="block">
		        		<h5 class="hd">收款交易</h5>
		        		<div class="bd">
		        			<div class="col">
		        				<div class="item">
		        					<span class="key">状态：</span>
		        					<span class="val">
										{% if(data.debitChannelDetails) { %}
											{% if(data.debitChannelDetails.channelStatus == 'ON') { %}
												<span class="color-success">已启用</span>
											{% }else if(data.debitChannelDetails.channelStatus == 'OFF'){ %} 
												<span class="color-danger">已关闭</span>
											{% }else if(data.debitChannelDetails.channelStatus == 'NOTLINK'){ %} 
												<span class="color-danger">未对接</span>
											{% } %}
										{% } %}
		        					</span>
		        				</div>
		        				<div class="item">
		        					<span class="key">通道扣率：</span>
		        					<span class="val">
		        						{% if(data.debitChannelDetails){ %}
		        							{% if(data.debitChannelDetails.chargeRateMode == 'SINGLEFIXED') { %}
			        							{%= data.debitChannelDetails.chargePerTranscation ? data.debitChannelDetails.chargePerTranscation + '元\/笔' : '' %}
		        							{% } else if(data.debitChannelDetails.chargeRateMode == 'SINGLERATE') { %}
		        								{%= '['+(data.debitChannelDetails.lowerestChargeLimitPerTransaction || ' ') + ', ' + (data.debitChannelDetails.upperChargeLimitPerTransaction || ' ') + ', ' + (data.debitChannelDetails.chargeRatePerTranscation || ' ') + '%]' %}
		        							{% } %}
		        						{% } %}
		        					</span>
		        				</div>
		        				<div class="item">
		        					<span class="key">清算周期：</span>
		        					<span class="val">
		        						{% if(data.debitChannelDetails && data.debitChannelDetails.clearingInterval){ %}
		        							T{%= data.debitChannelDetails.clearingInterval %}日
										{% } %}
		        					</span>
		        				</div>
		        			</div>
		        			<div class="col">
		        				<div class="item">
		        					<span class="key">交易类型：</span>
		        					<span class="val">
		        						{% if (data.debitChannelDetails) {%}
		        							{% if(data.debitChannelDetails.chargeType == 'BATCH') { %}
												批量
											{% }else if(data.debitChannelDetails.chargeType == 'SINGLE'){ %} 
												单笔
											{% }else if(data.debitChannelDetails.chargeType == 'BOTH'){ %} 
												批量、单笔
											{% } %}
										{% } %}
		        					</span>
		        				</div>
		        				<div class="item">
		        					<span class="key">费用模式：</span>
		        					<span class="val">
		        						{% if (data.debitChannelDetails) {%}
		        							{% if(data.debitChannelDetails.chargeExcutionMode == 'FORWARD') { %}
												向前收费
											{% }else if(data.debitChannelDetails.chargeExcutionMode == 'BACKWARD'){ %}
												向后收费
											{% } %}
										{% } %}
		        					</span>
		        				</div>
		        				<div class="item">
		        					<span class="key">银行限额表：</span>
		        					<span class="val">
		        						{% if(data.debitChannelDetails && typeof(data.debitChannelDetails.bankLimitationFileName) !='undefined'){ %}
			        						<a style="margin-left: 10px" href="${ctx}/paymentchannel/file/download?fileKey={%=data.debitChannelDetails.bankLimitationFileKey%}">{%= data.debitChannelDetails.bankLimitationFileName %}</a>
		        						{%  }  %}
		        					</span>
		        				</div>
		        			</div>
		        		</div>
		        	</div>
		        	<div class="block">
		        		<h5 class="hd">付款交易</h5>
		        		<div class="bd">
		        			<div class="col">
		        				<div class="item">
		        					<span class="key">状态：</span>
		        					<span class="val">
		        						{% if(data.creditChannelDetails) { %}
		        							{% if(data.creditChannelDetails.channelStatus == 'ON') { %}
												<span class="color-success">已启用</span>
											{% }else if(data.creditChannelDetails.channelStatus == 'OFF') { %} 
												<span class="color-danger">已关闭</span>
											{% }else if(data.creditChannelDetails.channelStatus == 'NOTLINK') { %} 
												<span class="color-danger">未对接</span>
											{% } %}
										{% } %}
		        					</span>
		        				</div>
		        				<div class="item">
		        					<span class="key">通道扣率：</span>
		        					<span class="val">
		        						{% if(data.creditChannelDetails){ %}
		        							{% if(data.creditChannelDetails.chargeRateMode == 'SINGLEFIXED') { %}
			        							{%= data.creditChannelDetails.chargePerTranscation ? data.creditChannelDetails.chargePerTranscation + '元\/笔' : '' %}
		        							{% } else { %}
		        								{%= '['+(data.creditChannelDetails.lowerestChargeLimitPerTransaction || ' ') + ', ' + (data.creditChannelDetails.upperChargeLimitPerTransaction || ' ') + ', ' + (data.creditChannelDetails.chargeRatePerTranscation || ' ') + '%]' %}
		        							{% } %}
		        						{% } %}
		        					</span>
		        				</div>
		        				<div class="item">
		        					<span class="key">清算周期：</span>
		        					<span class="val">
		        						{% if(data.creditChannelDetails && data.creditChannelDetails.clearingInterval){%}
		        							T{%= data.creditChannelDetails.clearingInterval %}日
										{% } %}
		        					</span>
		        				</div>
		        			</div>
		        			<div class="col">
		        				<div class="item">
		        					<span class="key">交易类型：</span>
		        					<span class="val">
		        						{% if(data.creditChannelDetails) { %}
		        							{% if(data.creditChannelDetails.chargeType == 'BATCH') { %}
												批量
											{% }else if(data.creditChannelDetails.chargeType == 'SINGLE'){ %} 
												单笔
											{% }else if(data.creditChannelDetails.chargeType == 'BOTH'){ %} 
												批量、单笔
											{% } %}
										{% } %}
		        					</span>
		        				</div>
		        				<div class="item">
		        					<span class="key">费用模式：</span>
		        					<span class="val">
		        						{% if(data.creditChannelDetails) { %}
		        							{% if(data.creditChannelDetails && data.creditChannelDetails.chargeExcutionMode == 'FORWARD') { %}
												向前收费
											{% } else if(data.creditChannelDetails && data.creditChannelDetails.chargeExcutionMode == 'BACKWARD') { %}
												向后收费
											{% } %}
										{% } %}
		        					</span>
		        				</div>
		        				<div class="item">
		        					<span class="key">银行限额表：</span>
		        					<span class="val">
		        						{% if(data.creditChannelDetails && typeof(data.creditChannelDetails.bankLimitationFileName) !='undefined'){ %}
	        								<a style="margin-left: 10px"  href="${ctx}/paymentchannel/file/download?fileKey={%=data.creditChannelDetails.bankLimitationFileKey%}">{%= data.creditChannelDetails.bankLimitationFileName %}</a>
		        						{%	} %}
		        					</span>
		        				</div>
		        			</div>
		        		</div>
		        	</div>
		        </div>
		      </div>
		      <div class="modal-footer">
		        <a href="${ctx}/paymentchannel/config/transactiondetail/{%= data.paymentChannelUuid %}" class="btn btn-primary">明细</a>
		        <a href="${ctx}/paymentchannel/config/{%= data.paymentChannelUuid %}" class="btn btn-success">重新配置</a>
		      </div>
		    </div>
		  </div>
	</script>
	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
</body>
</html>

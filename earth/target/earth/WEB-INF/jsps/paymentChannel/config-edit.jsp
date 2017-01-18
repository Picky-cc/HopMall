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
<title>配置通道 - 五维金融金融管理平台</title>

</head>
<body>
	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="position-map">
					<div class="pull-left">
						 当前位置:
						 <span class="item"><a href="javascript: window.history.go(-1);" class="deep-gray-color">通道配置</a> &gt;</span>
			            <span class="item current">配置通道</span>
					</div>
					<div class="pull-right">
						<a href="javascript:window.history.go(-1);"
							class="back btn btn-default">&lt;&lt; 返回</a>
					</div>
				</div>
		
				<div class="form-wrapper">
					<input type="hidden" name="model" value='${paymentChannelConfigData}'>
					<div class="form" style="display: none;">
						<div class="fieldset-group fieldset-baseinfo">
							<form>
								<input type="hidden" class="real-value" name="relatedFinancialContractUuid" value="${relatedFinancialContractUuid}">
								<h5 class="hd">基础信息</h5>
								<div class="field-row">
									<label class="field-title require">关联信托合同</label>
									<div class="field-value">
										<span class="form-control no-border">${relatedFinancialContractName}</span>
									</div>
								</div>
								<div class="field-row">
									<label class="field-title require">专户行、号</label>
									<div class="field-value">
										<span class="form-control no-border">${captitalAccountNameAndNo}</span>
									</div>
								</div>
								<div class="field-row">
									<label class="field-title">商户号</label>
									<div class="field-value">
										<span class="form-control no-border">${outlierChannelName}</span>
										<input type="hidden" name="paymentInstitutionOrdinal" value="${paymentInstitutionOrdinal}">
										<input type="hidden" name="outlierChannelName" value="${outlierChannelName}">
									</div>
								</div>
								<div class="field-row">
									<label class="field-title">清算号</label>
									<div class="field-value">
										<span class="form-control no-border">${clearingNo}</span>
									</div>
								</div>
								<div class="field-row">
									<label class="field-title require">通道名称</label>
									<div class="field-value">
									<input type="text" class="form-control real-value large" name="paymentChannelName" value="${paymentChannelName}">
									</div>
								</div>
							</form>
						</div>
						<div class="fieldset-group fieldset-debit">
							<form class="form-debit">
								<h5 class="hd">收款交易</h5>
								<div class="field-row">
									<label class="field-title require">通道状态</label>
									<div class="field-value">
										<c:choose>
											<c:when test="${debitChannelWorkingStatus.ordinal() == 0}">
												<select class="form-control real-value" name="channelStatus" disabled>
													<option value="NOTLINK">未对接</option>
												</select>
											</c:when>
											<c:otherwise>
												<select class="form-control real-value" name="channelStatus">
													<c:forEach var="item" items="${channelWorkingStatus}">
														<option value="${item.alias}" ${debitChannelWorkingStatus.ordinal == item.ordinal ? 'selected' : '' }>
															<fmt:message key="${item.key }"></fmt:message>
														</option>
													</c:forEach>
												</select>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<c:if test="${debitChannelWorkingStatus.ordinal != 0}">
									<div class="field-row">
										<label class="field-title require">通道扣率</label>
										<div class="field-value">
											<select class="form-control real-value small" name="chargeRateMode">
												<c:forEach var="item" items="${chargeRateMode}">
													{% if('${item.alias}' == debitChannelConfigure.chargeRateMode) { %}
														<option value="${item.alias}" selected>
															<fmt:message key="${item.key }"></fmt:message>
														</option>
													{% }else { %}
														<option value="${item.alias}">
															<fmt:message key="${item.key }"></fmt:message>
														</option>
													{% } %}
												</c:forEach>
											</select>
											<span class="charge-rate-mode">
												{% if (debitChannelConfigure.chargeRateMode == 'SINGLERATA') { %}
													<span class="parcel-input rear">
														<input type="text" name="chargeRatePerTranscation" value="{%= debitChannelConfigure.chargeRatePerTranscation %}" class="form-control real-value small">
														<span class="suffix">%</span>
													</span>
													<input type="text" name="lowerestChargeLimitPerTransaction" value="{%= debitChannelConfigure.lowerestChargeLimitPerTransaction %}" class="form-control real-value small" placeholder="最低收取" >
													<span class="text-muted">至</span> 
													<input type="text" name="upperChargeLimitPerTransaction" value="{%= debitChannelConfigure.upperChargeLimitPerTransaction %}" class="form-control real-value small" placeholder="最高收取" >
													<span class="text-muted">元/笔</span> 
													<i class="icon icon-help" style="margin-left:10px" data-title="最低最高收取为空时不作保底封顶计算。"></i>
												{% } else { %}
													<input type="text" name="chargePerTranscation" value="{%= debitChannelConfigure.chargePerTranscation %}" class="form-control real-value small">
													<span class="text-muted">元/笔</span> 
												{% } %}
											</span>
										</div>
									</div>
									<div class="field-row">
										<label class="field-title ">通道单笔限额</label>
										<div class="field-value">
											<input type="text" name="trasncationLimitPerTransaction" value="{%= debitChannelConfigure.trasncationLimitPerTransaction %}" class="form-control real-value small">
											<span class="text-muted">万元/笔</span> 
										</div>
									</div>
									<div class="field-row">
										<label class="field-title require">费用模式</label>
										<div class="field-value">
											<select class="form-control real-value" name="chargeExcutionMode">
												<c:forEach var="item" items="${chargeExcutionMode}">
													{% if('${item.alias}' == debitChannelConfigure.chargeExcutionMode) { %}
														<option value="${item.alias}" selected>
															<fmt:message key="${item.key }"></fmt:message>
														</option>
													{% }else { %}
														<option value="${item.alias}">
															<fmt:message key="${item.key }"></fmt:message>
														</option>
													{% } %}
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="field-row">
										<label class="field-title require">清算周期</label>
										<div class="field-value">
											<span class="parcel-input">
												<span class="prefix">T+</span>
												<input type="text" name="clearingInterval" value="{%= debitChannelConfigure.clearingInterval %}" class="form-control real-value small">
												<span class="suffix">日</span>
											</span>
										</div>
									</div>
									<div class="field-row">
										<label class="field-title ">银行限额列表</label>
										<div class="field-value">
											<ul class="upload-files">
												{% if (debitChannelConfigure.bankLimitationFileKey) { %}
													<li class="item" data-filename="{%= debitChannelConfigure.bankLimitationFileName %}" data-filekey="{%= debitChannelConfigure.bankLimitationFileKey %}">
														<span class="filename">{%= debitChannelConfigure.bankLimitationFileName %}</span><a href="#" class="delete">删除</a>
													</li>
												{% } %}
											</ul>
											<span class="file-input btn " data-target=".upload-files">
												<a class="file-btn">选择文件</a>
												<input type="file" name="file"></input>
											</span>
											<a style="margin:0px 10px 0px 10px" href="${ctx}/paymentchannel/file/download?fileKey=1">下载限额表模版</a>
											<a style="margin:0px 10px 0px 10px" href="${ctx}/paymentchannel/file/download?fileKey=2">下载银行编号列表</a>
											<i class="icon icon-help" data-title="可选配置项，限额列表为空代表不限额。" ></i>
											<div style="margin: 10px 0;"><a href="#" class="quota-preview">限额预览</a></div>
										</div>
									</div>
								</c:if>
							</form>
						</div>
						<div class="fieldset-group fieldset-credit">
							<form class="form-credit">
								<h5 class="hd">付款交易</h5>
								<div class="field-row">
									<label class="field-title require">通道状态</label>
									<div class="field-value">
										<c:choose>
											<c:when test="${creditChannelWorkingStatus.ordinal() == 0}">
												<select class="form-control real-value" name="channelStatus" disabled>
													<option value="NOTLINK">未对接</option>
												</select>
											</c:when>
											<c:otherwise>
												<select class="form-control real-value" name="channelStatus">
													<c:forEach var="item" items="${channelWorkingStatus}">
														<option value="${item.alias}" ${creditChannelWorkingStatus == item ? 'selected' : '' }>
															<fmt:message key="${item.key }"></fmt:message>
														</option>
													</c:forEach>
												</select>
											</c:otherwise>
										</c:choose>
									</div>
								</div>
								<c:if test="${creditChannelWorkingStatus.ordinal != 0}">
									<div class="field-row">
										<label class="field-title require">通道扣率</label>
										<div class="field-value">
											<select class="form-control real-value small" name="chargeRateMode">
												<c:forEach var="item" items="${chargeRateMode}">
													{% if('${item.alias}' == creditChannelConfigure.chargeRateMode) { %}
														<option value="${item.alias}" selected>
															<fmt:message key="${item.key }"></fmt:message>
														</option>
													{% }else { %}
														<option value="${item.alias}">
															<fmt:message key="${item.key }"></fmt:message>
														</option>
													{% } %}
												</c:forEach>
											</select>
											<span class="charge-rate-mode">
												{% if (creditChannelConfigure.chargeRateMode == 'SINGLERATA') { %}
													<span class="parcel-input rear">
														<input type="text" name="chargeRatePerTranscation" value="{%= creditChannelConfigure.chargeRatePerTranscation %}" class="form-control real-value small">
														<span class="suffix">%</span>
													</span>
													<input type="text" name="lowerestChargeLimitPerTransaction" value="{%= creditChannelConfigure.lowerestChargeLimitPerTransaction %}" class="form-control real-value small" placeholder="最低收取" >
													<span class="text-muted">至</span> 
													<input type="text" name="upperChargeLimitPerTransaction" value="{%= creditChannelConfigure.upperChargeLimitPerTransaction %}" class="form-control real-value small" placeholder="最高收取" >
													<span class="text-muted">元/笔</span> 
													<i class="icon icon-help" style="margin-left:10px" data-title="最低最高收取为空时不作保底封顶计算。"></i>
												{% } else { %}
													<input type="text" name="chargePerTranscation" value="{%= creditChannelConfigure.chargePerTranscation %}" class="form-control real-value small">
													<span class="text-muted">元/笔</span> 
												{% } %}
											</span>
										</div>
									</div>
									<div class="field-row">
										<label class="field-title ">通道单笔限额</label>
										<div class="field-value">
											<input type="text" name="trasncationLimitPerTransaction" value="{%= creditChannelConfigure.trasncationLimitPerTransaction %}" class="form-control real-value small">
											<span class="text-muted">万元/笔</span> 
										</div>
									</div>
									<div class="field-row">
										<label class="field-title require">费用模式</label>
										<div class="field-value">
											<select class="form-control real-value" name="chargeExcutionMode">
												<c:forEach var="item" items="${chargeExcutionMode}">
													{% if('${item.alias}' == creditChannelConfigure.chargeExcutionMode) { %}
														<option value="${item.alias}" selected>
															<fmt:message key="${item.key }"></fmt:message>
														</option>
													{% }else { %}
														<option value="${item.alias}">
															<fmt:message key="${item.key }"></fmt:message>
														</option>
													{% } %}
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="field-row">
										<label class="field-title require">清算周期</label>
										<div class="field-value">
											<span class="parcel-input">
												<span class="prefix">T+</span>
												<input type="text" name="clearingInterval" value="{%= creditChannelConfigure.clearingInterval %}" class="form-control real-value small">
												<span class="suffix">日</span>
											</span>
										</div>
									</div>
									<div class="field-row">
										<label class="field-title ">银行限额列表</label>
										<div class="field-value">
											<ul class="upload-files">
												{% if (creditChannelConfigure.bankLimitationFileKey) { %}
													<li class="item" data-filename="{%= creditChannelConfigure.bankLimitationFileName %}" data-filekey="{%= creditChannelConfigure.bankLimitationFileKey %}">
														<span class="filename">{%= creditChannelConfigure.bankLimitationFileName %}</span><a href="#" class="delete">删除</a>
													</li>
												{% } %}
											</ul>
											<span class="file-input btn " data-target=".upload-files">
												<a class="file-btn">选择文件</a>
												<input type="file" name="file" data-params=""></input>
											</span>
											<a style="margin:0px 10px 0px 10px" href="${ctx}/paymentchannel/file/download?fileKey=1">下载限额表模版</a>
											<a style="margin:0px 10px 0px 10px" href="${ctx}/paymentchannel/file/download?fileKey=2">下载银行编号列表</a>
											<i class="icon icon-help" data-title="可选配置项，限额列表为空代表不限额。" ></i>
											<div style="margin: 10px 0;"><a href="#" class="quota-preview">限额预览</a></div>
										</div>
									</div>
								</c:if>
	                		</form>
						</div>
						<div class="fieldset-group">
							<div class="field-row">
	             			    <div class="field-title"></div>
	                  			<div class="field-value">
	                    			<button type="button" class="btn btn-primary submit" data-loading-text="<i class='glyphicon glyphicon-refresh anim-rotate-infinite'></i>">提交</button>
	                  			</div>
	                		</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade form-modal" id='quotaPreview'>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close-dialog" aria-label="关闭">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="dialoglabel">银行限额预览</h4>
                </div>
                <div class="modal-body" style="padding: 20px 30px;">
                	<div class="segment-control">
                	    <ul class="segment-menu segment-menu-primary">
                	        <li class="segment-menu-item z-active" data-index="0">
                	        	<a href="#">各银行限额</a>
                	        </li>
                	        <li class="segment-menu-item hide" data-index="1">
                	            <a href="#">版本记录</a>
                	        </li>
                	    </ul>
                	    <div class="pull-left hide">
            	        	<input type="text" class="form-control" placeholder="商户号等关键字..." style="display: inline-block; width: auto; height: 28px; vertical-align: middle; margin: 0 20px;">
							<button id="lookup" class="btn btn-primary" style="padding: 3px 12px;">查询</button>
                	    </div>
                	    <div class="segment-content table without-alternative-bg">
                	        <div class="segment-content-item" style="display: none;">
	            				<div class="block">
	            				    <div class="lookup-params hide">
	            				    </div>
	            				    <div class="bd">
	            				        <table class="data-list" 
	            				            data-action="./edit/bankLimitPreview" 
	            				            data-autoload="true">
	            				            <thead>
	            				                <tr>
	            				                    <th>银行名称</th>
	            				                    <th>单笔限额</th>
	            				                    <th>单日限额</th>
	            				                    <th>单月限额</th>
	            				                </tr>
	            				            </thead>
	            				            <tbody>
	            				            	<script type="script/template" class="template">
		            				            	{% _.each(list, function(item, index){  %}
			            				            	<tr>
			            				            	    <td>{%= item.bankName %}</td>
			            				            	    <td>{%= (item.transactionLimitPerTranscation).formatMoney(2, '') %}</td>
			            				            	    <td>{%= (item.transcationLimitPerDay).formatMoney(2, '') %}</td>
			            				            	    <td>{%= (item.transactionLimitPerMonth).formatMoney(2, '') %}</td>
			            				            	</tr>
		            				            	{% }) %}
	            				            	</script>
	            				            </tbody>
	            				        </table>
	            				    </div>
	            				    <div class="ft hide">
	            				        <jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
	            				            <jsp:param value="page-control" name="type"/>
	            				            <jsp:param value="true" name="advanced"/>
	            				            <jsp:param value="99999" name="pageRecordNum"/>
	            				        </jsp:include>
	            				    </div>
	            				</div>
                	        </div>
                	        <div class="segment-content-item" style="display: none;">
                	            <div class="block">
                	                <div class="lookup-params hide">
                	                </div>
                	                <div class="bd">
                	                    <table class="data-list" 
                	                        data-action="" 
                	                        data-autoload="false">
                	                        <thead>
                	                            <tr>
                	                                <th>文件名称</th>
                	                                <th>上传文件</th>
                	                                <th>操作文件</th>
                	                            </tr>
                	                        </thead>
                	                        <tbody>
                	                        </tbody>
                	                    </table>
                	                </div>
                	                <div class="ft">
                	                    <jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
                	                        <jsp:param value="page-control" name="type"/>
                	                        <jsp:param value="true" name="advanced"/>
                	                    </jsp:include>
                	                </div>
                	            </div>
                	        </div>
                	    </div>
                	</div>
                </div>
            </div>
        </div>
	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>

</body>
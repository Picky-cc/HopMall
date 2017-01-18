<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

	<fmt:setBundle basename="ApplicationMessage" />
	<fmt:setLocale value="zh_CN" />

	<%@ include file="/WEB-INF/include/meta.jsp"%>
	<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
	<title>第三方限额列表 - 五维金融金融管理平台</title>

	<script type="script/template" id='createQuotaFormTmpl'>
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="dialoglabel">上传限额表</h4>
				</div>
				<div class="modal-body form-wrapper" style="padding-left: 90px;">
					<form class="form adapt" enctype="multipart/form-data">
						<div class="field-row">
							<div class="field-value">
								<select class="form-control middle real-value" name='paymentInstitutionName'>
									<option value="">选择网关</option>
									<c:forEach var="item" items="${gatewayList}">
										<option value="${item.ordinal }"> <fmt:message key="${item.key}"/> </option>
									</c:forEach>
								</select>
	                        </div>
						</div>
						<div class="field-row">
							<div class="field-value">
								<select	name="outlierChannelName" class="form-control real-value middle ">
									<option value="">选择商户号</option>
									<c:forEach var="item" items="${outlierChannelNames}">
										<option value="${item}">${item}</option>
									</c:forEach>
								</select>
	                        </div>
						</div>
						<div class="field-row">
							<div class="field-value">
								<select	name="accountSide" class="form-control real-value middle">
									<option value="">选择收付类型</option>
									<c:forEach var="item" items="${accountSide }">
										<option value="${item.ordinal}">
											<c:if test="${item.ordinal ==0}">
												代付
											</c:if>
											<c:if test="${item.ordinal ==1}">
												代收
											</c:if>
										</option>
									</c:forEach>
								</select>
								<i class="icon icon-help" style="margin-left: 20px;" data-title="新的通道需配置后再上传限额表"></i>
	                        </div>
						</div>
						<div class="field-row">
							<div class="field-value">
		                        <input type="file" name="file" class= "form-control real-value">
	                        </div>
						</div>
						<a style="margin-right:20px" href="${ctx}/paymentchannel/file/download?fileKey=1">下载限额表模版</a>
						<a href="${ctx}/paymentchannel/file/download?fileKey=2">下载银行编号列表</a>
						<div class="field-row" style="margin-bottom: 15px;">
							<div class="field-value">
								<button type="button" type="button" class="btn btn-primary submit">提交</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</script>

</head>
<body>
	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="modal fade form-modal" id='quotaDetail'>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="dialoglabel">限额详情</h4>
                </div>
                <div class="modal-body" style="padding: 20px 30px;">
                	<div class="segment-control">
                	    <ul class="segment-menu segment-menu-primary">
                	        <li class="segment-menu-item z-active" data-index="0">
                	        	<a href="#">修改限额</a>
                	        </li>
                	        <li class="segment-menu-item" data-index="1">
                	            <a href="#">操作日志</a>
                	        </li>
                	    </ul>
                	    <div class="segment-content">
                	        <div class="segment-content-item form-wrapper" style="padding: 0; display: none;">
	            				<form class="form adapt">
	            					<div class="field-row" style="padding: 8px 0;">
	            						<div class="field-value">
	            							<label for="" class="field-title">网关</label>
	            							<span class="field-span paymentInstitutionName"></span>
	                                    </div>
	            					</div>
	            					<div class="field-row" style="padding: 8px 0;">
	            						<div class="field-value">
	            							<label for="" class="field-title">商户号</label>
	            							<span class="field-span outlierChannelName"></span>
	                                    </div>
	            					</div>
	            					<div class="field-row" style="padding: 8px 0;">
	            						<div class="field-value">
	            							<label for="" class="field-title">银行</label>
	            							<span class="field-span bankName"></span>
	                                    </div>
	            					</div>
	            					<div class="field-row" style="padding: 8px 0;">
	            						<div class="field-value">
	            							<label for="" class="field-title">收付类型</label>
	            							<span class="field-span accountSide"></span>
	                                    </div>
	            					</div>
									<div class="field-row">
										<label for="" class="field-title">单笔限额</label>
										<div class="field-value">
											<input type="text" class="form-control middle real-value transactionLimitPerTranscation" name="transactionLimitPerTranscation" required>
				                        </div>
									</div>
									<div class="field-row">
										<label for="" class="field-title">单日限额</label>
										<div class="field-value">
											<input type="text" class="form-control middle real-value transcationLimitPerDay" name="transcationLimitPerDay" required>
				                        </div>
									</div>
									<div class="field-row">
										<label for="" class="field-title">单月限额</label>
										<div class="field-value">
											<input type="text" class="form-control middle real-value transactionLimitPerMonth" name="transactionLimitPerMonth" required>
				                        </div>
									</div>
	            					<div class="field-row" style="margin-top: 15px;">
	            						<label for="" class="field-title"></label>
	            						<div class="field-value">
		            						<button type="button" style="width: 80px; margin-right: 20px;" data-dismiss="modal" type="button" class="btn btn-default">取消</button>
	            							<button type="button" style="width: 80px;" type="button" class="btn btn-success modify-quota">提交</button>
	            						</div>
	            					</div>
	            				</form>
                	        </div>
                	        <div class="segment-content-item" style="display: none;">
                	            <jsp:include page="/WEB-INF/include/system-operate-log.jsp">
	                	            <jsp:param value=" " name="title"/>
                	                <jsp:param value="${assetSetUuid}" name="objectUuid"/>
                	            </jsp:include>
                	        </div>
                	    </div>
                	</div>
                </div>
            </div>
        </div>
	</div>

	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
			<div class="scroller">
				<div class="lookup-params">
					<div class="inner clearfix">
						<span class="item">
							<select	name="gateway" class="form-control real-value middle">
								<option value="-1">选择网关</option>
								<c:forEach var="item" items="${gatewayList}">
									<option value="${item.ordinal}"><fmt:message key="${item.key}"/></option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<select	name="outlierChannelName" class="form-control real-value middle ">
								<option value="">选择商户号</option>
								<c:forEach var="item" items="${outlierChannelNames}">
									<option value="${item}">${item}</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<select	name="accountSide" class="form-control real-value middle">
								<option value="-1">选择收付类型</option>
								<c:forEach var="item" items="${accountSide }">
									<option value="${item.ordinal}">
										<c:if test="${item.ordinal ==0}">
											代付
										</c:if>
										<c:if test="${item.ordinal ==1}">
											代收
										</c:if>
									</option>
								</c:forEach>
							</select>
						</span>
						<span class="item">
							<input type="text" class="form-control large real-value " name="keyWord" placeholder="银行等关键字">
						</span> 
						<span class="item">
							<button id="lookup" class="btn btn-primary">查询</button>
						</span>
						<span class="item">
							<button id="uploadQuotaForm" class="btn btn-primary">上传限额表</button>
						</span>
					</div>
				</div>
				<div class="table-area">
					<table class="data-list"
						data-action="${ctx}/paymentchannel/limitSheet/search"
						data-autoload="true">
						<thead>
							<tr>
								<th>网关</th>
								<th>商户号</th>
								<th>收付类型</th>
								<th>银行名称</th>
								<th>单笔限额</th>
								<th>单日限额</th>
								<th>单月限额</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<script type="script/template" id="tableFieldTmpl">
								{% _.each(list,function(item,index){ %}
									<tr 
										data-paymentinstitutionname="{%= item.paymentInstitutionName%}"
										data-paymentinstitutionordinal="{%= item.paymentInstitutionOrdinal%}"
										data-outlierchannelname="{%= item.outlierChannelName%}"
										data-accountsideordinal="{%= item.accountSideOrdinal%}"
										data-accountside="{%= item.accountSide%}"
										data-bankcode="{%= item.bankCode%}"
										data-bankname="{%= item.bankName%}"
										data-banktransactionlimitsheetuuid="{%= item.bankTransactionLimitSheetUuid%}"
										data-transactionLimitpertranscation="{%= item.transactionLimitPerTranscation%}"
										data-transcationLimitperday="{%= item.transcationLimitPerDay%}"
										data-transactionLimitpermonth="{%= item.transactionLimitPerMonth%}"
										>
										<td>{%= item.paymentInstitutionName%}</td>
										<td>{%= item.outlierChannelName%}</td>
										<td>{%= item.accountSide%}</td>
										<td>{%= item.bankName%}</td>
										<td>
											{% if(_.isUndefined(item.transactionLimitPerTranscation)){ %}
												--
											{% } else { %}
												{%= (+item.transactionLimitPerTranscation).formatMoney(2,'') %}
											{% } %}
										</td>
										<td>
											{% if(_.isUndefined(item.transcationLimitPerDay)){ %}
												--
											{% } else { %}
												{%= (+item.transcationLimitPerDay).formatMoney(2,'') %}
											{% } %}
										</td>
										<td>
											{% if(_.isUndefined(item.transactionLimitPerMonth)){ %}
												--
											{% } else { %}
												{%= (+item.transactionLimitPerMonth).formatMoney(2,'') %}
											{% } %}
										</td>
										<td 
										 >
										<a href="javascript:void(0)" class="modify-quota">编辑</a></td>
									</tr>
								{% }) %}
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

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	<script src="${ctx.resource}/js/jquery.form.min.js"></script>


</body>
</html>
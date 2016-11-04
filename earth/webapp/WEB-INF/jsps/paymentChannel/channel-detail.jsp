<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

	<fmt:setBundle basename="ApplicationMessage" />
	<fmt:setLocale value="zh_CN" />

	<%@ include file="/WEB-INF/include/meta.jsp"%>
	<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
	<title>信托合同通道详情 - 五维金融金融管理平台</title>

	<style>
		.field-row .table {
			margin-top: 20px;
		}
		.modal-body .lookup-params {
	        background: transparent;
	        border: none;
	        padding: 15px 0;
	    }
	</style>

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
			        	<span class="item current">信托合同通道详情</span>
					</div>
					<div class="pull-right">
						<button type="button" onclick="window.history.go(-1);" class="btn btn-default">&lt;&lt; 返回</button>
					</div>
				</div>
				<div class="form-wrapper">
					<div class="form">
						<div class="fieldset-group fieldset-baseinfo">
							<form>
								<h5 class="hd">基础信息</h5>
								<div class="field-row">
									<label class="field-title require">信托名称</label>
									<div class="field-value">
										<span class="field-span">${contractName}</span>
									</div>
								</div>
								<div class="field-row">
									<label class="field-title require">信托代码</label>
									<div class="field-value">
										<span class="field-span">${contractNo}</span>
									</div>
								</div>
							</form>
						</div>
						<div class="fieldset-group fieldset-mine" data-business-type="0">
							<form>
								<h5 class="hd">自有通道服务</h5>
								<div class="field-row">
									<label class="field-title require">清算账户行(号)</label>
									<div class="field-value">
										<span class="field-span">${bankNameUnionAccountNo}</span>
									</div>
								</div>
								<div class="field-row" data-account-side="1">
									<label class="field-title require">收款通道策略</label>
									<div class="field-value">
                                        <jsp:include page="/WEB-INF/jsps/paymentChannel/include/channel-list.jsp">
                                            <jsp:param value="debitPaymentChannelMode" name="paymentChannelMode"/>
                                            <jsp:param value="debitPaymentChannelModeData" name="paymentChannelData"/>
                                        </jsp:include>
										<button type="button" class="btn btn-default set-channel-strategy">设置策略</button>
									</div>
								</div>
								<div class="field-row" data-account-side="0">
									<label class="field-title require">付款通道策略</label>
									<div class="field-value">
                                        <jsp:include page="/WEB-INF/jsps/paymentChannel/include/channel-list.jsp">
                                            <jsp:param value="creditPaymentChannelMode" name="paymentChannelMode"/>
                                            <jsp:param value="creditPaymentChannelData" name="paymentChannelData"/>
                                        </jsp:include>
										<button type="button" class="btn btn-default set-channel-strategy">设置策略</button>
									</div>
								</div>
							</form>
						</div>
						<div class="fieldset-group fieldset-agent" data-business-type="1">
							<form>
								<h5 class="hd">委托通道服务</h5>
								<div class="field-row">
									<label class="field-title require">清算账户行(号)</label>
									<div class="field-value">
										<span class="field-span">${bankNameUnionAccountNo}</span>
									</div>
								</div>
								<div class="field-row" data-account-side="1">
									<label class="field-title require">收款通道策略</label>
									<div class="field-value">
                                        <jsp:include page="/WEB-INF/jsps/paymentChannel/include/channel-list.jsp">
                                            <jsp:param value="acDebitPaymentChannelMode" name="paymentChannelMode"/>
                                            <jsp:param value="acDebitPaymentChannelModeData" name="paymentChannelData"/>
                                        </jsp:include>
										<button type="button" class="btn btn-default set-channel-strategy">设置策略</button>
									</div>
								</div>
								<div class="field-row" data-account-side="0">
									<label class="field-title require">付款通道策略</label>
									<div class="field-value">
                                        <jsp:include page="/WEB-INF/jsps/paymentChannel/include/channel-list.jsp">
                                            <jsp:param value="acCreditPaymentChannelMode" name="paymentChannelMode"/>
                                            <jsp:param value="acCreditPaymentChannelModeData" name="paymentChannelData"/>
                                        </jsp:include>
										<button type="button" class="btn btn-default set-channel-strategy">设置策略</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade form-modal" id='setChannelStrategy'>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="dialoglabel">设置策略</h4>
                </div>
                <div class="modal-body">
                	<div class="step-operation single">
                		<div class="step-tip">
                			<div data-index="1" class="step-tip-item z-active">
                				<div class="ordinal">1</div>
                				<div class="text">选择通道策略</div>
                			</div>
                			<div data-index="2" class="step-tip-item">
                				<div class="ordinal">2</div>
                				<div class="text">配置通道</div>
                			</div>
                		</div>
                		<div class="step-content text-align-left" style="min-height: 115px">
                			<div class="step-content-item" style="display: none;">
                                <jsp:include page="/WEB-INF/jsps/paymentChannel/include/step-content-item-1.jsp"/>
                			</div>
                			<div class="step-content-item" style="display: none;">
	                			<jsp:include page="/WEB-INF/jsps/paymentChannel/include/step-content-item-2.jsp">
                                    <jsp:param value="single" name="paymentChannelMode"/>
                                </jsp:include>
                			</div>
                		</div>
                		<div class="step-footer">
                			<button type="button" class="btn btn-default btn-prev prev">取消</button>
            				<button type="button" class="btn btn-primary btn-next next">下一步</button>
                		</div>
                	</div>
                    <div class="step-operation issuer" style="display: none;">
                        <div class="step-tip">
                            <div data-index="1" class="step-tip-item z-active">
                                <div class="ordinal">1</div>
                                <div class="text">选择通道策略</div>
                            </div>
                            <div data-index="2" class="step-tip-item">
                                <div class="ordinal">2</div>
                                <div class="text">配置通道</div>
                            </div>
                            <div data-index="3" class="step-tip-item">
                                <div class="ordinal">3</div>
                                <div class="text">配置发卡行首选通道</div>
                            </div>
                            <div data-index="4" class="step-tip-item">
                                <div class="ordinal">4</div>
                                <div class="text">优先级预览</div>
                            </div>
                        </div>
                        <div class="step-content text-align-left" style="min-height: 115px">
                            <div class="step-content-item" style="display: none;">
                                <jsp:include page="/WEB-INF/jsps/paymentChannel/include/step-content-item-1.jsp"/>
                            </div>
                            <div class="step-content-item" style="display: none;">
                                <jsp:include page="/WEB-INF/jsps/paymentChannel/include/step-content-item-2.jsp">
                                    <jsp:param value="issuer" name="paymentChannelMode"/>
                                </jsp:include>
                            </div>
                            <div class="step-content-item step-content-item-3" style="display: none;">
                                <jsp:include page="/WEB-INF/jsps/paymentChannel/include/step-content-item-3.jsp"/>
                            </div>
                            <div class="step-content-item" style="display: none;">
                                <jsp:include page="/WEB-INF/jsps/paymentChannel/include/step-content-item-4.jsp"/>
                            </div>
                        </div>
                        <div class="step-footer">
                            <button type="button" class="btn btn-default prev">取消</button>
                            <button type="button" class="btn btn-primary next">下一步</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
	</div>

    <div class="modal fade form-modal" id="priorityPreview">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="dialoglabel">各银行通道顺序预览</h4>
                </div>
                <div class="modal-body">
                    <div class="text-align-left">
                        <div class="table without-alternative-bg">
                            <div class="block">
                                <div class="lookup-params hide" style="padding-top: 0;">
                                    <span class="item">
                                        <input type="text" class="form-control real-value" style="width: 180px;" placeholder="搜索银行">
                                    </span>
                                    <span class="item">
                                        <button id="lookup" class="btn btn-primary">查询</button>
                                    </span>
                                </div>
                                <div class="bd">
                                    <table class="data-list" data-action="${ctx}/paymentchannel/switch/paymentChannelOrder">
                                        <script type="script/template" class="template">
                                            <thead>
                                                <tr>
                                                    <th>银行名称</th> 
                                                    {% for(var i = 0; i < paymentChannelSize; i++) { %} 
                                                        <th>优先级第{%= i + 1 %}位</th>
                                                    {% } %} 
                                                </tr>
                                            </thead>
                                            <tbody>
                                                {% _.each(list, function(bank, index) { %}
                                                    <tr>
                                                        <td>{%= bank.bankName %}</td>
                                                        {% _.each(bank.paymentChannelList, function(paymentChannel) { %}
                                                            <td>{%= paymentChannel.paymentChannelName %}</td>
                                                        {% }) %}
                                                    </tr>
                                                {% }) %}
                                            </tbody>
                                        </script>
                                    </table>
                                </div>
                                <div class="ft hide">
                                    <jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
                                        <jsp:param value="page-control" name="type"/>
                                        <jsp:param value="true" name="advanced"/>
                                        <jsp:param value="9999" name="pageRecordNum"/>
                                    </jsp:include>
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
</html>

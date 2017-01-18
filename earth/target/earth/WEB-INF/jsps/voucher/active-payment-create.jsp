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
	<title>主动还款凭证新增 - 五维金融金融管理平台</title>
	<style type="text/css">
		 .field-row.field-row-selectpicker .field-value .btn{
		 	width: 362px;
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
						<span class="item"><a href="javascript: window.history.go(-1);" class="deep-gray-color">凭证管理</a> &gt;</span>
						<span class="item current">主动还款凭证新增</span>
					</div>
					<div class="pull-right">
						<a href="javascript:window.history.go(-1);"
						class="back btn btn-default">&lt;&lt; 返回</a>
					</div>
				</div>
				<div class="form-wrapper">
					<div class="form">
						<form>
							<div class="fieldset-group">
								<h5 class="hd">贷款信息</h5>
								<div class="field-row">
									<label class="field-title require">贷款合同编号</label>
									<div class="field-value">
										<input type="text" class="form-control real-value large"
										name="contractNo" >
									</div>
								</div>	
								<div class="field-row">
									<label class="field-title require">资产编号</label>
									<div class="field-value">
										<input type="text" class="form-control real-value large address" name="address" >
									</div>
								</div>	
								<div class="field-row field-row-selectpicker">
									<label class="field-title require">还款编号</label>
									<div class="field-value ">
										<select class="form-control real-value repaymentPlanNo selectpicker" multiple data-actions-box="true" data-title="请选择还款编号" name="repaymentPlanNo" required>
										</select>
									</div>
								</div>								
							</div>
							<div class="fieldset-group">
								<h5 class="hd">客户信息</h5>
								<div class="field-row">
									<label class="field-title require">客户名称</label>
									<div class="field-value">
										<input type="text" class="form-control real-value large payerName" name="payerName">
									</div>
								</div>
								<div class="field-row">
									<label class="field-title require">账户开户行</label>
									<div class="field-value">
										<input type="text" class="form-control real-value large bank" >
									</div>
								</div>
								<div class="field-row">
									<label class="field-title require">开户行所在地</label>
									<div class="field-value">
										<input type="text" class="form-control real-value large bankAddress">
									</div>
								</div>
								<div class="field-row">
									<label class="field-title require">绑定账号</label>
									<div class="field-value">
										<input type="text" class="form-control real-value large payAcNo"  >
									</div>
								</div>
							</div>
							<div class="fieldset-group">
								<h5 class="hd">凭证信息</h5>
								<div class="field-row">
									<label class="field-title require">银行流水号</label>
									<div class="field-value">
										<input type="text" name="bankTransactionNo"  class="form-control real-value large">
									</div>
								</div>
								<div class="field-row">
									<label class="field-title ">凭证来源</label>
									<div class="field-value">
										<span class="form-control no-border">银行流水</span> 
									</div>
								</div>
								<div class="field-row">
									<label class="field-title require">凭证类型</label>
									<div class="field-value">										
										<select class="form-control real-value " name="voucherType" required>
											<option value="">
												请选择凭证类型
											</option>
											<c:forEach var="item" items="${voucherTypes}">
												<option value="${item.ordinal()}"><fmt:message key="${item.key}"/></option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="field-row">
									<label class="field-title ">专户账号</label>
									<div class="field-value">
										<input type="text" name="receivableAccountNo"  class="form-control real-value large">
									</div>
								</div>
								<div class="field-row">
									<label class="field-title require">往来机构名称</label>
									<div class="field-value">
										<input type="text" name="paymentBank"  class="form-control real-value large paymentBank">
									</div>
								</div>
								<div class="field-row">
									<label class="field-title require">机构账户号</label>
									<div class="field-value">
										<input type="text" name="paymentAccountNo"  class="form-control real-value large paymentAccountNo">
									</div>
								</div>
								<div class="field-row">
									<label class="field-title require">还款方姓名</label>
									<div class="field-value">
										<input type="text" name="paymentName"  class="form-control real-value large paymentName">
									</div>
								</div>
								<div class="field-row">
									<label class="field-title require">凭证金额</label>
									<div class="field-value">
										<input type="text" name="voucherAmount"  class="form-control real-value large">
									</div>
								</div>
								<div class="field-row">
									<label class="field-title">发生时间</label>
									<div class="field-value">
										<span class="item">
											<jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
												<jsp:param value="true" name="pickTime"/>
												<jsp:param value="startDateString" name="paramName1" />
												<jsp:param value="请输入发生时间" name="placeHolder1" />
											</jsp:include>
										</span>
									</div>
								</div>
								<div class="field-row">
									<label class="field-title ">原始图片凭证新增</label>
									<div class="field-value">
										<ul class="upload-files attachment-files">
										</ul>
										<span class="file-input btn " data-target=".upload-files">
											<a class="file-btn">选择文件</a>
											<input type="file" name="file" data-params=""></input>
										</span>
									</div>
								</div>
								<div class="field-row">
									<label class="field-title ">备注</label>
									<div class="field-value">
										<textarea class="form-control real-value" style="width:600px" rows="6" name="comment"></textarea>
									</div>
								</div>
							</div>	
							<div class="fieldset-group">
								<div class="field-row">
									<label class="field-title "></label>
		                  			<div class="field-value">
		                    			<button type="button" class="btn btn-primary submit" data-loading-text="<i class='glyphicon glyphicon-refresh anim-rotate-infinite'></i>">提交</button>
		                    			<button type="button" class="btn btn-primary cancel">取消</button>
		                  			</div>
		                		</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
</body>
</html>
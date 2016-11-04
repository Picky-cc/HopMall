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
<link href="${ctx.resource}/css/select2.css" rel="stylesheet">
<link href="${ctx.resource}/css/select2-bootstrap.css" rel="stylesheet">
<title>修改密码 - 五维金融金融管理平台</title>
</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>
	<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
	       	<div class="form-wrapper bill-form-wrapper">
				<form action="" class="form">
					<fieldset class="fieldset-group">
			        	<h5 class="hd">修改密码</h5>
						<div class="bd">
							<div class="field-row">
								<label for="username" class="field-title">账户</label>
								<div class="field-value">
									<label for="username" class="field-title">${currentPrincipal.username}</label>
								</div>
							</div>
							<div class="field-row">
								<label for="oldpass" class="field-title">当前登陆密码</label>
								<div class="field-value">
									<input type="password" class="form-control"
									id="oldpass" placeholder="请输入旧密码">
								</div>
							</div>
							<div class="field-row">
								<label for="newpass" class="field-title">新密码</label>
								<div class="field-value">
									<input type="password" class="form-control"
									id="newpass" placeholder="请输入新密码">
								</div>
							</div>
							<div class="field-row">
								<label for="newpassAgain" class="field-title">确认新密码</label>
								<div class="field-value">
									<input type="password" class="form-control"
									id="newpassAgain" placeholder="再次输入新密码">
								</div>
							</div>
							<div class="field-row">
								<label for="newpassAgain" class="field-title"></label>
								<div class="field-value">
									<button class="btn btn-primary submit" id="submit">确认修改</button>
								</div>
							</div>
						</div>
					</fieldset> 
				</form>
	       </div>
			<!-- <%@ include file="/WEB-INF/include/footer.jsp"%> -->
		</div>
	</div>
		<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
		<script src="${ctx.resource}/js/bootstrap-switch.min.js"></script>
		<script src="${ctx.resource}/js/bootstrap-modal-trigger.js"></script>
		<script src="${ctx.resource}/js/bootstrap.validate.js"></script>
		<script src="${ctx.resource}/js/bootstrap.validate.en.js"></script>
		<script src="${ctx.resource}/js/bootstrap-alert-fade-trigger.js"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				var error = false;


				$("#oldpass").blur(function() {

					var oldpass = $("#oldpass").val();
					if (oldpass == '') {
						showError('oldpass', '密码不能为空');
						error = true;
					} else {
						$("#oldpass").css({
							"border-color" : "green"
						});
						$("#oldpassTip").css({
							"display" : "none"
						});
					}

				});

				$("#newpass").blur(function() {
					var newpass = $("#newpass").val();
					if (newpass == '') {
						showError('newpass', '新密码不能为空');
						error = true;
					} else {
						$("#newpass").css({
							"border-color" : "green"
						});
						$("#newpassTip").css({
							"display" : "none"
						});
					}
				});

				$("#newpassAgain").blur(function() {
					var newpass = $("#newpass").val();
					if (newpass == '') {
						showError('newpass', '新密码不能为空');
						error = true;
						return;
					}

					var newpassAgain = $("#newpassAgain").val();
					if (newpassAgain != newpass) {
						showError('newpassAgain', '与输入的新密码不一致');
						error = true;
					} else {
						$("#newpassAgain").css({
							"border-color" : "green"
						});
						$("#newpassAgainTip").css({
							"display" : "none"
						});
					}
				});

				
			$("#submit").click(function(event) {
					$("#username").blur();
					$("#oldpass").blur();
					$("#newpass").blur();
					$("#newpassAgain").blur();

					if (!error) {
						var username = $("#username").val();
						var newpass = $("#newpass").val();
						var oldpass = $("#oldpass").val();
						$.post('${ctx}/update-password', {
							userName : username,
							newPassword : newpass,
							oldPassword : oldpass
						}, function(data) {
							alert(data);
							window.location.reload(); 
						});
					}
					event.preventDefault();
					return false;
				});
			});

			function showError(formSpan, errorText) {
				$("#" + formSpan).css({
					"border-color" : "red"
				});
				$("#" + formSpan + "Tip").empty();
				$("#" + formSpan + "Tip").append(errorText);
				;
				$("#" + formSpan + "Tip").css({
					"display" : "inline"
				});
			}
		</script>
</body>
</html>

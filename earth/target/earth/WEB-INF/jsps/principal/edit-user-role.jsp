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
<title>编辑用户 - 五维金融金融管理平台</title>
</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>
	<div class="web-g-main">
		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content">
	    	<div class="position-map">
				<div class="pull-left">
		            当前位置:
		            <span class="item current">编辑用户</span>
				</div>
			</div>
			<div class="form-wrapper">
	          <form id="editUserForm" method="post" class="form">
	          	<input type="hidden" name="principalId" value="${principal.id}" >
	            <fieldset class="fieldset-group" style="padding-bottom: 0px;">
	              <h5 class="hd">用户信息</h5>
	              <div class="bd">
	                <div class="field-row">
	                  <label class="field-title">用户名</label>
	                  <div style="font-weight: bold;margin: 8px 2px;">
                  		${principal.name}
	                  </div>
	                </div>
	                <div class="field-row">
	                	<label for="" class="field-title">用户角色</label>
		              	<div class="field-value">
	                    	<select class="form-control" id="role" name="role">
		                    	<c:forEach items="${resources.keySet()}" var="item">
			                    	<option ${principal.authority eq item ? 'selected' : ''} value="${item}">${item}</option>
		                    	</c:forEach>
		                    </select>
	                  	</div>
                  	</div>
	              </div>
	            </fieldset>
	            <fieldset class="fieldset-group" style="padding-bottom: 0px;">
	              <h5 class="hd">基础信息</h5>
	              <div class="bd">
	              	<div class="field-row">
	                  <label for="" class="field-title require">真实名字</label>
	                  <div class="field-value">
	                    <input class="form-control" id="realname" name="realname" value="${principal.tUser.name}" type="text">
	                  </div>
	                </div>
	                <div class="field-row">
	                  <label for="" class="field-title">联系邮箱</label>
	                  <div class="field-value">
	                    <input class="form-control middle" id="email" name="email" value="${principal.tUser.email}" type="text">
	                  </div>
	                </div>
	                <div class="field-row">
	                  <label for="" class="field-title">联系电话</label>
	                  <div class="field-value">
	                    <input class="form-control middle" id="phone" name="phone" value="${principal.tUser.phone}" type="text">
	                  </div>
	                </div>
	              </div>
	            </fieldset>
	            <fieldset class="fieldset-group">
	              <h5 class="hd">其他信息</h5>
	              <div class="bd">
	              	<div class="field-row">
	                	<label for="" class="field-title">所属公司</label>
		              	<div class="field-value">
	                    	<select class="form-control" name="companyId">
		                    	<option value="0">请选择</option>
		                    	<c:forEach items="${companyList}" var="item">
			                    	<option ${principal.tUser.company.id eq item.id ? 'selected' : ''} value="${item.id}">${item.fullName}</option>
		                    	</c:forEach>
		                    </select>
	                  	</div>
                  	</div>
	              	<div class="field-row">
	                  <label for="" class="field-title">所属部门</label>
	                  <div class="field-value">
	                    <input class="form-control middle" id="deptName" name="deptName" value="${principal.tUser.deptName}" type="text">
	                  </div>
	                </div>
	                <div class="field-row">
	                  <label for="" class="field-title">所属职位</label>
	                  <div class="field-value">
	                    <input class="form-control middle" id="positionName" name="positionName" value="${principal.tUser.positionName}" type="text">
	                  </div>
	                </div>
	                <div class="field-row">
	                  <div class="field-title">备注</div>
	                  <div class="field-value">
	                    <textarea id="remark" name="remark" class="multiline-input form-control real-value" cols="30" rows="10">${principal.tUser.remark}</textarea>
	                  </div>
	                </div>
	                <div class="field-row">
	                  <div class="field-title"></div>
	                  <div class="field-value">
	                    <button type="button" id="submit" class="btn btn-primary submit">提交</button>
	                  </div>
	                </div>
	              </div>
	            </fieldset>
	          </form>
	        </div>
		</div>
	</div>
	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	<script src="${ctx.resource}/js/bootstrap.validate.js"></script>
	<script src="${ctx.resource}/js/bootstrap.validate.trigger.js"></script>
	
	<script type="text/javascript">
		$(function() {
			$("#submit").click(function() {
				var $btn = $(this);
				$btn.attr("disabled",true);
				var realname = $.trim($("#realname").val());
				if (realname == ""){
					alert("请输入真实名字！");
					$btn.removeAttr("disabled");
				}else{
					var param = $("#editUserForm").serialize();
					$.post('${ctx}/edit-user-role', param, function(data) {
						var jsonData = $.parseJSON(data);
						alert(jsonData.message);
						if(jsonData.code == '0') {
							location.reload(true);
						}
						$btn.removeAttr("disabled");
					});
				}
			});
		});
	</script>
</body>
</html>
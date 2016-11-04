<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh-CN" />

<div class="modal-dialog demo2do-modal-dialog">
	<div class="modal-content">
		<div class="modal-header clearfix">
			<button type="button" class="close close-dialog" data-dismiss="modal">&times;</button>
			<h4 class="modal-title" id="dialoglabel">编辑还款单备注</h4>
		</div>
		<div class="modal-body" style="padding: 15px;">
			<div id="funArea">
				<p>
					备注 <input name="comment" type="text" maxLength="50"
						value="${assetSet.comment }" />
				</p>
				<br> <input name="assetSetId" type="hidden"
					value="${assetSet.id }" />
				<button type="button" id="doUpdateBtn" name="doUpdateBtn"
					class="btn btn-default btn-shadow btn-shadow-default demo2do-btn">提交
				</button>
			</div>
			<div id="toastArea">
				<p id="messasge"></p>
			</div>
		</div>
	</div>
</div>
<script>
$('#doUpdateBtn').click(function() {
	var $comment = $("input[name='comment']").val();
	var $assetSetId = $("input[name='assetSetId']").val();
	$.ajax({
		type : 'post',
		url : '${ctx}/assets/'+$assetSetId+'/update-comment',
		data : {
			'comment' : $comment,
		},
		dataType : 'json',
		success : function(resp) {
			$('#funArea').hide()
			$('#toastArea').show()
			if (resp.code == 0) {
				$("p").text("编辑成功"); 
			} else {
				$("p").text("编辑失败"); 
			}
			$('#modal-dialog .close-dialog').click();
			window.location.reload();
		}
	});

});
</script>
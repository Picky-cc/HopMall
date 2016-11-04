<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary" %>

<c:set var="accountSide">
	<%= request.getParameter("accountSide") %>
</c:set>

<script type="script/template" id="addCashFlowTmpl">

   <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
        <h4 class="modal-title" id="dialoglabel">添加现金流水</h4>
      </div>
      <div class="modal-body form-wrapper">
        <form class="form adapt">
     		  <div class="field-row">
            <label for="" class="field-title require">${accountSide == "debit" ? "付款方名" : "收款方名"}</label>
            <div class="field-value">
      			  <input class="form-control real-value text-left" placeholder=${accountSide == "debit" ? "付款方名" : "收款方名"} name="payName" value="">
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">发生时间</label>
            <div class="field-value">
                <jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
                  <jsp:param value="true" name="calendarbtn"/>
                  <jsp:param value="single" name="type"/>
                  <jsp:param value="transactionDate" name="paramName1"/>
      					  <jsp:param value="" name="paramValue1"/>
                </jsp:include>
            </div>
          </div>
          <div class="field-row" id="amountField">
                <label for="" class="field-title require">金额</label>
                <div class="field-value">
                  <span class="parcel-input rear">
                       <input class="form-control real-value" type="text" style="text-align:left; " name="amount" placeholder="请输入金额" value="">
                       <span class="suffix">元</span>
                  </span>
                </div>
       	  </div>
   				<div class="field-row">
            <div class="field-title">备注</div>
            <div class="field-value">
              <textarea name="remark" class="multiline-input form-control real-value"  cols="30" rows="1" placeholder="30字以内"></textarea>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default cancel-dialog" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-success submit">确定</button>
      </div>
    </div>
   </div>
</script>
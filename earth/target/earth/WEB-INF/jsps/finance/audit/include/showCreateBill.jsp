<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary" %>

<script type="script/template" id="showCreateBillTmpl">
   <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
        <h4 class="modal-title" id="dialoglabel">创建自定义账单</h4>
      </div>
      <div class="modal-body form-wrapper">
        <form class="form adapt">
          <div class="field-row">
            <label for="" class="field-title require">自有物业编号</label>
            <div class="field-value">
              <input class="form-control text-left" placeholder="请输入自有物业编号" type="text" name="keyWord" value="{%= obj.subjectMatterSourceNo%}" required autofocus>
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">租约信息</label>
            <div class="field-value">
               <select class="form-control bill-style real-value" name="contractUuid" required>
        					{% _.each(obj.simplifedContractInfoList,function(item,index){ %}
        						<option value="{%=item.businessContractUuid%}">{%=item.contractName%}</option>
        					{% }); %}
               </select>
            </div>
          </div>
 		  <div class="field-row">
            <label for="" class="field-title require">账单名称</label>
            <div class="field-value">
  			  <input class="form-control real-value text-left" placeholder="账单名称" name="billName" value="" required>
            </div>
          </div>
		 <div class="field-row">
              <label for="" class="field-title require ">账单类型</label>
              <div class="field-value" >
                 <select class="form-control bill-style real-value" name="entryType" required>
        						{% _.each(obj.entryTypeMap, function(alias,key){ %}
                      {% if(obj.accountSide == 'debit' && (key == 'BILL_ENTRY_TYPE_ACCOUNT_RECIEVABLE' || key == 'BILL_ENTRY_TYPE_COLLECT')) { %}
          							<option value="{%=key%}">{%=alias%}</option>
                      {% }else if(obj.accountSide == 'credit' && key == 'BILL_ENTRY_TYPE_ACCOUNT_PAYABLE') { %}
                        <option value="{%=key%}">{%=alias%}</option>
                      {% } %}
        						{% }); %}
                  </select>
              </div>
          </div>
		  <div class="field-row">
                  <label for="" class="field-title require">费用类型</label>
                    <div class="field-value">
                       <select class="form-control real-value" name="billType" required>
						{% _.each(obj.billTypeMap,function(alias,key){ %}
							<option value="{%=key%}">{%=alias%}</option>
						{% }); %}
                       </select>
                    </div>
          </div>
     	  <div class="field-row">
                  <label for="" class="field-title require">对应日期</label>
                  <div class="field-value">
                    <div class="beginend-datepicker datetimepick-wrapper">
                      <jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
                        <jsp:param value="true" name="calendarbtn"/>
                        <jsp:param value="group" name="type"/>
                        <jsp:param value="billEffectiveDate" name="paramName1"/>
                        <jsp:param value="{%= obj.billEffectiveDate.format('yyyy-MM-dd') %}" name="paramValue1"/>
                        <jsp:param value="billMaturityDate" name="paramName2"/>
                        <jsp:param value="{%= obj.billMaturityDate.format('yyyy-MM-dd') %}" name="paramValue2"/>
                      </jsp:include>
                    </div>
                   </div>
                </div>
                <div class="field-row">
                  <label for="" class="field-title require">应收日期</label>
                  <div class="field-value">
                    <jsp:include page="/WEB-INF/include/plugins/datetimepicker.jsp">
                      <jsp:param value="true" name="calendarbtn"/>
                      <jsp:param value="single" name="type"/>
                      <jsp:param value="billTradeDate" name="paramName1"/>
					  <jsp:param value="{%= new Date(obj.cashFlowTime).format('yyyy-MM-dd') %}" name="paramValue1"/>
                    </jsp:include>
                  </div>
                </div>
                <div class="field-row" id="amountField">
                  <label for="" class="field-title require">应收金额</label>
                  <div class="field-value">
                    <span class="parcel-input rear">
                         <input class="form-control middle real-value" type="text" style="text-align:left; "name="amount" data-params="amount" placeholder="请输入金额"  required value="{%=obj.balanceMoney%}">
                         <span class="suffix">元</span>
                    </span>
                  </div>
         	   </div>
 				<div class="field-row">
                  <div class="field-title">备注</div>
                  <div class="field-value">
                    <textarea name="remark" class="multiline-input form-control real-value"  cols="30" rows="1" placeholder="30字以内" maxlength="30"></textarea>
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
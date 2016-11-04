<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="script/template" id="exportYonYouDataTmpl">
   <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
        <h4 class="modal-title" id="dialoglabel">导出用友凭证数据</h4>
      </div>
      <div class="modal-body form-wrapper">
        <form class="form adapt">
          <div class="field-row">
            <label for="" class="field-title require">唯一标识</label>
            <div class="field-value">
              <input class="form-control real-value text-left" placeholder="请输入唯一标识" type="text" name="fIdentity" value="" required>
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">分录自动编号</label>
            <div class="field-value">
              <input class="form-control real-value text-left" placeholder="请输入分录自动编号" type="text" name="fEntryAutoNo" value="" required>
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">账套号</label>
            <div class="field-value">
              <input class="form-control real-value text-left" placeholder="请输入账套号" type="text" name="fbookNo" value="" required>
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">核算单位</label>
            <div class="field-value">
              <input class="form-control real-value text-left" placeholder="请输入核算单位" type="text" name="fUnit" value="" required>
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">会计年份</label>
            <div class="field-value">
              <input class="form-control real-value text-left" placeholder="会计年份" type="number" name="fFinicialYear" value="<%=DateUtils.year()%>" required min="1970" max="2099">
            </div>
          </div>
     		  <div class="field-row">
            <label for="" class="field-title require">会计月份</label>
            <div class="field-value">
      			  <input class="form-control real-value text-left" placeholder="会计月份" type="number" name="fFinicialPeriod" value="<%=DateUtils.month()%>" required min="1" max="12">
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">凭证号</label>
            <div class="field-value">
              <input class="form-control real-value text-left" placeholder="请输入凭证号" type="text" name="fVoucherNo" value="" required>
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">制单者</label>
            <div class="field-value">
              <input class="form-control real-value text-left" placeholder="请输入制单者" type="text" name="fVoucherMaker" value="" required>
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
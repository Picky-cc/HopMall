<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="script/template" id="exportKingDeeDataTmpl">
   <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
        <h4 class="modal-title" id="dialoglabel">导出金蝶凭证数据</h4>
      </div>
      <div class="modal-body form-wrapper">
        <form class="form adapt">
          <div class="field-row">
            <label for="" class="field-title require">序号</label>
            <div class="field-value">
              <input class="form-control real-value text-left" placeholder="请输入序号" type="text" name="fSerialNum" value="" required>
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">会计年份</label>
            <div class="field-value">
              <input class="form-control real-value text-left" placeholder="会计年份" type="number" name="fYear" value="<%=DateUtils.year()%>" required min="1970" max="2099">
            </div>
          </div>
 		  <div class="field-row">
            <label for="" class="field-title require">会计月份</label>
            <div class="field-value">
  			  <input class="form-control real-value text-left" placeholder="会计月份" type="number" name="fMonth" value="<%=DateUtils.month()%>" required min="1" max="12">
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">凭证号</label>
            <div class="field-value">
              <input class="form-control real-value text-left" placeholder="请输入凭证号" type="text" name="fNumber" value="" required>
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title require">制单者</label>
            <div class="field-value">
              <input class="form-control real-value text-left" placeholder="请输入制单者" type="text" name="fPreparerId" value="" required>
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
 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="template/script" id="markCashTypeTmpl">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close close-dialog" aria-label="关闭" data-dismiss="modal"><span aria-hidden="true">×</span></button>
        <h4 class="modal-title" id="dialoglabel">添加标记</h4>
      </div>
      <div class="modal-body form-wrapper">
        <form class="form adapt">
          <div class="field-row">
            <label for="" class="field-title">类型</label>
            <div class="field-value">
              <select name="financialAccountName" id="" class="real-value form-control middle">
                <option value="">请选择（无类型）</option>
                {% _.each(obj.types, function(alias, value) { %}
                  <option value="{%= value %}" {%= value === obj.financialAccountName ? 'selected':'' %}>{%= alias %}</option>
                {% }); %}
              </select>
            </div>
          </div>
          <div class="field-row">
            <label for="" class="field-title">备注</label>
            <div class="field-value">
              <textarea placeholder="请输入备注" name="journal" class="form-control real-value multiline-input">{%= obj.journal || obj.summary %}</textarea>
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
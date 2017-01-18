<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="script/template" class="template">
    <div class="form-wrapper">
        <div class="form adapt">
            <div class="field-row">
                <label for="" class="field-title">请选择通道策略</label>
                <div class="field-value">
                    <select name="channel-mode" class="form-control">
                        <option value="0" {%= paymentStrategyMode == '0' ? 'selected' : '' %}>单一通道模式</option>
                        <option value="1" {%= paymentStrategyMode == '1' ? 'selected' : '' %}>发卡行优先模式</option>
                    </select>
                </div>
            </div>
        </div>
    </div>
</script>
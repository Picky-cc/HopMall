<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="script/template" class="template">
    <div class="channel-priority">
        {% if(list.length){ %}
            <div style="padding: 0 15px;">
                请配置默认通道：
                <c:if test="${param.paymentChannelMode == 'issuer'}">
                    <span class="color-dim">拖动方块，以从左到右、从上到下为序排优先级，左上优先级最高</span>
                </c:if>
            </div>
            <div class="panels drag-wrapper">
                {% _.each(list, function(item, index) { %}
                    <div 
                        class="panel drag-item" 
                        data-payment-channel-uuid="{%= item.paymentChannelUuid %}"
                    >
                        <div>{%= item.paymentChannelName %}</div>
                        <div style="font-size: 12px;">
                            <span class="color-dim">{%= item.fee %}</span>
                            <span class="pull-right">{%= item.channelStatusMsg %}</span>
                        </div>
                    </div>
                {% }) %} 
            </div>
            <div class="tip color-danger" style="display: none; padding-left: 15px;">该通道已关闭，请开启通道</tip>
        {% }else { %}
            <%-- <div style="padding-left: 15px;">没有通道</tip> --%>
        {% } %}
    </div>
</script>
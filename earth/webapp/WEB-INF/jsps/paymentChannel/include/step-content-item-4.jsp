<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="script/template" class="template">
    <div class="priority-preview">
        <div style="padding: 0 15px;">发卡行通道优先级预览</div>
        <div class="table without-alternative-bg">
            <div class="block">
                <div class="lookup-params hide">
                    <span class="item">
                        <input type="text" class="form-control real-value" style="width: 180px;" placeholder="搜索银行">
                    </span>
                    <span class="item">
                        <button id="lookup" class="btn btn-primary">查询</button>
                    </span>
                </div>
                <div class="bd">
                    <table class="data-list">
                        <thead>
                            <tr>
                                <th>银行名称</th>  
                                {% _.each(paymentChannelList, function(item, index) { %}
                                    <th>优先级第{%= index + 1 %}位</th>
                                {% }) %}
                            </tr>
                        </thead>
                        <tbody>
                            {% _.each(previewList, function(bank, index) { %}
                                <tr>
                                    <td>{%= bank.bankName %}</td>
                                    {% _.each(bank.paymentChannelOrder, function(channel, index) { %}
                                        <td>
                                            {%= channel.paymentChannelName %}
                                        </td>
                                    {% }) %}
                                </tr>
                            {% }) %}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</script>
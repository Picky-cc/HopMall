<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="script/template" class="template">
    <div class="first-choice">
        <div style="padding: 0 15px;">请配置发卡行首选通道</div>
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
                    {% if (paymentChannelList.length){ %}
                        <table class="data-list">
                            <thead>
                                <tr>
                                    <th></th>
                                    {% _.each(paymentChannelList, function(item, index) { %}
                                        <th>{%= item.paymentChannelName %}</th>
                                    {% }) %}
                                </tr>
                            </thead>
                            <tbody>
                                {% if (bankList.length) { %}
                                    {% _.each(bankList, function(bank, bankIndex) { %}
                                        <tr data-bank-code="{%= bank.bankCode %}">
                                            <td>{%= bank.bankName %}</td>
                                            {% _.each(paymentChannelList, function(channel, channelIndex) { %}
                                                <td>
                                                    <input 
                                                        type="checkbox" 
                                                        data-payment-channel-uuid="{%= channel.paymentChannelUuid %}"
                                                        {%= model.paymentChannelOrderForBanks[bank.bankCode] === channel.paymentChannelUuid 
                                                            ? 'checked' 
                                                            : (!model.paymentChannelOrderForBanks[bank.bankCode] && channelIndex === 0) ? 'checked' : '' %}
                                                    >
                                                </td>
                                            {% }) %}
                                        </tr>
                                    {% }) %}
                                {% } else { %}
                                    <tr>
                                        <td style="text-align: center;" colspan="{%= paymentChannelList.length + 1 %}">没有更多</td>
                                    </tr>
                                {% } %}
                            </tbody>
                        </table>
                    {% } %}
                </div>
            </div>
        </div>
    </div>
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh_CN" />

<c:if test="${not empty requestScope[param.paymentChannelMode]}">
    <div style="margin-bottom: 15px;">
        <span class="field-span">
            <fmt:message key='${requestScope[param.paymentChannelMode].key}'/>
            <c:if test="${requestScope[param.paymentChannelMode] == 'ISSUINGBANKFIRST'}">
                <i class="icon icon-help" style="margin-left:10px" data-title="默认优先级：列表从上往下为序排优先级，上方优先级最高"></i>
            </c:if>
        </span>
        <c:if test="${fn:length(requestScope[param.paymentChannelData]) > 0}">
            <div class="table without-alternative-bg">
                <div class="block">
                    <div class="lookup-params hide">
                    </div>
                    <div class="bd">
                        <table class="data-list">
                            <thead>
                                <tr>
                                    <th>通道名称</th>
                                    <th>通道费用</th>
                                    <th>状态</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="item" items="${requestScope[param.paymentChannelData]}">
                                    <tr>
                                        <td>${item.paymentChannelName}</td>
                                        <td>${item.fee}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${item.channelStatus == 'OFF'}">
                                                    <span class="color-danger"><fmt:message key="${item.channelStatus.key}" /></span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span><fmt:message key="${item.channelStatus.key}" /></span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <c:if test="${requestScope[param.paymentChannelMode] == 'ISSUINGBANKFIRST'}">
                <a href="#" class="btn-priority-preview">各银行通道顺序预览</a><br><br>
            </c:if>
        </c:if>
    </div>
</c:if>

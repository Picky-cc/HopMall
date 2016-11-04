<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh_CN" />
<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>主动还款凭证 - 五维金融金融管理平台</title>
</head>
<body>

<%@ include file="/WEB-INF/include/header.jsp"%>
<div class="web-g-main">
    <%@ include file="/WEB-INF/include/aside.jsp"%>
    <div class="content">
        <div class="scroller">
            <div class="lookup-params">
                <div class="inner clearfix">
                    <span class="item">
                        <select name="voucherType" class="form-control real-value">
                            <option value="-1">凭证类型</option>
                            <c:forEach var="voucherType" items= "${voucherTypeList }">
                                <option value="${voucherType.ordinal() }"><fmt:message key="${voucherType.key}" ></fmt:message></option>
                            </c:forEach>
                        </select>
                     </span>
                    <span class="item">
                        <select name="voucherStatus" class="form-control real-value">
                            <option value="-1">凭证状态</option>
                            <c:forEach var="voucherStatus" items= "${voucherStatusList }">
                                <option value="${voucherStatus.ordinal() }"><fmt:message key="${voucherStatus.key}" ></fmt:message></option>
                            </c:forEach>
                        </select>
                    </span>
                    <span class="item">
                        <input type="text" class="form-control real-value" name="hostAccount" placeholder="专户账号">
                    </span>
                    <span class="item">
                        <input type="text" class="form-control real-value" name="counterName" placeholder="账户姓名">
                    </span>
                    <span class="item">
                        <input type="text" class="form-control real-value" name="counterNo" placeholder="机构账户号">
                    </span>
                    <span class="item">
                        <button id="lookup" class="btn btn-primary">
                            查询
                        </button>
                    </span>
                    <span class="item">
                        <a href="${ctx}/voucher/active/create" id="create" class="btn btn-success">
                            新增
                        </a>
                    </span>
                </div>
            </div>
            <div class="table-area">
                <table class="data-list" data-action="${ctx}/voucher/active/query" data-autoload="true">
                        <thead>
                            <tr>
                                <th>
                                    <label><input class="check-box check-box-all" type="checkbox" >全选</label>
                                </th>
                                <th>凭证编号</th>
                                <th>专户账号</th>
                                <th>来往机构名称</th>
                                <th>账户姓名</th>
                                <th>机构账户号</th>
                                <th>凭证金额</th>
                                <th>凭证类型</th>
                                <th>凭证内容</th>
                                <th>凭证来源</th>
                                <th>凭证状态</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <script type="script/template" id="tableFieldTmpl">
                                {% _.each(list, function(item, index){ %}
                                    <tr data-detail-id="{%= item.detailId %}">
                                        <td>
                                            <label><input class="check-box" type="checkbox" ></label>
                                        </td>
                                        <td>{%= item.sourceDocumentNo %}</td>
                                        <td>{%= item.receivableAccountNo %}</td>
                                        <td>{%= item.paymentBank %}</td>
                                        <td>{%= item.paymentName %}</td>
                                        <td>{%= item.paymentAccountNo %}</td>
                                        <td>{%= (+item.amount).formatMoney(2, '') %}</td>
                                        <td>{%= item.voucherType %}</td>
                                        <td><a href='#'>无</a></td>
                                        <td>{%= item.voucherSource %}</td>
                                        <td>{%= item.voucherStatus %}</td>
                                        <td>
                                            <a href="${ctx}/voucher/active/detail/{%= item.detailId %}">详情</a>
                                        </td>
                                    </tr>
                                {% }) %}
                            </script>
                        </tbody>
                    </table>
            </div>
        </div>
        <div class="operations">
            <span class="item hide">
                <button class="btn export" data-action="">导出</button>
            </span>
            <span class="item hide">
                <button class="btn invalidate">作废</button>
            </span>
            <jsp:include page="/WEB-INF/include/plugins/assembly.jsp">
                <jsp:param value="page-control" name="type"/>
                <jsp:param value="true" name="advanced"/>
            </jsp:include>
        </div>
    </div>
	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
	<script src="${ctx.resource}/js/bootstrap-modal-trigger.js"></script>
	<script src="${ctx.resource}/js/bootstrap.validate.js"></script>

</body>
</html>

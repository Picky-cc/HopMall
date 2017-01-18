<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="dashboard ${param.show ? '' : 'hide'}" data-disable-toggle="${param.disableToggle}">
    <div class="inner">
        <sec:authentication property="principal" var="currentPrincipal" scope="page" />
        <div class="hd">
            <p class="name"><strong>${data.getRealnameBy(currentPrincipal.id)}[${currentPrincipal.username}],欢迎您</strong></p>
            <p>您今日所需关注的工作内容如下</p>
        </div>
        <c:set var="canAccessFinancialContractList" value="${data.getCanAccessFinancialContractList(currentPrincipal.id)}"></c:set>
        <div class="bd">
            <div class="projects">
            	<p style="color:#999;">请选择信托项目</p>
                <select id="" class="form-control selectpicker" multiple title="请选择信托项目" data-actions-box="true">
                	<c:forEach var="item" items="${canAccessFinancialContractList}">
						<option value="${item.id}" selected>${item.contractName}(${item.contractNo })</option>
					</c:forEach>
                </select>
            </div>
            <div class="todos">
                <div class="tab-panel">
                    <ul class="nav nav-tabs">
                        <li>
                            <a href="#" data-key="remittance" data-href="${ctx}/welcome/count?type=remittance">放款 <span class="total"></span></a>
                        </li>
                        <li>
                            <a href="#" data-key="repayment" data-href="${ctx}/welcome/count?type=repayment">还款 <span class="total"></span></a>
                        </li>
                        <!-- <li>
                            <a href="#" data-key="refund" data-href="${ctx}/welcome/count?type=refund">退款 <span class="total"></span></a>
                        </li> -->
                    </ul>
                    <div class="tab-content">
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

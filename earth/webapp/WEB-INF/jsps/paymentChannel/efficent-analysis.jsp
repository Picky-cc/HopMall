<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

<fmt:setBundle basename="ApplicationMessage" />
<fmt:setLocale value="zh_CN" />

<%@ include file="/WEB-INF/include/meta.jsp"%>
<%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
<title>通道效率分析 - 五维金融金融管理平台</title>

</head>
<body>

	<%@ include file="/WEB-INF/include/header.jsp"%>

	<div class="web-g-main">

		<%@ include file="/WEB-INF/include/aside.jsp"%>
		<div class="content content-passage">
			<div class="scroller">
				<div class="position-map">
		          <div class="pull-left">
		            当前位置：
		            <span class="item"><a href="${ctx}/paymentchannel" class="deep-gray-color">通道管理</a> &gt;</span>
		            <span class="item current">效率分析</span>
		          </div>
		        </div>
		        <div class="wrapper">
		        	<div class="sketch">
		        		<div class="hd">
		        		</div>
		        		<div class="bd">
		        			<div class="cols">
		        				<div class="col">
		        					<p><span class="color-warning">${debitTransferApplicationCount}</span></p>
		        					<p>累计收款成功笔数</p>
		        				</div>
		        				<div class="col">
		        					<p>
		        						<fmt:formatNumber type='number' pattern="#,#00.00#" value="${debitTotalTradingVolume}" />
		        					</p>
		        					<p>累计收款交易额</p>
		        				</div>
		        				<div class="col">
		        					<p>
		        						<fmt:formatNumber type='number' pattern="#,#00.00#" value="${debitTotalFee}" />
		        					</p>
		        					<p>累计收款通道费用</p>
		        				</div>
		        				<div class="col">
		        					<p><span class="color-warning">${creditTransferApplicationCount}</span></p>
		        					<p>累计付款成功笔数</p>
		        				</div>
		        				<div class="col">
		        					<p><fmt:formatNumber type='number' pattern='#,#00.00#' value='${creditTotalTradingVolume}' /></p>
		        					<p>累计付款交易额</p>
		        				</div>
		        				<div class="col">
		        					<p>
		        						<fmt:formatNumber type='number' pattern="#,#00.00#" value="${creditTotalFee}" />
		        					</p>
		        					<p>累计付款通道费用</p>
		        				</div>
		        			</div>
		        		</div>
		        	</div>
		        	<div class="graph">
		        		<div class="row">
    		        		<div class="item turnover">
    		        			<div class="hd">
    			        			<i class="icon icon-circle"></i>
    		        				<span>交易额趋势&nbsp;&nbsp;&nbsp;</span>
    		        				<select class="form-control filter-turnover">
    		        					<option value="7d">最近7天</option>
    		        					<option value="6m">最近6个月</option>
    		        				</select>
    			        		</div>
    		        			<div class="bd">
    		        				<div class="draw" style="width: 100%; height: 300px; margin: auto; max-width: 1160px;"></div>
    		        			</div>
    		        		</div>
		        		</div>
		        		<div class="row">
    		        		<div class="item channel">
    		        			<span class="legend">
    		        				<span data-legend-type="成功"><i style="background: #90ed7d" class="rect"></i>成功</span>
    		        				<span data-legend-type="失败"><i style="background: #f04f76" class="rect"></i>失败</span>
    		        				<span data-legend-type="异常"><i style="background: #f7a35c" class="rect"></i>异常</span>
    		        				<span data-legend-type="处理中"><i style="background: #7cb5ec" class="rect"></i>处理中</span>
                                    <span data-legend-type="等待处理"><i style="background: #434348" class="rect"></i>待处理</span>
    		        			</span>
    		        			<div class="hd">
    			        			<i class="icon icon-circle"></i>
    		        				<span>24小时通道交易统计&nbsp;&nbsp;&nbsp;</span>
    		        				<select class="form-control filter-channel">
    		        					<option value="0">收付款</option>
    		        					<option value="1">仅收款</option>
    		        					<option value="2">仅付款</option>
    		        				</select>
    			        		</div>
    		        			<div class="bd clearfix">
    		        			</div>
    		        		</div>
		        		</div>
		        	</div>
		        </div>
			</div>
		</div>
	</div>

	<%@ include file="/WEB-INF/include/script-newlayout.jsp"%>

</body>
</html>

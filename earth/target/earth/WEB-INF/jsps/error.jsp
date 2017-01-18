<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

  <fmt:setBundle basename="ApplicationMessage" />
  <fmt:setLocale value="zh_CN" />

  <%@ include file="/WEB-INF/include/meta.jsp"%>
  <%@ include file="/WEB-INF/include/css.jsp"%>
  <link href="${ctx.resource}/css/select2.css" rel="stylesheet">
  <link href="${ctx.resource}/css/select2-bootstrap.css" rel="stylesheet">
  <title>出错了！ - 五维金融金融管理平台</title>
</head>
<body>

  <%@ include file="/WEB-INF/include/header.jsp"%>
  
  <div class="web-g-main">

    <div class="content">
      <header class="header clearfix">
          <h3 class="pull-left btn-warning"><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>${not empty message?message:'系统繁忙请联系管理员！！！'}</h3>
      </header>
      
       <%@ include file="/WEB-INF/include/footer.jsp"%>
       
    </div>

  </div>
  <%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
  <script src="${ctx.resource}/js/bootstrap-modal-trigger.js"></script>
</body>
</html>

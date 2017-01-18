<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>

  <fmt:setBundle basename="ApplicationMessage" />
  <fmt:setLocale value="zh_CN" />

  <%@ include file="/WEB-INF/include/meta.jsp"%>
  <%@ include file="/WEB-INF/include/css-newlayout.jsp"%>

  <title>五维金融金融管理平台</title>

</head>

<body class="welcome">
    <jsp:include page="/WEB-INF/include/header.jsp">
        <jsp:param value="true" name="show"/>
        <jsp:param value="true" name="disableToggle"/>
    </jsp:include>

    <div class="web-g-main">
        <div class="background">
            <div class="inner">
                <div class="slogan">
                    <div class="bd">
                        <h1 class="title">Welcome</h1>
                        <h3 class="subtitle">五维金融管理平台</h3>
                    </div>
                </div>
                <div class="copyright">
                    © 2015 杭州随地付网络技术有限公司 版权所有
                </div>
            </div>
        </div>
    </div>

    <%@ include file="/WEB-INF/include/script-newlayout.jsp"%>

</body>
</html>

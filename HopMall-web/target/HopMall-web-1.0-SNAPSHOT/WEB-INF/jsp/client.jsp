<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/1/8
  Time: 16:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="../js/angular.min.js"></script>
    <%--<script src="../js/angular.js"></script>--%>
</head>
<body>
    <div ng-app="">
        <p>名字 : <input type="text" ng-model="name"></p>
        <h1>Hello {{5+name}}</h1>
    </div>
</body>
</html>

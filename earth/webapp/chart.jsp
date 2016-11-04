<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML>
<html>
  <head>
     <%@ include file="/WEB-INF/include/css-newlayout.jsp"%>
  </head>
  <body>
    <div id="datetimepicker" class="imitate-datetimepicker-input date input-group input-append">
        <input class="form-control datetimepicker-form-control" value="2012-01-01" size="16" type="text" readonly style="width: 180px">
        <span class="input-group-addon add-on">
          <i class="glyphicon glyphicon-calendar"></i>
        </span>
    </div>

    <%@ include file="/WEB-INF/include/script-newlayout.jsp"%>
    <script type="text/javascript">
      $('#datetimepicker').datetimepicker({
        startDate: new Date('2016-01-01'),
        format: 'yyyy-MM-dd',
        language: 'zh-CN',
        pickTime: false,
        autoclose: false,
        // orientation: 'left'
      });
    </script>
  </body>
<html>

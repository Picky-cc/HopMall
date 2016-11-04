<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
    <c:when test="${param.type == 'page-control' }">
    	<c:choose>
    		<c:when test="${param.advanced }">
          <div class="rt pull-right page-control advanced" data-page_record_num="${param.pageRecordNum}">
      			<div class="inner">
      				共<span class="total"></span>条
              <span class="nav">
                <a href="javascript: void 0;" class="first-page">首页</a>
                <a href="javascript: void 0;" class="prev">&lt; 上一页</a>
                <span class="tip">1/页</span>
                <a href="javascript: void 0;" class="next">下一页 &gt;</a>
                <a href="javascript: void 0;" class="last-page">尾页</a>
              </span>
              <span class="popup-redirect-form">跳转</span>
            </div>
            <div class="redirect-form hide">
              跳转至<input type="text" class="form-control page-index" name="" id="">页
              <button class="btn btn-defalut redirect">确定</button>
            </div>
          </div>
    		</c:when>
    		<c:otherwise>
          <div class="rt pull-right page-control" data-page_record_num="${param.pageRecordNum}">
      			<div class="inner">
                  <a href="javascript: void 0;" class="prev">&lt; 上一页</a>
                  <span class="tip">1/页</span>
                  <a href="javascript: void 0;" class="next">下一页 &gt;</a>
              </div>
          </div>
    		</c:otherwise>
    	</c:choose>
    </c:when>

    <%-- 
        param:
        type: area-select
        defaultProvinceCode：初始省code
        defaultCityCode：初始市code
        defaultAreaCode：初始县code
        minRank: 精确到哪个级别
        disabled: 是否禁用
     --%>
    <c:when test="${param.type == 'area-select' }">
        <div class="area-select" data-default_provincecode="${param.defaultProvinceCode}" data-default_citycode="${param.defaultCityCode}" data-default_areacode="${param.defaultAreaCode}">
          <select class="form-control real-value small province-select" name="provinceCode" ${param.disabled}>
            <option value="">请选择</option>
          </select>
          <c:if test="${param.minRank == 'city'}">
            <select class="form-control real-value small city-select" name="cityCode" ${param.disabled}>
              <option value="">请选择</option>
            </select>
          </c:if>
          <c:if test="${param.minRank == 'area' || empty param.minRank}">
            <select class="form-control real-value small city-select" name="cityCode" ${param.disabled}>
              <option value="">请选择</option>
            </select>
            <select class="form-control real-value small district-select" name="areaCode" ${param.disabled}>
              <option value="">请选择</option>
            </select>
          </c:if>
        </div>
    </c:when>

</c:choose>



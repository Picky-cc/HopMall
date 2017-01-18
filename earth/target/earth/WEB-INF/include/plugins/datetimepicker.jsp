<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 
    param:
    type: 默认single，group(开始，结束时间选择)，single(单个时间选择)
    paramName1：传给后台的时间参数名称1
    paramName2：传给后台的时间参数名称2
    paramValue1：参数1的默认值
    paramValue2：参数2的默认值
    placeHolder1:文本1显示的默认值
    placeHolder2:文本2显示的默认值
    pickTime: 是否选择到秒
    startDate1：限制开始时间的startDate
    endDate1：限制开始时间的endDate
    startDate2：限制开始时间的startDate
    endDate2：限制开始时间的endDate
    disabled: 禁用
    formatToMinimum：group类型时，未选择时分秒时是否设置为00:00:00
    formatToMaximum：group类型时，未选择时分秒时是否设置为23:59:59
 --%>
<c:choose>
    <c:when test="${param.type == 'group' }">
        <div 
            class="imitate-datetimepicker-input date input-group starttime-datepicker pull-left input-append emitbyclick" 
            data-pick-time='${(not empty param.pickTime) ? param.pickTime : "false"}'
            data-start-date='${(not empty param.startDate1) ? param.startDate1 : ""}'
            data-end-date='${(not empty param.endDate1) ? param.endDate1 : ""}'
            data-format-to-minimum='${(not empty param.formatToMinimum) ? param.formatToMinimum : "false"}'
            ${param.disabled}
        >
            <input 
                class="form-control datetimepicker-form-control real-value" 
                value="${param.paramValue1}" 
                placeholder="${(not empty param.placeHolder1) ? param.placeHolder1 : '开始时间'}"
                name="${(not empty param.paramName1) ? param.paramName1 : 'startTime'}"
                size="16" 
                type="text" 
                style='${(not empty param.pickTime) ? "min-width: 123px;" : ""}'
                readonly
            >
            <span class="add-on"><i></i></span>
            <span class="input-group-addon">
              <i class="glyphicon"></i>
            </span>
        </div>
        <span class="pull-left hyphen">&nbsp;至&nbsp; </span>
        <div 
            class="imitate-datetimepicker-input date input-group endtime-datepicker pull-left input-append emitbyclick" 
            data-pick-time='${(not empty param.pickTime) ? param.pickTime : "false"}'
            data-start-date='${(not empty param.startDate2) ? param.startDate2 : ""}'
            data-end-date='${(not empty param.endDate2) ? param.endDate2 : ""}'
            data-format-to-maximum='${(not empty param.formatToMaximum) ? param.formatToMaximum : "false"}'
            ${param.disabled}
        >
            <input 
                class="form-control datetimepicker-form-control real-value" 
                value="${param.paramValue2}" 
                placeholder="${(not empty param.placeHolder2) ? param.placeHolder2 : '开始时间'}"
                name="${(not empty param.paramName2) ? param.paramName2 : 'startTime'}"
                size="16" 
                type="text" 
                style='${(not empty param.pickTime) ? "min-width: 123px;" : ""}'
                readonly
            >
            <span class="add-on"><i></i></span>
            <span class="input-group-addon">
              <i class="glyphicon"></i>
            </span>
        </div>
    </c:when>
    <c:otherwise>
        <div 
            class="imitate-datetimepicker-input date input-group starttime-datepicker pull-left input-append emitbyclick" 
            data-pick-time='${(not empty param.pickTime) ? param.pickTime : "false"}'
            data-start-date='${(not empty param.startDate1) ? param.startDate1 : ""}'
            data-end-date='${(not empty param.endDate1) ? param.endDate1 : ""}'
            ${param.disabled}
        >
            <input 
                class="form-control datetimepicker-form-control real-value" 
                value="${param.paramValue1}" 
                placeholder="${(not empty param.placeHolder1) ? param.placeHolder1 : '开始时间'}"
                name="${(not empty param.paramName1) ? param.paramName1 : 'startTime'}"
                size="16" 
                type="text" 
                style='${(not empty param.pickTime) ? "min-width: 123px;" : ""}'
                readonly
            >
            <span class="add-on"><i></i></span>
            <span class="input-group-addon">
              <i class="glyphicon"></i>
            </span>
        </div>
    </c:otherwise>
</c:choose>

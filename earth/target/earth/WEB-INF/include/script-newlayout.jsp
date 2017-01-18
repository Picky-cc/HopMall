<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
    var global_config = { 
        webname: 'earth', 
        root: '${ctx}',
        resource: '${ctx.resource}'
    };
    var image_oss_host = {
        thumbnail: 'http://zufangbao1.img-cn-shanghai.aliyuncs.com/',
        original: 'http://zufangbao1.oss-cn-shanghai.aliyuncs.com/' 
    };
	var thumbnai_image_oss_host = "http://aliyuntstest1.img-cn-hangzhou.aliyuncs.com/";
</script>

<script src="${ctx.resource}/js/vendor/jquery-1.11.0.min.js"></script>
<script src="${ctx.resource}/js/vendor/bootstrap.min.js"></script>
<script src="${ctx.resource}/js/utils/underscore.js"></script>
<script src="${ctx.resource}/js/utils/backbone.js"></script>

<script src="${ctx.resource}/js/plugins/bootstrap-select.js"></script>
<script src="${ctx.resource}/js/plugins/bootstrap-datetimepicker-2.js"></script>
<script src="${ctx.resource}/js/plugins/common-content.js"></script>
    
<c:if test="${ param.immediateInit != 'false' }">
<script src="${ctx.resource}/js/mods/init.js"></script>
</c:if>
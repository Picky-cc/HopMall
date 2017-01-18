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

  <title>五维金融金融管理平台</title>

</head>

<body>

  <header class="web-g-hd login-g-hd">
      <div class="g-bd clearfix">
        <div class="lt">
          <span class="logo">
              <i class="icon"></i>
          </span>
          <span class="divider"></span>
          <span class="sub-title">
              <img class="company-logo" src="${ctx.resource}/images/company-logo/yunxin-login.png" alt="">
          </span>
        </div>
        <div class="rt">
          <ul class="list-inline hide">
              <li class="inline-item"><a href="">帮助中心</a></li>
              <li class="inline-item divider">|</li>
              <li class="inline-item"><a href="">开发者文档</a></li>
          </ul>
        </div>
      </div>
  </header>

  <div class="web-g-main login-g-main">
      <div class="web-g-bd login-g-bd">
          <div class="banner">
              <h1 class="title">
                <div style="text-align: center; font-size: 30px;">创造</div>
                从无到有的金融市场
              </h1>
              <img src="${ctx.resource}/images/computer.png" alt="" class="computer"> <i class="icon icon-coffer"></i>
          </div>
          <div class="login-area">
              <h2 class="title">登录</h2>
              <form class="login-form" id="login-form" action="${ctx}/j_spring_security_check" method="POST" data-validate="true">
                  <section class="field-row username-field">
                      <label for="" class="input-title">帐号</label>
                      <div class="input-group">
                          <input id="username" type="text" class="form-control" name="j_username" value="">
                          <span class="input-group-addon">
                              <i class="icon icon-input-cancel" id="cancel-icon">&times</i>
                          </span>
                      </div>
                      <div class="tip">
                        <%-- <c:if test="${not empty errorMessage}"><fmt:message key="${errorMessage}" /></c:if> --%>
                      </div>
                  </section>

                  <section class="field-row userpwd-field <c:if test="${not empty errorMessage}">has-error</c:if>">
                      <label for="" class="input-title">密码</label>
                      <div class="input-group">
                          <input id="userpwd" type="password" class="form-control" name="j_password">
                          <span class="input-group-addon">
                              <i class="icon icon-input-question">?</i>
                          </span>
                      </div>
                      <div class="tip">
                      	<c:if test="${not empty errorMessage}"><fmt:message key="${errorMessage}" /></c:if>
                      </div>
                  </section>

                  <section class="field-row verfiedcode-field hide">
                      <label for="" class="input-title">手机动态验证码</label>
                      <div class="input-group verfied-code">
                          <input type="text" class="form-control">
                          <span class="input-group-btn">
                              <button class="btn btn-default btn-primary btn-outline" type="button">发送验证码</button>
                          </span>
                      </div>
                      <div class="tip"></div>
                  </section>

                  <section class="field-row">
                      <!-- <label for="" class="input-title remember-pwd"><input type="checkbox" name="" id="">&ensp;记住动态密码10天</label> -->
                  </section>

                  <button type="submit" id="login" class="btn-login btn btn-primary btn-block">登录</button>

                  <footer class="hide">
                      没有账号？<a href="" class="rightnow-sign">立即注册</a>
                  </footer>
              </form>
          </div>
      </div>
  </div>


  <footer class="web-g-ft login-g-ft">
      <p class="abouts">
          <a href="" class="link">关于</a>
          -
          <a href="" class="link">联系我们</a>
          -
          <a href="" class="link">平台使用协议</a>
      </p>
      <p class="copyright">
          © 2015 杭州随地付网络技术有限公司 版权所有&emsp;&emsp;浙ICP备14021039号-1&emsp;&emsp;增值电信业务许可证：浙B2-20140026
      </p>
  </footer>


  <%@ include file="/WEB-INF/include/script.jsp"%>
  <script src="${ctx.resource}/js/bootstrap.validate.js"></script>
  <script src="${ctx.resource}/js/bootstrap.validate.en.js"></script>
  <script src="${ctx.resource}/js/bootstrap.validate.trigger.js"></script>

  <script>
    (function () {
      var tips=[
        '帐号不能为空',
        '邮箱错误，请重新输入',
        '邮箱格式不正确',
        '该邮箱绑定的手机号：139***6235',
        '密码错误，请重新输入',
        '密码不能为空',
        '账户名或密码错误，请重新输入!<br>您还有3次机会，您还可以：<a href="#">重置密码</a>'
      ];

      var $username=$("#username"),
          $userpwd=$("#userpwd"),
          submitUrl="http://www.baidu.com";

      $(".login-form .input-group-addon .icon-input-cancel").on("click", function (e) {
        $username.val("").focus();
      });

      $("#login").on("click", function (e) {
         if(!check()) {
           e.preventDefault();
           return;
         }

      })

      function check () {
        var isUsernameRight=true,
            isUserpwdRight=true;
        /* if(!isEmail($username.val()) && !isMobilePhone($username.val())){
          showTip($(".username-field"),tips[1]);
          isUsernameRight=false;
        }else{
          hideTip($(".username-field"));
        }  */
        if($.trim($username.val()) == ''){
            showTip($(".username-field"),tips[0]);
            isUsernameRight=false;
          }else{
            hideTip($(".username-field"));
        }

        if(!$userpwd.val()){
          showTip($(".userpwd-field"),tips[5]);
          isUserpwdRight=false;
        }else{
          hideTip($(".userpwd-field"));
        }

        return isUsernameRight && isUserpwdRight;
      }

      function showTip (elem, tipTxt) {
        elem.addClass("has-error").find(".tip").html(tipTxt);
      }
      function hideTip (elem) {
        elem.removeClass("has-error").remove(".tip");
      }

      // 判断是否为邮箱地址
      function isEmail(emailStr) {
          var regex=new RegExp("^[a-zA-Z\\d_-]+@[a-zA-Z\\d_-]+\\.com$");
          return regex.test(emailStr);
      }

      // 判断是否为手机号
      function isMobilePhone(phone) {
          var regex=/^1[3|4|5|8][0-9]\d{4,8}$/;
          return regex.test(phone);
      }

    })();
  </script>
  <script type="text/javascript">	
  	$(function(){
  		$("#username").focus(function(){
  	  		$(".username-field").removeClass("has-error");
  	  	});
  		$("#userpwd").focus(function(){
  	  		$(".userpwd-field").removeClass("has-error");
  	  	});
  		/* $("#username").change(function(){
  			alert("!");
  		  if(!$(this).val()){
  			$("#cancel-icon").css('display','none'); 
  		  }else{
  			$("#cancel-icon").css('display','block');
  		  }
  		}); */
  		/* $('#username').bind('input propertychange', function() {
  		    alert("!");
  		}); */
  	})
  	
  </script>

</body>
</html>

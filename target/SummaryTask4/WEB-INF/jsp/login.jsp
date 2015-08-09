<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 30/07/15
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/logo.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/formRows.css"/>"/>
    <%@include file="../jspf/header/header.jspf" %>
</head>
<body>
<div class="uk-vertical-align uk-text-center uk-height-1-1">

    <div class="uk-vertical-align-middle " style="width: 400px;">
        <img class="uk-margin-bottom logo-login"
             src="<c:url value="/resources/img/mainLogo.png"/>"
             alt="">

        <form class="uk-panel uk-panel-box uk-form" action="auth.do" method="POST">
            <%@include file="../jspf/validations/loginValidation.jspf" %>
            <label id="accountName-label" class="control-label" for="accountName"></label>

            <div class="uk-form-row row-margin">
                <input aria-labelledby="accountName-label" id="accountName" name="accountName"
                       class="uk-width-1-1 uk-form-large uk-form-width-large
                       ${badEmailClass}"
                       type="text" spellcheck="false" placeholder="E-mail"
                       value="${sessionScope.loginAccountName}">
            </div>

            <label id="password-label" class="control-label" for="accountName"></label>

            <div class="uk-form-row uk-form-password row-margin">
                <input aria-labelledby="password-label" id="password" name="password"
                       class="uk-width-1-1 uk-form-large uk-form-width-large
                       ${badPasswordClass}"
                       type="password" autocomplete="off" placeholder="Password"
                       value="${sessionScope.loginIsAccountNameEmpty == true && sessionScope.loginIsPasswordEmpty == false ? sessionScope.loginPassword : ""}">
            </div>

            <div class="uk-form-row">
                <button class="uk-width-1-1 uk-button uk-button-primary uk-button-large" type="submit">Log in</button>
            </div>
            <div class="uk-form-row">
                <a href="<c:url value="/registration.html"/>" class="uk-width-2-3 uk-button uk-button-success">
                    Create Account <i class="uk-icon-external-link"></i>
                </a>
            </div>

            <div class="uk-form-row uk-text-small">
                <a class="uk-link uk-link-muted uk-float-left"
                   href="<c:url value="/resendVerificationOrResetPassword.html?command=verifyAccount"/>">
                    Resent verification message <i class="uk-icon-external-link"></i>
                </a>
                <a class="uk-link uk-link-muted uk-float-right"
                   href="<c:url value="/resendVerificationOrResetPassword.html?command=resetPassword"/>">
                    Forgot Password? <i class="uk-icon-external-link"></i>
                </a>
            </div>
        </form>

    </div>
</div>
<%@include file="../jspf/modals/accountNameRules.jspf" %>
</body>
</html>

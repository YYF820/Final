<%@include file="../jspf/imports.jspf" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/logo.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/formRows.css"/>"/>
    <%@include file="../jspf/header/header.jspf" %>
</head>
<body>
    <%@include file="../jspf/topPanel.jspf"%>
    <div class="uk-container-center uk-text-center uk-width-8-10">
    <div class="uk-container-center uk-text-center uk-width-medium-4-10 uk-margin-large-top">
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
                       type="text" spellcheck="false" placeholder="<fmt:message key="login.field.email"/>"
                       value="${sessionScope.loginAccountName}">
            </div>

            <label id="password-label" class="control-label" for="accountName"></label>

            <div class="uk-form-row uk-form-password row-margin">
                <input aria-labelledby="password-label" id="password" name="password"
                       class="uk-width-1-1 uk-form-large uk-form-width-large
                       ${badPasswordClass}"
                       type="password" autocomplete="off" placeholder="<fmt:message key="login.field.password"/>"
                       value="${sessionScope.loginIsAccountNameEmpty == true && sessionScope.loginIsPasswordEmpty == false ? sessionScope.loginPassword : ""}">
            </div>

            <div class="uk-form-row">
                <button class="uk-width-1-1 uk-button uk-button-primary uk-button-large" type="submit">
                    <fmt:message key="login.submit"/>
                </button>
            </div>
            <div class="uk-form-row">
                <a href="<c:url value="/registration.html"/>" class="uk-width-2-3 uk-button uk-button-success">
                    <fmt:message key="login.create.account"/> <i class="uk-icon-external-link"></i>
                </a>
            </div>

            <div class="uk-form-row uk-text-small">
                <a class="uk-link uk-link-muted uk-float-left"
                   href="<c:url value="/resendVerificationOrResetPassword.html?command=verifyAccount"/>">
                    <fmt:message key="login.resend.verification.message"/> <i class="uk-icon-external-link"></i>
                </a>
                <a class="uk-link uk-link-muted uk-float-right"
                   href="<c:url value="/resendVerificationOrResetPassword.html?command=resetPassword"/>">
                    <fmt:message key="login.forgot.password"/> <i class="uk-icon-external-link"></i>
                </a>
            </div>
        </form>
    </div>
    </div>
<%@include file="../jspf/modals/accountNameRules.jspf" %>
</body>
</html>

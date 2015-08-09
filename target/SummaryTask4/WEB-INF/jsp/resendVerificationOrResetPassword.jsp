<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 03/08/15
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<%@include file="../jspf/topPanel.jspf" %>
<div class="uk-vertical-align uk-text-center uk-height-1-1">

    <div class="uk-vertical-align-middle " style="width: 400px;">

        <img class="uk-margin-bottom logo-login"
             src="<c:url value="/resources/img/mainLogo.png"/>"
             alt="">

        <form class="uk-panel uk-panel-box uk-form" action="resendVerificationOrResetPassword.do" method="POST">
            <div class="uk-alert uk-alert-warning" data-uk-alert>
                <p class="uk-text-middle">Please enter your email.</p>

                <p class="uk-text-middle">We will send you message with verifying link.</p>
            </div>
            <c:if test="${sessionScope.resendIsAccountNameValid == false ||
            sessionScope.resendIsAccountNameEmpty == true || sessionScope.resendIsUserExistsByAccountName == false ||
            sessionScope.resendIsAdminTryingVerifyAccount == true || sessionScope.resendIsActiveAccount == true ||
            sessionScope.resendIsBlockedAccount == true}">
                <c:set scope="page" var="badEmailClass" value="uk-form-danger"/>
                <div class="uk-alert" data-uk-alert>
                    <a href="" class="uk-alert-close uk-close"></a>
                    <c:choose>
                        <c:when test="${sessionScope.resendIsAccountNameEmpty == true}">
                            <p class="uk-text-danger">Please enter your Email.</p>
                        </c:when>
                        <c:when test="${sessionScope.resendIsAccountNameValid == false}">
                            <p class="uk-text-danger">Email is not valid
                                <a href="#email-id" class="uk-text-danger" data-uk-modal="{bgclose:false, center:true}">
                                    <i class="uk-icon-info-circle"></i>
                                </a>
                            </p>
                        </c:when>
                        <c:when test="${sessionScope.resendIsUserExistsByAccountName == false}">
                            <p class="uk-text-danger">Wrong e-mail, we don't have account with this email. </p>
                        </c:when>
                        <c:when test="${sessionScope.resendIsAdminTryingVerifyAccount == true}">
                            <p class="uk-text-danger">The administrator does not need to verify account.</p>
                        </c:when>
                        <c:when test="${sessionScope.resendIsActiveAccount == true}">
                            <p class="uk-text-danger">Your account is already verified,
                                <a href="<c:url value="/login.html"/>" class="uk-text-success">want to log in ?</a>
                            </p>
                        </c:when>
                        <c:when test="${sessionScope.resendIsBlockedAccount == true}">
                            <p class="uk-text-danger">Your account is blocked, contact our administrator.</p>
                        </c:when>
                    </c:choose>

                </div>
            </c:if>
            <label id="accountName-label" class="control-label" for="accountName"></label>

            <div class="uk-form-row row-margin">
                <input aria-labelledby="accountName-label" id="accountName" name="accountName"
                       class="uk-width-1-1 uk-form-large uk-form-width-large
                       ${badEmailClass}"
                       type="text" autocomplete="off" spellcheck="false" placeholder="E-mail"
                       value="${sessionScope.resendAccountName}">

            </div>
            <div class="uk-grid uk-form-row uk-align-center uk-margin-bottom-remove">
                <button class="uk-width-medium-3-5 uk-button uk-button-primary " type="submit">Next</button>
                <a href="<c:url value="/index.html"/>"
                   class="uk-width-medium-1-5 uk-button uk-button-success uk-push-2-10">Cancel</a>
            </div>
            <c:choose>
                <c:when test="${param.command == 'resetPassword'}">
                    <input type="hidden" name="command" value="resetPassword">
                </c:when>
                <c:when test="${param.command== 'verifyAccount'}">
                    <input type="hidden" name="command" value="verifyAccount">
                </c:when>
            </c:choose>
        </form>
    </div>
</div>
<%@include file="../jspf/modals/accountNameRules.jspf" %>
</body>
</html>

<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 02/08/15
  Time: 19:49
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
<div class="uk-container uk-container-center uk-width-8-10 uk-text-center uk-margin-top ">
    <div class="uk-grid uk-align-center uk-width-small-1-2 uk-margin-large-top">
        <c:if test="${sessionScope.isVerifiedAccount == true}">
            <p class="uk-text-success uk-text-large uk-text-middle"><i
                    class="uk-icon-check uk-icon-large uk-text-success"></i>&nbspThank you! Your email verification is
                complete!</p>

            <div class="uk-margin-top uk-text-left">
                <p class="">Your e-mail has been verified & your account is active. You can now view pages for budgetEntrants
                    and
                    your profile!</p>

                <p>On your profile you can:</p>
                <ul class="uk-list uk-list-line uk-list-space">
                    <li class="uk-margin-left">See your marks.</li>
                    <li class="uk-margin-left">Add scan for your certificate.</li>
                    <li class="uk-margin-left">In future you will be able to see all budgetEntrants information and who and
                        who
                        went to university on a budget.
                    </li>
                </ul>
                <p class="uk-text-success">Click the button below to return to Account Management, where you can try new
                    things.</p>
                <a class="uk-button uk-button-primary uk-width-8-10 uk-align-center"
                   href="<c:url value="/login.html"/>">Continue to
                    Account
                    Management</a>
            </div>
        </c:if>
        <c:if test="${sessionScope.isVerifiedAccount == false || sessionScope.isVerifiedAccount == null}">

            <p class="uk-text-danger uk-text-large uk-text-middle"><i
                    class="uk-icon-exclamation-triangle uk-icon-large uk-text-danger"></i>&nbspAccount Verification
                Error</p>

            <div class="uk-margin-top uk-text-left">
                <p class="">An error has occurred, possibly for one of the following reasons:</p>
                <ul class="uk-list uk-list-line uk-list-space">
                    <li class="uk-margin-left">This account has already been verified.</li>
                    <li class="uk-margin-left">The verification link you clicked on is expired or invalid.</li>
                    <li class="uk-margin-left">The account verification attempt was unsuccessful. Please try again, as
                        this may be a temporary
                        issue.
                    </li>
                </ul>
                <p class="uk-text-danger">Click the button below to return to Account Management, where you can request
                    another copy of the
                    verification e-mail.</p>
                <a class="uk-button uk-button-primary uk-width-8-10 uk-align-center"
                   href="<c:url value="/login.html"/>">Continue to
                    Account Management</a>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>

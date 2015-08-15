<%@include file="../jspf/imports.jspf" %>
<html>
<head>
    <%@include file="../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<%@include file="../jspf/topPanel.jspf" %>
<div class="uk-container uk-container-center uk-width-9-10 uk-text-center uk-margin-top ">
    <div class="uk-grid uk-align-center uk-width-small-1-2 uk-margin-large-top">
        <c:if test="${sessionScope.verifyAccountIsMessageSent == true}">
            <p class="uk-text-success uk-text-large uk-text-middle"><i
                    class="uk-icon-check uk-icon-large uk-text-success"></i>&nbsp
                <fmt:message key="registration.message.sent.success.subject.message"/>
            </p>

            <div class="uk-margin-top uk-text-left">
                <p class="uk-text"><fmt:message key="registration.message.sent.success.body.message"/></p>
                <a class="uk-button uk-button-primary uk-width-8-10 uk-align-center" href="login.html">
                    <fmt:message key="registration.message.sent.success.button"/>
                </a>
            </div>
        </c:if>
        <c:if test="${sessionScope.verifyAccountIsMessageSent == false || sessionScope.verifyAccountIsMessageSent == null}">
            <p class="uk-text-danger uk-text-large uk-text-middle"><i
                    class="uk-icon-exclamation-triangle uk-icon-large uk-text-danger"></i>&nbspWe couldn't send you
                verification email.</p>

            <div class="uk-margin-top uk-text-left">
                <p class="">An error has occurred, possibly for one of the following reasons:</p>
                <ul class="uk-list uk-list-line uk-list-space">
                    <li class="uk-margin-left">Problems with our servers.</li>
                    <li class="uk-margin-left">Problems with the servers, the company that owns your email.</li>
                    <li class="uk-margin-left">The account verification attempt was unsuccessful. Please try again, as
                        this may be a temporary
                        issue.
                    </li>
                </ul>
                <p class="uk-text-danger">Click the button below to return to Account Management, where you can resend
                    verification message.</p>
                <a class="uk-button uk-button-primary uk-width-8-10 uk-align-center" href="login.html">Continue to
                    Account Management</a>
            </div>
        </c:if>

    </div>
</div>
</body>
</html>

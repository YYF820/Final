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
                <fmt:message key="verify.account.message.sent.success.subject"/>
            </p>

            <div class="uk-margin-top uk-text-left">
                <p class="uk-text">
                    <fmt:message key="verify.account.message.sent.success.faq"/>
                </p>
                <a class="uk-button uk-button-primary uk-width-8-10 uk-align-center" href="login.html">
                    <fmt:message key="verify.account.message.sent.button"/>
                </a>
            </div>
        </c:if>
        <c:if test="${sessionScope.verifyAccountIsMessageSent == false || sessionScope.verifyAccountIsMessageSent == null}">
            <p class="uk-text-danger uk-text-large uk-text-left"><i
                    class="uk-icon-exclamation-triangle uk-icon-large uk-text-danger"></i>&nbsp
                <fmt:message key="verify.account.message.sent.error.subject"/>
            </p>

            <div class="uk-margin-top uk-text-left">
                <p class=""><fmt:message key="verify.account.message.sent.error.body.list.start"/></p>
                <ul class="uk-list uk-list-line uk-list-space">
                    <li class="uk-margin-left">
                        <fmt:message key="verify.account.message.sent.error.body.list.first"/>
                    </li>
                    <li class="uk-margin-left">
                        <fmt:message key="verify.account.message.sent.error.body.list.second"/>
                    </li>
                    <li class="uk-margin-left">
                        <fmt:message key="verify.account.message.sent.error.body.list.third"/>
                    </li>
                </ul>
                <p class="uk-text">
                    <fmt:message key="verify.account.message.sent.error.faq"/>
                </p>
                <a class="uk-button uk-button-primary uk-width-8-10 uk-align-center" href="login.html">
                    <fmt:message key="verify.account.message.sent.button"/>
                </a>
            </div>
        </c:if>

    </div>
</div>
</body>
</html>

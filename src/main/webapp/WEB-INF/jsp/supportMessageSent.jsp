<%@include file="../jspf/imports.jspf" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/logo.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/formRows.css"/>"/>
    <%@include file="../jspf/header/header.jspf" %>
</head>
<body>
<%@include file="../jspf/topPanel.jspf" %>
<div class="uk-container-center uk-text-center uk-width-8-10">
    <div class="uk-container-center uk-text-center uk-width-medium-4-10 uk-margin-large-top">
        <c:choose>
            <c:when test="${sessionScope.supportIsMessageSent == true}">
                <p class="uk-text-success uk-text-large uk-text-middle"><i
                        class="uk-icon-check uk-icon-large uk-text-success"></i>&nbsp
                    <fmt:message key="support.message.sent.success.subject"/>
                </p>

                <div class="uk-margin-top uk-text-center">
                    <p class="uk-text">
                        <fmt:message key="support.message.sent.success.faq"/>
                    </p>
                    <a class="uk-button uk-button-primary uk-width-8-10 uk-align-center"
                       href="<c:url value="/index.html"/>">
                        <fmt:message key="support.message.sent.success.button"/>
                    </a>
                </div>
            </c:when>
            <c:when test="${sessionScope.supportIsMessageSent == false}">
                <p class="uk-text-danger uk-text-large uk-text-center"><i
                        class="uk-icon-exclamation-triangle uk-icon-large uk-text-danger"></i>&nbsp
                    <fmt:message key="support.message.sent.error.subject"/>
                </p>

                <div class="uk-margin-top uk-text-left">
                    <p class=""><fmt:message key="support.message.sent.error.body.list.start"/></p>
                    <ul class="uk-list uk-list-line uk-list-space">
                        <li class="uk-margin-left">
                            <fmt:message key="support.message.sent.error.body.list.first"/>
                        </li>
                        <li class="uk-margin-left">
                            <fmt:message key="support.message.sent.error.body.list.third"/>
                        </li>
                    </ul>
                    <p class="uk-text">
                        <fmt:message key="support.message.sent.error.faq"/>
                    </p>
                    <a class="uk-button uk-button-primary uk-width-8-10 uk-align-center"
                       href="<c:url value="/index.html"/>">
                        <fmt:message key="support.message.sent.error.button"/>
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <c:redirect url="/index.html"/>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>

<%@include file="../../jspf/imports.jspf" %>
<html>
<head>
    <%@include file="../../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<%@include file="../../jspf/topPanel.jspf" %>
<div class="uk-container-center uk-text-center uk-width-8-10">
    <div class="uk-container-center uk-text-center uk-width-medium-4-10 uk-margin-large-top">
        <p class="uk-text-danger uk-text-large uk-text-middle"><i
                class="uk-icon-exclamation-triangle uk-icon-large uk-text-danger"></i>&nbsp
            <fmt:message key="reset.password.session.invalidated.subject"/>
        </p>

        <div class="uk-margin-top uk-text-left">
            <p class="uk-text-danger">
                <fmt:message key="reset.password.session.invalidated.faq"/>
            </p>
            <a class="uk-button uk-button-primary uk-width-1-1 uk-align-center" href="<c:url value="/login.html"/>">
                <fmt:message key="button.continue.to.account.management"/>
            </a>
        </div>
    </div>
</div>
</body>
</html>

<%@include file="../../jspf/imports.jspf" %>
<html>
<head>
    <%@include file="../../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<%@ include file="../../jspf/topPanel.jspf" %>
<div class="uk-container uk-container-center uk-margin-large-bottom uk-animation-fade">
    <%@ include file="../../jspf/logo.jspf" %>
    <%@ include file="../../jspf/navAndLogin.jspf" %>
    <div class="uk-margin-large-top uk-text-center uk-align-center">
        <p class="uk-text-warning uk-text-large uk-text-align-center"><i
                class="uk-icon-exclamation-triangle uk-icon-large uk-text-warning"></i>&nbsp
            <fmt:message key="401.error.page.header"/>
        </p>
    </div>

    <div class="uk-margin-top uk-margin-large-bottom uk-text-center uk-align-center">
        <p class="uk-text-danger">
            <fmt:message key="401.error.page.body"/>
        </p>
    </div>
    <%@ include file="../../jspf/footer.jspf" %>
</div>
</body>
</html>

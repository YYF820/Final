<%@include file="../../jspf/imports.jspf" %>
<html>
<head>
    <%@include file="../../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<%@include file="../../jspf/topPanel.jspf" %>
<c:choose>
    <c:when test="${sessionScope.resetPasswordIsUpdatedPassword == true}">
        <div class="uk-container-center uk-text-center uk-width-8-10">
            <div class="uk-container-center uk-text-center uk-width-medium-4-10 uk-margin-large-top">
                <p class="uk-text-success uk-text-large uk-text-middle">
                    <i class="uk-icon-check uk-icon-large uk-text-success"></i>&nbsp
                    <fmt:message key="reset.password.success.subject"/>
                    <br/>
                    <a href="<c:url value="/login.html"/>"
                       class="uk-text-success uk-text-large uk-align-center uk-text-center ">
                        <fmt:message key="reset.password.success.want.to.login"/>
                        <i class="uk-icon-external-link"></i>
                    </a>
                </p>
            </div>
        </div>
    </c:when>
    <c:when test="${requestScope.isUpdatedPassword == null}">
        <c:redirect url="login.html"/>
    </c:when>
</c:choose>
</body>
</html>

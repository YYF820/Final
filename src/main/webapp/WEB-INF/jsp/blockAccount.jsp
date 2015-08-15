<%@include file="../jspf/imports.jspf" %>
<html>
<head>
    <%@include file="../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<%@include file="../jspf/topPanel.jspf" %>
<div class="uk-container uk-container-center uk-width-8-10 uk-text-center uk-margin-top ">
    <c:choose>
        <c:when test="${sessionScope.checkQuestionIsBlockedAccount == true}">
            <p class="uk-text-exclamation-triangle uk-text-large uk-text-middle uk-text-danger"><i
                    class="uk-icon-check uk-icon-large uk-text-danger"></i>&nbspYou wrote wrong code 3 times, we
                blocked account, contact our support.</p>
            <a href="<c:url value="/index.html"/>">
                <p class="uk-text-large uk-text-middle">
                    Back to home page <i class="uk-icon-external-link"></i>
                </p>
            </a>
        </c:when>
        <c:otherwise>
            <c:redirect url="/index.html"/>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>

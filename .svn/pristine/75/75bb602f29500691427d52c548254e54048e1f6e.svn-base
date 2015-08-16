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
    <%@ include file="../../jspf/content.jspf" %>
    <%@ include file="../../jspf/entrantInfoMain.jspf" %>
    <%@ include file="../../jspf/footer.jspf" %>
    <p>В вашей корзине
        <c:if test="${sessionScope.counter != null}">
            <c:choose>
                <c:when test="${sessionScope.counter == 1}">
                    1 сладость
                </c:when>
                <c:when test="${sessionScope.counter == 2}">
                    2 сладости
                </c:when>
                <c:otherwise>
                    ${sessionScope.counter} сладостей
                </c:otherwise>
            </c:choose>
        </c:if>
        на сумму
        ${sessionScope.money != null ? sessionScope.money.concat(' грн') : '0 грн'}
    </p>
</div>
</body>
</html>

<%@include file="../../jspf/imports.jspf" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/logo.css"/>"/>
    <%@include file="../../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<c:if test="${sessionScope.entrantAccountSettingsIsSubjectsToAdd == null}">
    <c:redirect url="/accountSettings.html"/>
</c:if>
<%@include file="../../jspf/topPanel.jspf" %>
<div class="uk-container uk-container-center uk-width-medium-5-10 uk-margin-top uk-text-center">
    <img class="uk-margin-bottom logo-login uk-align-center"
         src="<c:url value="/resources/img/mainLogo.png"/>"
         alt="">

    <div class="uk-alert uk-alert-warning">
        <p class="uk-h3"><fmt:message key="account.settings.add.marks.warning"/></p>
    </div>

    <c:if test="${sessionScope.entrantAccountSettingsIsEmptyMarks == true ||
                sessionScope.entrantAccountSettingsIsValidMarks == false }">
        <div class="uk-alert uk-alert-danger uk-text-center uk-animation-fade">
            <c:if test="${sessionScope.entrantAccountSettingsIsEmptyMarks == true}">
                <p><fmt:message key="account.settings.add.marks.empty.marks"/></p>
            </c:if>
            <c:if test="${sessionScope.entrantAccountSettingsIsValidMarks == false}">
                <p><fmt:message key="account.settings.add.marks.not.valid.marks"/></p>
            </c:if>
        </div>
    </c:if>

    <form class="uk-form" method="post" action="<c:url value="/entrant/addSubjectsMarks.do"/>">
        <div class="uk-form-controls uk-form-controls-text uk-container uk-container-center
                uk-width-5-5 uk-margin-top">
            <c:forEach var="subject" items="${sessionScope.entrantAccountSettingsIsSubjectsToAdd}" varStatus="counter">
                <div class="uk-form-row row-margin">
                    <input name="${subject.name}"
                           class="uk-width-1-1 uk-form-large uk-form-width-medium"
                           type="text" spellcheck="false" placeholder="<fmt:message key="${subject.name}"/>"
                           value="${counter.index == 0 && sessionScope.entrantAccountSettingsMarkFirst != null ? sessionScope.entrantAccountSettingsMarkFirst :
                                            counter.index == 1 && sessionScope.entrantAccountSettingsMarkSecond != null ? sessionScope.entrantAccountSettingsMarkSecond :
                                                counter.index == 2 && sessionScope.entrantAccountSettingsMarkThird != null ? sessionScope.entrantAccountSettingsMarkThird :
                                                    ''}">
                </div>
            </c:forEach>
        </div>
        <div class="uk-grid uk-width-4-5 uk-form-row uk-container-center uk-margin-bottom-remove uk-margin-top">
            <button class="uk-width-medium-3-5 uk-button uk-button-primary" type="submit">
                <fmt:message key="button.save"/>
            </button>
            <a href="<c:url value="/accountSettings.html"/>"
               class="uk-width-medium-1-5 uk-button uk-button-success uk-push-2-10">
                <fmt:message key="button.cancel"/>
            </a>
        </div>
    </form>
</div>

</body>
</html>

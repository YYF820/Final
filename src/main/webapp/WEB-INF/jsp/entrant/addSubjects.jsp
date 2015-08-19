<%@include file="../../jspf/imports.jspf" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/logo.css"/>"/>
    <%@include file="../../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<%@include file="../../jspf/topPanel.jspf" %>
<div class="uk-container-center uk-text-center uk-width-8-10">
    <div class="uk-container-center uk-text-center uk-width-medium-4-10 uk-margin-large-top">
        <img class="uk-margin-large-bottom uk-margin-top"
             src="<c:url value="/resources/img/mainLogoLogin.png"/>"
             alt="">

        <div class="uk-alert uk-alert-warning uk-text-center">
            <p class="uk-h3">
                <fmt:message key="account.settings.add.subjects.warning"/>
            </p>
        </div>
        <form class="uk-form" method="post" action="<c:url value="/entrant/prepareInfoForAddMarks.do"/>">
            <c:if test="${sessionScope.entrantAccountSettingsIsEmptyForm == true ||
            sessionScope.entrantAccountSettingsIsCorrectNumberOfSubjects == false}">
                <div class="uk-alert uk-alert-danger uk-text-center uk-animation-fade">
                    <p><fmt:message key="account.settings.add.subjects.choose.three.subjects"/></p>
                </div>
            </c:if>
            <div class="uk-form-controls uk-form-controls-text uk-align-center
                uk-width-2-5 uk-text-left uk-margin-top">
                <c:forEach var="subject" items="${sessionScope.entrantAccountSettingsAllSubjects}" varStatus="counter">
                    <label class="uk-form-label ">
                        <input type="checkbox" name="subjectsIdToAdd" value="${subject.id}">
                        <fmt:message key="${subject.name}"/>
                    </label>
                    <br/>
                </c:forEach>
            </div>
            <div class="uk-grid uk-form-row uk-align-center uk-margin-bottom-remove uk-margin-top">
                <button class="uk-width-medium-3-5 uk-button uk-button-primary " type="submit">
                    <fmt:message key="button.next"/>
                </button>
                <a href="<c:url value="/accountSettings.html"/>"
                   class="uk-width-medium-1-5 uk-button uk-button-success uk-push-2-10">
                    <fmt:message key="button.cancel"/>
                </a>
            </div>
        </form>
    </div>
</div>
</body>
</html>

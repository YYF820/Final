<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 10/08/15
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <p class="uk-h3">Enter your marks in the appropriate fields</p>
    </div>

    <c:if test="${sessionScope.entrantAccountSettingsIsEmptyMarks == true ||
                sessionScope.entrantAccountSettingsIsValidMarks == false }">
        <div class="uk-alert uk-alert-danger uk-text-center uk-animation-fade">
            <c:if test="${sessionScope.entrantAccountSettingsIsEmptyMarks == true}">
                <p>Please enter all marks.</p>
            </c:if>
            <c:if test="${sessionScope.entrantAccountSettingsIsValidMarks == false}">
                <p>Not valid marks, mark should be 100-200 and can has dot and 2 more digits example: 155.5 or
                    155.55</p>
            </c:if>
        </div>
    </c:if>

    <form class="uk-form" method="post" action="<c:url value="/entrant/addSubjectsMarks.do"/>">
        <div class="uk-form-controls uk-form-controls-text uk-container uk-container-center
                uk-width-5-5 uk-margin-top">
            <c:forEach var="subject" items="${sessionScope.entrantAccountSettingsIsSubjectsToAdd}" varStatus="counter">
                <div class="uk-form-row row-margin">
                    <input name="${subject.name}" class="uk-width-1-1 uk-form-large uk-form-width-medium"
                           type="text" spellcheck="false" placeholder="${subject.name} :"
                           value="${counter.index == 0 && sessionScope.entrantAccountSettingsMarkFirst != null ? sessionScope.entrantAccountSettingsMarkFirst :
                                            counter.index == 1 && sessionScope.entrantAccountSettingsMarkSecond != null ? sessionScope.entrantAccountSettingsMarkSecond :
                                                counter.index == 2 && sessionScope.entrantAccountSettingsMarkThird != null ? sessionScope.entrantAccountSettingsMarkThird :
                                                    ''}">
                </div>
            </c:forEach>
        </div>
        <div class="uk-grid uk-width-4-5 uk-form-row uk-container-center uk-margin-bottom-remove uk-margin-top">
            <button class="uk-width-medium-3-5 uk-button uk-button-primary" type="submit">Save</button>
            <a href="<c:url value="/accountSettings.html"/>"
               class="uk-width-medium-1-5 uk-button uk-button-success uk-push-2-10">Cancel</a>
        </div>
    </form>
</div>

</body>
</html>
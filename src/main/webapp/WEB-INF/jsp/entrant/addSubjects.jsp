<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 10/08/15
  Time: 15:00
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
<%@include file="../../jspf/topPanel.jspf" %>
<div class="uk-container uk-container-center uk-width-medium-4-10 uk-margin-top ">

    <img class="logo-login uk-align-center"
         src="<c:url value="/resources/img/mainLogo.png"/>"
         alt="">

    <div class="uk-alert uk-alert-warning uk-text-center">
        <p class="uk-h3">Select the subjects for which you have the highest scores</p>
    </div>
    <form class="uk-form" method="post" action="<c:url value="/entrant/prepareInfoForAddMarks.do"/>">
        <c:if test="${sessionScope.entrantAccountSettingsIsEmptyForm == true ||
            sessionScope.entrantAccountSettingsIsCorrectNumberOfSubjects == false}">
            <div class="uk-alert uk-alert-danger uk-text-center uk-animation-fade">
                <p>Please choose 3 subjects.</p>
            </div>
        </c:if>
        <div class="uk-form-controls uk-form-controls-text uk-container uk-container-center
                uk-text-left uk-width-2-5 uk-margin-top">
            <c:forEach var="subject" items="${sessionScope.entrantAccountSettingsAllSubjects}" varStatus="counter">
                <label class="uk-form-label">
                    <input type="checkbox" name="subjectsIdToAdd" value="${subject.id}"> ${subject.name}
                </label>
                <br/>
            </c:forEach>
        </div>
        <div class="uk-grid uk-form-row uk-align-center uk-margin-bottom-remove uk-margin-top">
            <button class="uk-width-medium-3-5 uk-button uk-button-primary " type="submit">Next</button>
            <a href="<c:url value="/accountSettings.html"/>"
               class="uk-width-medium-1-5 uk-button uk-button-success uk-push-2-10">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>

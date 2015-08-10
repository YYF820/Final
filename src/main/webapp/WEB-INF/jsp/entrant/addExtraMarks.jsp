<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 11/08/15
  Time: 00:11
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
<c:if test="${sessionScope.entrantAccountSettingsNoExtraMarks == null}">
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

    <c:if test="${sessionScope.entrantAccountSettingsExtraMarksIsEmptyFields == true}">
        <div class="uk-alert uk-alert-danger uk-text-center uk-animation-fade">
            <c:if test="${sessionScope.entrantAccountSettingsExtraMarksIsEmptyFields == true}">
                <p>Please fill all fields.</p>
            </c:if>
        </div>
    </c:if>

    <form class="uk-form" method="post" action="<c:url value="/entrant/addExtraMarks.do"/>">
        <div class="uk-grid ">
            <div class="uk-form-row uk-width-medium-3-5
            ${sessionScope.entrantAccountSettingsExtraMarksIsValidCertificatePoints == true ?
                'uk-push-3-10' : sessionScope.entrantAccountSettingsExtraMarksIsValidCertificatePoints == null ?
                    'uk-push-3-10' : ''}">
                <input name="certificatePoints" class=" uk-form-large uk-width-medium-3-5 uk-pull-2-10
                ${sessionScope.entrantAccountSettingsExtraMarksIsValidCertificatePoints == false ?
                    'uk-form-danger' : sessionScope.entrantAccountSettingsExtraMarksIsValidCertificatePoints == true ?
                        'uk-form-success' : ''}"
                       type="text" spellcheck="false" placeholder="Certificate points:"
                       value="${sessionScope.entrantAccountSettingsExtraMarksCertificatePoints}">
            </div>
            <c:if test="${sessionScope.entrantAccountSettingsExtraMarksIsValidCertificatePoints == false &&
                            sessionScope.entrantAccountSettingsExtraMarksCertificatePoints != ''}">
                <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-width-medium-2-5">
                    Certificate points not valid, 30-60 you can use dot example 30.50 or 60.
                </div>
            </c:if>
        </div>
        <div class="uk-grid uk-margin-top">
            <div class="uk-form-row uk-width-medium-3-5
            ${sessionScope.entrantAccountSettingsExtraMarksIsValidExtraPoints == true ?
                'uk-push-3-10' : sessionScope.entrantAccountSettingsExtraMarksIsValidExtraPoints == null ?
                    'uk-push-3-10' : ''}">
                <input name="extraPoints" class=" uk-form-large uk-width-medium-3-5 uk-pull-2-10
                ${sessionScope.entrantAccountSettingsExtraMarksIsValidExtraPoints == false ?
                    'uk-form-danger' : sessionScope.entrantAccountSettingsExtraMarksIsValidExtraPoints == true ?
                        'uk-form-success' : ''} "
                       type="text" spellcheck="false" placeholder="Extra points:"
                       value="${sessionScope.entrantAccountSettingsExtraMarksExtraPoints}">
            </div>
            <c:if test="${sessionScope.entrantAccountSettingsExtraMarksIsValidExtraPoints == false &&
                            sessionScope.entrantAccountSettingsExtraMarksExtraPoints != ''}">
                <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-width-medium-2-5">
                    Certificate points not valid, 0-20 you can use dot example 10.50, 12, 20.
                </div>
            </c:if>
        </div>
        <div class="uk-grid uk-width-medium-1-1 uk-form-row uk-container-center uk-margin-bottom-remove uk-margin-top">
            <button class="uk-width-medium-3-5 uk-button uk-button-primary" type="submit">Save</button>
            <a href="<c:url value="/accountSettings.html"/>"
               class="uk-width-medium-1-5 uk-button uk-button-success uk-push-2-10">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>

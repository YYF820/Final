<%@include file="../jspf/imports.jspf" %>
<html>
<head>
    <%@include file="../jspf/header/header.jspf" %>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/logo.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/formRows.css"/>"/>
    <title></title>
</head>
<body>
<%@include file="../jspf/topPanel.jspf" %>
<div class="uk-container uk-container-center uk-width-8-10 uk-text-center uk-margin-top ">

    <img class="uk-margin-bottom logo-login"
         src="<c:url value="/resources/img/mainLogo.png"/>"
         alt="">

    <div class="uk-grid uk-align-center uk-width-small-9-10">
        <div class="uk-width-1-3 ${sessionScope.registrationIsFirstNameEmpty == false && sessionScope.registrationIsFirstNameValid == true ? 'uk-push-1-3' : ''}">
            <c:choose>
                <c:when test="${sessionScope.registrationIsLastNameEmpty == false && sessionScope.registrationIsLastNameValid == true}">
                    <c:set scope="page" var="lastNameSuccessClass" value="uk-form-success"/>
                </c:when>
                <c:when test="${sessionScope.registrationIsLastNameEmpty == true || sessionScope.registrationIsLastNameValid == false}">
                    <div class="uk-alert uk-width-1-1 uk-animation-fade" data-uk-alert>
                        <c:choose>
                            <c:when test="${sessionScope.registrationIsLastNameEmpty == true}">
                                <c:set scope="page" var="lastNameSuccessClass" value="uk-form-danger"/>
                                <p class="uk-text-danger"><fmt:message
                                        key="registration.error.empty.last.name"/></p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsLastNameValid == false}">
                                <c:set scope="page" var="lastNameSuccessClass" value="uk-form-danger"/>
                                <p class="uk-text-danger">
                                    <fmt:message key="registration.error.not.valid.last.name"/>
                                </p>

                                <p class="uk-text-left">
                                    <fmt:message key="registration.error.explanation.not.valid.last.name"/>
                                </p>
                            </c:when>
                        </c:choose>
                    </div>
                </c:when>
            </c:choose>
        </div>
        <div class="uk-width-1-3">
            <c:choose>
                <c:when test="${sessionScope.registrationIsFirstNameEmpty == false && sessionScope.registrationIsFirstNameValid == true}">
                    <c:set scope="page" var="firstNameSuccessClass" value="uk-form-success"/>
                </c:when>
                <c:when test="${sessionScope.registrationIsFirstNameEmpty == true || sessionScope.registrationIsFirstNameValid == false}">
                    <div class="uk-alert uk-width-1-1 uk-animation-fade" data-uk-alert>
                        <c:choose>
                            <c:when test="${sessionScope.registrationIsFirstNameEmpty == true}">
                                <c:set scope="page" var="firstNameSuccessClass" value="uk-form-danger"/>
                                <p class="uk-text-danger"><fmt:message
                                        key="registration.error.empty.first.name"/></p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsFirstNameValid == false}">
                                <c:set scope="page" var="firstNameSuccessClass" value="uk-form-danger"/>
                                <p class="uk-text-danger">
                                    <fmt:message key="registration.error.not.valid.first.name"/>
                                </p>

                                <p class="uk-text-left">
                                    <fmt:message key="registration.error.explanation.not.valid.first.name"/>
                                </p>
                            </c:when>
                        </c:choose>
                    </div>
                </c:when>
            </c:choose>
        </div>
        <div class="uk-width-1-3
        ${(sessionScope.registrationIsLastNameEmpty == false && sessionScope.registrationIsLastNameValid == true) && (sessionScope.registrationIsFirstNameEmpty == false && sessionScope.registrationIsFirstNameValid == true) ? 'uk-push-2-3' : ''}
        ${sessionScope.registrationIsFirstNameEmpty == false && sessionScope.registrationIsFirstNameValid == true ? 'uk-push-1-3' : ''}
        ${sessionScope.registrationIsLastNameEmpty == false && sessionScope.registrationIsLastNameValid == true ? 'uk-push-1-3' : ''}">
            <c:choose>
                <c:when test="${sessionScope.registrationIsPatronymicEmpty == false && sessionScope.registrationIsPatronymicValid == true}">
                    <c:set scope="page" var="patronymicSuccessClass" value="uk-form-success"/>
                </c:when>
                <c:when test="${sessionScope.registrationIsPatronymicEmpty == true || sessionScope.registrationIsPatronymicValid == false}">
                    <div class="uk-alert uk-width-1-1 uk-animation-fade" data-uk-alert>
                        <c:choose>
                            <c:when test="${sessionScope.registrationIsPatronymicEmpty == true}">
                                <c:set scope="page" var="patronymicSuccessClass" value="uk-form-danger"/>
                                <p class="uk-text-danger"><fmt:message
                                        key="registration.error.empty.patronymic"/></p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsPatronymicValid == false}">
                                <c:set scope="page" var="patronymicSuccessClass" value="uk-form-danger"/>
                                <p class="uk-text-danger">
                                    <fmt:message key="registration.error.not.valid.patronymic"/>
                                </p>

                                <p class="uk-text-left">
                                    <fmt:message key="registration.error.explanation.not.valid.patronymic"/>
                                </p>
                            </c:when>
                        </c:choose>
                    </div>
                </c:when>
            </c:choose>
        </div>
    </div>
    <form class="uk-form" action="<c:url value="/register.do"/>" method="POST">
        <div class="uk-grid uk-align-center uk-width-small-9-10">
            <div class="uk-width-1-3 ">
                <label id="lastName-label " class="control-label" for="lastName"></label>
                <input aria-labelledby="lastName-label" id="lastName" name="lastName"
                       class="uk-width-1-1 uk-form-large uk-form-width-large ${pageScope.lastNameSuccessClass}"
                       type="text" spellcheck="false" autocomplete="off" value="${sessionScope.registrationLastName}"
                       placeholder="<fmt:message key="registration.field.last.name"/>">
            </div>
            <div class="uk-width-1-3 ">
                <label id="firstName-label" class="control-label" for="firstName"></label>
                <input aria-labelledby="firstName-label" id="firstName" name="firstName"
                       class="uk-width-1-1 uk-form-large uk-form-width-large ${pageScope.firstNameSuccessClass}"
                       type="text" spellcheck="false" autocomplete="off" value="${sessionScope.registrationFirstName}"
                       placeholder="<fmt:message key="registration.field.first.name"/>">
            </div>
            <div class="uk-width-1-3 ">
                <label id="patronymic-label" class="control-label" for="patronymic"></label>
                <input aria-labelledby="patronymic-label" id="patronymic" name="patronymic"
                       class="uk-width-1-1 uk-form-large uk-form-width-large ${pageScope.patronymicSuccessClass}"
                       type="text" spellcheck="false" autocomplete="off" value="${sessionScope.registrationPatronymic}"
                       placeholder="<fmt:message key="registration.field.patronymic"/>">
            </div>
        </div>


        <div class="uk-grid uk-align-center uk-margin-bottom-remove uk-margin-top">
            <div class="uk-width-medium-1-2 ">
                <label id="city-label" class="control-label" for="city"></label>
                <input aria-labelledby="city-label" id="city" name="city"
                       class="uk-width-1-1
                       ${(sessionScope.registrationIsCityEmpty == false && sessionScope.registrationIsCityValid == true) ||
                       (sessionScope.registrationIsCityEmpty == null && sessionScope.registrationIsCityValid == null) ? 'uk-push-1-2' : ''}
                       uk-form-large uk-form-width-large
                       ${sessionScope.registrationIsCityEmpty == true || sessionScope.registrationIsCityValid == false ? 'uk-form-danger' : ''}
                       ${sessionScope.registrationIsCityEmpty == false && sessionScope.registrationIsCityValid == true ? 'uk-form-success' : ''}"
                       type="text" value="${sessionScope.registrationCity}" autocomplete="off"
                       placeholder="<fmt:message key="registration.field.city"/>">
            </div>
            <c:if test="${sessionScope.registrationIsCityEmpty == true || sessionScope.registrationIsCityValid == false}">
                <div class="uk-width-medium-1-2 uk-animation-fade ">
                    <div class="uk-alert uk-width-1-1 " data-uk-alert>
                        <c:choose>
                            <c:when test="${sessionScope.registrationIsCityEmpty == true}">
                                <p class="uk-text-danger"><fmt:message key="registration.error.empty.city"/></p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsCityValid == false}">
                                <p class="uk-text-danger">
                                    <fmt:message key="registration.error.not.valid.city"/>
                                </p>

                                <p class="uk-text-center">
                                    <fmt:message key="registration.error.explanation.not.valid.city"/>
                                </p>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:if>
        </div>

        <div class="uk-grid uk-align-center  uk-margin-bottom-remove uk-margin-top">
            <div class="uk-width-medium-1-2 ">
                <label id="region-label" class="control-label" for="region"></label>
                <input aria-labelledby="region-label" id="region" name="region"
                       class="uk-width-1-1
                       ${(sessionScope.registrationIsRegionEmpty == false && sessionScope.registrationIsRegionValid == true) ||
                       (sessionScope.registrationIsRegionEmpty == null && sessionScope.registrationIsRegionValid == null) ? 'uk-push-1-2' : ''}
                       uk-form-large uk-form-width-large
                       ${sessionScope.registrationIsRegionEmpty == true || sessionScope.registrationIsRegionValid == false ? 'uk-form-danger' : ''}
                       ${sessionScope.registrationIsRegionEmpty == false && sessionScope.registrationIsRegionValid == true ? 'uk-form-success' : ''}"
                       type="text" value="${sessionScope.registrationRegion}" autocomplete="off"
                       placeholder="<fmt:message key="registration.field.region"/>">
            </div>
            <c:if test="${sessionScope.registrationIsRegionEmpty == true || sessionScope.registrationIsRegionValid == false}">
                <div class="uk-width-medium-1-2 uk-animation-fade ">
                    <div class="uk-alert uk-width-1-1 " data-uk-alert>
                        <c:choose>
                            <c:when test="${sessionScope.registrationIsRegionEmpty == true}">
                                <p class="uk-text-danger"><fmt:message
                                        key="registration.error.empty.region"/></p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsRegionValid == false}">
                                <p class="uk-text-danger">
                                    <fmt:message key="registration.error.not.valid.region"/>
                                </p>

                                <p class="uk-text-center-small">
                                    <fmt:message key="registration.error.explanation.not.valid.region"/>
                                </p>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:if>
        </div>

        <div class="uk-grid uk-align-center uk-margin-bottom-remove uk-margin-top">
            <div class="uk-width-medium-1-2 ">
                <label id="accountName-label" class="control-label" for="accountName"></label>
                <input aria-labelledby="accountName-label" id="accountName" name="accountName"
                       class="uk-width-1-1
                       ${(sessionScope.registrationIsAccountNameEmpty == false && sessionScope.registrationIsAccountNameValid == true) && (sessionScope.registrationIsAccountNameExists == false || sessionScope.registrationIsAccountNameExists == null) ||
                       (sessionScope.registrationIsAccountNameEmpty == null && sessionScope.registrationIsAccountNameValid == null && sessionScope.registrationIsAccountNameExists == null) ? 'uk-push-1-2' : ''}
                       uk-form-large uk-form-width-large
                       ${sessionScope.registrationIsAccountNameEmpty == true || sessionScope.registrationIsAccountNameValid == false || sessionScope.registrationIsAccountNameExists == true ? 'uk-form-danger' : ''}
                       ${sessionScope.registrationIsAccountNameEmpty == false && sessionScope.registrationIsAccountNameValid == true && (sessionScope.registrationIsAccountNameExists == false || sessionScope.registrationIsAccountNameExists == null) ? 'uk-form-success' : ''}"
                       type="text" value="${sessionScope.registrationAccountName}" autocomplete="off"
                       placeholder="<fmt:message key="registration.field.email"/>">
            </div>
            <c:if test="${sessionScope.registrationIsAccountNameEmpty == true ||
            sessionScope.registrationIsAccountNameValid == false ||
            sessionScope.registrationIsAccountNameExists == true}">
                <div class="uk-width-medium-1-2 uk-animation-fade ">
                    <div class="uk-alert uk-width-1-1 " data-uk-alert>
                        <c:choose>
                            <c:when test="${sessionScope.registrationIsAccountNameEmpty == true}">
                                <p class="uk-text-danger"><fmt:message key="registration.error.empty.email"/></p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsAccountNameExists == true}">
                                <p class="uk-text-danger">This email is already in use.
                                    <a href="<c:url value="/login.html"/>" class="uk-text-success">Want to log in?</a>
                                </p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsAccountNameValid == false}">
                                <p class="uk-text-danger"><fmt:message key="registration.error.not.valid.email"/>
                                    <a href="#email-id"
                                       data-uk-modal="{center:true}">
                                        <i class="uk-icon-question"></i>
                                    </a>
                                </p>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:if>
        </div>

        <div class="uk-grid uk-align-center uk-margin-bottom-remove uk-margin-top">
            <div class="uk-width-medium-1-2 ">
                <label id="password-label" class="control-label" for="password"></label>
                <input aria-labelledby="password-label" id="password" name="password"
                       class="uk-width-1-1
                       ${(sessionScope.registrationIsPasswordEmpty == false && sessionScope.registrationIsPasswordValid == true) ||
                       (sessionScope.registrationIsPasswordEmpty == null && sessionScope.registrationIsPasswordValid == null) ? 'uk-push-1-2' : ''}
                       uk-form-large uk-form-width-large
                       ${sessionScope.registrationIsPasswordEmpty == true || sessionScope.registrationIsPasswordValid == false ? 'uk-form-danger' : ''}
                       ${sessionScope.registrationIsPasswordEmpty == false && sessionScope.registrationIsPasswordValid == true ? 'uk-form-success' : ''} "
                       type="password" autocomplete="off" value="${sessionScope.registrationPassword}"
                       placeholder="<fmt:message key="registration.field.password"/>">


            </div>
            <c:if test="${sessionScope.registrationIsPasswordEmpty == true || sessionScope.registrationIsPasswordValid == false}">
                <div class="uk-width-medium-1-2 uk-animation-fade ">
                    <div class="uk-alert uk-width-1-1 " data-uk-alert>
                        <c:choose>
                            <c:when test="${sessionScope.registrationIsPasswordEmpty == true}">
                                <c:set scope="page" var="passwordSuccessClass" value="uk-form-danger"/>
                                <p class="uk-text-danger"><fmt:message
                                        key="registration.error.empty.password"/></p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsPasswordValid == false}">
                                <c:set scope="page" var="passwordSuccessClass" value="uk-form-danger"/>
                                <p class="uk-text-danger">
                                    <fmt:message key="registration.error.not.valid.password"/>
                                </p>

                                <p class="uk-text-center-small">
                                    <fmt:message key="explanation.not.valid.password"/>
                                </p>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:if>
        </div>
        <div class="uk-grid uk-align-center uk-margin-bottom-remove uk-margin-top">
            <c:choose>
                <c:when test="${sessionScope.registrationIsSchoolEmpty == false && sessionScope.registrationIsSchoolValid == true}">
                    <c:set scope="page" var="schoolSuccessClass" value="uk-form-success"/>
                </c:when>
                <c:when test="${sessionScope.registrationIsSchoolEmpty == true || sessionScope.registrationIsSchoolValid == false}">
                    <c:set scope="page" var="schoolSuccessClass" value="uk-form-danger"/>
                </c:when>
            </c:choose>
            <div class="uk-width-medium-1-2 ">
                <label id="school-label" class="control-label" for="school"></label>
                <input aria-labelledby="school-label" id="school" name="school"
                       class="uk-form-width-small uk-form-width-small uk-push-1-10 ${pageScope.schoolSuccessClass}"
                       type="text" autocomplete="off" value="${sessionScope.registrationSchool}"
                       placeholder="<fmt:message key="registration.field.school"/>">
            </div>
            <c:if test="${sessionScope.registrationIsSchoolEmpty == true || sessionScope.registrationIsSchoolValid == false}">
                <div class="uk-width-medium-1-2 uk-animation-fade alertBlockSchoolNumber">
                    <div class="uk-alert uk-width-6-10 uk-pull-3-10 uk-align-center alertSchoolNumber" data-uk-alert>
                        <c:choose>
                            <c:when test="${sessionScope.registrationIsSchoolEmpty == true}">
                                <p class="uk-text-danger"><fmt:message
                                        key="registration.error.empty.school"/></p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsSchoolValid == false}">
                                <p class="uk-text-danger uk-margin-bottom-remove">
                                    <fmt:message key="registration.error.not.valid.school.number"/>
                                </p>

                                <p class="uk-text-center-small uk-margin-top-remove">
                                    <fmt:message key="registration.error.explanation.not.valid.school.number"/>
                                </p>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:if>
        </div>


        <div class="uk-grid uk-align-center uk-width-small-1-11 uk-margin-top">
            <div class="uk-width-1-1 uk-form-row registrationNormalizeButton">
                <button class="uk-width-1-2 uk-button uk-button-primary uk-button-large" type="submit">
                    <fmt:message key="registration.submit"/>
                </button>
                <a href="<c:url value="/index.html"/>"
                   class="uk-width-1-5 uk-button-large uk-button uk-button-success">
                    <fmt:message key="button.cancel"/>
                </a>
            </div>
        </div>
        <input type="hidden" name="command" value="verifyAccount">

    </form>
</div>
<%@include file="../jspf/modals/accountNameRules.jspf" %>
</body>
</html>

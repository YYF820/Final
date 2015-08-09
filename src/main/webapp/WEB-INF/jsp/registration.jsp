<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 31/07/15
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
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
                                <p class="uk-text-danger">Please enter your First name.</p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsFirstNameValid == false}">
                                <c:set scope="page" var="firstNameSuccessClass" value="uk-form-danger"/>
                                <p class="uk-text-danger">First name is not valid.</p>

                                <p class="uk-text-center-small"> First name starts with uppercase letter.</p>

                                <p class="uk-text-center-small"> First name can contain '- </p>

                                <p class="uk-text-center-small"> First name's length 2-15 letters</p>
                            </c:when>
                        </c:choose>
                    </div>
                </c:when>
            </c:choose>
        </div>
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
                                <p class="uk-text-danger">Please enter your Last name.</p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsLastNameValid == false}">
                                <c:set scope="page" var="lastNameSuccessClass" value="uk-form-danger"/>
                                <p class="uk-text-danger">Last name is not valid.</p>

                                <p class="uk-text-center-small"> Last name starts with uppercase letter.</p>

                                <p class="uk-text-center-small"> Last name can contain '- </p>

                                <p class="uk-text-center-small"> Last name's length 2-15 letters</p>
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
                                <p class="uk-text-danger">Please enter your Patronymic.</p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsPatronymicValid == false}">
                                <c:set scope="page" var="patronymicSuccessClass" value="uk-form-danger"/>
                                <p class="uk-text-danger">Patronymic name is not valid.</p>

                                <p class="uk-text-center-small"> Patronymic name starts with uppercase letter.</p>

                                <p class="uk-text-center-small"> Patronymic name can contain '- </p>

                                <p class="uk-text-center-small"> Patronymic's length 2-15 letters</p>
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
                <label id="firstName-label" class="control-label" for="firstName"></label>
                <input aria-labelledby="firstName-label" id="firstName" name="firstName"
                       class="uk-width-1-1 uk-form-large uk-form-width-large ${pageScope.firstNameSuccessClass}"
                       type="text" spellcheck="false" autocomplete="off" value="${sessionScope.registrationFirstName}"
                       placeholder="First Name:">
            </div>

            <div class="uk-width-1-3 ">
                <label id="lastName-label " class="control-label" for="lastName"></label>
                <input aria-labelledby="lastName-label" id="lastName" name="lastName"
                       class="uk-width-1-1 uk-form-large uk-form-width-large ${pageScope.lastNameSuccessClass}"
                       type="text" spellcheck="false" autocomplete="off" value="${sessionScope.registrationLastName}"
                       placeholder="Last Name:">
            </div>

            <div class="uk-width-1-3 ">
                <label id="patronymic-label" class="control-label" for="patronymic"></label>
                <input aria-labelledby="patronymic-label" id="patronymic" name="patronymic"
                       class="uk-width-1-1 uk-form-large uk-form-width-large ${pageScope.patronymicSuccessClass}"
                       type="text" spellcheck="false" autocomplete="off" value="${sessionScope.registrationPatronymic}"
                       placeholder="Patronymic:">
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
                       type="text" value="${sessionScope.registrationCity}" autocomplete="off" placeholder="City: ">
            </div>
            <c:if test="${sessionScope.registrationIsCityEmpty == true || sessionScope.registrationIsCityValid == false}">
                <div class="uk-width-medium-1-2 uk-animation-fade ">
                    <div class="uk-alert uk-width-1-1 " data-uk-alert>
                        <c:choose>
                            <c:when test="${sessionScope.registrationIsCityEmpty == true}">
                                <p class="uk-text-danger">Please enter your city.</p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsCityValid == false}">
                                <p class="uk-text-danger">City is not valid.</p>

                                <p class="uk-text-left-small">First letter must be uppercase.</p>

                                <p class="uk-text-left-small">City must be one word or 2 words with '- .</p>
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
                       type="text" value="${sessionScope.registrationRegion}" autocomplete="off" placeholder="Region: ">
            </div>
            <c:if test="${sessionScope.registrationIsRegionEmpty == true || sessionScope.registrationIsRegionValid == false}">
                <div class="uk-width-medium-1-2 uk-animation-fade ">
                    <div class="uk-alert uk-width-1-1 " data-uk-alert>
                        <c:choose>
                            <c:when test="${sessionScope.registrationIsRegionEmpty == true}">
                                <p class="uk-text-danger">Please enter your region.</p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsRegionValid == false}">
                                <p class="uk-text-danger">Region is not valid.</p>

                                <p class="uk-text-left-small">First letter of first word must be uppercase.</p>

                                <p class="uk-text-left-small">Second word starts with lowercase letter.</p>

                                <p class="uk-text-left-small">Region contains 2 words, which you can separate with
                                    space.</p>
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
                       ${(sessionScope.registrationIsAccountNameEmpty == false && sessionScope.registrationIsAccountNameValid == true) && (sessionScope.registrationIsAccountNameExregistrationIsts == false || sessionScope.registrationIsAccountNameExregistrationIsts == null) ||
                       (sessionScope.registrationIsAccountNameEmpty == null && sessionScope.registrationIsAccountNameValid == null && sessionScope.registrationIsAccountNameExregistrationIsts == null) ? 'uk-push-1-2' : ''}
                       uk-form-large uk-form-width-large
                       ${sessionScope.registrationIsAccountNameEmpty == true || sessionScope.registrationIsAccountNameValid == false || sessionScope.registrationIsAccountNameExregistrationIsts == true ? 'uk-form-danger' : ''}
                       ${sessionScope.registrationIsAccountNameEmpty == false && sessionScope.registrationIsAccountNameValid == true && (sessionScope.registrationIsAccountNameExregistrationIsts == false || sessionScope.registrationIsAccountNameExregistrationIsts == null) ? 'uk-form-success' : ''}"
                       type="text" value="${sessionScope.registrationAccountName}" autocomplete="off" placeholder="Email: ">
            </div>
            <c:if test="${sessionScope.registrationIsAccountNameEmpty == true || sessionScope.registrationIsAccountNameValid == false || sessionScope.registrationIsAccountNameExregistrationIsts == true}">
                <div class="uk-width-medium-1-2 uk-animation-fade ">
                    <div class="uk-alert uk-width-1-1 " data-uk-alert>
                        <c:choose>
                            <c:when test="${sessionScope.registrationIsAccountNameEmpty == true}">
                                <p class="uk-text-danger">Please enter Email.</p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsAccountNameExregistrationIsts == true}">
                                <p class="uk-text-danger">This email is already in use.
                                    <a href="<c:url value="/login.html"/>" class="uk-text-success">Want to log in?</a>
                                </p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsAccountNameValid == false}">
                                <p class="uk-text-danger">Email is not valid
                                    <a href="#email-id" class="uk-align-right"
                                       data-uk-modal="{bgclose:false, center:true}">
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
                       type="password" autocomplete="off" value="${sessionScope.registrationPassword}" placeholder="Password: ">


            </div>
            <c:if test="${sessionScope.registrationIsPasswordEmpty == true || sessionScope.registrationIsPasswordValid == false}">
                <div class="uk-width-medium-1-2 uk-animation-fade ">
                    <div class="uk-alert uk-width-1-1 " data-uk-alert>
                        <c:choose>
                            <c:when test="${sessionScope.registrationIsPasswordEmpty == true}">
                                <c:set scope="page" var="passwordSuccessClass" value="uk-form-danger"/>
                                <p class="uk-text-danger">Please enter Password.</p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsPasswordValid == false}">
                                <c:set scope="page" var="passwordSuccessClass" value="uk-form-danger"/>
                                <p class="uk-text-danger">Password is not valid.</p>

                                <p class="uk-text-left-small">Password must contains letters and at least one digit,
                                    length:
                                    6-20 characters.
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
                       type="text" autocomplete="off" value="${sessionScope.registrationSchool}" placeholder="School №: ">
            </div>
            <c:if test="${sessionScope.registrationIsSchoolEmpty == true || sessionScope.registrationIsSchoolValid == false}">
                <div class="uk-width-medium-1-2 uk-animation-fade alertBlockSchoolNumber">
                    <div class="uk-alert uk-width-6-10 uk-pull-3-10 uk-align-center alertSchoolNumber" data-uk-alert>
                        <c:choose>
                            <c:when test="${sessionScope.registrationIsSchoolEmpty == true}">
                                <p class="uk-text-danger">Please enter your school №.</p>
                            </c:when>
                            <c:when test="${sessionScope.registrationIsSchoolValid == false}">
                                <p class="uk-text-danger">School № is not valid, you can use number 0-255.</p>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:if>
        </div>


        <div class="uk-grid uk-align-center uk-width-small-1-11 uk-margin-top">
            <div class="uk-width-1-1 uk-form-row registrationNormalizeButton">
                <button class="uk-width-1-2 uk-button uk-button-primary uk-button-large" type="submit">Register</button>
                <a href="<c:url value="/index.html"/>"
                   class="uk-width-1-5 uk-button-large uk-button uk-button-success">Cancel</a>
            </div>
        </div>
        <input type="hidden" name="command" value="verifyAccount">

    </form>
</div>
<%@include file="../jspf/modals/accountNameRules.jspf" %>
</body>
</html>

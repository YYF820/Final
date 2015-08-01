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

    <c:if test="${
    requestScope.isAccountNameNull == true || requestScope.isAccountNameValid == false ||
    requestScope.isCityNull == true || requestScope.isCityValid == false ||
    requestScope.isRegionNull == true || requestScope.isRegionValid == false ||
    requestScope.isPasswordNull == true || requestScope.isPasswordValid == false ||
    requestScope.isSchoolNull == true || requestScope.isSchoolValid == false}">
        <div class="uk-grid uk-margin-bottom uk-animation-fade">
            <div class="uk-width-medium-1-2">
                <div class="uk-panel uk-panel-box uk-panel-box-primary">
                    <div class="uk-panel-badge uk-badge">Warning</div>
                    <c:choose>
                        <c:when test="${requestScope.isAccountNameNull == true}">
                            <c:set scope="page" var="accountSuccessClass" value="uk-form-danger"/>
                            <p class="uk-text-danger">Please enter Email.</p>
                        </c:when>
                        <c:when test="${requestScope.isAccountNameValid == false}">
                            <c:set scope="page" var="accountSuccessClass" value="uk-form-danger"/>
                            <p class="uk-text-danger uk-margin-top">Email is not valid
                                <a href="#email-id" class="uk-align-right" data-uk-modal="{bgclose:false, center:true}">
                                    <i class="uk-icon-question"></i>
                                </a>
                            </p>

                        </c:when>
                        <c:when test="${requestScope.isAccountNameNull == false && requestScope.isAccountNameValid == true}">
                            <c:set scope="page" var="accountSuccessClass" value="uk-form-success"/>
                        </c:when>
                    </c:choose>

                    <c:choose>
                        <c:when test="${requestScope.isPasswordNull == true}">
                            <c:set scope="page" var="passwordSuccessClass" value="uk-form-danger"/>
                            <c:if test="${requestScope.isAccountNameNull == true || requestScope.isAccountNameValid == false}">
                                <hr class="uk-grid-divider">
                            </c:if>
                            <p class="uk-text-danger">Please enter Password.</p>
                        </c:when>
                        <c:when test="${requestScope.isPasswordValid == false}">
                            <c:set scope="page" var="passwordSuccessClass" value="uk-form-danger"/>
                            <c:if test="${requestScope.isAccountNameNull == true || requestScope.isAccountNameValid == false}">
                                <hr class="uk-grid-divider">
                            </c:if>
                            <p class="uk-text-danger">Password is not valid.</p>

                            <p class="uk-text-left-small">Password must contains letters and at least one digit, length:
                                6-20 characters.
                            </p>
                        </c:when>
                        <c:when test="${requestScope.isPasswordNull == false && requestScope.isPasswordValid == true}">
                            <c:set scope="page" var="passwordSuccessClass" value="uk-form-success"/>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <div class="uk-width-medium-1-2 ">
                <div class="uk-panel uk-panel-box uk-panel-box-primary">
                    <div class="uk-panel-badge uk-badge">Warning</div>
                    <c:choose>
                        <c:when test="${requestScope.isCityNull == true}">
                            <c:set scope="page" var="citySuccessClass" value="uk-form-danger"/>
                            <p class="uk-text-danger">Please enter your city.</p>
                        </c:when>
                        <c:when test="${requestScope.isCityValid == false}">
                            <c:set scope="page" var="citySuccessClass" value="uk-form-danger"/>
                            <p class="uk-text-danger">City is not valid.</p>

                            <p class="uk-text-left-small">City can contains big and small letters and you can use '
                                .</p>
                        </c:when>
                        <c:when test="${requestScope.isCityNull == false && requestScope.isCityValid == true }">
                            <c:set scope="page" var="citySuccessClass" value="uk-form-success"/>
                        </c:when>
                    </c:choose>
                    <c:choose>
                        <c:when test="${requestScope.isRegionNull == true}">
                            <c:if test="${requestScope.isCityNull == true || requestScope.isCityValid == false}">
                                <hr class="uk-grid-divider">
                            </c:if>
                            <c:set scope="page" var="regionSuccessClass" value="uk-form-danger"/>
                            <p class="uk-text-danger">Please enter your region.</p>
                        </c:when>
                        <c:when test="${requestScope.isRegionValid == false}">
                            <c:if test="${requestScope.isCityNull == true || requestScope.isCityValid == false}">
                                <hr class="uk-grid-divider ">
                            </c:if>
                            <c:set scope="page" var="regionSuccessClass" value="uk-form-danger"/>
                            <p class="uk-text-danger">Region is not valid.</p>

                            <p class="uk-text-left-small">Region can contains big and small letters and you can use '
                                .</p>
                        </c:when>
                        <c:when test="${requestScope.isRegionNull == false && requestScope.isRegionValid == true }">
                            <c:set scope="page" var="regionSuccessClass" value="uk-form-success"/>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>
    </c:if>
    <form class="uk-form" action="register.do" method="POST">
        <div class="uk-grid uk-align-center uk-width-small-9-10">

            <div class="uk-width-1-3 ">
                <c:if test="${requestScope.isFirstNameNull == true || requestScope.isFirstNameValid == false}">
                    <div class="uk-alert uk-width-1-1 uk-animation-fade" data-uk-alert>
                        <c:choose>
                            <c:when test="${requestScope.isFirstNameNull == true}">
                                <p class="uk-text-danger">Please enter your First name.</p>
                            </c:when>
                            <c:when test="${requestScope.isFirstNameValid == false}">
                                <p class="uk-text-danger">First name is not valid.</p>
                            </c:when>
                        </c:choose>
                    </div>
                </c:if>
                <label id="firstName-label" class="control-label" for="firstName"></label>
                <input aria-labelledby="firstName-label" id="firstName" name="firstName"
                       class="uk-width-1-1 uk-form-large uk-form-width-large"
                       type="text" spellcheck="false" value="${requestScope.firstName}" placeholder="First Name:">
            </div>

            <div class="uk-width-1-3 ">
                <c:if test="${requestScope.isLastNameNull == true || requestScope.isLastNameValid == false}">
                    <div class="uk-alert uk-width-1-1 uk-animation-fade" data-uk-alert>
                        <c:choose>
                            <c:when test="${requestScope.isLastNameNull == true}">
                                <p class="uk-text-danger">Please enter your Last name.</p>
                            </c:when>
                            <c:when test="${requestScope.isLastNameValid == false}">
                                <p class="uk-text-danger">Last name is not valid.</p>
                            </c:when>
                        </c:choose>
                    </div>
                </c:if>
                <label id="lastName-label " class="control-label" for="lastName"></label>
                <input aria-labelledby="accountName-label" id="lastName" name="lastName"
                       class="uk-width-1-1 uk-form-large uk-form-width-large"
                       type="text" spellcheck="false" value="${requestScope.lastName}" placeholder="Last Name:">
            </div>

            <div class="uk-width-1-3 ">
                <c:if test="${requestScope.isPatronymicNull == true || requestScope.isPatronymicValid == false}">
                    <c:set scope="page" var="patronymicSuccessClass" value="false"/>
                    <div class="uk-alert uk-width-1-1 uk-animation-fade" data-uk-alert>
                        <c:choose>
                            <c:when test="${requestScope.isPatronymicNull == true}">
                                <p class="uk-text-danger">Please enter your patronymic.</p>
                            </c:when>
                            <c:when test="${requestScope.isPatronymicValid == false}">
                                <p class="uk-text-danger">Patronymic is not valid.</p>
                            </c:when>
                        </c:choose>
                    </div>
                </c:if>
                <label id="patronymic-label" class="control-label" for="patronymic"></label>
                <input aria-labelledby="patronymic-label" id="patronymic" name="patronymic"
                       class="uk-width-1-1 uk-form-large uk-form-width-large"
                       type="text" spellcheck="false" value="${requestScope.patronymic}" placeholder="Patronymic:">
            </div>
        </div>

        <div class="uk-form-row row-margin ">
            <label id="accountName-label" class="control-label" for="accountName"></label>
            <input aria-labelledby="accountName-label" id="accountName" name="accountName"
                   class="uk-width-1-1 uk-form-large uk-form-width-large ${pageScope.accountSuccessClass}"
                   type="text" spellcheck="false" value="${requestScope.accountName}" placeholder=" Email:">
        </div>

        <div class="uk-form-row row-margin ">
            <label id="city-label" class="control-label" for="city"></label>
            <input aria-labelledby="city-label" id="city" name="city"
                   class="uk-width-1-1 uk-form-large uk-form-width-large ${pageScope.citySuccessClass}"
                   type="text" spellcheck="false" value="${requestScope.city}" placeholder="City :">
        </div>

        <div class="uk-form-row row-margin ">
            <label id="region-label" class="control-label" for="region"></label>
            <input aria-labelledby="region-label" id="region" name="region"
                   class="uk-width-1-1 uk-form-large uk-form-width-large ${pageScope.regionSuccessClass}"
                   type="text" spellcheck="false" value="${requestScope.region}" placeholder="Region: ">
        </div>

        <div class="uk-form-row uk-form-password row-margin">
            <label id="password-label" class="control-label" for="password"></label>
            <input aria-labelledby="password-label" id="password" name="password"
                   class="uk-width-1-1 uk-form-large uk-form-width-large ${pageScope.passwordSuccessClass}"
                   type="password" autocomplete="off" placeholder="Password">
        </div>
        <div class="uk-grid uk-align-center uk-margin-top ">
            <c:choose>
                <c:when test="${requestScope.isSchoolNull == false && requestScope.isSchoolValid == true}">
                    <c:set scope="page" var="schoolSuccessClass" value="uk-form-success"/>
                </c:when>
                <c:when test="${requestScope.isSchoolNull == true || requestScope.isSchoolValid == false}">
                    <c:set scope="page" var="schoolSuccessClass" value="uk-form-danger"/>
                </c:when>
            </c:choose>
            <div class="uk-width-medium-1-2 ">
                <label id="school-label" class="control-label" for="school"></label>
                <input aria-labelledby="school-label" id="school" name="school"
                       class="uk-form-small uk-form-width-small uk-push-1-6 ${pageScope.schoolSuccessClass}"
                       type="text" autocomplete="off" value="${requestScope.school}" placeholder="School №: ">
            </div>
            <c:if test="${requestScope.isSchoolNull == true || requestScope.isSchoolValid == false}">
                <div class="uk-width-medium-1-2 uk-animation-fade">
                    <div class="uk-alert uk-width-5-10 uk-pull-1-10 " data-uk-alert>
                        <c:choose>
                            <c:when test="${requestScope.isSchoolNull == true}">
                                <p class="uk-text-danger">Please enter your school number.</p>
                            </c:when>
                            <c:when test="${requestScope.isSchoolValid == false}">
                                <p class="uk-text-danger">School number is not valid, you can use number 0-255.</p>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:if>

        </div>


        <div class="uk-grid uk-align-center uk-width-small-7-10 uk-margin-top">
            <div class="uk-width-1-1 uk-form-row registrationNormalizeButton">
                <button class="uk-width-1-2 uk-button uk-button-primary uk-button-large" type="submit">Register</button>
                <a href="registration.html" class="uk-width-1-5 uk-button-large uk-button uk-button-success">Cancel</a>
            </div>
        </div>

    </form>
</div>
<%@include file="../jspf/modals/accountNameRules.jspf" %>
</body>
</html>

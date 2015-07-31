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
<div class="uk-container uk-container-center uk-width-8-10 uk-text-center">
    <img class="uk-margin-bottom logo-login"
         src="<c:url value="/resources/img/mainLogo.png"/>"
         alt="">

    <form class="uk-form">
        <div class="uk-grid uk-align-center uk-width-small-9-10">

            <div class="uk-width-1-3">
                <label id="firstName-label" class="control-label" for="firstName"></label>
                <input aria-labelledby="accountName-label" id="firstName" name="firstName"
                       class="uk-width-1-1 uk-form-large uk-form-width-large"
                       type="text" spellcheck="false" placeholder="First Name:">
            </div>


            <div class="uk-width-1-3">
                <label id="lastName-label " class="control-label" for="lastName"></label>
                <input aria-labelledby="accountName-label" id="lastName" name="lastName"
                       class="uk-width-1-1 uk-form-large uk-form-width-large"
                       type="text" spellcheck="false" placeholder="Last Name:">
            </div>

            <div class="uk-width-1-3 ">
                <label id="patronymic-label" class="control-label" for="patronymic"></label>
                <input aria-labelledby="accountName-label" id="patronymic" name="patronymic"
                       class="uk-width-1-1 uk-form-large uk-form-width-large"
                       type="text" spellcheck="false" placeholder="Patronymic:">
            </div>
        </div>

        <div class="uk-form-row row-margin ">
            <label id="accountName-label" class="control-label" for="accountName"></label>
            <input aria-labelledby="accountName-label" id="accountName" name="accountName"
                   class="uk-width-1-1 uk-form-large uk-form-width-large"
                   type="text" spellcheck="false" placeholder="Email:">
        </div>

        <div class="uk-form-row row-margin ">
            <label id="city-label" class="control-label" for="city"></label>
            <input aria-labelledby="accountName-label" id="city" name="city"
                   class="uk-width-1-1 uk-form-large uk-form-width-large"
                   type="text" spellcheck="false" placeholder="City :">
        </div>

        <div class="uk-form-row row-margin ">
            <label id="region-label" class="control-label" for="region"></label>
            <input aria-labelledby="accountName-label" id="region" name="region"
                   class="uk-width-1-1 uk-form-large uk-form-width-large"
                   type="text" spellcheck="false" placeholder="Region: ">
        </div>

        <div class="uk-form-row uk-form-password row-margin">
            <label id="password-label" class="control-label" for="password"></label>
            <input aria-labelledby="password-label" id="password" name="password"
                   class="uk-width-1-1 uk-form-large uk-form-width-large "
                   type="password" autocomplete="off" placeholder="Password"
                   value="${requestScope.isAccountNameNull == true && requestScope.isPasswordNull == false ? requestScope.password : ""}">
        </div>
        <div class="uk-form-row row-margin">
            <div class="uk-grid uk-align-center uk-width-small-1-2">
                <label id="school-label" class="control-label" for="school"></label>
                <input aria-labelledby="password-label" id="school" name="school"
                       class="uk-width-3-10"
                       type="text" autocomplete="off" placeholder="School: ">
                <label><input type="checkbox" name="withoutCompetitiveEntry"> Do you have perision entry without exams:
                </label>
            </div>
        </div>

        <div class="uk-grid uk-align-center uk-width-small-7-10 ">
            <div class="uk-width-1-1 uk-form-row registrationNormalizeButton">
                <button class="uk-width-1-2 uk-button uk-button-primary uk-button-large" type="submit">Register</button>
                <a href="registration.html" class="uk-width-1-5 uk-button-large uk-button uk-button-success">Cancel</a>
            </div>
        </div>

    </form>
</div>
</body>
</html>

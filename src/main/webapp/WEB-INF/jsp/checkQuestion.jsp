<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 03/08/15
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<%@include file="../jspf/topPanel.jspf" %>
<div class="uk-vertical-align uk-text-center uk-height-1-1">

    <div class="uk-vertical-align-middle " style="width: 400px;">


        <img class="uk-margin-bottom logo-login"
             src="<c:url value="/resources/img/mainLogo.png"/>"
             alt="">

        <form class="uk-panel uk-panel-box uk-form" action="<c:url value="/checkQuestion.do"/>" method="POST">

            <div class="uk-alert uk-alert-warning" data-uk-alert>
                <p class="uk-text-middle">Please answer question to verify it's your email.</p>

                <p>What school did you study?</p>
            </div>
            <c:if test="${
            requestScope.isSchoolValid == false ||
            requestScope.isSchoolEmpty == true ||
            requestScope.isSchoolCorrect == false}">
                <c:set scope="page" var="badSchoolClass" value="uk-form-danger"/>
                <div class="uk-alert" data-uk-alert>
                    <a href="" class="uk-alert-close uk-close"></a>
                    <c:choose>
                        <c:when test="${requestScope.isSchoolEmpty == true}">
                            <p class="uk-text-danger">Please enter your school №.</p>
                        </c:when>
                        <c:when test="${requestScope.isSchoolValid == false}">
                            <p class="uk-text-danger">School № is not valid, you can use number 0-255. </p>
                        </c:when>
                        <c:when test="${requestScope.isSchoolCorrect == false}">
                            <p class="uk-text-danger">Wrong answer.</p>
                        </c:when>
                    </c:choose>
                </div>
            </c:if>
            <label id="school-label" class="control-label" for="school"></label>

            <div class="uk-form-row row-margin">
                <input aria-labelledby="school" id="school" name="school"
                       class="uk-width-1-1 uk-form-large uk-form-width-large ${badSchoolClass}"
                       type="text" spellcheck="false" autocomplete="off" placeholder="School №:"
                       value="${requestScope.school}">
            </div>

            <div class="uk-form-row">
                <button class="uk-width-1-1 uk-button uk-button-primary" type="submit">Next</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>

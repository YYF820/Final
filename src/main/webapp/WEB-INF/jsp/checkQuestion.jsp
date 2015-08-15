<%@include file="../jspf/imports.jspf" %>
<html>
<head>
    <%@include file="../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<%@include file="../jspf/topPanel.jspf" %>
<div class="uk-container-center uk-text-center uk-width-8-10">
    <div class="uk-container-center uk-text-center uk-width-medium-4-10 uk-margin-large-top">
        <img class="uk-margin-bottom logo-login"
             src="<c:url value="/resources/img/mainLogo.png"/>"
             alt="">

        <form class="uk-panel uk-panel-box uk-form" action="<c:url value="/checkQuestion.do"/>" method="POST">

            <div class="uk-alert uk-alert-warning" data-uk-alert>
                <p class="uk-text-middle"><fmt:message key="check.school.number.question.warning.first"/></p>

                <p><fmt:message key="check.school.number.question.warning.second"/></p>
            </div>
            <c:if test="${
            sessionScope.checkQuestionIsSchoolValid == false ||
            sessionScope.checkQuestionIsSchoolEmpty == true ||
            sessionScope.checkQuestionIsSchoolCorrect == false}">
                <c:set scope="page" var="badSchoolClass" value="uk-form-danger"/>
                <div class="uk-alert" data-uk-alert>
                    <a href="" class="uk-alert-close uk-close"></a>
                    <c:choose>
                        <c:when test="${sessionScope.checkQuestionIsSchoolEmpty == true}">
                            <p class="uk-text-danger">
                                <fmt:message key="check.school.number.question.empty.school.number"/>
                            </p>
                        </c:when>
                        <c:when test="${sessionScope.checkQuestionIsSchoolValid == false}">
                            <p class="uk-text-danger">
                                <fmt:message key="check.school.number.question.not.valid.school.number"/>
                            </p>
                        </c:when>
                        <c:when test="${sessionScope.checkQuestionIsSchoolCorrect == false}">
                            <p class="uk-text-danger">
                                <fmt:message key="check.school.number.question.wrong.school.number"/>
                            </p>
                        </c:when>
                    </c:choose>
                </div>
            </c:if>
            <label id="school-label" class="control-label" for="school"></label>

            <div class="uk-form-row row-margin">
                <input aria-labelledby="school" id="school" name="school"
                       class="uk-width-1-1 uk-form-large uk-form-width-large ${badSchoolClass}"
                       type="text" spellcheck="false" autocomplete="off"
                       placeholder="<fmt:message key="check.school.number.question.school.number"/>"
                       value="${sessionScope.checkQuestionSchool}">
            </div>

            <div class="uk-grid uk-form-row uk-align-center uk-margin-bottom-remove">
                <button class="uk-width-medium-3-5 uk-button uk-button-primary " type="submit">
                    <fmt:message key="button.next"/>
                </button>
                <a href="<c:url value="/index.html"/>"
                   class="uk-width-medium-1-5 uk-button uk-button-success uk-push-2-10">
                    <fmt:message key="button.cancel"/>
                </a>
            </div>
        </form>
    </div>
</div>
</body>
</html>

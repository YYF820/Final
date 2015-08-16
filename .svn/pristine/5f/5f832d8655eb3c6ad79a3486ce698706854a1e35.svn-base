<%@include file="../jspf/imports.jspf" %>
<html>
<head>
    <%@include file="../jspf/header/header.jspf" %>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/formRows.css"/>"/>
    <title></title>
</head>
<body>
<c:if test="${
    sessionScope.resetPasswordIsPasswordEmpty == true ||
    sessionScope.resetPasswordIsPasswordValid == false ||
    sessionScope.resetPasswordIsPasswordsSame == false
}">
    <c:set scope="page" var="badPasswordClass" value="uk-form-danger"/>
</c:if>
<div class="uk-container-center uk-text-center uk-width-8-10">
    <div class="uk-container-center uk-text-center uk-width-medium-4-10 uk-margin-large-top">
        <c:choose>
            <c:when test="${sessionScope.checkTicketIsTicketResetPasswordCorrect == true}">
                <p class="uk-text-success uk-text-large uk-text-middle"><i
                        class="uk-icon-check uk-icon-large uk-text-success"></i>&nbsp
                    <fmt:message key="reset.password.subject"/>
                </p>

                <div class="uk-margin-large-top">
                    <form class="uk-panel uk-panel-box uk-form"
                          action="<c:url value="/resetPassword.do"/>"
                          method="POST">
                        <div class="uk-alert uk-alert-warning" data-uk-alert>
                            <p class="uk-text-middle"><fmt:message key="reset.password.warning"/></p>

                        </div>
                        <div class="uk-grid">
                            <div class="uk-width-medium-2-5">
                                <label id="password-label" class="password-label"
                                       for="password"></label>
                                <input aria-labelledby="password-label" id="password"
                                       name="password"
                                       class="uk-form-width-small uk-form-width-small ${badPasswordClass}"
                                       type="password" autocomplete="off"
                                       placeholder="<fmt:message key="reset.password.password"/>">
                            </div>
                            <c:if test="${
                        sessionScope.resetPasswordIsPasswordEmpty == true ||
                        sessionScope.resetPasswordIsPasswordValid == false ||
                        sessionScope.resetPasswordIsPasswordsSame == false
                        }">
                                <div class="uk-width-medium-3-5">
                                    <div class="uk-alert alertSchoolNumber">
                                        <c:choose>
                                            <c:when test="${sessionScope.resetPasswordIsPasswordEmpty == true}">
                                                <p class="uk-text-danger uk-text-middle">
                                                    <fmt:message key="reset.password.empty.password"/>
                                                </p>
                                            </c:when>
                                            <c:when test="${sessionScope.resetPasswordIsPasswordValid == false}">
                                                <p class="uk-text-danger uk-text-middle">
                                                    <fmt:message key="reset.password.not.valid.password.subject"/>
                                                </p>

                                                <p class="uk-text-left-small">
                                                    <fmt:message key="reset.password.not.valid.password.explanation"/>
                                                </p>
                                            </c:when>
                                            <c:when test="${sessionScope.resetPasswordIsPasswordsSame == false}">
                                                <p class="uk-text-danger uk-text-middle">
                                                    <fmt:message key="reset.password.passwords.not.same"/>
                                                </p>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        <div class="uk-grid uk-margin-top">
                            <div class="uk-width-medium-2-5">
                                <label id="confirmPassword-label" class="confirmPassword-label"
                                       for="confirmPassword"></label>
                                <input aria-labelledby="confirmPassword-label" id="confirmPassword"
                                       name="confirmPassword"
                                       class="uk-form-width-small uk-form-width-small ${badPasswordClass}"
                                       type="password" autocomplete="off"
                                       placeholder="<fmt:message key="reset.password.confirm.password"/>">
                            </div>
                            <c:if test="${sessionScope.resetPasswordIsConfirmPasswordEmpty == true}">
                                <div class="uk-width-medium-3-5">
                                    <div class="uk-alert alertSchoolNumber">
                                        <c:if test="${sessionScope.resetPasswordIsConfirmPasswordEmpty == true}">
                                            <p class="uk-text-danger uk-text-middle">
                                                <fmt:message key="reset.password.empty.password"/>
                                            </p>
                                        </c:if>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                        <div class="uk-margin-top ">
                            <button class="uk-width-1-1 uk-button uk-button-primary" type="submit">
                                <fmt:message key="button.save.new.password"/>
                            </button>
                        </div>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <c:redirect url="/index.html"/>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>

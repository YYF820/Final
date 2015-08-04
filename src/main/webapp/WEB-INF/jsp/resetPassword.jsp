<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 04/08/15
  Time: 18:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../jspf/header/header.jspf" %>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/formRows.css"/>"/>
    <title></title>
</head>
<body>
<div class="uk-container uk-container-center uk-width-8-10 uk-text-center uk-margin-top ">
    <div class="uk-grid uk-align-center uk-width-small-1-2 uk-margin-large-top">
        <c:if test="${requestScope.isTicketResetPasswordCorrect == true || requestScope.isFromResetPasswordServlet == true}">
            <p class="uk-text-success uk-text-large uk-text-middle"><i
                    class="uk-icon-check uk-icon-large uk-text-success"></i>&nbspThank you! Code is correct now set up
                new password.</p>

            <div class="uk-margin-large-top">
                <form class="uk-panel uk-panel-box uk-form"
                      action="resetPassword.do"
                      method="POST">
                    <div class="uk-alert uk-alert-warning" data-uk-alert>
                        <p class="uk-text-middle">Please enter new password and confirm it in the form below.</p>

                    </div>
                    <div class="uk-grid">
                        <div class="uk-width-medium-2-5">
                            <label id="password-label" class="password-label"
                                   for="password"></label>
                            <input aria-labelledby="password-label" id="password"
                                   name="password"
                                   class="uk-form-width-small uk-form-width-small"
                                   type="password" autocomplete="off" value="${requestScope.password}"
                                   placeholder="Password: ">
                        </div>
                        <c:if test="${
                        requestScope.isPasswordNull == true ||
                        requestScope.isPasswordValid == false ||
                        requestScope.isPasswordsSame == false
                        }">
                            <div class="uk-width-medium-3-5">
                                <div class="uk-alert alertSchoolNumber">
                                    <c:choose>
                                        <c:when test="${requestScope.isPasswordNull == true}">
                                            <p class="uk-text-danger uk-text-middle">Please enter password.</p>
                                        </c:when>
                                        <c:when test="${requestScope.isPasswordValid == false}">
                                            <p class="uk-text-danger uk-text-middle">Password or confirm password is not valid.</p>

                                            <p class="uk-text-left-small">Password must contains letters and at least
                                                one digit,
                                                length:
                                                6-20 characters.
                                            </p>
                                        </c:when>
                                        <c:when test="${requestScope.isPasswordsSame == false}">
                                            <p class="uk-text-danger uk-text-middle">Passwords not same.</p>
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
                                   class="uk-form-width-small uk-form-width-small"
                                   type="password" autocomplete="off" value="${requestScope.confirmPassword}"
                                   placeholder="Confirm password: ">
                        </div>
                        <c:if test="${requestScope.isConfirmPasswordEmpty == true}">
                            <div class="uk-width-medium-3-5">
                                <div class="uk-alert alertSchoolNumber">
                                    <c:if test="${requestScope.isConfirmPasswordEmpty == true}">
                                        <p class="uk-text-danger uk-text-middle">Please enter password.</p>
                                    </c:if>
                                </div>
                            </div>
                        </c:if>
                    </div>
                    <div class="uk-margin-top ">
                        <button class="uk-width-1-1 uk-button uk-button-primary" type="submit">Save new password
                        </button>
                    </div>
                </form>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>
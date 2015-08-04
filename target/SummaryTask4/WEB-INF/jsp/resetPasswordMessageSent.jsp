<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 04/08/15
  Time: 15:03
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
<%@include file="../jspf/topPanel.jspf" %>
<div class="uk-container uk-container-center uk-width-8-10 uk-text-center uk-margin-top ">
    <div class="uk-grid uk-align-center uk-width-small-1-2 uk-margin-large-top">

        <c:if test="${requestScope.isMessageSent == true || requestScope.isFromServlet == true}">

            <p class="uk-text-success uk-text-large uk-text-middle"><i
                    class="uk-icon-check uk-icon-large uk-text-success"></i>&nbspThank you! We have sent verification
                code to your email.</p>

            <div class="uk-margin-large-top uk-text-center">
                <form class="uk-panel uk-panel-box uk-form"
                      action="checkTicketResetPassword.do"
                      method="POST">

                    <div class="uk-alert uk-alert-warning" data-uk-alert>
                        <p class="uk-text-middle">Please enter code from email in the form below to recover
                            password.</p>

                        <p class="uk-text-danger">You've got <span
                                class="uk-text-danger uk-h4">${3 - sessionScope.counterBadTicketInserts}</span>
                            tries and 10 minutes, failing that account will be blocked.</p>
                    </div>
                    <div class="uk-grid">
                        <div class="uk-width-medium-1-3">
                            <label id="ticketResetPassword-label" class="ticketResetPassword-label"
                                   for="ticketResetPassword"></label>
                            <input aria-labelledby="ticketResetPassword-label" id="ticketResetPassword"
                                   name="ticketResetPassword"
                                   class="uk-form-width-small uk-form-width-small"
                                   type="text" autocomplete="off" value="${requestScope.ticketResetPassword}"
                                   placeholder="Code : ">
                        </div>
                        <c:if test="${requestScope.isTicketResetPasswordEmpty == true || requestScope.isTicketResetPasswordCorrect == false}">
                            <div class="uk-width-medium-2-3">
                                <div class="uk-alert alertSchoolNumber uk-animation-fade">
                                    <c:choose>
                                        <c:when test="${requestScope.isTicketResetPasswordEmpty == true}">
                                            <p class="uk-text-danger">Please enter code.</p>
                                        </c:when>
                                        <c:when test="${requestScope.isTicketResetPasswordCorrect == false}">
                                            <p class="uk-text-danger">Wrong code.</p>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                        </c:if>
                    </div>
                    <div class="uk-margin-top ">
                        <button class="uk-width-1-1 uk-button uk-button-primary" type="submit">Next</button>
                    </div>
                    <c:set scope="session" var="counterBadTicketInserts"
                           value="${sessionScope.counterBadTicketInserts + 1}"/>
                </form>
            </div>
        </c:if>
        <c:if test="${requestScope.isMessageSent == false || (requestScope.isMessageSent == null && requestScope.isFromServlet == null && requestScope.isBlockedAccount == null)}">
            <p class="uk-text-danger uk-text-large uk-text-middle"><i
                    class="uk-icon-exclamation-triangle uk-icon-large uk-text-danger"></i>&nbspWe couldn't send you
                verification code to your email.</p>

            <div class="uk-margin-top uk-text-left">
                <p class="">An error has occurred, possibly for one of the following reasons:</p>
                <ul class="uk-list uk-list-line uk-list-space">
                    <li class="uk-margin-left">Problems with our servers.</li>
                    <li class="uk-margin-left">Problems with the servers, the company that owns your email.</li>
                    <li class="uk-margin-left">The account verification attempt was unsuccessful. Please try again, as
                        this may be a temporary
                        issue.
                    </li>
                </ul>
                <p class="uk-text-danger">Click the button below to return to Account Management, where you can resend
                    verification code to recover password.</p>
                <a class="uk-button uk-button-primary uk-width-8-10 uk-align-center" href="login.html">Continue to
                    Account Management</a>
            </div>
        </c:if>
        <c:if test="${requestScope.isBlockedAccount == true}">
            <p class="uk-text-exclamation-triangle uk-text-large uk-text-middle uk-text-danger"><i
                    class="uk-icon-check uk-icon-large uk-text-danger"></i>&nbspYou wrote wrong code 3 times, we
                blocked account, contact our support.</p>
        </c:if>
    </div>
</div>
</body>
</html>

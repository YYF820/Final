<%@include file="../jspf/imports.jspf" %>
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

        <c:if test="${sessionScope.checkTicketIsMessageSent == true}">

            <p class="uk-text-success uk-text-large uk-text-middle"><i
                    class="uk-icon-check uk-icon-large uk-text-success"></i>&nbsp
                <fmt:message key="check.ticket.reset.password.message.sent.subject"/>
            </p>

            <div class="uk-margin-large-top uk-text-center">
                <form class="uk-panel uk-panel-box uk-form"
                      action="<c:url value="/checkTicketResetPassword.do"/>"
                      method="POST">

                    <div class="uk-alert uk-alert-warning" data-uk-alert>
                        <p class="uk-text-middle"><fmt:message key="check.ticket.reset.password.warning"/></p>

                        <p class="uk-text-danger">
                            <fmt:message key="check.ticket.reset.password.danger.first"/>
                            <span class="uk-text-danger uk-h4">${3 - sessionScope.checkTicketCounterBadTicketInserts}</span>
                            <fmt:message key="check.ticket.reset.password.danger.second"/>
                        </p>
                    </div>
                    <div class="uk-grid">
                        <div class="uk-width-medium-1-3">
                            <label id="ticketResetPassword-label" class="ticketResetPassword-label"
                                   for="ticketResetPassword"></label>
                            <input aria-labelledby="ticketResetPassword-label" id="ticketResetPassword"
                                   name="ticketResetPassword"
                                   class="uk-form-width-small uk-width-4-5"
                                   type="text" autocomplete="off" value="${sessionScope.checkTicketTicketResetPassword}"
                                   placeholder="<fmt:message key="check.ticket.reset.password.code"/>">
                        </div>
                        <c:if test="${sessionScope.checkTicketIsEmpty == true || sessionScope.checkTicketIsTicketResetPasswordCorrect == false}">
                            <div class="uk-width-medium-2-3">
                                <div class="uk-alert alertSchoolNumber uk-animation-fade">
                                    <c:choose>
                                        <c:when test="${sessionScope.checkTicketIsEmpty == true}">
                                            <p class="uk-text-danger">
                                                <fmt:message key="check.ticket.reset.password.empty.code"/>
                                            </p>
                                        </c:when>
                                        <c:when test="${sessionScope.checkTicketIsTicketResetPasswordCorrect == false}">
                                            <p class="uk-text-danger">
                                                <fmt:message key="check.ticket.reset.password.wrong.code"/>
                                            </p>
                                        </c:when>
                                    </c:choose>
                                </div>
                            </div>
                        </c:if>
                    </div>
                    <div class="uk-grid uk-form-row uk-align-center uk-margin-bottom-remove uk-margin-top">
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
        </c:if>
        <c:if test="${sessionScope.checkTicketIsMessageSent == false || sessionScope.checkTicketIsMessageSent == null }">
            <p class="uk-text-danger uk-text-large uk-text-middle"><i
                    class="uk-icon-exclamation-triangle uk-icon-large uk-text-danger"></i>&nbsp
                <fmt:message key="check.ticket.reset.password.error.subject"/>
            </p>

            <div class="uk-margin-top uk-text-left">
                <p class=""><fmt:message key="check.ticket.reset.password.error.body.list.start"/></p>
                <ul class="uk-list uk-list-line uk-list-space">
                    <li class="uk-margin-left">
                        <fmt:message key="check.ticket.reset.password.error.body.list.first"/>
                    </li>
                    <li class="uk-margin-left">
                        <fmt:message key="check.ticket.reset.password.error.body.list.second"/>
                    </li>
                    <li class="uk-margin-left">
                        <fmt:message key="check.ticket.reset.password.error.body.list.third"/>
                    </li>
                </ul>
                <p class="uk-text-danger"><fmt:message key="check.ticket.reset.password.error.faq"/></p>
                <a class="uk-button uk-button-primary uk-width-8-10 uk-align-center"
                   href="<c:url value="/login.html"/>">
                    <fmt:message key="button.continue.to.account.management"/>
                </a>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>

<%@include file="../jspf/imports.jspf" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/logo.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/formRows.css"/>"/>
    <%@include file="../jspf/header/header.jspf" %>
</head>
<body>
<%@include file="../jspf/topPanel.jspf" %>
<div class="uk-container-center uk-text-center uk-width-8-10">
    <div class="uk-container-center uk-text-center uk-width-medium-4-10 uk-margin-large-top">
        <c:if test="${sessionScope.supportIsAnyEmptyField == true}">
            <div class="uk-alert uk-alert-danger">
                <fmt:message key="support.error.empty.fields"/>
            </div>
        </c:if>
        <c:if test="${sessionScope.supportIsValidAccountName == false}">
            <div class="uk-alert uk-alert-danger">
                <div>
                    <p class="uk-text-middle uk-text-danger uk-margin-bottom-remove"><fmt:message
                            key="login.error.email.not.valid"/>
                        <a href="#email-id" class="" data-uk-modal="{center:true}">
                            <i class="uk-icon-info-circle"></i>
                        </a>
                    </p>
                    <c:set scope="page" var="badEmailClass" value="uk-form-danger"/>
                </div>
            </div>
        </c:if>
        <c:if test="${sessionScope.supportIsValidSubject == false}">
            <div class="uk-alert uk-alert-danger">
                <p class="uk-text-middle uk-text-danger">
                    <fmt:message key="support.error.not.valid.subject"/>
                </p>
                <c:set scope="page" var="badSubjectClass" value="uk-form-danger"/>
            </div>
        </c:if>
        <c:if test="${sessionScope.supportIsValidMessage == false}">
            <div class="uk-alert uk-alert-danger">
                <p class="uk-text-middle uk-text-danger">
                    <fmt:message key="support.error.not.valid.message"/>
                </p>
                <c:set scope="page" var="badMessageClass" value="uk-form-danger"/>
            </div>
        </c:if>
        <form class="uk-form" action="<c:url value="/support.do"/>" method="POST">
            <fieldset data-uk-margin>
                <u:ifAuthAs role="guest">
                    <legend class="uk-h3 uk-text-success">
                        <fmt:message key="support.form.head.guest"/>
                    </legend>
                    <input class="uk-form-row uk-width-1-1
                    ${sessionScope.supportIsValidAccountName == false ? badEmailClass : ''}"
                           type="text" placeholder="<fmt:message key="account.name"/>"
                           name="accountName" value="${sessionScope.supportAccountName}"/>
                    <input type="hidden" name="command" value="withAccountName">
                </u:ifAuthAs>
                <u:ifAuthAs role="entrant">
                    <legend class="uk-h3 uk-text-success">
                        <fmt:message key="support.form.head.entrant"/>
                    </legend>
                    <input type="hidden" name="command" value="withOutAccountName">
                </u:ifAuthAs>
                <input class="uk-form-row uk-width-1-1
                ${sessionScope.supportIsValidSubject == false ? badSubjectClass : ''}"
                       type="text" placeholder="<fmt:message key="support.form.subject"/>" name="subject" value="${sessionScope.supportSubject}">

                <div class="uk-form-row">
                    <textarea class="${sessionScope.supportIsValidMessage == false ? badMessageClass : ''}"
                              cols="66" rows="10" placeholder="<fmt:message key="support.form.message"/>" name="message">
                        ${sessionScope.supportMessage}
                    </textarea>
                </div>
                <div class="uk-grid">
                    <button class="uk-button uk-button-primary uk-width-1-3 uk-push-1-10" type="submit">
                        <fmt:message key="support.button.send.mail"/> <i class="uk-icon-mail-forward"></i>
                    </button>
                    <a class="uk-button uk-button-success uk-width-1-3 uk-push-3-10"
                       href="<c:url value="/index.html"/>"><fmt:message key="button.cancel"/></a>
                </div>
            </fieldset>
        </form>
    </div>
</div>
<%@include file="../jspf/modals/accountNameRules.jspf" %>
</body>
</html>

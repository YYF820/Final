<%@include file="../../jspf/imports.jspf" %>
<html>
<head>
    <%@include file="../../jspf/header/header.jspf"%>
    <title></title>
</head>
<body>
<div class="uk-container uk-container-center uk-width-8-10 uk-text-center uk-margin-top ">
    <div class="uk-grid uk-align-center uk-width-small-1-2 uk-margin-large-top">
        <p class="uk-text-danger uk-text-large uk-text-middle"><i
                class="uk-icon-exclamation-triangle uk-icon-large uk-text-danger"></i>&nbspYour code for reset password
            is expired.</p>

        <div class="uk-margin-top uk-text-left">
            <p class="uk-text-danger">Click the button below to return to Account Management, where you can resend
                verification code to recover password.</p>
            <a class="uk-button uk-button-primary uk-width-1-1 uk-align-center" href="<c:url value="/login.html"/>">Continue to
                Account Management</a>
        </div>
    </div>
</div>
</body>
</html>

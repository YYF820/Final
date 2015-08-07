<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 05/08/15
  Time: 02:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<%@include file="../../jspf/topPanel.jspf" %>
<c:choose>
    <c:when test="${sessionScope.resetPasswordIsUpdatedPassword == true}">
        <div class="uk-container uk-container-center uk-width-8-10 uk-margin-top ">
            <div class="uk-grid uk-align-center uk-width-small-1-2 uk-margin-large-top">
                <p class="uk-text-success uk-text-large uk-text-middle">
                    <i class="uk-icon-check uk-icon-large uk-text-success"></i>&nbspThank you! Password was changed.
                    <br/>
                    <a href="<c:url value="/login.html"/>"
                       class="uk-text-success uk-text-large uk-align-center uk-text-center ">
                        Want to log in ?
                        <i class="uk-icon-external-link"></i>
                    </a>
                </p>
            </div>
        </div>
    </c:when>
    <c:when test="${requestScope.isUpdatedPassword == false}">
        <div class="uk-container uk-container-center uk-width-8-10 uk-margin-top ">
            <div class="uk-grid uk-align-center uk-width-small-1-2 uk-margin-large-top">
                <p class="uk-text-success uk-text-large uk-text-middle">
                    <i class="uk-icon-check uk-icon-large uk-text-success"></i>&nbspThank you! Password was changed. But
                    we couldn't notfy you by email
                    <br/>
                    <a href="<c:url value="/login.html"/>"
                       class="uk-text-success uk-text-large uk-align-center uk-text-center ">
                        Want to log in ?
                        <i class="uk-icon-external-link"></i>
                    </a>
                </p>
            </div>
        </div>
    </c:when>
    <c:when test="${requestScope.isUpdatedPassword == null}">
        <c:redirect url="login.html"/>
    </c:when>
</c:choose>
</body>
</html>

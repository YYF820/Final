<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 14/08/15
  Time: 03:34
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
<div class="uk-container uk-container-center uk-width-medium-1-1 uk-text-center ">
    <table class="uk-table  uk-table-hover uk-table-striped uk-table-condensed uk-animation-fade uk-panel-box uk-panel-box-primary">
        <caption>Entrants</caption>
        <thead>
        <tr>
            <th>Faculty name</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Patronymic</th>
            <th>Sum marks</th>
            <th>Enter enterUniversityStatus</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="passedEntrant" items="${sessionScope.passedEntrants}">
            <tr class="uk-table-middle ${entrant.statusId == 1 ? 'uk-text-danger' : ''}">
                <td>${passedEntrant.facultyName}</td>
                <td>${passedEntrant.firstName}</td>
                <td>${passedEntrant.lastName}</td>
                <td>${passedEntrant.patronymic}</td>
                <td>${passedEntrant.sumOfMarks}</td>
                <td>${passedEntrant.enterUniversityStatus}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>

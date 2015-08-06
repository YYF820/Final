<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 06/08/15
  Time: 17:35
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
<div class="uk-container uk-container-center uk-width-medium-1-1 uk-text-center ">

    <table class="uk-table uk-table-hover uk-table-striped uk-table-condensed uk-width-medium-8-10">
        <caption>Entrants</caption>
        <thead>
        <tr>
            <th>id</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Patronymic</th>
            <th>Email</th>
            <th>City</th>
            <th>Region</th>
            <th>School</th>
            <th>Entrant status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="entrant" items="${sessionScope.entrantsAdmin}">
            <tr class="uk-table-middle">
                <td>${entrant.id}</td>
                <td>${entrant.firstName}</td>
                <td>${entrant.lastName}</td>
                <td>${entrant.patronymic}</td>
                <td>${entrant.email}</td>
                <td>${entrant.city}</td>
                <td>${entrant.region}</td>
                <td>${entrant.school}</td>
                <td>
                    <c:choose>
                        <c:when test="${entrant.statusId == 3}">
                            <i class="uk-icon-envelope-o uk-text-danger"></i>
                            ${entrant.statusId}
                        </c:when>
                        <c:when test="${entrant.statusId == 2}">
                            <i class="uk-icon-unlock uk-text-success"></i>
                            ${entrant.statusId}
                        </c:when>
                        <c:otherwise>
                            <i class="uk-icon-lock uk-text-danger"></i>
                            ${entrant.statusId}
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${entrant.statusId == 2 || entrant.statusId == 3}">
                        <a href="blockEntrant.do?id="${entrant.id}><i
                                class="uk-float-right uk-icon-ban  uk-text-danger"></i></a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>

    </table>

    <ul class="uk-pagination uk-margin-bottom-remove">
        <c:if test="${sessionScope.currentPage != 1}">
            <li class="uk-pagination-previous">
                <a href="entrants.do?page=${sessionScope.currentPage - 1}">
                    <i class="uk-icon-angle-double-left"></i> Previous
                </a>
            </li>
        </c:if>
        <c:choose>
            <c:when test="${sessionScope.currentPage == 1}">
                <li class="uk-active"><span>${sessionScope.currentPage}</span></li>
            </c:when>
            <c:otherwise>
                <li><a href="entrants.do?page=1">1</a></li>
            </c:otherwise>
        </c:choose>
        <c:if test="${sessionScope.currentPage > 4}">
            <li><span>...</span></li>
        </c:if>
        <c:forEach begin="${sessionScope.currentPage - 4 < 2 ? 2 : sessionScope.currentPage - 4}"
                   end="${sessionScope.currentPage + 4 > sessionScope.numberOfPages ? sessionScope.numberOfPages : sessionScope.currentPage + 4}"
                   var="i">
            <c:choose>
                <c:when test="${sessionScope.currentPage eq i}">
                    <li class="uk-active"><span>${i}</span></li>
                </c:when>
                <c:otherwise>
                    <li class="uk-disabled"><span><a href="entrants.do?page=${i}">${i}</a></span></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${sessionScope.currentPage < sessionScope.numberOfPages - 4}">
            <li><span>...</span></li>
        </c:if>
        <c:if test="${sessionScope.currentPage < sessionScope.numberOfPages - 4}">
            <li><a href="entrants.do?page=${sessionScope.numberOfPages}">${sessionScope.numberOfPages}</a></li>
        </c:if>
        <c:if test="${sessionScope.currentPage lt sessionScope.numberOfPages}">
            <li class="uk-pagination-next">
                <a href="entrants.do?page=${sessionScope.currentPage + 1}">
                    Next <i class="uk-icon-angle-double-right"></i>
                </a>
            </li>
        </c:if>
    </ul>
</div>
</body>
</html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 08/08/15
  Time: 18:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../jspf/header/header.jspf" %>
    <link rel="stylesheet" href="<c:url value="/resources/stylesheets/customCss/formRows.css"/>"/>
    <title></title>
</head>
<body>
<%@include file="../../jspf/topPanel.jspf" %>
<c:set scope="session" value="${param.index}" var="index"/>
<div class="uk-container uk-container-center uk-width-8-10 uk-text-center uk-margin-top ">
    <h2 class="uk-panel-header">
        You can change information about this user.
    </h2>
    <table class="uk-table uk-table-striped uk-table-condensed uk-animation-fade uk-panel-box uk-panel-box-primary">
        <caption>Faculties</caption>
        <thead>
        <tr>
            <th>id</th>
            <th>Faculty name</th>
            <th>Total spots</th>
            <th>Budget spots</th>
            <th>Subjects</th>
        </tr>
        </thead>
        <tbody>
        <c:choose>
            <c:when test="${sessionScope.adminSortType == null || sessionScope.adminSortType =='noSort'}">
                <c:set var="faculty" value="
               ${sessionScope.adminFacultiesInfoBeans[param.index].faculty.id == param.facultyId ?
               sessionScope.adminFacultiesInfoBeans[param.index] : null}"/>
            </c:when>
            <c:otherwise>
                <c:set var="faculty" value="
               ${sessionScope.adminFacultiesInfoBeansSorted[param.index].faculty.id == param.facultyId ?
               sessionScope.adminFacultiesInfoBeansSorted[param.index] : null}"/>
            </c:otherwise>
        </c:choose>
        <tr class="uk-table-middle">
            <td>${
                    sessionScope.adminFacultiesInfoBeansSorted[sessionScope.index].faculty.id == param.facultyId ?
                            sessionScope.adminFacultiesInfoBeansSorted[sessionScope.index].faculty.id :
                            sessionScope.adminFacultiesInfoBeans[sessionScope.index].faculty.id == param.facultyId ?
                                    sessionScope.adminFacultiesInfoBeans[sessionScope.index].faculty.id : ''}
            </td>
            <td>${  sessionScope.adminFacultiesInfoBeansSorted[sessionScope.index].faculty.id == param.facultyId ?
                    sessionScope.adminFacultiesInfoBeansSorted[sessionScope.index].faculty.name :
                    sessionScope.adminFacultiesInfoBeans[sessionScope.index].faculty.id == param.facultyId ?
                            sessionScope.adminFacultiesInfoBeans[sessionScope.index].faculty.name : ''}
            </td>
            <td>${
                    sessionScope.adminFacultiesInfoBeansSorted[sessionScope.index].faculty.id == param.facultyId ?
                            sessionScope.adminFacultiesInfoBeansSorted[sessionScope.index].faculty.totalSpots :
                            sessionScope.adminFacultiesInfoBeans[sessionScope.index].faculty.id == param.facultyId ?
                                    sessionScope.adminFacultiesInfoBeans[sessionScope.index].faculty.totalSpots : ''}
            </td>
            <td>${
                    sessionScope.adminFacultiesInfoBeansSorted[sessionScope.index].faculty.id == param.facultyId ?
                            sessionScope.adminFacultiesInfoBeansSorted[sessionScope.index].faculty.budgetSpots :
                            sessionScope.adminFacultiesInfoBeans[sessionScope.index].faculty.id == param.facultyId ?
                                    sessionScope.adminFacultiesInfoBeans[sessionScope.index].faculty.budgetSpots : ''}
            </td>
            <td>
                <c:forEach var="subject" items="${
                    sessionScope.adminFacultiesInfoBeansSorted[sessionScope.index].faculty.id == param.facultyId ?
                    sessionScope.adminFacultiesInfoBeansSorted[sessionScope.index].subjects :
                    sessionScope.adminFacultiesInfoBeans[sessionScope.index].faculty.id == param.facultyId ?
                    sessionScope.adminFacultiesInfoBeans[sessionScope.index].subjects : ''}">
                    ${subject.name}<br/>
                </c:forEach>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="uk-container uk-container-center uk-width-medium-8-10">
        <form class="uk-form ">

            <div class="uk-grid uk-width-medium-1-1 uk-container-center">
                <label id="nameFaculty-label" class="nameFaculty-label"
                       for="nameFaculty"></label>
                <input aria-labelledby="nameFaculty-label" id="nameFaculty"
                       name="nameFaculty"
                       class="uk-form-row uk-form-width-medium"
                       type="text" autocomplete="off" placeholder="Name faculty: ">

                <div class="uk-alert uk-alert-danger uk-width-medium-1-3 uk-push-2-10 uk-margin uk-margin-top-remove alertSchoolNumber">
                    ...
                </div>
            </div>
            <div class="uk-grid uk-width-medium-1-1 uk-margin-top-remove uk-container-center">
                <div class="uk-form-controls uk-form-controls-text ">
                    <select id="totalSpots" class="uk-form-width-medium uk-float-left">
                        <option value="" disabled selected>Select total spots</option>
                        <c:forEach var="i" begin="1" end="350" step="1">
                            <option>${i}</option>
                        </c:forEach>
                    </select>
                    <label for="totalSpots"></label>

                </div>
                <div class="uk-alert uk-alert-danger uk-width-medium-1-3 uk-push-2-10 uk-margin uk-margin-top-remove alertSchoolNumber">
                    ...
                </div>
            </div>
            <div class="uk-grid uk-width-medium-1-1 uk-margin-top-remove uk-container-center">
                <div class="uk-form-controls uk-form-controls-text ">
                    <select id="budgetSpots" class="uk-form-width-medium uk-float-left">
                        <option value="" disabled selected>Select budget spots</option>
                        <c:forEach var="i" begin="1" end="120" step="1">
                            <option>${i}</option>
                        </c:forEach>
                    </select>
                    <label for="budgetSpots"></label>

                </div>
                <div class="uk-alert uk-alert-danger uk-width-medium-1-3 uk-push-2-10 uk-margin uk-margin-top-remove alertSchoolNumber">
                    ...
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</html>

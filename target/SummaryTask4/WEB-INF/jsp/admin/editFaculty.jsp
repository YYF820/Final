<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<div class="uk-container uk-container-center uk-width-8-10 uk-margin-top ">
    <h2 class="uk-article-title uk-text-center">
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
        <tr class="uk-table-middle">
            <td>${sessionScope.adminFacultyForEdit.faculty.id} </td>
            <td>${sessionScope.adminFacultyForEdit.faculty.name}</td>
            <td>${sessionScope.adminFacultyForEdit.faculty.totalSpots} </td>
            <td>${sessionScope.adminFacultyForEdit.faculty.budgetSpots}
            </td>
            <td>
                <c:forEach var="subject" items="${sessionScope.adminFacultyForEdit.subjects}">
                    ${subject.name}<br/>
                </c:forEach>
            </td>
        </tr>
        </tbody>
    </table>

    <div class="uk-container uk-container-center uk-width-medium-8-10">
        <form class="uk-form uk-margin-top" method="post" action="<c:url value="/admin/editFaculty.do"/>">
            <c:if test="${sessionScope.adminEditIsAllFieldsEmpty == true ||
                sessionScope.adminEditTotalLowerBudget == true ||
                    sessionScope.adminEditIsEnoughSubjects == false}">
                <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-text-center uk-align-center alertSchoolNumber">
                    <c:choose>
                        <c:when test="${sessionScope.adminEditIsAllFieldsEmpty == true}">
                            <p>Please choose minimum one field which you want edit.</p>
                        </c:when>
                        <c:when test="${sessionScope.adminEditTotalLowerBudget == true}">
                            <p>Total spots can't be lower then budget spots.</p>
                        </c:when>
                        <c:when test="${sessionScope.adminEditIsEnoughSubjects == false}">
                            <p>After editing the faculty it must contain at least 3 subjects.</p>
                        </c:when>
                    </c:choose>
                </div>
            </c:if>

            <div class="uk-grid uk-width-medium-1-1 uk-container-center">
                <div class="uk-align-center">
                    <label id="facultyName-label" class="facultyName-label"
                           for="facultyName"></label>
                    <input aria-labelledby="facultyName-label" id="facultyName"
                           name="facultyName"
                           class="uk-form-row uk-form-width-medium"
                           type="text" autocomplete="off" value="${sessionScope.adminEditFacultyName}"
                           placeholder="Faculty name:">
                </div>

                <c:if test="${sessionScope.adminEditIsFacultyNameValid == false}">
                    <div class="uk-alert uk-alert-danger uk-width-medium-1-3
                    k-margin uk-margin-top-remove uk-text-center uk-align-center alertSchoolNumber">
                        Faculty Name is not valid.
                    </div>
                </c:if>

            </div>
            <div class="uk-grid uk-width-medium-1-1 uk-margin-top-remove uk-container-center">
                <div class="uk-form-controls uk-form-controls-text uk-container-center uk-align-center">
                    <select id="totalSpots" class="uk-form-width-medium uk-float-left" name="totalSpots">
                        <option value="${sessionScope.adminEditTotalSpots}">
                            ${sessionScope.adminEditTotalSpots eq "" || sessionScope.adminEditTotalSpots == null ?
                                    'Select total spots' :
                                    sessionScope.adminEditTotalSpots}
                        </option>
                        <c:forEach var="i" begin="1" end="350" step="1">
                            <option>${i}</option>
                        </c:forEach>
                    </select>
                    <label for="totalSpots"></label>
                </div>
            </div>
            <div class="uk-grid uk-width-medium-1-1 uk-margin-top-remove uk-container-center">
                <div class="uk-form-controls uk-form-controls-text uk-align-center">
                    <select id="budgetSpots" class="uk-form-width-medium uk-float-left" name="budgetSpots">
                        <option value="${sessionScope.adminEditBudgetSpots}">
                            ${sessionScope.adminEditBudgetSpots eq "" || sessionScope.adminEditBudgetSpots == null ?
                                    'Select budget spots'  :
                                    sessionScope.adminEditBudgetSpots}
                        </option>
                        <c:forEach var="i" begin="1" end="120" step="1">
                            <option>${i}</option>
                        </c:forEach>
                    </select>
                    <label for="budgetSpots"></label>

                </div>

            </div>


            <c:if test="${fn:length(sessionScope.adminFacultyForEdit.subjects) gt 0}">
                <hr class="uk-article-divider">
                <h4 class="uk-text-center">Subjects to delete</h4>

                <div class="uk-grid uk-width-medium-1-1 uk-margin-top-remove">
                    <div class="uk-form-controls uk-form-controls-text uk-text-left">
                        <c:forEach var="subject" items="${sessionScope.adminFacultyForEdit.subjects}">
                            <label><input type="checkbox" name="subjectsIdToDelete"
                                          value="${subject.id}">${subject.name}
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </c:if>


            <c:if test="${fn:length(sessionScope.adminSubjectsToAdd) gt 0}">
                <h4 class="uk-text-center">Subjects to add</h4>

                <div class="uk-grid uk-width-medium-1-1 uk-margin-top-remove ">
                    <div class="uk-form-controls uk-form-controls-text uk-text-left">
                        <c:forEach var="subject" items="${sessionScope.adminSubjectsToAdd}">
                            <label class="uk-form-label">
                                <input type="checkbox" name="subjectsIdToAdd" value="${subject.id}"> ${subject.name}
                            </label>
                        </c:forEach>
                    </div>
                </div>
            </c:if>


            <div class="uk-grid uk-form-row uk-align-center uk-margin-bottom-remove">
                <button class="uk-width-medium-3-5 uk-button uk-button-primary " type="submit">Update</button>
                <a href="<c:url value="/admin/faculties.do"/>"
                   class="uk-width-medium-1-5 uk-button uk-button-success uk-push-2-10">Cancel</a>
            </div>

        </form>
    </div>
</div>
</body>
</html>

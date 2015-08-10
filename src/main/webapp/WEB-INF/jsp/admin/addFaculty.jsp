<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 09/08/15
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="../../../resources/stylesheets/customCss/formRows.css"/>
    <%@include file="../../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<%@include file="../../jspf/topPanel.jspf" %>
<div class="uk-container uk-container-center uk-width-8-10 uk-text-center uk-margin-top ">
    <p class="uk-article-title">You can add new Faculty<i class="uk-icon-university uk-alert-large"></i></p>

    <form class="uk-form uk-margin-large-top uk-animation-fade" method="post"
          action="<c:url value="/admin/addFaculty.do"/>">

        <c:if test="${sessionScope.adminAddIsAnyEmptyFields ||
            sessionScope.adminAddIsFacultyNameValid == false ||
                sessionScope.adminAddTotalLowerBudget == true ||
                    sessionScope.adminAddIsDuplicateFacultyName == true ||
                        sessionScope.adminAddIsEnoughSubjects == false}">
            <div class="uk-alert uk-width-medium-3-5 alertSchoolNumber uk-alert-danger uk-container-center">
                <c:if test="${sessionScope.adminAddIsAnyEmptyFields}">
                    <p>Please fill all fields.</p>
                </c:if>
                <c:if test="${sessionScope.adminAddIsFacultyNameValid == false}">
                    <p>Faculty name is not valid.</p>
                </c:if>
                <c:if test="${sessionScope.adminAddTotalLowerBudget == true}">
                    <p>Total spots can't be lower then budget spots.</p>
                </c:if>
                <c:if test="${sessionScope.adminAddIsDuplicateFacultyName == true}">
                    <p>Choose another faculty name that already exists.</p>
                </c:if>
                <c:if test="${sessionScope.adminAddIsEnoughSubjects == false}">
                    <p>You must choose minimum 3 subjects for Faculty.</p>
                </c:if>
            </div>
        </c:if>

        <div class="uk-width-1-1 uk-container-center">
            <label id="facultyName-label" class="facultyName-label"
                   for="facultyName"></label>
            <input aria-labelledby="facultyName-label" id="facultyName"
                   name="facultyName"
                   class="uk-form-width-medium uk-container-center"
                   type="text" autocomplete="off" value="${sessionScope.adminAddFacultyName}"
                   placeholder="Faculty name:">
        </div>
        <div class="uk-form-controls uk-form-controls-text uk-container-center uk-align-center uk-margin-top">
            <select id="totalSpots" class="uk-form-width-medium " name="totalSpots">
                <option value="${sessionScope.adminAddTotalSpots}">
                    ${sessionScope.adminAddTotalSpots eq "" || sessionScope.adminAddTotalSpots == null ?
                            'Select total spots' :
                            sessionScope.adminAddTotalSpots}
                </option>
                <c:forEach var="i" begin="1" end="350" step="1">
                    <option>${i}</option>
                </c:forEach>
            </select>
            <label for="totalSpots"></label>
        </div>
        <div class=" uk-width-medium-1-1 uk-form-controls uk-form-controls-text uk-margin-top">
            <select id="budgetSpots" class="uk-form-width-medium" name="budgetSpots">
                <option value="${sessionScope.adminAddBudgetSpots}">
                    ${sessionScope.adminAddBudgetSpots eq "" || sessionScope.adminAddBudgetSpots == null ?
                            'Select budget spots'  :
                            sessionScope.adminAddBudgetSpots}
                </option>
                <c:forEach var="i" begin="1" end="120" step="1">
                    <option>${i}</option>
                </c:forEach>
            </select>
            <label for="budgetSpots"></label>
        </div>

        <c:if test="${fn:length(sessionScope.adminAddAllSubjects) gt 0}">
            <div class="uk-form-controls uk-form-controls-text uk-container uk-container-center
                uk-text-left uk-panel-box uk-width-1-5 uk-margin-top">
                <c:forEach var="subject" items="${sessionScope.adminAddAllSubjects}">
                    <label class="uk-form-label">
                        <input type="checkbox" name="subjectsIdToAdd" value="${subject.id}"> ${subject.name}
                    </label>
                    <br/>
                </c:forEach>
            </div>
        </c:if>
        <div class=" uk-width-4-5 uk-container uk-container-center">
            <button class="uk-width-medium-1-5 uk-button uk-button-large uk-button-primary uk-margin-top"
                    type="submit">Create
            </button>
        </div>
        <div class="uk-width-4-5 uk-margin-top uk-container uk-container-center">
            <a href="<c:url value="/admin/faculties.do"/>"
               class="uk-width-medium-3-5 uk-button uk-button-success">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>

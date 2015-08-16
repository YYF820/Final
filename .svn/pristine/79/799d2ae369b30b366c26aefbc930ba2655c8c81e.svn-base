<%@include file="../../jspf/imports.jspf" %>
<html>
<head>
    <link rel="stylesheet" href="../../../resources/stylesheets/customCss/formRows.css"/>
    <%@include file="../../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<%@include file="../../jspf/topPanel.jspf" %>
<div class="uk-container uk-container-center uk-width-8-10 uk-text-center uk-margin-top ">
    <p class="uk-article-title">
        <fmt:message key="faculties.admin.add.head"/>
        <i class="uk-icon-university uk-alert-large"></i>
    </p>

    <form class="uk-form uk-margin-large-top uk-animation-fade" method="post"
          action="<c:url value="/admin/addFaculty.do"/>">

        <c:if test="${sessionScope.adminAddIsAnyEmptyFields ||
            sessionScope.adminAddIsFacultyNameValid == false ||
                sessionScope.adminAddTotalLowerBudget == true ||
                    sessionScope.adminAddIsDuplicateFacultyName == true ||
                        sessionScope.adminAddIsEnoughSubjects == false}">
            <div class="uk-alert uk-width-medium-3-5 alertSchoolNumber uk-alert-danger uk-container-center">
                <c:if test="${sessionScope.adminAddIsAnyEmptyFields}">
                    <p><fmt:message key="faculties.admin.add.error.fill.all.fields"/></p>
                </c:if>
                <c:if test="${sessionScope.adminAddIsFacultyNameValid == false}">
                    <p><fmt:message key="faculties.admin.add.error.not.valid.faculty.name"/></p>
                </c:if>
                <c:if test="${sessionScope.adminAddTotalLowerBudget == true}">
                    <p><fmt:message key="faculties.admin.add.error.check.spots"/></p>
                </c:if>
                <c:if test="${sessionScope.adminAddIsDuplicateFacultyName == true}">
                    <p><fmt:message key="faculties.admin.add.error.exists.faculty.name"/></p>
                </c:if>
                <c:if test="${sessionScope.adminAddIsEnoughSubjects == false}">
                    <p><fmt:message key="faculties.admin.add.error.minimum.three.subjects"/></p>
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
                   placeholder="<fmt:message key="faculty.name"/>">
        </div>
        <div class="uk-form-controls uk-form-controls-text uk-container-center uk-align-center uk-margin-top">
            <select id="totalSpots" class="uk-form-width-medium " name="totalSpots">
                <option value="${sessionScope.adminAddTotalSpots}">
                    <c:choose>
                        <c:when test="${sessionScope.adminEditTotalSpots eq '' || sessionScope.adminEditTotalSpots == null}">
                            <fmt:message key="faculties.admin.edit.select.total.spots"/>
                        </c:when>
                        <c:otherwise>
                            ${sessionScope.adminEditTotalSpots}
                        </c:otherwise>
                    </c:choose>
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
                    <c:choose>
                        <c:when test="${sessionScope.adminEditBudgetSpots eq '' || sessionScope.adminEditBudgetSpots == null}">
                            <fmt:message key="faculties.admin.edit.select.budget.spots"/>
                        </c:when>
                        <c:otherwise>
                            ${sessionScope.adminEditBudgetSpots}
                        </c:otherwise>
                    </c:choose>
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
                        <input type="checkbox" name="subjectsIdToAdd" value="${subject.id}">
                        <fmt:message key="${subject.name}"/>
                    </label>
                    <br/>
                </c:forEach>
            </div>
        </c:if>
        <div class=" uk-width-4-5 uk-container uk-container-center">
            <button class="uk-width-medium-1-5 uk-button uk-button-large uk-button-primary uk-margin-top"
                    type="submit">
                <fmt:message key="button.create"/>
            </button>
        </div>
        <div class="uk-width-4-5 uk-margin-top uk-container uk-container-center">
            <a href="<c:url value="/admin/faculties.do"/>"
               class="uk-width-medium-3-5 uk-button uk-button-success">
                <fmt:message key="button.cancel"/>
            </a>
        </div>
    </form>
</div>
</body>
</html>

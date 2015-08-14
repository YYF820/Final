<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 11/08/15
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <%@include file="../jspf/header/header.jspf" %>
    <link rel="stylesheet" href="../../resources/stylesheets/customCss/alertNormalise.css"/>
    <title></title>
</head>
<body>
<%@ include file="../jspf/topPanel.jspf" %>
<div class="uk-container uk-container-center uk-margin-large-bottom">
    <%@ include file="../jspf/logo.jspf" %>
    <%@ include file="../jspf/navAndLogin.jspf" %>
    <u:ifAuthAs role="entrant">
        <div class="uk-grid uk-text-center uk-margin-top">
            <div class="uk-width-medium-2-10">
                <a href="#sortFacultiesEntrant" data-uk-offcanvas></a>
                <!-- This is a button toggling the off-canvas sidebar -->
                <button class="uk-button uk-float-left" data-uk-offcanvas="{target:'#sortFacultiesEntrant'}">
                    Sort settings <i class="uk-icon-cog uk-text-primary"></i>
                </button>
                <%@include file="../jspf/canvas/sortFacultiesEntrant.jsp" %>
            </div>
            <div class="uk-width-medium-6-10">
                <c:choose>
                    <c:when test="${sessionScope.entrantHowManyMoreSubjectsNeed != 0 || sessionScope.entrantAccountSettingsExtraMarks == null }">
                        <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-margin-bottom-remove alert">
                            <p>You should set up your account <a href="<c:url value="/accountSettings.html"/>">Account
                                settings <i
                                        class="uk-icon-external-link"></i></a></p>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.facultiesIsAnyProblemsUpdate == true}">
                        <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-margin-bottom-remove alert">
                            <p>Some problems while configuring priorities please try again later.</p>
                        </div>
                        <c:remove var="facultiesIsAnyProblemsUpdate" scope="session"/>
                    </c:when>
                    <c:when test="${sessionScope.facultiesIsAnyProblemsUpdate == false}">
                        <div class="uk-alert uk-alert-success uk-margin-top-remove uk-margin-bottom-remove alert">
                            <p>Success configuring priorities <i class="ui-icon-check"></i></p>
                        </div>
                        <c:remove var="facultiesIsAnyProblemsUpdate" scope="session"/>
                    </c:when>
                    <c:when test="${sessionScope.facultiesIsSamePriorities == true}">
                        <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-margin-bottom-remove alert">
                            <p>You can't set same priority for 2 or more faculties, please try again.</p>
                        </div>
                        <c:remove var="facultiesIsSamePriorities" scope="session"/>
                    </c:when>
                </c:choose>
            </div>
            <div class="uk-width-medium-2-10">
                <a href="#setUpPriorities" data-uk-offcanvas></a>
                <button class="uk-button uk-float-right" data-uk-offcanvas="{target:'#setUpPriorities'}">
                    Configure priorities <i class="uk-icon-cog uk-text-primary"></i>
                </button>
                <%@include file="../jspf/canvas/setUpPriorities.jsp" %>
            </div>
        </div>
        <c:if test="${sessionScope.facultiesIsSorted == false}">
            <div class="uk-width-medium-1-10 uk-margin-top uk-text-center">
                <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-margin-bottom-remove alert">
                    <p>Please choose sort option.</p>
                </div>
                <c:remove var="facultiesIsSorted" scope="session"/>
            </div>
        </c:if>
    </u:ifAuthAs>

    <u:ifAuthAs role="guest">
        <div class="uk-grid uk-text-center uk-margin-top">
            <div class="uk-width-medium-2-10">
                <a href="#sortFacultiesEntrant" data-uk-offcanvas></a>
                <!-- This is a button toggling the off-canvas sidebar -->
                <button class="uk-button uk-float-left" data-uk-offcanvas="{target:'#sortFacultiesEntrant'}">
                    Sort settings <i class="uk-icon-cog uk-text-primary"></i>
                </button>
                <%@include file="../jspf/canvas/sortFacultiesEntrant.jsp" %>

            </div>
            <div class="uk-width-medium-6-10">
                <div class="uk-alert uk-alert-warning uk-margin-top-remove
             uk-margin-bottom-remove alert">
                    In order to register on the faculties you must be logged in.
                    <a href="<c:url value="/login.html"/>">Log in <i class="uk-icon-external-link"></i></a>
                </div>
            </div>
        </div>
        <c:if test="${sessionScope.facultiesIsSorted == false}">
            <div class="uk-width-medium-1-10 uk-margin-top uk-text-center">
                <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-margin-bottom-remove alert">
                    <p>Please choose sort option.</p>
                </div>
                <c:remove var="facultiesIsSorted" scope="session"/>
            </div>
        </c:if>
    </u:ifAuthAs>

    <u:ifAuthAs role="admin">
        <div class="uk-grid uk-text-center uk-margin-top">
            <div class="uk-width-medium-2-10">
                <a href="#sortFacultiesEntrant" data-uk-offcanvas></a>
                <!-- This is a button toggling the off-canvas sidebar -->
                <button class="uk-button uk-float-left" data-uk-offcanvas="{target:'#sortFacultiesEntrant'}">
                    Sort settings <i class="uk-icon-cog uk-text-primary"></i>
                </button>
                <%@include file="../jspf/canvas/sortFacultiesEntrant.jsp" %>
            </div>
            <c:if test="${sessionScope.facultiesIsSorted == false}">
                <div class="uk-width-medium-6-10">
                    <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-margin-bottom-remove alert">
                        <p>Please choose sort option.</p>
                    </div>
                    <c:remove var="facultiesIsSorted" scope="session"/>
                </div>
            </c:if>
        </div>
    </u:ifAuthAs>

    <c:forEach var="facultyBean" items="${sessionScope.facultiesInfoBeansPagination}" varStatus="counter" step="1">
    <c:choose>
    <c:when test="${counter.index % 2 == 0}">
        <div class="uk-grid uk-margin-large-top" data-uk-grid-margin="">
            <div class="uk-width-medium-1-2">
                <img width="660" height="400"
                     src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4NCjwhLS0gR2VuZXJhdG9yOiBBZG9iZSBJbGx1c3RyYXRvciAxNi4wLjQsIFNWRyBFeHBvcnQgUGx1Zy1JbiAuIFNWRyBWZXJzaW9uOiA2LjAwIEJ1aWxkIDApICAtLT4NCjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+DQo8c3ZnIHZlcnNpb249IjEuMSIgaWQ9IkViZW5lXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB3aWR0aD0iNjYwcHgiIGhlaWdodD0iNDAwcHgiIHZpZXdCb3g9IjAgMCA2NjAgNDAwIiBlbmFibGUtYmFja2dyb3VuZD0ibmV3IDAgMCA2NjAgNDAwIiB4bWw6c3BhY2U9InByZXNlcnZlIj4NCjxyZWN0IGZpbGw9IiNGNUY1RjUiIHdpZHRoPSI2NjAiIGhlaWdodD0iNDAwIi8+DQo8ZyBvcGFjaXR5PSIwLjciPg0KCTxwYXRoIGZpbGw9IiNEOEQ4RDgiIGQ9Ik0yNTguMTg0LDE0My41djExM2gxNDMuNjMydi0xMTNIMjU4LjE4NHogTTM5MC4yNDQsMjQ0LjI0N0gyNzAuNDM3di04OC40OTRoMTE5LjgwOEwzOTAuMjQ0LDI0NC4yNDcNCgkJTDM5MC4yNDQsMjQ0LjI0N3oiLz4NCgk8cG9seWdvbiBmaWxsPSIjRDhEOEQ4IiBwb2ludHM9IjI3Ni44ODEsMjM0LjcxNyAzMDEuNTcyLDIwOC43NjQgMzEwLjgyNCwyMTIuNzY4IDM0MC4wMTYsMTgxLjY4OCAzNTEuNTA1LDE5NS40MzQgDQoJCTM1Ni42ODksMTkyLjMwMyAzODQuNzQ2LDIzNC43MTcgCSIvPg0KCTxjaXJjbGUgZmlsbD0iI0Q4RDhEOCIgY3g9IjMwNS40MDUiIGN5PSIxNzguMjU3IiByPSIxMC43ODciLz4NCjwvZz4NCjwvc3ZnPg0K"
                     alt="">
            </div>
            <div class="uk-width-medium-1-2">
                <h1>${facultyBean.faculty.name}</h1>

                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut
                    labore
                    et
                    dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi
                    ut
                    aliquip
                    ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                    cillum
                    dolore eu
                    fugiat nulla pariatur.</p>

                <h2>Subheading</h2>

                <div class="uk-grid">

                    <p>Total spots: ${facultyBean.faculty.totalSpots} Budget
                        spots: ${facultyBean.faculty.budgetSpots}
                        and you must have this subjects:

                    <div class="uk-width-medium-2-6 uk-margin-top">
                        <ul>
                            <c:forEach var="subject" items="${facultyBean.subjects}">
                                <li>${subject.name}</li>
                            </c:forEach>
                        </ul>

                    </div>
                    </p>
                    <u:ifAuthAs role="entrant">
                        <c:choose>
                            <c:when test="${sessionScope.entrantHowManyMoreSubjectsNeed == 0 && sessionScope.entrantAccountSettingsExtraMarks != null &&
                        sessionScope.facultiesUsedFacultiesIdPriority.containsKey(facultyBean.faculty.id) == false && fn:length(sessionScope.facultiesAvailablePropertiesList) gt 0}">
                                <div class="uk-grid uk-width-medium-2-5 uk-margin-large-top">
                                    <form class="uk-form" method="post"
                                          action="<c:url value="/entrant/addFaculty.do"/>">
                                        <label class="uk-form-label">
                                            Select priority:
                                            <select name="priority" class="uk-form-small">
                                                <c:forEach var="priority"
                                                           items="${sessionScope.facultiesAvailablePropertiesList}">
                                                    <option>${priority}</option>
                                                </c:forEach>
                                            </select>
                                        </label>
                                        <button class="uk-button uk-button-mini uk-button-primary uk-push-1-10"
                                                type="submit">Enroll
                                        </button>
                                        <input type="hidden" name="facultyId" value="${facultyBean.faculty.id}">
                                        <input type="hidden" name="entrantId"
                                               value="${sessionScope.facultiesEntrantEntity.id}">
                                    </form>
                                </div>
                            </c:when>
                            <c:when test="${fn:length(sessionScope.facultiesAvailablePropertiesList) == 0 && sessionScope.facultiesUsedFacultiesIdPriority.containsKey(facultyBean.faculty.id) == false}">
                                <div class="uk-alert uk-alert-warning uk-width-medium-2-5">
                                    <p>You have already enrolled to the 3 faculties, to enroll you should unsubscribe
                                        from another faculty</p>
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.facultiesUsedFacultiesIdPriority.containsKey(facultyBean.faculty.id) == true}">
                                <div class="uk-alert uk-alert-success uk-width-medium-2-5">
                                    <p class="uk-margin-bottom-remove">You are enrolled to this faculty with priority
                                            ${sessionScope.facultiesUsedFacultiesIdPriority[facultyBean.faculty.id]},
                                        you
                                        can
                                        unsubscribe
                                        from it.</p>

                                    <div class="uk-text-right ">
                                        <a href="<c:url value="/entrant/deleteFaculty.do?entrantId=${sessionScope.facultiesEntrantEntity.id}&facultyId=${facultyBean.faculty.id}"/>"
                                           class="uk-text-danger">Unsubscribe <i
                                                class="uk-icon-remove uk-text-danger"></i></a>
                                    </div>
                                </div>
                            </c:when>
                        </c:choose>
                    </u:ifAuthAs>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
    <div class="uk-grid ">
        <div class="uk-width-medium-1-2">
            <h1>${facultyBean.faculty.name}</h1>

            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut
                labore
                et
                dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi
                ut
                aliquip
                ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
                cillum
                dolore eu
                fugiat nulla pariatur.</p>

            <h2>Subheading</h2>

            <div class="uk-grid">

                <p>Total spots: ${facultyBean.faculty.totalSpots} Budget
                    spots: ${facultyBean.faculty.budgetSpots}
                    and you must have this subjects:

                <div class="uk-width-medium-2-6">
                    <ul>
                        <c:forEach var="subject" items="${facultyBean.subjects}">
                            <li>${subject.name}</li>
                        </c:forEach>
                    </ul>

                </div>
                </p>
                <u:ifAuthAs role="entrant">
                    <c:choose>
                        <c:when test="${sessionScope.entrantHowManyMoreSubjectsNeed == 0 && sessionScope.entrantAccountSettingsExtraMarks != null &&
                        sessionScope.facultiesUsedFacultiesIdPriority.containsKey(facultyBean.faculty.id) == false && fn:length(sessionScope.facultiesAvailablePropertiesList) gt 0}">
                            <div class="uk-grid uk-width-medium-2-5 uk-margin-large-top">
                                <form class="uk-form" method="post"
                                      action="<c:url value="/entrant/addFaculty.do"/>">
                                    <label class="uk-form-label">
                                        Select priority:
                                        <select name="priority" class="uk-form-small">
                                            <c:forEach var="priority"
                                                       items="${sessionScope.facultiesAvailablePropertiesList}">
                                                <option>${priority}</option>
                                            </c:forEach>
                                        </select>
                                    </label>
                                    <button class="uk-button uk-button-mini uk-button-primary uk-push-1-10"
                                            type="submit">Enroll
                                    </button>
                                    <input type="hidden" name="facultyId" value="${facultyBean.faculty.id}">
                                    <input type="hidden" name="entrantId"
                                           value="${sessionScope.facultiesEntrantEntity.id}">
                                </form>
                            </div>
                        </c:when>
                        <c:when test="${fn:length(sessionScope.facultiesAvailablePropertiesList) == 0 && sessionScope.facultiesUsedFacultiesIdPriority.containsKey(facultyBean.faculty.id) == false}">
                            <div class="uk-alert uk-alert-warning uk-width-medium-2-5">
                                <p>You have already enrolled to the 3 faculties, to enroll you should unsubscribe
                                    from another faculty</p>
                            </div>
                        </c:when>
                        <c:when test="${sessionScope.facultiesUsedFacultiesIdPriority.containsKey(facultyBean.faculty.id) == true}">
                            <div class="uk-alert uk-alert-success uk-width-medium-2-5">
                                <p class="uk-margin-bottom-remove">You are enrolled to this faculty with priority
                                        ${sessionScope.facultiesUsedFacultiesIdPriority[facultyBean.faculty.id]}, you
                                    can
                                    unsubscribe
                                    from it.</p>

                                <div class="uk-text-right ">
                                    <a href="<c:url value="/entrant/deleteFaculty.do?entrantId=${sessionScope.facultiesEntrantEntity.id}&facultyId=${facultyBean.faculty.id}"/>"
                                       class="uk-text-danger">Unsubscribe <i
                                            class="uk-icon-remove uk-text-danger"></i></a>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>
                </u:ifAuthAs>
            </div>
        </div>
        <div class="uk-width-medium-1-2">
            <img width="660" height="400"
                 src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz4NCjwhLS0gR2VuZXJhdG9yOiBBZG9iZSBJbGx1c3RyYXRvciAxNi4wLjQsIFNWRyBFeHBvcnQgUGx1Zy1JbiAuIFNWRyBWZXJzaW9uOiA2LjAwIEJ1aWxkIDApICAtLT4NCjwhRE9DVFlQRSBzdmcgUFVCTElDICItLy9XM0MvL0RURCBTVkcgMS4xLy9FTiIgImh0dHA6Ly93d3cudzMub3JnL0dyYXBoaWNzL1NWRy8xLjEvRFREL3N2ZzExLmR0ZCI+DQo8c3ZnIHZlcnNpb249IjEuMSIgaWQ9IkViZW5lXzEiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgeG1sbnM6eGxpbms9Imh0dHA6Ly93d3cudzMub3JnLzE5OTkveGxpbmsiIHg9IjBweCIgeT0iMHB4Ig0KCSB3aWR0aD0iNjYwcHgiIGhlaWdodD0iNDAwcHgiIHZpZXdCb3g9IjAgMCA2NjAgNDAwIiBlbmFibGUtYmFja2dyb3VuZD0ibmV3IDAgMCA2NjAgNDAwIiB4bWw6c3BhY2U9InByZXNlcnZlIj4NCjxyZWN0IGZpbGw9IiNGNUY1RjUiIHdpZHRoPSI2NjAiIGhlaWdodD0iNDAwIi8+DQo8ZyBvcGFjaXR5PSIwLjciPg0KCTxwYXRoIGZpbGw9IiNEOEQ4RDgiIGQ9Ik0yNTguMTg0LDE0My41djExM2gxNDMuNjMydi0xMTNIMjU4LjE4NHogTTM5MC4yNDQsMjQ0LjI0N0gyNzAuNDM3di04OC40OTRoMTE5LjgwOEwzOTAuMjQ0LDI0NC4yNDcNCgkJTDM5MC4yNDQsMjQ0LjI0N3oiLz4NCgk8cG9seWdvbiBmaWxsPSIjRDhEOEQ4IiBwb2ludHM9IjI3Ni44ODEsMjM0LjcxNyAzMDEuNTcyLDIwOC43NjQgMzEwLjgyNCwyMTIuNzY4IDM0MC4wMTYsMTgxLjY4OCAzNTEuNTA1LDE5NS40MzQgDQoJCTM1Ni42ODksMTkyLjMwMyAzODQuNzQ2LDIzNC43MTcgCSIvPg0KCTxjaXJjbGUgZmlsbD0iI0Q4RDhEOCIgY3g9IjMwNS40MDUiIGN5PSIxNzguMjU3IiByPSIxMC43ODciLz4NCjwvZz4NCjwvc3ZnPg0K"
                 alt="">
        </div>
    </div>
</div>
</c:otherwise>
</c:choose>

</c:forEach>
<ul class="uk-pagination uk-margin-large-top">
    <c:choose>
        <c:when test="${sessionScope.currentPage == 1}">
            <li class="uk-active"><span>${sessionScope.currentPage}</span></li>
        </c:when>
        <c:otherwise>
            <li><a href="<c:url value="/faculties.html?page=1"/>">1
            </a>
            </li>
        </c:otherwise>
    </c:choose>
    <c:if test="${sessionScope.currentPage > 3}">
        <li><span>...</span></li>
    </c:if>
    <c:forEach begin="${sessionScope.currentPage - 3 < 2 ? 2 : sessionScope.currentPage - 3}"
               end="${sessionScope.currentPage + 3 > sessionScope.numberOfPages ? sessionScope.numberOfPages : sessionScope.currentPage + 3}"
               var="i">
        <c:choose>
            <c:when test="${sessionScope.currentPage eq i}">
                <li class="uk-active"><span>${i}</span></li>
            </c:when>
            <c:otherwise>
                <li class="uk-disabled">
                                <span>
                                    <a href="<c:url value="/faculties.html?page=${i}"/>">
                                            ${i}
                                    </a>
                                </span>
                </li>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</ul>
<%@ include file="../jspf/footer.jspf" %>
</div>
</body>
</html>

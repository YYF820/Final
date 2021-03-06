<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@include file="../jspf/imports.jspf" %>
<html>
<head>
    <%@include file="../jspf/header/header.jspf" %>
    <link rel="stylesheet" href="../../resources/stylesheets/customCss/alertNormalise.css"/>
    <title></title>
</head>
<body>
<c:import var="facultiesLogoInfo" url="/resources/xml/facultiesLogo.xml"/>
<x:parse xml="${facultiesLogoInfo}" var="output"/>
<%@ include file="../jspf/topPanel.jspf" %>
<div class="uk-container uk-container-center uk-margin-large-bottom ">
    <%@ include file="../jspf/logo.jspf" %>
    <%@ include file="../jspf/navAndLogin.jspf" %>
    <u:ifAuthAs role="entrant">
        <div class="uk-grid uk-text-center uk-margin-large-top">
            <div class="uk-width-medium-2-10">

                <a href="#sortFacultiesEntrant" data-uk-offcanvas></a>
                <!-- This is a button toggling the off-canvas sidebar -->
                <button class="uk-button uk-float-left" data-uk-offcanvas="{target:'#sortFacultiesEntrant'}">
                    <fmt:message key="faculties.public.sort.button"/> <i class="uk-icon-cog uk-text-primary"></i>
                </button>
                <%@include file="../jspf/canvas/sortFacultiesEntrant.jspf" %>
            </div>
            <div class="uk-width-medium-6-10">
                <c:choose>
                    <c:when test="${sessionScope.entrantHowManyMoreSubjectsNeed != 0 || sessionScope.entrantAccountSettingsExtraMarks == null }">
                        <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-margin-bottom-remove alert">
                            <p>
                                <fmt:message key="faculties.public.alert.need.setup.account"/>
                                <a href="<c:url value="/accountSettings.html"/>">
                                    <fmt:message key="faculties.public.alert.account.settings.link"/>
                                    <i class="uk-icon-external-link"></i>
                                </a>
                            </p>
                        </div>
                    </c:when>
                    <c:when test="${sessionScope.facultiesIsAnyProblemsUpdate == true}">
                        <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-margin-bottom-remove alert">
                            <p><fmt:message key="faculties.public.alert.server.error.configure.priorities"/></p>
                        </div>
                        <c:remove var="facultiesIsAnyProblemsUpdate" scope="session"/>
                    </c:when>
                    <c:when test="${sessionScope.facultiesIsAnyProblemsUpdate == false}">
                        <div class="uk-alert uk-alert-success uk-margin-top-remove uk-margin-bottom-remove alert">
                            <p><fmt:message key="faculties.public.success.configure.priorities"/>
                                <i class="ui-icon-check"></i>
                            </p>
                        </div>
                        <c:remove var="facultiesIsAnyProblemsUpdate" scope="session"/>
                    </c:when>
                    <c:when test="${sessionScope.facultiesIsSamePriorities == true}">
                        <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-margin-bottom-remove alert">
                            <p><fmt:message key="faculties.public.alert.error.same.priorities"/></p>
                        </div>
                        <c:remove var="facultiesIsSamePriorities" scope="session"/>
                    </c:when>
                    <c:when test="${sessionScope.facultiesIsSameSubjects == false}">
                        <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-margin-bottom-remove alert">
                            <p><fmt:message key="faculties.public.alert.not.same.subjects"/></p>
                        </div>
                        <c:remove var="facultiesIsSameSubjects" scope="session"/>
                    </c:when>
                </c:choose>
            </div>
            <div class="uk-width-medium-2-10">
                <a href="#setUpPriorities" data-uk-offcanvas></a>
                <button class="uk-button uk-float-right" data-uk-offcanvas="{target:'#setUpPriorities'}">
                    <fmt:message key="faculties.public.configure.priorities"/>
                    <i class="uk-icon-cog uk-text-primary"></i>
                </button>
                <%@include file="../jspf/canvas/setUpPriorities.jspf" %>
            </div>
        </div>
        <c:if test="${sessionScope.facultiesIsSorted == false}">
            <div class="uk-width-medium-1-10 uk-margin-top uk-text-center">
                <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-margin-bottom-remove alert">
                    <p><fmt:message key="faculties.public.alert.no.sort.option"/></p>
                </div>
                <c:remove var="facultiesIsSorted" scope="session"/>
            </div>
        </c:if>
    </u:ifAuthAs>

    <u:ifAuthAs role="guest">
        <div class="uk-grid uk-text-center uk-margin-large-top">
            <div class="uk-width-medium-2-10">
                <a href="#sortFacultiesEntrant" data-uk-offcanvas></a>
                <!-- This is a button toggling the off-canvas sidebar -->
                <button class="uk-button uk-float-left" data-uk-offcanvas="{target:'#sortFacultiesEntrant'}">
                    <fmt:message key="faculties.public.sort.button"/>
                    <i class="uk-icon-cog uk-text-primary"></i>
                </button>
                <%@include file="../jspf/canvas/sortFacultiesEntrant.jspf" %>

            </div>
            <div class="uk-width-medium-6-10">
                <div class="uk-alert uk-alert-warning uk-margin-top-remove
             uk-margin-bottom-remove alert">
                    <fmt:message key="faculties.public.alert.not.logged.in"/>
                    <a href="<c:url value="/login.html"/>">
                        <fmt:message key="faculties.public.log.in"/>
                        <i class="uk-icon-external-link"></i>
                    </a>
                </div>
            </div>
        </div>
        <c:if test="${sessionScope.facultiesIsSorted == false}">
            <div class="uk-width-medium-1-10 uk-margin-top uk-text-center">
                <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-margin-bottom-remove alert">
                    <p><fmt:message key="faculties.public.alert.no.sort.option"/></p>
                </div>
                <c:remove var="facultiesIsSorted" scope="session"/>
            </div>
        </c:if>
    </u:ifAuthAs>

    <u:ifAuthAs role="admin">
        <div class="uk-grid uk-text-center uk-margin-large-top">
            <div class="uk-width-medium-2-10">
                <a href="#sortFacultiesEntrant" data-uk-offcanvas></a>
                <!-- This is a button toggling the off-canvas sidebar -->
                <button class="uk-button uk-float-left" data-uk-offcanvas="{target:'#sortFacultiesEntrant'}">
                    <fmt:message key="faculties.public.sort.button"/> <i class="uk-icon-cog uk-text-primary"></i>
                </button>
                <%@include file="../jspf/canvas/sortFacultiesEntrant.jspf" %>
            </div>
            <c:if test="${sessionScope.facultiesIsSorted == false}">
                <div class="uk-width-medium-6-10">
                    <div class="uk-alert uk-alert-danger uk-margin-top-remove uk-margin-bottom-remove alert">
                        <p><fmt:message key="faculties.public.alert.no.sort.option"/></p>
                    </div>
                    <c:remove var="facultiesIsSorted" scope="session"/>
                </div>
            </c:if>
        </div>
    </u:ifAuthAs>
    <c:forEach var="facultyBean" items="${sessionScope.facultiesInfoBeansPagination}" varStatus="counter" step="1">
    <c:set var="facultyName" scope="page" value="${facultyBean.faculty.name}"/>
    <c:choose>

    <c:when test="${counter.index % 2 == 0}">
        <div class="uk-grid uk-margin-large-top" data-uk-grid-margin="">
            <div class="uk-width-medium-1-2">

                <img width="660" height="400"
                     src="<x:out select="$output/logos/logo[@facultyName=$pageScope:facultyName]/src"/>"
                     alt="">

            </div>
            <div class="uk-width-medium-1-2">
                <h1>${facultyBean.faculty.name}</h1>

                <p><fmt:message
                        key="${fn:toLowerCase(fn:replace(facultyBean.faculty.name,' ', '.')).concat('.faq')}"/></p>

                <h2><fmt:message key="faculties.public.info"/></h2>

                <div class="uk-grid">

                    <p>
                            <fmt:message key="faculties.public.total.spots"/> ${facultyBean.faculty.totalSpots}
                            <fmt:message key="faculties.public.budget.spots"/> ${facultyBean.faculty.budgetSpots}
                            <fmt:message key="faculties.public.message.last.words"/>


                    <div class="uk-width-medium-2-6 uk-margin-top">
                        <ul>
                            <c:forEach var="subject" items="${facultyBean.subjects}">
                                <li><fmt:message key="${subject.name}"/></li>
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
                                            <fmt:message key="faculties.public.select.priority"/>
                                            <select name="priority" class="uk-form-small">
                                                <c:forEach var="priority"
                                                           items="${sessionScope.facultiesAvailablePropertiesList}">
                                                    <option>${priority}</option>
                                                </c:forEach>
                                            </select>
                                        </label>
                                        <button class="uk-button uk-button-mini uk-button-primary uk-push-1-10"
                                                type="submit">
                                            <fmt:message key="faculties.public.button.enroll"/>
                                        </button>
                                        <input type="hidden" name="facultyId" value="${facultyBean.faculty.id}">
                                        <input type="hidden" name="entrantId"
                                               value="${sessionScope.facultiesEntrantEntity.id}">
                                    </form>
                                </div>
                            </c:when>
                            <c:when test="${fn:length(sessionScope.facultiesAvailablePropertiesList) == 0 && sessionScope.facultiesUsedFacultiesIdPriority.containsKey(facultyBean.faculty.id) == false}">
                                <div class="uk-alert uk-alert-warning uk-width-medium-2-5">
                                    <p><fmt:message key="faculties.public.entrant.already.three.faculties"/></p>
                                </div>
                            </c:when>
                            <c:when test="${sessionScope.facultiesUsedFacultiesIdPriority.containsKey(facultyBean.faculty.id) == true}">
                                <div class="uk-alert uk-alert-success uk-width-medium-2-5">
                                    <p class="uk-margin-bottom-remove">
                                        <fmt:message key="faculties.public.entrant.unsubscribe.faculty.start"/>
                                            ${sessionScope.facultiesUsedFacultiesIdPriority[facultyBean.faculty.id]},
                                        <fmt:message key="faculties.public.entrant.unsubscribe.faculty.end"/>
                                    </p>

                                    <div class="uk-text-right ">
                                        <a href="<c:url value="/entrant/deleteFaculty.do?entrantId=${sessionScope.facultiesEntrantEntity.id}&facultyId=${facultyBean.faculty.id}"/>"
                                           class="uk-text-danger">
                                            <fmt:message key="faculties.public.entrant.unsubscribe.faculty.link"/>
                                            <i class="uk-icon-remove uk-text-danger"></i></a>
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

            <p><fmt:message key="${fn:toLowerCase(fn:replace(facultyBean.faculty.name,' ', '.')).concat('.faq')}"/></p>

            <h2><fmt:message key="faculties.public.info"/></h2>

            <div class="uk-grid">

                <p>
                        <fmt:message key="faculties.public.total.spots"/> ${facultyBean.faculty.totalSpots}
                        <fmt:message key="faculties.public.budget.spots"/> ${facultyBean.faculty.budgetSpots}
                        <fmt:message key="faculties.public.message.last.words"/>

                <div class="uk-width-medium-2-6">
                    <ul>
                        <c:forEach var="subject" items="${facultyBean.subjects}">
                            <li><fmt:message key="${subject.name}"/></li>
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
                                        <fmt:message key="faculties.public.select.priority"/>
                                        <select name="priority" class="uk-form-small">
                                            <c:forEach var="priority"
                                                       items="${sessionScope.facultiesAvailablePropertiesList}">
                                                <option>${priority}</option>
                                            </c:forEach>
                                        </select>
                                    </label>
                                    <button class="uk-button uk-button-mini uk-button-primary uk-push-1-10"
                                            type="submit">
                                        <fmt:message key="faculties.public.button.enroll"/>
                                    </button>
                                    <input type="hidden" name="facultyId" value="${facultyBean.faculty.id}">
                                    <input type="hidden" name="entrantId"
                                           value="${sessionScope.facultiesEntrantEntity.id}">
                                </form>
                            </div>
                        </c:when>
                        <c:when test="${fn:length(sessionScope.facultiesAvailablePropertiesList) == 0 && sessionScope.facultiesUsedFacultiesIdPriority.containsKey(facultyBean.faculty.id) == false}">
                            <div class="uk-alert uk-alert-warning uk-width-medium-2-5">
                                <p><fmt:message key="faculties.public.entrant.already.three.faculties"/></p>
                            </div>
                        </c:when>
                        <c:when test="${sessionScope.facultiesUsedFacultiesIdPriority.containsKey(facultyBean.faculty.id) == true}">
                            <div class="uk-alert uk-alert-success uk-width-medium-2-5">
                                <p class="uk-margin-bottom-remove">
                                    <fmt:message key="faculties.public.entrant.unsubscribe.faculty.start"/>
                                        ${sessionScope.facultiesUsedFacultiesIdPriority[facultyBean.faculty.id]},
                                    <fmt:message key="faculties.public.entrant.unsubscribe.faculty.end"/>
                                </p>

                                <div class="uk-text-right ">
                                    <a href="<c:url value="/entrant/deleteFaculty.do?entrantId=${sessionScope.facultiesEntrantEntity.id}&facultyId=${facultyBean.faculty.id}"/>"
                                       class="uk-text-danger">
                                        <fmt:message key="faculties.public.entrant.unsubscribe.faculty.link"/>
                                        <i class="uk-icon-remove uk-text-danger"></i></a>
                                </div>
                            </div>
                        </c:when>
                    </c:choose>
                </u:ifAuthAs>
            </div>
        </div>
        <div class="uk-width-medium-1-2">
            <img width="660" height="400"
                 src="<x:out select="$output/logos/logo[@facultyName=$pageScope:facultyName]/src"/>"
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
</body>
</html>

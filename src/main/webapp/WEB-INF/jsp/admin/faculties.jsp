<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 08/08/15
  Time: 12:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<c:if test="${sessionScope.isFromPost == true}">
    <c:redirect url="/admin/facultiesSort.do?page=1"/>
</c:if>
<%@include file="../../jspf/topPanel.jspf" %>
<div class="uk-container uk-container-center uk-width-1-1 uk-text-center ">
    <c:if test="${sessionScope.adminDeleteIsValidParamFacultyId == false || sessionScope.adminDeleteIsDeleted == false}">
        <div class="uk-alert uk-alert-danger uk-margin-large-top uk-width-4-5 uk-push-1-10 uk-container-center">
            <c:choose>
                <c:when test="${sessionScope.adminDeleteIsValidParamFacultyId == false}">
                    <p><i class="uk-icon-exclamation uk-text-danger"></i> Check url, wrong value facultyId, example:
                        facultyId=1,2,3etc
                    </p>
                </c:when>
                <c:when test="${sessionScope.adminDeleteIsDeleted == false}">
                    <p><i class="uk-icon-exclamation uk-text-danger"></i> Can't delete specified faculty, maybe another
                        admin deleted
                        it earlier.</p>
                </c:when>
            </c:choose>
        </div>
    </c:if>

    <c:if test="${sessionScope.adminDeleteIsDeleted == true}">
        <div class="uk-alert uk-alert-success uk-margin-large-top uk-width-4-5 uk-push-1-10 uk-container-center">
            <c:choose>
                <c:when test="${sessionScope.adminDeleteIsDeleted == true}">
                    <p>The faculty has been removed successfully <i class="uk-icon-check uk-text-success"></i></p>
                </c:when>
            </c:choose>
        </div>
    </c:if>


    <div class="uk-grid uk-margin-top">
        <div class="uk-width-medium-2-10 uk-margin-top">
            <form class="uk-form uk-panel-box " method="POST"
                  action="<c:url value="/admin/facultiesSort.do"/>">
                <c:if test="${sessionScope.adminIsSortedFaculties == false}">
                    <div class="uk-panel uk-animation-fade uk-text-middle">
                        <div class="uk-alert uk-alert-danger " data-uk-alert>
                            <p>Please choose sort option.</p>
                        </div>
                    </div>
                </c:if>
                <div class="uk-form-row uk-margin-top uk-text-left">
                    <label id="sortByNameAsc-label" class="uk-form-label" for="sortByNameAsc-label">
                        <input aria-labelledby="sortByNameAsc-label" name="sort" value="byNameAsc" type="radio"
                        ${sessionScope.adminSortType == 'byNameAsc' ? 'checked' : ''}>
                        Sort by name <i class="uk-icon-sort-alpha-asc"></i>
                    </label>
                    <br/>
                    <label id="sortByNameDesc-label" class="uk-form-label" for="sortByNameDesc-label">
                        <input aria-labelledby="sortByNameDesc-label" name="sort" value="byNameDesc" type="radio"
                        ${sessionScope.adminSortType == 'byNameDesc' ? 'checked' : ''}>
                        Sort by name <i class="uk-icon-sort-alpha-desc"></i>
                    </label>
                </div>

                <div class="uk-form-row uk-margin-top uk-text-left">
                    <label id="sortByNumberOfBudgetSpotsAsc-label" class="uk-form-label"
                           for="sortByNumberOfBudgetSpotsAsc-label">
                        <input aria-labelledby="sortByNumberOfBudgetSpotsAsc-label" name="sort"
                               value="byBudgetSpotsAsc" type="radio"
                        ${sessionScope.adminSortType == 'byBudgetSpotsAsc' ? 'checked' : ''}>
                        Sort by budget spots <i class="uk-icon-sort-amount-asc"></i>
                    </label>
                    <br/>
                    <label id="sortByNumberOfBudgetSpotsDesc-label" class="uk-form-label"
                           for="sortByNumberOfBudgetSpotsDesc-label">
                        <input aria-labelledby="sortByNumberOfBudgetSpotsDesc-label" name="sort"
                               value="byBudgetSpotsDesc" type="radio"
                        ${sessionScope.adminSortType == 'byBudgetSpotsDesc' ? 'checked' : ''}>
                        Sort by budget spots <i class="uk-icon-sort-amount-desc"></i>
                    </label>
                </div>

                <div class="uk-form-row uk-margin-top uk-text-left">
                    <label id="sortByNumberOfAllSpotsAsc-label" class="uk-form-label"
                           for="sortByNumberOfAllSpotsAsc-label">
                        <input aria-labelledby="sortByNumberOfAllSpotsAsc-label" name="sort"
                               value="byAllSpotsAsc" type="radio"
                        ${sessionScope.adminSortType == 'byAllSpotsAsc' ? 'checked' : ''}>
                        Sort by all spots <i class="uk-icon-sort-amount-asc"></i>
                    </label>
                    <br/>
                    <label id="sortByNumberOfAllSpotsDesc-label" class="uk-form-label"
                           for="sortByNumberOfAllSpotsDesc-label">
                        <input aria-labelledby="sortByNumberOfAllSpotsDesc-label" name="sort"
                               value="byAllSpotsDesc" type="radio"
                        ${sessionScope.adminSortType == 'byAllSpotsDesc' ? 'checked' : ''}>
                        Sort by all spots <i class="uk-icon-sort-amount-desc"></i>
                    </label>
                </div>
                <div class="uk-form-row uk-margin-top uk-text-left">
                    <button class="uk-width-medium-3-5 uk-button uk-button-primary " type="submit">Sort</button>

                </div>
                <div class="uk-form-row uk-margin-top uk-text-left">
                    <a class="uk-button" href="<c:url value="/admin/faculties.do"/>">No sort <i
                            class="uk-icon-refresh"></i></a>
                </div>
            </form>
        </div>
        <div class="uk-width-medium-8-10">
            <table class="uk-table uk-table-hover uk-table-striped uk-table-condensed uk-animation-fade uk-panel-box uk-panel-box-primary">
                <caption>Faculties</caption>
                <thead>
                <tr>
                    <th>id</th>
                    <th>Faculty name
                        ${sessionScope.adminSortType == 'byNameAsc' ? ' <i class="uk-icon-sort-alpha-asc"></i>' :
                                sessionScope.adminSortType == 'byNameDesc' ? ' <i class="uk-icon-sort-alpha-desc"></i>' : ''}
                    </th>
                    <th>Total spots
                        ${sessionScope.adminSortType == 'byAllSpotsAsc' ? ' <i class="uk-icon-sort-amount-asc"></i>' :
                                sessionScope.adminSortType == 'byAllSpotsDesc' ? ' <i class="uk-icon-sort-amount-desc"></i>' : ''}
                    </th>
                    <th>Budget spots
                        ${sessionScope.adminSortType == 'byBudgetSpotsAsc' ? ' <i class="uk-icon-sort-amount-asc"></i>' :
                                sessionScope.adminSortType == 'byBudgetSpotsDesc' ? ' <i class="uk-icon-sort-amount-desc"></i>' : ''}
                    </th>
                    <th>Subjects</th>
                    <th>Edit
                        <a href="<c:url value="/admin/prepareInfoAddFaculty.do"/>">
                            <i class="uk-icon-plus uk-float-right uk uk-text-success"></i>
                        </a>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="facultyInfoBeanPagination"
                           items="${sessionScope.adminSortType == null || sessionScope.adminSortType =='noSort'
                           ? sessionScope.adminFacultiesInfoBeansPagination : sessionScope.adminFacultiesInfoBeansSortedPagination}"
                           varStatus="loop">
                    <tr class="uk-table-middle">
                        <td>${facultyInfoBeanPagination.faculty.id}</td>
                        <td>${facultyInfoBeanPagination.faculty.name}</td>
                        <td>${facultyInfoBeanPagination.faculty.totalSpots}</td>
                        <td>${facultyInfoBeanPagination.faculty.budgetSpots}</td>
                        <td>
                            <c:forEach var="subject" items="${facultyInfoBeanPagination.subjects}">
                                ${subject.name}<br/>
                            </c:forEach>
                        </td>
                        <td class="uk-width-1-10">
                            <a href="<c:url value="/admin/prepareInfoEditFaculty.do?index=${loop.index}"/>"><i
                                    class="uk-icon-edit uk-float-left"></i></a>
                            <a href="<c:url value="/admin/deleteFaculty.do?facultyId=${facultyInfoBeanPagination.faculty.id}"/>">
                                <i class="uk-icon-remove uk-text-danger uk-float-right"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

            </table>
            <ul class="uk-pagination uk-margin-bottom-remove">
                <c:if test="${sessionScope.currentPage != 1}">
                    <li class="uk-pagination-previous">
                        <a href="
                        ${sessionScope.adminSortType == null || sessionScope.adminSortType == 'noSort' ?
                        '/admin/faculties.do?page='.concat(sessionScope.currentPage - 1) :
                        '/admin/facultiesSort.do?page='.concat(sessionScope.currentPage - 1) }">
                            <i class="uk-icon-angle-double-left"></i> Previous
                        </a>
                    </li>
                </c:if>
                <c:choose>
                    <c:when test="${sessionScope.currentPage == 1}">
                        <li class="uk-active"><span>${sessionScope.currentPage}</span></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${sessionScope.adminSortType == null || sessionScope.adminSortType == 'noSort' ?
                        '/admin/faculties.do?page=1' :
                        '/admin/facultiesSort.do?page=1'}">1
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
                                    <a href="${sessionScope.adminSortType == null || sessionScope.adminSortType == 'noSort' ?
                                            '/admin/faculties.do?page='.concat(i) :
                                            '/admin/facultiesSort.do?page='.concat(i)}">
                                            ${i}
                                    </a>
                                </span>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${sessionScope.currentPage lt sessionScope.numberOfPages}">
                    <li class="uk-pagination-next">
                        <a href="
                        ${sessionScope.adminSortType == null || sessionScope.adminSortType == 'noSort' ?
                        '/admin/faculties.do?page='.concat(sessionScope.currentPage + 1) :
                        '/admin/facultiesSort.do?page='.concat(sessionScope.currentPage + 1)}">
                            Next <i class="uk-icon-angle-double-right"></i>
                        </a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
    <c:remove scope="session" var="adminDeleteIsValidParamFacultyId"/>
    <c:remove scope="session" var="adminDeleteIsDeleted"/>
</div>
</body>
</html>

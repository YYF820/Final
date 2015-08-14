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
<c:choose>
    <c:when test="${requestScope.isReadyFinalSheet == false}">
        <%@ include file="../jspf/logo.jspf" %>
        <%@ include file="../jspf/navAndLogin.jspf" %>
        <div class="uk-container uk-container-center uk-width-8-10 uk-text-center uk-margin-top ">
            <div class="uk-align-center uk-width-small-1-2 uk-margin-large-top">
                <p class="uk-text-warning uk-text-large uk-text-middle"><i
                        class="uk-icon-exclamation-triangle uk-icon-large uk-text-warning"></i>&nbspAt the moment final
                    sheet is not ready.</p>

                <div class="uk-margin-top uk-text-center uk-align-center">
                    <p class="uk-text-danger">Coming soon...</p>
                </div>
            </div>
        </div>
    </c:when>
    <c:when test="${sessionScope.finalSheetIsFoundSomething == false}">
        <div class="uk-grid uk-container-center uk-margin-top">
            <div class="uk-width-medium-2-10 uk-margin-top">
                <form class="uk-form uk-panel-box " method="POST"
                      action="<c:url value="/paginationFinalSheet.do"/>">
                    <div class="uk-text-center">
                        <p class="uk-h3">Search <i class="uk-icon-search"></i></p>
                    </div>
                    <c:if test="${sessionScope.finalSheetIsFormEmpty == true}">
                        <div class="uk-panel uk-animation-fade uk-text-center ">
                            <div class="uk-alert uk-alert-danger " data-uk-alert>
                                <p>Please fill minimum one field.</p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.finalSheetIsValidLastName == false}">
                        <div class="uk-panel uk-animation-fade uk-text-center uk-margin-top">
                            <div class="uk-alert uk-alert-danger " data-uk-alert>
                                <p>Last name is not valid.</p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.finalSheetIsValidFacultyName == false}">
                        <div class="uk-panel uk-animation-fade uk-text-center uk-margin-top">
                            <div class="uk-alert uk-alert-danger " data-uk-alert>
                                <p>Faculty name is not valid.</p>
                            </div>
                        </div>
                    </c:if>
                    <div class="uk-width-1-1 uk-margin-top">
                        <input name="lastName"
                               class="uk-width-1-1"
                               type="text" spellcheck="false" autocomplete="off"
                               value="${sessionScope.finalSheetLastName}"
                               placeholder="Last Name:">
                    </div>
                    <div class="uk-width-1-1 uk-margin-top">
                        <input name="facultyName"
                               class="uk-width-1-1"
                               type="text" spellcheck="false" autocomplete="off"
                               value="${sessionScope.finalSheetFacultyName}"
                               placeholder="Faculty Name:">
                    </div>
                    <div class="uk-form-row uk-margin-top uk-text-left">
                        <button class="uk-width-medium-3-5 uk-button uk-button-primary " type="submit">Find</button>
                    </div>
                    <u:ifAuthAs role="entrant">
                        <div class="uk-form-row uk-margin-top uk-text-left">
                            <a class="uk-button uk-button-success"
                               href="<c:url value="/paginationFinalSheet.do?command=findMe"/>">Find me <i
                                    class="uk-icon-user"></i></a>
                        </div>
                    </u:ifAuthAs>
                    <div class="uk-form-row uk-margin-top uk-text-left">
                        <a class="uk-button" href="
                        <c:url value="/paginationFinalSheet.do?command=findAllEntrants&page=${sessionScope.currentPage}"/>">
                            Reset <i class="uk-icon-refresh"></i></a>
                    </div>
                </form>
            </div>
            <div class="uk-container uk-container-center uk-width-8-10 uk-text-center uk-margin-top ">
                <div class="uk-align-center uk-width-small-1-2 uk-margin-large-top">
                    <p class="uk-text-danger uk-text-large uk-text-middle"><i
                            class="uk-icon-search-minus uk-icon-large uk-text-danger"></i>&nbspNothing by your
                        search
                    </p>

                    <div class="uk-margin-top uk-text-center uk-align-center">
                        <p class="uk-text-danger uk-h2">Ooops can't find anything.</p>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
    <c:when test="${requestScope.commandFind eq 'findAllEntrants' || requestScope.commandFind == null}">
        <div class="uk-grid uk-container-center uk-margin-top">
            <div class="uk-width-medium-2-10 uk-margin-top">
                <form class="uk-form uk-panel-box " method="POST"
                      action="<c:url value="/paginationFinalSheet.do"/>">
                    <div class="uk-text-center">
                        <p class="uk-h3">Search <i class="uk-icon-search"></i></p>
                    </div>
                    <c:if test="${sessionScope.finalSheetIsFormEmpty == true}">
                        <div class="uk-panel uk-animation-fade uk-text-center ">
                            <div class="uk-alert uk-alert-danger " data-uk-alert>
                                <p>Please fill minimum one field.</p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.finalSheetIsValidLastName == false}">
                        <div class="uk-panel uk-animation-fade uk-text-center uk-margin-top">
                            <div class="uk-alert uk-alert-danger " data-uk-alert>
                                <p>Last name is not valid.</p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.finalSheetIsValidFacultyName == false}">
                        <div class="uk-panel uk-animation-fade uk-text-center uk-margin-top">
                            <div class="uk-alert uk-alert-danger " data-uk-alert>
                                <p>Faculty name is not valid.</p>
                            </div>
                        </div>
                    </c:if>
                    <div class="uk-width-1-1 uk-margin-top">
                        <input name="lastName"
                               class="uk-width-1-1"
                               type="text" spellcheck="false" autocomplete="off"
                               value="${sessionScope.finalSheetLastName}"
                               placeholder="Last Name:">
                    </div>
                    <div class="uk-width-1-1 uk-margin-top">
                        <input name="facultyName"
                               class="uk-width-1-1"
                               type="text" spellcheck="false" autocomplete="off"
                               value="${sessionScope.finalSheetFacultyName}"
                               placeholder="Faculty Name:">
                    </div>
                    <div class="uk-form-row uk-margin-top uk-text-left">
                        <button class="uk-width-medium-3-5 uk-button uk-button-primary " type="submit">Find</button>
                    </div>
                    <u:ifAuthAs role="entrant">
                        <div class="uk-form-row uk-margin-top uk-text-left">
                            <a class="uk-button uk-button-success"
                               href="<c:url value="/paginationFinalSheet.do?command=findMe"/>">Find me <i
                                    class="uk-icon-user"></i></a>
                        </div>
                    </u:ifAuthAs>
                    <div class="uk-form-row uk-margin-top uk-text-left">
                        <a class="uk-button" href="
                        <c:url value="/paginationFinalSheet.do?command=findAllEntrants&page=${sessionScope.currentPage}"/>">
                            Reset <i class="uk-icon-refresh"></i></a>
                    </div>
                </form>
            </div>
            <div class="uk-width-medium-7-10 uk-text-center ">
                <table class="uk-table  uk-table-hover uk-table-striped uk-table-condensed uk-animation-fade uk-panel-box uk-panel-box-primary">
                    <caption>Final sheet</caption>
                    <thead>
                    <tr>
                        <th>Faculty name</th>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Patronymic</th>
                        <th>Sum marks</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="passedEntrant" items="${sessionScope.passedEntrantsPagination}">
                        <tr class="uk-table-middle">
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
                <ul class="uk-pagination uk-margin-bottom-remove">
                    <c:if test="${sessionScope.currentPage != 1}">
                        <li class="uk-pagination-previous">
                            <a href="<c:url value="/paginationFinalSheet.do?page=${sessionScope.currentPage - 1}&command=findAllEntrants"/>">
                                <i class="uk-icon-angle-double-left"></i> Previous
                            </a>
                        </li>
                    </c:if>
                    <c:choose>
                        <c:when test="${sessionScope.currentPage == 1}">
                            <li class="uk-active"><span>${sessionScope.currentPage}</span></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="<c:url value="/paginationFinalSheet.do?page=1&command=findAllEntrants"/>">1
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
                                    <a href="<c:url value="/paginationFinalSheet.do?page=${i}&command=findAllEntrants"/>">
                                            ${i}
                                    </a>
                                </span>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${sessionScope.currentPage < sessionScope.numberOfPages - 3}">
                        <li><span>...</span></li>
                    </c:if>
                    <c:if test="${sessionScope.currentPage < sessionScope.numberOfPages - 3}">
                        <li>
                            <a href="<c:url value="/paginationFinalSheet.do?page=${sessionScope.numberOfPages}&command=findAllEntrants"/>">${sessionScope.numberOfPages}</a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.currentPage lt sessionScope.numberOfPages}">
                        <li class="uk-pagination-next">
                            <a href="<c:url value="/paginationFinalSheet.do?page=${sessionScope.currentPage + 1}&command=findAllEntrants"/>">
                                Next <i class=" uk-icon-angle-double-right"></i>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </c:when>
    <c:when test="${requestScope.commandFind eq 'findMe'}">
        <div class="uk-grid uk-container-center uk-margin-top">
            <div class="uk-width-medium-2-10 uk-margin-top">
                <form class="uk-form uk-panel-box " method="POST"
                      action="<c:url value="/admin/facultiesSort.do"/>">
                    <div class="uk-text-center">
                        <p class="uk-h3">Search <i class="uk-icon-search"></i></p>
                    </div>
                    <c:if test="${sessionScope.finalSheetIsFormEmpty == true}">
                        <div class="uk-panel uk-animation-fade uk-text-center">
                            <div class="uk-alert uk-alert-danger " data-uk-alert>
                                <p>Please fill minimum one field.</p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.finalSheetIsFormEmpty == true}">
                        <div class="uk-panel uk-animation-fade uk-text-center ">
                            <div class="uk-alert uk-alert-danger " data-uk-alert>
                                <p>Please fill minimum one field.</p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.finalSheetIsValidLastName == false}">
                        <div class="uk-panel uk-animation-fade uk-text-center uk-margin-top">
                            <div class="uk-alert uk-alert-danger " data-uk-alert>
                                <p>Last name is not valid.</p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.finalSheetIsValidFacultyName == false}">
                        <div class="uk-panel uk-animation-fade uk-text-center uk-margin-top">
                            <div class="uk-alert uk-alert-danger " data-uk-alert>
                                <p>Faculty name is not valid.</p>
                            </div>
                        </div>
                    </c:if>
                    <div class="uk-width-1-1 uk-margin-top">
                        <input name="lastName"
                               class="uk-width-1-1"
                               type="text" spellcheck="false" autocomplete="off"
                               value=""
                               placeholder="Last Name:">
                    </div>
                    <div class="uk-width-1-1 uk-margin-top">
                        <input name="facultyName"
                               class="uk-width-1-1"
                               type="text" spellcheck="false" autocomplete="off"
                               value=""
                               placeholder="Faculty Name:">
                    </div>
                    <div class="uk-form-row uk-margin-top uk-text-left">
                        <button class="uk-width-medium-3-5 uk-button uk-button-primary " type="submit">Find</button>
                    </div>
                    <u:ifAuthAs role="entrant">
                        <div class="uk-form-row uk-margin-top uk-text-left">
                            <a class="uk-button uk-button-success"
                               href="<c:url value="/paginationFinalSheet.do?command=findMe"/>">Find me <i
                                    class="uk-icon-user"></i></a>
                        </div>
                    </u:ifAuthAs>
                    <div class="uk-form-row uk-margin-top uk-text-left">
                        <a class="uk-button" href="
                        <c:url value="/paginationFinalSheet.do?command=findAllEntrants&page=${sessionScope.currentPage}"/>">Reset
                            <i class="uk-icon-refresh"></i></a>
                    </div>
                </form>
            </div>
            <c:choose>
                <c:when test="${requestScope.isPassedEntrantFinalSheet == false}">
                    <div class="uk-container uk-container-center uk-width-8-10 uk-text-center uk-margin-top ">
                        <div class="uk-align-center uk-width-small-1-2 uk-margin-large-top">
                            <p class="uk-text-warning uk-text-large uk-text-middle"><i
                                    class="uk-icon-exclamation-triangle uk-icon-large uk-text-warning"></i>&nbspYou
                                didn't pass this year.</p>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="uk-width-medium-7-10 uk-text-center ">
                        <table class="uk-table  uk-table-hover uk-table-striped uk-table-condensed uk-animation-fade uk-panel-box uk-panel-box-primary">
                            <caption>Final sheet</caption>
                            <thead>
                            <tr>
                                <th>Faculty name</th>
                                <th>First name</th>
                                <th>Last name</th>
                                <th>Patronymic</th>
                                <th>Sum marks</th>
                                <th>Status</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr class="uk-table-middle">
                                <td>${requestScope.passedEntrantByUserId.facultyName}</td>
                                <td>${requestScope.passedEntrantByUserId.firstName}</td>
                                <td>${requestScope.passedEntrantByUserId.lastName}</td>
                                <td>${requestScope.passedEntrantByUserId.patronymic}</td>
                                <td>${requestScope.passedEntrantByUserId.sumOfMarks}</td>
                                <td>${requestScope.passedEntrantByUserId.enterUniversityStatus}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </c:when>
    <c:when test="${requestScope.commandFind eq 'search'}">
        <div class="uk-grid uk-container-center uk-margin-top">
            <div class="uk-width-medium-2-10 uk-margin-top">
                <form class="uk-form uk-panel-box " method="POST"
                      action="<c:url value="/paginationFinalSheet.do"/>">
                    <div class="uk-text-center">
                        <p class="uk-h3">Search <i class="uk-icon-search"></i></p>
                    </div>
                    <c:if test="${sessionScope.finalSheetIsFormEmpty == true}">
                        <div class="uk-panel uk-animation-fade uk-text-center ">
                            <div class="uk-alert uk-alert-danger " data-uk-alert>
                                <p>Please fill minimum one field.</p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.finalSheetIsValidLastName == false}">
                        <div class="uk-panel uk-animation-fade uk-text-center uk-margin-top">
                            <div class="uk-alert uk-alert-danger " data-uk-alert>
                                <p>Last name is not valid.</p>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.finalSheetIsValidFacultyName == false}">
                        <div class="uk-panel uk-animation-fade uk-text-center uk-margin-top">
                            <div class="uk-alert uk-alert-danger " data-uk-alert>
                                <p>Faculty name is not valid.</p>
                            </div>
                        </div>
                    </c:if>
                    <div class="uk-width-1-1 uk-margin-top">
                        <input name="lastName"
                               class="uk-width-1-1"
                               type="text" spellcheck="false" autocomplete="off"
                               value="${sessionScope.finalSheetLastName}"
                               placeholder="Last Name:">
                    </div>
                    <div class="uk-width-1-1 uk-margin-top">
                        <input name="facultyName"
                               class="uk-width-1-1"
                               type="text" spellcheck="false" autocomplete="off"
                               value="${sessionScope.finalSheetFacultyName}"
                               placeholder="Faculty Name:">
                    </div>
                    <div class="uk-form-row uk-margin-top uk-text-left">
                        <button class="uk-width-medium-3-5 uk-button uk-button-primary " type="submit">Find</button>
                    </div>
                    <u:ifAuthAs role="entrant">
                        <div class="uk-form-row uk-margin-top uk-text-left">
                            <a class="uk-button uk-button-success"
                               href="<c:url value="/paginationFinalSheet.do?command=findMe"/>">Find me <i
                                    class="uk-icon-user"></i></a>
                        </div>
                    </u:ifAuthAs>
                    <div class="uk-form-row uk-margin-top uk-text-left">
                        <a class="uk-button" href="
                        <c:url value="/paginationFinalSheet.do?command=findAllEntrants"/>">
                            Reset <i class="uk-icon-refresh"></i></a>
                    </div>
                </form>
            </div>
            <div class="uk-width-medium-7-10 uk-text-center ">
                <table class="uk-table  uk-table-hover uk-table-striped uk-table-condensed uk-animation-fade uk-panel-box uk-panel-box-primary">
                    <caption>Final sheet</caption>
                    <thead>
                    <tr>
                        <th>Faculty name</th>
                        <th>First name</th>
                        <th>Last name</th>
                        <th>Patronymic</th>
                        <th>Sum marks</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="passedEntrant" items="${sessionScope.passedEntrantsPagination}">
                        <tr class="uk-table-middle">
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
                <ul class="uk-pagination uk-margin-bottom-remove">
                    <c:if test="${sessionScope.currentPage != 1}">
                        <li class="uk-pagination-previous">
                            <a href="<c:url value="/paginationFinalSheet.do?page=${sessionScope.currentPage - 1}&command=search"/>">
                                <i class="uk-icon-angle-double-left"></i> Previous
                            </a>
                        </li>
                    </c:if>
                    <c:choose>
                        <c:when test="${sessionScope.currentPage == 1}">
                            <li class="uk-active"><span>${sessionScope.currentPage}</span></li>
                        </c:when>
                        <c:otherwise>
                            <li>
                                <a href="<c:url value="/paginationFinalSheet.do?page=1&command=search"/>">1
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
                                    <a href="<c:url value="/paginationFinalSheet.do?page=${i}&command=search"/>">
                                            ${i}
                                    </a>
                                </span>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${sessionScope.currentPage < sessionScope.numberOfPages - 3}">
                        <li><span>...</span></li>
                    </c:if>
                    <c:if test="${sessionScope.currentPage < sessionScope.numberOfPages - 3}">
                        <li>
                            <a href="<c:url value="/paginationFinalSheet.do?page=${sessionScope.numberOfPages}&command=search"/>">${sessionScope.numberOfPages}</a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.currentPage lt sessionScope.numberOfPages}">
                        <li class="uk-pagination-next">
                            <a href="<c:url value="/paginationFinalSheet.do?page=${sessionScope.currentPage + 1}&command=search"/>">
                                Next <i class=" uk-icon-angle-double-right"></i>
                            </a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </c:when>
</c:choose>
</body>
</html>

<%@include file="../jspf/imports.jspf" %>
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
                        class="uk-icon-exclamation-triangle uk-icon-large uk-text-warning"></i>&nbsp
                    <fmt:message key="final.sheet.not.ready.head"/>
                </p>

                <div class="uk-margin-top uk-text-center uk-align-center">
                    <p class="uk-text-danger">
                        <fmt:message key="final.sheet.not.ready.body"/>
                    </p>
                </div>
            </div>
        </div>
    </c:when>
    <c:when test="${sessionScope.finalSheetIsFoundSomething == false}">
        <div class="uk-grid uk-container-center uk-margin-top">
            <div class="uk-width-medium-2-10 uk-margin-top">
                <%@include file="../jspf/finalSheetSearchForm.jspf" %>
            </div>
            <div class="uk-container uk-container-center uk-width-8-10 uk-text-center uk-margin-top ">
                <div class="uk-align-center uk-width-small-1-2 uk-margin-large-top">
                    <p class="uk-text-danger uk-text-large uk-text-middle"><i
                            class="uk-icon-search-minus uk-icon-large uk-text-danger"></i>&nbsp
                        <fmt:message key="final.sheet.nothing.by.search.head"/>
                    </p>

                    <div class="uk-margin-top uk-text-center uk-align-center">
                        <p class="uk-text-danger uk-h2">
                            <fmt:message key="final.sheet.nothing.by.search.body"/>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
    <c:when test="${requestScope.commandFind eq 'findAllEntrants' || requestScope.commandFind == null}">
        <div class="uk-grid uk-container-center uk-margin-top">
            <div class="uk-width-medium-2-10 uk-margin-top">
                <%@include file="../jspf/finalSheetSearchForm.jspf" %>
            </div>
            <div class="uk-width-medium-7-10 uk-text-center ">
                <%@include file="../jspf/finalSheetTable.jspf" %>
                <ul class="uk-pagination uk-margin-bottom-remove">
                    <c:if test="${sessionScope.currentPage != 1}">
                        <li class="uk-pagination-previous">
                            <a href="<c:url value="/paginationFinalSheet.do?page=${sessionScope.currentPage - 1}&command=findAllEntrants"/>">
                                <i class="uk-icon-angle-double-left"></i>
                                <fmt:message key="button.previous.page"/>
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
                                <fmt:message key="button.next.page"/> <i class=" uk-icon-angle-double-right"></i>
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
                <%@include file="../jspf/finalSheetSearchForm.jspf" %>
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
                        <%@include file="../jspf/finalSheetTable.jspf" %>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </c:when>
    <c:when test="${requestScope.commandFind eq 'search'}">
        <div class="uk-grid uk-container-center uk-margin-top">
            <div class="uk-width-medium-2-10 uk-margin-top">
                <%@include file="../jspf/finalSheetSearchForm.jspf" %>
            </div>
            <div class="uk-width-medium-7-10 uk-text-center ">
                <%@include file="../jspf/finalSheetTable.jspf" %>
                <ul class="uk-pagination uk-margin-bottom-remove">
                    <c:if test="${sessionScope.currentPage != 1}">
                        <li class="uk-pagination-previous">
                            <a href="<c:url value="/paginationFinalSheet.do?page=${sessionScope.currentPage - 1}&command=search"/>">
                                <i class="uk-icon-angle-double-left"></i>
                                <fmt:message key="button.previous.page"/>
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
                                <fmt:message key="button.next"/> <i class=" uk-icon-angle-double-right"></i>
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

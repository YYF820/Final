<div id="sortFacultiesEntrant" class="uk-offcanvas">
    <div class="uk-offcanvas-bar uk-container uk-container-center">
        <form class="uk-form uk-contrast uk-margin-large-top" method="POST"
              action="<c:url value="/facultiesSort.do"/>">

            <p class="uk-h2 uk-text-left"><fmt:message key="sort.settings"/>
                <i class="uk-icon-cog uk-text-primary"></i></p>
            <c:if test="${sessionScope.facultiesIsSorted == false}">
                <div class="uk-panel uk-animation-fade uk-text-middle">
                    <div class="uk-alert uk-alert-danger " data-uk-alert>
                        <p><fmt:message key="please.choose.sort.option"/></p>
                    </div>
                </div>
            </c:if>
            <div class="uk-form-row uk-margin-top uk-text-left">
                <label id="sortByNameAsc-label" class="uk-form-label" for="sortByNameAsc-label">
                    <input aria-labelledby="sortByNameAsc-label" name="sort" value="byNameAsc" type="radio"
                    ${sessionScope.facultiesSortType == 'byNameAsc' ? 'checked' : ''} >
                    <fmt:message key="sort.by.name"/> <i class="uk-icon-sort-alpha-asc"></i>
                </label>
                <br/>
                <label id="sortByNameDesc-label" class="uk-form-label" for="sortByNameDesc-label">
                    <input aria-labelledby="sortByNameDesc-label" name="sort" value="byNameDesc" type="radio"
                    ${sessionScope.facultiesSortType == 'byNameDesc' ? 'checked' : ''}>
                    <fmt:message key="sort.by.name"/> <i class="uk-icon-sort-alpha-desc"></i>
                </label>
            </div>

            <div class="uk-form-row uk-margin-top uk-text-left">
                <label id="sortByNumberOfBudgetSpotsAsc-label" class="uk-form-label"
                       for="sortByNumberOfBudgetSpotsAsc-label">
                    <input aria-labelledby="sortByNumberOfBudgetSpotsAsc-label" name="sort"
                           value="byBudgetSpotsAsc" type="radio"
                    ${sessionScope.facultiesSortType == 'byBudgetSpotsAsc' ? 'checked' : ''}>
                    <fmt:message key="sort.by.budget.spots"/> <i class="uk-icon-sort-amount-asc"></i>
                </label>
                <br/>
                <label id="sortByNumberOfBudgetSpotsDesc-label" class="uk-form-label"
                       for="sortByNumberOfBudgetSpotsDesc-label">
                    <input aria-labelledby="sortByNumberOfBudgetSpotsDesc-label" name="sort"
                           value="byBudgetSpotsDesc" type="radio"
                    ${sessionScope.facultiesSortType == 'byBudgetSpotsDesc' ? 'checked' : ''}>
                    <fmt:message key="sort.by.budget.spots"/> <i class="uk-icon-sort-amount-desc"></i>
                </label>
            </div>

            <div class="uk-form-row uk-margin-top uk-text-left">
                <label id="sortByNumberOfAllSpotsAsc-label" class="uk-form-label"
                       for="sortByNumberOfAllSpotsAsc-label">
                    <input aria-labelledby="sortByNumberOfAllSpotsAsc-label" name="sort"
                           value="byAllSpotsAsc" type="radio"
                    ${sessionScope.facultiesSortType == 'byAllSpotsAsc' ? 'checked' : ''}>
                    <fmt:message key="sort.by.total.spots"/> <i class="uk-icon-sort-amount-asc"></i>
                </label>
                <br/>
                <label id="sortByNumberOfAllSpotsDesc-label" class="uk-form-label"
                       for="sortByNumberOfAllSpotsDesc-label">
                    <input aria-labelledby="sortByNumberOfAllSpotsDesc-label" name="sort"
                           value="byAllSpotsDesc" type="radio"
                    ${sessionScope.facultiesSortType == 'byAllSpotsDesc' ? 'checked' : ''}>
                    <fmt:message key="sort.by.name"/> <i class="uk-icon-sort-amount-desc"></i>
                </label>
            </div>
            <div class="uk-form-row uk-margin-top uk-text-left">
                <button class="uk-width-medium-3-5 uk-button uk-button-primary " type="submit">
                    <fmt:message key="button.sort"/>
                </button>
            </div>
            <div class="uk-form-row uk-margin-top uk-text-left">
                <a class="uk-button" href="<c:url value="/faculties.html?page=1"/>" onclick="changeSortType()">
                    <fmt:message key="button.no.sort"/> <i class="uk-icon-refresh"></i>
                </a>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    function changeSortType() {
        <c:set value="noSort" scope="session" var="facultiesSortType"/>
    }
</script>
<div id="sortFacultiesEntrant" class="uk-offcanvas">
    <div class="uk-offcanvas-bar uk-container uk-container-center">
        <form class="uk-form uk-contrast uk-margin-large-top" method="POST"
              action="<c:url value="/facultiesSort.do"/>">

            <p class="uk-h2 uk-text-left">Sort settings <i class="uk-icon-cog uk-text-primary"></i></p>
            <c:if test="${sessionScope.facultiesIsSorted == false}">
                <div class="uk-panel uk-animation-fade uk-text-middle">
                    <div class="uk-alert uk-alert-danger " data-uk-alert>
                        <p>Please choose sort option.</p>
                    </div>
                </div>
            </c:if>
            <div class="uk-form-row uk-margin-top uk-text-left">
                <label id="sortByNameAsc-label" class="uk-form-label" for="sortByNameAsc-label">
                    <input aria-labelledby="sortByNameAsc-label" name="sort" value="byNameAsc" type="radio"
                    ${sessionScope.facultiesSortType == 'byNameAsc' ? 'checked' : ''} >
                    Sort by name <i class="uk-icon-sort-alpha-asc"></i>
                </label>
                <br/>
                <label id="sortByNameDesc-label" class="uk-form-label" for="sortByNameDesc-label">
                    <input aria-labelledby="sortByNameDesc-label" name="sort" value="byNameDesc" type="radio"
                    ${sessionScope.facultiesSortType == 'byNameDesc' ? 'checked' : ''}>
                    Sort by name <i class="uk-icon-sort-alpha-desc"></i>
                </label>
            </div>

            <div class="uk-form-row uk-margin-top uk-text-left">
                <label id="sortByNumberOfBudgetSpotsAsc-label" class="uk-form-label"
                       for="sortByNumberOfBudgetSpotsAsc-label">
                    <input aria-labelledby="sortByNumberOfBudgetSpotsAsc-label" name="sort"
                           value="byBudgetSpotsAsc" type="radio"
                    ${sessionScope.facultiesSortType == 'byBudgetSpotsAsc' ? 'checked' : ''}>
                    Sort by budget spots <i class="uk-icon-sort-amount-asc"></i>
                </label>
                <br/>
                <label id="sortByNumberOfBudgetSpotsDesc-label" class="uk-form-label"
                       for="sortByNumberOfBudgetSpotsDesc-label">
                    <input aria-labelledby="sortByNumberOfBudgetSpotsDesc-label" name="sort"
                           value="byBudgetSpotsDesc" type="radio"
                    ${sessionScope.facultiesSortType == 'byBudgetSpotsDesc' ? 'checked' : ''}>
                    Sort by budget spots <i class="uk-icon-sort-amount-desc"></i>
                </label>
            </div>

            <div class="uk-form-row uk-margin-top uk-text-left">
                <label id="sortByNumberOfAllSpotsAsc-label" class="uk-form-label"
                       for="sortByNumberOfAllSpotsAsc-label">
                    <input aria-labelledby="sortByNumberOfAllSpotsAsc-label" name="sort"
                           value="byAllSpotsAsc" type="radio"
                    ${sessionScope.facultiesSortType == 'byAllSpotsAsc' ? 'checked' : ''}>
                    Sort by all spots <i class="uk-icon-sort-amount-asc"></i>
                </label>
                <br/>
                <label id="sortByNumberOfAllSpotsDesc-label" class="uk-form-label"
                       for="sortByNumberOfAllSpotsDesc-label">
                    <input aria-labelledby="sortByNumberOfAllSpotsDesc-label" name="sort"
                           value="byAllSpotsDesc" type="radio"
                    ${sessionScope.facultiesSortType == 'byAllSpotsDesc' ? 'checked' : ''}>
                    Sort by all spots <i class="uk-icon-sort-amount-desc"></i>
                </label>
            </div>
            <div class="uk-form-row uk-margin-top uk-text-left">
                <button class="uk-width-medium-3-5 uk-button uk-button-primary " type="submit">Sort</button>

            </div>
            <div class="uk-form-row uk-margin-top uk-text-left">
                <a class="uk-button" href="<c:url value="/faculties.html?page=1"/>" onclick="changeSortType()">
                    No sort <i class="uk-icon-refresh"></i>
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
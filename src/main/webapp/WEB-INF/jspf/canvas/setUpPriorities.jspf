<div id="setUpPriorities" class="uk-offcanvas">
    <div class="uk-offcanvas-bar uk-offcanvas-bar-flip uk-container uk-container-center uk-contrast">
        <form class="uk-form uk-contrast uk-margin-large-top" method="POST"
              action="<c:url value="/entrant/configurePriorities.do"/>">
            <p class="uk-h3 uk-text-center">
                <fmt:message key="configure.priorities"/>
                <i class="uk-icon-cog uk-text-primary"></i></p>
            <c:choose>
                <c:when test="${fn:length(sessionScope.facultiesEnrolledFaculties) gt 0}">
                    <p class="uk-h4"><fmt:message key="configure.priorities.same.priorities"/>
                        <i class="uk-icon-exclamation uk-text-danger"></i>
                    </p>
                    <c:forEach var="faculty" items="${sessionScope.facultiesEnrolledFaculties}">
                        <div class="uk-form-row row-margin">
                            <p><fmt:message
                                    key="${fn:toLowerCase(fn:replace(faculty.name, ' ', '.'))}"/>:</p>
                            <select name="${faculty.name} priority" class="uk-form-small uk-form-width-mini">
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                            </select>
                        </div>
                    </c:forEach>
                    <div class="uk-form-row uk-margin-top uk-text-center">
                        <button class="uk-width-medium-3-5 uk-button uk-button-primary " type="submit">
                            <fmt:message key="button.save"/>
                        </button>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="uk-alert uk-alert-warning">
                        <fmt:message key="configure.priorities.not.enrolled.on.faculty"/>
                        <i class="uk-icon-exclamation uk-text-danger"></i>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
</div>

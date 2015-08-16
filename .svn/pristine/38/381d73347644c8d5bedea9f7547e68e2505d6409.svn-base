<%@include file="../jspf/imports.jspf" %>
<html>
<head>
    <%@include file="../jspf/header/header.jspf" %>
    <title></title>
</head>
<body>
<%@include file="../jspf/topPanel.jspf" %>
<div class="uk-container uk-container-center uk-width-4-10 uk-margin-top ">
    <u:ifAuthAs role="entrant">
        <c:if test="${sessionScope.entrantNoSubjectsAdded == true ||
        sessionScope.entrantAccountSettingsAllSubjects eq 'noSubjectsInDataBase' ||
        sessionScope.entrantAccountSettingsSomethingBad == true}">
            <div class="uk-alert-warning uk-alert uk-text-center">
                <c:choose>
                    <c:when test="${sessionScope.entrantNoSubjectsAdded == true}">
                        <p><fmt:message key="account.settings.warning.marks.body"/></p>
                        <a href="<c:url value="/entrant/addSubjects.html"/>">
                            <fmt:message key="account.settings.you.can.do.it.here"/>
                            <i class="uk-icon-external-link"></i></a>
                    </c:when>
                    <c:when test="${sessionScope.entrantAccountSettingsAllSubjects eq 'noSubjectsInDataBase'}">
                        <p><fmt:message key="account.settings.you.can.do.it.here"/></p>
                    </c:when>
                </c:choose>
                <c:if test="${sessionScope.entrantAccountSettingsSomethingBad == true}">
                    <p><fmt:message key="account.settings.server.problems"/></p>
                </c:if>
            </div>
        </c:if>
    </u:ifAuthAs>
    <u:ifAuthAs role="entrant">
        <c:if test="${sessionScope.entrantAccountSettingsNoExtraMarks == true}">
            <div class="uk-alert-warning uk-alert uk-text-center">
                <c:choose>
                    <c:when test="${sessionScope.entrantAccountSettingsNoExtraMarks == true}">
                        <p><fmt:message key="account.settings.warning.extra.marks.body"/></p>
                        <a href="<c:url value="/entrant/addExtraMarks.html"/>">
                            <fmt:message key="account.settings.you.can.do.it.here"/>
                            <i class="uk-icon-external-link"></i></a>
                    </c:when>
                </c:choose>
            </div>
        </c:if>
    </u:ifAuthAs>
    <table class="uk-table uk-table-striped uk-table-condensed uk-animation-fade uk-panel-box uk-panel-box-primary">
        <caption>
            <u:ifAuthAs role="admin">
                <fmt:message key="account.settings.role.admin"/>
            </u:ifAuthAs>
            <u:ifAuthAs role="entrant">
                <fmt:message key="account.settings.role.entrant"/>
            </u:ifAuthAs>
        </caption>
        <thead>
        </thead>
        <tbody>
        <tr class="uk-table-middle">
            <td><fmt:message key="last.name"/></td>
            <td>${sessionScope.account.lastName} </td>
        </tr>
        <tr class="uk-table-middle">
            <td><fmt:message key="first.name"/></td>
            <td>${sessionScope.account.firstName} </td>
        </tr>
        <tr class="uk-table-middle">
            <td><fmt:message key="patronymic"/></td>
            <td>${sessionScope.account.patronymic} </td>
        </tr>
        <tr class="uk-table-middle">
            <td><fmt:message key="account.name"/></td>
            <td>${sessionScope.account.email} </td>
        </tr>
        </tbody>
    </table>
    <u:ifAuthAs role="entrant">
        <c:if test="${sessionScope.entrantHowManyMoreSubjectsNeed == 0}">
            <table class="uk-table uk-table-striped uk-table-condensed uk-animation-fade uk-panel-box uk-panel-box-primary">
                <caption>
                    <fmt:message key="account.settings.table.marks"/>
                </caption>
                <thead>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${sessionScope.entrantAccountSettingsBean.subjectMark}">
                    <tr class="uk-table-middle">
                        <td><fmt:message key="${entry.key}"/></td>
                        <td>${entry.value} </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${sessionScope.entrantAccountSettingsNoExtraMarks != true}">
            <table class="uk-table uk-table-striped uk-table-condensed uk-animation-fade uk-panel-box uk-panel-box-primary">
                <caption>
                    <fmt:message key="account.settings.table.extra.marks"/>
                </caption>
                <thead>
                </thead>
                <tbody>
                <tr class="uk-table-middle">
                    <td><fmt:message key="account.settings.add.extra.marks.certificate.points"/></td>
                    <td>${sessionScope.entrantAccountSettingsExtraMarks.certificatePoints}</td>
                </tr>
                <tr class="uk-table-middle">
                    <td><fmt:message key="account.settings.add.extra.marks.additional.points.placeholder"/></td>
                    <td>${sessionScope.entrantAccountSettingsExtraMarks.extraPoints}</td>
                </tr>
                </tbody>
            </table>
        </c:if>
    </u:ifAuthAs>
</div>
</body>
</html>

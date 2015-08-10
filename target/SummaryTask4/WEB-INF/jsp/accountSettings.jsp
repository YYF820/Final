<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%--
  Created by IntelliJ IDEA.
  User: faffi-ubuntu
  Date: 10/08/15
  Time: 12:51
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
<div class="uk-container uk-container-center uk-width-4-10 uk-margin-top ">
    <u:ifAuthAs role="entrant">
        <c:if test="${sessionScope.entrantNoSubjectsAdded == true ||
        sessionScope.entrantAccountSettingsAllSubjects eq 'noSubjectsInDataBase' ||
        sessionScope.entrantAccountSettingsSomethingBad == true}">
            <div class="uk-alert-warning uk-alert uk-text-center">
                <c:choose>
                    <c:when test="${sessionScope.entrantNoSubjectsAdded == true}">
                        <p>You must set up your profile, enter scores for subjects after that you could enroll in the
                            faculties. </p>
                        <a href="<c:url value="/entrant/addSubjects.html"/>">You can do it here <i
                                class="uk-icon-external-link"></i></a>
                    </c:when>
                    <c:when test="${sessionScope.entrantAccountSettingsAllSubjects eq 'noSubjectsInDataBase'}">
                        <p>Currently there are no subjects that you could add to your profile, sorry about this.</p>
                    </c:when>
                </c:choose>
                <c:if test="${sessionScope.entrantAccountSettingsSomethingBad == true}">
                    <p>Something went wrong, problems with servers please try a bit later.</p>
                </c:if>
            </div>
        </c:if>
    </u:ifAuthAs>
    <u:ifAuthAs role="entrant">
        <c:if test="${sessionScope.entrantAccountSettingsNoExtraMarks == true}">
            <div class="uk-alert-warning uk-alert uk-text-center">
                <c:choose>
                    <c:when test="${sessionScope.entrantAccountSettingsNoExtraMarks == true}">
                        <p>You must set up your profile, enter extra scores for school subjects and additional lessons
                            after that you could enroll in the faculties. </p>
                        <a href="<c:url value="/entrant/addExtraMarks.html"/>">You can do it here <i
                                class="uk-icon-external-link"></i></a>
                    </c:when>
                </c:choose>
            </div>
        </c:if>
    </u:ifAuthAs>
    <table class="uk-table uk-table-striped uk-table-condensed uk-animation-fade uk-panel-box uk-panel-box-primary">
        <caption>
            <u:ifAuthAs role="admin">
                Admin
            </u:ifAuthAs>
            <u:ifAuthAs role="entrant">
                Entrant
            </u:ifAuthAs>
        </caption>
        <thead>
        </thead>
        <tbody>
        <tr class="uk-table-middle">
            <td>First name</td>
            <td>${sessionScope.account.firstName} </td>
        </tr>
        <tr class="uk-table-middle">
            <td>Last name</td>
            <td>${sessionScope.account.lastName} </td>
        </tr>
        <tr class="uk-table-middle">
            <td>Patronymic</td>
            <td>${sessionScope.account.patronymic} </td>
        </tr>
        <tr class="uk-table-middle">
            <td>Account name</td>
            <td>${sessionScope.account.email} </td>
        </tr>
        </tbody>
    </table>
    <c:if test="${sessionScope.entrantHowManyMoreSubjectsNeed == 0}">
        <table class="uk-table uk-table-striped uk-table-condensed uk-animation-fade uk-panel-box uk-panel-box-primary">
            <caption>
                Marks
            </caption>
            <thead>
            </thead>
            <tbody>
            <c:forEach var="entry" items="${sessionScope.entrantAccountSettingsBean.subjectMark}">
                <tr class="uk-table-middle">
                    <td>${entry.key}</td>
                    <td>${entry.value} </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${sessionScope.entrantAccountSettingsNoExtraMarks != true}">
        <table class="uk-table uk-table-striped uk-table-condensed uk-animation-fade uk-panel-box uk-panel-box-primary">
            <caption>
                Extra Marks
            </caption>
            <thead>
            </thead>
            <tbody>
            <tr class="uk-table-middle">
                <td>Certificate Points</td>
                <td>${sessionScope.entrantAccountSettingsExtraMarks.certificatePoints}</td>
            </tr>
            <tr class="uk-table-middle">
                <td>Extra Points</td>
                <td>${sessionScope.entrantAccountSettingsExtraMarks.extraPoints}</td>
            </tr>
            </tbody>
        </table>
    </c:if>
</div>
</body>
</html>

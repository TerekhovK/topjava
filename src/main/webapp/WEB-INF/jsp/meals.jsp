<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/datatablesUtil.js" defer></script>
<script type="text/javascript" src="resources/js/mealsDatatables.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><spring:message code="meal.title"/></h3>
        </div>
        <form class="form-horizontal" id="filterDetails">
            <div class="form-group">
                <label for="startDate" class="control-label col-xs-1"><spring:message code="meal.startDate"/></label>

                <div class="col-xs-3">
                    <input type="date" class="form-control" id="startDate" name="startDate"
                           placeholder="<spring:message code="meal.startDate"/>">
                </div>
            </div>

            <div class="form-group">
                <label for="endDate" class="control-label col-xs-1"><spring:message code="meal.endDate"/></label>

                <div class="col-xs-3">
                    <input type="date" class="form-control" id="endDate" name="endDate"
                           placeholder="<spring:message code="meal.endDate"/>">
                </div>
            </div>

            <div class="form-group">
                <label for="startTime" class="control-label col-xs-1"><spring:message code="meal.startTime"/></label>

                <div class="col-xs-3">
                    <input type="time" class="form-control" id="startTime" name="startTime"
                           placeholder="<spring:message code="meal.startTime"/>">
                </div>
            </div>

            <div class="form-group">
                <label for="endTime" class="control-label col-xs-1"><spring:message code="meal.endTime"/></label>

                <div class="col-xs-3">
                    <input type="time" class="form-control" id="endTime" name="endTime"
                           placeholder="<spring:message code="meal.endTime"/>">
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-offset-1 col-xs-1">
                    <a class="btn btn-danger" type="button" onclick="undoFilterTable()">
                        <span class="glyphicon glyphicon-remove-circle" aria-hidden="true"></span>
                        <spring:message code="common.cancel"/>
                    </a>
                </div>
                <div class=" col-xs-2">
                    <a class="btn btn-default" type="button" onclick="newUpdateTable()">
                        <span class="glyphicon glyphicon-filter" aria-hidden="true"></span>
                        <spring:message code="meal.filter"/>
                    </a>
                </div>
            </div>

        </form>
        <hr>

        <div>

            <a class="btn btn-primary btn-centre" onclick="add()">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
                <spring:message code="meal.add"/>
            </a>

        </div>

        <hr>


        <table class="table table-striped display" id="datatable1">
            <thead>
            <tr>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
                <%--<th colspan="2"><spring:message code="common.actions"/></th>--%>
            </tr>
            </thead>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
                <tr class="${meal.exceed ? 'exceeded' : 'normal'}" id="${meal.id}">
                    <td>
                            <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                            <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                            <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                            ${fn:formatDateTime(meal.dateTime)}
                    </td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                    <td><a href="meals/update?id=${meal.id}">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span><spring:message
                            code="common.update"/></a></td>
                    <td><a class="delete">
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span><spring:message
                            code="common.delete"/></a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</div>
</div>
</section>
<jsp:include page="fragments/footer.jsp"/>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><spring:message code="meal.add"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3"><spring:message
                                code="meal.description"/></label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" id="description" name="description"
                                   placeholder="<spring:message code="meal.description"/>">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="dateTime" class="control-label col-xs-3"><spring:message
                                code="meal.dateTime"/></label>

                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" id="dateTime" name="dateTime"
                                   placeholder="">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3"><spring:message
                                code="meal.calories"/></label>

                        <div class="col-xs-9">
                            <input type="number" min="10" max="10000" class="form-control" id="calories" name="calories"
                                   placeholder="1000">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">
                                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
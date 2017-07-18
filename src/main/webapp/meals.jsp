<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="functions" prefix="f" %>
<html>
<style>
    .green {
        color: green;
    }

    .red {
        color: red;
    }
</style>
<head>
    <title>MealsPage</title>
</head>
<body>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr class="${!meal.exceed?"green":"red"}">

            <td> ${f:formatLocalDateTime(meal.dateTime, 'uuuu-MM-dd  HH:mm')}</td>
            <td><c:out value="${meal.description}" /></td>
            <td><c:out value="${meal.calories}" /></td>
            <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Delete</a></td>

        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=add">Add Meal</a></p>
</body>
</html>

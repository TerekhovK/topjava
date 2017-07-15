<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 015 15.07.17
  Time: 16:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="functions" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Add new meal</title>
</head>
<body>


<form method="POST" action='meals' name="frmAddUser">
    <table class="table_price">

        <input type="hidden" name="mealid"
               value="<c:out value="${meal.id}" />"/> <br/>
        <tr>
            <td>DateTime :</td>
            <td><input
                    type="text" name="DateTime"
                    value="<c:out value="${f:formatLocalDateTime(meal.dateTime, 'uuuu-MM-dd  HH:mm')}" />"/> <br/></td>
        </tr>
        <tr>
            <td>Description :</td>
            <td><input
                    type="text" name="description"
                    value="<c:out value="${meal.description}" />"/> <br/></td>
        </tr>
        <tr>
            <td>Calories :</td>
            <td><input
                    type="text" name="calories"
                    value="<c:out value="${meal.calories}" />"/> <br/></td>
        </tr>
        <br/>
        <tr>
            <td><input
                    type="submit" value="Submit"/></td>
        </tr>
    </table>
</form>
</body>
</html>

<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %><%--
  Created by IntelliJ IDEA.
  User: Kirilo
  Date: 29.06.2018
  Time: 22:09
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Meals</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <style type="text/css">
        #maintable td.green {
            color: green;
        }

        #maintable td.red {
            color: red;
        }
    </style>
</head>

<body>

<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>


<%--
https://stackoverflow.com/questions/19090153/dynamic-database-table-display-using-jstl
https://forums.adobe.com/thread/495122
--%>
<table width="600" border="1" cellpadding="5" cellspacing="0" id="maintable">
    <thead>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${exceededList}" var="name">
        <jsp:useBean id="name" scope="page" type="ru.javawebinar.topjava.model.MealWithExceed"/>

        <% String tColor = name.isExceed() ? "red" : "green"; %>
        <tr>
            <td class="<%= tColor %>"><%=TimeUtil.formatToString(name.getDateTime())%>
            </td>
            <td class="<%= tColor %>">${name.description}</td>
            <td class="<%= tColor %>">${name.calories}</td>
            <td><a href="meals?action=update&mealId=<c:out value="${name.id}"/>">Update</a> </td>
            <td><a href="meals?action=delete&mealId=<c:out value="${name.id}"/>">Delete</a> </td>
        </tr>

    </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=create">Add Meal</a></p>
</body>
</html>

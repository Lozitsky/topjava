<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Kirilo
  Date: 30.06.2018
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create or update</title>
</head>
<body>

<h3><a href="index.html">Home</a></h3>
<h2>Add or edit Meal</h2>

<form method="POST" action="meals" name="formMeal">

    ID: <input type="text" readonly="readonly" name="mealId"
               value="<c:out value="${meal.id}"/>"/> <br/>

    Date/Time: <input type="datetime-local" name="dateTime"
                      value="<c:out value="${meal.dateTime}" />"/> <br/>
    Description : <input type="text" name="description"
                         value="<c:out value="${meal.description}"/>"/> <br/>
    Calories : <input type="text" name="calories"
                      value="<c:out value="${meal.calories}"/>"/> <br/>
    <input type="submit" value="Submit"/>

</form>

</body>
</html>

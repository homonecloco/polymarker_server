<%--
  Created by IntelliJ IDEA.
  User: ramirezr
  Date: 19/07/2014
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Results for primer</title>
</head>
<body>
The status of your file is: <strong> ${sf.status}</strong>
<br/>
<c:choose>
    <c:when test="${sf.status=='DONE' || sf.status=='NOTIFIED'}">
<ul>
    <li><a href="get_file?id=${sf.id}&output=primers" >Primers</a></li>
    <li><a href="get_file?id=${sf.id}&output=mask" >Mask</a></li>
</ul>
    </c:when>
    <c:when test="${sf.status=='NEW'}">
       Waiting to be submitted.
    </c:when>

    <c:otherwise>
         Please come back later, you'll recieve an email when the primers had been designed. <br />
    </c:otherwise>
</c:choose>




</body>
</html>

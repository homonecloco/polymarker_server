<%--
  Created by IntelliJ IDEA.
  User: ramirezr
  Date: 19/07/2014
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Results for primer</title>
</head>
<body>
The status of your file is: <strong> ${sf.status}</strong>

<a href="get_file?id=${sf.id}&output=primers" >Primers</a>

<a href="get_file?id=${sf.id}&output=mask" >Mask</a>

</body>
</html>

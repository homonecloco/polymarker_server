<%--
  Created by IntelliJ IDEA.
  User: ramirezr
  Date: 09/12/14
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <c:import url="head.jsp"/>
</head>
<body id="page-wrap">
<c:import url="header.jsp"/>
<div id="main-body">
    ${rendered_md}
 </div>
</body>
</html>

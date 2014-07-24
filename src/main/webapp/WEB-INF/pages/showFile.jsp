<%--
  Created by IntelliJ IDEA.
  User: ramirezr
  Date: 04/03/2014
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>Polymarker</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
</head>
<body id="page-wrap">
<img class="main-header" src="images/polymarker_header.png"/>

<div id="main-body">


  <h2>File submitted</h2>

  File name : "<strong> ${message}</strong>" uploaded.
  You will receive an email when the primers are ready.
  See the status of your request <a href="status?id=${id}">here</a>
</div>

</body>
</html>



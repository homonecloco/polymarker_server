<%--
  Created by IntelliJ IDEA.
  User: ramirezr
  Date: 04/03/2014
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:import url="head.jsp"/>
<body id="page-wrap">
<c:import url="header.jsp"/>
<div id="main-body">
    ${rendered_md}


<form:form method="post" enctype="multipart/form-data"
   modelAttribute="uploadedFile" action="fileUpload.htm">
   <table>
    <tr>
     <td>Upload File: </td>
     <td><input type="file" name="file" />
     </td>
     <td style="color: red; font-style: italic;"><form:errors
       path="file" />
     </td>
    </tr>
       <tr>
           <td>Email:</td>
           <td><form:input path="email" id="emailInput"/>
           </td>
           <td> <form:errors path="email" cssclass="error"/></td>
    <tr>
     <td> </td>
     <td><input type="submit" value="Upload" />
     </td>
     <td> </td>
    </tr>
   </table>
</form:form>

</div>

</body>
</html>



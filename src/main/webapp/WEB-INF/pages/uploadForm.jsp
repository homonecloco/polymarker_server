<%--
  Created by IntelliJ IDEA.
  User: ramirezr
  Date: 04/03/2014
  Time: 12:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<script type="text/javascript" src="jquery-2.1.0.js"></script>
<title>Being Java Guys | Hello World</title>
</head>
<body>


  <h2>Polymarker </h2>
  <h3>Please select a file with the primers.</h3>
  The input must be a CSV (can be exprted from excel) with the following collumns:
  primer_id,chromosome,sequence

  The snp must be marked in the format: [a/t] for either A or T
  Example:
  Primer_1,2BS,GAAAACA<b>[T/C]</b>ACGGGGGAAAA

  Polymarker takes ~1 minute per marker, once it has started. Please provide an email to send you the link with your results.
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
           <td><input type="text" name="email" />
           </td>
           <td> </td>
    <tr>
     <td> </td>
     <td><input type="submit" value="Upload" />
     </td>
     <td> </td>
    </tr>
   </table>
  </form:form>

</body>
</html>



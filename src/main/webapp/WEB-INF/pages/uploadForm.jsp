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
    <h1>Request primers</h1>
    <p>
        PolyMarker is an automated bioinformatics pipeline for SNP assay
        development which increases the probability of generating
        homoeologue-specific assays for polyploid wheat.
        PolyMarker generates a multiple alignment between the target SNP
        sequence and the IWGSC chromosome survey sequences
        (<a href=" http://www.sciencemag.org/content/345/6194/1251788.abstract" >IWGSC, 2014</a> )
        for each of the three wheat genomes. It then generates a mask with
        informative positions which are highlighted with respect to the target genome.
        <br/>
        See <a href="/Markdown?md=About">About</a> to know how to prepare your input.
        <br/>
        We have <a href="/Markdown?md=DesignedPrimers">designed primers</a> for the iSelect 90K chip
</p>


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



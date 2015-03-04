<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:import url="head.jsp"/>

<body id="page-wrap">
<c:import url="header.jsp"/>
<div id="main-body">
<div id="wrapper">
    <div id="content">
        ${rendered_md}
        ${papers_md}
    </div>
    <div id="sidebar">
        <form:form method="post" enctype="multipart/form-data"
           modelAttribute="uploadedFile" action="fileUpload.htm">

            <fieldset>

                <label for="emailInput">Email: </label>
                <form:input path="email" id="emailInput"/>
                 <form:errors path="email" cssclass="error"/>
                <br/>

                <label for="file">File: </label>
                <input id="file" type="file" name="file"  />
                 <form:errors path="file" cssclass="error" />
                <br/>

                    <label for="reference">Reference: </label>
                    <select name="reference" id="reference">
                    <c:forEach items="${references}" var="item" >
                        <option value="${item}"  ${item.key == reference ? 'selected="selected"' : ''} >
                            ${item.value}
                        </option>
                    </c:forEach>

                    </select>
                <form:errors path="reference" cssclass="error" />
                <br/>
                <input type="submit" value="Upload" />


            </fieldset>



            </div>



        </form:form>
    </div>
</div>

</body>
</html>



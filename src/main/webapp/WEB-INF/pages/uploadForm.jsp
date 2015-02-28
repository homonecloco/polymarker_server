<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:import url="head.jsp"/>
<script>
    $(function() {
        dialog = $("#preferences").dialog({
            autoOpen: true,
            height: 300,
            width: 350,
            modal: true,
            close: function () {
                // form[ 0 ].reset();
                // allFields.removeClass( "ui-state-error" );
            }
        });
        console.log("DIalog: " + dialog);
    }
    )
</script>
<body id="page-wrap">
<c:import url="header.jsp"/>
<div id="main-body">
    ${rendered_md}




<form:form method="post" enctype="multipart/form-data"
   modelAttribute="uploadedFile" action="fileUpload.htm">

    <fieldset>
        <label for="emailInput">Email</label>
        <form:input path="email" id="emailInput"/>
         <form:errors path="email" cssclass="error"/>
        <br/>
        <label for="file">File</label>

        <input type="file" name="file" />
        <form:errors path="file" />
    </fieldset>

    <div id="preferences" title="Preferences">
      <fieldset>
        <p>Options:</p>
      </fieldset>

    </div>


    <input type="submit" value="Upload" />
</form:form>

</div>

</body>
</html>



<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<c:import url="head.jsp"/>
<script>
    $(function() {
        dialog = $("#preferences").dialog({

            autoOpen: false,
            height: 300,
            width: 350,
            modal: true

            //close: function () {
                // form[ 0 ].reset();
                // allFields.removeClass( "ui-state-error" );
            //}
        });

        $( "#preferences-button" ).button().on( "click", function() {
            dialog.dialog( "open" );
            return false;
          });

         $(function() {
                    $( "input[type=submit]" )
                      .button() ;

                  });
       }


    )
</script>
<body id="page-wrap">
<c:import url="header.jsp"/>
<div id="main-body">
    adads
    ${rendered_md}




<form:form method="post" enctype="multipart/form-data"
   modelAttribute="uploadedFile" action="fileUpload.htm">

    <fieldset id="upload_file_fieldset">
        <label for="emailInput">Email</label>
        <form:input path="email" id="emailInput"/>
         <form:errors path="email" cssclass="error"/>
        <br/>
        <label for="file">File</label>

        <input type="file" name="file" />
        <form:errors path="file" />
        <br/>
        <button id="preferences-button" >Preferences</button>
        <input type="submit" value="Upload" />
    </fieldset>

    <div id="preferences" title="Preferences">


    </div>



</form:form>

</div>

</body>
</html>



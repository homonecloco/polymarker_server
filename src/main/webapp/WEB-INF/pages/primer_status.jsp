<%--
  Created by IntelliJ IDEA.
  User: ramirezr
  Date: 19/07/2014
  Time: 19:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Polymarker-Results</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <link rel="stylesheet" href="css/csvtable.css" type="text/css" />
    <link rel="stylesheet" href="css/msa.min.gz.css" type="text/css" />
    <script type="text/javascript" src="js/jquery-2.1.0.js"></script>
    <script type="text/javascript" src="js/jquery.csvToTable.js"></script>
    <script type="text/javascript" src="js/jExpand.js"></script>
    <!--<script type="text/javascript" src="js/biojs_io_fasta.min.js"></script>   -->
    <!--  <script type="text/javascript" src="js/msa.min.gz.js"></script>            -->
    <script src=//cdn.biojs-msa.org/msa/latest/msa.min.gz.js></script>

    <script>
        $(function() {
            var table = $('#CSVTable').CSVToTable('get_file?id=${sf.id}&output=primers', {headers: ["ID",	"SNP", "chr","total", 	"contig regions", 	"SNP type", "A", "B","common", "primer type", "product size"], startLine: 1});
            table.bind("loadComplete",function() {
                $('#CSVTable').jExpand();
                $('#CSVTable').load_msa(${sf.id});
            });
            $('#statusTable').jExpand();

            $( "#show_list").click(function() {
                $("#statusTable" ).toggle();
            });
            $("#statusTable").hide();
            $( "#show_mask").click(function() {
                $("#CSVTable" ).show_all();
            });
            $( "#hide_mask").click(function() {
                $("#CSVTable" ).hide_all();
            });



        });
    </script>
</head>
<body id="page-wrap">
<img class="main-header" src="images/polymarker_header.png"/>

<div id="main-body">
    The status of your file (${sf.filename} ) is: <strong> ${sf.status}</strong>
    <br/>
    <button id="show_list" > Show or hide primer warning</button>
    <table id="statusTable" class="CSVTable" >
        <thead>
        <tr >
            <th>id</th>
            <th>Message</th>
        </tr>

        </thead>
        <tbody>
        <c:forEach items="${sf.snpList}" var="element">
            <c:if test="${fn:length(element.warnings) gt 0}">
                <tr>
                    <td>${element.name}</td>
                    <td>
                        <c:forEach items="${element.warnings}" var="warn">
                            ${warn.message} <br/>
                        </c:forEach>

                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="font-family: monospace">
                        <c:forEach items="${element.sequenceSplitted}" var="seq">
                            ${seq} <br/>
                        </c:forEach>
                    </td>
                </tr>
            </c:if>


        </c:forEach>


        </tbody>

    </table>



    <c:choose>
        <c:when test="${sf.status=='DONE' || sf.status=='NOTIFIED'}">
            <ul>
                <li><a href="get_file?id=${sf.id}&output=primers" >Primers</a></li>
                <li><a href="get_file?id=${sf.id}&output=mask" >Mask</a></li>
            </ul>
            <button id="show_mask" > Show all masks</button>
            <button id="hide_mask" > Hide all masks</button>
            <div id="CSVTable"></div>
        </c:when>
        <c:when test="${sf.status=='NEW'}">
            Waiting to be submitted.
        </c:when>

        <c:otherwise>
            Please come back later, you'll receive an email when the primers had been designed. <br />
        </c:otherwise>
    </c:choose>
</div>



</body>
</html>

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
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <script type="text/javascript" src="js/jquery-2.1.0.js"></script>

<title>Polymarker</title>
</head>
<body id="page-wrap">
<img class="main-header" src="images/polymarker_header.png"/>

<div id="main-body">
<p>
    PolyMarker is an automated bioinformatics pipeline for SNP assay development which increases the probability of generating homoeologue-specific assays for polyploid wheat.
    PolyMarker generates a multiple alignment between the target SNP sequence and the IWGSC chromosome survey sequences (<a href=" http://www.sciencemag.org/content/345/6194/1251788.abstract" >IWGSC, 2014</a> ) for each of the three wheat genomes.
    It then generates a mask with informative positions which are highlighted with respect to the target genome.
</p>

<p>These positions include (see figure for example):
    <ul>
     <li>Varietal polymorphism: this is the SNP that is targeted in the assay (&)</li>
     <li>Genome specific: this is a homoeologous polymorphism which is <em style="text-decoration: underline" >only</em> present in the target genome (upper case)</li>
     <li>Genome semi-specific: this is a homoeologous polymorphism which is found in 2 of the 3 genomes, hence it discriminates against one of the off-target genomes (lowercase)  </li>
     <li>Homoeologous: if the target varietal SNP is also a homoeologous polymorphism between A,B and D genomes in the reference Chinese Spring </li>
    </ul>

</p>
<p>PolyMarker will generate KASP assays (<a href="http://www.lgcgenomics.com/genotyping/kasp-genotyping-chemistry/">read more</a>) which are based on a three primer system.
 Two diagnostic primers incorporate the alternative varietal SNP at the 3’ end, but are otherwise similar (black boxed primers in figure).
 The third common primer is preferentially selected to incorporate a genome-specific base at the 3´end (red boxed primer in figure), or a semi-specific base in the absence of an adequate genome specific position.
</p>

<p>
    The detailed code of the PolyMarker pipeline is available in <a href="https://github.com/TGAC/bioruby-polyploid-tools">github</a>
</p>


<h3>Using PolyMarker</h3>
<p>
<ul>
     <li>The input file must be uploaded as a CSV file (can be exported from Excel) with the following columns: Gene_id, target chromosome, sequence. </li>
    <li>The SNP must be marked in the format <b>[A/T]</b> for a varietal SNP with alternative bases of A or T. </li>
    <li>PolyMarker takes ~1 minute per marker assuming an input sequence of 200 bp (with the varietal SNP in the middle).
        <em>[Longer sequences can be used, but this will slow down the initial BLAST against the wheat survey sequence.
            We have not seen improvement in performance with longer sequences; therefore we recommend 200-bp of input sequence.
            The final multiple alignment for the primer design only considers 100-bp on either side of the target varietal SNP.] </em>
    </li>
    <li>Exonerate is used to search for the contigs which align to the SNP. By default, the miniumm identity used to match across the genomes it is 90% and the model used is est2genome. </li>
    <li>Please provide an email to send the link with your results.</li>
</ul>
</p>
<p>

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
</p>

<p>
<h3>Example</h3>


<h4>Input file</h4>
<code>
Gene_1,6B,GATAAGCGATGACGATACGGACGACA[A/T]ACGGGGGACGAGGGATACGAT <br/>
Gene_2,2A,CGATAGCATAGCATGGCGTTAGCAGT[G/C]TAGTACGATAGATCAGTACGA  <br/>
Ta#S58861868,1A,CATACTGATGACACGATTGGCTACSGGCCTTGAAGATAGMAGCAGAT[A/G]ACTTCAGTGTAATCCAAGTTGACTG
</code>


<h4>Output: mask</h4>
The mask contains the details of the local alignment
<img src="images/mask.png" width="800px"/>
</p>
</div>

</body>
</html>



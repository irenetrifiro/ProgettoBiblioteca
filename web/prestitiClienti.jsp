<%@page import="dao.Libro"%>
<%@page import="dao.Prestito"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>prestitiClienti</title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <!-- Fogli stile personali -->
        <link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
        <link href="bibliostyle.css" rel="stylesheet" type="text/css"/>

        <!-- Librerie per bootstrap -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <script src="javascript.js" type="text/javascript"></script>
    </head>

    <body>

        <!-- INIZIO NAVBAR -->
        <%@include file="navbar.txt"%>
        <!-- FINE NAVBAR -->

        <!-- LOGO DELL'APPLICAZIONE -->
        <%@include file="header.txt" %>

        <!-- INIZIO CONTAINER -->
        <div class="container main-container">

            <!-- TITOLO -->
            <h3 class="titolo">PRESTITI CLIENTI</h3>

            <!-- INIZIO SEARCHBAR-->
            <div class="row searchBar">
                <div class="col-md-4 col-md-offset-8">
                    <div class="input-group col-md-12">
                        <span class="input-group-addon" id="basic_addon">
                            <i class="glyphicon glyphicon-search" id="searchedBookicon"></i>
                        </span>
                        <input type="text" id="toSearch" class="form-control input-lg" placeholder="Ricerca per account..." onkeyup="searchIntoTable('borrowTableAdmin')" aria-describedby="basic_addon" />
                    </div>
                </div>
            </div>
            <!-- FINE SEARCHBAR -->

            <!-- INZIO TABELLA PRESTITI -->
            <%
                if (((ArrayList<Prestito>) request.getAttribute("prestiti")).isEmpty()) {%>
		    <p class="noBorrow">Non sono stati effettuati prestiti</p>
            <%	} 
		else {%> 
		    <table class="table" id="borrowTableAdmin">
			<thead>
			    <tr>
				<th class="col-md-3">Account</th>
				<th class="col-md-3">Libro</th>
				<th class="center">Data prestito</th>
				<th class="center">Data restituzione</th>
			    </tr>                       
			</thead>
			<tbody>
			    <!-- RIEMPIMENTO DELLA TABELLA -->
			    <c:forEach items="${prestiti}" var="prestito">
				<tr>
				    <td>${prestito.getUtente()}</td>
				    <td>${prestito.getLibro().getTitolo()}</td>
				    <td class="center">${prestito.getStringD_inverted(prestito.getD_prestito())}</td>
				    <c:choose>
					<c:when test="${empty prestito.getD_restituzione()}">
					    <td class="daRestituire">${'DA RESTITUIRE'}</td>
					</c:when>
					<c:otherwise>
					    <td class="center">${prestito.getStringD_inverted(prestito.getD_restituzione())}
					</c:otherwise>
				    </c:choose> 
				</tr>
			    </c:forEach>
			</tbody>
		    </table>
		<%}
            %>
            <!-- FINE TABELLA PRESTITI -->

        </div>
        <!-- FINE CONTAINER -->

        <!-- FOOTER -->
        <%@include file="footer.txt"%>

    </body>
</html>
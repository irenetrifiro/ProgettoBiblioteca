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
        <title>restituisciPrestito</title>

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
            <h3 class="titolo">RESTITUISCI PRESTITO</h3>

            <!-- INZIO TABELLA CATALOGO LIBRI -->
            <%
                if (((ArrayList<Prestito>) request.getAttribute("prestitiAttivi")).isEmpty()) {
            %>
		    <p class="noBorrow">Non sono presenti prestiti attivi</p>
            <%
		} else {
            %>
		    <table class="table" id="activeBorrowTable">
			<thead>
			    <tr>
				<th class="col-md-4">Titolo</th>
				<th class="col-md-3">Autori</th>
				<th class="col-md-2">Editore</th>
				<th class="center">Data prestito</th>
				<th class="center">Restituisci</th>
			    </tr>                       
			</thead>
			<tbody>
			    <!-- RIEMPIMENTO DELLA TABELLA -->
			    <c:forEach items="${prestitiAttivi}" var="prestito">
				<tr>
				    <td>${prestito.getLibro().getTitolo()}</td>
				    <td>${prestito.getLibro().getStringAutori()}</td>
				    <td>${prestito.getLibro().getEditore()}</td>
				    <td class="center">${prestito.getStringD_inverted(prestito.getD_prestito())}</td>
				    <td class="center">
					<a href="/ProgettoBiblioteca/Controller?action=restituisci&id=${prestito.getLibro().getId()}" onclick="return confirmReturn('${prestito.getLibro().getTitolo()}')">
					    <span class="glyphicon glyphicon-ok"></span>
				    </td>
				</tr>
			    </c:forEach>
			</tbody>
		    </table>
	    <%
                }
            %>
            <!-- FINE TABELLA LIBRI -->

            <!-- ESITO RESTITUZIONE LIBRO -->
            <%
                if (request.getAttribute("succesMsg") != null) {
                    out.println("<p class=\"success\">" + request.getAttribute("succesMsg") + "</p>");
                }
                if (request.getAttribute("errorMsg") != null) {
                    out.println("<p class=\"error\">" + request.getAttribute("errorMsg") + "</p>");
                }
            %>

        </div>
        <!-- FINE CONTAINER -->

        <!--FOOTER-->
        <%@include file="footer.txt"%>

    </body>
</html>
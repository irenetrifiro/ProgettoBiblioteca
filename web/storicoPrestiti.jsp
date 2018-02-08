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
        <title>storicoPrestiti</title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <!-- Fogli stile personali -->
        <link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
        <link href="bibliostyle.css" rel="stylesheet" type="text/css"/>

        <!-- Librerie per bootstrap -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

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
            <h3 class="titolo">STORICO PRESTITI</h3>

            <!-- INZIO TABELLA CATALOGO LIBRI -->
            <%
                if (((ArrayList<Prestito>) request.getAttribute("prestiti")).isEmpty()) {
            %>
		    <p class="noBorrow">Non sono stati effettuati prestiti</p>
            <%
		} else {
            %>
		    <table class="table table" id="historianBorrowTable">
			<thead>
			    <!-- stampa prima riga tabella -->
			    <tr>
				<th class="col-md-4">Titolo</th>
				<th class="col-md-3">Autori</th>
				<th class="col-md-2">Editore</th>
				<th class="center">Data prestito</th>
				<th class="center">Data restituzione</th>
			    </tr>
			</thead>
			<tbody>
			    <!-- RIEMPIMENTO DELLA TABELLA -->
			    <c:forEach items="${prestiti}" var="prestito">
				<tr>
				    <td>${prestito.getLibro().getTitolo()}</td>
				    <td>${prestito.getLibro().getStringAutori()}</td>
				    <td>${prestito.getLibro().getEditore()}</td>
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
            <!-- FINE TABELLA LIBRI -->

        </div>
        <!-- FINE CONTAINER -->

        <!--FOOTER-->
        <%@include file="footer.txt"%>
    </body>
</html>

<%@page import="dao.Libro"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestione Catalogo</title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

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
        <%@include file="header.txt"%>

        <!-- INIZIO CONTAINER -->
        <div class="container main-container">

            <h3 class="titolo">GESTIONE CATALOGO</h3>

            <!-- searchbar + inserisci -->
            <div class="row searchBar">

                <!-- INIZIO INSERISCI -->
                <%
                    if (session.getAttribute("ruolo") != null && session.getAttribute("ruolo").equals("Amministratore")) {%>
			<div class="col-md-2">
			    <a href="inserisciLibro.jsp" class="btn btn-lg btn-block" role="button">Inserisci</a>
			</div>
		    <%}
                %>
                <!-- FINE INSERISCI -->

                <!-- INIZIO SEARCHBAR -->
                <div class="col-md-4 col-md-offset-6">
                    <div class="input-group col-md-12">
                        <span class="input-group-addon">
                            <i class="glyphicon glyphicon-search" id="searchedBookicon"></i>
                        </span>
                        <input type="text" id="toSearch" class="form-control input-lg" placeholder="Ricerca per titolo..." onkeyup="searchIntoTable('bookTable')" />
                    </div>
                </div>
                <!-- FINE SEARCHBAR -->
            </div>

            <!-- INZIO TABELLA CATALOGO LIBRI -->
            <table class="table" id="bookTable">
                <thead>
                    <tr>
                        <th class="col-md-4">Titolo</th>
                        <th class="col-md-3">Autori</th>
                        <th class="col-md-2">Editore</th>
                        <th class="center">Disponibili</th>
                        <%
			    if (session.getAttribute("ruolo") != null && session.getAttribute("ruolo").equals("Amministratore")) {%>
				<th class="center">Rimuovi</th>
                            <%}
			%>
                    </tr>
                </thead>
                <tbody id="bodyBookTable">
                    <!-- RIEMPIMENTO DELLA TABELLA -->
                    <c:forEach items="${applicationScope.catalogo}" var="libro">
                        <tr>
                            <td>${libro.getTitolo()}</td>
                            <td>${libro.getStringAutori()}</td>
                            <td>${libro.getEditore()}</td>
                            <td class="center">${libro.getDisponibili()}</td>

                            <c:if test="${sessionScope.ruolo == 'Amministratore'}">
                                <td class="center">
                                    <a onclick="removeBook(this, ${libro.getId()}, '${libro.getTitolo()}')">
                                        <span class="glyphicon glyphicon-remove" role="button"></span>
                                    </a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <!-- FINE TABELLA LIBRI -->

        </div>
        <!-- FINE CONTAINER -->

        <!-- FOOTER -->
        <%@include file="footer.txt"%>
	
    </body>

</html>

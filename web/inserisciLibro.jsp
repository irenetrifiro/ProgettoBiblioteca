<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inserisci</title>

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
        <link href="bibliostyle.css" rel="stylesheet" type="text/css"/>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <script src="javascript.js" type="text/javascript"></script>

        <script>

	    //quando la pagina è stata caricata
            $(document).ready(function () {

		//al submit del form
                $('form').submit(function (e) {

                    var title = $.trim($('#title').val()); //titolo del libro
                    var editor = $.trim($('#editor').val()); //editore del libro
                    var available = $.trim($('#available').val()); //numero di libri disponibili
                    var regex = /^([1-9]|[1-9]+[0-9]*[0-9])$/; //espressione regolare per interi positivi

                    if (title.length === 0) {
                        e.preventDefault();
                        alert('Campo titolo mancante!');
                    } else if (editor.length === 0) {
                        e.preventDefault();
                        alert('Campo editore mancante!');
                    } else if (available.length === 0) {
                        e.preventDefault();
                        alert('Campo disponibili mancante!');
                    } else if (!regex.test(available)) {
                        e.preventDefault();
                        alert('Inserire un numero maggiore di 0!');
                    } else if (!authorExists()) {
                        e.preventDefault();
                        alert('Inserire almeno un autore!');
                    }
                });

                /*
                 * Verifica che almeno un campo autore sia compilato
                 * return true se almeno un campo è compilato
                 * return false se nessun campo è stato compilato
                 */
                function authorExists() {

                    var author1 = $.trim($('#author1').val());
                    var author2 = $.trim($('#author2').val());
                    var author3 = $.trim($('#author3').val());
                    var author4 = $.trim($('#author4').val());
                    var author5 = $.trim($('#author5').val());
                    var author6 = $.trim($('#author6').val());

                    //verifica se c'è autore 1
                    if (author1.length > 0) {
                        console.log('true');
                        return true; //c'è almeno autore 1
                    }

                    //non c'è autore 1, prova con 2
                    if (author2.length > 0) {
                        console.log('autore2: ' + author2);
                        return true; //c'è almeno autore 2
                    }

                    //non ci sono autore 1 e 2, prova con 3
                    if (author3.length > 0) {
                        console.log('autore3: ' + author3);
                        return true; //c'è almeno autore 3
                    }

                    //non ci sono autore 1,2 e 3, prova con 4
                    if (author4.length > 0) {
                        console.log('autore4: ' + author4);
                        return true; //c'è almeno autore 4
                    }

                    //non ci sono autore 1,2,3 e 4, prova con 5
                    if (author5.length > 0) {
                        console.log('autore5: ' + author5);
                        return true; //c'è almeno autore 5
                    }

                    //non ci sono autore 1,2,3,4, e 5, prova con 6
                    if (author6.length > 0) {
                        console.log('autore6: ' + author6);
                        return true; //c'è autore 6
                    }

                    return false; //nessun campo autore compilato

                }

            });

        </script>

    </head>
    <body>

        <!-- INIZIO NAVBAR -->
        <%@include file="navbar.txt"%>
        <!-- FINE NAVBAR -->

	<!-- LOGO APPLICAZIONE -->
        <%@include file="header.txt" %>

	<!-- INIZIO CONTAINER -->
        <div class="container main-container">

            <div class="add-container">
                <h2>INSERISCI LIBRO</h2>
                <form class="form-horizontal" name="addForm" action="/ProgettoBiblioteca/Controller" method="post">

                    <!-- TITOLO -->
                    <div class="form-group">
                        <label class="control-label col-md-2" for="title">Titolo: </label>
                        <div class="col-md-9">
                            <input id="title" type="text" class="form-control" name="title" 
                                   <%
                                       if (request.getAttribute("title") != null) {
                                           out.print("value=" + request.getAttribute("title"));
                                       }
                                   %>
                                   required>
                        </div>
                    </div>

                    <!-- AUTORI -->
                    <div id="authorsList">
                        <div class="form-group">
                            <label class="control-label col-md-2" for="author1">Autori: </label>
                            <div class="col-md-9">
                                <input id="author1" type="text" class="form-control" name="author1">
                            </div>
                            <div class="col-md-1">
                                <i class="btn glyphicon glyphicon-plus" onclick="addAuthorField();"></i>
                            </div>
                        </div>
                    </div>

                    <!-- EDITORE -->
                    <div class="form-group">
                        <label class="control-label col-md-2" for="editor">Editore: </label>
                        <div class="col-md-9">
                            <input id="editor" type="text" class="form-control" name="editor" 
                                   <%
                                       if (request.getAttribute("editor") != null) {
                                           out.print("value=" + request.getAttribute("editor"));
                                       }
                                   %>
                                   required>
                        </div>
                    </div>

                    <!-- DISPONIBILI -->
                    <div class="form-group">
                        <label class="control-label col-md-2" for="available">Disponibili: </label>
                        <div class="col-md-9">
                            <input id="available" type="number" value="1" min="1" class="form-control" name="available" 
                                   <%
                                       if (request.getAttribute("available") != null) {
                                           out.print("value=" + request.getAttribute("available"));
                                       }
                                   %>
                                   required>
                        </div>
                    </div>

                    <!-- BOTTONE INSERISCI -->
                    <div class="btn-lg-container">
                        <button type="submit" class="btn btn-lg btn-primary btn-block" name="action" value="inserisci">Inserisci</button>
                    </div>

                </form>

                <!-- MESSAGGI PER L'UTENTE (ERRORE NEL FORM) -->
                <%
                    if (request.getAttribute("errorMsg") != null) {
                        out.println("<p class=\"error\">" + request.getAttribute("errorMsg") + "</p>");
                    }
                %>

            </div>
        </div>
	<!-- FINE CONTAINER -->

        <!-- FOOTER -->
        <%@include file="footer.txt"%>
        
    </body>
</html>

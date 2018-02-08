<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

    <head>
        <title>Login</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <link href="https://fonts.googleapis.com/css?family=Indie+Flower" rel="stylesheet">
        <link href="bibliostyle.css" rel="stylesheet" type="text/css"/>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <script>
	    
	    //quando la pagina è stata caricata
            $(document).ready(function () {

		//al submit del form
                $("form").submit(function (e) {

                    var email = $('#user').val(); //email
                    var pwd = $('#pwd').val(); //password
                    var regex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/; //espressione regolare per l'email

                    if (email === "") {
                        alert("Campo email mancante!");
                        e.preventDefault();
                    } else if (!regex.test(email)) {
                        alert("Email non valida!");
                        e.preventDefault();
                    } else if (pwd === "") {
                        alert("Campo password mancante!");
                        e.preventDefault();
                    }
                });
            });

        </script>
    </head>

    <body>

        <!-- INIZIO NAVBAR -->
        <%@include file="navbar.txt"%>
        <!-- FINE NAVBAR -->

	<!-- LOGO APPLICAZIONE -->
        <%@include file="header.txt"%>

	<!-- INIZIO CONTAINER -->
        <div class="container main-container">

            <div class="login-container">
                <h2 class="login-title">LOGIN</h2>
                <form name="loginForm" action="/ProgettoBiblioteca/Controller" method="post">
		    <!-- USERNAME -->
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input id="user" type="text" class="form-control" name="user" placeholder="Email" 
                               <%
                                   if (request.getAttribute("user") != null) {
                                       out.print("value=" + request.getAttribute("user"));
                                   }
                               %>
                               required>

                    </div>
		    <!-- PASSWORD -->
                    <div class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input id="pwd" type="password" class="form-control" name="pwd" placeholder="Password" required>
                    </div>

                    <div class="btn-lg-container">
                        <button type="submit" class="btn btn-lg btn-primary btn-block" name="action" value="login">Accedi</button>
                    </div>

                </form>

                <!-- ERRORE NEL FORM -->
                <%
                    if (request.getAttribute("errorMsg") != null) {
                        out.println("<p class=\"error\">" + request.getAttribute("errorMsg") + "</p>");
                    }
                %>
                
            </div>
                
        </div>
        <!-- FINE CONTAINER -->  
	
        <!--FOOTER -->
        <%@include file="footer.txt"%>
        
    </body>
</html>

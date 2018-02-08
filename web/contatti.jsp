<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contatti</title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

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

	    <h3 class="titolo">CONTATTI</h3>

	    <div class="row">
		<!-- MAPPA -->
		<div class="col-sm-6 col-sm-push-6">
		    <section id="dove-siamo">
			<h3 class="contatti-titoli">Dove siamo</h3>
			<div id="google-maps">
			    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2816.7964646436635!2d7.657224514869105!3d45.08992036636128!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47886db2ac05e99b%3A0x53d868944a1e154b!2sVia+Pessinetto%2C+12%2C+10149+Torino!5e0!3m2!1sit!2sit!4v1491784233847" width="555" height="450" frameborder="0" style="border:0" allowfullscreen></iframe>
			</div>
		    </section>
		</div>
		<!-- CONTATTI -->
		<div class="col-sm-6 col-sm-pull-6">
		    <section id="info-contatto">
			<h3 class="contatti-titoli">Recapiti</h3>
			<address>
			    <strong>E-lib</strong><br>
			    Via Pessinetto, 12<br>
			    Torino, 10149, Italy<br>
			    Telefono/fax: 123 456-7890
			</address>
			<address>
			    <strong>E-mail</strong>
			    <a href="mailto:#">info@elib.it</a>
			</address>
		    </section>
		</div>
	    </div>

	    <!-- DOMANDE FREQUENTI -->
	    <section id="faq">
		<h3 class="contatti-titoli">Domande frequenti</h3>
		<div class="panel-group" id="accordion-faq">
		    <div class="panel panel-default">
			<div class="panel-heading">
			    <h4 class="panel-title">
				<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion-faq" href="#faq-pannello-1">
				    &Egrave; possibile prendere in prestito pi&ugrave; copie dello stesso titolo?</a>
			    </h4>
			</div>
			<div id="faq-pannello-1" class="panel-collapse collapse">
			    <div class="panel-body">
				<p>&Egrave; consentito prendere in prestito una sola copia dello stesso titolo. Una volta restituito il prestito &egrave; consentito richiederne un nuovo per lo stesso titolo.</p>
			    </div>
			</div>
		    </div>
		    <div class="panel panel-default">
			<div class="panel-heading">
			    <h4 class="panel-title">
				<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion-faq" href="#faq-pannello-2">
				    &Egrave; possibile prendere in prestito più libri?</a>
			    </h4>
			</div>
			<div id="faq-pannello-2" class="panel-collapse collapse">
			    <div class="panel-body">
				<p>Non sono presenti limitazioni sul numero di libri da prendere in prestito, ma non &egrave; possibile prendere in prestito pi&ugrave; di una copia dello stesso titolo.</p>
			    </div>
			</div>
		    </div>
		    <div class="panel panel-default">
			<div class="panel-heading">
			    <h4 class="panel-title">
				<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion-faq" href="#faq-pannello-3">
				    Perchè il flag di prenotazione libro &egrave; disabilitato?</a>
			    </h4>
			</div>
			<div id="faq-pannello-3" class="panel-collapse collapse">
			    <div class="panel-body">
				<p>Il flag di prenotazione libro &egrave; disabilitato in due casi: <br>
				    - se in quel momento non sono disponibili copie in biblioteca poich&egrave; in prestito <br>
				    - se hai personalmente in prestito una copia del libro. &Egrave; possibile controllare ci&ograve; nella pagina Restituzione Prestito o nella pagina Storico Prestito prestando attenzione al fatto che il suddetto libro presenter&agrave; come data di restituzione la voce 'DA RESTITUIRE'.
				</p>
			    </div>
			</div>
		    </div>
		</div>
	    </section>
        </div>
	<!-- FINE CONTAINER -->

        <%@include file="footer.txt"%>

    </body>
</html>
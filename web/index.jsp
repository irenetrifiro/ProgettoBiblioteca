<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Home</title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

        <link href="bibliostyle.css" rel="stylesheet" type="text/css"/>

        <!-- Librerie per bootstrap -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

        <script src="javascript.js" type="text/javascript"></script>

        <script>
	    //quando la pagina è stata caricata
            $(document).ready(function(){
		
                var $item = $('.carousel .item');
                var $wHeight = $(window).height();
		
		//seleziona elemento del DOM con indice 0 (prendi la prima immagine)
                $item.eq(0).addClass('active');
                $item.height($wHeight);
                $item.addClass('full-screen');

		//per ogni immagine del carousel
                $('.carousel img').each(function () {
                    var $src = $(this).attr('src');
                    var $color = $(this).attr('data-color');
                    $(this).parent().css({
                        'background-image': 'url(' + $src + ')',
                        'background-color': $color
                    });
                    $(this).remove();
                });

		/* metodo on() mette a disposizione uno o più event handler all'elemento selezionato */
		//funzione da eseguire all'evento resize
                $(window).on('resize', function () {
                    $wHeight = $(window).height();
                    $item.height($wHeight);
                });

		//attiva la riproduzione del carousel
                $('.carousel').carousel({
                    interval: 6000,
                    pause: "false"
                });
            });
            
        </script>

    </head>

    <body class="bodyIndex">
        <!-- INIZIO NAVBAR -->
        <%@include file="navbar.txt"%>
        <!-- FINE NAVBAR -->

        <!-- slideshow -->

        <!-- Carousel -->
        <div id="myCarousel" class="main-index carousel slide" data-ride="carousel">
	    
            <!-- Indicators -->
            <ol class="carousel-indicators">
                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
                <li data-target="#myCarousel" data-slide-to="2"></li>
            </ol>

            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox">
                <div class="item active">
                    <img src="img/Biblioteca.jpg" data-color="lightblue" alt="Biblioteca">
                </div>

                <div class="item">
                    <img src="img/Books.jpg" data-color="ochre" alt="Libri">

                </div>

                <div class="item">
                    <img src="img/RagazzaLibro.jpg" data-color="firebrick" alt="Ragazza">
                </div>
            </div>

            <!-- Left and right controls -->
            <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                <span class="glyphicon glyphicon-chevron-left"></span>
                <span class="sr-only">Precedente</span>
            </a>
            <a class="right carousel-control" href="#myCarousel" data-slide="next">
                <span class="glyphicon glyphicon-chevron-right"></span>
                <span class="sr-only">Successivo</span>
            </a>
        </div>

        <!-- FOOTER -->
        <%@include file="footer.txt"%>
    </body>

</html>

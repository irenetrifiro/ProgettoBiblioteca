/* variabili globali */

var req; //oggetto request
var bodyBookTable; //body della tabella contenente il catalogo dei libri
var numAuthors = 1; //numero di campi autori attuale
var bookToRemove; //riga della tabella corrispondente il libro da rimuovere
var bookToBorrow; //cella icona di prenotazione
var disponibilityNode; //cella disponibilità
var icon; //icona prenotazione

//funzione per l'inizializzazione dell'oggetto request (compatibilità con i vecchi browser)
function initRequest() {
    if (window.XMLHttpRequest) {
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

//funzione di prenotazione libro tramite ajax
function borrowBook(element, id, title) {
    //chiedo all'utente se è sicuro di voler prenotare il libro
    if (confirm("Sei sicuro di voler prendere in prestito il libro " + title + "?") === true) {
        //salvo td icona in variabile globale
        bookToBorrow = element.parentNode;
        //salvo icona in variabile globale
        icon = element;
        //salvo td disponibilità in variabile globale
        disponibilityNode = bookToBorrow.previousElementSibling;

        url = "/ProgettoBiblioteca/Controller?action=prenota&id=" + id;
        req = initRequest();
        req.open('GET', url, true);
        req.onreadystatechange = callbackBorrow;
        req.send();
    }
}

//funzione di callback per la prenotazione di un libro
function callbackBorrow() {
    var iconDisable;
    var newDisponibility;
    var operation = document.getElementById("operation");
    var message = operation.firstChild;
    
    /* invia un messaggio all'utente con l'esito dell'operazione
     * in caso di successo, aggiorna il catalogo a video
    */
    if (req.readyState === 4 && req.status === 200) {

        var msg = req.responseText;

        if (msg === 'S') {
            //inserisci messaggio "operazione effettuata con successo"
            message.innerHTML = "Operazione effettuata con successo";
            message.className = "success";
            
            //elimino icona abilitata
            bookToBorrow.removeChild(icon);
            //creo icona disable
            iconDisable = document.createElement("span");
            iconDisable.className = "glyphicon glyphicon-ok disable";
            //inserisco icona disable
            bookToBorrow.appendChild(iconDisable);
            //decremento disponibilità e la sostituisco
            newDisponibility = disponibilityNode.innerHTML - 1;
            disponibilityNode.innerHTML = newDisponibility;
        } else if (msg === 'E') {
            //prendi id operazione per inserire messaggio
            message.innerHTML = "Errore nel completamento dell'operazione";
            message.className = "error";
            
        }

    }
}

//funzione per la rimozione di un libro tramite ajax
function removeBook(element, id, title) {

    //chiedo all'utente se è sicuro di voler rimuovere il libro
    if (confirm("Sei sicuro di voler rimuovere il libro " + title + "?") === true) {

        bodyBookTable = document.getElementById("bodyBookTable");
        bookToRemove = element.parentElement.parentElement; //riga del libro da rimuovere

        url = "/ProgettoBiblioteca/Controller?action=rimuovi&id=" + id; //url della servlet con i dati inseriti dall'utente
        req = initRequest(); //inizializza l'oggetto request
        req.open('GET', url, true); //richiesta ajax asincrona
        req.onreadystatechange = callbackRemove; //funzione di callback
        req.send(); //invia la richiesta
    }
}

//funzione di callback per la rimozione di un libro
function callbackRemove() {
    /* 
     * invia un messaggio all'utente con l'esito dell'operazione
     * in caso di successo, aggiorna il catalogo a video
     */
    if (req.readyState === 4 && req.status === 200) {

        var msg = req.responseText;

        if (msg === 'S') {
            alert("Operazione effettuata con successo");
            bodyBookTable.removeChild(bookToRemove);
        } else if (msg === 'P') {
            alert("Il libro è attualmente in prestito");
        } else if (msg === 'E') {
            alert("Errore nella rimozione del libro");
        }
    }
}

//funzione che aggiunge un campo autore
function addAuthorField() {
    
    var maxAuthors = 6; //numero massimo di campi autore

    if (numAuthors < maxAuthors) {

        var authorsList = document.getElementById("authorsList"); //div della lista di autori
        numAuthors++; //incrementa il numero dei campi autore

        //div authorGroup
        var author = document.createElement("div");
        author.className = "form-group";
        author.id = "authorGroup" + numAuthors;

        //div campo autore
        var authorDiv = document.createElement("div");
        authorDiv.className = "col-md-offset-2 col-md-9";

        //input campo autore
        var authorField = document.createElement("input");
        authorField.id = "author" + numAuthors;
        authorField.type = "text";
        authorField.className = "form-control";
        authorField.name = "author" + numAuthors;

        authorDiv.appendChild(authorField); //aggiungi l'input al div

        //div bottone rimuovi
        var removeDiv = document.createElement("div");
        removeDiv.className = "col-md-1";

        //icon bottone rimuovi
        var removeBtn = document.createElement("i");
        removeBtn.className = "btn glyphicon glyphicon-minus";
        removeBtn.onclick = function () {
            removeAuthorField(numAuthors);
        };

        removeDiv.appendChild(removeBtn); //aggiungi il bottone al div

        author.appendChild(authorDiv); //aggiungi il campo autore al group
        author.appendChild(removeDiv); //aggiungi il bottone rimuovi al group

        authorsList.appendChild(author); //aggiungi il group alla lista dei campi autore
    }
}

//funzione che rimuove un campo autore
function removeAuthorField(i) {
    $('div').remove('#authorGroup' + i);
    numAuthors--;
}

//funzione che crea popup per la restituzione del libro
function confirmReturn(title) {
    return confirm("Sei sicuro di voler restituire il libro " + title + "?");
}

//funzione di ricerca in tabella per prima colonna
function searchIntoTable(idTable) {
    var input, filter, table, tr, td, i;
    input = document.getElementById("toSearch");
    filter = input.value.toUpperCase(); //case insensitive
    table = document.getElementById(idTable);
    tr = table.getElementsByTagName("tr");
    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}
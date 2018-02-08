/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BiblioController;

import dao.Libro;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Pc_User
 */
public class RicercaLibro extends HttpServlet {
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	
	ServletContext ctx = getServletContext(); //contesto della Servlet
	HttpSession s = request.getSession(); //sessione utente
	
	ArrayList<Libro> catalogo = (ArrayList<Libro>) ctx.getAttribute("catalogo");
	String bookSearched = request.getParameter("book"); //stringa digitata dall'utente
	StringBuilder sb = new StringBuilder();
	
	if(bookSearched != null) {
	    bookSearched = bookSearched.trim().toLowerCase();
	}
	else {
	    //se non è arrivato nulla, vai alla pagina di errore
	    ctx.getRequestDispatcher("/error.html").forward(request, response);
	}

	boolean bookFounded = false;

	/* tre casi:
	    - stringa vuota -> stampa tutta la tabella
	    - almeno una corrispondenza -> filtra la tabella per titolo
	    - nessuna corrispondenza -> una sola riga con scritto "nessun suggerimento"
	*/

	//stringa vuota -> invia tutta la tabella
	if(bookSearched.equals("")) {

	    //invia tutta la tabella
	    for(Libro l: catalogo) {
		sb.append("<book>");
		sb.append("<id>").append(l.getId()).append("</id>");
		sb.append("<titolo>").append(l.getTitolo()).append("</titolo>");
		sb.append("<autori>").append(l.getStringAutori()).append("</autori>");
		sb.append("<editore>").append(l.getEditore()).append("</editore>");
		sb.append("<disponibili>").append(l.getDisponibili()).append("</disponibili>");

		//utente non loggato
		if(s.getAttribute("ruolo") == null) {
		    sb.append("<ultima>O</ultima>");
		}
		//se è un amministratore
		else if(s.getAttribute("ruolo").equals("Amministratore")){
		    sb.append("<ultima>A</ultima>");
		}
		//se è un cliente e non ci sono libri disponibili (non prenotabile)
		else if(s.getAttribute("ruolo").equals("Cliente") && l.getDisponibili() == 0){
		    sb.append("<ultima>NP</ultima>");
		}
		//se è un cliente e c'è almeno un libro disponibile
		else if(s.getAttribute("ruolo").equals("Cliente") && l.getDisponibili() > 0){
		    //se è attualmente in prestito (non prenotabile)
		    if(isBorrowed(l.getId(), (ArrayList<Integer>)s.getAttribute("prestitiAttivi"))){
			sb.append("<ultima>NP</ultima>");
		    }
		    //se non è in prestito (prenotabile
		    else{
			sb.append("<ultima>P</ultima>");
		    }
		}

		sb.append("</book>");
	    }

	    bookFounded = true;

	}
	else {

	    //scorri il catalogo
	    for(Libro l: catalogo) {

		//filtra per titolo
		if(l.getTitolo().trim().toLowerCase().indexOf(bookSearched) > -1) {
		    sb.append("<book>");
		    sb.append("<id>").append(l.getId()).append("</id>");
		    sb.append("<titolo>").append(l.getTitolo()).append("</titolo>");
		    sb.append("<autori>").append(l.getStringAutori()).append("</autori>");
		    sb.append("<editore>").append(l.getEditore()).append("</editore>");
		    sb.append("<disponibili>").append(l.getDisponibili()).append("</disponibili>");

		    //utente non loggato
		    if(s.getAttribute("ruolo") == null) {
			sb.append("<ultima>O</ultima>");
		    }
		    //se è un amministratore
		    else if(s.getAttribute("ruolo").equals("Amministratore")){
			sb.append("<ultima>A</ultima>");
		    }
		    //se è un cliente e non ci sono libri disponibili (non prenotabile)
		    else if(s.getAttribute("ruolo").equals("Cliente") && l.getDisponibili() == 0){
			sb.append("<ultima>NP</ultima>");
		    }
		    //se è un cliente e c'è almeno un libro disponibile
		    else if(s.getAttribute("ruolo").equals("Cliente") && l.getDisponibili() > 0){
			//se è attualmente in prestito (non prenotabile)
			if(isBorrowed(l.getId(), (ArrayList<Integer>)s.getAttribute("prestitiAttivi"))){
			    sb.append("<ultima>NP</ultima>");
			}
			//se non è in prestito (prenotabile
			else{
			    sb.append("<ultima>P</ultima>");
			}
		    }

		    sb.append("</book>");

		    bookFounded = true;
		}
	    }

	}

	//se ho trovato almeno una corrispondenza, invia la risposta con il file XML
	if(bookFounded) {
	    response.setContentType("text/xml");
	    response.setHeader("Cache-Control", "no-cache");
	    response.getWriter().write("<books>" + sb.toString() + "</books>");
	}
	//altrimenti non c'è nulla da mostrare
	else {
	    //SC_NO_CONTENT = send code 204
	    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}
	
    }
    
    private static boolean isBorrowed(int libro, ArrayList<Integer> prestiti){
	
	for(int l: prestiti){
	    if(libro == l)
		return true;
	}
	
	return false;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>

}

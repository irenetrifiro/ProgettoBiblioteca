package BiblioController;

import dao.Libro;
import dao.Model;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InserisciLibro extends HttpServlet {

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
	
	ServletContext ctx = getServletContext(); //contesto dell'applicazione
	ArrayList<Libro> catalogo = (ArrayList) ctx.getAttribute("catalogo"); //catalogo dei libri
	
	/*campi del form (alcuni campi autore possono essere null) */
	String title = request.getParameter("title");
	String editor = request.getParameter("editor");
	String available = request.getParameter("available");
	ArrayList<String> authors = new ArrayList();
	authors.add(request.getParameter("author1"));
	authors.add(request.getParameter("author2"));
	authors.add(request.getParameter("author3"));
	authors.add(request.getParameter("author4"));
	authors.add(request.getParameter("author5"));
	authors.add(request.getParameter("author6"));
	
	/* verifico che i campi non siano vuoti */
	if(title.equals("") || editor.equals("") || available.equals("") || !authorExists(authors)){
	    /* passo a parametro i campi del form */
	    request.setAttribute("title", title);
	    request.setAttribute("editor", editor);
	    request.setAttribute("available", available);
	    
	    request.setAttribute("errorMsg", "Tutti i campi sono obbligatori"); //manda un messaggio di errore all'utente
	    ctx.getRequestDispatcher("/inserisciLibro.jsp").forward(request, response); //torna alla pagina inserisciLibro
	}
	//tutti i campi compilati
	else {
	   
	    //crea l'oggetto libro
	    Libro l = new Libro(title, editor, Integer.parseInt(available), authors);

	    //verifico che non vi sia un altro libro con lo stesso titolo e editore nel db
	    if(Model.getLibro(title, editor) != 0){
		/* passo a parametro i campi del form */
		request.setAttribute("title", title);
		request.setAttribute("editor", editor);
		request.setAttribute("available", available);
		
		request.setAttribute("errorMsg", "Libro già esistente nel db"); //manda un messaggio di errore all'utente
		ctx.getRequestDispatcher("/inserisciLibro.jsp").forward(request, response); //torna alla pagina inserisciLibro
	    }
	    //non ci sono altri libri nel db con gli stessi dati
	    else {
		//inserisco il libro nel db
		int id = Model.inserisciLibro(l);

		//se ci sono stati errori nell'inserimento del libro
		if(id == 0){
		    /* passo a parametro i campi del form */
		    request.setAttribute("title", title);
		    request.setAttribute("editor", editor);
		    request.setAttribute("available", available);

		    request.setAttribute("errorMsg", "Errore nell'inserimento dei dati"); //manda un messaggio di errore all'utente
		    ctx.getRequestDispatcher("/inserisciLibro.jsp").forward(request, response); //torna alla pagina inserisciLibro
		}
		//nessun errore nell'inserimento del libro
		else {
		    
		    //sovrascrivo l'oggetto inserendo un nuovo libro
		    l = new Libro(id, l.getTitolo(), l.getEditore(), l.getDisponibili(), l.getAutori()); 
		    
		    authors.removeAll(Arrays.asList("", null)); //elimino tutte le stringhe vuote o nulle
                    
		    boolean error = false; //assumo che non ci siano errori

		    //inserisco gli autori nel db
		    for(String a : authors){
			//se ci sono stati problemi nell'inserimento dell'autore
			if(!(Model.inserisciAutore(id, a))){
			    error = true; //notifico l'errore
			    
			    /* passo a parametro i campi del form */
			    request.setAttribute("title", title);
			    request.setAttribute("editor", editor);
			    request.setAttribute("available", available);

			    request.setAttribute("errorMsg", "Errore nell'inserimento dei dati"); //manda un messaggio di errore all'utente
			    ctx.getRequestDispatcher("/inserisciLibro.jsp").forward(request, response); //torna alla pagina inserisciLibro
			}
		    }
		    //nessun errore nell'inserimento degli autori
		    if(!error) {
			catalogo.add(l); //aggiungo il libro al catalogo
			ctx.setAttribute("catalogo", catalogo); //aggiorno il catalogo

			ctx.getRequestDispatcher("/gestioneCatalogo.jsp").forward(request, response); //torna alla pagina di gestione catalogo
		    }
		}
	    }
	}
    }
    
    /**
     * verifica che almeno un elemento dell'array list non sia una stringa vuota
     * @param authors
     * @return 
     */
    public static boolean authorExists(ArrayList<String> authors){
	
	for(String a : authors){
	    if(!(a.equals("")))
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

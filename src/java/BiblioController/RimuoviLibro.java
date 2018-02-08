package BiblioController;

import dao.Libro;
import dao.Model;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RimuoviLibro extends HttpServlet {

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

	ServletContext ctx = getServletContext(); //contesto della servlet
	ArrayList<Libro> catalogo = (ArrayList) ctx.getAttribute("catalogo"); //recupero il catalogo dei libri
	int id = Integer.parseInt(request.getParameter("id")); //id del libro da rimuovere
	String infoMsg; //messaggio da mostrare all'utente
	
	//se il libro è in prestito
	if(Model.isBorrowed(id)) {
	    infoMsg = "P"; //Il libro è attualmente in prestito
	}
	//altrimenti tenta di rimuovere il libro
	else if(Model.rimuoviLibro(id)) {
	    
	    infoMsg = "S"; //Operazione effettuata con successo

	    Libro toRemove = null; //libro da rimuovere

	    //cerco il libro con quell'id
	    toRemove = getLibro(id, catalogo);
            
	    catalogo.remove(toRemove); //rimozione del libro dal catalogo
	    ctx.setAttribute("catalogo", catalogo); //aggiorno il catalogo
	    
	}
	else {
	    infoMsg = "E"; //Errore nella rimozione del libro
	}
	
	response.setContentType("text");
	response.setHeader("Cache-Control", "no-cache");
	response.getWriter().write(infoMsg);

    }
    
    public static Libro getLibro(int id, ArrayList<Libro> catalogo){
        for(Libro l: catalogo){
            if(l.getId() == id)
                return l;
        }
        return null;
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

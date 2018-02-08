package BiblioController;

import dao.Model;
import dao.Utente;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Login extends HttpServlet {

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
	
	/* campi del form */
	String user = request.getParameter("user");
	String pwd = request.getParameter("pwd");

	//se i campi del form sono vuoti
	if(user.equals("") || pwd.equals("")){
	    request.setAttribute("errorMsg", "Credenziali non valide!"); //imposta un messaggio di errore
	    ctx.getRequestDispatcher("/login.jsp").forward(request, response); //torna alla pagina di login
	}
	// se sono arrivati dati dal form
	else {
	    Utente u = Model.getUtente(user, pwd);

	    //se esiste un utente con queste credenziali
	    if(u != null){
		HttpSession s = request.getSession(); //creo la sessione
		
		//salva i dati dell'utente in sessione
		s.setAttribute("username", u.getUsername());
		s.setAttribute("ruolo", u.getRuolo());
		s.setAttribute("prestitiAttivi", u.getPrestitiAttivi());

		ctx.getRequestDispatcher("/catalogo.jsp").forward(request, response); //vai al catalogo
	    }
	    //altrimenti
	    else{
		request.setAttribute("user", user);
		request.setAttribute("errorMsg", "Credenziali non valide!"); //imposta un messaggio di errore
		ctx.getRequestDispatcher("/login.jsp").forward(request, response); //torna alla pagina di login
	    }
	}
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

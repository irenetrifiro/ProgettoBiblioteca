package BiblioController;
import dao.Libro;
import dao.Model;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Controller extends HttpServlet {
    
    @Override
    public void init(ServletConfig conf) throws ServletException {
	
	super.init(conf); //metodo della superclasse
	
	Model.registerDriver(); //registrazione del driver per la comunicazione con il DB
	ArrayList<Libro> catalogo = Model.getCatalogo(); //recupero il catalogo dal db
	
	getServletContext().setAttribute("catalogo", catalogo); //salva il catalogo nel contesto dell'applicazione
	
    }
    
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
	String action = request.getParameter("action"); //parametro action
	HttpSession s = request.getSession(); //se la sessione non esiste la crea
	
	/* ### AVVIO DELL'APPLICAZIONE ### */
	if(action == null){
	    ctx.getRequestDispatcher("/index.jsp").forward(request, response); //vai alla index
	}
	
	/* ### HOME ### */
	else if(action.equals("home")){
	    ctx.getRequestDispatcher("/index.jsp").forward(request, response); //vai alla index
	}
	
	/* ### LOGIN ### */
	else if(action.equals("login")&& !isLogged(s)){
	    ctx.getNamedDispatcher("Login").forward(request, response);
	}
	
	/* ### LOGOUT ### */
	else if(action.equals("logout") && isLogged(s)){
	    s.invalidate(); //distrugge la sessione
	    ctx.getRequestDispatcher("/index.jsp").forward(request, response); //vai alla index
	}
	
	/* ### CATALOGO ### */
	else if(action.equals("catalogo")){
	    ctx.getRequestDispatcher("/catalogo.jsp").forward(request, response); //vai alla pagina del catalogo
	}
	
	/* ### GESTIONE CATALOGO (SOLO ADMIN) ### */
	else if(action.equals("gestioneCatalogo") && isAdmin(s)){
	    ctx.getRequestDispatcher("/gestioneCatalogo.jsp").forward(request, response); //vai alla pagina del catalogo
	}
	
	/* ### INSERISCI LIBRO (SOLO ADMIN) ### */
	else if(action.equals("inserisci") && isAdmin(s)){
	    ctx.getNamedDispatcher("InserisciLibro").forward(request, response);
	}
	
	/* ### RIMUOVI LIBRO (SOLO ADMIN) ### */
	else if(action.equals("rimuovi") && isAdmin(s)){
	    ctx.getNamedDispatcher("RimuoviLibro").forward(request, response);
	}
	
        /* ### STORICO PRESTITI UTENTE ### */
	else if(action.equals("storico") && isLogged(s)){
	    ctx.getNamedDispatcher("StoricoPrestiti").forward(request, response);
	}
        
        /* ### RESTITUISCI PRESTITO ### */
        
        //per generare la pagina di restituzione
	else if(action.equals("goRestituisciPrestito") && isLogged(s)){
            ctx.getNamedDispatcher("goRestituisciPrestito").forward(request, response);
        }
        //per restituire
	else if(action.equals("restituisci") && isLogged(s)){
	    ctx.getNamedDispatcher("Restituisci").forward(request,response);
	}
        
        /* ### STORICO PRESTITI CUMULATIVO (SOLO ADMIN) ### */
	else if(action.equals("prestitiClienti") && isAdmin(s)){
	    ctx.getNamedDispatcher("PrestitiClientiAdmin").forward(request, response);
	}
	
	/* ### EFFETTUA PRENOTAZIONE ### */
	else if(action.equals("prenota") && isLogged(s)){
            ctx.getNamedDispatcher("Prenota").forward(request, response);
	}
	
        /* ### PAGINA CONTATTI ### */
        else if(action.equals("contatti")){
            ctx.getRequestDispatcher("/contatti.jsp").forward(request, response);
        }
	
	/* ### AZIONI NON GESTITE -> ERRORE! ### */
	else {
	    ctx.getRequestDispatcher("/error.html").forward(request, response); //vai alla pagina di errore
	}
	
    }
    
    /**
     * Verifica se un utente è loggato
     * @param s session
     * @return true se è loggato, false altrimenti
     */
    public static boolean isLogged(HttpSession s){
        
        //username dell'utente
        String username = (String) s.getAttribute("username");
        
        if(username == null || username.equals(""))
            return false;
        else
            return true;
    }
    
    /**
     * Verifica se l'utente in questione è un amministratore
     * @param s sessione
     * @return true se è amministratore, false altrimenti
     */
    public static boolean isAdmin(HttpSession s){
        String ruolo = (String) s.getAttribute("ruolo");
        return(ruolo.equals("Amministratore"));
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

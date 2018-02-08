package BiblioController;

import dao.Libro;
import dao.Model;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Restituisci extends HttpServlet {

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
        HttpSession s = request.getSession();     //sessione
        
        ArrayList<Libro> catalogo = (ArrayList<Libro>) ctx.getAttribute("catalogo"); //recupero catalogo dei libri
        ArrayList<Integer> IDprestitiAttivi = (ArrayList<Integer>) s.getAttribute("prestitiAttivi"); //recupero gli id prestitiAttivi utente
        String username = (String) s.getAttribute("username"); //recupero username utente
        
        int id = Integer.parseInt(request.getParameter("id")); //id libro da restituire
        
        String infoMsg;
        Libro l;
        
        if(Model.returnBook(id, username)){
            
            infoMsg="Operazione effettuata con successo";
            
            l = getLibro(id, catalogo); //libro con quell'id
            l.setDisponibili(l.getDisponibili()+1); //aumento la disponibilità
            
            IDprestitiAttivi.remove(new Integer(id));
            
            s.setAttribute("prestitiAttivi", IDprestitiAttivi);
            ctx.setAttribute("catalogo", catalogo);
            
            request.setAttribute("succesMsg", infoMsg);
            
        }else{
            infoMsg="Errore nella restituzione del libro";
            
            request.setAttribute("errorMsg", infoMsg);
        }        
        
        RequestDispatcher rd = ctx.getNamedDispatcher("goRestituisciPrestito");
        rd.forward(request, response);   
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

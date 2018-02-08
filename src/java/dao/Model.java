package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Model {
    
    private static final String URL = "jdbc:derby://localhost:1527/BiblioDB";
    private static final String USR = "book";
    private static final String PWD = "book";
    
    /**
     * Registra il driver per la comunicazione con il DB
     */
    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Recupera tutti i dati dei libri presenti nella biblioteca
     * @return catalogo dei libri
     */
    public static ArrayList<Libro> getCatalogo() {
	
	ArrayList<Libro> catalogo = new ArrayList();
	ArrayList<String> autori = new ArrayList();
	int id = 0;
	
	try {
	    
	    Connection conn = DriverManager.getConnection(URL, USR, PWD);
	    Statement st = conn.createStatement();
	    //seleziono tutti i libri con i relativi autori e ordino la tabella risultante per id in ordine crescente
	    ResultSet r = st.executeQuery("SELECT LIBRI.ID, LIBRI.TITOLO, LIBRI.EDITORE, LIBRI.DISPONIBILI, AUTORILIBRI.AUTORE " 
					+ "FROM AUTORILIBRI, LIBRI WHERE AUTORILIBRI.LIBRO = LIBRI.ID ORDER BY LIBRI.ID ASC");
	    
	    //scorro il risultato della query
	    while(r.next()) {
		
		if(r.getInt("ID") != id) {
		    
		    id = r.getInt("ID"); //aggiorna l'id
		    autori = new ArrayList(); //crea una nuova lista di autori
		    autori.add(r.getString("AUTORE")); //inserisci l'autore
		    
		    Libro l = new Libro(id, r.getString("TITOLO"), r.getString("EDITORE"), r.getInt("DISPONIBILI"), autori); //crea un nuovo libro
		    
		    catalogo.add(l); //inserisci il nuovo libro nel catalogo
		    
		}
		else {
		    
		    autori.add(r.getString("AUTORE")); //inserisci l'autore
		    
		    Libro l = new Libro(id, r.getString("TITOLO"), r.getString("EDITORE"), r.getInt("DISPONIBILI"), autori); //crea un nuovo libro
		    
		    catalogo.set(catalogo.size()-1, l); //modifica l'ultimo libro inserito
		}
	    }
	    
	    r.close(); //chiudo il result
	    st.close(); //chiudo lo statement
	    conn.close(); //chiudo la connessione
	    
	} catch(SQLException e){
	    System.out.println(e.getMessage());
	    return null;
	}
	
	return catalogo;
	
    }
    
    /**
     * Verifica se esiste un utente con le credenziali passate a parametro
     * @param username
     * @param pwd
     * @return dati dell'utente se esiste, null altrimenti
     */
    public static Utente getUtente(String username, String pwd){
	
	Utente u = null;
		
	try {
	    Connection conn = DriverManager.getConnection(URL, USR, PWD);
	    //richiede tutti gli utenti con username e password passati a parametro
	    PreparedStatement ps = conn.prepareStatement("SELECT * FROM UTENTI " 
							+ "WHERE ACCOUNT = ? AND PASSWORD = ?");
	    
	    //setta i parametri della query
	    ps.setString(1, username);
	    ps.setString(2, pwd);
	    
	    ResultSet r = ps.executeQuery(); //eseguo la query
	    
	    //se esiste un utente con queste credenziali
	    if(r.next()){
		
		//creo l'utente
		u = new Utente(r.getString("ACCOUNT"), r.getString("RUOLO"));
		
		ArrayList<Integer>prestitiAttivi = new ArrayList(); //array per i prestiti attivi
		
		//recupera la lista dei prestiti attivi dell'utente (data restituzione = NULL))
		ps = conn.prepareStatement("SELECT LIBRO FROM PRESTITI " 
					+ "WHERE UTENTE = ? AND D_RESTITUZIONE IS NULL");
		
		ps.setString(1, username); //setto il parametro della query
		
		r = ps.executeQuery(); //eseguo la query
		
		//inserisco nella lista gli id dei libri attualmente in prestito
		while(r.next()){
		    prestitiAttivi.add(r.getInt("LIBRO"));
		}
		
		u.setPrestitiAttivi(prestitiAttivi); //setto l'array dei prestiti attivi
		
	    }
	    
	    r.close(); //chiudo il result
	    ps.close(); //chiudo lo statement
	    conn.close(); //chiudo la connessione
	    
	} catch(SQLException e){
	    System.out.println(e.getMessage());
	    return null;
	}
        
	return u;
	
    }
    
    /**
     * Verifica se il libro passato a parametro è attualmente in prestito
     * @param id del libro
     * @return true se è in prestito, false altrimenti
     */
    public static boolean isBorrowed(int id) {
	
	boolean borrowed = false; //flag
	
	try {
	    
	    Connection conn = DriverManager.getConnection(URL, USR, PWD);
	    //trova tutti i prestiti relativi a quel libro
	    PreparedStatement ps = conn.prepareStatement("SELECT * FROM PRESTITI " 
							+ "WHERE LIBRO = ?");
	    
	    ps.setInt(1, id);
	    
	    ResultSet r = ps.executeQuery();
	    
	    borrowed = r.next(); //se c'è almeno un elemento, r.next() sarà true
	    
	    r.close(); //chiudo il result
	    ps.close(); //chiudo lo statement
	    conn.close(); //chiudo la connessione
	    
	} catch(SQLException e){
	    System.out.println(e.getMessage());
	    return false;
	}
	
	return borrowed;
    }
    
    /**
     * Rimuove i dati di un libro dal db
     * @param id del libro
     * @return true se l'operazione è andata a buon fine
     */
    public static boolean rimuoviLibro(int id){
	
	boolean ok = false; //flag
	
	try {
	    
	    Connection conn = DriverManager.getConnection(URL, USR, PWD);
	    //elimino il libro dalla tabella degli autori
	    PreparedStatement ps = conn.prepareStatement("DELETE FROM AUTORILIBRI WHERE LIBRO = ?");
	    
	    ps.setInt(1, id); //setto i parametri
	    
	    ps.executeUpdate(); //eseguo la query
	    
	    //elimino il libro dalla tabella dei libri
	    ps = conn.prepareStatement("DELETE FROM LIBRI WHERE ID = ?");
	    
	    ps.setInt(1, id); //setto i parametri
	    
	    ps.executeUpdate(); //eseguo la query
	    
	    ok = true; //rimozione avvenuta con successo
	    
	    ps.close(); //chiudo lo statement
	    conn.close(); //chiudo la connessione
	    
	} catch(SQLException e){
	    System.out.println(e.getMessage());
	    return false;
	}
	
	return ok;
    }
    
    /**
     * Inserisce un libro nella tabella LIBRI
     * @param l dati del libro da inserire
     * @return l'id del libro inserito
     */
    public static int inserisciLibro(Libro l){
	
	int id = 0; //flag
	
	try {
	    
	    Connection conn = DriverManager.getConnection(URL, USR, PWD);
	    //inserisco i dati del libro nella tabella libri
	    PreparedStatement ps = conn.prepareStatement("INSERT INTO LIBRI(TITOLO, EDITORE, DISPONIBILI) VALUES(?, ?, ?)");
	    
	    //setto i parametri
	    ps.setString(1, l.getTitolo());
	    ps.setString(2, l.getEditore());
	    ps.setInt(3, l.getDisponibili());
	    
	    //eseguo la query
	    if(ps.executeUpdate() > 0){
		
		//se è andata a buon fine (numero di colonne coinvolte > 0)
		//cerco tutti i libri con i dati inseriti prima e li ordino per id decrescente
		ps = conn.prepareStatement("SELECT * FROM LIBRI " 
					+ "WHERE TITOLO = ? AND EDITORE = ? " 
					+ "ORDER BY ID DESC");
		
		//setto i parametri
		ps.setString(1, l.getTitolo());
		ps.setString(2, l.getEditore());
		
		ResultSet r = ps.executeQuery(); //eseguo la query

		//il primo libro del risultato è l'ultimo inserito
		if(r.next()){
		    id = r.getInt("ID");
		}
		
		r.close(); //chiudo il result
	    }
	    
	    ps.close(); //chiudo lo statement
	    conn.close(); //chiudo la connessione
	    
	} catch(SQLException e){
	    System.out.println(e.getMessage());
	    return 0;
	}
	
	return id;
    }
    
    /**
     * Inserisce un autore nella tabella AUTORILIBRI
     * @param id del libro
     * @param autore del libro
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    public static boolean inserisciAutore(int id, String autore){
	
	try {
	    
	    Connection conn = DriverManager.getConnection(URL, USR, PWD);
	    //inserisco i dati dell'autore nella tabella AUTORI
	    PreparedStatement ps = conn.prepareStatement("INSERT INTO AUTORILIBRI VALUES(?, ?)");
	    
	    //setto i parametri
	    ps.setInt(1, id);
	    ps.setString(2, autore);
	    
	    //eseguo la query
	    ps.executeUpdate();
	    
	    ps.close(); //chiudo lo statement
	    conn.close(); //chiudo la connessione
	    
	} catch(SQLException e){
	    System.out.println(e.getMessage());
	    return false;
	}
	
	return true;
    }
    
    /**
     * Restituisce il libro con titolo e editore indicati
     * @param titolo del libro
     * @param editore del libro
     * @return id del libro cercato, 0 se non esiste
     */
    public static int getLibro(String titolo, String editore){
	
	int id = 0;
	
	try {
	    
	    Connection conn = DriverManager.getConnection(URL, USR, PWD);
	    
	    PreparedStatement ps = conn.prepareStatement("SELECT * FROM LIBRI " 
							+ "WHERE TITOLO = ? AND EDITORE = ?");
	    
	    //setto i parametri
	    ps.setString(1, titolo);
	    ps.setString(2, editore);
	    
	    ResultSet r = ps.executeQuery(); //eseguo la query

	    //se ho trovato un libro
	    if(r.next()){
		id = r.getInt("ID"); //salvo l'id
	    }

	    r.close(); //chiudo il result	    
	    ps.close(); //chiudo lo statement
	    conn.close(); //chiudo la connessione
	    
	}catch(SQLException e){
	    System.out.println(e.getMessage());
	    return 0;
	}
	
	return id;
    }
    
    /**
     * aggiorna diminuendo di una unità il libro nel catalogo
     * inserisce il prestito in tabella prestiti
     * @param id del libro
     * @param username dell'utente
     * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    public static boolean borrowBook(int id, String username){
       
        try{
           
           Connection conn = DriverManager.getConnection(URL,USR,PWD);
           
           //query per aggiornare disponibilità libro
           PreparedStatement ps = conn.prepareStatement("UPDATE LIBRI SET DISPONIBILI=DISPONIBILI-1 "
                                                            + "WHERE ID = ?");
           
           //setto i parametri
           ps.setInt(1, id);
           
           //eseguo la query
           ps.executeUpdate();
           
           //query per inserire prestito in tabella prestiti
           ps = conn.prepareStatement("INSERT INTO PRESTITI(LIBRO, UTENTE, D_PRESTITO) VALUES (?, ?, ?)");
           
           //setto i parametri
           ps.setInt(1,id);
           ps.setString(2, username);
           ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
           
           //eseguo la query
           ps.executeUpdate();
           
           ps.close(); //chiudo lo statement
           conn.close(); //chiudo la connessione
           
       }catch(SQLException e){
           System.out.println(e.getMessage());
           return false;
       }
       
       return true; 
   }
    
    /**
      * aggiorna data restituzione prestito
      * aggiorna aumentando di una unità il libro nel catalogo
      * @param id del libro
      * @param username dell'utente
      * @return true se l'operazione è andata a buon fine, false altrimenti
     */
    public static boolean returnBook(int id, String username){
       try {
	    
	    Connection conn = DriverManager.getConnection(URL, USR, PWD);
	    
	    //query per inserire data di restituzione del libro
	    PreparedStatement ps = conn.prepareStatement("UPDATE PRESTITI SET D_RESTITUZIONE = ? "
                                                            + "WHERE LIBRO = ? AND UTENTE = ? AND D_RESTITUZIONE is NULL");
	    
            //setto i parametri
	    ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            ps.setInt(2, id);
            ps.setString(3, username);
	    
            //eseguo la query
	    ps.executeUpdate();
	    
	    //query per aggiornare disponibilità libro
	    ps = conn.prepareStatement("UPDATE LIBRI SET DISPONIBILI=DISPONIBILI+1  "
                                            + "WHERE ID = ?");
	    
            //setto parametro
	    ps.setInt(1, id);
	    
            //eseguo la query
	    ps.executeUpdate();
	    
	    ps.close(); //chiudo lo statement
	    conn.close(); //chiudo la connessione
	    
	}catch(SQLException e){
	    System.out.println(e.getMessage());
            return false;
	}
       return true;
   } 
   
    /**
      * Recupera tutti i libri da restituire di un utente
      * @param catalogo
      * @param username
      * @return catalogo dei libri
     */
    public static ArrayList<Prestito> getPrestitiAttivi(ArrayList<Libro> catalogo, String username){
        
        ArrayList<Prestito> prestitiAttivi=new ArrayList();
        
        try {
	    
	    Connection conn = DriverManager.getConnection(URL, USR, PWD);
	    
            //query per recuperare tutti i prestiti attivi dell'utente
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM PRESTITI WHERE UTENTE = ? AND D_RESTITUZIONE IS NULL");
	    
            //setto il parametro
            ps.setString(1, username);
            
	    ResultSet r = ps.executeQuery();
            
            Libro libro=null;
            
            //scorro il risultato della query
	    while(r.next()) {
                int id = r.getInt("LIBRO");
		
                //scorro il catalogo per trovare il libro associato all'id
                libro = searchBook(id, catalogo);
                
                //creo prestito
		Prestito p = new Prestito(r.getInt("ID"),r.getString("UTENTE"), libro, r.getDate("D_PRESTITO"), r.getDate("D_RESTITUZIONE"));
		
                //aggiungo il prestito all'arraylist    
		prestitiAttivi.add(p);		    
	    }
	    
	    r.close(); //chiudo il result
	    ps.close();//chiudo lo statement
	    conn.close(); //chiudo la connessione
	    
	}catch(SQLException e){
	    System.out.println(e.getMessage());
            return null;
	}
        
        return prestitiAttivi;
    }
    
    /**
     * Recupera lo storico prestiti di un utente
     * @param catalogo
     * @param username
     * @return prestiti
   */
    public static ArrayList<Prestito> getStoricoPrestiti(ArrayList<Libro> catalogo, String username){
        ArrayList<Prestito> prestiti=new ArrayList();
        
        try {
	    
	    Connection conn = DriverManager.getConnection(URL, USR, PWD);
	    
            //query per recuperare tutti i prestiti dell'utente
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM PRESTITI "
                                                            + "WHERE UTENTE = ? ORDER BY D_RESTITUZIONE DESC" );
	    
            //setto parametro
            ps.setString(1, username);
            
	    ResultSet r = ps.executeQuery();
            
            Libro libro=null;
            
            //scorro il risultato della query
	    while(r.next()) {
                int id = r.getInt("LIBRO");
                
                //scorro il catalogo per trovare il libro associato all'id
                libro = searchBook(id, catalogo);
                
                //creo prestito
		Prestito p = new Prestito(r.getInt("ID"),r.getString("UTENTE"), libro, r.getDate("D_PRESTITO"), r.getDate("D_RESTITUZIONE")); 		    
                //aggiungo all'arraylist il prestito
                prestiti.add(p);     
	    }
	    
	    r.close(); //chiudo il result
	    ps.close();//chiudo lo statement
	    conn.close(); //chiudo la connessione
	    
	}catch(SQLException e){
	    System.out.println(e.getMessage());
            return null;
	}
        
        return prestiti;
    }
    
    /**
     * Recupera tutti i prestiti per l'amministratore del sito
     * @param catalogo
     * @return prestiti
   */
    public static ArrayList<Prestito> getPrestitiAdmin(ArrayList<Libro> catalogo) {
	
        ArrayList<Prestito> prestiti=new ArrayList();
	
	try {
	    
	    Connection conn = DriverManager.getConnection(URL, USR, PWD);
	    Statement st = conn.createStatement();
	    
            //seleziono tutti i prestiti
	    ResultSet r = st.executeQuery("SELECT * FROM PRESTITI ORDER BY D_RESTITUZIONE DESC");
	    
            Libro libro=null;
            //scorro il risultato della query
	    while(r.next()) {
                int id = r.getInt("LIBRO");
		
                //scorro il catalogo per trovare il libro associato all'id
                libro = searchBook(id, catalogo);
                    
		//creo prestito
                Prestito p = new Prestito(r.getInt("ID"),r.getString("UTENTE"), libro, r.getDate("D_PRESTITO"), r.getDate("D_RESTITUZIONE"));
		
                //aggiungo prestito all'arraylist
		prestiti.add(p); //aggiungo prestito
		    
	    }
	    
	    r.close(); //chiudo il result
	    st.close(); //chiudo lo statement
	    conn.close(); //chiudo la connessione
	    
	}catch(SQLException e){
	    System.out.println(e.getMessage());
            return null;
	}
	
	return prestiti;	
    }
   
    /**
     * Cerca un libro per id nel catalogo
     * @param id
     * @param catalogo
     * @return oggetto Libro
     */
    public static Libro searchBook(int id, ArrayList<Libro> catalogo){
        for(Libro l: catalogo){
            if(l.getId() == id)
                return l;
        }
        return null;
    }
}

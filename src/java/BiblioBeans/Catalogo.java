/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BiblioBeans;

import dao.Libro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Pc_User
 */
public class Catalogo {
    
    private String urlDB;
    private String userDB;
    private String pwdDB;
    private ArrayList<Libro> catalogo; //lista dei libri con i relativi autori

    public Catalogo(String urlDB, String userDB, String pwdDB) {
	this.urlDB = urlDB;
	this.userDB = userDB;
	this.pwdDB = pwdDB;
	this.catalogo = new ArrayList();
    }
    
    public ArrayList<Libro> getCatalogo() {
	
	ArrayList<String> autori = new ArrayList();
	int id = 0;
	
	try {
	    
	    Connection conn = DriverManager.getConnection(urlDB, userDB, pwdDB);
	    Statement st = conn.createStatement();
	    //seleziono tutti i libri con i relativi autori e ordino la tabella risultante per id in ordine crescente
	    ResultSet r = st.executeQuery("SELECT LIBRI.ID, LIBRI.TITOLO, LIBRI.EDITORE, LIBRI.DISPONIBILI, AUTORILIBRI.AUTORE FROM AUTORILIBRI, LIBRI WHERE AUTORILIBRI.LIBRO = LIBRI.ID ORDER BY LIBRI.ID ASC");
	    
	    //scorro il risultato della query
	    while(r.next()) {
		
		if(r.getInt("ID") != id) {
		    
		    id = r.getInt("ID"); //aggiorna l'id
		    autori = new ArrayList(); //crea una nuova lista di autori
		    autori.add(r.getString("AUTORE")); //inserisci l'autore
		    
		    Libro l = new Libro(id, r.getString("TITOLO"), r.getString("EDITORE"), r.getInt("DISPONIBILI"), autori); //crea un nuovo libro
		    
		    this.catalogo.add(l); //inserisci il nuovo libro nel catalogo
		    
		}else{
		    
		    autori.add(r.getString("AUTORE")); //inserisci l'autore
		    
		    Libro l = new Libro(id, r.getString("TITOLO"), r.getString("EDITORE"), r.getInt("DISPONIBILI"), autori); //crea un nuovo libro
		    
		    this.catalogo.set(this.catalogo.size()-1, l); //modifica l'ultimo libro inserito
		}
	    }
	    
	    r.close(); //chiudo il result
	    st.close(); //chiudo lo statement
	    conn.close(); //chiudo la connessione
	    
	}catch(SQLException e){
	    System.out.println(e.getMessage());
	}
	
	return this.catalogo;
	
    }
    
}

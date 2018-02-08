/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BiblioBeans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Pc_User
 */
public class Login {
    
    private String urlDB;
    private String userDB;
    private String pwdDB;
    private String username;
    private String ruolo;
    //private int[] prestitiAttivi;

    public Login(String urlDB, String userDB, String pwdDB) {
	this.urlDB = urlDB;
	this.userDB = userDB;
	this.pwdDB = pwdDB;
    }
    
    public boolean userExists(String username, String pwd){
	
	boolean exists = false; //supponiamo che l'utente non esista
	
	try{
	    Connection conn = DriverManager.getConnection(urlDB, userDB, pwdDB);	    
	    PreparedStatement ps = conn.prepareStatement("SELECT * FROM UTENTI " 
							+ "WHERE ACCOUNT = ? AND PASSWORD = ?");
	    
	    //setta i parametri della query
	    ps.setString(1, username);
	    ps.setString(2, pwd);
	    
	    ResultSet r = ps.executeQuery(); //eseguo la query
	    
	    //se esiste un utente con queste credenziali
	    if(r.next()){
		this.username = r.getString("ACCOUNT");
		this.ruolo = r.getString("RUOLO");
		exists = true;
	    }
	    
	    r.close(); //chiudo il result
	    ps.close(); //chiudo lo statement
	    conn.close(); //chiudo la connessione
	    
	}catch(SQLException e){
	    System.out.println(e.getMessage());
	}
	
	return exists;
	
    }

    public String getUsername() {
	return username;
    }

    public String getRuolo() {
	return ruolo;
    }
    
}

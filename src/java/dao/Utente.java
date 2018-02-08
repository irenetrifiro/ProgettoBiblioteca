package dao;

import java.util.ArrayList;

public class Utente {  
    
    private final String username; //email dell'utente
    private String ruolo; //ruolo dell'utente
    private ArrayList<Integer> prestitiAttivi; //lista degli id dei libri in prestito

    public Utente(String username, String ruolo) {
	this.username = username;
	this.ruolo = ruolo;
	this.prestitiAttivi = new ArrayList();
    }

    public String getUsername() {
	return username;
    }

    public String getRuolo() {
	return ruolo;
    }

    public void setRuolo(String ruolo) {
	this.ruolo = ruolo;
    }
    
    public ArrayList<Integer> getPrestitiAttivi() {
	return prestitiAttivi;
    }

    public void setPrestitiAttivi(ArrayList<Integer> prestitiAttivi) {
	this.prestitiAttivi = prestitiAttivi;
    }
    
}

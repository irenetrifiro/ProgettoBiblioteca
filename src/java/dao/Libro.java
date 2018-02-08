package dao;

import java.util.ArrayList;

public class Libro {
    
    private final int id; //id del libro
    private String titolo; //titolo del libro
    private String editore; //editore del libro
    private int disponibili; //numero di copie disponibili
    private ArrayList<String> autori; //autori del libro
    
    public Libro(int id, String titolo, String editore, int disponibili, ArrayList<String> autori) {
	this.id = id;
	this.titolo = titolo;
	this.editore = editore;
	this.disponibili = disponibili;
	this.autori = autori;
    }

    public Libro(String titolo, String editore, int disponibili, ArrayList<String> autori) {
	this.id = 0;
	this.titolo = titolo;
	this.editore = editore;
	this.disponibili = disponibili;
	this.autori = autori;
    }

    public int getId() {
	return id;
    }
    
    public String getTitolo() {
	return titolo;
    }

    public void setTitolo(String titolo) {
	this.titolo = titolo;
    }

    public String getEditore() {
	return editore;
    }

    public void setEditore(String editore) {
	this.editore = editore;
    }

    public int getDisponibili() {
	return disponibili;
    }

    public void setDisponibili(int disponibili) {
	this.disponibili = disponibili;
    }

    public ArrayList<String> getAutori() {
	return autori;
    }

    public void setAutori(ArrayList<String> autori) {
	this.autori = autori;
    }
    
    /**
     * Trasforma la lista degli autori in una stringa in cui i nomi degli autori sono separati dal virgola
     * @return stringa autori
     */
    public String getStringAutori(){
	
	String stringa = "";
	
	for(String a: autori){
	    stringa = stringa + a + ", ";
	}
	
	stringa = stringa.substring(0, stringa.length()-2);
	
	return stringa;
    }
    
}

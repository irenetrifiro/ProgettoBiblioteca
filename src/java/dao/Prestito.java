package dao;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Prestito {
    private final int id; //id del prestito
    private String utente; //username dell'utente
    private Libro libro; //libro in prestito
    private Date d_prestito; //data del prestito
    private Date d_restituzione; //data di restituzione (NULL se è ancora in prestito)

    public Prestito(Integer id, String utente, Libro libro, Date d_prestito, Date d_restituzione) {
        this.id = id;
        this.utente = utente;
        this.libro = libro;
        this.d_prestito = d_prestito;
        this.d_restituzione = d_restituzione;
    }

    public int getId(){
        return id;
    }
    
    public String getUtente() {
        return utente;
    }

    public Libro getLibro() {
        return libro;
    }

    public Date getD_prestito() {
        return d_prestito;
    }

    public Date getD_restituzione() {
        return d_restituzione;
    }
    
    public void setUtente(String utente) {
        this.utente = utente;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public void setD_prestito(Date d_prestito) {
        this.d_prestito = d_prestito;
    }

    public void setD_restituzione(Date d_restituzione) {
        this.d_restituzione = d_restituzione;
    }

    /**
     * Restituisce la data come stringa secondo il formato "gg/mm/aaaa"
     * @param inverted
     * @return data formattata
     */
    public String getStringD_inverted(Date inverted){
        
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        
        String date=formatter.format(inverted);
        
        return date;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author filipposcotto
 */
public class Categoria {
    final private int idCategoria;
    final private String descrizione;
    private int disponibilita;

    public int getIdCategoria() {
        return idCategoria;
    }

    public String getDescrizione() {
        return descrizione;
    }

    
    public int getDisponibilita() {
        return disponibilita;
    }
    
    public void setDisponibilita(int disponibilita) {
        this.disponibilita = disponibilita;
    }
    
    
    public Categoria(int i, String d, int di) {
        idCategoria = i;
        descrizione = d;
        disponibilita = di;
    }
    
    
    public Categoria(int i, String d) {
        idCategoria = i;
        descrizione = d;
    }
}

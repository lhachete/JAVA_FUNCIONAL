/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;


public class Producte {
    private final int codi;
    private final String nom;
    private final String categoria;
    private final double preu;    
    private final int iva;

    public Producte(int codi, String nom, String descripcio, double preu, int iva) {
        this.codi = codi;
        this.nom = nom;
        this.categoria = descripcio;
        this.preu = preu;
        this.iva = iva;
    }

    public int getCodi() {
        return codi;
    }

    public String getNom() {
        return nom;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPreu() {
        return preu;
    }

    public int getIva() {
        return iva;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.codi;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producte other = (Producte) obj;
        return this.codi == other.codi;
    }

    @Override
    public String toString() {
        return "Producte{" + "codi=" + codi + ", nom=" + nom + ", descripcio=" + categoria + ", preu=" + preu + ", iva=" + iva + '}';
    }
    
    

}

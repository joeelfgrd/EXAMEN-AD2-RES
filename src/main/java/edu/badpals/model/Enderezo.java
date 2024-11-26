package edu.badpals.model;

public class Enderezo {

    private int idEnderezo;
    private String descricion;

    public Enderezo() {
    }

    public Enderezo(int idEnderezo) {
        this.idEnderezo = idEnderezo;
    }

    public Enderezo(int idEnderezo, String descricion) {
        this.idEnderezo = idEnderezo;
        this.descricion = descricion;
    }

    public int getIdEnderezo() {
        return idEnderezo;
    }

    public void setIdEnderezo(int idEnderezo) {
        this.idEnderezo = idEnderezo;
    }

    public String getDescricion() {
        return descricion;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }

    @Override
    public String toString() {
        return "Enderezo{" +
                "idEnderezo=" + idEnderezo +
                ", descricion='" + descricion + '\'' +
                '}';
    }
}
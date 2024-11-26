package edu.badpals.model;

public class Cliente {

    private int idCliente;
    private String nifCliente;
    private String nomeApelidos;
    private Enderezo enderezo;

    public Cliente(int idCliente, String nifCliente, Enderezo enderezo, String nomeApelidos) {
        this.idCliente = idCliente;
        this.nifCliente = nifCliente;
        this.enderezo = enderezo;
        this.nomeApelidos = nomeApelidos;
    }

    public String getNifCliente() {
        return nifCliente;
    }

    public void setNifCliente(String nifCliente) {
        this.nifCliente = nifCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeApelidos() {
        return nomeApelidos;
    }

    public void setNomeApelidos(String nomeApelidos) {
        this.nomeApelidos = nomeApelidos;
    }

    public Enderezo getEnderezo() {
        return enderezo;
    }

    public void setEnderezo(Enderezo enderezo) {
        this.enderezo = enderezo;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nifCliente='" + nifCliente + '\'' +
                ", nomeApelidos='" + nomeApelidos + '\'' +
                ", enderezo=" + enderezo +
                '}';
    }
}
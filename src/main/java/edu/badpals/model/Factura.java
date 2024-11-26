package edu.badpals.model;

public class Factura {

    private int idFactura;
    private double prezo;
    private Cliente cliente;

    public Factura() {
    }

    public Factura(int idFactura, double prezo, Cliente cliente) {
        this.idFactura = idFactura;
        this.prezo = prezo;
        this.cliente = cliente;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public double getPrezo() {
        return prezo;
    }

    public void setPrezo(double prezo) {
        this.prezo = prezo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "idFactura=" + idFactura +
                ", prezo=" + prezo +
                ", cliente=" + cliente +
                '}';
    }
}

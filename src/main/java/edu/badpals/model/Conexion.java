package edu.badpals.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Conexion {
    private static Connection conexion = null;

    private static void crearConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String urldb = "jdbc:mysql://localhost:3306/facturacion";
            conexion = DriverManager.getConnection(urldb, "root", "root");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int addCliente(Cliente cliente) {
        try {
            String newClisql = "INSERT INTO cliente (idCliente, nifCliente, nomeApelidos, idEnderezo) VALUES (?, ?, ?, ?)";
            crearConexion();
            PreparedStatement psnewCli = conexion.prepareStatement(newClisql);
            psnewCli.setInt(1, cliente.getIdCliente());
            psnewCli.setString(2, cliente.getNifCliente());
            psnewCli.setString(3, cliente.getNomeApelidos());
            psnewCli.setInt(4, cliente.getEnderezo().getIdEnderezo());
            int rowsAffected = psnewCli.executeUpdate();
            System.out.println("Se han modificado este número de líneas: " + rowsAffected);
            psnewCli.close();
            cerrarConexion();
            return rowsAffected;
        } catch (SQLException e) {
            return 0;
        }
    }

    public static int updateCliente(Cliente cliente) {
        try {
            String modifClisql = "UPDATE cliente SET nifCliente = ?, nomeApelidos = ?, idEnderezo = ? WHERE idCliente = ?";
            crearConexion();
            PreparedStatement psmodif = conexion.prepareStatement(modifClisql);
            psmodif.setString(1, cliente.getNifCliente());
            psmodif.setString(2, cliente.getNomeApelidos());
            psmodif.setInt(3, cliente.getEnderezo().getIdEnderezo());
            psmodif.setInt(4, cliente.getIdCliente());
            int rowsAfected = psmodif.executeUpdate();
            System.out.println("Se han modificado este número de líneas: " + rowsAfected);
            psmodif.close();
            cerrarConexion();
            return rowsAfected;
        } catch (SQLException e) {
            return 0;
        }
    }

    public static int deleteCliente(Integer idCliente) {
        try {
            String delClisql = "DELETE FROM cliente WHERE idCliente = ?";
            crearConexion();
            PreparedStatement psDel = conexion.prepareStatement(delClisql);
            psDel.setInt(1, idCliente);
            int rowsAffected = psDel.executeUpdate();
            System.out.println("Se han modificado este número de líneas: " + rowsAffected);
            psDel.close();
            cerrarConexion();
            return rowsAffected;
        } catch (SQLException e) {
            return 0;
        }
    }

    public static Enderezo getDatosEnderezo(Integer idEnderezo) {
        try {
            String datosEnd = "SELECT * FROM enderezo WHERE idEnderezo = ?";
            crearConexion();
            PreparedStatement psEnd = conexion.prepareStatement(datosEnd);
            psEnd.setInt(1, idEnderezo);
            ResultSet rs = psEnd.executeQuery();
            Enderezo enderezo = null;
            if (rs.next()) {
                int id = rs.getInt("idEnderezo");
                String descricion = rs.getString("descricion");
                return new Enderezo(id, descricion);
            }
            rs.close();
            psEnd.close();
            cerrarConexion();
            return enderezo;
        } catch (SQLException e) {
            System.out.println("Error en getDatosEnderezo en Conexion");
            return null;
        }
    }

    public static Cliente getDatosCliente(Integer idcliente) {
        try {
            String datosClisql = "SELECT * FROM cliente WHERE idCliente = ?";
            crearConexion();
            PreparedStatement psCli = conexion.prepareStatement(datosClisql);
            psCli.setInt(1, idcliente);
            ResultSet rs = psCli.executeQuery();
            Cliente cliente = null;
            if (rs.next()) {
                int id = rs.getInt("idCliente");
                String nifCliente = rs.getString("nifCliente");
                String nomeApelidos = rs.getString("nomeApelidos");
                int idEnderezo = rs.getInt("idEnderezo");
                cliente = new Cliente(id, nifCliente, getDatosEnderezo(idEnderezo), nomeApelidos);
            }
            rs.close();
            psCli.close();
            cerrarConexion();
            return cliente;
        } catch (SQLException e) {
            System.out.println("Error en getDatosCliente en Conexion");
            return null;
        }
    }

    public static ArrayList<Factura> listadoFacturas() {
        ArrayList<Factura> listaFacturas = new ArrayList<>();
        try {
            String listFacsql = "SELECT * FROM factura";
            crearConexion();
            PreparedStatement psListFac = conexion.prepareStatement(listFacsql);
            ResultSet rs = psListFac.executeQuery();
            while (rs.next()) {
                int idFactura = rs.getInt("idFactura");
                double prezo = rs.getDouble("prezo");
                int idCliente = rs.getInt("idCliente");
                Factura factura = new Factura(idFactura, prezo, getDatosCliente(idCliente));
                listaFacturas.add(factura);
            }
            rs.close();
            psListFac.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en listadoFacturas en Conexion");
        }
        return listaFacturas;
    }

    /***********SEGUNDO APARTADO******************/

    public static ArrayList<String> getClienteMaxFacturas() {
        ArrayList<String> datos = new ArrayList<>();
        try {
            String CliMaxFacsql = "SELECT idCliente, COUNT(idFactura) AS numFacturas, SUM(prezo) AS sumaTotal FROM factura GROUP BY idCliente ORDER BY sumaTotal DESC LIMIT 1;";
            crearConexion();
            PreparedStatement psCliMaxFac = conexion.prepareStatement(CliMaxFacsql);
            ResultSet rs = psCliMaxFac.executeQuery();
            if (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                int numFacturas = rs.getInt("numFacturas");
                int sumaTotal = rs.getInt("sumaTotal");
                datos.add(String.valueOf(idCliente));
                datos.add(String.valueOf(numFacturas));
                datos.add(String.valueOf(sumaTotal));
            }
            rs.close();
            psCliMaxFac.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en getClienteMaxFacturas en Conexion");
        }
        return datos;
    }

    public static ArrayList<Factura> getListadoFacturasSinIva() {
        ArrayList<Factura> facturas = new ArrayList<>();
        try {
            String facSinIvasql = "SELECT * FROM factura";
            crearConexion();
            PreparedStatement psfaciva = conexion.prepareStatement(facSinIvasql);
            ResultSet rs = psfaciva.executeQuery();
            while (rs.next()) {
                int idFactura = rs.getInt("idFactura");
                double prezo = rs.getDouble("prezo");
                double precioSinIVA = (prezo - (prezo * 21) / 100);
                int idCliente = rs.getInt("idCliente");
                facturas.add(new Factura(idFactura, precioSinIVA, getDatosCliente(idCliente)));
            }
            rs.close();
            psfaciva.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en getListadoFacturasSinIva en Conexion");
        }
        return facturas;
    }

    public static Integer NumFacturasPorCalle(String calle) {
        int numFacturas = 0;
        try {
            String numFac = "SELECT COUNT(*) AS numFacturas FROM factura WHERE idCliente IN (" +
                                        "SELECT idCliente FROM cliente WHERE idEnderezo = (" +
                                                "SELECT idEnderezo FROM enderezo WHERE descricion = ?))";
            crearConexion();
            PreparedStatement psNumFac = conexion.prepareStatement(numFac);
            psNumFac.setString(1, calle);
            ResultSet rs = psNumFac.executeQuery();
            if (rs.next()) {
                numFacturas = rs.getInt("numFacturas");
            }
            rs.close();
            psNumFac.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en NumFacturasPorCalle en Conexion");
        }
        return numFacturas;
    }

    public static List<Double> getListadoFacturasPorDni(String dniClienteBuscar) {
        List<Double> prezos = new ArrayList<>();
        try {
            String procListFac = "{CALL pr_DniPrezo(?)}";
            crearConexion();
            CallableStatement cs = conexion.prepareCall(procListFac);
            cs.setString(1, dniClienteBuscar);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                prezos.add(rs.getDouble("prezo"));
            }
            rs.close();
            cs.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println(prezos);
        return prezos;
    }



    /***********TERCER APARTADO******************/

    public static ResultSet getInformacionConexion(String esquema, String tabla) {
        try {
            crearConexion();
            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet columns = dbmd.getColumns(esquema, null, tabla, null);
            cerrarConexion();
                return columns;
        } catch (SQLException e) {
            System.out.println("Error en getInformacionConexion en Conexion");
                return null;
        }
    }





























    /********************POSIBLES OTROS METODOS*****************************/

    public static void addFactura(Factura factura) {
        try {
            String newFacsql = "INSERT INTO factura (idFactura, prezo, idCliente) VALUES (?, ?, ?)";
            crearConexion();
            PreparedStatement psnewFac = conexion.prepareStatement(newFacsql);
            psnewFac.setInt(1, factura.getIdFactura());
            psnewFac.setDouble(2, factura.getPrezo());
            psnewFac.setInt(3, factura.getCliente().getIdCliente());
            psnewFac.executeUpdate();
            psnewFac.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en addFactura en Conexion");
        }
    }

    public static void updateFactura(Factura factura) {
        try {
            String modifFacsql = "UPDATE factura SET prezo = ?, idCliente = ? WHERE idFactura = ?";
            crearConexion();
            PreparedStatement psmodifFac = conexion.prepareStatement(modifFacsql);
            psmodifFac.setDouble(1, factura.getPrezo());
            psmodifFac.setInt(2, factura.getCliente().getIdCliente());
            psmodifFac.setInt(3, factura.getIdFactura());
            psmodifFac.executeUpdate();
            psmodifFac.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en updateFactura en Conexion");
        }
    }

    public static void deleteFactura(Integer idFactura) {
        try {
            String delFacsql = "DELETE FROM factura WHERE idFactura = ?";
            crearConexion();
            PreparedStatement psDelFac = conexion.prepareStatement(delFacsql);
            psDelFac.setInt(1, idFactura);
            psDelFac.executeUpdate();
            psDelFac.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en deleteFactura en Conexion");
        }
    }

    public static ArrayList<Cliente> listadoClientes() {
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        try {
            String listClisql = "SELECT * FROM cliente";
            crearConexion();
            PreparedStatement psListCli = conexion.prepareStatement(listClisql);
            ResultSet rs = psListCli.executeQuery();
            while (rs.next()) {
                int idCliente = rs.getInt("idCliente");
                String nifCliente = rs.getString("nifCliente");
                String nomeApelidos = rs.getString("nomeApelidos");
                int idEnderezo = rs.getInt("idEnderezo");
                listaClientes.add(new Cliente(idCliente, nifCliente, getDatosEnderezo(idEnderezo), nomeApelidos));
            }
            rs.close();
            psListCli.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en listadoClientes en Conexion");
        }
        return listaClientes;
    }

    public static ArrayList<Enderezo> listadoEnderezos() {
        ArrayList<Enderezo> listaEnderezos = new ArrayList<>();
        try {
            String listEndsql = "SELECT * FROM enderezo";
            crearConexion();
            PreparedStatement psListEnd = conexion.prepareStatement(listEndsql);
            ResultSet rs = psListEnd.executeQuery();
            while (rs.next()) {
                int idEnderezo = rs.getInt("idEnderezo");
                String descricion = rs.getString("descricion");
                listaEnderezos.add(new Enderezo(idEnderezo, descricion));
            }
            rs.close();
            psListEnd.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en listadoEnderezos en Conexion");
        }
        return listaEnderezos;
    }

    public static ArrayList<Factura> listadoFacturasCliente(Integer idCliente) {
        ArrayList<Factura> listaFacturas = new ArrayList<>();
        try {
            String listFacClisql = "SELECT * FROM factura WHERE idCliente = ?";
            crearConexion();
            PreparedStatement psListFacCli = conexion.prepareStatement(listFacClisql);
            psListFacCli.setInt(1, idCliente);
            ResultSet rs = psListFacCli.executeQuery();
            while (rs.next()) {
                int idFactura = rs.getInt("idFactura");
                double prezo = rs.getDouble("prezo");
                listaFacturas.add(new Factura(idFactura, prezo, getDatosCliente(idCliente)));
            }
            rs.close();
            psListFacCli.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en listadoFacturasCliente en Conexion");
        }
        return listaFacturas;
    }

    public static ArrayList<Factura> listadoFacturasSinIvaCliente(Integer idCliente) {
        ArrayList<Factura> listaFacturas = new ArrayList<>();
        try {
            String listFacClisql = "SELECT * FROM factura WHERE idCliente = ?";
            crearConexion();
            PreparedStatement psListFacCli = conexion.prepareStatement(listFacClisql);
            psListFacCli.setInt(1, idCliente);
            ResultSet rs = psListFacCli.executeQuery();
            while (rs.next()) {
                int idFactura = rs.getInt("idFactura");
                double prezo = rs.getDouble("prezo");
                double precioSinIVA = (prezo - (prezo * 21) / 100);
                listaFacturas.add(new Factura(idFactura, precioSinIVA, getDatosCliente(idCliente)));
            }
            rs.close();
            psListFacCli.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en listadoFacturasSinIvaCliente en Conexion");
        }
        return listaFacturas;
    }

    public static void ordenarClientes() {
        try {
            String ordCli = "SELECT * FROM cliente ORDER BY nomeApelidos";
            crearConexion();
            PreparedStatement psOrdCli = conexion.prepareStatement(ordCli);
            ResultSet rs = psOrdCli.executeQuery();
            while (rs.next()) {
                System.out.println("ID Cliente: " + rs.getInt("idCliente") + ", NIF: " + rs.getString("nifCliente") + ", Nombre y apellidos: " + rs.getString("nomeApelidos") + ", ID Enderezo: " + rs.getInt("idEnderezo"));
            }
            rs.close();
            psOrdCli.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en ordenarClientes en Conexion");
        }
    }

    public static void ordenarEnderezos() {
        try {
            String ordEnd = "SELECT * FROM enderezo ORDER BY descricion";
            crearConexion();
            PreparedStatement psOrdEnd = conexion.prepareStatement(ordEnd);
            ResultSet rs = psOrdEnd.executeQuery();
            while (rs.next()) {
                System.out.println("ID Enderezo: " + rs.getInt("idEnderezo") + ", Descripción: " + rs.getString("descricion"));
            }
            rs.close();
            psOrdEnd.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en ordenarEnderezos en Conexion");
        }
    }

    public static void ordenarFacturas() {
        try {
            String ordFac = "SELECT * FROM factura ORDER BY prezo";
            crearConexion();
            PreparedStatement psOrdFac = conexion.prepareStatement(ordFac);
            ResultSet rs = psOrdFac.executeQuery();
            while (rs.next()) {
                System.out.println("ID Factura: " + rs.getInt("idFactura") + ", Precio: " + rs.getDouble("prezo") + ", ID Cliente: " + rs.getInt("idCliente"));
            }
            rs.close();
            psOrdFac.close();
            cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error en ordenarFacturas en Conexion");
        }
    }
}

package edu.badpals.view;

import edu.badpals.model.Cliente;
import edu.badpals.model.Conexion;
import edu.badpals.model.Enderezo;
import edu.badpals.model.Factura;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {
    public static void OpcionesMenu() {
        System.out.println("---------- Menu ----------");
        System.out.println("1. Agregar cliente");
        System.out.println("2. Modificar cliente");
        System.out.println("3. Eliminar cliente");
        System.out.println("4. Listar facturas");
        System.out.println("5. Mostrar cliente con más facturas");
        System.out.println("6. Listar facturas sin IVA");
        System.out.println("7. Número de facturas por calle");
        System.out.println("8. Precio facturas de un cliente");
        System.out.println("9. Mostrar columnas de una tabla");
        System.out.println("0. Salir");
        System.out.println("-------------------------");
        System.out.print("Selecciona una opción: ");
    }

    public static void listadoFacturas() {
        ArrayList<Factura> facturas = Conexion.listadoFacturas();
        StringBuilder sb = new StringBuilder();
        sb.append("---------- Listado de Facturas ----------\n");
        for (Factura factura : facturas) {
            sb.append("\nFactura ID: ").append(factura.getIdFactura())
                    .append("\nPrecio: €").append(factura.getPrezo())
                    .append("\nCliente: ").append(factura.getCliente().getNomeApelidos())
                    .append("\nNIF: ").append(factura.getCliente().getNifCliente())
                    .append("\nDirección: ").append(factura.getCliente().getEnderezo().getDescricion())
                    .append("\n----------------------------------------\n");
        }
        System.out.println(sb);
    }

    public static void deleteClienteMenu(Scanner scanner) {
        System.out.print("ID del cliente a eliminar: ");
        int idEliminar = scanner.nextInt();
        Conexion.deleteCliente(idEliminar);
    }

    public static void modifClienteMenu(Scanner scanner) {
        System.out.print("ID del cliente a modificar: ");
        int idMod = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nuevo NIF del cliente: ");
        String nuevoNif = scanner.nextLine();
        System.out.print("Nuevo nombre y apellidos: ");
        String nuevoNomeApelidos = scanner.nextLine();
        System.out.print("Nuevo ID del Enderezo: ");
        int nuevoIdEnderezo = scanner.nextInt();
        Cliente clienteModificado = new Cliente(idMod, nuevoNif, new Enderezo(nuevoIdEnderezo, ""), nuevoNomeApelidos);
        Conexion.updateCliente(clienteModificado);
    }

    public static void crearClienteMenu(Scanner scanner) {
        System.out.print("ID del cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("NIF del cliente: ");
        String nif = scanner.nextLine();
        System.out.print("Nombre y apellidos: ");
        String nomeApelidos = scanner.nextLine();
        System.out.print("ID del Enderezo: ");
        int idEnderezo = scanner.nextInt();
        Cliente nuevoCliente = new Cliente(id, nif, new Enderezo(idEnderezo, ""), nomeApelidos);
        Conexion.addCliente(nuevoCliente);
    }

    public static void numFacturaPorCalle(Scanner scanner) {
        scanner.nextLine();
        System.out.print("Nombre de la calle: ");
        String calle = scanner.nextLine();
        int numFacturas = Conexion.NumFacturasPorCalle(calle);
        System.out.println("Número de facturas en la calle " + calle + ": " + numFacturas);
    }

    public static void listadoFacturaSinIva() {
        ArrayList<Factura> facturasSinIva = Conexion.getListadoFacturasSinIva();
        StringBuilder sb = new StringBuilder();
        sb.append("---------- Listado de Facturas sin IVA ----------\n");
        for (Factura facturaSinIva : facturasSinIva) {
            sb.append("\nFactura ID: ").append(facturaSinIva.getIdFactura())
                    .append("\nPrecio sin IVA: €").append(facturaSinIva.getPrezo())
                    .append("\nCliente: ").append(facturaSinIva.getCliente().getNomeApelidos())
                    .append("\nNIF: ").append(facturaSinIva.getCliente().getNifCliente())
                    .append("\nDirección: ").append(facturaSinIva.getCliente().getEnderezo().getDescricion())
                    .append("\n----------------------------------------\n");
        }
        System.out.println(sb);
    }

    public static void clienteMasFacturas() {
        ArrayList<String> datosCliente = Conexion.getClienteMaxFacturas();
        System.out.println("Cliente con más facturas:");
        System.out.println("ID Cliente: " + datosCliente.get(0));
        System.out.println("Número de facturas: " + datosCliente.get(1));
        System.out.println("Suma total: " + datosCliente.get(2));
    }

    public static void precioFacturasCliente(Scanner scanner) {
        System.out.print("DNI del cliente: ");
        scanner.nextLine();
        String dni = scanner.nextLine();
        Conexion.getListadoFacturasPorDni(dni);
    }
    public static void ensenarInforBBDD(ResultSet rs) {
        try {
            while (rs.next()) {
                System.out.println("Columna: " + rs.getString("COLUMN_NAME") + ", Tipo de datos: " + rs.getString("TYPE_NAME") +
                        ", Tamaño: " + rs.getInt("COLUMN_SIZE") + ", Admite nulos: " + rs.getString("IS_NULLABLE"));
            }
        } catch (SQLException e) {
            System.out.println("Error al enseñar la información de la bbdd");
        }
    }


}

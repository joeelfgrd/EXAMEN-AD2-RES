package edu.badpals.controller;
import edu.badpals.model.Conexion;
import edu.badpals.view.View;
import java.util.Scanner;
public class Controller {

    public static void logicaMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            View.OpcionesMenu();
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    View.crearClienteMenu(scanner);
                    break;
                case 2:
                    View.modifClienteMenu(scanner);
                    break;
                case 3:
                    View.deleteClienteMenu(scanner);
                    break;
                case 4:
                    View.listadoFacturas();
                    break;
                case 5:
                    View.clienteMasFacturas();
                    break;
                case 6:
                    View.listadoFacturaSinIva();
                    break;
                case 7:
                    View.numFacturaPorCalle(scanner);
                    break;
                case 8:
                    View.precioFacturasCliente(scanner);
                    break;
                case 9:
                    View.ensenarInforBBDD(Conexion.getInformacionConexion("facturacion", "cliente"));
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);
        scanner.close();
    }
}


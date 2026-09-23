import java.util.Deque;
import java.util.LinkedList;
import java.util.Scanner;

public class ColaTrabajos {
    public static void main(String[] args) {
        Deque<String> trabajos = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = leerOpcion(scanner);

            switch (opcion) {
                case 1 -> agregarNormal(trabajos, scanner);
                case 2 -> agregarUrgente(trabajos, scanner);
                case 3 -> procesarSiguiente(trabajos);
                case 4 -> consultarSiguiente(trabajos);
                case 5 -> mostrarPendientes(trabajos);
                case 6 -> mostrarNumeroTrabajos(trabajos);
                case 7 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción no válida.");
            }
            System.out.println();
        } while (opcion != 7);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("===== Sistema de cola de trabajos =====");
        System.out.println("1. Agregar trabajo normal");
        System.out.println("2. Agregar trabajo urgente");
        System.out.println("3. Procesar siguiente trabajo");
        System.out.println("4. Consultar siguiente trabajo");
        System.out.println("5. Mostrar trabajos pendientes");
        System.out.println("6. Mostrar número de trabajos");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int leerOpcion(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void agregarNormal(Deque<String> trabajos, Scanner scanner) {
        System.out.print("Nombre del trabajo normal: ");
        String nombre = scanner.nextLine();
        trabajos.addLast(nombre);
        System.out.println("Trabajo agregado al final: " + nombre);
    }

    private static void agregarUrgente(Deque<String> trabajos, Scanner scanner) {
        System.out.print("Nombre del trabajo urgente: ");
        String nombre = scanner.nextLine();
        trabajos.addFirst(nombre);
        System.out.println("Trabajo urgente agregado al frente: " + nombre);
    }

    private static void procesarSiguiente(Deque<String> trabajos) {
        String trabajo = trabajos.pollFirst();
        if (trabajo == null) {
            System.out.println("No hay trabajos pendientes por procesar.");
        } else {
            System.out.println("Procesando: " + trabajo);
        }
    }

    private static void consultarSiguiente(Deque<String> trabajos) {
        String trabajo = trabajos.peekFirst();
        if (trabajo == null) {
            System.out.println("No hay trabajos pendientes.");
        } else {
            System.out.println("Siguiente trabajo: " + trabajo);
        }
    }

    private static void mostrarPendientes(Deque<String> trabajos) {
        if (trabajos.isEmpty()) {
            System.out.println("No hay trabajos pendientes.");
            return;
        }
        System.out.println("Trabajos pendientes (en orden de atención):");
        int posicion = 1;
        for (String trabajo : trabajos) {
            System.out.println("  " + posicion + ". " + trabajo);
            posicion++;
        }
    }

    private static void mostrarNumeroTrabajos(Deque<String> trabajos) {
        System.out.println("Número de trabajos pendientes: " + trabajos.size());
    }
}

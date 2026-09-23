import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BenchmarkListas {
    private static final int N = 100_000;

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        llenar(arrayList);
        llenar(linkedList);

        System.out.println("--- Acceso con get(i) ---");
        medirAccesoGet("ArrayList", arrayList);
        medirAccesoGet("LinkedList", linkedList);

        System.out.println("--- Acceso con for-each ---");
        medirAcceso("ArrayList", arrayList);
        medirAcceso("LinkedList", linkedList);

        System.out.println("--- Inserción al inicio ---");
        medirInsercionInicio("ArrayList", new ArrayList<>());
        medirInsercionInicio("LinkedList", new LinkedList<>());

        System.out.println("--- Inserción al final ---");
        medirInsercionFinal("ArrayList", new ArrayList<>());
        medirInsercionFinal("LinkedList", new LinkedList<>());

        System.out.println("--- Eliminación desde el inicio ---");
        List<Integer> arrayListParaEliminar = new ArrayList<>();
        List<Integer> linkedListParaEliminar = new LinkedList<>();
        llenar(arrayListParaEliminar);
        llenar(linkedListParaEliminar);
        medirEliminacionInicio("ArrayList", arrayListParaEliminar);
        medirEliminacionInicio("LinkedList", linkedListParaEliminar);
    }

    private static void llenar(List<Integer> lista) {
        for (int i = 0; i < N; i++) {
            lista.add(i);
        }
    }

    private static void medirAccesoGet(String nombre, List<Integer> lista) {
        long inicio = System.nanoTime();
        long suma = 0;

        for (int i = 0; i < lista.size(); i++) {
            suma += lista.get(i);
        }

        long fin = System.nanoTime();

        System.out.printf("%s: %.3f ms%n",
                nombre, (fin - inicio) / 1_000_000.0);
        System.out.println("Suma: " + suma);
    }

    private static void medirAcceso(String nombre, List<Integer> lista) {
        long inicio = System.nanoTime();
        long suma = 0;

        for (Integer valor : lista) {
            suma += valor;
        }

        long fin = System.nanoTime();

        System.out.printf("%s: %.3f ms%n",
                nombre, (fin - inicio) / 1_000_000.0);
        System.out.println("Suma: " + suma);
    }

    private static void medirInsercionInicio(
        String nombre, List<Integer> lista) {

        long inicio = System.nanoTime();

        for (int i = 0; i < 50_000; i++) {
            lista.add(0, i);
        }

        long fin = System.nanoTime();

        System.out.printf("%s: %.3f ms%n",
                nombre, (fin - inicio) / 1_000_000.0);
    }

    private static void medirInsercionFinal(
            String nombre, List<Integer> lista) {

        long inicio = System.nanoTime();

        for (int i = 0; i < 100_000; i++) {
            lista.add(i);
        }

        long fin = System.nanoTime();

        System.out.printf("%s: %.3f ms%n",
                nombre, (fin - inicio) / 1_000_000.0);
    }

    private static void medirEliminacionInicio(
            String nombre, List<Integer> lista) {

        long inicio = System.nanoTime();

        while (!lista.isEmpty()) {
            lista.remove(0);
        }

        long fin = System.nanoTime();

        System.out.printf("%s: %.3f ms%n",
                nombre, (fin - inicio) / 1_000_000.0);
    }
}

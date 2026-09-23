import java.util.LinkedList;

public class EjemploDeque {
    public static void main(String[] args) {
        LinkedList<String> tareas = new LinkedList<>();

        tareas.addFirst("Primera");
        tareas.addLast("Última");
        tareas.addFirst("Urgente");

        System.out.println(tareas);
        System.out.println("Primera: " + tareas.getFirst());
        System.out.println("Última: " + tareas.getLast());

        tareas.removeFirst();
        System.out.println(tareas);
    }
}

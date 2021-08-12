package trabajopracticofinal1;

import java.util.Comparator;

//Clase que me permite comparar dos libros segun su nombre y devolverlos ordenados al metodo que lo llame;
public class ComparadorNombre implements Comparator<Libro> {

    public int compare(Libro l1, Libro l2) {
        return l1.getNombre().compareToIgnoreCase(l2.getNombre());
    }
}
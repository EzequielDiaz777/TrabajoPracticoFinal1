package trabajopracticofinal1;

import java.util.Comparator;

//Clase que me permite comparar dos libros segun su codigo y devolverlos ordenados al metodo que lo llame;
public class ComparadorCodigo implements Comparator<Libro> {

    public int compare(Libro l1, Libro l2) {
        return l1.getCodigo()-l2.getCodigo();
    }
}
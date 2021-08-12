package trabajopracticofinal1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//Clase donde guardaremos los libros que vayamos cargando;
public class Biblioteca{

    //Atributo principal;
    private ArrayList<Libro> biblioteca;

    //Constructores de la clase;
    public Biblioteca() {
        this.biblioteca = new ArrayList();
    }

    public Biblioteca(ArrayList<Libro> biblioteca) {
        this.biblioteca = biblioteca;
    }

    //Metodos para agregar libros al arreglo;
    public void agregaLibro(LibroFisico lf) {
        biblioteca.add(lf);
    }

    public void agregaLibro(LibroDigital ld) {
        biblioteca.add(ld);
    }

    public void agregaLibro(LibroAudio la) {
        biblioteca.add(la);
    }

    //Metodo para eliminar libros del arreglo;
    public void eliminaLibro(int i){
        biblioteca.remove(i);
    }
    
    //Metodos que devuelven el libro que se encuentre en la posicion indicada;
    public Libro getLibro(int i){
        return biblioteca.get(i);
    }
    
    public LibroFisico getLibroF(int i) {
        LibroFisico lf = (LibroFisico) biblioteca.get(i);
        return lf;
    }

    public LibroDigital getLibroD(int i) {
        LibroDigital ld = (LibroDigital) biblioteca.get(i);
        return ld;
    }

    public LibroAudio getLibroA(int i) {
        LibroAudio la = (LibroAudio) biblioteca.get(i);
        return la;
    }

    //Metodo para saber la cantidad de libros ingresados hasta el momento;
    public int numeroLibros() {
        return biblioteca.size();
    }

    //Metodo para saber si un autor en particular fue ingresado en la biblioteca;
    public int buscaAutor(String autor) {
        for (int i = 0; i < biblioteca.size(); i++) {
            if (autor.equalsIgnoreCase(biblioteca.get(i).getAutor())) {
                return i;
            }
        }
        return -1;
    }
    
    //Metodo para saber si un codigo en particular fue ingresado en la biblioteca;
    public int buscaCodigo(int codigo) {
        for (int i = 0; i < biblioteca.size(); i++) {
            int b = biblioteca.get(i).getCodigo();
            if (b == codigo) {
                return i;
            }
        }
        return -1;
    }

    //Metodo para ordenar el arreglo segun el criterio que elijamos;
    public void sort(Comparator ca) {
        Collections.sort(biblioteca, ca);
    }
}
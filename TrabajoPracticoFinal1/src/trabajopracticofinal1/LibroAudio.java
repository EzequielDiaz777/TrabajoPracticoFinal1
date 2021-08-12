package trabajopracticofinal1;

//Clase LibroAudio, hija de la clase Libro;
public class LibroAudio extends Libro {

    //Atributos particulares de la clase LibroAudio;
    private int cantidad;
    private int total;
    
    //constructor vacio de la clase;
    public LibroAudio(){
    }
    
    //Constructor con parametros de la clase;
    public LibroAudio(String nombre, String autor, int año, int codigo, int cantidad, int total) {
        super.setNombre(nombre);
        super.setAutor(autor);
        super.setAño(año);
        super.setCodigo(codigo);
        this.cantidad = cantidad;
        this.total = total;
    }

    //Setters y Getters;
    public void setCantidad(int cant) {
        this.cantidad = cant;
    }

    public void setTotal(int tot) {
        this.total = tot;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getTotal() {
        return total;
    }

    //Getter que le indica al metodo que carga libros desde el archivo txt al arreglo, que clase de libros esta tratando en el arreglo de caracteres;
    public String getTipo(){
        return "Audiolibro";
    }
    
    //Metodo equals heredado de Libro y reescrita por LibroAudio;
    public boolean equals(Libro o){
        LibroAudio la = (LibroAudio) o;
        return la.getCodigo() == this.getCodigo();
    }
}
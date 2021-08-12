package trabajopracticofinal1;

//Clase LibroFisico, hija dela clase Libro;
public class LibroFisico extends Libro {

    //constructor vacio de la clase;
    public LibroFisico() {
    }

    //Constructor con parametros de la clase;
    public LibroFisico(String nombre, String autor, int año, int codigo) {
        super(nombre, autor, año, codigo);
    }

    //Setters y Getters;
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    //Getter que le indica al metodo que carga libros desde el archivo txt al arreglo, que clase de libros esta tratando en el arreglo de caracteres;
    public String getTipo() {
        return "Libro fisico";
    }

    //Metodo equals heredado de Libro, y reescrita por LibroFisico;
    public boolean equals(Libro o) {
        LibroFisico l = (LibroFisico) o;
        return l.getCodigo() == this.getCodigo();
    }
}
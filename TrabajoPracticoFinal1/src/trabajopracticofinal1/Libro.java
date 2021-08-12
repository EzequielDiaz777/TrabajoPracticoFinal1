package trabajopracticofinal1;

//Clase padre;
public abstract class Libro {

    //Atributos particulares de la Clase Libro;
    protected String nombre;
    protected String autor;
    protected int año;
    protected int codigo;

    //constructor vacio de la clase;
    public Libro() {
    }

    //Constructor con parametros de la clase;
    public Libro(String nombre, String autor, int año, int codigo) {
        this.nombre = nombre;
        this.autor = autor;
        this.año = año;
        this.codigo = codigo;
    }

    //Setters y Getters;
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAutor() {
        return autor;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public int getAño() {
        return año;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    //Getter que le indica al metodo que carga libros desde el archivo txt al arreglo, que clase de libros esta tratando en el arreglo de caracteres;
    public String getTipo() {
        return "Libro";
    }
}
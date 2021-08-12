package trabajopracticofinal1;

//Clase LibroDigital, hija de la clase Libro;
public class LibroDigital extends Libro {

    //Atributos particulares de la clase LibroDigital;
    private int memoria;
    private String programas;

    //constructor vacio de la clase;
    public LibroDigital() {
    }

    //Constructor con parametros de la clase;
    public LibroDigital(String nombre, String autor, int año, int codigo, int memoria, String programas) {
        super.setNombre(nombre);
        super.setAutor(autor);
        super.setAño(año);
        super.setCodigo(codigo);
        this.memoria = memoria;
        this.programas = programas;
    }

    //Setters y Getters;
    public void setMemoria(int mem) {
        this.memoria = mem;
    }

    public int getMemoria() {
        return memoria;
    }
    
    public void setPrograma(String prog) {
        this.programas = prog;
    }

    public String getPrograma() {
        return programas;
    }

    //Getter que le indica al metodo que carga libros desde el archivo txt al arreglo, que clase de libros esta tratando en el arreglo de caracteres;
    public String getTipo(){
        return "Libro digital";
    }
    
    //Metodo equals heredado de Libro y reescrita por LibroDigital.
    public boolean equals(Libro o){
        LibroDigital ld = (LibroDigital) o;
        return ld.getCodigo() == this.getCodigo();
    }
}
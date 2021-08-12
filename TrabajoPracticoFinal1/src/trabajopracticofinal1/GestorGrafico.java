package trabajopracticofinal1;

import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

//Clase principal desde donde administramos las clases y gestionamos todos los eventos y las ejecuciones de los metodos;
public class GestorGrafico extends javax.swing.JFrame {

    //Atributos de la clase;
    private File archivoBiblio;
    private File archivoNom;
    private File archivoAño;
    private LibroFisico libroF;
    private LibroDigital libroD; 
    private LibroAudio libroA;
    private Biblioteca biblioteca;
    private DefaultTableModel miModelo;
    private DefaultTableModel miModelo2;
    String[] cabecera = {"Nombre", "Autor", "Año", "Codigo", "Memoria", "Programa", "Cantidad", "Total"};
    String[][] data = {};

    //Inicializo todos los atributos de la clase;
    public GestorGrafico() {
        initComponents();
        archivoBiblio = new File("Biblioteca.txt");
        archivoNom = new File("Listado por Nombre.txt");
        archivoAño = new File("Listado por Año.txt");
        Fichero.setArchivoBiblio(archivoBiblio);
        Fichero.setArchivoNom(archivoNom);
        Fichero.setArchivoAño(archivoAño);
        biblioteca = new Biblioteca(Fichero.cargarLibros());
        miModelo = new DefaultTableModel(data, cabecera);
        miModelo2 = new DefaultTableModel(data, cabecera);
        jTableLibros1.setModel(miModelo);
        TableRowSorter<TableModel> elQueOrdena1 = new TableRowSorter<>(miModelo);
        jTableLibros1.setRowSorter(elQueOrdena1);
        jTableLibros2.setModel(miModelo);
        jTableLibros3.setModel(miModelo2);
        cargarTabla();
    }

    //Metodo que sirve para mostrar una ventana con un mensaje predefinido;
    public void mensaje(String texto) {
        JOptionPane.showMessageDialog(this, texto);
    }

    //Metodos para agregar libros al arreglo y a la tabla que se encuentra 
    //en la pestaña "Agregar" y "Eliminar";
    public void insertarFi(String nombre, String autor, int año, int codigo) {
        libroF = new LibroFisico(nombre, autor, año, codigo);
        biblioteca.agregaLibro(libroF);
    }

    public void insertarDi(String nombre, String autor, int año, int codigo, int memoria, String programa) {
        libroD = new LibroDigital(nombre, autor, año, codigo, memoria, programa);
        biblioteca.agregaLibro(libroD); 
    }

    public void insertarAu(String nombre, String autor, int año, int codigo, int cantidad, int total) {
        libroA = new LibroAudio(nombre, autor, año, codigo, cantidad, total);
        biblioteca.agregaLibro(libroA);
    }

    //Metodo para eliminar libros del arreglo y de la tabla que se encuentra en las pestañas
    // "Agregar" y "Eliminar";
    public void eliminar() {
        if (jTableLibros2.getSelectedRow() < 0) {
            mensaje("Seleccione algun libro para eliminar.");
        } else if (biblioteca.buscaCodigo(Integer.parseInt(jTextFieldElCod.getText())) != -1) {
                int i = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar a éste libro?",
                        "Reponder", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                switch (i) {
                    case 0:
                        biblioteca.eliminaLibro(biblioteca.buscaCodigo((Integer.parseInt(jTextFieldElCod.getText()))));
                        limpiarElEntradas();
                        vaciarTablaEl();
                        cargarTabla();
                        Fichero.setArchivoBiblio(Fichero.guardarLibros(biblioteca));
                        mensaje("¡La biblioteca se ha guardada con exito!");
                        break;
                    case 1:
                        limpiarElEntradas();
                        vaciarTablaEl();
                        cargarTabla();
                        break;
                    case -1:
                        limpiarElEntradas();
                        vaciarTablaEl();
                        cargarTabla();
                        biblioteca.sort(new ComparadorCodigo());
                        break;
                }
            } else {
                mensaje("El Libro que usted busca no se encuentra en la biblioteca");
            }
        }

    //Metodos que sirve para vaciar la tabla que se encuentra en la pestaña "Agregar";
    public void vaciarTablaAg() {
        int filas = jTableLibros1.getRowCount();
        for (int i = 0; i < filas; i++) {
            miModelo.removeRow(0);
        }
    }

    //Metodos que sirve para vaciar la tabla que se encuentra en la pestaña "Eliminar";
    public void vaciarTablaEl() {
        int filas = jTableLibros2.getRowCount();
        for (int i = 0; i < filas; i++) {
            miModelo.removeRow(0);
        }
    }

    //Metodos que sirve para vaciar la tabla que se encuentra en la pestaña "Consultar";
    public void vaciarTablaLis() {
        int filas = jTableLibros3.getRowCount();
        for (int i = 0; i < filas; i++) {
            miModelo2.removeRow(0);
        }
    }

    //Metodo que sirva para mostrar un libro en particular ingresando el codigo en la pestaña "Consultar";
    public void consulta() {
        String cod = jTextFieldCon.getText();
        int a = Integer.parseInt(cod);
        if (biblioteca.numeroLibros() > 0) {
            if (a == 0) {
                mensaje("El codigo debe ser mayor a 0.");
            } else {
                if (biblioteca.buscaCodigo(a) != -1) {
                    if (biblioteca.getLibro(biblioteca.buscaCodigo(a)) instanceof LibroFisico == true) {
                        libroF = biblioteca.getLibroF(biblioteca.buscaCodigo(a));
                        String nombre = libroF.getNombre();
                        String autor = libroF.getAutor();
                        int añ = libroF.getAño();
                        String año = String.valueOf(añ);
                        int codig = libroF.getCodigo();
                        String codigo = String.valueOf(codig);
                        String mem = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
                        String pro = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
                        String can = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
                        String tot = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
                        jTextFieldConNom.setText(nombre);
                        jTextFieldConAut.setText(autor);
                        jTextFieldConAño.setText(año);
                        jTextFieldConCod.setText(codigo);
                        jTextFieldConMemNec.setText(mem);
                        jTextFieldConProNec.setText(pro);
                        jTextFieldConCanAud.setText(can);
                        jTextFieldConDurTot.setText(tot);
                    } else if (biblioteca.getLibro(biblioteca.buscaCodigo(a)) instanceof LibroDigital == true) {
                        libroD = biblioteca.getLibroD(biblioteca.buscaCodigo(a));
                        String nombre = libroD.getNombre();
                        String autor = libroD.getAutor();
                        int añ = libroD.getAño();
                        String año = String.valueOf(añ);
                        int codig = libroD.getCodigo();
                        String codigo = String.valueOf(codig);
                        String mem = String.valueOf(libroD.getMemoria());
                        String pro = libroD.getPrograma();
                        String programa = String.valueOf(pro);
                        String can = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
                        String tot = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
                        jTextFieldConNom.setText(nombre);
                        jTextFieldConAut.setText(autor);
                        jTextFieldConAño.setText(año);
                        jTextFieldConCod.setText(codigo);
                        jTextFieldConMemNec.setText(mem);
                        jTextFieldConProNec.setText(programa);
                        jTextFieldConCanAud.setText(can);
                        jTextFieldConDurTot.setText(tot);
                    } else if (biblioteca.getLibro(biblioteca.buscaCodigo(a)) instanceof LibroAudio == true) {
                        libroA = biblioteca.getLibroA(biblioteca.buscaCodigo(a));
                        String nombre = libroA.getNombre();
                        String autor = libroA.getAutor();
                        int añ = libroA.getAño();
                        String año = String.valueOf(añ);
                        int codig = libroA.getCodigo();
                        String codigo = String.valueOf(codig);
                        String mem = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
                        String pro = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
                        int can = libroA.getCantidad();
                        String cantidad = String.valueOf(can);
                        int tot = libroA.getTotal();
                        String total = String.valueOf(tot);
                        jTextFieldConNom.setText(nombre);
                        jTextFieldConAut.setText(autor);
                        jTextFieldConAño.setText(año);
                        jTextFieldConCod.setText(codigo);
                        jTextFieldConMemNec.setText(mem);
                        jTextFieldConProNec.setText(pro);
                        jTextFieldConCanAud.setText(cantidad);
                        jTextFieldConDurTot.setText(total);
                    }
                } else {
                    mensaje("El libro no se encuentra en la biblioteca");
                }
            }
        } else {
            mensaje("La biblioteca esta vacia.");
        }
        jTextFieldCon.requestFocus();
    }

    //Metodo para limpiar los jTextFields de la pestaña "Agregar";
    public void limpiarAgEntradasFi() {
        jTextFieldAgNomFi.setText("");
        jTextFieldAgAutFi.setText("");
        jTextFieldAgAñoFi.setText("");
        jTextFieldAgCodFi.setText("");
    }

    public void limpiarAgEntradasDi() {
        jTextFieldAgNomDi.setText("");
        jTextFieldAgAutDi.setText("");
        jTextFieldAgAñoDi.setText("");
        jTextFieldAgCodDi.setText("");
        jTextFieldAgMemNecDi.setText("");
        jTextFieldAgProNecDi.setText("");
    }

    public void limpiarAgEntradasAu() {
        jTextFieldAgNomAu.setText("");
        jTextFieldAgAutAu.setText("");
        jTextFieldAgAñoAu.setText("");
        jTextFieldAgCodAu.setText("");
        jTextFieldAgCanAudAu.setText("");
        jTextFieldAgDurTotAu.setText("");
    }

    //Metodo para limpiar los jTextField de la pestaña "Eliminar";
    public void limpiarElEntradas() {
        jTextFieldElNom.setText("");
        jTextFieldElAut.setText("");
        jTextFieldElAño.setText("");
        jTextFieldElCod.setText("");
        jTextFieldElMemNec.setText("");
        jTextFieldElProNec.setText("");
        jTextFieldElCanAud.setText("");
        jTextFieldElDurTot.setText("");
    }

    //Metodo para limpiar los jTextField de la pestaña "Consultar";
    public void limpiarConEntradas() {
        jTextFieldConNom.setText("");
        jTextFieldConAut.setText("");
        jTextFieldConAño.setText("");
        jTextFieldConCod.setText("");
        jTextFieldConMemNec.setText("");
        jTextFieldConProNec.setText("");
        jTextFieldConCanAud.setText("");
        jTextFieldConDurTot.setText("");
    }

    //Metodo para cargar las tablas de las pestañas "Agregar" y "Eliminar"
    //cuando se inicializa el gestor;
    public void cargarTabla() {
        if (biblioteca.numeroLibros() > 0) {
            biblioteca.sort(new ComparadorCodigo());
            for (int i = 0; i < biblioteca.numeroLibros(); i++) {
                if (biblioteca.getLibro(i) instanceof LibroFisico == true) {
                    libroF = biblioteca.getLibroF(i);
                    String[] Datos = new String[8];
                    Datos[0] = libroF.getNombre();
                    Datos[1] = libroF.getAutor();
                    Datos[2] = String.valueOf(libroF.getAño());
                    Datos[3] = String.valueOf(libroF.getCodigo());
                    Datos[4] = "--------------------";
                    Datos[5] = "--------------------";
                    Datos[6] = "--------------------";
                    Datos[7] = "--------------------";
                    miModelo.addRow(Datos);
                } else if (biblioteca.getLibro(i) instanceof LibroDigital == true) {
                    libroD = biblioteca.getLibroD(i);
                    String[] Datos = new String[8];
                    Datos[0] = libroD.getNombre();
                    Datos[1] = libroD.getAutor();
                    Datos[2] = String.valueOf(libroD.getAño());
                    Datos[3] = String.valueOf(libroD.getCodigo());
                    Datos[4] = String.valueOf(libroD.getMemoria());
                    Datos[5] = libroD.getPrograma();
                    Datos[6] = "--------------------";
                    Datos[7] = "--------------------";
                    miModelo.addRow(Datos);
                } else if (biblioteca.getLibro(i) instanceof LibroAudio == true) {
                    libroA = biblioteca.getLibroA(i);
                    String[] Datos = new String[8];
                    Datos[0] = libroA.getNombre();
                    Datos[1] = libroA.getAutor();
                    Datos[2] = String.valueOf(libroA.getAño());
                    Datos[3] = String.valueOf(libroA.getCodigo());
                    Datos[4] = "--------------------";
                    Datos[5] = "--------------------";
                    Datos[6] = String.valueOf(libroA.getCantidad());
                    Datos[7] = String.valueOf(libroA.getTotal());
                    miModelo.addRow(Datos);
                }
            }
        } else {
            mensaje("La biblioteca esta vacia.");
        }
    }

    //Metodo para listar por año los libros que se encuentran en el arreglo mostrandolos
    //en la tabla que esta en la pestaña "Listar libro por: ";
    public void ordenarPorAño() {
        if (biblioteca.numeroLibros() > 0) {
            biblioteca.sort(new ComparadorAño());
            for (int i = 0; i < biblioteca.numeroLibros(); i++) {
                if (biblioteca.getLibro(i) instanceof LibroFisico == true) {
                    libroF = biblioteca.getLibroF(i);
                    String[] Datos = new String[8];
                    Datos[0] = libroF.getNombre();
                    Datos[1] = libroF.getAutor();
                    Datos[2] = String.valueOf(libroF.getAño());
                    Datos[3] = String.valueOf(libroF.getCodigo());
                    Datos[4] = "--------------------";
                    Datos[5] = "--------------------";
                    Datos[6] = "--------------------";
                    Datos[7] = "--------------------";
                    miModelo2.addRow(Datos);
                } else if (biblioteca.getLibro(i) instanceof LibroDigital == true) {
                    libroD = biblioteca.getLibroD(i);
                    String[] Datos = new String[8];
                    Datos[0] = libroD.getNombre();
                    Datos[1] = libroD.getAutor();
                    Datos[2] = String.valueOf(libroD.getAño());
                    Datos[3] = String.valueOf(libroD.getCodigo());
                    Datos[4] = String.valueOf(libroD.getMemoria());
                    Datos[5] = libroD.getPrograma();
                    Datos[6] = "--------------------";
                    Datos[7] = "--------------------";
                    miModelo2.addRow(Datos);
                } else if (biblioteca.getLibro(i) instanceof LibroAudio == true) {
                    libroA = biblioteca.getLibroA(i);
                    String[] Datos = new String[8];
                    Datos[0] = libroA.getNombre();
                    Datos[1] = libroA.getAutor();
                    Datos[2] = String.valueOf(libroA.getAño());
                    Datos[3] = String.valueOf(libroA.getCodigo());
                    Datos[4] = "--------------------";
                    Datos[5] = "--------------------";
                    Datos[6] = String.valueOf(libroA.getCantidad());
                    Datos[7] = String.valueOf(libroA.getTotal());
                    miModelo2.addRow(Datos);
                }
            }
        } else {
            mensaje("La biblioteca esta vacia.");
        }
    }

    //Metodo para listar por nombre los libros que se encuentran en el arreglo mostrandolos
    //en la tabla que esta en la pestaña "Listar libro por: ";
    public void ordenarPorNombre() {
        if (biblioteca.numeroLibros() > 0) {
            biblioteca.sort(new ComparadorNombre());
            for (int i = 0; i < biblioteca.numeroLibros(); i++) {
                if (biblioteca.getLibro(i) instanceof LibroFisico == true) {
                    libroF = biblioteca.getLibroF(i);
                    String[] Datos = new String[8];
                    Datos[0] = libroF.getNombre();
                    Datos[1] = libroF.getAutor();
                    Datos[2] = String.valueOf(libroF.getAño());
                    Datos[3] = String.valueOf(libroF.getCodigo());
                    Datos[4] = "--------------------";
                    Datos[5] = "--------------------";
                    Datos[6] = "--------------------";
                    Datos[7] = "--------------------";
                    miModelo2.addRow(Datos);
                } else if (biblioteca.getLibro(i) instanceof LibroDigital == true) {
                    libroD = biblioteca.getLibroD(i);
                    String[] Datos = new String[8];
                    Datos[0] = libroD.getNombre();
                    Datos[1] = libroD.getAutor();
                    Datos[2] = String.valueOf(libroD.getAño());
                    Datos[3] = String.valueOf(libroD.getCodigo());
                    Datos[4] = String.valueOf(libroD.getMemoria());
                    Datos[5] = libroD.getPrograma();
                    Datos[6] = "--------------------";
                    Datos[7] = "--------------------";
                    miModelo2.addRow(Datos);
                } else if (biblioteca.getLibro(i) instanceof LibroAudio == true) {
                    libroA = biblioteca.getLibroA(i);
                    String[] Datos = new String[8];
                    Datos[0] = libroA.getNombre();
                    Datos[1] = libroA.getAutor();
                    Datos[2] = String.valueOf(libroA.getAño());
                    Datos[3] = String.valueOf(libroA.getCodigo());
                    Datos[4] = "--------------------";
                    Datos[5] = "--------------------";
                    Datos[6] = String.valueOf(libroA.getCantidad());
                    Datos[7] = String.valueOf(libroA.getTotal());
                    miModelo2.addRow(Datos);
                }
            }
        } else {
            mensaje("La biblioteca esta vacia.");
        }
    }

    //Metodo que busca por autor en la pestaña "Listar libro por: " que lista
    //los libros segun el autor que se ingresa en el jTextField
    public void buscarAutor() {
        String aut = jTextFieldLisAut.getText();
        if (biblioteca.buscaAutor(aut) == -1) {
            mensaje("No hay ningun libro de " + aut + " en la biblioteca.");
        } else {
            for (int i = 0; i < biblioteca.numeroLibros(); i++) {
                if (aut.equals(biblioteca.getLibro(i).getAutor()) == true) {
                    if (biblioteca.getLibro(i) instanceof LibroFisico == true) {
                        libroF = (LibroFisico) biblioteca.getLibroF(i);
                        String[] Datos = new String[8];
                        Datos[0] = libroF.getNombre();
                        Datos[1] = libroF.getAutor();
                        Datos[2] = String.valueOf(libroF.getAño());
                        Datos[3] = String.valueOf(libroF.getCodigo());
                        Datos[4] = "--------------------";
                        Datos[5] = "--------------------";
                        Datos[6] = "--------------------";
                        Datos[7] = "--------------------";
                        miModelo2.addRow(Datos);
                    } else if (biblioteca.getLibro(i) instanceof LibroDigital == true) {
                        libroD = (LibroDigital) biblioteca.getLibroD(i);
                        String[] Datos = new String[8];
                        Datos[0] = libroD.getNombre();
                        Datos[1] = libroD.getAutor();
                        Datos[2] = String.valueOf(libroD.getAño());
                        Datos[3] = String.valueOf(libroD.getCodigo());
                        Datos[4] = String.valueOf(libroD.getMemoria());
                        Datos[5] = libroD.getPrograma();
                        Datos[6] = "--------------------";
                        Datos[7] = "--------------------";
                        miModelo2.addRow(Datos);
                    } else if (biblioteca.getLibro(i) instanceof LibroAudio == true) {
                        libroA = (LibroAudio) biblioteca.getLibroA(i);
                        String[] Datos = new String[8];
                        Datos[0] = libroA.getNombre();
                        Datos[1] = libroA.getAutor();
                        Datos[2] = String.valueOf(libroA.getAño());
                        Datos[3] = String.valueOf(libroA.getCodigo());
                        Datos[4] = "--------------------";
                        Datos[5] = "--------------------";
                        Datos[6] = String.valueOf(libroA.getCantidad());
                        Datos[7] = String.valueOf(libroA.getTotal());
                        miModelo2.addRow(Datos);
                    }
                }
            }
        }
    }

    //metodo que es llamado por los diferentes disparadores de evento para
    //agregar libros fisicos tanto a la biblioteca como a la tabla;
    public void agregarLibroF() {
        String nombre = jTextFieldAgNomFi.getText();
        String autor = jTextFieldAgAutFi.getText();
        String añ = jTextFieldAgAñoFi.getText();
        String cod = jTextFieldAgCodFi.getText();
        int año = Integer.parseInt(añ);
        int codigo = Integer.parseInt(cod);
        if (0 <= año && año <= 2017) {
            if (codigo > 0) {
                if (biblioteca.buscaCodigo(codigo) != -1) {
                    mensaje("El codigo ya existe en la biblioteca.");
                } else {
                    insertarFi(nombre, autor, año, codigo);
                    vaciarTablaAg();
                    vaciarTablaEl();
                    cargarTabla();
                    Fichero.setArchivoBiblio(Fichero.guardarLibros(biblioteca));
                    mensaje("¡La biblioteca ha sido guardada con exito!");
                    limpiarAgEntradasFi();
                    jTextFieldAgNomFi.requestFocus();
                }
            } else {
                mensaje("El codigo debe ser mayor a 0.");
            }
        } else {
            mensaje("El año ingresado es incorrecto.");
        }
    }

    //metodo que es llamado por los diferentes disparadores de evento para
    //agregar libros digitales tanto a la biblioteca como a la tabla;
    public void agregarLibroD() {
        String nom = jTextFieldAgNomDi.getText();
        String aut = jTextFieldAgAutDi.getText();
        String años = jTextFieldAgAñoDi.getText();
        String cod = jTextFieldAgCodDi.getText();
        String mem = jTextFieldAgMemNecDi.getText();
        String prog = jTextFieldAgProNecDi.getText();
        int año = Integer.parseInt(años);
        int codi = Integer.parseInt(cod);
        int memo = Integer.parseInt(mem);
        if (0 <= año && año <= 2017) {
            if (codi > 0) {
                if (biblioteca.buscaCodigo(codi) != -1) {
                    mensaje("El codigo ya existe en la biblioteca.");
                } else {
                    insertarDi(nom, aut, año, codi, memo, prog);
                    vaciarTablaAg();
                    vaciarTablaEl();
                    cargarTabla();
                    Fichero.setArchivoBiblio(Fichero.guardarLibros(biblioteca));
                    mensaje("¡La biblioteca ha sido guardada con exito!");
                    limpiarAgEntradasDi();
                    jTextFieldAgNomDi.requestFocus();
                }
            } else {
                mensaje("El codigo debe ser mayor a 0.");
            }
        } else {
            mensaje("El año ingresado es incorrecto.");
        }
    }

    //metodo que es llamado por los diferentes disparadores de evento para
    //agregar audiolibros tanto a la biblioteca como a la tabla;
    public void agregarLibroA() {
        String nom = jTextFieldAgNomAu.getText();
        String aut = jTextFieldAgAutAu.getText();
        String cod = jTextFieldAgCodAu.getText();
        String años = jTextFieldAgAñoAu.getText();
        String can = jTextFieldAgCanAudAu.getText();
        String tot = jTextFieldAgDurTotAu.getText();
        int año = Integer.parseInt(años);
        int codi = Integer.parseInt(cod);
        int cantidad = Integer.parseInt(can);
        int tota = Integer.parseInt(tot);
        if (0 <= año && año <= 2017) {
            if (codi > 0) {
                if (biblioteca.buscaCodigo(codi) != -1) {
                    mensaje("El codigo ya existe en la biblioteca.");
                } else {
                    insertarAu(nom, aut, año, codi, cantidad, tota);
                    vaciarTablaAg();
                    vaciarTablaEl();
                    cargarTabla();
                    Fichero.setArchivoBiblio(Fichero.guardarLibros(biblioteca));
                    mensaje("¡La biblioteca ha sido guardada con exito!");
                    limpiarAgEntradasAu();
                    jTextFieldAgNomAu.requestFocus();
                }
            } else {
                mensaje("El codigo debe ser mayor a 0.");
            }
        } else {
            mensaje("El año ingresado es incorrecto.");
        }
    }

    //metodo que es llamado por los diferentes disparadores de evento para
    //listar en la tabla a los libros por autores;
    public void listarAutor() {
        if (jTextFieldLisAut.getText().trim().length() == 0) {
            mensaje("No ha ingresado ningún autor.");
        } else {
            vaciarTablaLis();
            buscarAutor();
            jTextFieldLisAut.setText("");
        }
        jTextFieldLisAut.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabelGesBiblio = new javax.swing.JLabel();
        jTabbedPaneIngresar = new javax.swing.JTabbedPane();
        jPanelAg = new javax.swing.JPanel();
        jTabbedPaneAgregar = new javax.swing.JTabbedPane();
        jPanelAgFi = new javax.swing.JPanel();
        jLabelAgNomFi = new javax.swing.JLabel();
        jLabelagAgAutFi = new javax.swing.JLabel();
        jLabelAgAñoFi = new javax.swing.JLabel();
        jLabelAgCodFi = new javax.swing.JLabel();
        jButtonAgLibFi = new javax.swing.JButton();
        jTextFieldAgNomFi = new javax.swing.JTextField();
        jTextFieldAgAutFi = new javax.swing.JTextField();
        jTextFieldAgAñoFi = new javax.swing.JTextField();
        jTextFieldAgCodFi = new javax.swing.JTextField();
        jPanelAgDi = new javax.swing.JPanel();
        jLabelAgNomDi = new javax.swing.JLabel();
        jLabelAgAutDi = new javax.swing.JLabel();
        jLabelAgAñoDi = new javax.swing.JLabel();
        jLabelAgCodDi = new javax.swing.JLabel();
        jLabelAgMemNecDi = new javax.swing.JLabel();
        jLabelAgProgNecDi = new javax.swing.JLabel();
        jTextFieldAgMemNecDi = new javax.swing.JTextField();
        jTextFieldAgCodDi = new javax.swing.JTextField();
        jTextFieldAgAñoDi = new javax.swing.JTextField();
        jTextFieldAgNomDi = new javax.swing.JTextField();
        jTextFieldAgAutDi = new javax.swing.JTextField();
        jTextFieldAgProNecDi = new javax.swing.JTextField();
        jButtonAgLibDi = new javax.swing.JButton();
        jPanelAgAu = new javax.swing.JPanel();
        jLabelAgNomAu = new javax.swing.JLabel();
        jLabelAgAutAu = new javax.swing.JLabel();
        jLabelAgAñoAu = new javax.swing.JLabel();
        jLabelAgCodAu = new javax.swing.JLabel();
        jLabelAgCanAudAu = new javax.swing.JLabel();
        jLabelAgDurTotAu = new javax.swing.JLabel();
        jButtonAgAudLi = new javax.swing.JButton();
        jTextFieldAgNomAu = new javax.swing.JTextField();
        jTextFieldAgAutAu = new javax.swing.JTextField();
        jTextFieldAgAñoAu = new javax.swing.JTextField();
        jTextFieldAgCodAu = new javax.swing.JTextField();
        jTextFieldAgDurTotAu = new javax.swing.JTextField();
        jTextFieldAgCanAudAu = new javax.swing.JTextField();
        jLabelTabLib = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableLibros1 = new javax.swing.JTable();
        jPanelEl = new javax.swing.JPanel();
        jLabelElCon = new javax.swing.JLabel();
        jLabelElNom = new javax.swing.JLabel();
        jLabelElAut = new javax.swing.JLabel();
        jLabelElAño = new javax.swing.JLabel();
        jLabelElCod = new javax.swing.JLabel();
        jLabelElMemNec = new javax.swing.JLabel();
        jLabelElProNec = new javax.swing.JLabel();
        jLabelElCanAud = new javax.swing.JLabel();
        jLabelElDurTot = new javax.swing.JLabel();
        jTextFieldElDurTot = new javax.swing.JTextField();
        jTextFieldElCanAud = new javax.swing.JTextField();
        jTextFieldElProNec = new javax.swing.JTextField();
        jTextFieldElMemNec = new javax.swing.JTextField();
        jTextFieldElCod = new javax.swing.JTextField();
        jTextFieldElAño = new javax.swing.JTextField();
        jTextFieldElAut = new javax.swing.JTextField();
        jTextFieldElNom = new javax.swing.JTextField();
        jButtonElBorrar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableLibros2 = new javax.swing.JTable();
        jPanelConsulta = new javax.swing.JPanel();
        jLabelCon = new javax.swing.JLabel();
        jTextFieldCon = new javax.swing.JTextField();
        jLabelConNom = new javax.swing.JLabel();
        jLabelConAut = new javax.swing.JLabel();
        jLabelConAño = new javax.swing.JLabel();
        jLabelConCod = new javax.swing.JLabel();
        jLabelConMemNec = new javax.swing.JLabel();
        jLabelConProNec = new javax.swing.JLabel();
        jLabelConCanAud = new javax.swing.JLabel();
        jLabelConDurTot = new javax.swing.JLabel();
        jTextFieldConNom = new javax.swing.JTextField();
        jTextFieldConAut = new javax.swing.JTextField();
        jTextFieldConAño = new javax.swing.JTextField();
        jTextFieldConCod = new javax.swing.JTextField();
        jTextFieldConMemNec = new javax.swing.JTextField();
        jTextFieldConProNec = new javax.swing.JTextField();
        jTextFieldConCanAud = new javax.swing.JTextField();
        jTextFieldConDurTot = new javax.swing.JTextField();
        jButtonCon = new javax.swing.JButton();
        jPanelListarLibro = new javax.swing.JPanel();
        jRadioButtonLisNom = new javax.swing.JRadioButton();
        jRadioButtonLisAño = new javax.swing.JRadioButton();
        jLabelLista = new javax.swing.JLabel();
        jScrollPaneLista = new javax.swing.JScrollPane();
        jTableLibros3 = new javax.swing.JTable();
        jLabelLisAut = new javax.swing.JLabel();
        jTextFieldLisAut = new javax.swing.JTextField();
        jButtonLisAut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestor de la Biblioteca");

        jLabelGesBiblio.setText("                                                                                                                                Gestor de la Biblioteca");

        jPanelAgFi.setPreferredSize(new java.awt.Dimension(1160, 255));

        jLabelAgNomFi.setText("Nombre:");

        jLabelagAgAutFi.setText("Autor:");

        jLabelAgAñoFi.setText("Año:");

        jLabelAgCodFi.setText("Codigo:");

        jButtonAgLibFi.setText("Agregar");
        jButtonAgLibFi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgLibFiActionPerformed(evt);
            }
        });
        jButtonAgLibFi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonAgLibFiKeyPressed(evt);
            }
        });

        jTextFieldAgNomFi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgNomFiKeyPressed(evt);
            }
        });

        jTextFieldAgAutFi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgAutFiKeyPressed(evt);
            }
        });

        jTextFieldAgAñoFi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgAñoFiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAgAñoFiKeyTyped(evt);
            }
        });

        jTextFieldAgCodFi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgCodFiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAgCodFiKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanelAgFiLayout = new javax.swing.GroupLayout(jPanelAgFi);
        jPanelAgFi.setLayout(jPanelAgFiLayout);
        jPanelAgFiLayout.setHorizontalGroup(
            jPanelAgFiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgFiLayout.createSequentialGroup()
                .addGroup(jPanelAgFiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAgAñoFi)
                    .addComponent(jLabelAgCodFi)
                    .addGroup(jPanelAgFiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabelAgNomFi, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabelagAgAutFi)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgFiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldAgNomFi)
                    .addComponent(jTextFieldAgAutFi)
                    .addComponent(jTextFieldAgAñoFi)
                    .addGroup(jPanelAgFiLayout.createSequentialGroup()
                        .addComponent(jButtonAgLibFi)
                        .addGap(0, 433, Short.MAX_VALUE))
                    .addComponent(jTextFieldAgCodFi)))
        );
        jPanelAgFiLayout.setVerticalGroup(
            jPanelAgFiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgFiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAgFiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAgNomFi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAgNomFi, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgFiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelagAgAutFi, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAgAutFi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgFiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAgAñoFi, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAgAñoFi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgFiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAgCodFi, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldAgCodFi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAgLibFi)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jTabbedPaneAgregar.addTab("Libro", jPanelAgFi);

        jLabelAgNomDi.setText("Nombre:");

        jLabelAgAutDi.setText("Autor:");

        jLabelAgAñoDi.setText("Año:");

        jLabelAgCodDi.setText("Codigo:");

        jLabelAgMemNecDi.setText("Mem. nec:");

        jLabelAgProgNecDi.setText("Prog. nec:");

        jTextFieldAgMemNecDi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgMemNecDiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAgMemNecDiKeyTyped(evt);
            }
        });

        jTextFieldAgCodDi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgCodDiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAgCodDiKeyTyped(evt);
            }
        });

        jTextFieldAgAñoDi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgAñoDiKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAgAñoDiKeyTyped(evt);
            }
        });

        jTextFieldAgNomDi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgNomDiKeyPressed(evt);
            }
        });

        jTextFieldAgAutDi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgAutDiKeyPressed(evt);
            }
        });

        jTextFieldAgProNecDi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgProNecDiKeyPressed(evt);
            }
        });

        jButtonAgLibDi.setText("Agregar");
        jButtonAgLibDi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonAgLibDiMouseClicked(evt);
            }
        });
        jButtonAgLibDi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgLibDiActionPerformed(evt);
            }
        });
        jButtonAgLibDi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonAgLibDiKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAgDiLayout = new javax.swing.GroupLayout(jPanelAgDi);
        jPanelAgDi.setLayout(jPanelAgDiLayout);
        jPanelAgDiLayout.setHorizontalGroup(
            jPanelAgDiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgDiLayout.createSequentialGroup()
                .addGroup(jPanelAgDiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAgNomDi)
                    .addComponent(jLabelAgAutDi)
                    .addComponent(jLabelAgAñoDi)
                    .addComponent(jLabelAgCodDi)
                    .addComponent(jLabelAgMemNecDi)
                    .addComponent(jLabelAgProgNecDi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgDiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgDiLayout.createSequentialGroup()
                        .addComponent(jButtonAgLibDi)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTextFieldAgAutDi)
                    .addComponent(jTextFieldAgAñoDi)
                    .addComponent(jTextFieldAgCodDi)
                    .addComponent(jTextFieldAgMemNecDi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                    .addComponent(jTextFieldAgProNecDi, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextFieldAgNomDi))
                .addContainerGap())
        );
        jPanelAgDiLayout.setVerticalGroup(
            jPanelAgDiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgDiLayout.createSequentialGroup()
                .addGroup(jPanelAgDiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldAgNomDi)
                    .addComponent(jLabelAgNomDi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgDiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelAgAutDi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldAgAutDi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgDiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelAgAñoDi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldAgAñoDi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgDiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldAgCodDi)
                    .addComponent(jLabelAgCodDi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgDiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldAgMemNecDi)
                    .addComponent(jLabelAgMemNecDi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgDiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelAgProgNecDi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldAgProNecDi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAgLibDi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneAgregar.addTab("Libro Digital", jPanelAgDi);

        jLabelAgNomAu.setText("Nombre:");

        jLabelAgAutAu.setText("Autor:");

        jLabelAgAñoAu.setText("Año:");

        jLabelAgCodAu.setText("Codigo:");

        jLabelAgCanAudAu.setText("Cant. Aud:");

        jLabelAgDurTotAu.setText("Dur. Total:");

        jButtonAgAudLi.setText("Agregar");
        jButtonAgAudLi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgAudLiActionPerformed(evt);
            }
        });
        jButtonAgAudLi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonAgAudLiKeyPressed(evt);
            }
        });

        jTextFieldAgNomAu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgNomAuKeyPressed(evt);
            }
        });

        jTextFieldAgAutAu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgAutAuKeyPressed(evt);
            }
        });

        jTextFieldAgAñoAu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgAñoAuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAgAñoAuKeyTyped(evt);
            }
        });

        jTextFieldAgCodAu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgCodAuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAgCodAuKeyTyped(evt);
            }
        });

        jTextFieldAgDurTotAu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgDurTotAuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAgDurTotAuKeyTyped(evt);
            }
        });

        jTextFieldAgCanAudAu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldAgCanAudAuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldAgCanAudAuKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanelAgAuLayout = new javax.swing.GroupLayout(jPanelAgAu);
        jPanelAgAu.setLayout(jPanelAgAuLayout);
        jPanelAgAuLayout.setHorizontalGroup(
            jPanelAgAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgAuLayout.createSequentialGroup()
                .addGroup(jPanelAgAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAgNomAu)
                    .addComponent(jLabelAgAutAu)
                    .addComponent(jLabelAgAñoAu)
                    .addComponent(jLabelAgCodAu)
                    .addComponent(jLabelAgCanAudAu)
                    .addComponent(jLabelAgDurTotAu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAgAuLayout.createSequentialGroup()
                        .addComponent(jButtonAgAudLi)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelAgAuLayout.createSequentialGroup()
                        .addGroup(jPanelAgAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldAgCanAudAu, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldAgCodAu, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldAgAñoAu, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldAgAutAu, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldAgDurTotAu, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                            .addComponent(jTextFieldAgNomAu, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(16, 16, 16))))
        );
        jPanelAgAuLayout.setVerticalGroup(
            jPanelAgAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgAuLayout.createSequentialGroup()
                .addGroup(jPanelAgAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldAgNomAu)
                    .addComponent(jLabelAgNomAu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelAgAutAu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldAgAutAu))
                .addGap(9, 9, 9)
                .addGroup(jPanelAgAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelAgAñoAu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldAgAñoAu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldAgCodAu)
                    .addComponent(jLabelAgCodAu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldAgCanAudAu)
                    .addComponent(jLabelAgCanAudAu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelAgAuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelAgDurTotAu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldAgDurTotAu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAgAudLi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneAgregar.addTab("Audiolibro", jPanelAgAu);

        jLabelTabLib.setText("          Tabla de Libros");

        jTableLibros1.setAutoCreateRowSorter(true);
        jTableLibros1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableLibros1.setEnabled(false);
        jTableLibros1.setFocusable(false);
        jTableLibros1.setRequestFocusEnabled(false);
        jTableLibros1.setRowSelectionAllowed(false);
        jTableLibros1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTableLibros1);

        javax.swing.GroupLayout jPanelAgLayout = new javax.swing.GroupLayout(jPanelAg);
        jPanelAg.setLayout(jPanelAgLayout);
        jPanelAgLayout.setHorizontalGroup(
            jPanelAgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelAgLayout.createSequentialGroup()
                .addContainerGap(252, Short.MAX_VALUE)
                .addComponent(jTabbedPaneAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(252, Short.MAX_VALUE))
            .addGroup(jPanelAgLayout.createSequentialGroup()
                .addGap(426, 426, 426)
                .addComponent(jLabelTabLib, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelAgLayout.setVerticalGroup(
            jPanelAgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAgLayout.createSequentialGroup()
                .addComponent(jTabbedPaneAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTabLib)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
        );

        jTabbedPaneIngresar.addTab("Agregar", jPanelAg);

        jLabelElCon.setText("Selecccione de la lista el libro que desea eliminar:");

        jLabelElNom.setText("Nombre:");

        jLabelElAut.setText("Autor:");

        jLabelElAño.setText("Año:");

        jLabelElCod.setText("Código:");

        jLabelElMemNec.setText("Mem.nec:");

        jLabelElProNec.setText("Prog. nec:");

        jLabelElCanAud.setText("Cant. aud:");

        jLabelElDurTot.setText("Dur. total:");

        jTextFieldElDurTot.setEditable(false);

        jTextFieldElCanAud.setEditable(false);

        jTextFieldElProNec.setEditable(false);

        jTextFieldElMemNec.setEditable(false);

        jTextFieldElCod.setEditable(false);

        jTextFieldElAño.setEditable(false);

        jTextFieldElAut.setEditable(false);

        jTextFieldElNom.setEditable(false);

        jButtonElBorrar.setText("Borrar");
        jButtonElBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonElBorrarActionPerformed(evt);
            }
        });

        jTableLibros2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int columnIndex){
                return false;
            }
        };
        jTableLibros2.setEditingColumn(-1);
        jTableLibros2.setEditingRow(-1);
        jTableLibros2.setFocusable(false);
        jTableLibros2.setOpaque(false);
        jTableLibros2.setRequestFocusEnabled(false);
        jTableLibros2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableLibros2.getTableHeader().setReorderingAllowed(false);
        jTableLibros2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableLibros2MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTableLibros2);
        jTableLibros2.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout jPanelElLayout = new javax.swing.GroupLayout(jPanelEl);
        jPanelEl.setLayout(jPanelElLayout);
        jPanelElLayout.setHorizontalGroup(
            jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1036, Short.MAX_VALUE)
            .addGroup(jPanelElLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelElLayout.createSequentialGroup()
                        .addGroup(jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelElNom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelElAut)
                            .addComponent(jLabelElAño, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldElNom)
                            .addComponent(jTextFieldElAut)
                            .addComponent(jTextFieldElAño)))
                    .addGroup(jPanelElLayout.createSequentialGroup()
                        .addComponent(jLabelElCon)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelElLayout.createSequentialGroup()
                        .addGroup(jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelElMemNec)
                            .addComponent(jLabelElCod)
                            .addComponent(jLabelElProNec)
                            .addComponent(jLabelElCanAud)
                            .addComponent(jLabelElDurTot))
                        .addGap(17, 17, 17)
                        .addGroup(jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelElLayout.createSequentialGroup()
                                .addComponent(jButtonElBorrar)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextFieldElDurTot)
                            .addComponent(jTextFieldElCanAud)
                            .addComponent(jTextFieldElMemNec)
                            .addComponent(jTextFieldElCod)
                            .addComponent(jTextFieldElProNec))))
                .addContainerGap())
        );
        jPanelElLayout.setVerticalGroup(
            jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelElLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabelElCon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldElNom)
                    .addComponent(jLabelElNom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldElAut)
                    .addComponent(jLabelElAut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldElAño)
                    .addComponent(jLabelElAño, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldElCod)
                    .addComponent(jLabelElCod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldElMemNec)
                    .addComponent(jLabelElMemNec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldElProNec)
                    .addComponent(jLabelElProNec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldElCanAud)
                    .addComponent(jLabelElCanAud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelElLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelElDurTot, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldElDurTot))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonElBorrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPaneIngresar.addTab("Eliminar", jPanelEl);

        jLabelCon.setText("Por favor ingrese el código del libro que desea consultar:");

        jTextFieldCon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldConKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldConKeyTyped(evt);
            }
        });

        jLabelConNom.setText("Nombre:");

        jLabelConAut.setText("Autor:");

        jLabelConAño.setText("Año:");

        jLabelConCod.setText("Codigo:");

        jLabelConMemNec.setText("Memoria:");

        jLabelConProNec.setText("Programa:");

        jLabelConCanAud.setText("Cantidad:");

        jLabelConDurTot.setText("Total:");

        jTextFieldConNom.setEditable(false);
        jTextFieldConNom.setFocusable(false);

        jTextFieldConAut.setEditable(false);

        jTextFieldConAño.setEditable(false);

        jTextFieldConCod.setEditable(false);

        jTextFieldConMemNec.setEditable(false);

        jTextFieldConProNec.setEditable(false);

        jTextFieldConCanAud.setEditable(false);

        jTextFieldConDurTot.setEditable(false);

        jButtonCon.setText("Consultar");
        jButtonCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConActionPerformed(evt);
            }
        });
        jButtonCon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonConKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanelConsultaLayout = new javax.swing.GroupLayout(jPanelConsulta);
        jPanelConsulta.setLayout(jPanelConsultaLayout);
        jPanelConsultaLayout.setHorizontalGroup(
            jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConsultaLayout.createSequentialGroup()
                .addGap(284, 284, 284)
                .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCon)
                    .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelConsultaLayout.createSequentialGroup()
                            .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelConDurTot)
                                .addComponent(jLabelConCanAud)
                                .addComponent(jLabelConProNec)
                                .addComponent(jLabelConCod))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextFieldConDurTot, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldConProNec, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelConsultaLayout.createSequentialGroup()
                                    .addComponent(jLabelConMemNec)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextFieldConMemNec, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelConsultaLayout.createSequentialGroup()
                                    .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelConNom)
                                        .addComponent(jLabelConAño)
                                        .addComponent(jLabelConAut)
                                        .addComponent(jTextFieldCon, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldConAut, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldConNom, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButtonCon)
                                        .addComponent(jTextFieldConAño, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextFieldConCod, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jTextFieldConCanAud, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(246, Short.MAX_VALUE))
        );
        jPanelConsultaLayout.setVerticalGroup(
            jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConsultaLayout.createSequentialGroup()
                .addComponent(jLabelCon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCon))
                .addGap(14, 14, 14)
                .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelConNom)
                    .addComponent(jTextFieldConNom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelConAut)
                    .addComponent(jTextFieldConAut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelConAño)
                    .addComponent(jTextFieldConAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelConCod)
                    .addComponent(jTextFieldConCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelConMemNec)
                    .addComponent(jTextFieldConMemNec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelConProNec)
                    .addComponent(jTextFieldConProNec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelConCanAud)
                    .addComponent(jTextFieldConCanAud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelConsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelConDurTot)
                    .addComponent(jTextFieldConDurTot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 157, Short.MAX_VALUE))
        );

        jTabbedPaneIngresar.addTab("Consultar", jPanelConsulta);

        buttonGroup1.add(jRadioButtonLisNom);
        jRadioButtonLisNom.setText("Nombre");
        jRadioButtonLisNom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButtonLisNomMouseClicked(evt);
            }
        });

        buttonGroup1.add(jRadioButtonLisAño);
        jRadioButtonLisAño.setText("Año de publicación");
        jRadioButtonLisAño.setToolTipText("");
        jRadioButtonLisAño.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButtonLisAñoMouseClicked(evt);
            }
        });

        jLabelLista.setText("Desea ordenar la lista por:");

        jTableLibros3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableLibros3.setToolTipText("");
        jTableLibros3.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTableLibros3.setEnabled(false);
        jTableLibros3.setFocusable(false);
        jTableLibros3.setRowSelectionAllowed(false);
        jTableLibros3.getTableHeader().setReorderingAllowed(false);
        jScrollPaneLista.setViewportView(jTableLibros3);

        jLabelLisAut.setText("Si desea buscar libros de un solo autor ingrese su nombre:");

        jTextFieldLisAut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldLisAutKeyPressed(evt);
            }
        });

        jButtonLisAut.setText("Buscar");
        jButtonLisAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLisAutActionPerformed(evt);
            }
        });
        jButtonLisAut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButtonLisAutKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanelListarLibroLayout = new javax.swing.GroupLayout(jPanelListarLibro);
        jPanelListarLibro.setLayout(jPanelListarLibroLayout);
        jPanelListarLibroLayout.setHorizontalGroup(
            jPanelListarLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneLista)
            .addGroup(jPanelListarLibroLayout.createSequentialGroup()
                .addGap(305, 305, 305)
                .addGroup(jPanelListarLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelListarLibroLayout.createSequentialGroup()
                        .addComponent(jLabelLista)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jRadioButtonLisAño)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonLisNom))
                    .addComponent(jLabelLisAut)
                    .addGroup(jPanelListarLibroLayout.createSequentialGroup()
                        .addComponent(jTextFieldLisAut, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonLisAut)))
                .addContainerGap(380, Short.MAX_VALUE))
        );
        jPanelListarLibroLayout.setVerticalGroup(
            jPanelListarLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListarLibroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelListarLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonLisNom)
                    .addComponent(jRadioButtonLisAño)
                    .addComponent(jLabelLista))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelLisAut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelListarLibroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLisAut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLisAut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneLista, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE))
        );

        jTabbedPaneIngresar.addTab("Listar libro por:", jPanelListarLibro);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelGesBiblio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPaneIngresar)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabelGesBiblio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPaneIngresar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonElBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonElBorrarActionPerformed
        //Evento de accion que al cliquear el boton "Eliminar", si cumple la condicion.
        //ejecuta el metodo "eliminar()";
        if (jTableLibros2.getSelectedRow() == -1) {
            mensaje("Seleccione algun libro para eliminar.");
        } else {
            eliminar();
        }
    }//GEN-LAST:event_jButtonElBorrarActionPerformed

    private void jButtonAgAudLiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgAudLiActionPerformed
        //Evento de accion que cliquear el boton "Agregar", si cumple la condicion
        //ejecuta el metodo "agregarLibroA()";
        if (jTextFieldAgNomAu.getText().trim().length() == 0 || jTextFieldAgAutAu.getText().trim().length() == 0 || jTextFieldAgAñoAu.getText().trim().length() == 0 || jTextFieldAgCodAu.getText().trim().length() == 0 || jTextFieldAgCanAudAu.getText().trim().length() == 0 || jTextFieldAgDurTotAu.getText().trim().length() == 0) {
            mensaje("Faltan rellenar campos.");
        } else {
            agregarLibroA();
        }
    }//GEN-LAST:event_jButtonAgAudLiActionPerformed

    private void jButtonAgLibDiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgLibDiActionPerformed
        //Evento de accion que cliquear el boton "Agregar", si cumple la condicion
        //ejecuta el metodo agregarLibroD();
        if (jTextFieldAgNomDi.getText().trim().length() == 0 || jTextFieldAgAutDi.getText().trim().length() == 0 || jTextFieldAgAñoDi.getText().trim().length() == 0 || jTextFieldAgCodDi.getText().trim().length() == 0 || jTextFieldAgMemNecDi.getText().trim().length() == 0 || jTextFieldAgProNecDi.getText().trim().length() == 0) {
            mensaje("Faltan rellenar campos.");
        } else {
            agregarLibroD();
        }
    }//GEN-LAST:event_jButtonAgLibDiActionPerformed

    private void jRadioButtonLisNomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButtonLisNomMouseClicked
        //Evento de boton de radio que al cliquearlo ordena a la tabla que esta 
        //en la pestaña "Listar libro por: " ordenarse segun los nombres que 
        //estan en la tabla y que tambien guarda en un txt la lista de la tabla 
        //segun el orden asignado;
        vaciarTablaLis();
        ordenarPorNombre();
        Fichero.setArchivoNom(Fichero.guardarListaNombre(biblioteca));
        jTextFieldLisAut.requestFocus();
    }//GEN-LAST:event_jRadioButtonLisNomMouseClicked

    private void jRadioButtonLisAñoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButtonLisAñoMouseClicked
        //Evento de boton de radio que al cliquearlo ordena a la tabla que esta 
        //en la pestaña "Listar libro por: " ordenarse segun los años que 
        //estan en la tabla y que tambien guarda en un txt la lista de la tabla 
        //segun el orden asignado;
        vaciarTablaLis();
        ordenarPorAño();
        Fichero.setArchivoAño(Fichero.guardarListaAño(biblioteca));
        jTextFieldLisAut.requestFocus();
    }//GEN-LAST:event_jRadioButtonLisAñoMouseClicked

    private void jButtonLisAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLisAutActionPerformed
        //Evento de accion que al presionar el boton "Buscar", si cumple la condicion
        // ejecuta el metodo "listarAutor()";
        if (jTextFieldLisAut.getText().trim().length() == 0) {
            mensaje("Ingrese un autor.");
        } else {
            listarAutor();
        }
    }//GEN-LAST:event_jButtonLisAutActionPerformed

    private void jTextFieldAgAñoDiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgAñoDiKeyTyped
        //Evento de tecla que impide ingresar otra cosa que no sean numeros,
        //la tecla borrar y la tecla eliminar y, que limita el ingreso de
        //caracteres a 4;
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
        String Caracteres = jTextFieldAgAñoDi.getText();
        if (Caracteres.length() >= 4) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldAgAñoDiKeyTyped

    private void jTextFieldAgCodDiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgCodDiKeyTyped
        //Evento de tecla que impide ingresar otra cosa que no sean numeros,
        //la tecla borrar y la tecla eliminar;
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldAgCodDiKeyTyped

    private void jTextFieldAgMemNecDiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgMemNecDiKeyTyped
        //Evento de tecla que impide ingresar otra cosa que no sean numeros,
        //la tecla borrar y la tecla eliminar;
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldAgMemNecDiKeyTyped

    private void jTextFieldAgAñoAuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgAñoAuKeyTyped
        //Evento de tecla que impide ingresar otra cosa que no sean numeros,
        //la tecla borrar y la tecla eliminar y, que limita el ingreso de
        //caracteres a 4;
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) || (c == evt.VK_BACK_SPACE) || (c == evt.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
        String Caracteres = jTextFieldAgAñoAu.getText();
        if (Caracteres.length() >= 4) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldAgAñoAuKeyTyped

    private void jTextFieldAgCodAuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgCodAuKeyTyped
        //Evento de tecla que impide ingresar otra cosa que no sean numeros,
        //la tecla borrar y la tecla eliminar;
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) || (c == evt.VK_BACK_SPACE) || (c == evt.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldAgCodAuKeyTyped

    private void jTextFieldAgCanAudAuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgCanAudAuKeyTyped
        //Evento de tecla que impide ingresar otra cosa que no sean numeros,
        //la tecla borrar y la tecla eliminar;
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) || (c == evt.VK_BACK_SPACE) || (c == evt.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldAgCanAudAuKeyTyped

    private void jTextFieldAgDurTotAuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgDurTotAuKeyTyped
        //Evento de tecla que impide ingresar otra cosa que no sean numeros,
        //la tecla borrar y la tecla eliminar;
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) || (c == evt.VK_BACK_SPACE) || (c == evt.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldAgDurTotAuKeyTyped

    private void jTextFieldConKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldConKeyTyped
        //Evento de tecla que impide ingresar otra cosa que no sean numeros,
        //la tecla borrar y la tecla eliminar;
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) || (c == evt.VK_BACK_SPACE) || (c == evt.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldConKeyTyped

    private void jTableLibros2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLibros2MouseClicked
        //Evento que me permite cliquear una fila de la tabla que esta
        //en la pestaña "Eliminar" y que setee sus datos en los jTextField
        //asignados para cada parametro, y que guarda el lugar que seleccione en
        //la variable "filas";
        int filasele = jTableLibros2.getSelectedRow();
        jTextFieldElNom.setText(jTableLibros2.getValueAt(filasele, 0).toString());
        jTextFieldElAut.setText(jTableLibros2.getValueAt(filasele, 1).toString());
        jTextFieldElAño.setText(jTableLibros2.getValueAt(filasele, 2).toString());
        jTextFieldElCod.setText(jTableLibros2.getValueAt(filasele, 3).toString());
        jTextFieldElMemNec.setText(jTableLibros2.getValueAt(filasele, 4).toString());
        jTextFieldElProNec.setText(jTableLibros2.getValueAt(filasele, 5).toString());
        jTextFieldElCanAud.setText(jTableLibros2.getValueAt(filasele, 6).toString());
        jTextFieldElDurTot.setText(jTableLibros2.getValueAt(filasele, 7).toString());
    }//GEN-LAST:event_jTableLibros2MouseClicked

    private void jTextFieldAgNomDiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgNomDiKeyPressed
        //Evento de tecla que al presionar "Enter", si cumple la condicion ejecuta el metodo
        //"agregarLibroD()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomDi.getText().trim().length() == 0 || jTextFieldAgAutDi.getText().trim().length() == 0 || jTextFieldAgAñoDi.getText().trim().length() == 0 || jTextFieldAgCodDi.getText().trim().length() == 0 || jTextFieldAgMemNecDi.getText().trim().length() == 0 || jTextFieldAgProNecDi.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroD();
            }
        }
    }//GEN-LAST:event_jTextFieldAgNomDiKeyPressed

    private void jTextFieldAgAutDiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgAutDiKeyPressed
        //Evento de tecla que al presionar "Enter", si cumple la condicion ejecuta el metodo
        //"agregarLibroD()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomDi.getText().trim().length() == 0 || jTextFieldAgAutDi.getText().trim().length() == 0 || jTextFieldAgAñoDi.getText().trim().length() == 0 || jTextFieldAgCodDi.getText().trim().length() == 0 || jTextFieldAgMemNecDi.getText().trim().length() == 0 || jTextFieldAgProNecDi.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroD();
            }
        }
    }//GEN-LAST:event_jTextFieldAgAutDiKeyPressed

    private void jTextFieldAgAñoDiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgAñoDiKeyPressed
        //Evento de tecla que al presionar "Enter", si cumple la condicion ejecuta el metodo
        //"agregarLibroD()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomDi.getText().trim().length() == 0 || jTextFieldAgAutDi.getText().trim().length() == 0 || jTextFieldAgAñoDi.getText().trim().length() == 0 || jTextFieldAgCodDi.getText().trim().length() == 0 || jTextFieldAgMemNecDi.getText().trim().length() == 0 || jTextFieldAgProNecDi.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroD();
            }
        }
    }//GEN-LAST:event_jTextFieldAgAñoDiKeyPressed

    private void jTextFieldAgCodDiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgCodDiKeyPressed
        //Evento de tecla que al presionar "Enter", si cumple la condicion ejecuta el metodo
        //"agregarLibroD()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomDi.getText().trim().length() == 0 || jTextFieldAgAutDi.getText().trim().length() == 0 || jTextFieldAgAñoDi.getText().trim().length() == 0 || jTextFieldAgCodDi.getText().trim().length() == 0 || jTextFieldAgMemNecDi.getText().trim().length() == 0 || jTextFieldAgProNecDi.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroD();
            }
        }
    }//GEN-LAST:event_jTextFieldAgCodDiKeyPressed

    private void jTextFieldAgMemNecDiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgMemNecDiKeyPressed
        //Evento de tecla que al presionar "Enter", si cumple la condicion ejecuta el metodo
        //"agregarLibroD()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomDi.getText().trim().length() == 0 || jTextFieldAgAutDi.getText().trim().length() == 0 || jTextFieldAgAñoDi.getText().trim().length() == 0 || jTextFieldAgCodDi.getText().trim().length() == 0 || jTextFieldAgMemNecDi.getText().trim().length() == 0 || jTextFieldAgProNecDi.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroD();
            }
        }
    }//GEN-LAST:event_jTextFieldAgMemNecDiKeyPressed

    private void jTextFieldAgProNecDiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgProNecDiKeyPressed
        //Evento de tecla que al presionar "Enter", si cumple la condicion ejecuta el metodo
        //"agregarLibroD()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomDi.getText().trim().length() == 0 || jTextFieldAgAutDi.getText().trim().length() == 0 || jTextFieldAgAñoDi.getText().trim().length() == 0 || jTextFieldAgCodDi.getText().trim().length() == 0 || jTextFieldAgMemNecDi.getText().trim().length() == 0 || jTextFieldAgProNecDi.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroD();
            }
        }
    }//GEN-LAST:event_jTextFieldAgProNecDiKeyPressed

    private void jButtonAgAudLiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonAgAudLiKeyPressed
        //Evento de tecla que al presionar "Enter", si cumple la condicion ejecutal el metodo
        //"agregarLibroA()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomAu.getText().trim().length() == 0 || jTextFieldAgAutAu.getText().trim().length() == 0 || jTextFieldAgAñoAu.getText().trim().length() == 0 || jTextFieldAgCodAu.getText().trim().length() == 0 || jTextFieldAgCanAudAu.getText().trim().length() == 0 || jTextFieldAgDurTotAu.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroA();
            }
        }
    }//GEN-LAST:event_jButtonAgAudLiKeyPressed

    private void jButtonAgLibDiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonAgLibDiKeyPressed
        // Evento de tecla que al presionar "Enter", si cumple la condicion ejecuta el metodo 
        //"agregarLibroD()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomDi.getText().trim().length() == 0 || jTextFieldAgAutDi.getText().trim().length() == 0 || jTextFieldAgAñoDi.getText().trim().length() == 0 || jTextFieldAgCodDi.getText().trim().length() == 0 || jTextFieldAgMemNecDi.getText().trim().length() == 0 || jTextFieldAgProNecDi.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroD();
            }
        }
    }//GEN-LAST:event_jButtonAgLibDiKeyPressed

    private void jButtonAgLibFiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonAgLibFiKeyPressed
        // Evento de tecla que al presionar "Enter", si cumple la condicion ejecuta el metodo 
        //"agregarLibroF()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomFi.getText().trim().length() == 0 || jTextFieldAgAutFi.getText().trim().length() == 0 || jTextFieldAgAñoFi.getText().trim().length() == 0 || jTextFieldAgCodFi.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroF();
            }
        }
    }//GEN-LAST:event_jButtonAgLibFiKeyPressed

    private void jButtonAgLibFiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgLibFiActionPerformed
        // Evento de accion que al presionar "Enter", si cumple la condicion ejecuta el metodo 
        //"agregarLibroF()";
        if (jTextFieldAgNomFi.getText().trim().length() == 0 || jTextFieldAgAutFi.getText().trim().length() == 0 || jTextFieldAgAñoFi.getText().trim().length() == 0 || jTextFieldAgCodFi.getText().trim().length() == 0) {
            mensaje("Faltan rellenar campos.");
        } else {
            agregarLibroF();
        }
    }//GEN-LAST:event_jButtonAgLibFiActionPerformed

    private void jTextFieldLisAutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldLisAutKeyPressed
        //Evento de tecla que al presionar "Enter" si cumple la condicion
        //se ejecuta el metodo "buscarAutor()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldLisAut.getText().trim().length() == 0) {
                mensaje("Ingrese un autor.");
            } else {
                vaciarTablaLis();
                buscarAutor();
                jTextFieldLisAut.setText("");
            }
            jTextFieldLisAut.requestFocus();
        }
    }//GEN-LAST:event_jTextFieldLisAutKeyPressed

    private void jButtonConKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonConKeyPressed
        //Evento de boton que al presionarlo si cumple la condicion
        //se ejecuta el metodo "consulta()";
        if (jTextFieldCon.getText().trim().length() == 0) {
            mensaje("Ingrese un codigo.");
        } else {
            consulta();
            jTextFieldCon.setText("");
            jTextFieldCon.requestFocus();
        }
    }//GEN-LAST:event_jButtonConKeyPressed

    private void jTextFieldConKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldConKeyPressed
        //Evento de tecla que al presionar "Enter" si se cumple la condicion
        //ejecuta el metodo "consulta()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String consultar = jTextFieldCon.getText().trim();
            if (consultar.length() == 0) {
                mensaje("Ingrese un codigo.");
            } else {
                consulta();
                jTextFieldCon.setText("");
                jTextFieldCon.requestFocus();
            }
        }
    }//GEN-LAST:event_jTextFieldConKeyPressed

    private void jTextFieldAgDurTotAuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgDurTotAuKeyPressed
        //Evento de tecla que al presionar "Enter" si se cumple la condicion se
        //ejecuta el metodo "agregarLibroA()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomAu.getText().trim().length() == 0 || jTextFieldAgAutAu.getText().trim().length() == 0 || jTextFieldAgAñoAu.getText().trim().length() == 0 || jTextFieldAgCodAu.getText().trim().length() == 0 || jTextFieldAgCanAudAu.getText().trim().length() == 0 || jTextFieldAgDurTotAu.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroA();
            }
        }
    }//GEN-LAST:event_jTextFieldAgDurTotAuKeyPressed

    private void jTextFieldAgCanAudAuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgCanAudAuKeyPressed
        //evento de tecla que al presionar "Enter" si se cumple la condicion se
        //ejecuta el metodo "agregarLibroA()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomAu.getText().trim().length() == 0 || jTextFieldAgAutAu.getText().trim().length() == 0 || jTextFieldAgAñoAu.getText().trim().length() == 0 || jTextFieldAgCodAu.getText().trim().length() == 0 || jTextFieldAgCanAudAu.getText().trim().length() == 0 || jTextFieldAgDurTotAu.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroA();
            }
        }
    }//GEN-LAST:event_jTextFieldAgCanAudAuKeyPressed

    private void jTextFieldAgCodAuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgCodAuKeyPressed
        //evento de tecla que al presionar "Enter" si se cumple la condicion se
        //ejecuta el metodo "agregarLibroA()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomAu.getText().trim().length() == 0 || jTextFieldAgAutAu.getText().trim().length() == 0 || jTextFieldAgAñoAu.getText().trim().length() == 0 || jTextFieldAgCodAu.getText().trim().length() == 0 || jTextFieldAgCanAudAu.getText().trim().length() == 0 || jTextFieldAgDurTotAu.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroA();
            }
        }
    }//GEN-LAST:event_jTextFieldAgCodAuKeyPressed

    private void jTextFieldAgAñoAuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgAñoAuKeyPressed
        //evento de tecla que al presionar "Enter" si se cumple la condicion se
        //ejecuta el metodo "agregarLibroA()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomAu.getText().trim().length() == 0 || jTextFieldAgAutAu.getText().trim().length() == 0 || jTextFieldAgAñoAu.getText().trim().length() == 0 || jTextFieldAgCodAu.getText().trim().length() == 0 || jTextFieldAgCanAudAu.getText().trim().length() == 0 || jTextFieldAgDurTotAu.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroA();
            }
        }
    }//GEN-LAST:event_jTextFieldAgAñoAuKeyPressed

    private void jTextFieldAgAutAuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgAutAuKeyPressed
        //evento de tecla que al presionar "Enter" si se cumple la condicion se
        //ejecuta el metodo "agregarLibroA()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomAu.getText().trim().length() == 0 || jTextFieldAgAutAu.getText().trim().length() == 0 || jTextFieldAgAñoAu.getText().trim().length() == 0 || jTextFieldAgCodAu.getText().trim().length() == 0 || jTextFieldAgCanAudAu.getText().trim().length() == 0 || jTextFieldAgDurTotAu.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroA();
            }
        }
    }//GEN-LAST:event_jTextFieldAgAutAuKeyPressed

    private void jTextFieldAgNomAuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgNomAuKeyPressed
        //evento de tecla que al presionar "Enter" si se cumple la condicion se
        //ejecuta el metodo "agregarLibroA()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomAu.getText().trim().length() == 0 || jTextFieldAgAutAu.getText().trim().length() == 0 || jTextFieldAgAñoAu.getText().trim().length() == 0 || jTextFieldAgCodAu.getText().trim().length() == 0 || jTextFieldAgCanAudAu.getText().trim().length() == 0 || jTextFieldAgDurTotAu.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroA();
            }
        }
    }//GEN-LAST:event_jTextFieldAgNomAuKeyPressed


    private void jTextFieldAgAutFiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgAutFiKeyPressed
        // Evento de accion que al presionar "Enter", si cumple la condicion ejecuta el metodo 
        //"agregarLibroF()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomFi.getText().trim().length() == 0 || jTextFieldAgAutFi.getText().trim().length() == 0 || jTextFieldAgAñoFi.getText().trim().length() == 0 || jTextFieldAgCodFi.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroF();
            }
        }
    }//GEN-LAST:event_jTextFieldAgAutFiKeyPressed

    private void jButtonAgLibDiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAgLibDiMouseClicked

    }//GEN-LAST:event_jButtonAgLibDiMouseClicked

    private void jButtonLisAutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonLisAutKeyPressed
        //Evento de boton que al presionar "Enter" si se cumple la condicion
        // se ejecuta el metodo "listarAutor()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldLisAut.getText().trim().length() == 0) {
                mensaje("Ingrese un autor.");
            } else {
                listarAutor();
            }
        }
    }//GEN-LAST:event_jButtonLisAutKeyPressed

    private void jButtonConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConActionPerformed
        //Evento de boton que al presionar "Enter" si se cumple la condicion
        // se ejecuta el metodo "consulta()";
        if (jTextFieldCon.getText().trim().length() == 0) {
            mensaje("Ingrese un codigo.");
        } else {
            consulta();
            jTextFieldCon.setText("");
            jTextFieldCon.requestFocus();
        }
    }//GEN-LAST:event_jButtonConActionPerformed

    private void jTextFieldAgAñoFiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgAñoFiKeyPressed
        // Evento de accion que al presionar "Enter", si cumple la condicion ejecuta el metodo 
        //"agregarLibroF()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomFi.getText().trim().length() == 0 || jTextFieldAgAutFi.getText().trim().length() == 0 || jTextFieldAgAñoFi.getText().trim().length() == 0 || jTextFieldAgCodFi.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroF();
            }
        }
    }//GEN-LAST:event_jTextFieldAgAñoFiKeyPressed

    private void jTextFieldAgAñoFiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgAñoFiKeyTyped
        //Evento de tecla que impide ingresar otra cosa que no sean numeros,
        //la tecla borrar y la tecla eliminar y, que limita el ingreso de
        //caracteres a 4;
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
        String Caracteres = jTextFieldAgAñoFi.getText();
        if (Caracteres.length() >= 4) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldAgAñoFiKeyTyped

    private void jTextFieldAgCodFiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgCodFiKeyPressed
        // Evento de accion que al presionar "Enter", si cumple la condicion ejecuta el metodo 
        //"agregarLibroF()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomFi.getText().trim().length() == 0 || jTextFieldAgAutFi.getText().trim().length() == 0 || jTextFieldAgAñoFi.getText().trim().length() == 0 || jTextFieldAgCodFi.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroF();
            }
        }
    }//GEN-LAST:event_jTextFieldAgCodFiKeyPressed

    private void jTextFieldAgCodFiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgCodFiKeyTyped
        //Evento de tecla que impide ingresar otra cosa que no sean numeros,
        //la tecla borrar y la tecla eliminar;
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c)) || (c == evt.VK_BACK_SPACE) || (c == evt.VK_DELETE)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldAgCodFiKeyTyped

    private void jTextFieldAgNomFiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAgNomFiKeyPressed
        // Evento de accion que al presionar "Enter", si cumple la condicion ejecuta el metodo 
        //"agregarLibroF()";
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (jTextFieldAgNomFi.getText().trim().length() == 0 || jTextFieldAgAutFi.getText().trim().length() == 0 || jTextFieldAgAñoFi.getText().trim().length() == 0 || jTextFieldAgCodFi.getText().trim().length() == 0) {
                mensaje("Faltan rellenar campos.");
            } else {
                agregarLibroF();
            }
        }
    }//GEN-LAST:event_jTextFieldAgNomFiKeyPressed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GestorGrafico.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestorGrafico.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestorGrafico.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestorGrafico.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestorGrafico().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButtonAgAudLi;
    private javax.swing.JButton jButtonAgLibDi;
    private javax.swing.JButton jButtonAgLibFi;
    private javax.swing.JButton jButtonCon;
    private javax.swing.JButton jButtonElBorrar;
    private javax.swing.JButton jButtonLisAut;
    private javax.swing.JLabel jLabelAgAutAu;
    private javax.swing.JLabel jLabelAgAutDi;
    private javax.swing.JLabel jLabelAgAñoAu;
    private javax.swing.JLabel jLabelAgAñoDi;
    private javax.swing.JLabel jLabelAgAñoFi;
    private javax.swing.JLabel jLabelAgCanAudAu;
    private javax.swing.JLabel jLabelAgCodAu;
    private javax.swing.JLabel jLabelAgCodDi;
    private javax.swing.JLabel jLabelAgCodFi;
    private javax.swing.JLabel jLabelAgDurTotAu;
    private javax.swing.JLabel jLabelAgMemNecDi;
    private javax.swing.JLabel jLabelAgNomAu;
    private javax.swing.JLabel jLabelAgNomDi;
    private javax.swing.JLabel jLabelAgNomFi;
    private javax.swing.JLabel jLabelAgProgNecDi;
    private javax.swing.JLabel jLabelCon;
    private javax.swing.JLabel jLabelConAut;
    private javax.swing.JLabel jLabelConAño;
    private javax.swing.JLabel jLabelConCanAud;
    private javax.swing.JLabel jLabelConCod;
    private javax.swing.JLabel jLabelConDurTot;
    private javax.swing.JLabel jLabelConMemNec;
    private javax.swing.JLabel jLabelConNom;
    private javax.swing.JLabel jLabelConProNec;
    private javax.swing.JLabel jLabelElAut;
    private javax.swing.JLabel jLabelElAño;
    private javax.swing.JLabel jLabelElCanAud;
    private javax.swing.JLabel jLabelElCod;
    private javax.swing.JLabel jLabelElCon;
    private javax.swing.JLabel jLabelElDurTot;
    private javax.swing.JLabel jLabelElMemNec;
    private javax.swing.JLabel jLabelElNom;
    private javax.swing.JLabel jLabelElProNec;
    private javax.swing.JLabel jLabelGesBiblio;
    private javax.swing.JLabel jLabelLisAut;
    private javax.swing.JLabel jLabelLista;
    private javax.swing.JLabel jLabelTabLib;
    private javax.swing.JLabel jLabelagAgAutFi;
    private javax.swing.JPanel jPanelAg;
    private javax.swing.JPanel jPanelAgAu;
    private javax.swing.JPanel jPanelAgDi;
    private javax.swing.JPanel jPanelAgFi;
    private javax.swing.JPanel jPanelConsulta;
    private javax.swing.JPanel jPanelEl;
    private javax.swing.JPanel jPanelListarLibro;
    private javax.swing.JRadioButton jRadioButtonLisAño;
    private javax.swing.JRadioButton jRadioButtonLisNom;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPaneLista;
    private javax.swing.JTabbedPane jTabbedPaneAgregar;
    private javax.swing.JTabbedPane jTabbedPaneIngresar;
    private javax.swing.JTable jTableLibros1;
    private javax.swing.JTable jTableLibros2;
    private javax.swing.JTable jTableLibros3;
    private javax.swing.JTextField jTextFieldAgAutAu;
    private javax.swing.JTextField jTextFieldAgAutDi;
    private javax.swing.JTextField jTextFieldAgAutFi;
    private javax.swing.JTextField jTextFieldAgAñoAu;
    private javax.swing.JTextField jTextFieldAgAñoDi;
    private javax.swing.JTextField jTextFieldAgAñoFi;
    private javax.swing.JTextField jTextFieldAgCanAudAu;
    private javax.swing.JTextField jTextFieldAgCodAu;
    private javax.swing.JTextField jTextFieldAgCodDi;
    private javax.swing.JTextField jTextFieldAgCodFi;
    private javax.swing.JTextField jTextFieldAgDurTotAu;
    private javax.swing.JTextField jTextFieldAgMemNecDi;
    private javax.swing.JTextField jTextFieldAgNomAu;
    private javax.swing.JTextField jTextFieldAgNomDi;
    private javax.swing.JTextField jTextFieldAgNomFi;
    private javax.swing.JTextField jTextFieldAgProNecDi;
    private javax.swing.JTextField jTextFieldCon;
    private javax.swing.JTextField jTextFieldConAut;
    private javax.swing.JTextField jTextFieldConAño;
    private javax.swing.JTextField jTextFieldConCanAud;
    private javax.swing.JTextField jTextFieldConCod;
    private javax.swing.JTextField jTextFieldConDurTot;
    private javax.swing.JTextField jTextFieldConMemNec;
    private javax.swing.JTextField jTextFieldConNom;
    private javax.swing.JTextField jTextFieldConProNec;
    private javax.swing.JTextField jTextFieldElAut;
    private javax.swing.JTextField jTextFieldElAño;
    private javax.swing.JTextField jTextFieldElCanAud;
    private javax.swing.JTextField jTextFieldElCod;
    private javax.swing.JTextField jTextFieldElDurTot;
    private javax.swing.JTextField jTextFieldElMemNec;
    private javax.swing.JTextField jTextFieldElNom;
    private javax.swing.JTextField jTextFieldElProNec;
    private javax.swing.JTextField jTextFieldLisAut;
    // End of variables declaration//GEN-END:variables
}

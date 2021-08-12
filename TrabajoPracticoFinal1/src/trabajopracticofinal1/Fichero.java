package trabajopracticofinal1;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//Clase abstracta que manejara los ficheros del programa;
public abstract class Fichero {

    //Atributo principal de la clase Fichero;
    private static File archivoBiblio;
    private static File archivoNom;
    private static File archivoAño;

    //Setter y Getter de la clase Fichero;
    public static File getArchivoBiblio() {
        return archivoBiblio;
    }

    public static void setArchivoBiblio(File archivoBiblio) {
        Fichero.archivoBiblio = archivoBiblio;
    }

    public static File getArchivoNom() {
        return archivoNom;
    }

    public static void setArchivoNom(File archivoNom) {
        Fichero.archivoNom = archivoNom;
    }

    public static File getArchivoAño() {
        return archivoAño;
    }

    public static void setArchivoAño(File archivoAño) {
        Fichero.archivoAño = archivoAño;
    }

    //Metodo que me permite tomar un archivo en formate txt y cargar el arreglo;
    public static ArrayList<Libro> cargarLibros() {
        ArrayList<Libro> arreglo = new ArrayList();
        BufferedReader br = null;
        String aux = "";
        try {
            br = new BufferedReader(new FileReader(archivoBiblio));
            while ((aux = br.readLine()) != null) {
                String[] atributos = aux.split("/");
                if (atributos.length == 5 || atributos.length == 7) {
                    switch (atributos[0]) {
                        case "Libro fisico":
                            LibroFisico libroF = new LibroFisico(atributos[1], atributos[2], Integer.parseInt(atributos[3]), Integer.parseInt(atributos[4]));
                            arreglo.add(libroF);
                            break;
                        case "Libro digital":
                            LibroDigital libroD = new LibroDigital(atributos[1], atributos[2], Integer.parseInt(atributos[3]), Integer.parseInt(atributos[4]), Integer.parseInt(atributos[5]), atributos[6]);
                            arreglo.add(libroD);
                            break;
                        case "Audiolibro":
                            LibroAudio libroA = new LibroAudio(atributos[1], atributos[2], Integer.parseInt(atributos[3]), Integer.parseInt(atributos[4]), Integer.parseInt(atributos[5]), Integer.parseInt(atributos[6]));
                            arreglo.add(libroA);
                            break;
                    }
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e1) { 
            e1.getMessage();
        }catch (NumberFormatException e2) {
            e2.getMessage();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e3) {
                    e3.getMessage();
                } catch (Exception e4) {
                    e4.getMessage();
                }
            }
        }
        return arreglo;
    }

    //Metodo que me permite guardar los libros ingresados desde el arreglo a un archivo de texto en formato txt;
    public static File guardarLibros(Biblioteca arreglo) {
        archivoBiblio.delete();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(archivoBiblio, true));
            for (int i = 0; i < arreglo.numeroLibros(); i++) {
                if (arreglo.getLibro(i) instanceof LibroFisico == true) {
                    LibroFisico libroF = (LibroFisico) arreglo.getLibroF(i);
                    String año = String.valueOf(libroF.getAño());
                    String codigo = String.valueOf(libroF.getCodigo());
                    String datos = "Libro fisico" + "/" + libroF.getNombre() + "/" + libroF.getAutor() + "/" + año + "/" + codigo;
                    bw.write(datos);
                    bw.newLine();
                } else if (arreglo.getLibro(i) instanceof LibroDigital == true) {
                    LibroDigital libroD = (LibroDigital) arreglo.getLibroD(i);
                    String año = String.valueOf(libroD.getAño());
                    String codigo = String.valueOf(libroD.getCodigo());
                    String memoria = String.valueOf(libroD.getMemoria());
                    String datos = "Libro digital" + "/" + libroD.getNombre() + "/" + libroD.getAutor() + "/" + año + "/" + codigo + "/" + memoria + "/" + libroD.getPrograma();
                    bw.write(datos);
                    bw.newLine();
                } else if (arreglo.getLibro(i) instanceof LibroAudio == true) {
                    LibroAudio libroA = (LibroAudio) arreglo.getLibroA(i);
                    String año = String.valueOf(libroA.getAño());
                    String codigo = String.valueOf(libroA.getCodigo());
                    String cantidad = String.valueOf(libroA.getCantidad());
                    String total = String.valueOf(libroA.getTotal());
                    String datos = "Audiolibro" + "/" + libroA.getNombre() + "/" + libroA.getAutor() + "/" + año + "/" + codigo + "/" + cantidad + "/" + total;
                    bw.write(datos);
                    bw.newLine();
                }
            }
            bw.close();
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e1) {
            e1.getMessage();
        } catch (Exception e2) {
            e2.getMessage();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e3) {
                    e3.getMessage();
                } catch (Exception e4) {
                    e4.getMessage();
                }
            }
        }
        return archivoBiblio;
    }

    //Metodo que sirve para guardar el arreglo listado por nombre en un archivo txt;
    public static File guardarListaNombre(Biblioteca arreglo) {
        archivoNom.delete();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(archivoNom, true));
            for (int i = 0; i < arreglo.numeroLibros(); i++) {
                if (arreglo.getLibro(i) instanceof LibroFisico == true) {
                    LibroFisico libroF = (LibroFisico) arreglo.getLibroF(i);
                    String año = String.valueOf(libroF.getAño());
                    String codigo = String.valueOf(libroF.getCodigo());
                    String datos = "Tipo de libro: " + libroF.getTipo() + " /" + " Nombre: " + libroF.getNombre() + " /" + " Autor: " + libroF.getAutor() + " /" + " Año: " + año + " /" + " Codigo: " + codigo + " /" + " Memoria: " + "-----------------------" + " /" + " Programa: " + "-----------------------" + " /" + " Cantidad: " + "----------------------------" + " /" + " Total: " + "----------------------------" + " /";
                    bw.write(datos);
                    bw.newLine();
                } else if (arreglo.getLibro(i) instanceof LibroDigital == true) {
                    LibroDigital libroD = (LibroDigital) arreglo.getLibroD(i);
                    String año = String.valueOf(libroD.getAño());
                    String codigo = String.valueOf(libroD.getCodigo());
                    String memoria = String.valueOf(libroD.getMemoria());
                    String datos = "Tipo de libro: " + libroD.getTipo() + " /" + " Nombre: " + libroD.getNombre() + " /" + " Autor: " + libroD.getAutor() + " /" + " Año: " + año + " /" + " Codigo: " + codigo + " /" + " Memoria: " + memoria + " /" + " Programa: " + libroD.getPrograma() + " /" + " Cantidad: " + "----------------------------" + " /" + " Total: " + "----------------------------" + " /";
                    bw.write(datos);
                    bw.newLine();
                } else if (arreglo.getLibro(i) instanceof LibroAudio == true) {
                    LibroAudio libroA = (LibroAudio) arreglo.getLibroA(i);
                    String año = String.valueOf(libroA.getAño());
                    String codigo = String.valueOf(libroA.getCodigo());
                    String cantidad = String.valueOf(libroA.getCantidad());
                    String total = String.valueOf(libroA.getTotal());
                    String datos = "Tipo de libro: " + libroA.getTipo() + " /" + " Nombre: " + libroA.getNombre() + " /" + " Autor: " + libroA.getAutor() + " /" + " Año: " + año + " /" + " Codigo: " + codigo + " /" + " Memoria: " + "-----------------------" + " /" + " Programa: " + "-----------------------" + " /" + " Cantidad: " + cantidad + " /" + " Total: " + total + " /";
                    bw.write(datos);
                    bw.newLine();
                }
            }
            bw.close();
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e1) {
            e1.getMessage();
        } catch (Exception e2) {
            e2.getMessage();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e3) {
                    e3.getMessage();
                } catch (Exception e4) {
                    e4.getMessage();
                }
            }
        }
        return archivoNom;
    }

    //Metodo que sirve para guardar el arreglo listado por año en un archivo txt;
    public static File guardarListaAño(Biblioteca arreglo) {
        archivoAño.delete();
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(archivoAño, true));
            for (int i = 0; i < arreglo.numeroLibros(); i++) {
                if (arreglo.getLibro(i) instanceof LibroFisico == true) {
                    LibroFisico libroF = (LibroFisico) arreglo.getLibroF(i);
                    String año = String.valueOf(libroF.getAño());
                    String codigo = String.valueOf(libroF.getCodigo());
                    String datos = "Tipo de libro: " + libroF.getTipo() + " /" + " Nombre: " + libroF.getNombre() + " /" + " Autor: " + libroF.getAutor() + " /" + " Año: " + año + " /" + " Codigo: " + codigo + " /" + " Memoria: " + "-----------------------" + " /" + " Programa: " + "-----------------------" + " /" + " Cantidad: " + "----------------------------" + " /" + " Total: " + "----------------------------" + " /";
                    bw.write(datos);
                    bw.newLine();
                } else if (arreglo.getLibro(i) instanceof LibroDigital == true) {
                    LibroDigital libroD = (LibroDigital) arreglo.getLibroD(i);
                    String año = String.valueOf(libroD.getAño());
                    String codigo = String.valueOf(libroD.getCodigo());
                    String memoria = String.valueOf(libroD.getMemoria());
                    String datos = "Tipo de libro: " + libroD.getTipo() + " /" + " Nombre: " + libroD.getNombre() + " /" + " Autor: " + libroD.getAutor() + " /" + " Año: " + año + " /" + " Codigo: " + codigo + " /" + " Memoria: " + memoria + " /" + " Programa: " + libroD.getPrograma() + " /" + " Cantidad: " + "----------------------------" + " /" + " Total: " + "----------------------------" + " /";
                    bw.write(datos);
                    bw.newLine();
                } else if (arreglo.getLibro(i) instanceof LibroAudio == true) {
                    LibroAudio libroA = (LibroAudio) arreglo.getLibroA(i);
                    String año = String.valueOf(libroA.getAño());
                    String codigo = String.valueOf(libroA.getCodigo());
                    String cantidad = String.valueOf(libroA.getCantidad());
                    String total = String.valueOf(libroA.getTotal());
                    String datos = "Tipo de libro: " + libroA.getTipo() + " /" + " Nombre: " + libroA.getNombre() + " /" + " Autor: " + libroA.getAutor() + " /" + " Año: " + año + " /" + " Codigo: " + codigo + " /" + " Memoria: " + "-----------------------" + " /" + " Programa: " + "-----------------------" + " /" + " Cantidad: " + cantidad + " /" + " Total: " + total + " /";
                    bw.write(datos);
                    bw.newLine();
                }
            }
            bw.close();
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e1) {
            e1.getMessage();
        } catch (Exception e2) {
            e2.getMessage();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e3) {
                    e3.getMessage();
                } catch (Exception e4) {
                    e4.getMessage();
                }
            }
        }
        return archivoAño;
    }
}
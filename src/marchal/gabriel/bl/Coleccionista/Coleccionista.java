package marchal.gabriel.bl.Coleccionista;

import marchal.gabriel.bl.Categoria.Categoria;
import marchal.gabriel.bl.ItemSubasta.ItemSubasta;
import marchal.gabriel.bl.Usuario.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;


/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-06-18
 *
 */


public class Coleccionista extends Usuario {
    private String direccion;
    private ArrayList<Categoria> intereses;
    private ArrayList<ItemSubasta> coleccion;
    private int calificacion;
    private boolean esModerador;


    /**
     * Constructor simple del objeto Coleccionista
     */
    public Coleccionista() {
    }

    /**
     * Constructor del objeto Coleccionista que toma:
     * @param nombre
     * @param identificacion
     * @param fechaNacimiento
     * @param correoElectronico
     * @param contrasena
     * @param direccion
     * @param intereses
     * @param estado
     * @param avatar
     * @param coleccion
     * @param calificacion
     * @param esModerador
     */

    public Coleccionista(String nombre, String identificacion, LocalDate fechaNacimiento, String correoElectronico, String contrasena, boolean estado, String avatar, String direccion, ArrayList<Categoria> intereses, ArrayList<ItemSubasta> coleccion, int calificacion, boolean esModerador) {
        super(nombre, identificacion, fechaNacimiento, correoElectronico, contrasena, estado, avatar);
        this.direccion = direccion;
        this.intereses = intereses;
        this.coleccion = coleccion;
        this.calificacion = calificacion;
        this.esModerador = esModerador;
    }


    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Categoria> getIntereses() {
        return intereses;
    }

    public void setIntereses(ArrayList<Categoria> intereses) {
        this.intereses = intereses;
    }

    public ArrayList<ItemSubasta> getColeccion() {
        return coleccion;
    }

    public void setColeccion(ArrayList<ItemSubasta> coleccion) {
        this.coleccion = coleccion;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public boolean isEsModerador() {
        return esModerador;
    }

    public void setEsModerador(boolean esModerador) {
        this.esModerador = esModerador;
    }

    @Override
    public String toString() {
        String esModeradorString;

        if(esModerador){
            esModeradorString = "Si";
        }else{
            esModeradorString = "No";
        }
        return  super.toString() +
                ", Calificacion: " + calificacion +
                ", esModerador: " + esModeradorString;
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDireccion(), getIntereses(), getColeccion(), getCalificacion(), isEsModerador());
    }
}

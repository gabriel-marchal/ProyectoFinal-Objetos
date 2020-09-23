package marchal.gabriel.bl.Vendedor;

import marchal.gabriel.bl.ItemSubasta.ItemSubasta;

import java.util.ArrayList;

/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-06-18
 *
 */


public class Vendedor{
    private String nombre;
    private String direccion;
    private String correoElectronico;
    private ArrayList<ItemSubasta> coleccion;

    /**
     * Constructor simple del objeto Vendedor
     */
    public Vendedor() {
    }

    /**
     * Constructor  del objeto Vendedor que toma:
     * @param nombre
     * @param direccion
     * @param correoElectronico
     * @param coleccion
     */
    public Vendedor(String nombre, String direccion, String correoElectronico, ArrayList<ItemSubasta> coleccion) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.correoElectronico = correoElectronico;
        this.coleccion = new ArrayList<>();
    }

    /**
     *
     * @return del objeto Vendedor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre del objeto Vendedor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return del objeto Vendedor
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     *
     * @param direccion del objeto Vendedor
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     *
     * @return del objeto Vendedor
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     *
     * @param correoElectronico del objeto Vendedor
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     *
     * @return del objeto Vendedor
     */
    public ArrayList<ItemSubasta> getColeccion() {
        return coleccion;
    }

    /**
     *
     * @param coleccion del objeto Vendedor
     */
    public void setColeccion(ArrayList<ItemSubasta> coleccion) {
        this.coleccion = coleccion;
    }

    /**
     *
     * @return toString del objeto Vendedor
     */
    @Override
    public String toString() {
        return "Vendedor{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", coleccion=" + coleccion +
                '}';
    }
}

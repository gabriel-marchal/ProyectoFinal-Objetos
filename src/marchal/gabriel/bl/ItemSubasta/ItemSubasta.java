package marchal.gabriel.bl.ItemSubasta;

import marchal.gabriel.bl.Usuario.Usuario;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.StrictMath.abs;

/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-06-18
 *
 */

public class ItemSubasta {
    private int id;
    private String nombre;
    private String descripcion;
    private String estado;
    private ArrayList<String> imagenes;
    private LocalDate fechaCompra;
    private int antiguedad;
    private String categoria;
    private Usuario vendedor;

    /**
     * Constructor simple que no toma parametros
     */
    public ItemSubasta() {
    }

    /**
     * Constructor de ItemSubasta que toma los siguientes parametros:
     * @param nombre
     * @param descripcion
     * @param estado
     * @param imagenes
     * @param fechaCompra
     * @param categoria
     */
    public ItemSubasta(String nombre, String descripcion, String estado, ArrayList<String> imagenes, LocalDate fechaCompra, String categoria, Usuario vendedor) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.imagenes = new ArrayList<>();
        this.fechaCompra = fechaCompra;
        this.antiguedad = getAntiguedad();
        this.categoria = categoria;
        this.vendedor = vendedor;
    }
    public ItemSubasta(int id, String nombre, String descripcion, String estado, ArrayList<String> imagenes, LocalDate fechaCompra, String categoria, Usuario vendedor) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.imagenes = new ArrayList<>();
        this.fechaCompra = fechaCompra;
        this.antiguedad = getAntiguedad();
        this.categoria = categoria;
        this.vendedor = vendedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getVendedor() {
        return vendedor;
    }


    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    /**
     *
     * @return nombre del ItemSubasta
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre del ItemSubasta
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return descripcion del ItemSubasta
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     *
     * @param descripcion del ItemSubasta
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     *
     * @return  estado del ItemSubasta
     */
    public String getEstado() {
        return estado;
    }

    /**
     *
     * @param estado del ItemSubasta
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     *
     * @return imagenes del ItemSubasta
     */
    public ArrayList<String> getImagenes() {
        return imagenes;
    }

    /**
     *
     * @param imagenes del ItemSubasta
     */
    public void setImagenes(ArrayList<String> imagenes) {
        this.imagenes = imagenes;
    }

    /**
     *
     * @return fechaCompra  del ItemSubasta
     */
    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    /**
     *
     * @param fechaCompra del ItemSubasta
     */
    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
        this.antiguedad = getAntiguedad();
    }

    /**
     *
     * @return antiguedad del ItemSubasta
     */
    public int getAntiguedad() {
        Period period = Period.between(LocalDate.now(), fechaCompra);
        return abs(period.getYears());
    }

    /**
     *
     * @return del ItemSubasta
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     *
     * @param categoria del ItemSubasta
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     *
     * @return el toString del objeto ItemSubastada
     */
    @Override
    public String toString() {
        return nombre + ", " + descripcion + ", " + estado + ", " + categoria;
    }
    public String toStringResumen(){
        return nombre+" "+categoria+ " "+estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemSubasta)) return false;
        ItemSubasta that = (ItemSubasta) o;
        return getId() == that.getId() &&
                Objects.equals(getNombre(), that.getNombre()) &&
                Objects.equals(getVendedor(), that.getVendedor());
    }

}

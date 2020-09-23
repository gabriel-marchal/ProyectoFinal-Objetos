package marchal.gabriel.bl.Subasta;

import marchal.gabriel.bl.Coleccionista.Coleccionista;
import marchal.gabriel.bl.ItemSubasta.ItemSubasta;
import marchal.gabriel.bl.Usuario.Usuario;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-06-18
 *
 */


public class Subasta {
    private int id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaFinalizacion;
    private String estado;
    private double precio;
    private double ofertaMinima;
    private ArrayList<ItemSubasta> itemesSubastadas;
    private int calificacion;
    private Usuario vendedor;
    private String tipoUsuario;
    private Coleccionista moderador;

    /**
     * Constructor simple del objeto Subasta
     */
    public Subasta() {
    }

    /**
     * Constructor del objeto Subasta que toma:
     * @param fechaInicio
     * @param fechaCreacion
     * @param fechaFinalizacion
     * @param estado
     * @param ofertaMinima
     * @param itemesSubastadas
     * @param calificacion
     * @param vendedor
     * @param tipoUsuario
     * @param moderador
     */
    public Subasta(int id,LocalDateTime fechaInicio, LocalDateTime fechaCreacion, LocalDateTime fechaFinalizacion, String estado, double ofertaMinima, ArrayList<ItemSubasta> itemesSubastadas, int calificacion, Usuario vendedor, String tipoUsuario, Coleccionista moderador, double precio) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaCreacion = fechaCreacion;
        this.fechaFinalizacion = fechaFinalizacion;
        this.estado = estado;
        this.ofertaMinima = ofertaMinima;
        this.itemesSubastadas = itemesSubastadas;
        this.calificacion = calificacion;
        this.vendedor = vendedor;
        this.tipoUsuario = tipoUsuario;
        this.moderador = moderador;
        this.precio = precio;
    }

    public Subasta(int id,LocalDateTime fechaInicio, LocalDateTime fechaCreacion, LocalDateTime fechaFinalizacion, String estado, double ofertaMinima, int calificacion, Usuario vendedor, String tipoUsuario, Coleccionista moderador) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaCreacion = fechaCreacion;
        this.fechaFinalizacion = fechaFinalizacion;
        this.estado = estado;
        this.ofertaMinima = ofertaMinima;
        this.itemesSubastadas = new ArrayList<>();
        this.calificacion = calificacion;
        this.vendedor = vendedor;
        this.tipoUsuario = tipoUsuario;
        this.moderador = moderador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     *
     * @return fechaInicio del objeto Subasta
     */
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    /**
     *
     * @param fechaInicio del objeto Subasta
     */
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     *
     * @return fechaCreacion del objeto Subasta
     */
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     *
     * @param fechaCreacion del objeto Subasta
     */
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     *
     * @return fechaFinalizacion del objeto Subasta
     */
    public LocalDateTime getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    /**
     *
     * @param fechaFinalizacion del objeto Subasta
     */
    public void setFechaFinalizacion(LocalDateTime fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    /**
     *
     * @return estado del objeto Subasta
     */
    public String getEstado() {
        return estado;
    }

    /**
     *
     * @param estado del objeto Subasta
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     *
     * @return ofertaMinima del objeto Subasta
     */
    public double getOfertaMinima() {
        return ofertaMinima;
    }

    /**
     *
     * @param ofertaMinima del objeto Subasta
     */
    public void setOfertaMinima(double ofertaMinima) {
        this.ofertaMinima = ofertaMinima;
    }

    /**
     *
     * @return itemesSubastadas del objeto Subasta
     */
    public ArrayList<ItemSubasta> getItemesSubastadas() {
        return itemesSubastadas;
    }

    /**
     *
     * @param itemesSubastadas del objeto Subasta
     */
    public void setItemesSubastadas(ArrayList<ItemSubasta> itemesSubastadas) {
        this.itemesSubastadas = itemesSubastadas;
    }

    /**
     *
     * @return calificacion del objeto Subasta
     */
    public int getCalificacion() {
        return calificacion;
    }

    /**
     *
     * @param calificacion del objeto Subasta
     */
    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    /**
     *
     * @return vendedor del objeto Subasta
     */
    public Usuario getVendedor() {
        return vendedor;
    }

    /**
     *
     * @param vendedor del objeto Subasta
     */
    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    /**
     *
     * @return tipoUsuario del objeto Subasta
     */
    public String getTipoUsuario() {
        return tipoUsuario;
    }

    /**
     *
     * @param tipoUsuario del objeto Subasta
     */
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    /**
     *
     * @return moderador del objeto Subasta
     */
    public Coleccionista getModerador() {
        return moderador;
    }

    /**
     *
     * @param moderador del objeto Subasta
     */
    public void setModerador(Coleccionista moderador) {
        this.moderador = moderador;
    }

    /**
     *
     * @return toString del objeto Subasta
     */
    @Override
    public String toString() {
        return "Subasta{" +
                "id: "+id +
                "fechaInicio=" + fechaInicio +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaFinalizacion=" + fechaFinalizacion +
                ", estado='" + estado + '\'' +
                ", ofertaMinima=" + ofertaMinima +
                ", itemesSubastadas=" + itemesSubastadas +
                ", calificacion=" + calificacion +
                ", vendedor=" + vendedor +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", moderador=" + moderador +
                ", precio = " + precio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subasta)) return false;
        Subasta subasta = (Subasta) o;
        return getId() == subasta.getId() &&
                getCalificacion() == subasta.getCalificacion() &&
                Objects.equals(getFechaInicio(), subasta.getFechaInicio()) &&
                Objects.equals(getFechaCreacion(), subasta.getFechaCreacion()) &&
                Objects.equals(getFechaFinalizacion(), subasta.getFechaFinalizacion()) &&
                Objects.equals(getItemesSubastadas(), subasta.getItemesSubastadas()) &&
                Objects.equals(getVendedor(), subasta.getVendedor());
    }

}

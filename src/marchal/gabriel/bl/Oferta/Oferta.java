package marchal.gabriel.bl.Oferta;

import marchal.gabriel.bl.Coleccionista.Coleccionista;
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


public class Oferta {
    private int idOferta;
    private Coleccionista comprador;
    private int idSubasta;
    private int calificacion;
    private double precioOferta;
    private LocalDateTime fechaOferta;
    private ArrayList<Coleccionista> usuariosOfertados;

    /**
     * Constructor simple que no toma parametros
     */
    public Oferta() {
    }

    /**
     * Constructor del objeto que toma los parametros:
     * @param comprador
     * @param precioOferta
     * @param fechaOferta
     */
    public Oferta(int idOferta, Coleccionista comprador,int idSubasta, double precioOferta, LocalDateTime fechaOferta) {
        this.idOferta = idOferta;
        this.comprador = comprador;
        this.idSubasta = idSubasta;
        this.calificacion = getCalificacion();
        this.precioOferta = precioOferta;
        this.fechaOferta = fechaOferta;
    }

    public int getIdOferta() {
        return idOferta;
    }

    public void setIdOferta(int idOferta) {
        this.idOferta = idOferta;
    }

    public int getIdSubasta() {
        return idSubasta;
    }

    public void setIdSubasta(int idSubasta) {
        this.idSubasta = idSubasta;
    }

    public ArrayList<Coleccionista> getUsuariosOfertados() {
        return usuariosOfertados;
    }

    public void setUsuariosOfertados(ArrayList<Coleccionista> usuariosOfertados) {
        this.usuariosOfertados = usuariosOfertados;
    }
    public void agregarOfertante(Coleccionista usuario){
        usuariosOfertados.add(usuario);
    }

    /**
     *
     * @return vendedor del objeto Oferta
     */
    public Coleccionista getComprador() {
        return comprador;
    }

    /**
     *
     * @param comprador del objeto Oferta
     */
    public void setComprador(Coleccionista comprador) {
        this.comprador = comprador;
    }

    /**
     *
     * @return calificacion del objeto Oferta
     */
    public int getCalificacion() {
        return comprador.getCalificacion();
    }

    /**
     *
     * @param
     */
    public void setCalificacion() {
        this.calificacion = comprador.getCalificacion();
    }

    /**
     *
     * @return precioOferta del objeto Oferta
     */
    public double getPrecioOferta() {
        return precioOferta;
    }

    /**
     *
     * @param precioOferta del objeto Oferta
     */
    public void setPrecioOferta(double precioOferta) {
        this.precioOferta = precioOferta;
    }

    /**
     *
     * @return fechaOferta del objeto Oferta
     */
    public LocalDateTime getFechaOferta() {
        return fechaOferta;
    }

    /**
     *
     * @param fechaOferta del objeto Oferta
     */
    public void setFechaOferta(LocalDateTime fechaOferta) {
        this.fechaOferta = fechaOferta;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "id="+idOferta+
                "comprador=" + comprador +
                ", idSubasta=" + idSubasta +
                ", calificacion=" + calificacion +
                ", precioOferta=" + precioOferta +
                ", fechaOferta=" + fechaOferta +
                ", usuariosOfertados=" + usuariosOfertados +
                '}';
    }

    /**
     *
     * @return el toString del objeto Oferta
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Oferta)) return false;
        Oferta oferta = (Oferta) o;
        return getIdSubasta() == oferta.getIdSubasta() &&
                getComprador().equals(oferta.getComprador());
    }
}

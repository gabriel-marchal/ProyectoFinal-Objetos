package marchal.gabriel.bl.OrdenDeCompra;

import marchal.gabriel.bl.ItemSubasta.ItemSubasta;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-06-18
 *
 */


public class OrdenDeCompra {
    private int idSubasta;
    private int idOrdenDeCompra;
    private String nombreGanador;
    private LocalDateTime fechaOrdenCompra;
    private ArrayList<ItemSubasta> itemes;
    private double precioTotal;
    private boolean esEntregado;

    /**
     * Constructor simple del objeto OrdenDeCompra
     */
    public OrdenDeCompra() {
    }

    /**
     * Constructor del objeto OrdenDeCompra que toma:
     * @param nombreGanador
     * @param fechaOrdenCompra
     * @param itemes
     * @param precioTotal
     * @param esEntregado
     */
    public OrdenDeCompra(int idSubasta, int idOrdenDeCompra, String nombreGanador, LocalDateTime fechaOrdenCompra, ArrayList<ItemSubasta> itemes, double precioTotal,boolean esEntregado) {
        this.idSubasta = idSubasta;
        this.idOrdenDeCompra = idOrdenDeCompra;
        this.nombreGanador = nombreGanador;
        this.fechaOrdenCompra = fechaOrdenCompra;
        this.itemes = itemes;
        this.precioTotal = precioTotal;
        this.esEntregado = esEntregado;
    }

    public OrdenDeCompra(int idSubasta,String nombreGanador, LocalDateTime fechaOrdenCompra, double precioTotal, boolean esEntregado) {
        this.idSubasta = idSubasta;
        this.nombreGanador = nombreGanador;
        this.fechaOrdenCompra = fechaOrdenCompra;
        this.itemes = new ArrayList<>();
        this.precioTotal = precioTotal;
        this.esEntregado = esEntregado;
    }

    public int getIdSubasta() {
        return idSubasta;
    }

    public void setIdSubasta(int idSubasta) {
        this.idSubasta = idSubasta;
    }

    public int getIdOrdenDeCompra() {
        return idOrdenDeCompra;
    }

    public void setIdOrdenDeCompra(int idOrdenDeCompra) {
        this.idOrdenDeCompra = idOrdenDeCompra;
    }

    public boolean isEsEntregado() {
        return esEntregado;
    }

    /**
     *
     * @return nombreGanador del objeto OrdenDeCompra
     */
    public String getNombreGanador() {
        return nombreGanador;
    }

    /**
     *
     * @param nombreGanador del objeto OrdenDeCompra
     */
    public void setNombreGanador(String nombreGanador) {
        this.nombreGanador = nombreGanador;
    }

    /**
     *
     * @return fechaOrdenCompra del objeto OrdenDeCompra
     */
    public LocalDateTime getFechaOrdenCompra() {
        return fechaOrdenCompra;
    }

    /**
     *
     * @param fechaOrdenCompra del objeto OrdenDeCompra
     */
    public void setFechaOrdenCompra(LocalDateTime fechaOrdenCompra) {
        this.fechaOrdenCompra = fechaOrdenCompra;
    }

    /**
     *
     * @return itemes del objeto OrdenDeCompra
     */
    public ArrayList<ItemSubasta> getItemes() {
        return itemes;
    }

    /**
     *
     * @param itemes del objeto OrdenDeCompra
     */
    public void setItemes(ArrayList<ItemSubasta> itemes) {
        this.itemes = itemes;
    }

    /**
     *
     * @return precioTotal del objeto OrdenDeCompra
     */
    public double getPrecioTotal() {
        return precioTotal;
    }

    /**
     *
     * @param precioTotal
     */
    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    /**
     *
     * @return esEntregado del objeto OrdenDeCompra
     */
    public boolean getEsEntregado() {
        return esEntregado;
    }

    /**
     *
     * @param esEntregado del objeto OrdenDeCompra
     */
    public void setEsEntregado(boolean esEntregado) {
        this.esEntregado = esEntregado;
    }

    /**
     *
     * @return toString del objeto OrdenDeCompra
     */
    @Override
    public String toString() {
        String msg = "OrdenDeCompra\n" +
                "id: "+idOrdenDeCompra +"\n"+
                "idSubasta: "+idSubasta +"\n"+
                "Ganador: " + nombreGanador + "\n" +
                "Fecha: " + fechaOrdenCompra +"\n" +
                "Itemes: ";
        for (ItemSubasta tempItem :itemes ) {
            msg += tempItem.toString() +"\n";
        }
        msg +="Total: " + precioTotal;

        return msg;
    }
    public String toStringEmail(){
        String msg = "OrdenDeCompra<br>" +
                "id: "+idOrdenDeCompra +"<br>"+
                "idSubasta: "+idSubasta +"<br>"+
                "Ganador: " + nombreGanador + "<br>" +
                "Fecha: " + fechaOrdenCompra.toString() +"<br>" +
                "Itemes: <br>";
        msg+="------------------------------<br>";
        for (ItemSubasta tempItem :itemes ) {
            msg += tempItem.toString() +"<br>";
            msg+="------------------------------<br>";
        }
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(2);

        msg +="<p><b>Precio Total: " +df.format(precioTotal)+"</b></p>";

        return msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrdenDeCompra)) return false;
        OrdenDeCompra that = (OrdenDeCompra) o;
        return  getNombreGanador().equals(that.getNombreGanador()) &&
                getFechaOrdenCompra().equals(that.getFechaOrdenCompra()) &&
                getItemes().equals(that.getItemes());
    }

}

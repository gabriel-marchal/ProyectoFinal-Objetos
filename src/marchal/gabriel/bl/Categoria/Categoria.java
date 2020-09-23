package marchal.gabriel.bl.Categoria;

import java.util.Objects;

/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-06-18
 *
 */


public class Categoria {
    private int codigo;
    private String nombre;
    private String descripcion;

    /**
     * Constructor simple del objeto Categoria
     */
    public Categoria() {
    }

    public Categoria(int codigo, String nombre, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo +", "+ nombre + ", Descripcion: " + descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        Categoria categoria = (Categoria) o;
        return getCodigo() == categoria.getCodigo() &&
                getNombre().equals(categoria.getNombre());
    }

}

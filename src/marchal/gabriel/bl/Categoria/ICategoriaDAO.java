package marchal.gabriel.bl.Categoria;

import marchal.gabriel.bl.Usuario.Usuario;

import java.util.ArrayList;

public interface ICategoriaDAO {
    public int getNextCodigoCategoria()  throws Exception;
    public void registrarCategoria(Categoria categoria) throws Exception;
    public ArrayList<Categoria> retornarCategorias() throws Exception;
    public Categoria retornarCategoriaEspecifica(String nombre) throws Exception;
    public void actualizarCategoria(Categoria categoria, String descripcion) throws Exception;
    public void eliminarCategoria(Categoria categoria) throws Exception;

    public ArrayList<Categoria> retornarMisIntereses(Usuario usuario) throws Exception;

}

package marchal.gabriel.bl.Coleccionista;

import marchal.gabriel.bl.Categoria.Categoria;
import marchal.gabriel.bl.Usuario.Usuario;

import java.util.ArrayList;

public interface IColeccionistaDAO {
    public ArrayList<Usuario> retorarTodosColeccionistas() throws Exception;
    public void registrarColeccionista(Coleccionista coleccionista) throws Exception;
    public ArrayList<Usuario> getModeradores() throws Exception;
    public void editarInfoColeccionista(Coleccionista coleccionista) throws Exception;

    public void registrarMisIntereses(Usuario usuario, Categoria categoria) throws Exception;
    public void eliminarMisIntereses(Usuario usuario);

}

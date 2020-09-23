package marchal.gabriel.bl.Usuario;

import java.util.ArrayList;

public interface IUsuarioDAO {

    public Usuario retornarUsuario(String email) throws Exception;
    public int editarPerfilUsuario(Usuario usuario) throws Exception;
    public void cambiarContrasena(Usuario usuario, String nuevaContrasena) throws Exception;

}

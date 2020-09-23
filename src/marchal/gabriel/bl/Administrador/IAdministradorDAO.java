package marchal.gabriel.bl.Administrador;

import marchal.gabriel.bl.Usuario.Usuario;

import java.util.ArrayList;

public interface IAdministradorDAO {

    public boolean eliminarAdministrador(Administrador administrador) throws Exception;
    public boolean modificarAdministrador(Administrador administrador) throws Exception;
    public ArrayList<Administrador> obtenerAdministradores() throws Exception;
    public ArrayList<Administrador> obtenerAdministradorEspecifico(String correo) throws Exception;
    public Usuario retornarAdminEspecifico(String email) throws Exception;
    public ArrayList<Usuario> retornarTodosAdmin() throws Exception;
    public void registrarAdministrador(Administrador administrador) throws Exception;

}

package marchal.gabriel.bl.Administrador;

import marchal.gabriel.bl.Usuario.Usuario;
import marchal.gabriel.dl.Conector;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-07-31
 *
 */

public class MySqlAdministrador implements IAdministradorDAO {
    @Override
    public boolean eliminarAdministrador(Administrador administrador) throws Exception {
        return false;
    }

    @Override
    public boolean modificarAdministrador(Administrador administrador) throws Exception {
        return false;
    }

    @Override
    public ArrayList<Administrador> obtenerAdministradores() throws Exception {
        return null;
    }

    @Override
    public ArrayList<Administrador> obtenerAdministradorEspecifico(String correo) throws Exception {
        return null;
    }

    @Override
    public Usuario retornarAdminEspecifico(String email) {
        System.out.print("CALL BD retornarAdminEspecifico");
        String query = "SELECT * FROM collectorsbazar.administradores WHERE correoelectronico = '"+email+"';";
        Usuario usuario = null;
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while(rs.next()){
                Administrador administrador = new Administrador();
                administrador.setIdentificacion(rs.getString("identificacion"));
                administrador.setNombre(rs.getString("nombre"));
                administrador.setCorreoElectronico(rs.getString("correoelectronico"));
                administrador.setContrasena(rs.getString("contrasena"));
                administrador.setFechaNacimiento( LocalDate.parse(rs.getDate("fechanacimiento").toString()));
                administrador.setEstado(rs.getBoolean("estado"));
                administrador.setAvatar(rs.getString("avatar"));
                usuario = administrador;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return usuario;
    }

    @Override
    public ArrayList<Usuario> retornarTodosAdmin() {
        System.out.println("CALL BD retornarTodosAdmin");
        String query = "SELECT * FROM collectorsbazar.administradores;";
        ArrayList<Usuario> administradores = new ArrayList<>();
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                Administrador admin = new Administrador();
                admin.setIdentificacion(rs.getString("identificacion"));
                admin.setNombre(rs.getString("nombre"));
                admin.setCorreoElectronico(rs.getString("correoelectronico"));
                admin.setContrasena(rs.getString("contrasena"));
                admin.setFechaNacimiento(LocalDate.parse(rs.getDate("fechanacimiento").toString()));
                admin.setEstado(rs.getBoolean("estado"));
                admin.setAvatar(rs.getString("avatar"));
                administradores.add(admin);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return administradores;
    }

    @Override
    public void registrarAdministrador(Administrador administrador) {
        System.out.println("CALL BD registrarAdministrador");
        String query = "INSERT INTO collectorsbazar.administradores VALUES ('"+administrador.getIdentificacion()+"','"+administrador.getNombre()+
                "','"+administrador.getCorreoElectronico()+"','"+administrador.getContrasena()+"','"+administrador.getFechaNacimiento().toString()+
                "',"+administrador.isEstado()+",'"+administrador.getAvatar()+"');";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

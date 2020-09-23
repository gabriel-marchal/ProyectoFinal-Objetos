package marchal.gabriel.bl.Usuario;

import marchal.gabriel.bl.Administrador.Administrador;
import marchal.gabriel.bl.Coleccionista.Coleccionista;
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

public class MySqlUsuario implements IUsuarioDAO{
    @Override
    public Usuario retornarUsuario(String email) throws Exception {
        System.out.println("CALL BD retornarUsuario");
        String queryColeccionista = "SELECT * FROM collectorsbazar.coleccionistas WHERE correoelectronico = '"+email+"' ;";
        String queryAdministrador ="SELECT * FROM collectorsbazar.administradores WHERE correoelectronico = '"+email+"';";
        Usuario usuario = null;
        ResultSet rs = Conector.getConnector().ejecutarSELECT(queryColeccionista);
        if(rs.next()){
            Coleccionista coleccionista = new Coleccionista();
            coleccionista.setIdentificacion(rs.getString("identificacion"));
            coleccionista.setNombre(rs.getString("nombre"));
            coleccionista.setCorreoElectronico(rs.getString("correoelectronico"));
            coleccionista.setContrasena(rs.getString("contrasena"));
            coleccionista.setFechaNacimiento(LocalDate.parse(rs.getDate("fechanacimiento").toString()));
            coleccionista.setEstado(rs.getBoolean("estado"));
            coleccionista.setAvatar(rs.getString("avatar"));
            coleccionista.setDireccion(rs.getString("direccion"));
            coleccionista.setCalificacion(rs.getInt("calificacion"));
            coleccionista.setEsModerador(rs.getBoolean("esmoderador"));
            usuario = coleccionista;
        }else{
            rs = Conector.getConnector().ejecutarSELECT(queryAdministrador);
            if(rs.next()){
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
        }
        return usuario;
    }

    @Override
    public int editarPerfilUsuario(Usuario usuario) {
        System.out.println("CALL BD editarPerfilUsuario");
        String query;
        if(usuario instanceof Coleccionista){
            query = "UPDATE collectorsbazar.coleccionistas ";
            query += "SET direccion = '"+((Coleccionista) usuario).getDireccion()+"', ";
            query+= " esmoderador= "+((Coleccionista) usuario).isEsModerador()+", ";
        }else{
            query = "UPDATE collectorsbazar.administradores ";
            query +="SET ";
        }
        query += " estado= "+usuario.isEstado()+", ";
        query+=" avatar = '"+usuario.getAvatar()+"', ";
        query +="identificacion = '"+usuario.getIdentificacion()+"', ";
        query +=" nombre = '"+usuario.getNombre()+"', ";
        query += "fechanacimiento = '"+usuario.getFechaNacimiento().toString()+"' ";
        query +=" WHERE correoelectronico = '"+usuario.getCorreoElectronico()+"';";

        int exitoso;
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
            exitoso = 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            exitoso = 0;
        }

        return exitoso;
    }

    @Override
    public void cambiarContrasena(Usuario usuario, String nuevaContrasena) {
        System.out.println("CALL BD cambiarContrasena");
        String query;
        if(usuario instanceof Administrador){
            query = "UPDATE collectorsbazar.administradores ";
        }else{
            query = "UPDATE collectorsbazar.coleccionistas ";
        }
        query += "SET contrasena = '"+nuevaContrasena+"' ";
        query+= "WHERE correoelectronico = '"+usuario.getCorreoElectronico()+"';";

        try {
           Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

package marchal.gabriel.bl.Coleccionista;

import marchal.gabriel.bl.Categoria.Categoria;
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

public class MySqlColeccionista implements IColeccionistaDAO {
    @Override
    public ArrayList<Usuario> retorarTodosColeccionistas()  throws Exception{
        System.out.println("CALL BD retorarTodosColeccionistas");
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String query = "SELECT * FROM collectorsbazar.coleccionistas;";

        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
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
                usuarios.add(coleccionista);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return usuarios;
    }

    @Override
    public void registrarColeccionista(Coleccionista coleccionista)  throws Exception{
        System.out.println("CALL BD registrarColeccionista");
        String query = "INSERT INTO collectorsbazar.coleccionistas VALUES ('"+coleccionista.getIdentificacion()+"','"+coleccionista.getNombre()+
                "','"+coleccionista.getCorreoElectronico()+"','"+coleccionista.getContrasena()+"','"+coleccionista.getFechaNacimiento().toString()+
                "',"+coleccionista.isEstado()+",'"+coleccionista.getAvatar()+"','"+coleccionista.getDireccion()+"',"+coleccionista.getCalificacion()+
                ","+coleccionista.isEsModerador()+");";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Usuario> getModeradores() throws Exception {
        System.out.println("CALL BD getModeradores");
        ArrayList<Usuario> moderadores = new ArrayList<>();
        String query = "SELECT * FROM collectorsbazar.coleccionistas WHERE esmoderador = 1 ;";
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while(rs.next()){
                Coleccionista moderador = new Coleccionista();
                moderador.setIdentificacion(rs.getString("identificacion"));
                moderador.setNombre(rs.getString("nombre"));
                moderador.setCorreoElectronico(rs.getString("correoelectronico"));
                moderador.setContrasena(rs.getString("contrasena"));
                moderador.setFechaNacimiento(LocalDate.parse(rs.getDate("fechanacimiento").toString()));
                moderador.setEstado(rs.getBoolean("estado"));
                moderador.setAvatar(rs.getString("avatar"));
                moderador.setDireccion(rs.getString("direccion"));
                moderador.setCalificacion(rs.getInt("calificacion"));
                moderador.setEsModerador(rs.getBoolean("esmoderador"));
                moderadores.add(moderador);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return moderadores;
    }

    @Override
    public void editarInfoColeccionista(Coleccionista coleccionista)  throws Exception{
        System.out.println("CALL BD editarInfoColeccionista");
        String query = "UPDATE collectorsbazar.coleccionistas ";
        query += "SET estado = "+coleccionista.isEstado()+", ";
        query += "esmoderador = "+coleccionista.isEsModerador()+" ";
        query += "WHERE correoelectronico = '"+coleccionista.getCorreoElectronico()+"';";

        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void registrarMisIntereses(Usuario usuario, Categoria categoria) throws Exception {
        System.out.println("CALL BD registrarMisIntereses");
        String query = "INSERT INTO collectorsbazar.usuarioxcategoria VALUES('"+usuario.getCorreoElectronico()+"','"+categoria.getNombre()+"');";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void eliminarMisIntereses(Usuario usuario) {
        System.out.println("CALL BD eliminarMisIntereses");
        String query = "DELETE FROM collectorsbazar.usuarioxcategoria WHERE correousuario ='"+usuario.getCorreoElectronico()+"';";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

package marchal.gabriel.bl.Categoria;

import marchal.gabriel.bl.Usuario.Usuario;
import marchal.gabriel.dl.Conector;

import java.sql.ResultSet;
import java.util.ArrayList;
/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-07-31
 *
 */

public class MySqlCategoria implements ICategoriaDAO {
    @Override
    public int getNextCodigoCategoria() {
        System.out.println("CALL BD getNextCodigoCategoria");
        String query = "SELECT codigo FROM (SELECT codigo FROM collectorsbazar.categorias) AS codigotable ORDER BY codigo DESC LIMIT 1;";
        System.out.println("ENTERING getNextCodigoCategoria");
        int nextid =1;
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while(rs.next()){
                nextid = rs.getInt("codigo") +1;
                System.out.println("NEXT codigo: "+ nextid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return nextid;
    }

    @Override
    public void registrarCategoria(Categoria categoria) {
        System.out.println("CALL BD registrarCategoria");
        String query = "INSERT INTO collectorsbazar.categorias VALUES ("+categoria.getCodigo()+",'"+categoria.getNombre()+"','"+categoria.getDescripcion()+"');";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Categoria> retornarCategorias() {
        System.out.println("CALL BD retornarCategorias");
        String query = "SELECT * FROM collectorsbazar.categorias;";
        ArrayList<Categoria> categorias = new ArrayList<>();
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                Categoria categoria = new Categoria();
                categoria.setCodigo(rs.getInt("codigo"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(rs.getString("descripcion"));
                categorias.add(categoria);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return categorias;
    }

    @Override
    public Categoria retornarCategoriaEspecifica(String nombre) {
        System.out.println("CALL BD retornarCategoriaEspecifica");
        String query = "SELECT * FROM collectorsbazar.categorias WHERE nombre = '"+nombre+"';";
        Categoria categoria= null;
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                categoria.setCodigo(rs.getInt("codigo"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setDescripcion(rs.getString("descripcion"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return categoria;
    }

    @Override
    public void actualizarCategoria(Categoria categoria, String descripcion) {
        System.out.println("CALL BS actualizarCategoria");
        String query = "UPDATE collectorsbazar.categorias SET descripcion = '"+descripcion+"' WHERE nombre = '"+categoria.getNombre()+"' ;";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void eliminarCategoria(Categoria categoria) {
        System.out.println("CALL BD eliminarCategoria");
        String query = "DELETE FROM collectorsbazar.categorias WHERE nombre = '"+categoria.getNombre()+"'";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Categoria> retornarMisIntereses(Usuario usuario) throws Exception {
        ArrayList<Categoria> categorias = new ArrayList<>();

        String query = "SELECT * FROM collectorsbazar.usuarioxcategoria WHERE correousuario ='"+usuario.getCorreoElectronico()+"';";
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                Categoria categoria = new Categoria();
                categoria.setNombre(rs.getString("nombrecategoria"));
                categoria = retornarCategoriaEspecifica(categoria.getNombre());
                categorias.add(categoria);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return categorias;
    }
}

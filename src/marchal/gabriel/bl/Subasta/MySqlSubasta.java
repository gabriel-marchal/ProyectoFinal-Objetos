package marchal.gabriel.bl.Subasta;

import marchal.gabriel.bl.ItemSubasta.ItemSubasta;
import marchal.gabriel.bl.Usuario.Usuario;
import marchal.gabriel.dl.Conector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-07-31
 *
 */

public class MySqlSubasta implements ISubastaDAO {
    @Override
    public boolean guardarSubasta(Subasta subasta) throws Exception {
        System.out.println("CALL BD guardarSubasta");
        String query = "INSERT INTO collectorsbazar.subastas VALUES ( ";
        query+= " "+subasta.getId()+", ";
        query+= " '"+subasta.getFechaInicio().toString()+"', ";
        query+= " '"+subasta.getFechaCreacion().toString()+"',";
        query+= " '"+subasta.getFechaFinalizacion().toString()+"', ";
        query+= " '"+subasta.getEstado()+"',";
        query+=" "+subasta.getOfertaMinima()+", ";
        query+= " "+subasta.getCalificacion()+",";
        query+= " '"+subasta.getVendedor().getCorreoElectronico()+"', ";
        query+= " '"+subasta.getTipoUsuario()+"', ";
        query+= " '"+subasta.getModerador().getCorreoElectronico()+"', ";
        query+= " "+subasta.getPrecio()+");";

        boolean exitoso;
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
            exitoso = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            exitoso= false;
        }

        return exitoso;
    }

    @Override
    public boolean modificarSubasta(Subasta subasta) throws Exception {
        System.out.println("CALL BD modificarSubasta");
        String query = "";

        boolean exitoso;
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
            exitoso = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            exitoso = false;
        }
        return exitoso;
    }

    public void actualizarEstadoSubasta(Subasta subasta) throws Exception{
        System.out.println("CALL BD actualizarEstadoSubasta");
        String query = "UPDATE collectorsbazar.subastas SET estado ='"+subasta.getEstado()+"' WHERE idsubasta="+subasta.getId()+";";
        System.out.println(subasta.getEstado());
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void actualizarEstadoFechaFinalSubasta(Subasta subasta) throws Exception{
        System.out.println("CALL BD actualizarEstadoFechaFinalSubasta");
        String query = "UPDATE collectorsbazar.subastas SET estado ='"+subasta.getEstado()+"', fechafinalizacion='"+subasta.getFechaFinalizacion().toString()+"' WHERE idsubasta="+subasta.getId()+";";
        System.out.println(subasta.getEstado());
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean eliminarSubasta(int id) throws Exception {
        System.out.println("CALL BD eliminarSubasta");
        String query = "DELETE FROM collectorsbazar.subastas WHERE id ="+id+";";
        boolean exitoso;
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
            exitoso = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            exitoso = false;
        }

        return exitoso;
    }

    @Override
    public ArrayList<Subasta> obtenerSubastas() throws Exception {
        System.out.println("CALL BD obtenerSubastas");
        ArrayList<Subasta> subastas = new ArrayList<>();
        String query = "SELECT * FROM collectorsbazar.subastas;";

        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                Subasta subasta = new Subasta();
                subasta.setId(rs.getInt("idsubasta"));
                subasta.setFechaInicio(rs.getTimestamp("fechainicio").toLocalDateTime());
                subasta.setFechaCreacion(rs.getTimestamp("fechacreacion").toLocalDateTime());
                subasta.setFechaFinalizacion(rs.getTimestamp("fechafinalizacion").toLocalDateTime());
                subasta.setEstado(rs.getString("estado"));
                subasta.setOfertaMinima(rs.getDouble("ofertaminima"));
                subasta.setCalificacion(rs.getInt("calificacion"));
                subasta.setPrecio(rs.getDouble("precio"));
                //subasta.setItemesSubastadas(retornarItemxSubasta(subasta));
                subastas.add(subasta);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return subastas;
    }

    @Override
    public ArrayList<Subasta> obtenerMisSubastas(Usuario usuario) throws Exception {
        System.out.println("CALL BD obtenerMisSubastas");
        ArrayList<Subasta> subastas = new ArrayList<>();
        String query = "SELECT * FROM collectorsbazar.subastas WHERE correovendedor ='"+usuario.getCorreoElectronico()+"';";

        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                Subasta subasta = new Subasta();
                subasta.setId(rs.getInt("idsubasta"));
                subasta.setFechaInicio(rs.getTimestamp("fechainicio").toLocalDateTime());
                subasta.setFechaCreacion(rs.getTimestamp("fechacreacion").toLocalDateTime());
                subasta.setFechaFinalizacion(rs.getTimestamp("fechafinalizacion").toLocalDateTime());
                subasta.setEstado(rs.getString("estado"));
                subasta.setOfertaMinima(rs.getDouble("ofertaminima"));
                subasta.setCalificacion(rs.getInt("calificacion"));
                subasta.setPrecio(rs.getDouble("precio"));
                System.out.println(subasta);
                subastas.add(subasta);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return subastas;
    }

    @Override
    public Subasta obtenerSubastaEspecifica(int id) throws Exception {
        System.out.println("CALL BD obtenerSubastaEspecifica");
        String query = "SELECT * FROM collectorsbazar.subastas WHERE idsubasta ="+id+";";
        Subasta subasta = null;
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                subasta.setId(rs.getInt("idsubasta"));
                subasta.setFechaInicio(LocalDateTime.parse(rs.getTimestamp("fechainicio").toString()));
                subasta.setFechaCreacion(LocalDateTime.parse(rs.getTimestamp("fechacreacion").toString()));
                subasta.setFechaFinalizacion(LocalDateTime.parse(rs.getTimestamp("fechafinalizacion").toString()));
                subasta.setEstado(rs.getString("estado"));
                subasta.setOfertaMinima(rs.getDouble("ofertaminima"));
                subasta.setCalificacion(rs.getInt("calificacion"));
                subasta.setPrecio(rs.getDouble("precio"));
                //subasta.setItemesSubastadas(retornarItemxSubasta(subasta));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return subasta;
    }

    @Override
    public String getCorreoModeradorSubasta(Subasta subasta) {
        System.out.println("CALL BD getCorreoModeradorSubasta");
        String query = "SELECT correomoderador FROM subastas WHERE idsubasta = "+subasta.getId()+";";
        String correoelectronico = "";
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                correoelectronico = rs.getString("correomoderador");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return correoelectronico;
    }

    @Override
    public String getCorreoVendedorSubasta(Subasta subasta) {
        System.out.println("CALL BD getCorreoVendedorSubasta");
        String query = "SELECT correovendedor FROM subastas WHERE idsubasta = "+subasta.getId()+";";

        String correoelectronico = "";
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                correoelectronico = rs.getString("correovendedor");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return correoelectronico;
    }

    @Override
    public int getNextIdSubasta() {
        System.out.println("CALL BD getNextIdSubasta");
        String query = "SELECT idsubasta FROM (SELECT idsubasta FROM collectorsbazar.subastas) AS idtable ORDER BY idsubasta DESC LIMIT 1;";
        int nextid =1;
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while(rs.next()){
                nextid = rs.getInt("idsubasta") +1;
                System.out.println("NEXT id: "+ nextid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return nextid;
    }


    public void registrarItemxSubasta(Subasta subasta) throws Exception {
        System.out.println("CALL BD registrarItemxSubasta");
        for (ItemSubasta tempItem  :subasta.getItemesSubastadas()) {
            int nextIdItemxSubasta = getNextIdItemXSubasta();
            String query = "INSERT INTO collectorsbazar.itemxsubasta VALUES ( "+nextIdItemxSubasta+","+tempItem.getId()+","+subasta.getId()+");";
            try {
                Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public ArrayList<ItemSubasta> retornarItemxSubasta(Subasta subasta) {
        System.out.println("CALL BD retornarItemxSubasta");
        String query = "SELECT * FROM collectorsbazar.itemxsubasta WHERE idsubasta = "+subasta.getId()+";";
        ArrayList<ItemSubasta> itemes = new ArrayList<>();
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                ItemSubasta item = new ItemSubasta();
                item.setId(rs.getInt("iditem"));
                itemes.add(item);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return itemes;
    }
    public int getNextIdItemXSubasta() throws Exception{
        int nextId=1;
        String query = "SELECT iditemxsubasta FROM (SELECT iditemxsubasta FROM collectorsbazar.itemxsubasta) AS idtable ORDER BY iditemxsubasta DESC LIMIT 1;";
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                nextId = rs.getInt("iditemxsubasta")+1;
                System.out.println("Next ID: "+nextId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextId;
    }
}

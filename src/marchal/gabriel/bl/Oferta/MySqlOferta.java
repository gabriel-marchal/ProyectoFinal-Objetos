package marchal.gabriel.bl.Oferta;

import marchal.gabriel.bl.Subasta.Subasta;
import marchal.gabriel.bl.Usuario.Usuario;
import marchal.gabriel.dl.Conector;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MySqlOferta implements IOfertaDAO {

    // collectorsbazar.ofertas


    @Override
    public void registrarOferta(Oferta oferta) throws Exception {
        System.out.println("CALL BD registrarOferta");
        int nextId = getNextIdOferta();
        String query = "INSERT INTO collectorsbazar.ofertas VALUES ("+nextId+",'"+oferta.getComprador().getCorreoElectronico()+"',"+
                oferta.getIdSubasta()+","+oferta.getComprador().getCalificacion()+","+oferta.getPrecioOferta()+",'"+oferta.getFechaOferta().toString()+"');";

        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Oferta> retornarTodasOfertas() throws Exception {
        ArrayList<Oferta> ofertas = new ArrayList<>();
        String query = "SELECT * FROM collectorsbazar.ofertas;";

        ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
        while (rs.next()){
            Oferta oferta = new Oferta();
            oferta.setIdOferta(rs.getInt("idoferta"));
            oferta.setIdSubasta(rs.getInt("idsubasta"));
            oferta.setPrecioOferta(rs.getDouble("preciooferta"));
            oferta.setFechaOferta(rs.getTimestamp("fechaoferta").toLocalDateTime());

            ofertas.add(oferta);
        }
        return ofertas;
    }
    @Override
    public String retornarCorreoComprador(Oferta oferta) throws Exception {
        String query = "SELECT correocomprador FROM collectorsbazar.ofertas WHERE idoferta ="+oferta.getIdOferta()+";";
        String correocomprador = "";
        ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
        while (rs.next()){
            correocomprador = rs.getString("correocomprador");
        }

        return correocomprador;
    }

    @Override
    public Oferta retornarOfertaEspecifica(Oferta oferta) throws Exception {
        String query = "SELECT * FROM collectorsbazar.ofertas WHERE idoferta="+oferta.getIdOferta()+";";

        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                oferta.setIdSubasta(rs.getInt("idsubasta"));
                oferta.setPrecioOferta(rs.getDouble("preciooferta"));
                oferta.setFechaOferta(rs.getTimestamp("fechaoferta").toLocalDateTime());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return oferta;
    }
    public String retornarCorreoCompradorOferta(Oferta oferta){
        String query = "SELECT correocomprador FROM collectorsbazar.ofertas WHERE idoferta ="+oferta.getIdOferta()+";";
        String correo ="";
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                correo = rs.getString("correocomprador");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return correo;
    }
    public ArrayList<Oferta> retornarOfertaXUsuario(Usuario usuario){
        ArrayList<Oferta> ofertas = new ArrayList<>();

        String query = "SELECT idoferta FROM collectorsbazar.ofertas WHERE correocomprador = '"+usuario.getCorreoElectronico()+"';";
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                Oferta oferta = new Oferta();
                oferta.setIdOferta(rs.getInt("idoferta"));
                oferta = retornarOfertaEspecifica(oferta);
                ofertas.add(oferta);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ofertas;
    }

    @Override
    public void modificarOferta(Oferta oferta) throws Exception {
        String query = "UPDATE collectorsbazar.ofertas SET correocomprador = '"+oferta.getComprador().getCorreoElectronico()+"' , idsubasta = "+oferta.getIdSubasta()+", calificacion = "+oferta.getCalificacion() +
                ", preciooferta="+oferta.getPrecioOferta()+", fechaoferta='"+oferta.getFechaOferta().toString()+"'"+
                " WHERE idoferta = "+oferta.getIdOferta()+";";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void eliminarOferta(Oferta oferta) throws Exception {
        String query = "DELETE FROM collectorsbazar.ofertas WHERE idoferta="+oferta.getIdOferta()+";";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getNextIdOferta() throws Exception {
        String query = "SELECT idoferta FROM (SELECT idoferta FROM collectorsbazar.ofertas) AS idtable ORDER BY idoferta DESC LIMIT 1;";
        int nextid =1;
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while(rs.next()){
                nextid = rs.getInt("idoferta") +1;
                System.out.println("NEXT ID: "+ nextid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return nextid;

    }

    public void registrarOfertasXSubasta(Oferta oferta, Subasta subasta) throws Exception {
        // ofertasxsubastas
        int nextId = getNextIDOfertasXSubasta();
        String query = "INSERT INTO collectorsbazar.ofertasxsubastas VALUES("+nextId+","+subasta.getId()+","+oferta.getIdOferta()+",'"+oferta.getComprador().getCorreoElectronico()+"');";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public ArrayList<Oferta> retornarOfertasXSubasta(Subasta subasta){
        ArrayList<Oferta> ofertas = new ArrayList<>();

        String query = "SELECT * FROM collectorsbazar.ofertasxsubastas WHERE idsubasta ="+subasta.getId()+";";

        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                Oferta oferta = new Oferta();
                int idOferta = rs.getInt("idoferta");
                oferta.setIdOferta(idOferta);
                oferta = retornarOfertaEspecifica(oferta);
                ofertas.add(oferta);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ofertas;

    }
    public int getNextIDOfertasXSubasta() throws Exception{
        System.out.println("CALL BD getNextIDOfertasXSubasta");
        String query = "SELECT idofertasxsubastas FROM (SELECT idofertasxsubastas FROM collectorsbazar.ofertasxsubastas) AS idtable ORDER BY idofertasxsubastas DESC LIMIT 1;";
        int nextId = 1;
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                nextId = rs.getInt("idofertasxsubastas")+1;
                System.out.println("Next idofertasxsubastas "+ nextId);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return nextId;
    }
}

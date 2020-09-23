package marchal.gabriel.bl.OrdenDeCompra;

import marchal.gabriel.bl.Subasta.Subasta;
import marchal.gabriel.dl.Conector;

import java.sql.ResultSet;
import java.util.ArrayList;

public class MySqlOrdenDeCompra implements IOrdenDeCompraDAO {

    // collectorsbazar.ordenesdecompra

    @Override
    public void registrarOrdenCompra(OrdenDeCompra ordenDeCompra) throws Exception {
        System.out.println("CALL BD registrarOrdenCompra");
        String query = "INSERT INTO collectorsbazar.ordenesdecompra VALUES("+ordenDeCompra.getIdSubasta()+","+ordenDeCompra.getIdOrdenDeCompra()+",'"+ordenDeCompra.getNombreGanador()+
                "','"+ordenDeCompra.getFechaOrdenCompra().toString()+"',"+ordenDeCompra.getPrecioTotal()+","+ordenDeCompra.getEsEntregado()+");";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<OrdenDeCompra> retornarTodosOrdenCompra() throws Exception {
        System.out.println("CALL BD retornarTodosOrdenCompra");
        ArrayList<OrdenDeCompra> ordenes = new ArrayList<>();
        String query = "SELECT * FROM collectorsbazar.ordenesdecompra;";

        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                OrdenDeCompra orden = new OrdenDeCompra();
                orden.setIdSubasta(rs.getInt("idSubasta"));
                orden.setIdOrdenDeCompra(rs.getInt("idordendecompra"));
                orden.setNombreGanador(rs.getString("nombreganador"));
                orden.setFechaOrdenCompra(rs.getTimestamp("fechaordencompra").toLocalDateTime());
                orden.setPrecioTotal(rs.getDouble("preciototal"));
                orden.setEsEntregado(rs.getBoolean("esentregado"));
                ordenes.add(orden);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ordenes;
    }

    @Override
    public OrdenDeCompra retornarOrdenCompraEspecifica(OrdenDeCompra ordenDeCompra) throws Exception {
        System.out.println("CALL BD retornarOrdenCompraEspecifica");
        String query = "SELECT * FROM collectorsbazar.ordenesdecompra WHERE idordendecompra = "+ordenDeCompra.getIdOrdenDeCompra()+";";
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                ordenDeCompra.setIdSubasta(rs.getInt("idSubasta"));
                ordenDeCompra.setNombreGanador(rs.getString("nombreganador"));
                ordenDeCompra.setFechaOrdenCompra(rs.getTimestamp("fechaordencompra").toLocalDateTime());
                ordenDeCompra.setPrecioTotal(rs.getDouble("preciototal"));
                ordenDeCompra.setEsEntregado(rs.getBoolean("esentregado"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return ordenDeCompra;
    }
    public OrdenDeCompra buscarOrdenPorSubasta(Subasta subasta) throws Exception{
        System.out.println("CALL BD buscarOrdenPorSubasta");
        String query = "SELECT * FROM collectorsbazar.ordenesdecompra WHERE idSubasta = "+subasta.getId()+";";
        OrdenDeCompra orden = null;
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                orden.setIdOrdenDeCompra(rs.getInt("idordendecompra"));
                orden.setIdSubasta(rs.getInt("idSubasta"));
                orden.setNombreGanador(rs.getString("nombreganador"));
                orden.setFechaOrdenCompra(rs.getTimestamp("fechaordencompra").toLocalDateTime());
                orden.setPrecioTotal(rs.getDouble("preciototal"));
                orden.setEsEntregado(rs.getBoolean("esentregado"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return orden;
    }
    public boolean existeOrdenPorSubasta(Subasta subasta) throws Exception{
        System.out.println("CALL BD existeOrdenPorSubasta");
        String query = "SELECT * FROM collectorsbazar.ordenesdecompra WHERE idSubasta = "+subasta.getId()+";";
        boolean existe = false;
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            if (rs.next()){
                existe = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return existe;
    }

    @Override
    public void modificarOrdenDeCompra(OrdenDeCompra ordenDeCompra) throws Exception {
        System.out.println("CALL BD modificarOrdenDeCompra");
        String query = "UPDATE collectorsbazar.ordenesdecompra SET nombreganador ='"+ordenDeCompra.getNombreGanador()+"', fechaordencompra = '"+ordenDeCompra.getFechaOrdenCompra().toString()+
                "',preciototal= "+ordenDeCompra.getPrecioTotal()+", esentregado="+ordenDeCompra.getEsEntregado()+
                " WHERE idordendecompra="+ordenDeCompra.getIdOrdenDeCompra()+";";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void eliminarOrdenDeCompra(OrdenDeCompra ordenDeCompra) throws Exception {
        System.out.println("CALL BD eliminarOrdenDeCompra");
        String query = "DELETE FROM collectorsbazar.ordenesdecompra WHERE idordendecompra="+ordenDeCompra.getIdOrdenDeCompra()+";";

        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int getNextIdOrdenDeCompra() throws Exception {
        System.out.println("CALL BD getNextIdOrdenDeCompra");
        String query = "SELECT idordendecompra FROM (SELECT idordendecompra FROM collectorsbazar.ordenesdecompra) AS idtable ORDER BY idordendecompra DESC LIMIT 1;";
        int nextid =1;
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while(rs.next()){
                nextid = rs.getInt("idordendecompra") +1;
                System.out.println("NEXT ID: "+ nextid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return nextid;
    }
}

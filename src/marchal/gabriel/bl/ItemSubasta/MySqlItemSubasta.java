package marchal.gabriel.bl.ItemSubasta;

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

public class MySqlItemSubasta implements IItemSubastaDAO {
    @Override
    public ArrayList<ItemSubasta> getMisItemes(String email) {
        System.out.println("CALL BD getMisItemes");
        String query = "SELECT * FROM collectorsbazar.itemes WHERE emailvendedor = '"+email+"' ;";
        ArrayList<ItemSubasta> misItemes = new ArrayList<>();
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                ItemSubasta item = new ItemSubasta();
                item.setId(rs.getInt("iditem"));
                item.setNombre(rs.getString("nombre"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setEstado(rs.getString("estado"));
                item.setCategoria(rs.getString("categoria"));
                item.setFechaCompra(LocalDate.parse(rs.getDate("fechacompra").toString()));
                misItemes.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        System.out.println("misItemes.size(): "+misItemes.size());
        return misItemes;
    }

    @Override
    public int registrarItemSubasta(ItemSubasta itemSubasta) {
        System.out.println("CALL BD registrarItemSubasta");
        int nextid = getNextItemId();
        String query = "INSERT INTO collectorsbazar.itemes VALUES ("+nextid+",'"+itemSubasta.getNombre()+"','"+itemSubasta.getDescripcion()+"','"+
                itemSubasta.getEstado()+"','"+itemSubasta.getFechaCompra().toString()+"','"+itemSubasta.getCategoria()+"','"+ itemSubasta.getVendedor().getCorreoElectronico()+"');";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        /*
        for (String imagen:itemSubasta.getImagenes() ) {
            registrarImagenesItemes(itemSubasta.getId(),imagen);
        }

         */

        return nextid;
    }

    @Override
    public void registrarImagenesItemes(int iditem, String imagen) {
        System.out.println("CALL BD registrarImagenesItemes");
        int nextid= getNextiditemximagen();
        String query = "INSERT INTO collectorsbazar.itemximagen VALUES ("+nextid+","+iditem+",'"+imagen+"');";
        try {
            System.out.println("-------------------------------------- "+imagen);
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<String> retornarImagenesItemes(int iditem) {
        System.out.println("CALL BD retornarImagenesItemes");
        String query = "SELECT imagen FROM collectorsbazar.itemximagen WHERE iditem = "+ iditem+";";
        ArrayList<String> imagenes = new ArrayList<>();
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                imagenes.add(rs.getString("imagen"));
                System.out.println("IMAGEN DEL ITEM EN BD : "+ rs.getString("imagen"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        System.out.println("IMAGENES EN retornarImagenesItemes: "+imagenes);
        return imagenes;
    }

    @Override
    public int getNextiditemximagen() {
        System.out.println("CALL BD getNextiditemximagen");
        String query = "SELECT iditemximagen FROM (SELECT iditemximagen FROM collectorsbazar.itemximagen) AS idtable ORDER BY iditemximagen DESC LIMIT 1;";
        int nextid =1;
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while(rs.next()){
                nextid = rs.getInt("iditemximagen") +1;
                System.out.println("NEXT ID: "+ nextid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return nextid;
    }
    public ItemSubasta retornarFullItem(ItemSubasta item){
        System.out.println("CALL BD retornarFullItem");
        String query = "SELECT * FROM collectorsbazar.itemes WHERE iditem = "+ item.getId()+";";

        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                item.setId(rs.getInt("iditem"));
                item.setNombre(rs.getString("nombre"));
                item.setDescripcion(rs.getString("descripcion"));
                item.setEstado(rs.getString("estado"));
                item.setFechaCompra(LocalDate.parse(rs.getDate("fechacompra").toString()));
                item.setCategoria(rs.getString("categoria"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return item;
    }


    @Override
    public int getNextItemId() {
        System.out.println("CALL BD getNextItemId");
        String query = "SELECT iditem FROM (SELECT iditem FROM collectorsbazar.itemes) AS itemtable ORDER BY iditem DESC LIMIT 1;";
        System.out.println("ENTERING getNextItemId");
        int nextid =1;
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while(rs.next()){
                nextid = rs.getInt("iditem") +1;
                System.out.println("NEXT ID: "+ nextid);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return nextid;
    }

    public String retornarEmailVendedorItem(ItemSubasta item){
        System.out.println("CALL BD retornarEmailVendedorItem");
        String query = "SELECT emailvendedor FROM collectorsbazar.itemes WHERE iditem = "+ item.getId()+";";
        String email = "";
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                email = rs.getString("emailvendedor");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return email;
    }
}

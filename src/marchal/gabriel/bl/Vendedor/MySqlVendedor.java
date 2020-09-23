package marchal.gabriel.bl.Vendedor;

import marchal.gabriel.dl.Conector;

import java.sql.ResultSet;
import java.util.ArrayList;

public class MySqlVendedor implements IVendedorDAO{
    /*
    private String nombre;
    private String direccion;
    private String correoElectronico;
     */


    @Override
    public void registrarVendedor(Vendedor vendedor) throws Exception {
        String query = "INSERT INTO collectorsbazar.vendedores VALUES('"+vendedor.getNombre()+"','"+vendedor.getDireccion()+"','"+vendedor.getCorreoElectronico()+"');";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void eliminarVendedor(Vendedor vendedor) throws Exception {
        String query ="DELETE FROM collectorsbazar.vendedores WHERE correoelectronico ='"+vendedor.getCorreoElectronico()+"';";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Vendedor retornarVendedor(String email) throws Exception {
        String query = "SELECT * FROM collectorsbazar.vendedores WHERE correoelectronico ='"+email+"';";
        Vendedor vendedor = new Vendedor();
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                vendedor.setNombre(rs.getString("nombre"));
                vendedor.setDireccion(rs.getString("direccion"));
                vendedor.setCorreoElectronico(rs.getString("correoelectronico"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return vendedor;
    }

    @Override
    public ArrayList<Vendedor> retornarTodosVendedores() throws Exception {
        String query = "SELECT * FROM collectorsbazar.vendedores;";
        ArrayList<Vendedor> vendedores = new ArrayList<>();
        try {
            ResultSet rs = Conector.getConnector().ejecutarSELECT(query);
            while (rs.next()){
                Vendedor vendedor = new Vendedor();
                vendedor.setNombre(rs.getString("nombre"));
                vendedor.setDireccion(rs.getString("direccion"));
                vendedor.setCorreoElectronico(rs.getString("correoelectronico"));

                vendedores.add(vendedor);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return vendedores;
    }

    @Override
    public void modificarVendedor(Vendedor vendedor) throws Exception {
        String query = "UPDATE collectorsbazar.vendedores SET nombre = '"+vendedor.getNombre()+"', direccion ='"+vendedor.getDireccion()+"';";
        try {
            Conector.getConnector().ejecutarINSERT_UPDATE_DELETE(query);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

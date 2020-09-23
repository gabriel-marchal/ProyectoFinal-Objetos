package marchal.gabriel.bl.Vendedor;

import java.util.ArrayList;

public interface IVendedorDAO {

    public void registrarVendedor(Vendedor vendedor) throws Exception;
    public void eliminarVendedor(Vendedor vendedor) throws Exception;
    public Vendedor retornarVendedor(String email) throws Exception;
    public ArrayList<Vendedor> retornarTodosVendedores() throws Exception;
    public void modificarVendedor(Vendedor vendedor) throws Exception;
}

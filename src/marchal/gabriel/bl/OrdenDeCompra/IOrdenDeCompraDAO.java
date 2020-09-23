package marchal.gabriel.bl.OrdenDeCompra;

import marchal.gabriel.bl.Subasta.Subasta;

import java.rmi.server.ExportException;
import java.util.ArrayList;

public interface IOrdenDeCompraDAO {

    public void registrarOrdenCompra(OrdenDeCompra ordenDeCompra) throws Exception;
    public ArrayList<OrdenDeCompra> retornarTodosOrdenCompra() throws Exception;
    public OrdenDeCompra retornarOrdenCompraEspecifica(OrdenDeCompra ordenDeCompra) throws Exception;
    public void modificarOrdenDeCompra(OrdenDeCompra ordenDeCompra) throws Exception;
    public void eliminarOrdenDeCompra(OrdenDeCompra ordenDeCompra) throws Exception;
    public int getNextIdOrdenDeCompra() throws Exception;
    public OrdenDeCompra buscarOrdenPorSubasta(Subasta subasta) throws Exception;
    public boolean existeOrdenPorSubasta(Subasta subasta) throws Exception;
}

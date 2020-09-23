package marchal.gabriel.bl;

import marchal.gabriel.bl.Administrador.IAdministradorDAO;
import marchal.gabriel.bl.Categoria.ICategoriaDAO;
import marchal.gabriel.bl.Coleccionista.IColeccionistaDAO;
import marchal.gabriel.bl.ItemSubasta.IItemSubastaDAO;
import marchal.gabriel.bl.Oferta.IOfertaDAO;
import marchal.gabriel.bl.OrdenDeCompra.IOrdenDeCompraDAO;
import marchal.gabriel.bl.Subasta.ISubastaDAO;
import marchal.gabriel.bl.Usuario.IUsuarioDAO;
import marchal.gabriel.bl.Vendedor.IVendedorDAO;

/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-07-31
 *
 */

public abstract class DaoFactory {

    public static DaoFactory getDaoFactory(){
        return new MySqlDaoFactory();
    }

    public abstract IAdministradorDAO getAdministradorImp();
    public abstract ICategoriaDAO getCategoriaImp();
    public abstract IColeccionistaDAO getColeccionistaImp();
    public abstract IItemSubastaDAO getItemSubastaImp();
    public abstract ISubastaDAO getSubastaImp();
    public abstract IUsuarioDAO getUsuarioImp();
    public abstract IOfertaDAO getOfertaImp();
    public abstract IOrdenDeCompraDAO getOrdenDeCompraImp();
    public abstract IVendedorDAO getVendedorImp();
}
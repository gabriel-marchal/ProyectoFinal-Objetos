package marchal.gabriel.bl;

import marchal.gabriel.bl.Administrador.IAdministradorDAO;
import marchal.gabriel.bl.Administrador.MySqlAdministrador;
import marchal.gabriel.bl.Categoria.ICategoriaDAO;
import marchal.gabriel.bl.Categoria.MySqlCategoria;
import marchal.gabriel.bl.Coleccionista.IColeccionistaDAO;
import marchal.gabriel.bl.Coleccionista.MySqlColeccionista;
import marchal.gabriel.bl.ItemSubasta.IItemSubastaDAO;
import marchal.gabriel.bl.ItemSubasta.MySqlItemSubasta;
import marchal.gabriel.bl.Oferta.IOfertaDAO;
import marchal.gabriel.bl.Oferta.MySqlOferta;
import marchal.gabriel.bl.OrdenDeCompra.IOrdenDeCompraDAO;
import marchal.gabriel.bl.OrdenDeCompra.MySqlOrdenDeCompra;
import marchal.gabriel.bl.Subasta.ISubastaDAO;
import marchal.gabriel.bl.Subasta.MySqlSubasta;
import marchal.gabriel.bl.Usuario.IUsuarioDAO;
import marchal.gabriel.bl.Usuario.MySqlUsuario;
import marchal.gabriel.bl.Vendedor.IVendedorDAO;
import marchal.gabriel.bl.Vendedor.MySqlVendedor;

/**
 * @author Gabriel Marchal
 * @version V1
 * @since 2020-07-31
 *
 */

public class MySqlDaoFactory extends DaoFactory{
    @Override
    public IAdministradorDAO getAdministradorImp() {
        return new MySqlAdministrador();
    }

    @Override
    public ICategoriaDAO getCategoriaImp() {
        return new MySqlCategoria();
    }

    @Override
    public IColeccionistaDAO getColeccionistaImp() {
        return new MySqlColeccionista();
    }

    @Override
    public IItemSubastaDAO getItemSubastaImp() {
        return new MySqlItemSubasta();
    }

    @Override
    public ISubastaDAO getSubastaImp() {
        return new MySqlSubasta();
    }

    @Override
    public IUsuarioDAO getUsuarioImp() {
        return new MySqlUsuario();
    }

    @Override
    public IOfertaDAO getOfertaImp() {
        return new MySqlOferta();
    }

    @Override
    public IOrdenDeCompraDAO getOrdenDeCompraImp() {
        return new MySqlOrdenDeCompra();
    }

    @Override
    public IVendedorDAO getVendedorImp() {
        return new MySqlVendedor();
    }

}
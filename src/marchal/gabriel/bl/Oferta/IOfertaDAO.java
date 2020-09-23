package marchal.gabriel.bl.Oferta;

import marchal.gabriel.bl.Subasta.Subasta;
import marchal.gabriel.bl.Usuario.Usuario;

import java.util.ArrayList;

public interface IOfertaDAO {

    public void registrarOferta(Oferta oferta) throws Exception;
    public ArrayList<Oferta> retornarTodasOfertas() throws Exception;
    public Oferta retornarOfertaEspecifica(Oferta oferta) throws Exception;
    public void modificarOferta(Oferta oferta) throws Exception;
    public void eliminarOferta(Oferta oferta) throws Exception;
    public int getNextIdOferta() throws Exception;
    public String retornarCorreoComprador(Oferta oferta) throws Exception;
    public void registrarOfertasXSubasta(Oferta oferta, Subasta subasta) throws Exception;
    public int getNextIDOfertasXSubasta() throws Exception;
    public String retornarCorreoCompradorOferta(Oferta oferta) throws Exception;
    public ArrayList<Oferta> retornarOfertaXUsuario(Usuario usuario) throws Exception;
    public ArrayList<Oferta> retornarOfertasXSubasta(Subasta subasta) throws Exception;
}

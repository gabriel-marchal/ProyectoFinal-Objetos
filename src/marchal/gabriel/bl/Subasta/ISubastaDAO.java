package marchal.gabriel.bl.Subasta;

import marchal.gabriel.bl.ItemSubasta.ItemSubasta;
import marchal.gabriel.bl.Usuario.Usuario;

import java.util.ArrayList;

public interface ISubastaDAO {

    public boolean guardarSubasta(Subasta subasta) throws Exception;
    public boolean modificarSubasta(Subasta subasta) throws Exception;
    public boolean eliminarSubasta(int id) throws Exception;
    public ArrayList<Subasta> obtenerSubastas() throws Exception;
    public ArrayList<Subasta> obtenerMisSubastas(Usuario usuario) throws Exception;
    public Subasta obtenerSubastaEspecifica(int id) throws Exception;
    public String getCorreoModeradorSubasta(Subasta subasta)  throws Exception ;
    public String getCorreoVendedorSubasta(Subasta subasta) throws Exception;
    public int getNextIdSubasta() throws Exception;


    public void registrarItemxSubasta(Subasta subasta) throws Exception;
    public ArrayList<ItemSubasta> retornarItemxSubasta(Subasta subasta) throws Exception;
    public int getNextIdItemXSubasta()throws Exception;
    public void actualizarEstadoSubasta(Subasta subasta) throws Exception;
    public void actualizarEstadoFechaFinalSubasta(Subasta subasta) throws Exception;



}

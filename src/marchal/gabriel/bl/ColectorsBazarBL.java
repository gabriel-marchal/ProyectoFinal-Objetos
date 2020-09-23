package marchal.gabriel.bl;

import marchal.gabriel.bl.Subasta.Subasta;
import marchal.gabriel.bl.Usuario.Usuario;


public class ColectorsBazarBL {

    private static Usuario usuarioLogeado;
    private static Subasta subastaObservado;

    public ColectorsBazarBL(){

    }
    public void setUsuarioLogeado(Usuario usuario) throws Exception{
        this.usuarioLogeado = usuario;
    }
    public Usuario getUsuarioLogeado(){
        return usuarioLogeado;
    }

    public Subasta getSubastaObservado() {
        return subastaObservado;
    }

    public void setSubastaObservado(Subasta subastaObservado) {
        this.subastaObservado = subastaObservado;
    }
}

package marchal.gabriel.tl;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import marchal.gabriel.bl.*;
import marchal.gabriel.bl.Administrador.Administrador;
import marchal.gabriel.bl.Administrador.IAdministradorDAO;
import marchal.gabriel.bl.Categoria.Categoria;
import marchal.gabriel.bl.Categoria.ICategoriaDAO;
import marchal.gabriel.bl.Coleccionista.Coleccionista;
import marchal.gabriel.bl.Coleccionista.IColeccionistaDAO;
import marchal.gabriel.bl.Email.EnviarEmail;
import marchal.gabriel.bl.ItemSubasta.IItemSubastaDAO;
import marchal.gabriel.bl.ItemSubasta.ItemSubasta;
import marchal.gabriel.bl.Oferta.IOfertaDAO;
import marchal.gabriel.bl.Oferta.Oferta;
import marchal.gabriel.bl.OrdenDeCompra.IOrdenDeCompraDAO;
import marchal.gabriel.bl.OrdenDeCompra.OrdenDeCompra;
import marchal.gabriel.bl.Subasta.ISubastaDAO;
import marchal.gabriel.bl.Subasta.Subasta;
import marchal.gabriel.bl.Usuario.IUsuarioDAO;
import marchal.gabriel.bl.Usuario.Usuario;
import marchal.gabriel.bl.Vendedor.IVendedorDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.StrictMath.abs;

public class MainController {
    protected static ColectorsBazarBL logica ;
    protected DaoFactory factory;
    protected IItemSubastaDAO daoItemSubasta;
    protected IAdministradorDAO daoAdministrador;
    protected ICategoriaDAO daoCategoria;
    protected IColeccionistaDAO daoColeccionista;
    protected ISubastaDAO daoSubasta;
    protected IUsuarioDAO daoUsuario;
    protected IOfertaDAO daoOferta;
    protected IOrdenDeCompraDAO daoOrdenDeCompra;
    protected IVendedorDAO daoVendedor;

    public MainController() throws Exception {
        logica = new ColectorsBazarBL();
        factory = DaoFactory.getDaoFactory();
        daoItemSubasta = factory.getItemSubastaImp();
        daoAdministrador = factory.getAdministradorImp();
        daoCategoria = factory.getCategoriaImp();
        daoColeccionista = factory.getColeccionistaImp();
        daoSubasta = factory.getSubastaImp();
        daoUsuario = factory.getUsuarioImp();
        daoOferta = factory.getOfertaImp();
        daoOrdenDeCompra = factory.getOrdenDeCompraImp();
        daoVendedor = factory.getVendedorImp();

    }

    public void INICIOAPP_enviarOrdenesDeSubastasFinalizadas() throws Exception{
        calcularModificarSubastasEstado();

        ArrayList<Subasta> subastas = daoSubasta.obtenerSubastas();
        System.out.println(subastas);
        for (Subasta tempSubasta:subastas ) {
            tempSubasta = retornarFullSubasta(tempSubasta);
            if(!daoOrdenDeCompra.existeOrdenPorSubasta(tempSubasta) && tempSubasta.getEstado().equals("finalizada")){
                ArrayList<Oferta> ofertas = daoOferta.retornarOfertasXSubasta(tempSubasta);
                System.out.println(ofertas);
                if(ofertas.size()>0){
                    double mayor = 0;
                    Coleccionista ganador =null;
                    Oferta ofertaMayor = null;
                    for (Oferta tempOferta:ofertas) {
                        System.out.println("Entering FOR OFERTAS");
                        tempOferta = retornarFullOferta(tempOferta);
                        if(tempOferta.getPrecioOferta()>mayor){
                            mayor=tempOferta.getPrecioOferta();
                            ofertaMayor = tempOferta;
                            ganador = tempOferta.getComprador();
                        }
                    }
                    OrdenDeCompra ordenNueva = new OrdenDeCompra(tempSubasta.getId(),daoOrdenDeCompra.getNextIdOrdenDeCompra(),ganador.getNombre(),LocalDateTime.now(),tempSubasta.getItemesSubastadas(),ofertaMayor.getPrecioOferta(),false);
                    daoOrdenDeCompra.registrarOrdenCompra(ordenNueva);
                    EnviarEmail email = new EnviarEmail().enviarEmailGanadorSubasta(ganador,tempSubasta,ordenNueva);
                }
            }
        }
    }

    public void calcularModificarSubastasEstado() throws Exception{
        System.out.println("calcularModificarSubastasEstado");
        ArrayList<Subasta> subastas = daoSubasta.obtenerSubastas();
        for (Subasta tempSubasta: subastas ) {
            //Duration durationInicio = Duration.between(LocalDateTime.now(),tempSubasta.getFechaInicio());
            //Duration durationFinal = Duration.between(LocalDateTime.now(),tempSubasta.getFechaFinalizacion());

            if(tempSubasta.getFechaInicio().compareTo(LocalDateTime.now())>0){
                tempSubasta.setEstado("por iniciar");
            }else if(tempSubasta.getFechaInicio().compareTo(LocalDateTime.now())<0 && tempSubasta.getFechaFinalizacion().compareTo(LocalDateTime.now())>0){
                tempSubasta.setEstado("iniciada");
            }
            if(tempSubasta.getFechaFinalizacion().compareTo(LocalDateTime.now()) <0){
                tempSubasta.setEstado("finalizada");
            }
            daoSubasta.actualizarEstadoSubasta(tempSubasta);
        }
    }

    public void cambiarJavaFXScene(javafx.event.ActionEvent actionEvent ,String nombreArchivoFXML) throws Exception {
        String path = "../ui/"+nombreArchivoFXML+".fxml";

        Parent paginaPrincipalParent = FXMLLoader.load(getClass().getResource(path));
        Scene paginaPrincipalScene = new Scene(paginaPrincipalParent);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.getIcons().add(new Image(String.valueOf(getClass().getResource("../assets/icon.png"))));
        window.setScene(paginaPrincipalScene);
        window.setTitle("Collectors Bazar");
        window.show();
    }


    public void signout(javafx.event.ActionEvent actionEvent) throws Exception {
        logica.setUsuarioLogeado(null);
        cambiarJavaFXScene(actionEvent,"LoginPage");
    }

    public ArrayList<Usuario> imprimirUsuarios() throws Exception {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        ArrayList<Usuario> coleccionistas = daoColeccionista.retorarTodosColeccionistas();
        ArrayList<Usuario> administradores = daoAdministrador.retornarTodosAdmin();

        for (Usuario tempColeccionista: coleccionistas) {
            usuarios.add(tempColeccionista);
        }
        for (Usuario tempAdmin:administradores ) {
            usuarios.add(tempAdmin);
        }

        return usuarios;
    }


    public Usuario getUsuarioLogeado(){
        return logica.getUsuarioLogeado();
    }

    public ArrayList<Usuario> getModeradores()throws SQLException{

        ArrayList<Usuario> moderadores = null;
        try {
            moderadores = daoColeccionista.getModeradores();
        } catch (Exception e) {
            moderadores.set(0,null);
            System.out.println(e.getMessage());
        }

        return moderadores;
    }
    public Usuario getModeradorRandom(){
        try {
            ArrayList<Usuario> moderadores = getModeradores();
            int rand = new Random().nextInt(moderadores.size());;
            return moderadores.get(rand);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String retornarString_LOCALDATE(LocalDate fecha){
        try {
            String month= "";
            String day="";

            if(fecha.getMonthValue()<10){
                month ="0"+fecha.getMonthValue();
            }else{
                month= String.valueOf(fecha.getMonthValue());
            }
            if(fecha.getDayOfMonth()<10){
                day = "0"+fecha.getDayOfMonth() ;
            }else{
                day=String.valueOf(fecha.getDayOfMonth());
            }

            String fechaString = fecha.getYear()+"-"+month+"-"+day;

            return fechaString;
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
            return fecha.toString();
        }
    }
    public String retornarString_LOCALDATETIME(LocalDateTime fecha){
        try {
            String month;
            String day;
            String hour;
            String minute;
            String second;

            if(fecha.getMonthValue()<10){
                month ="0"+fecha.getMonthValue();
            }else{
                month= String.valueOf(fecha.getMonthValue());
            }
            if(fecha.getDayOfMonth()<10){
                day = "0"+fecha.getDayOfMonth() ;
            }else{
                day=String.valueOf(fecha.getDayOfMonth());
            }
            if(fecha.getHour()<10){
                hour = "0"+fecha.getHour() ;
            }else{
                hour =String.valueOf(fecha.getHour());
            }
            if(fecha.getMinute()<10){
                minute = "0"+fecha.getMinute();
            }else{
                minute =String.valueOf(fecha.getMinute());
            }
            if(fecha.getSecond()<10){
                second = "0"+fecha.getSecond();
            }else{
                second =String.valueOf(fecha.getSecond());
            }

            String fechaString = fecha.getYear()+"-"+month +"-"+day+" "+hour+":"+minute+":"+second;

            return fechaString;
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println(e.getMessage());
            return fecha.toString();
        }

    }
    public void clickVolverPerfil(javafx.event.ActionEvent actionEvent) throws Exception {
        if(logica.getUsuarioLogeado() instanceof Administrador){
            cambiarJavaFXScene(actionEvent,"PerfilAdministrador");
        }else{
            cambiarJavaFXScene(actionEvent,"PerfilUsuario");
        }
    }

    public void clickEditarPerfil(javafx.event.ActionEvent actionEvent)throws Exception{
        cambiarJavaFXScene(actionEvent,"EditarPerfil");

    }
    public void clickCambiarContrasena(javafx.event.ActionEvent actionEvent) throws Exception {
        cambiarJavaFXScene(actionEvent,"CambiarContrasena");
    }
    public boolean validarCredenciales(String email, String password){
        boolean validado = false;
        try {
            Usuario usuario = daoUsuario.retornarUsuario(email);
            if(usuario.getContrasena().equals(password)){
                validado = true;
            }else{
                validado = false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return validado;
    }
    public boolean validarMayorEdad(LocalDate fechaNacimiento){
        Period period = Period.between(fechaNacimiento,LocalDate.now());
        if(abs(period.getYears())>18){
            return true;
        }else{
            return false;
        }
    }
    public void enviarEmailBienvenida(Usuario usuario) throws Exception {
        EnviarEmail enviarEmail = new EnviarEmail().enviarEmailBienvenida(usuario);
    }
    public void enviarEmailSeleccionModerador(Usuario usuario, Subasta subasta) throws Exception{
        EnviarEmail enviarEmail = new EnviarEmail().enviarEmailModerador(usuario,subasta);
    }
    public void enviarEmailAdminSelectModerador(Usuario usuario) throws Exception{
        EnviarEmail enviarEmail = new EnviarEmail().enviarEmailadminSelectModerador(usuario);
    }
    // TODO Implementar el otro enviarEmail (low prioridad)

    public ArrayList<String> getNombreCategorias() throws Exception {
        ArrayList<Categoria> categorias = daoCategoria.retornarCategorias();
        ArrayList<String> nombreCategorias = new ArrayList<>();
        for (Categoria tempCategoria: categorias ) {
            nombreCategorias.add(tempCategoria.getNombre());
        }
        return nombreCategorias;
    }

    public Subasta retornarFullSubasta(Subasta subasta) throws Exception {
        String emailVendor = daoSubasta.getCorreoVendedorSubasta(subasta);
        String emailModerador = daoSubasta.getCorreoModeradorSubasta(subasta);
        ArrayList<ItemSubasta> itemsSubasta = daoSubasta.retornarItemxSubasta(subasta);
        for (ItemSubasta tempItem:itemsSubasta ) {
            tempItem = retornarFullItemSubasta(tempItem);
        }
        Usuario vendedor = daoUsuario.retornarUsuario(emailVendor);
        Usuario moderador = daoUsuario.retornarUsuario(emailModerador);

        subasta.setVendedor(vendedor);
        subasta.setModerador((Coleccionista) moderador);
        subasta.setTipoUsuario(vendedor.getClass().getSimpleName());
        subasta.setItemesSubastadas(itemsSubasta);

        return subasta;
    }

    public Oferta retornarFullOferta(Oferta oferta) throws Exception {
        oferta = daoOferta.retornarOfertaEspecifica(oferta);
        Coleccionista comprador = (Coleccionista) daoUsuario.retornarUsuario(daoOferta.retornarCorreoComprador(oferta));
        oferta.setComprador(comprador);

        return oferta;
    }

    public ItemSubasta retornarFullItemSubasta(ItemSubasta item) throws Exception {
        item = daoItemSubasta.retornarFullItem(item);
        try {
            item.setVendedor(daoUsuario.retornarUsuario(daoItemSubasta.retornarEmailVendedorItem(item)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return item;
    }
    public void clickVerSubastas(javafx.event.ActionEvent actionEvent) throws Exception {
        cambiarJavaFXScene(actionEvent,"Subastas");
    }
    public void clickVolverSubastas(javafx.event.ActionEvent actionEvent) throws Exception {
        cambiarJavaFXScene(actionEvent,"Subastas");
    }
}

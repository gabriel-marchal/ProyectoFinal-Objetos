package marchal.gabriel.tl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import marchal.gabriel.bl.ItemSubasta.ItemSubasta;
import marchal.gabriel.bl.Oferta.Oferta;
import marchal.gabriel.bl.Subasta.Subasta;
import marchal.gabriel.bl.Usuario.Usuario;

import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;

public class PerfilUsuarioController extends MainController implements Initializable{
    //static Stage primaryStage;
    @FXML
    Label bienvenido;
    @FXML
    Label miEmail;
    @FXML
    Label miIdentificacion;
    @FXML
    Label miFechaNacimiento;
    @FXML
    Label miEdad;
    @FXML
    Label tipoUsuario;
    @FXML
    ImageView miAvatar;

    @FXML ListView listMisSubastas;
    @FXML ListView listMisItemes;
    @FXML ListView listMisOfertas;

    public PerfilUsuarioController() throws Exception {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Usuario usuario =logica.getUsuarioLogeado();
        System.out.println(usuario);

        try {
            miAvatar.setImage(new Image (String.valueOf(getClass().getResource(usuario.getAvatar()))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            miAvatar.setImage(new Image ("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));
        }

        bienvenido.setText("Bienvenido "+usuario.getNombre() +"!");
        miEmail.setText(usuario.getCorreoElectronico());
        miIdentificacion.setText(usuario.getIdentificacion());
        miFechaNacimiento.setText(usuario.getFechaNacimiento().toString());
        miEdad.setText(String.valueOf(usuario.getEdad()));

        try {
            tipoUsuario.setText(usuario.getClass().getSimpleName());
        } catch (Exception e) {
            tipoUsuario.setText("Usuario");
            System.out.println(e.getMessage());
        }

        ArrayList<Subasta> misSubastas = null;
        try {
            misSubastas = daoSubasta.obtenerMisSubastas(usuario);
            System.out.println("Mis Subastas Size: "+misSubastas.size());
            for (Subasta tempSubasta: misSubastas ) {
                tempSubasta = retornarFullSubasta(tempSubasta);
                System.out.println(tempSubasta);
                //listMisSubastas.getItems().add(tempSubasta);
                listMisSubastas.getItems().add(tempSubasta.getId()+" "+tempSubasta.getEstado()+". Itemes: "+tempSubasta.getItemesSubastadas().size()+". Oferta Minima: "+tempSubasta.getOfertaMinima());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<ItemSubasta> misItemes = null;
        try {
            misItemes = daoItemSubasta.getMisItemes(usuario.getCorreoElectronico());
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        for (ItemSubasta tempItem  : misItemes ) {
            listMisItemes.getItems().add(tempItem.getNombre()+" "+tempItem.getCategoria()+" "+tempItem.getAntiguedad());
        }

        try {
            ArrayList<Oferta> misOfertas = daoOferta.retornarOfertaXUsuario(usuario);
            ArrayList<Oferta> misOfertasFull = new ArrayList<>();

            for (Oferta tempOferta:misOfertas ) {
                tempOferta = retornarFullOferta(tempOferta);
                misOfertasFull.add(tempOferta);
            }
            for (Oferta tempOfertaFull: misOfertasFull ) {
                listMisOfertas.getItems().add("Subasta: "+tempOfertaFull.getIdSubasta()+", Mi Oferta: "+tempOfertaFull.getPrecioOferta());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickCrearSubasta(javafx.event.ActionEvent actionEvent) throws Exception {
        cambiarJavaFXScene(actionEvent,"CrearSubasta");

    }
    public void clickAnadirItem(javafx.event.ActionEvent actionEvent)throws Exception {
        cambiarJavaFXScene(actionEvent,"CrearItem");

    }
}

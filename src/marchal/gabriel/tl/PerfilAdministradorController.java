package marchal.gabriel.tl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import marchal.gabriel.bl.Administrador.Administrador;
import marchal.gabriel.bl.Categoria.Categoria;
import marchal.gabriel.bl.Coleccionista.Coleccionista;
import marchal.gabriel.bl.Usuario.Usuario;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PerfilAdministradorController extends MainController implements Initializable {
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

    @FXML
    ListView listUsuarios;
    @FXML
    Label cantUsuarios;
    @FXML
    Label cantCategorias;

    public PerfilAdministradorController() throws Exception {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Usuario usuario =logica.getUsuarioLogeado();
        System.out.println(usuario);

        try {
            miAvatar.setImage(new Image(String.valueOf(getClass().getResource(usuario.getAvatar()))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            miAvatar.setImage(new Image ("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));
        }

        try {
            bienvenido.setText("Bienvenido "+usuario.getNombre() +"!");
            miEmail.setText(usuario.getCorreoElectronico());
            miIdentificacion.setText(usuario.getIdentificacion());
            miFechaNacimiento.setText(usuario.getFechaNacimiento().toString());
            miEdad.setText(String.valueOf(usuario.getEdad()));
            if(usuario instanceof Administrador) {
                tipoUsuario.setText("Administrador");
            }else if(usuario instanceof Coleccionista){
                tipoUsuario.setText("Coleccionista");
            }else if(((Coleccionista) usuario).isEsModerador()){
                tipoUsuario.setText("Moderador");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        ArrayList<Usuario> usuarios = null;
        try {
            usuarios = imprimirUsuarios();
            for (Usuario tempUsuario  : usuarios ) {
                listUsuarios.getItems().add(tempUsuario);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        cantUsuarios.setText("Usuarios: "+ usuarios.size());

        ArrayList<Categoria> categorias= null;
        try {
            categorias = daoCategoria.retornarCategorias();
            cantCategorias.setText("Categorias: "+ categorias.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void clickAgregarCategoria(javafx.event.ActionEvent actionEvent) throws Exception{
        cambiarJavaFXScene(actionEvent,"ConfigurarCategorias");
    }
    public void clickConfigurarUsuarios(javafx.event.ActionEvent actionEvent) throws Exception{
        cambiarJavaFXScene(actionEvent,"ConfigurarUsuarios");
    }
}

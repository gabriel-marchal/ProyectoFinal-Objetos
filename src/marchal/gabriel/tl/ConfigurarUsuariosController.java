package marchal.gabriel.tl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marchal.gabriel.bl.Administrador.Administrador;
import marchal.gabriel.bl.Coleccionista.Coleccionista;
import marchal.gabriel.bl.Usuario.Usuario;
import org.apache.commons.mail.EmailException;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConfigurarUsuariosController extends MainController implements Initializable {

    @FXML
    ListView listaUsuarios;

    @FXML
    Label nombreUsuario;
    @FXML
    Label fechaNacimientoUsuario;
    @FXML
    ImageView avatarUsuario;
    @FXML
    Label estadoUsuario;
    @FXML
    CheckBox estadoUsuarioCheck;
    @FXML
    Label identificacionUsuario;
    @FXML
    Label correoElectronicoUsuario;

    @FXML
    Label direccionUsuario;
    @FXML
    Label esModeradorUsuario;
    @FXML
    CheckBox esModeradorUsuarioCheck;

    private ArrayList<Usuario> usuarios;

    public ConfigurarUsuariosController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            actualizarListaUsuarios();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void actualizarListaUsuarios() throws Exception {
        usuarios = imprimirUsuarios();
        ObservableList<Usuario> usuariosObsList = FXCollections.observableList(usuarios);
        listaUsuarios.setItems(usuariosObsList);
    }

    public void clickSeleccionar(javafx.event.ActionEvent actionEvent){
        int index = listaUsuarios.getSelectionModel().getSelectedIndex();
        Usuario usuario = usuarios.get(index);
        nombreUsuario.setText(usuario.getNombre());
        identificacionUsuario.setText(usuario.getIdentificacion());
        fechaNacimientoUsuario.setText(retornarString_LOCALDATE(usuario.getFechaNacimiento()));
        if(usuario instanceof Administrador) {
            direccionUsuario.setVisible(false);
            esModeradorUsuario.setVisible(false);
            esModeradorUsuarioCheck.setVisible(false);
        }else{
            direccionUsuario.setVisible(true);
            direccionUsuario.setText(((Coleccionista) usuario).getDireccion());
            esModeradorUsuario.setVisible(true);
            esModeradorUsuarioCheck.setVisible(true);
        }

        correoElectronicoUsuario.setText(usuarios.get(index).getCorreoElectronico());

        try {
            avatarUsuario.setImage(new Image((String.valueOf(getClass().getResource(usuarios.get(index).getAvatar())))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            avatarUsuario.setImage(new Image ("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));
        }
        if(usuario.isEstado()){
            estadoUsuario.setText("Activo");
            estadoUsuarioCheck.setSelected(true);
        }else{
            estadoUsuario.setText("Inactivo");
            estadoUsuarioCheck.setSelected(false);
        }

        if(usuario instanceof Coleccionista && ((Coleccionista) usuario).isEsModerador()){
            esModeradorUsuario.setText("Si");
            esModeradorUsuarioCheck.setSelected(true);
        }else{
            esModeradorUsuario.setText("No");
            esModeradorUsuarioCheck.setSelected(false);
        }
    }
    public void clickModificarUsuario() throws Exception {
        Usuario usuario = daoUsuario.retornarUsuario(correoElectronicoUsuario.getText());
        if(usuario instanceof Coleccionista){
            boolean esModeradorPrev = ((Coleccionista) usuario).isEsModerador();
            boolean esModeradorNue = esModeradorUsuarioCheck.isSelected();
            try {
                ((Coleccionista) usuario).setEsModerador(esModeradorUsuarioCheck.isSelected());
                usuario.setEstado(estadoUsuarioCheck.isSelected());
                daoUsuario.editarPerfilUsuario(usuario);
                //logica.editarInfoColeccionistaAdmin(correoElectronicoUsuario.getText(),estadoUsuarioCheck.isSelected(),esModeradorUsuarioCheck.isSelected());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Usuario "+correoElectronicoUsuario.getText()+" modificado exitosamente.");
                alert.show();
                if(enviarEmailSelecModerador(esModeradorPrev,esModeradorNue)){
                    try {
                        enviarEmailAdminSelectModerador(usuario);
                        System.out.println("Email enviar seleccionado moderador");
                    } catch (Exception e) {
                        System.out.println("Error con email seleccionado moderador");
                        System.out.println(e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR,"Por favor seleccione un usuario de la lista.");
                alert.show();
            }finally {
                if(esModeradorUsuarioCheck.isSelected()){
                    esModeradorUsuario.setText("Si");
                }else  {
                    esModeradorUsuario.setText("No");
                }
                if(estadoUsuarioCheck.isSelected()){
                    estadoUsuario.setText("Activo");
                }else{
                    estadoUsuario.setText("Inactivo");
                }
                actualizarListaUsuarios();
            }
        }else{
            try {
                usuario.setEstado(estadoUsuarioCheck.isSelected());
                daoUsuario.editarPerfilUsuario(usuario);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Usuario "+correoElectronicoUsuario.getText()+" modificado exitosamente.");
                alert.show();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR,"Por favor seleccione un usuario de la lista.");
                alert.show();
            }finally {
                if(estadoUsuarioCheck.isSelected()){
                    estadoUsuario.setText("Activo");
                }else{
                    estadoUsuario.setText("Inactivo");
                }
                actualizarListaUsuarios();
            }
        }
    }

    public boolean enviarEmailSelecModerador(boolean esModeradorPrev, boolean esModeradorNue) {
        if(!esModeradorPrev && esModeradorNue){
            return true;
        }
        return false;
    }
}

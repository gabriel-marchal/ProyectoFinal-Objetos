package marchal.gabriel.tl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import marchal.gabriel.bl.Coleccionista.Coleccionista;
import marchal.gabriel.bl.Usuario.Usuario;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditarPerfilController extends MainController implements Initializable {
    static LocalDate fechaDeNacimiento;
    /**
     * Labels predeterminados
     */
    @FXML
    Label miNombre;

    @FXML
      Label miIdentificacion;
    @FXML
      Label miFechaNacimiento;
    @FXML
      ImageView miAvatar;
    @FXML
      Label labelDireccion;
    @FXML
      Label miDireccion;
    @FXML
      ComboBox avatarInput;

    /**
     * Inputs segun seleccion
     */
    @FXML
    TextField nuevoIdentificacion;

    @FXML
    DatePicker nuevoFechaNacimiento;
    @FXML
    TextArea nuevoDireccion;
    @FXML
    TextField nuevoNombre;

    @FXML
    Button botonDireccion;
    @FXML
    Button btnMisIntereses;

    static Usuario usuario;

    public EditarPerfilController() throws Exception {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        avatarInput.getItems().addAll(
                "avatar1",
                "avatar2",
                "avatar3",
                "avatar4",
                "avatar5",
                "avatar6",
                "avatar7",
                "avatar8",
                "avatar9",
                "avatar10"
        );

        usuario = getUsuarioLogeado();
        System.out.println(usuario);

        try {
            miAvatar.setImage(new Image(String.valueOf(getClass().getResource(usuario.getAvatar()))));
        } catch (Exception e) {
            miAvatar.setImage(new Image ("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));
            System.out.println(e.getMessage());
        }
        miNombre.setText(usuario.getNombre());
        miIdentificacion.setText(usuario.getIdentificacion());
        fechaDeNacimiento = getUsuarioLogeado().getFechaNacimiento();
        miFechaNacimiento.setText(retornarString_LOCALDATE(fechaDeNacimiento));

        if(!(usuario instanceof Coleccionista)){
            miDireccion.setVisible(false);
            labelDireccion.setVisible(false);
            nuevoDireccion.setVisible(false);
            botonDireccion.setVisible(false);
            btnMisIntereses.setVisible(false);
        }else {
            miDireccion.setText(((Coleccionista)usuario).getDireccion());
        }
    }

    public void cambiarAvatar(javafx.event.ActionEvent actionEvent) throws Exception {
        String imagen = "../assets/avatars/"+avatarInput.getSelectionModel().getSelectedItem().toString()+".png";
        miAvatar.setImage(new Image (String.valueOf(getClass().getResource(imagen))));

    }
    public void clickCambiarIdentificacion(javafx.event.ActionEvent actionEvent)throws Exception{
        miIdentificacion.setText(nuevoIdentificacion.getText());
    }

    public void clickCambiarFechaNaci(javafx.event.ActionEvent actionEvent)throws Exception{
        // Validar 18 anios
        if(validarMayorEdad(nuevoFechaNacimiento.getValue())){
            fechaDeNacimiento = nuevoFechaNacimiento.getValue();
            miFechaNacimiento.setText(retornarString_LOCALDATE(fechaDeNacimiento));
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Tiene que ser mayor de edad");
            alert.show();
        }
    }
    public void clickCambiarDireccion(javafx.event.ActionEvent actionEvent)throws Exception{
        miDireccion.setText(nuevoDireccion.getText());
    }
    public void clickCambiarNombre(javafx.event.ActionEvent actionEvent)throws Exception{
        miNombre.setText(nuevoNombre.getText());
    }

    public void clickGuardarCambios(javafx.event.ActionEvent actionEvent) throws Exception {
        String avatar = "";
        if (avatarInput.getSelectionModel().getSelectedItem() == null) {
            avatar = getUsuarioLogeado().getAvatar();
        } else {
            avatar = "../assets/avatars/" + avatarInput.getSelectionModel().getSelectedItem().toString() + ".png";
        }

        try {
            if (usuario instanceof Coleccionista) {
                usuario.setAvatar(avatar);
                usuario.setIdentificacion(miIdentificacion.getText());
                usuario.setFechaNacimiento(fechaDeNacimiento);
                ((Coleccionista) usuario).setDireccion(miDireccion.getText());
                usuario.setNombre(miNombre.getText());
            } else {
                usuario.setAvatar(avatar);
                usuario.setIdentificacion(miIdentificacion.getText());
                usuario.setFechaNacimiento(fechaDeNacimiento);
                usuario.setNombre(miNombre.getText());
            }
            if (daoUsuario.editarPerfilUsuario(usuario) == 1) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Informacion cambiado de manera exitosa.");
                alert.show();
                clickVolverPerfil(actionEvent);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Hubo un error en la actualizacion de datos.\nFavor corregir e volver a intentar.");
                alert.show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void clickMisIntereses(javafx.event.ActionEvent actionEvent) throws Exception {
        cambiarJavaFXScene(actionEvent,"ConfigurarMisIntereses");

    }
}

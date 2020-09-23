package marchal.gabriel.tl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import marchal.gabriel.bl.Usuario.Usuario;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class CambiarContrasenaController extends MainController implements Initializable {

    @FXML
    PasswordField antiguaContrasena;
    @FXML
    PasswordField nuevaContrasena;
    @FXML
    PasswordField confirmarContrasena;

    Usuario usuario;

    public CambiarContrasenaController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usuario = getUsuarioLogeado();
    }

    public void clickActualizarContrasena(javafx.event.ActionEvent actionEvent) throws Exception {
        if(validarCredenciales(usuario.getCorreoElectronico(),antiguaContrasena.getText())){
            if(nuevaContrasena.getText().equals(confirmarContrasena.getText())){
                daoUsuario.cambiarContrasena(usuario,nuevaContrasena.getText());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Contrasena cambiado de manera exitosa.");
                alert.show();
                clickVolverPerfil(actionEvent);
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING, "Las nuevas contrasenas tienen que coincidir.");
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Contrasena antigua no coincide.");
            alert.show();
        }
    }
}

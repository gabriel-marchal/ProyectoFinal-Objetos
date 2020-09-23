package marchal.gabriel.tl;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import marchal.gabriel.bl.Administrador.Administrador;
import marchal.gabriel.bl.Usuario.Usuario;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginPageController extends MainController implements Initializable {


    private static String emailUsuario;

    @FXML
    TextField emailInput;
    @FXML
    PasswordField passwordinput;

    public LoginPageController() throws Exception {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            System.out.println("Usuario: "+logica.getUsuarioLogeado());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void clickSignIn(javafx.event.ActionEvent actionEvent) throws Exception {
        if(validarAdminExis()){
            if(validarCredenciales(emailInput.getText(),passwordinput.getText())){ //TODO: Intentar implementar validacion con Hash
                emailUsuario = emailInput.getText();
                Usuario usuario = daoUsuario.retornarUsuario(emailUsuario);
                if(usuario.isEstado()){
                    System.out.println("Login exitoso!");
                    signInSuccess(actionEvent);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Esta cuenta se encuentra desactiva.");
                    alert.show();
                }
            }else{
                System.out.println("Login invalido");
                Alert alert = new Alert(Alert.AlertType.ERROR,"Login invalido\nCorreo electronico o contraseña incorrecta.");
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"No hay administrador activo actualmente registrado\nFavor registrarse como Admin para seguir.");
            alert.show();
        }
    }

    public boolean validarAdminExis() throws Exception {
        ArrayList<Usuario> administradores = daoAdministrador.retornarTodosAdmin();
        for (Usuario tempAdmin:administradores ) {
            if(tempAdmin.isEstado()){
                return true;
            }
        }
        return false;
    }


    public void signInSuccess(ActionEvent actionEvent)throws Exception{
        logica.setUsuarioLogeado(daoUsuario.retornarUsuario(emailUsuario));
        if(logica.getUsuarioLogeado() instanceof Administrador){
            cambiarJavaFXScene(actionEvent,"PerfilAdministrador");
        }else{
            cambiarJavaFXScene(actionEvent,"PerfilUsuario");
        }
    }

    public  void clickRegistrarUsuario(ActionEvent actionEvent) throws Exception {
        cambiarJavaFXScene(actionEvent,"RegistrarUsuario");
    }
}

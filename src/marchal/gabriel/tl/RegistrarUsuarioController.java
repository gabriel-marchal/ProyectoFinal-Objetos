package marchal.gabriel.tl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import marchal.gabriel.bl.Administrador.Administrador;
import marchal.gabriel.bl.Coleccionista.Coleccionista;
import marchal.gabriel.bl.Usuario.Usuario;
import org.apache.commons.mail.EmailException;

public class RegistrarUsuarioController extends MainController implements Initializable {
    @FXML
    TextField nombreInput;
    @FXML
    TextField identificacionInput;
    @FXML
    TextField correoElectronicoInput;
    @FXML
    TextField contrasenaInput;
    @FXML
    ComboBox avatarInput;
    @FXML
    DatePicker fechaNacimientoInput;
    @FXML
    TextField direccion;
    @FXML
    Label labelDireccion;
    @FXML
    CheckBox soyAdministrador;

    public RegistrarUsuarioController() throws Exception {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        soyAdministrador.setSelected(false);
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
    }

    public void clickRegistrarme(javafx.event.ActionEvent actionEvent) throws Exception{

        try {
            String nombre = nombreInput.getText();
            String identificacion = identificacionInput.getText();
            String correoElectronico = correoElectronicoInput.getText();
            String contrasena = contrasenaInput.getText();
            String avatar = "../assets/avatars/"+avatarInput.getSelectionModel().getSelectedItem().toString()+".png";
            System.out.println("Nombre: "+nombre);
            System.out.println("Fecha nacimiento: "+ fechaNacimientoInput.getValue());
            System.out.println(avatar);
            LocalDate fechaNacimientoConv = LocalDate.of(fechaNacimientoInput.getValue().getYear(),fechaNacimientoInput.getValue().getMonth(),fechaNacimientoInput.getValue().getDayOfMonth());


            if(validarMayorEdad(fechaNacimientoConv)){
                if(daoUsuario.retornarUsuario(correoElectronico) ==null){
                    Usuario usuario;
                    if(soyAdministrador.isSelected()){
                        if(!validarExistAdmin()){
                            usuario = new Administrador(nombre,identificacion,fechaNacimientoConv,correoElectronico,contrasena,true,avatar);
                            System.out.println("Administrador seleccionado");
                            registrarUsuario(actionEvent, usuario);
                        }else{
                            Alert alert = new Alert(Alert.AlertType.ERROR,"Ya hay un administrador activo registrado");
                            alert.show();
                        }
                    }else{
                        usuario = new Coleccionista(nombre, identificacion, fechaNacimientoConv, correoElectronico, contrasena, true, avatar,direccion.getText(),null,null,5,false );
                        System.out.println("Coleccionista seleccionado");
                        registrarUsuario(actionEvent,usuario);
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Ese correo ya esta registrado!");
                    alert.show();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING,"Tiene que ser mayor de edad para registrarse!");
                alert.show();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Algun error succedio.\nFavor corregir y volver a intentar.");
            alert.show();
            System.out.println(e.getMessage());
        }

    }

    public boolean validarExistAdmin() throws Exception {
        ArrayList<Administrador> administradores =daoAdministrador.obtenerAdministradores();//logica.getTodosAdmins()
        for (Administrador tempAdmin:administradores) {
            if(tempAdmin.isEstado()){
                return true;
            }
        }
        return false;
    }
    public void registrarUsuario(javafx.event.ActionEvent actionEvent,Usuario usuario) throws Exception {
        if(usuario instanceof Coleccionista){
            daoColeccionista.registrarColeccionista((Coleccionista) usuario);
        }else{
            daoAdministrador.registrarAdministrador((Administrador) usuario);
        }
        //logica.registrarUsuario(usuario);
        logica.setUsuarioLogeado(usuario);
        enviarEmailBienvenida(usuario);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Registrado con exito!");
        Optional<ButtonType> result = alert.showAndWait();
        System.out.println("Registrado " + usuario.getCorreoElectronico()+" con exito");
        if(result.get() == ButtonType.OK){
            System.out.println("OK pressed");
            okButtonPressed(usuario, actionEvent);

        }else if(result.get() == ButtonType.CANCEL){
            System.out.println("CANCEL pressed");
        }else{
            System.out.println("lol");
        }
    }

    public void okButtonPressed(Usuario usuario, ActionEvent actionEvent) throws Exception {
        if(usuario instanceof Administrador){
            cambiarJavaFXScene(actionEvent,"PerfilAdministrador");
        }else{
            cambiarJavaFXScene(actionEvent,"PerfilUsuario");
        }
    }
    public void clickSoyAdmin(ActionEvent actionEvent){
        if(soyAdministrador.isSelected()) {
            direccion.setVisible(false);
            labelDireccion.setVisible(false);
        }else{
            direccion.setVisible(true);
            labelDireccion.setVisible(true);
        }
    }
}

package marchal.gabriel.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import marchal.gabriel.bl.Usuario.Usuario;
import marchal.gabriel.tl.MainController;
import java.util.ArrayList;

public class Main extends Application {

    static MainController gestor;

    static {
        try {
            gestor = new MainController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
        primaryStage.setTitle("Collectors Bazar");
        primaryStage.setScene(new Scene(root));
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("../assets/icon.png"))));
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception{

        System.out.println("DEFAULT USUARIOS REGISTRADOS EN MAIN -- NO OLVIDAR DELETE");
        ArrayList<Usuario> datosusuarios = gestor.imprimirUsuarios();
        for (Usuario tempUsuario  :datosusuarios ) {
            System.out.println("> "+ tempUsuario.toString());
        }
        System.out.println("---------------------------------------------------------");

        System.out.println("INICIADO APP START - ACTUALIZAR SUBASTAS Y ORDENES DE COMPRA");
        gestor.INICIOAPP_enviarOrdenesDeSubastasFinalizadas();
        System.out.println("******************FINALIZADO APP START**********************");


        launch(args);
    }
}

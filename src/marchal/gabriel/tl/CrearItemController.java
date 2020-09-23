package marchal.gabriel.tl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import marchal.gabriel.bl.ItemSubasta.ItemSubasta;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CrearItemController extends MainController implements Initializable {

    @FXML
    TextField inputNombre;
    @FXML
    ComboBox inputCategoria;
    @FXML
    ComboBox inputEstado;
    @FXML
    DatePicker inputFechacompra;

    @FXML ComboBox inputImagen1;
    @FXML ComboBox inputImagen2;
    @FXML ComboBox inputImagen3;
    @FXML
    TextArea inputDescripcion;

    @FXML
    ImageView outputImage;

    public CrearItemController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inputEstado.getItems().addAll(
                "Nuevo","Usado","Antiguo sin abrir"
        );
        try {
            inputCategoria.getItems().addAll(getNombreCategorias());
        } catch (Exception e) {
            e.printStackTrace();
        }

        inputImagen1.getItems().addAll("pixel2.jpg","pixel22.jpg","pixel4.jpg","hummer1.jpg","hummer2.jpg","hummer3.jpg","moto1.jpg","moto2.jpg","moto3.jpg","casa1.jpg","casa2.jpg","casa3.jpg");
        inputImagen2.getItems().addAll("pixel2.jpg","pixel22.jpg","pixel4.jpg","hummer1.jpg","hummer2.jpg","hummer3.jpg","moto1.jpg","moto2.jpg","moto3.jpg","casa1.jpg","casa2.jpg","casa3.jpg");
        inputImagen3.getItems().addAll("pixel2.jpg","pixel22.jpg","pixel4.jpg","hummer1.jpg","hummer2.jpg","hummer3.jpg","moto1.jpg","moto2.jpg","moto3.jpg","casa1.jpg","casa2.jpg","casa3.jpg");

    }
    public void clickSubirItem(javafx.event.ActionEvent actionEvent)throws IOException {

        try {
            String nombre = inputNombre.getText();
            String categoria = inputCategoria.getSelectionModel().getSelectedItem().toString();
            String estado = inputEstado.getSelectionModel().getSelectedItem().toString();
            LocalDate fechaCompra = inputFechacompra.getValue();
            String descripcion = inputDescripcion.getText();
            ArrayList<String> imagenes = getImagenes();
            System.out.println(imagenes.size());
            ItemSubasta item = new ItemSubasta(daoItemSubasta.getNextItemId(),nombre,descripcion,estado,imagenes,fechaCompra,categoria,getUsuarioLogeado());
            daoItemSubasta.registrarItemSubasta(item);
            for (String imagenString :imagenes ) {
                daoItemSubasta.registrarImagenesItemes(item.getId(),imagenString);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Item "+nombre+"\nCreado exitosamente");
            alert.show();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error en la creacion del Item\nFavor volver a intentar.");
            alert.show();
            System.out.println(e.getMessage());
        }
    }
    public void cambiarImagen(javafx.event.ActionEvent actionEvent)throws IOException {
        String imagen = "../assets/items/"+inputImagen1.getSelectionModel().getSelectedItem().toString();
        outputImage.setImage(new Image (String.valueOf(getClass().getResource(imagen))));
    }
    public ArrayList<String> getImagenes(){
        ArrayList<String> imagenes = new ArrayList<>();
        try {
            String imagen1 ="../assets/items/"+ inputImagen1.getSelectionModel().getSelectedItem().toString();
            String imagen2 ="../assets/items/"+ inputImagen2.getSelectionModel().getSelectedItem().toString();
            String imagen3 ="../assets/items/"+ inputImagen3.getSelectionModel().getSelectedItem().toString();
            imagenes.add(imagen1);
            imagenes.add(imagen2);
            imagenes.add(imagen3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return imagenes;
    }

}

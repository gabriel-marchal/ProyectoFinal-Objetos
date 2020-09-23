package marchal.gabriel.tl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import marchal.gabriel.bl.Categoria.Categoria;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConfigurarCategoriasController extends MainController implements Initializable {


    @FXML
    ListView listaCategorias;
    @FXML
    TextField nombre;
    @FXML
    TextArea descripcion;


    @FXML
    Label nombreCategoriaBorrar;
    @FXML
    Text descripcionCategoriaBorrar;


    private ArrayList<Categoria> categorias;

    public ConfigurarCategoriasController() throws Exception {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources){
        try {
            actualizarListaCategorias();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void actualizarListaCategorias() throws Exception {
        categorias = daoCategoria.retornarCategorias();
        ObservableList<Categoria> observableList = FXCollections.observableList(categorias);
        listaCategorias.setItems(observableList);
    }

    public void clickSeleccionar(javafx.event.ActionEvent actionEvent) {
        int categoria = listaCategorias.getSelectionModel().getSelectedIndex(); // Retorna el INDEX del array en ListView
        nombreCategoriaBorrar.setText(String.valueOf(categorias.get(categoria).getNombre()));
        descripcionCategoriaBorrar.setText(String.valueOf(categorias.get(categoria).getDescripcion()));
    }

    public void clickAgregarCategoria(javafx.event.ActionEvent actionEvent) throws Exception {
        try {
            // TODO FIX dejo de funcionar de la nada
            if(nombre.getText() != null && descripcion.getText() != null){
                if(!validarExisCategoria(nombre.getText())){
                    int nextCodido = daoCategoria.getNextCodigoCategoria();
                    Categoria nuevaCategoria = new Categoria(nextCodido,nombre.getText(),descripcion.getText());
                    daoCategoria.registrarCategoria(nuevaCategoria);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,"Categoria "+nuevaCategoria.getNombre()+" registrado con exito.");
                    alert.show();
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING,"Esa categoria ya exite.");
                    alert.show();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING,"Los campos no puedes estar vacios.");
                alert.show();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Los campos no puedes estar vacios.");
            alert.show();
            System.out.println(e.getMessage());
        }finally {
            actualizarListaCategorias();
        }
    }

    public boolean validarExisCategoria(String nombre){
        boolean existe = false;
        try {
            Categoria categoria = daoCategoria.retornarCategoriaEspecifica(nombre);
            if(categoria !=null){
                existe = true;
            }else{
                existe = false;
            }
        } catch (Exception e) {
            existe = false;
            System.out.println(e.getMessage());
        }
        return existe;
    }
    public void clickBorrarCategoria(javafx.event.ActionEvent actionEvent) throws Exception {
        try {
            Categoria nuevaCategoria = daoCategoria.retornarCategoriaEspecifica(nombreCategoriaBorrar.getText());
            daoCategoria.eliminarCategoria(nuevaCategoria);
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Categoria "+nuevaCategoria.getNombre()+" borrada con exito.");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Los campos no puedes estar vacios\nFavor seleccionar una categoria de la lista.");
            alert.show();
            System.out.println(e.getMessage());
        }finally {
            nombreCategoriaBorrar.setText("");
            descripcionCategoriaBorrar.setText("");
            actualizarListaCategorias();
        }
    }
}
package marchal.gabriel.tl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ListView;
import marchal.gabriel.bl.Categoria.Categoria;
import marchal.gabriel.bl.Coleccionista.Coleccionista;
import marchal.gabriel.bl.Usuario.Usuario;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConfigurarMisIntereses extends MainController implements Initializable {

    @FXML
    ListView listCategorias;
    @FXML ListView listMisIntereses;


    static ArrayList<Categoria> misInteresesArray = new ArrayList<>();
    static ArrayList<Categoria> categorias;

    Coleccionista usuario = null;



    public ConfigurarMisIntereses() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usuario =(Coleccionista) getUsuarioLogeado();
        try {
            usuario.setIntereses(daoCategoria.retornarMisIntereses(usuario));
            System.out.println("INTERESSSSESSS: "+ usuario.getIntereses());
            try {
                misInteresesArray = usuario.getIntereses();
                listMisIntereses.getItems().addAll(misInteresesArray);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            categorias = daoCategoria.retornarCategorias();
            listCategorias.getItems().addAll(getNombreCategorias());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickAgregarInteres(javafx.event.ActionEvent actionEvent) throws Exception {
        try {
            int index = listCategorias.getSelectionModel().getSelectedIndex();

            listMisIntereses.getItems().add(categorias.get(index));
            System.out.println("CATEGORIAS INDEX "+index+" ="+categorias.get(index));
            misInteresesArray.add(categorias.get(index));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        //actualizarMisInteres();

    }


    public void clickRemoverInteres(javafx.event.ActionEvent actionEvent) throws Exception {

        try {
            int index = listMisIntereses.getSelectionModel().getSelectedIndex();
            listMisIntereses.getItems().remove(index);
            misInteresesArray.remove(index);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        //actualizarMisInteres();
    }

    private void actualizarMisInteres() throws Exception {
        try {
            //misInteresesArray.clear();
            misInteresesArray = usuario.getIntereses();
            ObservableList<Categoria> observableList = FXCollections.observableList(misInteresesArray);
            listMisIntereses.setItems(observableList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void clickGuardarCambios(javafx.event.ActionEvent actionEvent){
        System.out.println(usuario.getIntereses());
        try {
            usuario.setIntereses(misInteresesArray);
            daoColeccionista.eliminarMisIntereses(usuario);
            for (Categoria categoriaTemp: usuario.getIntereses() ) {
                daoColeccionista.registrarMisIntereses(usuario,categoriaTemp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}

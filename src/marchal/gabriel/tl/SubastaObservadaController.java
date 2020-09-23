package marchal.gabriel.tl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import marchal.gabriel.bl.Administrador.Administrador;
import marchal.gabriel.bl.Coleccionista.Coleccionista;
import marchal.gabriel.bl.ItemSubasta.ItemSubasta;
import marchal.gabriel.bl.Oferta.Oferta;
import marchal.gabriel.bl.Subasta.Subasta;

import java.io.IOException;
import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class SubastaObservadaController extends MainController implements Initializable {

    private static Subasta subastaObservado;

    @FXML
    Label subastaObservadoID;

    @FXML Label vendedorLabel;
    @FXML Label moderadorLabel;

    @FXML Label fechaFinalizacionLabel;
    @FXML Label tiempoRestanteLabel;

    @FXML Label estadoLabel;
    @FXML Label precioLabel;
    @FXML
    TextField ofertarTextField;
    @FXML Label LabelOfertar;
    @FXML Label ofertaMinimaLabel;

    @FXML
    ListView itemesSubastados;

    @FXML
    ImageView image1;
    @FXML ImageView image2;
    @FXML ImageView image3;


    @FXML
    Button btnOfertar;

    @FXML
    CheckBox checkCerrar;
    @FXML
    Button btnCerrar;



    public SubastaObservadaController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        image1.setImage(new Image("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));
        image2.setImage(new Image("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));
        image3.setImage(new Image("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));

        subastaObservado = logica.getSubastaObservado();

        if(!getUsuarioLogeado().equals(subastaObservado.getModerador())){
            checkCerrar.setVisible(false);
            btnCerrar.setVisible(false);
        }


        subastaObservadoID.setText("Subasta: "+subastaObservado.getId());

        itemesSubastados.getItems().addAll(subastaObservado.getItemesSubastadas());

        vendedorLabel.setText(subastaObservado.getVendedor().getNombre());
        moderadorLabel.setText(subastaObservado.getModerador().getNombre());

        fechaFinalizacionLabel.setText(retornarString_LOCALDATETIME(subastaObservado.getFechaFinalizacion()));
        estadoLabel.setText(subastaObservado.getEstado());
        if(subastaObservado.getEstado().equals("iniciada")){
            estadoLabel.setStyle("-fx-text-fill: green");
            ofertarTextField.setVisible(true);
            btnOfertar.setVisible(true);
            LabelOfertar.setVisible(true);
            Duration duration = Duration.between(LocalDateTime.now(),subastaObservado.getFechaFinalizacion());
            int days = (int) duration.toDays();
            int hours = (int) duration.minusDays(days).toHours();
            int minutes = (int) ((duration.getSeconds() % (60 * 60)) / 60);
            int seconds = (int) (duration.getSeconds() % 60);

            String tiempoRestante =days+" dias, "+hours+" horas, "+minutes+" minutos, "+ seconds +" segundos";
            tiempoRestanteLabel.setText("Finaliza en: "+ tiempoRestante);

        }else if(subastaObservado.getEstado().equals("por iniciar")){
            estadoLabel.setStyle("-fx-text-fill: red");
            ofertarTextField.setVisible(false);
            btnOfertar.setVisible(false);
            LabelOfertar.setText("No puede ofertar aun");
            Duration duration = Duration.between(LocalDateTime.now(),subastaObservado.getFechaInicio());
            int days = (int) duration.toDays();
            int hours = (int) duration.minusDays(days).toHours();
            int minutes = (int) ((duration.getSeconds() % (60 * 60)) / 60);
            int seconds = (int) (duration.getSeconds() % 60);

            String tiempoRestante =days+" dias, "+hours+" horas, "+minutes+" minutos, "+ seconds +" segundos";
            tiempoRestanteLabel.setText("Inicia en: "+ tiempoRestante);
        }else{
            ofertarTextField.setVisible(false);
            btnOfertar.setVisible(false);
            LabelOfertar.setVisible(false);
            tiempoRestanteLabel.setText("Finalizada");
        }

        DecimalFormat df1 = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df1.setMaximumFractionDigits(2);

        precioLabel.setText(String.valueOf(df1.format(subastaObservado.getPrecio())));

        try {
            ArrayList<Oferta> ofertas = daoOferta.retornarOfertasXSubasta(subastaObservado);
            if(ofertas.size()>0){
                double mayor = 0;
                for (Oferta ofertaTempPrecio :ofertas ) {
                    if(ofertaTempPrecio.getPrecioOferta() > mayor){
                        mayor = ofertaTempPrecio.getPrecioOferta();
                    }
                }
                DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                df.setMaximumFractionDigits(2);

                System.out.println(df.format(mayor));

                ofertaMinimaLabel.setText(df.format(mayor+1));
                //ofertaMinimaLabel.setText(String.valueOf(subastaObservado.getOfertaMinima()));
            }else{
                ofertaMinimaLabel.setText(String.valueOf(subastaObservado.getOfertaMinima()+1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> imagenesItems = new ArrayList<>();
        for (ItemSubasta tempItem: subastaObservado.getItemesSubastadas()) {
            ArrayList<String> imagenes = null;
            try {
                imagenes = daoItemSubasta.retornarImagenesItemes(subastaObservado.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            imagenesItems.addAll(imagenes);
        }
        try {
            image1.setImage(new Image(String.valueOf(getClass().getResource(imagenesItems.get(0)))));
            image2.setImage(new Image(String.valueOf(getClass().getResource(imagenesItems.get(1)))));
            image3.setImage(new Image(String.valueOf(getClass().getResource(imagenesItems.get(2)))));
        } catch (Exception e) {
            image1.setImage(new Image("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));
            image2.setImage(new Image("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));
            image3.setImage(new Image("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));
            System.out.println(e.getMessage());
        }
    }

    public void clickVerItem() throws Exception {
        int index = itemesSubastados.getSelectionModel().getSelectedIndex();

        ItemSubasta itemObservado = subastaObservado.getItemesSubastadas().get(index);
        ArrayList<String> imagenes = daoItemSubasta.retornarImagenesItemes(itemObservado.getId());
        try {
            image1.setImage(new Image(String.valueOf(getClass().getResource(imagenes.get(0)))));
            image1.setFitHeight(160);
            image1.setFitWidth(110);
            image2.setImage(new Image(String.valueOf(getClass().getResource(imagenes.get(1)))));
            image2.setFitHeight(160);
            image2.setFitWidth(110);
            image3.setImage(new Image(String.valueOf(getClass().getResource(imagenes.get(2)))));
            image3.setFitHeight(160);
            image3.setFitWidth(110);
        } catch (Exception e) {
            image1.setImage(new Image("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));
            image2.setImage(new Image("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));
            image3.setImage(new Image("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));
            System.out.println(e.getMessage());
        }
    }

    public void clickOfertar() throws Exception {
        if(getUsuarioLogeado() instanceof Coleccionista){
            if(!subastaObservado.getModerador().equals(getUsuarioLogeado()) || subastaObservado.getVendedor().equals(getUsuarioLogeado())){
                if(validarOferta()){
                    int nextId =  daoOferta.getNextIdOferta();
                    Oferta oferta = new Oferta(nextId,(Coleccionista) getUsuarioLogeado(),subastaObservado.getId(),Double.parseDouble(ofertarTextField.getText()),LocalDateTime.now());
                    daoOferta.registrarOferta(oferta);
                    daoOferta.registrarOfertasXSubasta(oferta,subastaObservado);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Oferta registrada exitosamente.");
                    alert.show();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Hay algun error con la oferta\nFavor corregir.");
                    alert.show();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"Ud no puede ofertar esta subasta.\nYa que es el vendedor o moderador.");
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Ud no puede ofertar esta subasta.\n!Coleccionista validacion");
            alert.show();
        }
    }

    public boolean validarOferta() {
        boolean exitoso = false;
        try {
            if(ofertarTextField.getText() !=null){
                double oferta = Double.parseDouble(ofertarTextField.getText());
                if(oferta >=Double.parseDouble(ofertaMinimaLabel.getText()) && oferta != 0){
                    exitoso = true;
                }else{
                    exitoso = false;
                }
            }

        } catch (Exception e) {
            exitoso = false;
            System.out.println(e.getMessage());
        }
        return exitoso;
    }

    public void clickVerOfertas(javafx.event.ActionEvent actionEvent) throws Exception {
        cambiarJavaFXScene(actionEvent,"OfertasPorSubasta");
    }
    public void clickBtnCerrar(javafx.event.ActionEvent actionEvent) throws Exception {
        if(checkCerrar.isSelected()){
            subastaObservado.setEstado("finalizada");
            subastaObservado.setFechaFinalizacion(LocalDateTime.now());
            daoSubasta.actualizarEstadoFechaFinalSubasta(subastaObservado);
            Alert alert = new Alert(Alert.AlertType.ERROR,"La subasta "+subastaObservado.getId()+" fue cerrada.");
            alert.show();
            INICIOAPP_enviarOrdenesDeSubastasFinalizadas();
        }
    }
}

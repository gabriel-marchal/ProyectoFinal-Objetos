package marchal.gabriel.tl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import marchal.gabriel.bl.Coleccionista.Coleccionista;
import marchal.gabriel.bl.ItemSubasta.ItemSubasta;
import marchal.gabriel.bl.Subasta.Subasta;
import marchal.gabriel.bl.Usuario.Usuario;
import javafx.scene.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class SubastasController extends MainController implements Initializable {

    private static ArrayList<Subasta> subastas;

    @FXML VBox vBoxSubastas;
    @FXML ScrollPane scrollPaneContainer;

    ArrayList<VBox> containers = new ArrayList<>();

    public SubastasController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ActionEvent actionEvent = new ActionEvent();
        scrollPaneContainer.setStyle("-fx-background-color:  #cfafff");

        ArrayList<Subasta> subastas = null;
        try {
            subastas = daoSubasta.obtenerSubastas();

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Subasta tempSubasta :subastas ) {
            try {
                tempSubasta = retornarFullSubasta(tempSubasta);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Duration durationFinal = Duration.between(LocalDateTime.now(),tempSubasta.getFechaFinalizacion());
            Duration duracionInicio = Duration.between(LocalDateTime.now(),tempSubasta.getFechaInicio());


            /**
             * El Coleccionista solo podra ver las subastas POR INICIAR o INICIADA
             * El Admin podra verlos todos
             */
            if(getUsuarioLogeado() instanceof Coleccionista){
                if(tempSubasta.getEstado().equals("por iniciar") || tempSubasta.getEstado().equals("iniciada")){
                    createVBox(actionEvent,tempSubasta);
                }
            }else{
                createVBox(actionEvent,tempSubasta);
            }
        }

        /**
         * Jalo el container con todos los VBox individuales y se agregar al ScrollPane de SceneBuilder
         */
        vBoxSubastas.getChildren().addAll(containers);
        scrollPaneContainer.setContent(vBoxSubastas);
    }

    public void createVBox(javafx.event.ActionEvent actionEvent, Subasta subasta){
        System.out.println("-------------- STARTVBOX --------------");
        VBox vBox = new VBox(); // Creacion del VBox que contendra la info de 1 subasta
        vBox.setStyle("-fx-background-color: #b6caff ;-fx-border-color: #FFFFFF; -fx-border-width: 1px;");
        HBox hBox1 = createHBox1(actionEvent,subasta); // Llama a crear el HBox1
        HBox hBox2 = null;
        try {
            hBox2 = createHBox2(actionEvent,subasta); // Llama a crear el HBox2
        } catch (Exception e) {
            e.printStackTrace();
        }
        HBox hBox3 = createHBox3(actionEvent,subasta); // Llama a crear el HBox3
        HBox hBox4 = null;
        try {
            hBox4 = createHBox4(actionEvent,subasta); // Llama a crear el HBox4
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * Los 4 HBox se devuelven y se coloncan dentro del VBox
         */
        vBox.getChildren().addAll(hBox1, hBox2, hBox3,hBox4);
        VBox.setMargin(vBox,new Insets(5,0,5,0));
        vBox.setMinWidth(scrollPaneContainer.getWidth()-275);
        /**
         * El VBox se agrega al ArrayList containers
         */
        containers.add(vBox);

        System.out.println("-------------- ENDVBOX --------------");
    }
    public HBox createHBox1(javafx.event.ActionEvent actionEvent, Subasta subasta){

        HBox hBox1 = new HBox();
        Label subastaNombreLabel = new Label("ID: "+subasta.getId());
        System.out.println("ID DE LA SUBASTA: "+ subasta.getId());
        subastaNombreLabel.setMinWidth(hBox1.getWidth()/2);
        subastaNombreLabel.setPadding(new Insets(0,hBox1.getWidth(),0,20));
        Label subastaItemContLabel = new Label("Itemes: "+ subasta.getItemesSubastadas().size());
        subastaItemContLabel.setMinWidth(hBox1.getWidth()/2);
        subastaItemContLabel.setPadding(new Insets(0,0,0,hBox1.getWidth()));

        AnchorPane anchorIzq = new AnchorPane();
        anchorIzq.setLayoutY(scrollPaneContainer.getWidth()/2);
        anchorIzq.setMinWidth(scrollPaneContainer.getWidth()/2);
        anchorIzq.setLayoutX(scrollPaneContainer.getHeight()/2);

        anchorIzq.prefWidthProperty().bind(hBox1.widthProperty());
        anchorIzq.getChildren().add(subastaNombreLabel);

        AnchorPane anchorCen = new AnchorPane();
        anchorCen.setLayoutY(scrollPaneContainer.getWidth()/2);
        anchorCen.setMinWidth(scrollPaneContainer.getWidth()/2);
        anchorCen.setLayoutX(scrollPaneContainer.getHeight()/2);
        //anchorDer.setStyle("-fx-border-color: #000000; -fx-border-width: 1px;");
        anchorCen.prefWidthProperty().bind(hBox1.widthProperty());
        anchorCen.getChildren().add(subastaItemContLabel);

        AnchorPane anchorDer = new AnchorPane();
        anchorDer.setMinWidth(hBox1.getWidth()/3);
        anchorDer.prefWidthProperty().bind(hBox1.widthProperty());
        Button btnVer = new Button("Ver");

        btnVer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    clickBtnVer(event,subasta);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        anchorDer.getChildren().add(btnVer);

        hBox1.setMinHeight(20);
        hBox1.setPadding(new Insets(5,0,5,0));

        hBox1.getChildren().addAll(anchorIzq,anchorCen, anchorDer);

        return hBox1;
    }
    public HBox createHBox2(javafx.event.ActionEvent actionEvent, Subasta subasta) throws Exception {
        HBox hBox2 = new HBox();

        ArrayList<String> imagenesItems = new ArrayList<>();
        for (ItemSubasta tempItem: subasta.getItemesSubastadas()) {
            ArrayList<String> imagenes = daoItemSubasta.retornarImagenesItemes(tempItem.getId());
            imagenesItems.addAll(imagenes);
        }

        // Imagen
        ImageView image = null;

        try {
            image = new ImageView(new Image(String.valueOf(getClass().getResource(imagenesItems.get(0)))));
        } catch (Exception e) {
            image = new ImageView(new Image("https://repository-images.githubusercontent.com/15949540/b0a70b80-cb15-11e9-8338-661f601406a1"));
            System.out.println(e.getMessage());
        }


        AnchorPane anchorIzq = new AnchorPane();
        anchorIzq.setLayoutX(scrollPaneContainer.getWidth()/2);
        anchorIzq.setLayoutY(scrollPaneContainer.getHeight()/2);
        image.setFitHeight(50);
        image.setFitWidth(80);
        anchorIzq.getChildren().addAll(image);


        Label subastaEstado = new Label(subasta.getEstado());


        Label tiempoRestanteLabel = new Label();
        if(subasta.getEstado().equals("iniciada")){
            subastaEstado.setStyle("-fx-text-fill: green");
            Duration duration = Duration.between(LocalDateTime.now(),subasta.getFechaFinalizacion());
            int days = (int) duration.toDays();
            int hours = (int) duration.minusDays(days).toHours();
            int minutes = (int) ((duration.getSeconds() % (60 * 60)) / 60);
            int seconds = (int) (duration.getSeconds() % 60);
            String tiempoRestante =days+" dias, "+hours+" horas, "+minutes+" minutos, "+ seconds +" segundos";
            tiempoRestanteLabel.setText("Finaliza en: "+ tiempoRestante);
        }else if(subasta.getEstado().equals("por iniciar")){
            subastaEstado.setStyle("-fx-text-fill: red");
            Duration duration = Duration.between(LocalDateTime.now(),subasta.getFechaInicio());
            int days = (int) duration.toDays();
            int hours = (int) duration.minusDays(days).toHours();
            int minutes = (int) ((duration.getSeconds() % (60 * 60)) / 60);
            int seconds = (int) (duration.getSeconds() % 60);
            String tiempoRestante =days+" dias, "+hours+" horas, "+minutes+" minutos, "+ seconds +" segundos";
            tiempoRestanteLabel.setText("Inicia en: "+ tiempoRestante);
        }

        // Fecha Final
        LocalDateTime fechaFinal = subasta.getFechaFinalizacion();
        Label fechafinalLabel = new Label("Finaliza: "+ retornarString_LOCALDATETIME(fechaFinal));


        Label ofertaMinimaLabel = new Label("Oferta minima: "+ subasta.getOfertaMinima());
        
        VBox anchorDer = new VBox();
        anchorDer.setLayoutX(scrollPaneContainer.getWidth()/2);
        anchorDer.setLayoutY(scrollPaneContainer.getHeight()/2);
        anchorDer.getChildren().addAll(subastaEstado, fechafinalLabel,tiempoRestanteLabel,ofertaMinimaLabel);

        hBox2.setPadding(new Insets(5,0,5,0));
        hBox2.getChildren().addAll(anchorIzq,anchorDer);

        return hBox2;
    }
    public HBox createHBox3(javafx.event.ActionEvent actionEvent, Subasta subasta){
        HBox HBox3 = new HBox();

        AnchorPane anchorIzq = new AnchorPane();
        anchorIzq.prefWidthProperty().bind(HBox3.widthProperty());


        AnchorPane anchorDer = new AnchorPane();
        double precio = subasta.getPrecio();

        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(2);

        Label precioLabel = new Label("Precio: "+ df.format(subasta.getPrecio()));
        precioLabel.setStyle("-fx-font-size: 16; -fx-font-weight:bold; -fx-text-alignment: right;");
        precioLabel.setMinHeight(30);
        precioLabel.setMinWidth(200);
        precioLabel.setPadding(new Insets(0,40,0,0));

        anchorDer.getChildren().add(precioLabel);

        HBox3.setMinHeight(30);
        HBox3.getChildren().addAll(anchorIzq,anchorDer);


        return HBox3;
    }
    public HBox createHBox4(javafx.event.ActionEvent actionEvent, Subasta subasta) throws Exception {

        HBox hBox4 = new HBox();

        AnchorPane anchorIzq = new AnchorPane();
        anchorIzq.setMinWidth(hBox4.getWidth()/3);
        anchorIzq.prefWidthProperty().bind(hBox4.widthProperty());
        Label vendedorLabel = new Label("Vendedor: "+ subasta.getVendedor().getNombre());
        vendedorLabel.setPadding(new Insets(0,0,0,10));

        anchorIzq.getChildren().add(vendedorLabel);

        AnchorPane anchorCen = new AnchorPane();
        anchorCen.setMinWidth(hBox4.getWidth()/3);
        anchorCen.prefWidthProperty().bind(hBox4.widthProperty());
        Usuario vendedor = subasta.getVendedor();
        Label calificacionLabel;
        if(!(vendedor instanceof Coleccionista)){
            calificacionLabel = new Label("Calificacion: 5");
        }else{
            Coleccionista vendedorCol = (Coleccionista) daoUsuario.retornarUsuario(subasta.getVendedor().getCorreoElectronico());
            calificacionLabel = new Label("Calificacion: "+vendedorCol.getCalificacion());
        }
        anchorCen.getChildren().add(calificacionLabel);

        hBox4.getChildren().addAll(anchorIzq,anchorCen);

        return hBox4;
    }

    private void clickBtnVer(javafx.event.ActionEvent actionEvent,Subasta subasta) throws Exception {
        System.out.println("Ver Btn clicked: "+ subasta.getId());
        logica.setSubastaObservado(subasta);

        cambiarJavaFXScene(actionEvent,"SubastaObservado");
    }
}

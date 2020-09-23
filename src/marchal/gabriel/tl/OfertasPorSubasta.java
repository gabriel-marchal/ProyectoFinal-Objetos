package marchal.gabriel.tl;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import marchal.gabriel.bl.Coleccionista.Coleccionista;
import marchal.gabriel.bl.Oferta.Oferta;
import marchal.gabriel.bl.Subasta.Subasta;
import marchal.gabriel.tl.MainController;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class OfertasPorSubasta extends MainController implements Initializable {
    private static Subasta subastaObservado;
    @FXML
    Label subastaObservadoID;

    @FXML
    TableView ofertasTableView;

    @FXML Label precioLabel;
    @FXML Label ofertaMinimaLabel;

    @FXML
    TableColumn ofertanteTableView;
    @FXML TableColumn montoTableView;

    @FXML
    AnchorPane anchor;


    TableView<String> table;



    public OfertasPorSubasta() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        subastaObservado = logica.getSubastaObservado();
        subastaObservadoID.setText("Subasta: "+subastaObservado.getId());

        TreeMap<String, String> mapOfertas = new TreeMap<>();

        try {
            ArrayList<Oferta> ofertas = daoOferta.retornarOfertasXSubasta(subastaObservado);
            for (Oferta oferta : ofertas ) {
                oferta = retornarFullOferta(oferta);
                mapOfertas.put(oferta.getComprador().getNombre(),String.valueOf(oferta.getPrecioOferta()) );
            }
            //ObservableList<TreeMap<String, String>> ofertasObsbList = FXCollections.observableArrayList(mapOfertas);

            /**
             * Creacion de la Table populada por el TreeMap de las Ofertas correspondientes a la Subasta visualizada.
             */
            table = new TableView<>();

            TableColumn<String, String> keyColumn = new TableColumn<>("Ofertante");
            keyColumn.setCellValueFactory(cd -> new SimpleStringProperty(String.valueOf(cd.getValue())));

            TableColumn<String, String> valueColumn = new TableColumn<>("Monto Ofertado");
            valueColumn.setCellValueFactory(cd -> new SimpleStringProperty(String.valueOf(mapOfertas.get(cd.getValue()))));

            table.getItems().addAll(mapOfertas.keySet());
            table.getColumns().addAll(keyColumn,valueColumn);
            table.setMaxWidth(288);
            table.setMaxHeight(294);
            table.setPlaceholder(new Label("No hay ofertas aun"));

            anchor.getChildren().add(table);

            DecimalFormat df1 = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
            df1.setMaximumFractionDigits(2);

            precioLabel.setText(String.valueOf(df1.format(subastaObservado.getPrecio())));

            /**
             * Se obtiene la Oferta mayor y se muestra este valor en el Label ofertaMinimaLabel
             */
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

                ofertaMinimaLabel.setText(df.format(mayor));
            }else{
                ofertaMinimaLabel.setText(String.valueOf(subastaObservado.getOfertaMinima()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

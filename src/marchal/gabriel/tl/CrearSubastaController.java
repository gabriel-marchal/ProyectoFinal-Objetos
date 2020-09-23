package marchal.gabriel.tl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import marchal.gabriel.bl.Administrador.Administrador;
import marchal.gabriel.bl.Coleccionista.Coleccionista;
import marchal.gabriel.bl.ItemSubasta.ItemSubasta;
import marchal.gabriel.bl.Subasta.Subasta;
import marchal.gabriel.bl.Usuario.Usuario;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class CrearSubastaController  extends MainController implements Initializable {
    @FXML Label fechaInicio;
    @FXML Label fechaFinalizacion;
    @FXML Label ofertaMinima;
    @FXML Label id;
    @FXML Label fechaCreacion;
    @FXML Label calificacion;
    @FXML Label vendedor;
    @FXML Label tipoUsuario;
    @FXML Label moderador;
    @FXML DatePicker inputFechaInput;
    @FXML TextField precio;
    @FXML Label precioLabel;

    @FXML
    Label horaInicioLabel;
    @FXML
    Label minutosDeInicioLabel;
    @FXML
    TextField horaInicioTextField;
    @FXML
    TextField minutosInicioTextField;

    @FXML
    Label horaFinalizacionLabel;
    @FXML
    Label minutosDeFinalizacionLabel;
    @FXML
    TextField horaFinalizacionTextField;
    @FXML
    TextField minutosFinalizacionTextField;



    @FXML
    DatePicker nuevoFechaInicion;
    @FXML
    DatePicker nuevoFechaFinalizacion;
    @FXML TextField nuevoOfertaMinima;

    @FXML
    ListView misItemes;
    @FXML
    ListView itemesASubastar;

    private Usuario moderadorSeleccionado;
    private LocalDateTime fechaFinalizacionCalc;
    private LocalDateTime fechaInicioCalc;
    private double nuevoOfertaMinDouble;

    static ArrayList<ItemSubasta> misItemesArray;
    static ArrayList<ItemSubasta> misItemesASubastarArray = new ArrayList<>();

    public CrearSubastaController() throws Exception {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        precioLabel.setVisible(false);


        try {
            id.setText(String.valueOf(getSubastaIdSiguiente()) );
        } catch (Exception e) {
            e.printStackTrace();
        }
        fechaCreacion.setText(LocalDate.now().toString());
        vendedor.setText(logica.getUsuarioLogeado().getNombre());
        fechaInicioCalc = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth(),LocalTime.now().getHour(),LocalTime.now().getMinute(),LocalTime.now().getSecond()).plusHours(24);
        fechaInicio.setText(retornarString_LOCALDATE(fechaInicioCalc.toLocalDate()));
        horaInicioLabel.setText(String.valueOf(fechaInicioCalc.getHour()));
        minutosDeInicioLabel.setText(String.valueOf(fechaInicioCalc.getMinute()));
        fechaFinalizacionCalc = LocalDateTime.of(LocalDate.now().getYear(),LocalDate.now().getMonthValue(),LocalDate.now().getDayOfMonth(),LocalTime.now().getHour(),LocalTime.now().getSecond()).plusMonths(1);
        fechaFinalizacion.setText(retornarString_LOCALDATE(fechaFinalizacionCalc.toLocalDate()));
        horaFinalizacionLabel.setText(String.valueOf(fechaFinalizacionCalc.getHour()));
        minutosDeFinalizacionLabel.setText(String.valueOf(fechaFinalizacionCalc.getMinute()));

        ofertaMinima.setText("1000");

        Usuario usuario = logica.getUsuarioLogeado();

        if(usuario instanceof Coleccionista){
            tipoUsuario.setText("Coleccionista");
        }else if(usuario instanceof Administrador){
            tipoUsuario.setText("Administrador");
        } else{
            tipoUsuario.setText("Vendedor");
        }
        if(usuario instanceof Coleccionista){
            calificacion.setText(String.valueOf(((Coleccionista) usuario).getCalificacion()));
        }else{
            calificacion.setText("-");
        }

        /**
         * Esto agarra un moderador random de los que estan registrados, y coloca el nombre del moderador en el Label correspondiente.
         * Si no hay, pone N/A
         */
        try {
            this.moderadorSeleccionado = getModeradorRandom();
            moderador.setText(moderadorSeleccionado.getNombre());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            moderador.setText("N/A");
        }

        /**
         * Pesca los ItemSubasta del usuario y los coloca en el ListView
         */
        try {
            misItemesArray = daoItemSubasta.getMisItemes(usuario.getCorreoElectronico());
            for (ItemSubasta tempItem :misItemesArray ) {
                misItemes.getItems().add(tempItem.getNombre()+" "+tempItem.getCategoria()+" "+tempItem.getEstado());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickCambiarFechaInicio(javafx.event.ActionEvent actionEvent) throws IOException {
        if(nuevoFechaInicion.getValue() != null){
            fechaInicio.setText(retornarString_LOCALDATE(nuevoFechaInicion.getValue()));
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error\nFecha de inicio no puede estar vacio");
            alert.show();
        }
    }
    public void clickCambiarFechaFinal(javafx.event.ActionEvent actionEvent)throws IOException{
        if(nuevoFechaFinalizacion.getValue() != null){
            fechaFinalizacion.setText(retornarString_LOCALDATE(nuevoFechaFinalizacion.getValue()));
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error\nFecha de finalizacion no puede estar vacio");
            alert.show();
        }
    }
    public void clickCambiarOfertaMinima(javafx.event.ActionEvent actionEvent)throws IOException{
        try {
            nuevoOfertaMinDouble = Double.parseDouble(nuevoOfertaMinima.getText());
            ofertaMinima.setText(nuevoOfertaMinima.getText());
        } catch (Exception e) {
            System.out.println("Error");
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error\nTiene que ser un numero, o no puede estar vacio.");
            alert.show();
            System.out.println(e.getMessage());
        }
    }

    public int getSubastaIdSiguiente() throws Exception {
        return daoSubasta.getNextIdSubasta();
    }



    public void submitSubasta(javafx.event.ActionEvent actionEvent){
        if(validarItemes()){
            if(validarPrecio()){
                if(validarFechas()){
                    ArrayList<ItemSubasta> itemesSubastadas = misItemesASubastarArray;
                    try {
                        Subasta subasta = new Subasta(Integer.parseInt(id.getText()) ,fechaInicioCalc,LocalDateTime.now(),fechaFinalizacionCalc,"por iniciar",Double.parseDouble(ofertaMinima.getText()) ,itemesSubastadas,Integer.parseInt(calificacion.getText()) ,logica.getUsuarioLogeado(),tipoUsuario.getText(),(Coleccionista) moderadorSeleccionado,Double.parseDouble(precioLabel.getText()));
                        daoSubasta.guardarSubasta(subasta);
                        daoSubasta.registrarItemxSubasta(subasta);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION,"Subasta "+id.getText()+"\nCreada exitosamente!");
                        alert.show();
                        enviarEmailSeleccionModerador(moderadorSeleccionado,subasta);
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR,"Error en la creacion de la subasta.");
                        alert.show();
                        System.out.println(e.getMessage());
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Error con las fechas de la subasta\nFavor corregir.");
                    alert.show();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"Error con el Precio de la subasta\nFavor corregir.");
                alert.show();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"La lista de Itemes no puede estar vacia.\nFavor agregar itemes para subastar.");
            alert.show();
        }
    }

    public boolean validarPrecio() {
        try {
            if(precioLabel.getText() != null && Double.parseDouble(precioLabel.getText()) > Double.parseDouble(ofertaMinima.getText())){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean validarItemes() {
        if(misItemesASubastarArray.size()>0){
            return true;
        }else{
            return false;
        }
    }
    public boolean validarFechas(){
        try {
            /*
            Tomar valores de los Label FECHA INICIO y convertir en LDT
             */
            LocalDate fechaInicioLD =LocalDate.parse(fechaInicio.getText());
            LocalDateTime fechaInicioLDT = LocalDateTime.of(fechaInicioLD.getYear(),fechaInicioLD.getMonthValue(),fechaInicioLD.getDayOfMonth(),Integer.parseInt(horaInicioLabel.getText()),Integer.parseInt(minutosDeInicioLabel.getText()),LocalDateTime.now().getSecond());
            fechaInicioCalc = fechaInicioLDT;
            //Duration durationInicio = Duration.between(fechaInicioCalc,LocalDateTime.now().plusHours(24));

            /*
            Tomar valores de los Label FECHA FINAL y convertir en LDT
             */
            LocalDate fechaFinalLD = LocalDate.parse(fechaFinalizacion.getText());
            LocalDateTime fechaFinalLDT = LocalDateTime.of(fechaFinalLD.getYear(),fechaFinalLD.getMonthValue(),fechaFinalLD.getDayOfMonth(),Integer.parseInt(horaFinalizacionLabel.getText()),Integer.parseInt(minutosDeFinalizacionLabel.getText()),LocalDateTime.now().getSecond());
            fechaFinalizacionCalc = fechaFinalLDT;
            //Duration durationFinal = Duration.between(LocalDateTime.now().plusMonths(1),fechaFinalizacionCalc);

            if(fechaInicioCalc.compareTo(LocalDateTime.now().plusHours(24))>0  && fechaFinalizacionCalc.compareTo(LocalDateTime.now().plusMonths(1))>0){
                return true;
            }else {
                return false;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void actualizarListaItemes(javafx.event.ActionEvent actionEvent) throws Exception {
        ObservableList<ItemSubasta> misItemesObs = FXCollections.observableList(misItemesArray);
        ObservableList<ItemSubasta> misItemesASubastarObs = FXCollections.observableList(misItemesASubastarArray);

        misItemes.setItems(misItemesObs);
        itemesASubastar.setItems(misItemesASubastarObs);

    }

    public void clickSeleccionarItem(javafx.event.ActionEvent actionEvent) throws Exception {
        /*
         int categoria = listaCategorias.getSelectionModel().getSelectedIndex(); // Retorna el INDEX del array en ListView
         */
        int indexItem = misItemes.getSelectionModel().getSelectedIndex();
        misItemesASubastarArray.add(misItemesArray.get(indexItem));
        ObservableList<ItemSubasta> observableList = FXCollections.observableList(misItemesASubastarArray);
        itemesASubastar.setItems(observableList);
        //itemesASubastar.setItems() .add(misItemesArray.get(indexItem));
        //actualizarListaItemes();
    }
    public void clickRemoverItem(javafx.event.ActionEvent actionEvent) throws Exception {
        int indexItemASubastar = itemesASubastar.getSelectionModel().getSelectedIndex();
        misItemesASubastarArray.remove(misItemesASubastarArray.get(indexItemASubastar));
        ObservableList<ItemSubasta> observableList = FXCollections.observableList(misItemesArray);
        misItemes.setItems(observableList);
        actualizarListaItemes(actionEvent);
    }

    public void clickCambiarHoraInicio(javafx.event.ActionEvent actionEvent){
        try {
            int horaInicioInt = Integer.parseInt(horaInicioTextField.getText());
            if(horaInicioTextField.getText() != null && horaInicioInt >0 && horaInicioInt <24){
                horaInicioLabel.setText(String.valueOf(horaInicioInt));
            }else{
                alertInteger("hora");
            }
        } catch (Exception e) {
            alertInteger("hora");
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }
    public void clickCambiarHoraFinalizacion(javafx.event.ActionEvent actionEvent){
        try {
            int horaFinalInt = Integer.parseInt(horaFinalizacionTextField.getText());
            if(horaFinalizacionTextField.getText() != null && horaFinalInt >0 && horaFinalInt <24){
                horaFinalizacionLabel.setText(String.valueOf(horaFinalInt));
            }else{
                alertInteger("hora");
            }
        } catch (Exception e) {
            alertInteger("hora");
            System.out.println(e.getMessage());
        }

    }
    public void clickMinutosInicio(javafx.event.ActionEvent actionEvent){
        try {
            int minutoInicioInt = Integer.parseInt(minutosInicioTextField.getText());
            if(minutosInicioTextField.getText()!=null && minutoInicioInt>0 && minutoInicioInt<60){
                minutosDeInicioLabel.setText(String.valueOf(minutoInicioInt));
                clickCambiarFechaInicio(actionEvent);
            }else{
                alertInteger(".");
            }
        } catch (Exception e) {
            alertInteger("minuto");
            System.out.println(e.getMessage());
        }
    }
    public void clickMinutosFinalizacion(javafx.event.ActionEvent actionEvent){
        try {
            int minutoFinalInt = Integer.parseInt(minutosFinalizacionTextField.getText());
            if(minutosFinalizacionTextField.getText() !=null && minutoFinalInt>0 && minutoFinalInt<60){
                minutosDeFinalizacionLabel.setText(String.valueOf(minutoFinalInt));
            }else{
                alertInteger(".");
            }
        } catch (Exception e) {
            alertInteger("minuto");
            System.out.println(e.getMessage());
        }
    }
    public void alertInteger(String tipo){
        String mensaje = "";
        if(tipo.equals("hora")){
            mensaje = "entre 0 a 23.";
        }else if(tipo.equals("minuto")){
            mensaje = "entre 0 a 59.";
        }else{
            mensaje =".";
        }
        Alert alert = new Alert(Alert.AlertType.ERROR,"Tiene que ingresar un numero "+mensaje);
        alert.show();
    }
    public void clickCambiarPrecio(){
        if(precio.getText() != null){
            precioLabel.setVisible(true);
            precioLabel.setText(precio.getText());
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"El precio no puede estar vacio");
            alert.show();
        }
    }
}

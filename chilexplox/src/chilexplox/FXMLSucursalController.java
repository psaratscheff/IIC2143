/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Camion;
import chilexplox.classes.Empresa;
import chilexplox.classes.Encomienda;
import chilexplox.classes.Mensaje;
import chilexplox.classes.Sucursal;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ciruman.EllipsisListCell;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.util.Callback;

//Listener
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxResult;
import se.mbaeumer.fxmessagebox.MessageBoxType;

/**
 * FXML Controller class
 *
 * @author Thomas Pryce Jones
 */
public class FXMLSucursalController implements Initializable {
    
    @FXML
    private Label LabelNombreTrabajador;
    @FXML
    private Label Urgencia;
    @FXML
    private Label LabelRecibidosCamiones;
    @FXML
    private ComboBox<String> ChoiceBoxCamiones;
    @FXML
    private ComboBox<String> ChoiceBoxSucursales;
    @FXML
    private ChoiceBox<String> ChoiceBoxDestinoCamiones;
    @FXML
    private Button IngresarPedido;
    @FXML
    private Button VerEncomienda;
    @FXML
    private Button PasarACola;
    @FXML
    private Button VerPedidosRecibidos;
    @FXML
    private Button Mensajes;
    @FXML
    private Button Salir;
    @FXML
    private Button ModificarPedido;
    @FXML
    private Button NotificarErrorPedido;
    @FXML
    private Label LabelCamionesDisponibles;
    @FXML
    private Button CargarCamion;
    @FXML
    private Button DescargarPedido;
    @FXML
    private Button EnviarCamion;
    @FXML
    private Button CargarSucursal;
    @FXML
    private Button EntregarEncomienda;
    @FXML
    private Button QuitarEncomiendaCamion;
    @FXML
    private ListView ListMessagesPreview; //TIENE CELL-FACTORY!! No convertir a ListView<String>!!
    @FXML
    private ListView<String> EncomiendasEnSucursal;
    @FXML
    private ListView<String> EncomiendasRecibidas;
    @FXML
    private Label ErrorLabelSucursal;
    @FXML
    private Label LabelSucursal;
    @FXML
    private ProgressBar ProgressBarCapacity;

    Empresa emp;
    Camion camionActual;
    double espacioCamion = -1;
    Camion camion = null;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Limpiar campos de muestra
        LabelSucursal.setText("");
        ErrorLabelSucursal.setText("");
        Urgencia.setText("");
        // Get singleton empresa
        emp = Empresa.getInstance();
        // Set welcome message
        String bienvenida = "Hola, " + emp.getempleadoactual().getNombre();
        LabelNombreTrabajador.setText(bienvenida);
        // Update sucursales
        LoadSucursales();
        // Ocultar botones deshabilitados
        QuitarEncomiendaCamion.setVisible(false);
        VerPedidosRecibidos.setVisible(false);
    }
    
    // Métodos privados para cargar información
    // ---------Sucursales: -------------
    private void AddSucursal(final Sucursal s)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ChoiceBoxSucursales.getItems().add(s.getDireccion());
                ChoiceBoxDestinoCamiones.getItems().add(s.getDireccion());
                emp.getsucursales().add(s);
            }
        });
    }
    private void RemoveSucursal(final Sucursal s)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ChoiceBoxSucursales.getItems().remove(s.getDireccion());
                ChoiceBoxDestinoCamiones.getItems().remove(s.getDireccion());
                emp.getsucursales().remove(s);
            }
        });
    }
    private void LoadSucursales()
    {
        Firebase sucursalesRef = emp.fbRef().child("sucursales");
        sucursalesRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot ds, String previousChildKey) {
                Sucursal s = ds.getValue(Sucursal.class);
                System.out.println("Sucursal Agregada:" + s.toString());
                AddSucursal(s);
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String previousChildKey) {
                emp = Empresa.getInstance();
                Sucursal s = ds.getValue(Sucursal.class);
                System.out.println("Sucursal Modificada:" + s.toString());
                // Elimino la versión antigua
                Sucursal temp = null;
                for(Sucursal s2: emp.getsucursales())
                {
                    if (s2.getDireccion().equals(s.getDireccion()))
                    {
                        temp=s2;
                    }
                }
                emp.getsucursales().remove(temp);
                // Agrego la versión nueva
                emp.getsucursales().add(s);
                // La dejo seleccionada si estaba ya seleccionada y fue modificada:
                if (emp.getsucursalactual() != null && emp.getsucursalactual().getDireccion().equals(s.getDireccion()))
                {
                    emp.setsucursalactual(s);
                    RefreshConSucursal(); // Actualizar data mostrada (Visual)
                }
            }
            @Override
            public void onChildRemoved(DataSnapshot ds) {
                Sucursal s = ds.getValue(Sucursal.class);
                System.out.println("Sucursal REMOVIDA:" + s.toString());
                RemoveSucursal(s);
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                // Who cares... (No se requiere hacer nada)
            }
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println("ERROR FB-101:" + fe.getMessage());
            }
        });/**/
    }
    public void UpdateConSucursal()
    {
        Platform.runLater(new Runnable() { // Evitar problemas con el "Not on FX Thread"
            @Override
            public void run() {
                Sucursal s = emp.getsucursalactual();
                System.out.println("Cargando..." + s + " - " + s.getEncomiendasAlmacenadas() + " - " + s.getEncomiendasRecibidas() + " - " + s.getCamionesEstacionados());
                 // CARGAR ENCOMIENDAS
                EncomiendasEnSucursal.getItems().clear();
                Boolean boolurgencia = false;
                for(Encomienda en: emp.getsucursalactual().getEncomiendasAlmacenadas())
                {
                    if (en.getPrioridad().equals("Urgente"))
                    {
                        boolurgencia = true;
                    }
                    EncomiendasEnSucursal.getItems().add("["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + " -- " + "ID: #" + en.getId() + "# -- Destino: " + en.getSucursalDestino()+" Tipo: "+en.getTipo());
                }
                if (boolurgencia == false)
                {
                    Urgencia.setText(null);
                }
                if (boolurgencia) 
                {
                    Urgencia.setText("¡Hay una encomienda urgente!");
                    boolurgencia = false;
                }

                // CARGAR ENCOMIENDAS RECIBIDAS
                EncomiendasRecibidas.getItems().clear();
                for(Encomienda en: emp.getsucursalactual().getEncomiendasRecibidas())
                {
                    EncomiendasRecibidas.getItems().add("["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + " -- " + "ID: #" + en.getId() + "# -- Origen: " + en.getSucursalOrigen()+" -- Tipo: "+en.getTipo());
                }
                // CARGAR PREVIEW MENSAJES!! (Agregar un timer de sincronización?)
                ListMessagesPreview.getItems().clear();
                for(Mensaje m: emp.getsucursalactual().getMensajesRecibidos())
                {
                    if (m.getUrgente()== true) 
                    {
                        String mensajePreview ="URGENTE " + m.getContenido();
                        String[] mpArray = mensajePreview.split("\\r?\\n");
                        if (mpArray.length > 1) { mensajePreview = mpArray[0]+"...";} // Solo la primera linea
                        ListMessagesPreview.getItems().add(0, mensajePreview); // Añado al principio
                    }
                    else
                    {
                        String mensajePreview = m.getContenido();
                        String[] mpArray = mensajePreview.split("\\r?\\n");
                        if (mpArray.length > 1) { mensajePreview = mpArray[0]+"...";} // Solo la primera linea
                        ListMessagesPreview.getItems().add(0, mensajePreview); // Añado al principio
                    }
                }
                ListMessagesPreview.setCellFactory(new Callback<ListView<String>, EllipsisListCell>() {
                    @Override
                    public EllipsisListCell call(ListView<String> p) {
                        EllipsisListCell cell = new EllipsisListCell();
                        return cell;
                    }
                });
                // CARGAR CAMIONES DISPONIBLES
                espacioCamion = -1;
                ChoiceBoxCamiones.getItems().clear();
                for (Camion c: emp.getsucursalactual().getCamionesEstacionados()) 
                {
                    ChoiceBoxCamiones.getItems().add(c.NombreCompleto());
                }/**/
                //Stufffff to do
                ProgressBarCapacity.setProgress(espacioCamion);
                if (espacioCamion<0.7)
                {
                    ProgressBarCapacity.setStyle("-fx-accent: green;");
                }
                else if (espacioCamion<0.85)
                {
                    ProgressBarCapacity.setStyle("-fx-accent: yellow;");
                }
                else
                {
                    ProgressBarCapacity.setStyle("-fx-accent: red;");
                }
                Platform.runLater(new Runnable() { // Asegurarme que se limpie la selección corriendolo poco después de cargar la lista
                    @Override
                    public void run() {
                        ChoiceBoxCamiones.getSelectionModel().clearSelection();
                    }
                });
                // Volver a ver recibidos si se estaba viendo un camion
                try {
                    VerPedidosRecibidosAction();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLSucursalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void RefreshConSucursal()
    {
        Platform.runLater(new Runnable() { // Evitar problemas con el "Not on FX Thread"
            @Override
            public void run() {
                Sucursal s = emp.getsucursalactual();
                System.out.println("Refrescando..." + s + " - " + s.getEncomiendasAlmacenadas() + " - " + s.getEncomiendasRecibidas() + " - " + s.getCamionesEstacionados());
                // CARGAR ENCOMIENDAS
                String seleccionado = EncomiendasEnSucursal.getSelectionModel().getSelectedItem();
                EncomiendasEnSucursal.getItems().clear();
                Boolean boolurgencia = false;
                for(Encomienda en: emp.getsucursalactual().getEncomiendasAlmacenadas())
                {
                    if (en.getPrioridad().equals("Urgente"))
                    {
                        boolurgencia = true;
                    }
                    EncomiendasEnSucursal.getItems().add("["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + " -- " + "ID: #" + en.getId() + "# -- Destino: " + en.getSucursalDestino()+" Tipo: "+en.getTipo());
                }
                if (boolurgencia == false)
                {
                    Urgencia.setText(null);
                }
                if (boolurgencia) 
                {
                    Urgencia.setText("Hay una encomienda urgente!");
                    boolurgencia = false;
                }
                // Dejo seleccionado el que estaba seleccionado antes de refrescar la ventana
                try{
                    EncomiendasEnSucursal.getSelectionModel().select(seleccionado);
                }catch(Exception e){
                    System.out.println("Ya no está disponible la encomienda: "+e.getMessage());
                }/**/

                // CARGAR ENCOMIENDAS RECIBIDAS
                EncomiendasRecibidas.getItems().clear();
                if (VerPedidosRecibidos.visibleProperty().getValue())
                {
                    String seleccionadoR = EncomiendasRecibidas.getSelectionModel().getSelectedItem();
                    for(Encomienda en: camion.getEncomiendas())
                    {
                        EncomiendasRecibidas.getItems().add("["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + " -- " + "ID: #" + en.getId() + "# -- Origen: " + en.getSucursalOrigen()+" -- Tipo: "+en.getTipo());
                    }
                    // Dejo seleccionado el que estaba seleccionado antes de refrescar la ventana
                    try{
                        EncomiendasRecibidas.getSelectionModel().select(seleccionadoR);
                    }catch(Exception e){
                        System.out.println("Ya no está disponible la encomiendaR: "+e.getMessage());
                    }/**/
                }
                else
                {
                    String seleccionadoR = EncomiendasRecibidas.getSelectionModel().getSelectedItem();
                    for(Encomienda en: emp.getsucursalactual().getEncomiendasRecibidas())
                    {
                        EncomiendasRecibidas.getItems().add("["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + " -- " + "ID: #" + en.getId() + "# -- Origen: " + en.getSucursalOrigen()+" -- Tipo: "+en.getTipo());
                    }
                    // Dejo seleccionado el que estaba seleccionado antes de refrescar la ventana
                    try{
                        EncomiendasRecibidas.getSelectionModel().select(seleccionadoR);
                    }catch(Exception e){
                        System.out.println("Ya no está disponible la encomiendaR: "+e.getMessage());
                    }/**/
                }
                // CARGAR PREVIEW MENSAJES!! (Agregar un timer de sincronización?)
                ListMessagesPreview.getItems().clear();
                for(Mensaje m: emp.getsucursalactual().getMensajesRecibidos())
                {
                    if (m.getUrgente()== true)
                    {
                        String mensajePreview ="URGENTE " + m.getContenido();
                        String[] mpArray = mensajePreview.split("\\r?\\n");
                        if (mpArray.length > 1) { mensajePreview = mpArray[0]+"...";} // Solo la primera linea
                        ListMessagesPreview.getItems().add(0, mensajePreview); // Añado al principio
                    }
                    else
                    {
                        String mensajePreview = m.getContenido();
                        String[] mpArray = mensajePreview.split("\\r?\\n");
                        if (mpArray.length > 1) { mensajePreview = mpArray[0]+"...";} // Solo la primera linea
                        ListMessagesPreview.getItems().add(0, mensajePreview); // Añado al principio
                    }
                }
                ListMessagesPreview.setCellFactory(new Callback<ListView<String>, EllipsisListCell>() {
                    @Override
                    public EllipsisListCell call(ListView<String> p) {
                        EllipsisListCell cell = new EllipsisListCell();
                        return cell;
                    }
                });
                // CARGAR CAMIONES DISPONIBLES
                String selectedCamion = ChoiceBoxCamiones.getSelectionModel().getSelectedItem();
                ChoiceBoxCamiones.getItems().clear();
                for (Camion c: emp.getsucursalactual().getCamionesEstacionados()) 
                {
                    ChoiceBoxCamiones.getItems().add(c.NombreCompleto());
                }/**/
                // Dejo seleccionado el que estaba seleccionado antes de refrescar la ventana
                try{
                    ChoiceBoxCamiones.getSelectionModel().select(selectedCamion);
                    RefreshProgressBarAction();
                }catch(Exception e){
                    System.out.println("Ya no está disponible el camion: "+e.getMessage());
                }/**/
            }
        });
    }
    
    @FXML
    private void CargarVentanaInformes()
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLVerInforme.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            FXMLVerInformeController controller = fxmlLoader.<FXMLVerInformeController>getController();
            controller.setEmpresa(emp);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));  
            stage.show();
        }
        catch (Exception e){
            System.out.println("ERROR 101: " + e.toString());
        }
    }
    
    @FXML
    private void EntregarEncomiendaAction(){
        String enco = EncomiendasRecibidas.getSelectionModel().getSelectedItem();
        String encomiendaID;
        try{
            encomiendaID = EncomiendasRecibidas.getSelectionModel().getSelectedItem().split("#")[1]; // Obtengo el id
        }catch(Exception e){
            System.out.println("1Debes seleccionar un pedido a entregar");
            return; // FIN
        }
        Encomienda encomienda = null;
        try
        {
            encomienda = emp.getsucursalactual().getEncomienda(encomiendaID);
        } 
        catch (Exception e)
        {
            return; //Nada
        }
        emp.getsucursalactual().getEncomiendasRecibidas().remove(encomienda);
        emp.fbRef().child("sucursales").child(emp.getsucursalactual().getDireccion()).setValue(emp.getsucursalactual());
        EncomiendasRecibidas.getItems().remove(enco);
        encomienda.setestado("Entrega satisfactoria");
        emp.fbRef().child("encomiendas").child(encomienda.getId()).setValue(encomienda);
    }
    
    @FXML
    private void RefreshProgressBarAction()
    {
        Camion camionSeleccionado = null;
        String cs = ChoiceBoxCamiones.getValue();
        for (Camion c: emp.getsucursalactual().getCamionesEstacionados())
        {
            if (c.NombreCompleto().equals(cs))
            {
                camionSeleccionado = c;
            }
        }
        // Reviso que haya seleccion al momento de llamar al metodo
        if (camionSeleccionado == null) { 
            if (espacioCamion!=-1){ System.out.println("5No hay camion seleccionado"); } 
            return; 
        }
        camionActual = camionSeleccionado;
        espacioCamion = camionSeleccionado.PorcentajeDisponible();

        //Stufffff to do
        ProgressBarCapacity.setProgress(espacioCamion);
         if (espacioCamion<0.7)
         {
              ProgressBarCapacity.setStyle("-fx-accent: green;");
         }
         else if (espacioCamion<0.85)
         {
              ProgressBarCapacity.setStyle("-fx-accent: yellow;");
         }
         else
         {
              ProgressBarCapacity.setStyle("-fx-accent: red;");
         }
    }
    
    @FXML
    private void btnCargarSucursal() throws IOException{
        String temp = ChoiceBoxSucursales.getValue();
        Sucursal s = null;
        for (Sucursal s2: emp.getsucursales())
        {
            if (temp.equals(s2.getDireccion()))
            {
                s=s2;
            }
        }
        if (s == null) { System.out.println("1No hay sucursal seleccionada"); return; }
        emp.setsucursalactual(s);
        UpdateConSucursal();
        ErrorLabelSucursal.setText("");
        LabelSucursal.setText(emp.getsucursalactual().getDireccion());
    }
    
    @FXML
    private void CargarCamionAction() throws IOException{
        if (EncomiendasEnSucursal.getSelectionModel().getSelectedItem() == null) { return; }
        String encomiendaID;
        try{
            encomiendaID = EncomiendasEnSucursal.getSelectionModel().getSelectedItem().split("#")[1]; // Obtengo el id
        }catch(Exception e){
            System.out.println("2Debes seleccionar un pedido a entregar");
            return; // FIN
        }
        Encomienda encomienda = null;
        try
        {
            encomienda = emp.getsucursalactual().getEncomienda(encomiendaID);
            encomienda.setEmpleado(emp.getempleadoactual().getUsername());
        } 
        catch (Exception e)
        {
            System.out.println("No hay encomienda seleccionada");
            return; //Nada
        }
        
        try
        {
            //OJO con la importancia de que el id sea un numero valido
            // Obtener Camion a Enviar
            Camion camion = null;
            String cs = ChoiceBoxCamiones.getValue();
            for (Camion c: emp.getsucursalactual().getCamionesEstacionados())
            {
                if (c.NombreCompleto().equals(cs))
                {
                    camion = c;
                }
            }
            // Reviso que haya seleccion al momento de llamar al metodo
            if (camion == null) { System.out.println("1No hay camion seleccionado"); return; }

            if(camion.getTipo().equals(encomienda.getTipo()))
            {
                espacioCamion = camion.PorcentajeDisponible();
                if (espacioCamion>=1.0){ return; }
                //CARGAR CAMION!!
                emp.getsucursalactual().getEncomiendasAlmacenadas().remove(encomienda);
                encomienda.setestado("En Camión");
                camion.addencomienda(encomienda); // Camion obtenido de los estacionados en sucursal actual!
                emp.fbRef().child("sucursales").child(emp.getsucursalactual().getDireccion()).setValue(emp.getsucursalactual());

                //RECARGAR ENCOMIENDAS
                // Firebase lo hace automágicamente :)

                //ACTUALIZAR CAPACIDAD
                espacioCamion = camion.PorcentajeDisponible();
                // Reciclar este código después!!! ¡¡¡¡DRY!!!!
                ProgressBarCapacity.setProgress(espacioCamion);
                if (espacioCamion<0.7)
                {
                    ProgressBarCapacity.setStyle("-fx-accent: green;");
                }
                else if (espacioCamion<0.85)
                {
                    ProgressBarCapacity.setStyle("-fx-accent: yellow;");
                }
                else
                {
                    ProgressBarCapacity.setStyle("-fx-accent: red;");
                }
                //Actualizo servidor
                emp.fbRef().child("sucursales").child(emp.getsucursalactual().getDireccion()).setValue(emp.getsucursalactual());
            }
            else
            {
                MessageBox mb = new MessageBox("Solo se puede cargar una encomienda \n tipo "+ encomienda.getTipo()+
                        " en un camion "+encomienda.getTipo()+"!", MessageBoxType.OK_ONLY);
                mb.showAndWait();
            }//podria hacer un messagebox que no coincide tipo
        }
        catch (Exception e)
        {
            System.out.println("ERROR 101: " + e.toString());
        }
    }
    
    @FXML
    private void EnviarCamionAction() throws IOException{
        // Obtener Camion a Enviar
        Camion camion = null;
        String cs = ChoiceBoxCamiones.getValue();
        for (Camion c: emp.getsucursalactual().getCamionesEstacionados())
        {
            if (c.NombreCompleto().equals(cs))
            {
                camion = c;
            }
        }
        // Reviso que haya seleccion al momento de llamar al metodo
        if (camion == null) { System.out.println("2No hay camion seleccionado"); return; }
        // Obtener Sucursal destino
        Sucursal destinoSucursal = null;
        String ds = ChoiceBoxDestinoCamiones.getValue();
        for (Sucursal s: emp.getsucursales())
        {
            if (s.getDireccion().equals(ds))
            {
                destinoSucursal = s;
            }
        }
        // Reviso que haya seleccion al momento de llamar al metodo
        if (destinoSucursal == null) { System.out.println("1No hay destino seleccionado"); return; }
        // Enviar Camion
        Camion temp = null;
        for(Camion c2: emp.getsucursalactual().getCamionesEstacionados())
        {
            if (c2.NombreCompleto().equals(camion.NombreCompleto()))
            {
                temp=c2;
            }
        }
        // Remove camion de sucursal
        emp.getsucursalactual().getCamionesEstacionados().remove(temp);
        emp.fbRef().child("sucursales").child(emp.getsucursalactual().getDireccion()).setValue(emp.getsucursalactual());
        // Descargar encomiendas en destino (Inmediato por ahora)
        destinoSucursal.getCamionesEstacionados().add(camion);
        for (Encomienda e: camion.getEncomiendas())
        {
            e.setestado("En Destino");
            destinoSucursal.getEncomiendasRecibidas().add(e);
        }camion.borrarencomiendas();/**/
        emp.fbRef().child("sucursales").child(destinoSucursal.getDireccion()).setValue(destinoSucursal);

        ChoiceBoxDestinoCamiones.getSelectionModel().clearSelection();
        // CARGAR CAMIONES DISPONIBLES
        /*ChoiceBoxCamiones.getItems().clear();
        for (Camion c: emp.getsucursalactual().getCamionesEstacionados()) 
        {
            ChoiceBoxCamiones.getItems().add(c);
        }/**/
        espacioCamion = -1;
        ProgressBarCapacity.setProgress(-1); // -1 para indeterminado
    }
    @FXML
    private void IngresarPedidoAction() throws IOException{
        if (ChoiceBoxSucursales.getValue() == null)
        {
            ErrorLabelSucursal.setText("¡Debes seleccionar una sucursal!");
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLIngresoPedido.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            FXMLIngresoPedidoController controller = fxmlLoader.<FXMLIngresoPedidoController>getController();
            controller.setSucursalController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e){
            System.out.println("ERROR 304: " + e.toString());
        }
    }
    
    @FXML
    private void MensajesAction() throws IOException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLMensajesRecibidos.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));  
            stage.show();
        } catch (Exception e){
            ErrorLabelSucursal.setText("¡Debes seleccionar una sucursal!");
        }
    }
    
    @FXML
    private void VerCamionAction() throws IOException{
        // Obtener Camion a Revisar
        Camion camionS = null;
        String cs = ChoiceBoxCamiones.getValue();
        for (Camion c: emp.getsucursalactual().getCamionesEstacionados())
        {
            if (c.NombreCompleto().equals(cs))
            {
                camionS = c;
            }
        }
        // Reviso que haya seleccion al momento de llamar al metodo
        if (camionS == null) { System.out.println("3No hay camion seleccionado"); return; }
        LabelRecibidosCamiones.setText("Camion: "+camionS.NombreCompleto());
        NotificarErrorPedido.setVisible(false);
        EntregarEncomienda.setVisible(false);
        PasarACola.setVisible(false);
        QuitarEncomiendaCamion.setVisible(true);
        VerPedidosRecibidos.setVisible(true);
        EncomiendasRecibidas.getItems().clear();
        for(Encomienda en: camionS.getEncomiendas())
        {
            EncomiendasRecibidas.getItems().add("["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + "// " + "ID: #" + en.getId() + "# Destino: " + emp.getSucursalConDireccion(en.getSucursalDestino()).getDireccion());
        }
        this.camion = camionS;
    }
    
    @FXML
    private void VerPedidosRecibidosAction() throws IOException{
        LabelRecibidosCamiones.setText("Pedidos Recibidos");
        NotificarErrorPedido.setVisible(true);
        EntregarEncomienda.setVisible(true);
        PasarACola.setVisible(true);
        QuitarEncomiendaCamion.setVisible(false);
        VerPedidosRecibidos.setVisible(false);
        EncomiendasRecibidas.getItems().clear();
        for(Encomienda en: emp.getsucursalactual().getEncomiendasRecibidas())
        {
            EncomiendasRecibidas.getItems().add("["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + "// " + "ID: #" + en.getId() + "# Destino: " + emp.getSucursalConDireccion(en.getSucursalDestino()).getDireccion());
        }
    }
    
    @FXML
    private void NotificarErrorAction() throws IOException{
        Firebase postRef;
        Firebase newPostRef;
        String encomiendaID;
        try{
            encomiendaID = EncomiendasRecibidas.getSelectionModel().getSelectedItem().split("#")[1]; // Obtengo el id
        }catch(Exception e){
            System.out.println("3Debes seleccionar un pedido a entregar");
            return; // FIN
        }
        Encomienda encomienda = null;
        try
        {
            encomienda = emp.getsucursalactual().getEncomienda(encomiendaID);
        } 
        catch (Exception e)
        {
            return; //Nada
        }
        String contenido = "Se ha detectado error en la encomienda ID #"+encomienda.getId()+"#";
        Mensaje mnsj = new Mensaje(contenido,true);
        Sucursal s = emp.getSucursalConDireccion(encomienda.getSucursalOrigen());
        s.getMensajesRecibidos().add(mnsj);
        postRef = emp.fbRef().child("sucursales");
        newPostRef = postRef.child(encomienda.getSucursalOrigen()); newPostRef.setValue(s);
        String error = "Error en la encomienda ID #"+encomienda.getId()+"#" + "/n por parte del funcionario: " + encomienda.getEmpleado();
        Mensaje mnsjerror = new Mensaje(error, false);
        postRef = emp.fbRef().child("errores");
        newPostRef = postRef.push(); newPostRef.setValue(mnsjerror);
        
    }
    
    @FXML
    private void QuitarEncomiendaCamionAction() throws IOException{
        String encomiendaID;
        try{
            encomiendaID = EncomiendasRecibidas.getSelectionModel().getSelectedItem().split("#")[1]; // Obtengo el id
        }catch(Exception e){
            System.out.println("4Debes seleccionar un pedido a entregar");
            return; // FIN
        }
        Encomienda encomienda = null;
        Camion camion = null;
        String cs = ChoiceBoxCamiones.getValue();
        for (Camion c: emp.getsucursalactual().getCamionesEstacionados())
        {
            if (c.NombreCompleto().equals(cs))
            {
                camion = c;
            }
        }
        // Reviso que haya seleccion al momento de llamar al metodo
        if (camion == null) { System.out.println("4No hay camion seleccionado"); return; }
        for(Encomienda en: camion.getEncomiendas())
        {
            if (en.getId().equals(encomiendaID)) 
            {
                encomienda = en;
            }
        }
        // Reviso que haya seleccion al momento de llamar al metodo
        if (encomienda == null) { System.out.println("2No hay encomienda seleccionada"); return; }
        
        System.out.println("Quitando encomienda "+encomienda.getId()+" al camion "+camion.NombreCompleto());
        
        espacioCamion = camion.PorcentajeDisponible();
        if (espacioCamion>=1.0){ return; }

        //CARGAR CAMION!!
        encomienda.setestado("En cola");
        camion.borrarencomienda(encomienda);
        emp.getsucursalactual().getEncomiendasAlmacenadas().add(encomienda);
        emp.fbRef().child("sucursales").child(emp.getsucursalactual().getDireccion()).setValue(emp.getsucursalactual());
        //Recargar Encomiendas
        EncomiendasEnSucursal.getItems().clear();
        Boolean boolurgencia = false;
        for(Encomienda en: emp.getsucursalactual().getEncomiendasAlmacenadas())
        {
            if ("Urgente".equals(en.getPrioridad())) 
            {
                boolurgencia = true;
            }
            EncomiendasEnSucursal.getItems().add("["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + "// " + "ID: #" + en.getId() + "# Destino: " + emp.getSucursalConDireccion(en.getSucursalDestino()).getDireccion()+" Tipo: "+en.getTipo());
        }
        if (boolurgencia == false) 
        {
            Urgencia.setText(null);
        }
        if (boolurgencia) 
        {
            Urgencia.setText("Hay una encomienda urgente!");
            boolurgencia = false;
        }
        EncomiendasRecibidas.getItems().clear();
        RefreshConSucursal();
        
        //Actualizar capacidad
        espacioCamion = camion.PorcentajeDisponible();
        // RECICLAR ESTE CODIGO DESPUES!!! ¡¡¡¡DRY!!!!
        ProgressBarCapacity.setProgress(espacioCamion);
        if (espacioCamion<0.7)
        {
             ProgressBarCapacity.setStyle("-fx-accent: green;");
        }
        else if (espacioCamion<0.85)
        {
             ProgressBarCapacity.setStyle("-fx-accent: yellow;");
        }
        else
        {
             ProgressBarCapacity.setStyle("-fx-accent: red;");
        }
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    VerCamionAction();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLSucursalController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    @FXML
    private void PasarAColaAction() throws IOException{
        String encomiendaID;
        try{
            encomiendaID = EncomiendasRecibidas.getSelectionModel().getSelectedItem().split("#")[1]; // Obtengo el id
        }catch(Exception e){
            System.out.println("5Debes seleccionar un pedido a entregar");
            return; // FIN
        }
        Encomienda encomienda = null;
        try
        {
            encomienda = emp.getsucursalactual().getEncomienda(encomiendaID);
        } 
        catch (Exception e)
        {
            return; //Nada
        }
        EncomiendasEnSucursal.getItems().add("["+encomienda.getPrioridad()+"]" + "(" + encomienda.getEstado() +")" + "// " + "ID: #" + encomienda.getId() + "# Destino: " + emp.getSucursalConDireccion(encomienda.getSucursalDestino()).getDireccion()+" Tipo: "+encomienda.getTipo());
        emp.getsucursalactual().getEncomiendasAlmacenadas().add(encomienda);
        emp.getsucursalactual().getEncomiendasRecibidas().remove(encomienda);
        emp.fbRef().child("sucursales").child(emp.getsucursalactual().getDireccion()).setValue(emp.getsucursalactual());
        //Recargar Encomiendas
        EncomiendasEnSucursal.getItems().clear();
        Boolean boolurgencia = false;
        for(Encomienda en: emp.getsucursalactual().getEncomiendasAlmacenadas())
        {
            if (en.getPrioridad().equals("Urgente"))
            {
                boolurgencia = true;
            }
            EncomiendasEnSucursal.getItems().add("["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + "// " + "ID: #" + en.getId() + "# Destino: " + emp.getSucursalConDireccion(en.getSucursalDestino()).getDireccion()+" Tipo: "+encomienda.getTipo());
        }
        if (boolurgencia == false) 
        {
            Urgencia.setText(null);
        }
        if (boolurgencia) 
        {
            Urgencia.setText("Hay una encomienda urgente!");
            boolurgencia = false;
        }
        
        EncomiendasRecibidas.getItems().clear();
        for(Encomienda en: emp.getsucursalactual().getEncomiendasRecibidas())
        {
            EncomiendasRecibidas.getItems().add("["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + "// " + "ID: #" + en.getId() + "# Destino: " + emp.getSucursalConDireccion(en.getSucursalDestino()).getDireccion());
        }   
    }
    
    @FXML
    private void ModificarPedidoAction() throws IOException{
        try
        {
            String encomiendaID;
            try{
                encomiendaID = EncomiendasEnSucursal.getSelectionModel().getSelectedItem().split("#")[1]; // Obtengo el id
            }catch(Exception e){
                System.out.println("6Debes seleccionar un pedido a entregar");
                return; // FIN
            }
            Encomienda encomienda = null;
            encomienda = emp.getsucursalactual().getEncomienda(encomiendaID);
            if (emp.getsucursalactual() == emp.getSucursalConDireccion(encomienda.getSucursalOrigen())) 
            {
                emp.setencomiendatemporal(encomienda);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLModificarPedido.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                FXMLModificarPedidoController controller = fxmlLoader.<FXMLModificarPedidoController>getController();
                controller.setSucursalController(this);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));  
                stage.show();
            }
            if(emp.getsucursalactual() != emp.getSucursalConDireccion(encomienda.getSucursalOrigen()))
            {
                MessageBox mb = new MessageBox("Solo se puede editar desde la sucursal de origen!", MessageBoxType.OK_CANCEL);
                mb.showAndWait();
                if (mb.getMessageBoxResult() == MessageBoxResult.OK)
                {
                        System.out.println("OK");
                        
                }
                else
                {
                        System.out.println("Cancel");
                }
            }
            
        } 
        catch (Exception e)
        {
            //Nada
        }
    }
    
    @FXML
    private void SalirAction() throws IOException{
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLLogin.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));  
            stage.show();
            Stage cerrar = (Stage)Salir.getScene().getWindow();
            cerrar.close();
        } 
        catch (Exception e)
        {
            //Nada
        }
    }
}

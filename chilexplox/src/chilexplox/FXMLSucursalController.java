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
import java.util.Iterator;
import java.util.List;
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
    private ComboBox<Camion> ChoiceBoxCamiones;
    @FXML
    private ComboBox<Sucursal> ChoiceBoxSucursales;
    @FXML
    private ChoiceBox<Sucursal> ChoiceBoxDestinoCamiones;
    @FXML
    private Button IngresarPedido;
    @FXML
    private Button PasarACola;
    @FXML
    private Button VerPedidosRecibidos;
    @FXML
    private Button Mensajes;
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                ChoiceBoxSucursales.getItems().add(s);
                ChoiceBoxDestinoCamiones.getItems().add(s);
                emp.getsucursales().add(s);
            }
        });
    }
    private void RemoveSucursal(final Sucursal s)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ChoiceBoxSucursales.getItems().remove(s);
                ChoiceBoxDestinoCamiones.getItems().remove(s);
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
                //System.out.println("Sucursal:" + post.toString());
                AddSucursal(s);
                System.out.println("Sucursal Agregada:" + s.toString());
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                Sucursal s = ds.getValue(Sucursal.class);
                // Elimino la versión antigua
                emp.getsucursales().remove(s);
                // Agrego la versión nueva
                emp.getsucursales().add(s);
                //System.out.println("Sucursal changed: "+s+ " camiones-> "+s.getCamionesEstacionados()+ " encomiendas-> "+s.getEncomiendasAlmacenadas());
                System.out.println("Sucursal Modificada:" + s.toString());
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
    // ---------Camiones: -------------
    private void AddCamion(final Camion c)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ChoiceBoxCamiones.getItems().add(c);
            }
        });
    }
    private void RemoveCamion(final Camion c)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Camion temp = null;
                for(Camion c2: ChoiceBoxCamiones.getItems())
                {
                    if (c2.getNombre().equals(c.getNombre()))
                    {
                        temp=c2;
                    }
                }
                ChoiceBoxCamiones.getItems().remove(temp);
                System.out.println(ChoiceBoxCamiones.getItems());
                
                temp = null;
                for(Camion c2: emp.getsucursalactual().getCamionesEstacionados())
                {
                    if (c2.getNombre().equals(c.getNombre()))
                    {
                        temp=c2;
                    }
                }
                emp.getsucursalactual().getCamionesEstacionados().remove(temp);
                System.out.println(emp.getsucursalactual().getCamionesEstacionados());
            }
        });
    }
    private void LoadCamiones()
    {
        espacioCamion = -1;
        ChoiceBoxCamiones.getItems().clear();
        
        Firebase camionesRef = emp.fbRef().child("sucursales").child(emp.getsucursalactual().getDireccion()).child("camionesEstacionados");
        camionesRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot ds, String previousChildKey) {
                Camion c = ds.getValue(Camion.class);
                //System.out.println("Camión:" + post.toString());
                AddCamion(c);
                System.out.println("Camión Agregado:" + c.toString());
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                // Elimino la versión antigua
                Camion old = emp.getCamionConNombre(string);
                RemoveCamion(old);
                // Agrego la versión nueva
                Camion c = ds.getValue(Camion.class);
                AddCamion(c);
                System.out.println("Camión Modificado:" + c.toString());
            }
            @Override
            public void onChildRemoved(DataSnapshot ds) {
                Camion c = ds.getValue(Camion.class);
                System.out.println("Camión REMOVIDO:" + c.toString());
                  
                RemoveCamion(c);
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
    // ---------Encomiendas almacenadas: -------------
    private void SetUrgentText()
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Urgencia.setText("Hay una encomienda urgente!"); //Falta deshabilitar esto...
            }
        });
    }
    private void AddEncomiendaToSucursal(final Encomienda en)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String s = "["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + "// " + "ID: #" + en.getId() + "# Destino: " + emp.getSucursalConDireccion(en.getSucursalDestino()).getDireccion()+" Tipo: "+en.getTipo();
                EncomiendasEnSucursal.getItems().add(s);
            }
        });
    }
    private void RemoveEncomiendaToSucursal(final Encomienda en)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String s = "["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + "// " + "ID: #" + en.getId() + "# Destino: " + emp.getSucursalConDireccion(en.getSucursalDestino()).getDireccion()+" Tipo: "+en.getTipo();
                EncomiendasEnSucursal.getItems().remove(s);
                Encomienda temp = null;
                for(Encomienda en2: emp.getsucursalactual().getEncomiendasAlmacenadas())
                {
                    if (en2.getId().equals(en.getId()))
                    {
                        temp=en2;
                    }
                }
                emp.getsucursalactual().getEncomiendasAlmacenadas().remove(temp);
            }
        });
    }
    private void LoadEncomiendasAlmacenadas()
    {
        EncomiendasEnSucursal.getItems().clear();
        
        Firebase encomiendasRef = emp.fbRef().child("sucursales").child(emp.getsucursalactual().getDireccion()).child("encomiendasAlmacenadas");
        encomiendasRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot ds, String previousChildKey) {
                Encomienda en = ds.getValue(Encomienda.class);
                if (en.getPrioridad().equals("Urgente")) 
                {
                    SetUrgentText();
                }
                AddEncomiendaToSucursal(en);
                System.out.println("Encomienda Agregada:" + en.toString());
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                Encomienda en = ds.getValue(Encomienda.class);
                /* NADA
                // Elimino la versión antigua
                RemoveEncomiendaToSucursal(string);
                // Agrego la versión nueva
                if (en.getPrioridad().equals("Urgente")) 
                {
                    SetUrgentText();
                }
                AddEncomiendaToSucursal(en);*/
                System.out.println("Encomienda Modificada:" + en.toString());
            }
            @Override
            public void onChildRemoved(DataSnapshot ds) {
                Encomienda en = ds.getValue(Encomienda.class);
                System.out.println("Encomienda REMOVIDA:" + en.toString());
                
                // Elimino la versión antigua
                RemoveEncomiendaToSucursal(en);
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                // Who cares... (No se requiere hacer nada)
            }
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println("ERROR FB-107:" + fe.getMessage());
            }
        });/**/
    }
    // ---------Encomiendas recibidas: -------------
   private void AddEncomiendaToRecibidos(final Encomienda en)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String s = "["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + "// " + "ID: #" + en.getId() + "# Destino: " + emp.getSucursalConDireccion(en.getSucursalDestino()).getDireccion()+" Tipo: "+en.getTipo();
                EncomiendasRecibidas.getItems().add(s);
                emp.getsucursalactual().getEncomiendasAlmacenadas().add(en);
            }
        });
    }
    private void RemoveEncomiendaToRecibidos(final Encomienda en)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String s = "["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + "// " + "ID: #" + en.getId() + "# Destino: " + emp.getSucursalConDireccion(en.getSucursalDestino()).getDireccion()+" Tipo: "+en.getTipo();
                EncomiendasRecibidas.getItems().remove(s);
                Encomienda temp = null;
                for(Encomienda en2: emp.getsucursalactual().getEncomiendasRecibidas())
                {
                    if (en2.getId().equals(en.getId()))
                    {
                        temp=en2;
                    }
                }
                emp.getsucursalactual().getEncomiendasRecibidas().remove(en);
            }
        });
    }
    private void LoadEncomiendasRecibidas()
    {
        EncomiendasRecibidas.getItems().clear();
        
        Firebase encomiendasRef = emp.fbRef().child("sucursales").child(emp.getsucursalactual().getDireccion()).child("encomiendasRecibidas");
        encomiendasRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot ds, String previousChildKey) {
                Encomienda en = ds.getValue(Encomienda.class);
                if (en.getPrioridad().equals("Urgente")) 
                {
                    SetUrgentText();
                }
                AddEncomiendaToRecibidos(en);
                System.out.println("EncomiendaR Agregada:" + en.toString());
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                Encomienda en = ds.getValue(Encomienda.class);
                /* NADA
                // Elimino la versión antigua
                RemoveEncomiendaToRecibidos(string);
                // Agrego la versión nueva
                if (en.getPrioridad().equals("Urgente")) 
                {
                    SetUrgentText();
                }
                AddEncomiendaToRecibidos(en);*/
                System.out.println("EncomiendaR Modificada:" + en.toString());
            }
            @Override
            public void onChildRemoved(DataSnapshot ds) {
                Encomienda en = ds.getValue(Encomienda.class);
                System.out.println("EncomiendaR REMOVIDA:" + en.toString());
                
                // Elimino la versión antigua
                RemoveEncomiendaToRecibidos(en);
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                // Who cares... (No se requiere hacer nada)
            }
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println("ERROR FB-109:" + fe.getMessage());
            }
        });/**/
    }
    
    // ---------Mensajes: -------------
    private void AddMensaje(final Mensaje m)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (m.getUrgente() == true) 
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
        });
    }
    private void RemoveMensaje(final Mensaje m)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (m.getUrgente() == true) 
                {
                    String mensajePreview ="URGENTE " + m.getContenido();
                    String[] mpArray = mensajePreview.split("\\r?\\n");
                    if (mpArray.length > 1) { mensajePreview = mpArray[0]+"...";} // Solo la primera linea
                    ListMessagesPreview.getItems().remove(mensajePreview);
                }
                else
                {
                    String mensajePreview = m.getContenido();
                    String[] mpArray = mensajePreview.split("\\r?\\n");
                    if (mpArray.length > 1) { mensajePreview = mpArray[0]+"...";} // Solo la primera linea
                    ListMessagesPreview.getItems().remove(mensajePreview);
                }
            }
        });
    }
    private void LoadMensajes()
    {
        ListMessagesPreview.getItems().clear();
        
        Firebase mensajesRef = emp.fbRef().child("sucursales").child(emp.getsucursalactual().getDireccion()).child("mensajesRecibidos");
        mensajesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot ds, String previousChildKey) {
                Mensaje m = ds.getValue(Mensaje.class);
                AddMensaje(m);
                System.out.println("Mensaje Agregado:" + m.toString());
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                // Elimino la versión antigua
                ListMessagesPreview.getItems().remove(Integer.parseInt(string));
                emp.getsucursalactual().getMensajesRecibidos().remove(Integer.parseInt(string));
                // Agrego la versión nueva
                Mensaje m = ds.getValue(Mensaje.class);
                AddMensaje(m);
                System.out.println("Mensaje Modificado:" + m.toString());
            }
            @Override
            public void onChildRemoved(DataSnapshot ds) {
                Mensaje m = ds.getValue(Mensaje.class);
                RemoveMensaje(m);
                System.out.println("Mensaje REMOVIDO:" + m.toString());
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                // Who cares... (No se requiere hacer nada)
            }
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println("ERROR FB-104:" + fe.getMessage());
            }
        });/**/
        
        ListMessagesPreview.setCellFactory(new Callback<ListView<String>, EllipsisListCell>() {
            @Override
            public EllipsisListCell call(ListView<String> p) {
                EllipsisListCell cell = new EllipsisListCell();
                return cell;
            }
        });
    }
    
    
    public void UpdateConSucursal(){
        // CARGAR ENCOMIENDAS ALMACENADAS
        LoadEncomiendasAlmacenadas();
        
        // CARGAR ENCOMIENDAS RECIBIDAS
        LoadEncomiendasRecibidas();
        
        // CARGAR PREVIEW MENSAJES!!
        LoadMensajes();
        
        // CARGAR CAMIONES DISPONIBLES
        LoadCamiones();
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
            System.out.println("Debes seleccionar un pedido a entregar");
            return; // FIN
        }
        System.out.println("AAAA: " + encomiendaID);
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
        EncomiendasRecibidas.getItems().remove(enco);
    }
    
    @FXML
    private void RefreshProgressBarAction(){
       
        Camion camionSeleccionado = ChoiceBoxCamiones.getValue();
        
        // Reviso que haya seleccion al momento de llamar al metodo
        if (camionSeleccionado == null) { return; }
        
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
        Sucursal s = ChoiceBoxSucursales.getValue();
        emp.setsucursalactual(s);
        UpdateConSucursal();
        ErrorLabelSucursal.setText("");
        LabelSucursal.setText(emp.getsucursalactual().getDireccion());
    }
    
    @FXML
    private void CargarCamionAction() throws IOException{
        String encomiendaID = EncomiendasEnSucursal.getSelectionModel().getSelectedItem().split("#")[1]; // Obtengo el id
        Encomienda encomienda = null;
        try
        {
            encomienda = emp.getsucursalactual().getEncomienda(encomiendaID);
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
            Camion camion = ChoiceBoxCamiones.getValue();
            if (camion != null && encomienda != null)// aca
            {
                if(camion.getTipo().equals(encomienda.getTipo()))
                {
                    espacioCamion = camion.PorcentajeDisponible();
                    if (espacioCamion>=1.0){ return; }

                    //CARGAR CAMION!!
                    Integer index = emp.getsucursalactual().getEncomiendasAlmacenadas().indexOf(encomienda);
                    emp.fbRef().child("sucursales").child(emp.getsucursalactual().getDireccion()).child("encomiendasAlmacenadas").child(index.toString()).removeValue();
                    
                    encomienda.setestado("En Camión");
                    camion.addencomienda(encomienda);
                    emp.fbRef().child("camiones").child(camion.getNombre()).setValue(camion);

                    //Recargar Encomiendas
                    // Firebase lo hace automágicamente :)

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
                }
                else
                {
                    MessageBox mb = new MessageBox("Solo se puede cargar una encomienda \n tipo "+ encomienda.getTipo()+
                            " en un camion "+encomienda.getTipo()+"!", MessageBoxType.OK_ONLY);
                    mb.showAndWait();

                    return;
                }//podria hacer un messagebox que no coincide tipo
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR 101: " + e.toString());
        }
    }
    
    @FXML
    private void EnviarCamionAction() throws IOException{
        // Obtener Camion a Enviar
        Camion camion = ChoiceBoxCamiones.getValue();
        // Obtener Sucursal destino
        Sucursal destinoSucursal = ChoiceBoxDestinoCamiones.getValue();
        if (camion != null && destinoSucursal != null)
        {
            // Enviar Camion
            Camion temp = null;
            for(Camion c2: emp.getsucursalactual().getCamionesEstacionados())
            {
                if (c2.getNombre().equals(camion.getNombre()))
                {
                    temp=c2;
                }
            }
            Integer index = emp.getsucursalactual().getCamionesEstacionados().indexOf(temp);
            emp.fbRef().child("sucursales").child(emp.getsucursalactual().getDireccion()).child("camionesEstacionados").child(index.toString()).removeValue();
            
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
            System.out.println("ERROR 102: " + e.toString());
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
        Camion camion = ChoiceBoxCamiones.getValue();
        LabelRecibidosCamiones.setText("Camion: "+camion.getNombre());
        NotificarErrorPedido.setVisible(false);
        EntregarEncomienda.setVisible(false);
        PasarACola.setVisible(false);
        QuitarEncomiendaCamion.setVisible(true);
        VerPedidosRecibidos.setVisible(true);
        EncomiendasRecibidas.getItems().clear();
        for(Encomienda en: camion.getEncomiendas())
        {
            EncomiendasRecibidas.getItems().add("["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + "// " + "ID: #" + en.getId() + "# Destino: " + emp.getSucursalConDireccion(en.getSucursalDestino()).getDireccion());
        }
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
        String encomiendaID = EncomiendasRecibidas.getSelectionModel().getSelectedItem().split("#")[1]; // Obtengo el id
        Encomienda encomienda = null;
        try
        {
            encomienda = emp.getsucursalactual().getEncomienda(encomiendaID);
        } 
        catch (Exception e)
        {
            return; //Nada
        }
        String mensaje = "Se ha detectado error en la encomienda ID #"+encomienda.getId()+"#";
        emp.getempleadoactual().EnviarMensaje(emp.getSucursalConDireccion(encomienda.getSucursalOrigen()), mensaje, true);
    }
    
    @FXML
    private void QuitarEncomiendaCamionAction() throws IOException{
        String encomiendaID = EncomiendasRecibidas.getSelectionModel().getSelectedItem().split("#")[1]; // Obtengo el id
        Encomienda encomienda = null;
        Camion camion = ChoiceBoxCamiones.getValue();
        for(Encomienda en: camion.getEncomiendas())
        {
            if (en.getId() == encomiendaID) 
            {
                encomienda = en;
            }
        }
        if (camion != null && encomienda != null)
        {
            espacioCamion = camion.PorcentajeDisponible();
            if (espacioCamion>=1.0){ return; }
            
            //CARGAR CAMION!!
            encomienda.setestado("En cola");
            camion.borrarencomienda(encomienda);
            emp.getsucursalactual().getEncomiendasAlmacenadas().add(encomienda);
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
            for(Encomienda en: camion.getEncomiendas())
            {
                EncomiendasRecibidas.getItems().add("["+en.getPrioridad()+"]" + "(" + en.getEstado() +")" + "// " + "ID: #" + en.getId() + "# Destino: " + emp.getSucursalConDireccion(en.getSucursalDestino()).getDireccion());
            }
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
        }
    }
    
    @FXML
    private void PasarAColaAction() throws IOException{
        String encomiendaID = EncomiendasRecibidas.getSelectionModel().getSelectedItem().split("#")[1]; // Obtengo el id
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
        //Recargar Encomiendas
        EncomiendasEnSucursal.getItems().clear();
        Boolean boolurgencia = false;
        for(Encomienda en: emp.getsucursalactual().getEncomiendasAlmacenadas())
        {
            if (en.getPrioridad() == "Urgente") 
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
            String encomiendaID = EncomiendasEnSucursal.getSelectionModel().getSelectedItem().split("#")[1]; // Obtengo el id
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
}

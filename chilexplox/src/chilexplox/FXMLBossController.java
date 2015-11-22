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
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxResult;
import se.mbaeumer.fxmessagebox.MessageBoxType;
/**
 * FXML Controller class
 *
 * @author Felix
 */
public class FXMLBossController implements Initializable {
    @FXML
    private Label LabelNombreJefe;
    @FXML
    private ComboBox<Sucursal> ChoiceBoxSucursales;
    @FXML
    private Button Salir;
    @FXML
    private Button Mensajes;
    @FXML
    private Button CargarSucursal;
    @FXML
    private Button VerErrores;
    @FXML
    private ListView ListMessagesPreview; //TIENE CELL-FACTORY!! No convertir a ListView<String>!!
    
    @FXML
    private Label LabelSucursal;
     @FXML
    private Label ErrorLabelSucursal;
   
    Empresa emp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emp = Empresa.getInstance();
        String bienvenida = "Hola, estimad@  " + emp.getjefeactual().getNombre();
        LabelNombreJefe.setText(bienvenida);
        LoadSucursales();
        
        
    }
    
    
    private void AddSucursal(final Sucursal s)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                
                ChoiceBoxSucursales.getItems().add(s);
                
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
    @FXML
    private void agregarEmpleadoAction() throws IOException
    {
        try 
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLAgregarEmpleado.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));  
            stage.show();
        } 
        catch (Exception e)
        {
            System.out.println("ERROR AL CARGAR LA VENTANA AGREGAR_EMPLEADO");
        }
    }
    @FXML
    private void eliminarEmpleadoAction() throws IOException
    {
        try 
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLEliminarEmpleado.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));  
            stage.show();
        } 
        catch (Exception e)
        {
            System.out.println("ERROR AL CARGAR LA VENTANA ELIMINAR_EMPLEADO");
        }
    }
    @FXML
    private void btnCargarSucursal() throws IOException{
        Sucursal s = ChoiceBoxSucursales.getValue();
        emp.setsucursalactual(s);
        UpdateConSucursal();
        
        LabelSucursal.setText(s.getDireccion());
    }
    
    public void UpdateConSucursal(){
        // CARGAR ENCOMIENDAS
        
       
        
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
        
    }
    public void RefreshConSucursal()
    {
        Platform.runLater(new Runnable() { // Evitar problemas con el "Not on FX Thread"
            @Override
            public void run() {
                Sucursal s = emp.getsucursalactual();
                System.out.println("Refrescando..." + s + " - " + s.getEncomiendasAlmacenadas() + " - " + s.getEncomiendasRecibidas() + " - " + s.getCamionesEstacionados());
                // CARGAR ENCOMIENDAS
                
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
               
            }
        });
    }
    
    @FXML
    private void MensajesAction() throws IOException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLMensajesGerente.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));  
            stage.show();
        } catch (Exception e){
            ErrorLabelSucursal.setText("¡Debes seleccionar una sucursal!");
        }
    }
    @FXML
    private void CargarVentanaInformes()
    {/*
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
        }*/
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
    
    @FXML
    private void VerErroresAction() throws IOException{
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLErrores.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));  
            stage.show();
        } 
        catch (Exception e)
        {
            //Nada
        }
    }
    
    
    
}

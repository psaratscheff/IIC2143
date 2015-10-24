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
import java.util.Iterator;
import java.util.List;
import javafx.util.Callback;

//Listener
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;

/**
 * FXML Controller class
 *
 * @author Thomas Pryce Jones
 */
public class FXMLSucursalController implements Initializable {
    @FXML
    private Label LabelNombreTrabajador;
    @FXML
    private ChoiceBox<String> ChoiceBoxCamiones;
    @FXML
    private ChoiceBox<String> ChoiceBoxSucursales;
    @FXML
    private ChoiceBox<String> ChoiceBoxDestinoCamiones;
    @FXML
    private Button IngresarPedido;
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
        emp = Empresa.getInstance();
        String bienvenida = "Hola, " + emp.empleadoActual.nombre;
        LabelNombreTrabajador.setText(bienvenida);
        for (Sucursal s: emp.sucursales) 
        {
            ChoiceBoxSucursales.getItems().add(s.direccion);
            ChoiceBoxDestinoCamiones.getItems().add(s.direccion);
        }
        // SUCURSAL CHOICEBOX LISTENER (En caso de no usar el boton cargar)
        /*ChoiceBoxSucursales.getSelectionModel().selectedIndexProperty().addListener(new
            ChangeListener<Number>() {
                public void changed(ObservableValue ov,
                    Number value, Number new_value) {
                       String direccion = ChoiceBoxSucursales.getValue();
                       for(Sucursal s: emp.sucursales)
                       {
                           if (s.direccion == direccion)
                           {
                               emp.sucursalActual = s;
                           }
                       }
                       List<String> list = emp.getDireccionSucursales();
                       String[] sucursalesArray = list.toArray(new String[list.size()]);
                       UpdateConSucursal();
                       ErrorLabelSucursal.setText("");
                       LabelSucursal.setText(sucursalesArray[new_value.intValue()]);
                   }
            });/**/
        // CAMIONES CHOICEBOX LISTENER
        /*ChoiceBoxCamiones.getSelectionModel().selectedIndexProperty().addListener(new
            ChangeListener<Number>() {
                public void changed(ObservableValue ov,
                    Number value, Number new_value) {
                       String name = ChoiceBoxCamiones.getValue();
                       for(Camion c: emp.sucursalActual.camionesEstacionados)
                       {
                           if (c.getNombre().equals(name))
                           {
                               camionActual = c;
                               espacioCamion = c.PorcentajeDisponible();
                           }
                       }
                       //Stufffff to do al seleccionar camion
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
            });/**/
    }  
    
    public void UpdateConSucursal()
    {
        // CARGAR ENCOMIENDAS
        EncomiendasEnSucursal.getItems().clear();
        for(Encomienda en: emp.sucursalActual.encomiendasAlmacenadas)
        {
            EncomiendasEnSucursal.getItems().add("["+en.prioridad+"] // "+"ID: #"+en.id+"# Destino: " + en.destino.direccion);
        }
        // CARGAR ENCOMIENDAS RECIBIDAS
        EncomiendasRecibidas.getItems().clear();
        for(Encomienda en: emp.sucursalActual.encomiendasRecibidas)
        {
            EncomiendasRecibidas.getItems().add("["+en.prioridad+"] // "+"ID: #"+en.id+"# Destino: " + en.destino.direccion);
        }
        // CARGAR PREVIEW MENSAJES!! (Agregar un timer de sincronización?)
        ListMessagesPreview.getItems().clear();
        for(Mensaje m: emp.sucursalActual.mensajesRecibidos)
        {
            if (m.urgente == true) 
            {
                String mensajePreview ="URGENTE " + m.contenido;
                String[] mpArray = mensajePreview.split("\\r?\\n");
                if (mpArray.length > 1) { mensajePreview = mpArray[0]+"...";} // Solo la primera linea
                ListMessagesPreview.getItems().add(0, mensajePreview); // Añado al principio
            }
            else
            {
                String mensajePreview = m.contenido;
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
        for (Camion c: emp.sucursalActual.camionesEstacionados) 
        {
            ChoiceBoxCamiones.getItems().add(c.getNombre());
        }/**/
    }
    
    @FXML
    private void EntregarEncomiendaAction()
    {
        String encomienda = EncomiendasRecibidas.getSelectionModel().getSelectedItem();
        EncomiendasRecibidas.getItems().remove(encomienda);
    }
    
    @FXML
    private void RefreshProgressBarAction() // SE EJECUTA TODO EL RATO!!
    {
        String name = ChoiceBoxCamiones.getValue();
        for(Camion c: emp.sucursalActual.camionesEstacionados)
        {
            if (c.getNombre().equals(name))
            {
                camionActual = c;
                espacioCamion = c.PorcentajeDisponible();
            }
        }
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
        String direccion = ChoiceBoxSucursales.getValue();
        for(Sucursal s: emp.sucursales)
        {
            if (s.direccion == direccion)
            {
                emp.sucursalActual = s;
            }
        }
        UpdateConSucursal();
        ErrorLabelSucursal.setText("");
        LabelSucursal.setText(emp.sucursalActual.direccion);
    }
    
    @FXML
    private void CargarCamionAction() throws IOException{
        String encomiendaID = EncomiendasEnSucursal.getSelectionModel().getSelectedItem().split("#")[1]; // Obtengo el id
        Encomienda encomienda = null;
        try
        {
            encomienda = emp.sucursalActual.getEncomienda(Integer.parseInt(encomiendaID));
        } 
        catch (Exception e)
        {
            return; //Nada
        }
        
        //OJO con la importancia de que el id sea un numero valido
        // Obtener Camion a Enviar
        String nombreCamion = ChoiceBoxCamiones.getValue();
        Camion camion = null;
        for(Camion c: emp.sucursalActual.camionesEstacionados)
        {
            if (c.getNombre().equals(nombreCamion))
            {
                camion = c;
            }
        }
        if (camion != null && encomienda != null)
        {
            espacioCamion = camion.PorcentajeDisponible();
            if (espacioCamion>=1.0){ return; }
            
            //CARGAR CAMION!!
            camion.encomiendas.add(encomienda);
            emp.sucursalActual.encomiendasAlmacenadas.remove(encomienda);
            //Recargar Encomiendas
            EncomiendasEnSucursal.getItems().clear();
            for(Encomienda en: emp.sucursalActual.encomiendasAlmacenadas)
            {
                EncomiendasEnSucursal.getItems().add("["+en.prioridad+"] // "+"ID: #"+en.id+"# Destino: " + en.destino.direccion);
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
    private void EnviarCamionAction() throws IOException{
        // Obtener Camion a Enviar
        String nombreCamion = ChoiceBoxCamiones.getValue();
        Camion camion = null;
        for(Camion c: emp.sucursalActual.camionesEstacionados)
        {
            if (c.getNombre().equals(nombreCamion))
            {
                camion = c;
            }
        }
        if (camion != null)
        {
            // Obtener Sucursal destino
            String destino = ChoiceBoxDestinoCamiones.getValue();
            Sucursal destinoSucursal;
            for(Sucursal s: emp.sucursales)
            {
                if (s.direccion.equals(destino))
                {
                    // Enviar Camion
                    destinoSucursal = s;
                    destinoSucursal.camionesEstacionados.add(camion);
                    emp.sucursalActual.camionesEstacionados.remove(camion);
                    // Descargar encomiendas en destino (Inmediato por ahora)
                    for (Encomienda e: camion.encomiendas)
                    {
                        destinoSucursal.encomiendasRecibidas.add(e);
                    }
                    camion.encomiendas.clear();/**/
                }
            }
            // NO SE SI PONER ESTO DENTRO DEL FOR DE AQUI ARRIBA ^
            ChoiceBoxDestinoCamiones.getSelectionModel().clearSelection();
            // CARGAR CAMIONES DISPONIBLES
            ChoiceBoxCamiones.getItems().clear();
            for (Camion c: emp.sucursalActual.camionesEstacionados) 
            {
                ChoiceBoxCamiones.getItems().add(c.getNombre());
            }/**/
            espacioCamion = -1;
            ProgressBarCapacity.setProgress(-1); // -1 para indeterminado
        }
    }
    
    @FXML
    private void IngresarPedidoAction() throws IOException{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLIngresoPedido.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));  
            stage.show();
        } catch (Exception e){
            ErrorLabelSucursal.setText("¡Debes seleccionar una sucursal!");
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
}

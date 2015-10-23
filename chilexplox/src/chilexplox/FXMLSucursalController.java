/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;
import chilexplox.classes.Camion;
import chilexplox.classes.Empresa;
import chilexplox.classes.Encomienda;
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
    private ChoiceBox<String> ChoiceBoxSucursales;
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
    private ChoiceBox<Camion> Camiones;
    @FXML
    private Button EnviarCamion;
    @FXML
    private Button CargarSucursal;
    @FXML
    private ListView<String> ListMessagesPreview;
    @FXML
    private ListView<String> EncomiendasEnSucursal;
    @FXML
    private ListView<String> EncomiendasRecibidas;
    @FXML
    private Label ErrorLabelSucursal;
    @FXML
    private Label LabelSucursal;

    Empresa emp;
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
        }
        ChoiceBoxSucursales.getSelectionModel().selectedIndexProperty().addListener(new
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
            });
    }  
    
    public void UpdateConSucursal()
    {
        for(Encomienda en: emp.sucursalActual.encomiendasAlmacenadas)
        {
            EncomiendasEnSucursal.getItems().add("["+en.prioridad+"] // "+"ID: "+en.id+" Destino: " + en.destino);
        }
        
    }
    
    
    
    @FXML
    private void IngresarPedidoAction(MouseEvent event) throws IOException{
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
    private void MensajesAction(MouseEvent event) throws IOException{
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
    private void btnCargarSucursal(MouseEvent event) throws IOException{
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
    }
}

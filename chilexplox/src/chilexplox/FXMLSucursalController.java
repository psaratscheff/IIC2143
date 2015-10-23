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
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Thomas Pryce Jones
 */
public class FXMLSucursalController implements Initializable {
    @FXML
    private Label LabelNombreTrabajador;
    @FXML
    private ChoiceBox<String> Sucursales;
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
            Sucursales.getItems().add(s.direccion);
        }
    }  
    
    public void UpdateConSucursal()
    {
        for(Encomienda en: emp.sucursalActual.encomiendasAlmacenadas)
        {
            System.out.print("asd");
            EncomiendasEnSucursal.getItems().add("["+en.prioridad+"] // "+"ID: "+en.id+" Destino: " + en.destino);
        }
        
    }
    
    
    
    @FXML
    private void IngresarPedidoAction(MouseEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLIngresoPedido.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));  
        stage.show();
    }
    
    @FXML
    private void MensajesAction(MouseEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLMensajesRecibidos.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));  
        stage.show();
    }
    
    @FXML
    private void btnCargarSucursal(MouseEvent event) throws IOException{
        String direccion = Sucursales.getValue();
        for(Sucursal s: emp.sucursales)
        {
            if (s.direccion == direccion)
            {
                emp.sucursalActual = s;
            }
        }
        UpdateConSucursal();
    }
}

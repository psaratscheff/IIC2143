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
public class FXMLClienteController implements Initializable {

    @FXML
    private Button BotonAgregarEncomienda;
    @FXML
    private Button BotonVerEstadoEncomienda;
    @FXML
    private Label label1;
    @FXML
    private TextField TextFieldNrEncomeinda;
    @FXML
    private Label LabelNombreTrabajador;
    Empresa emp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        emp = Empresa.getInstance();
        String bienvenida = "Hola, " + emp.getclienteactual().getnombre();
        LabelNombreTrabajador.setText(bienvenida);
    }
    
   /* @FXML
    private void EventoAgregarEncomiendaCliente(javafx.event.ActionEvent event) {
        try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLIngresoPedidoCliente.fxml"));
          
        } catch (Exception e){
            System.out.println("ERROR 102: " + e.toString());
        }
    }*/
    
    @FXML
    private void EventoAgregarEncomiendaCliente(javafx.event.ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLIngresoPedidoCliente.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            FXMLIngresoPedidoClienteController controller = fxmlLoader.<FXMLIngresoPedidoClienteController>getController();
            controller.setClienteController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));  
            stage.show();
        } catch (Exception e){
            System.out.println("ERROR 102: " + e.toString());
        }
    }
    
    
    
}

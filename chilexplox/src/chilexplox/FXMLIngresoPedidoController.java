/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Empresa;
import chilexplox.classes.Encomienda;
import chilexplox.classes.Sucursal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Thomas Pryce Jones
 */
public class FXMLIngresoPedidoController implements Initializable {
    @FXML
    private TextField CNombre;
    @FXML
    private TextField CApellido;
    @FXML
    private TextField CDireccion;
    @FXML
    private TextField CRut;
    @FXML
    private Button Pagar;
    @FXML
    private Button AgregarEncomienda;
    @FXML
    private TextField EAncho;
    @FXML
    private ChoiceBox<String> EPrioridad;
    @FXML
    private TextField EPeso;
    @FXML
    private TextField ELargo;
    @FXML
    private ChoiceBox<String> EDestino;
    @FXML
    private Label EOrigen;
    @FXML
    private Label TotalPedido;
    @FXML
    private ListView<Encomienda> ListEncomiendas;

    Empresa emp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emp = Empresa.getInstance();
        EPrioridad.getItems().add("Normal");
        EPrioridad.getItems().add("Alta");
        for (Sucursal s: emp.sucursales) 
        {
            EDestino.getItems().add(s.direccion);
        }
        EOrigen.setText(emp.sucursalActual.direccion);
        
    }    

    @FXML
    private void btnPagar(MouseEvent event) 
    {
        
    }

    @FXML
    private void btnAgregarEncomienda(MouseEvent event) 
    {
        int tamaño = Integer.parseInt(EPeso.getText())*Integer.parseInt(ELargo.getText())*Integer.parseInt(EAncho.getText());
        String prioridad = EPrioridad.getValue();
        String destino = EDestino.getValue();
    }
    
}

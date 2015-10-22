/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Camion;
import chilexplox.classes.Sucursal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Thomas Pryce Jones
 */
public class FXMLSucursalController implements Initializable {
    @FXML
    private Label LabelNombreTrabajador;
    @FXML
    private ChoiceBox<Sucursal> Sucursales;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

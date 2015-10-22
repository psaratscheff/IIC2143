/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Thomas Pryce Jones
 */
public class FXMLVentanaPagoController implements Initializable {
    @FXML
    private Label messageLabel;
    @FXML
    private Label MontoTotal;
    @FXML
    private HBox actionParent;
    @FXML
    private Button cancelButton;
    @FXML
    private HBox okParent;
    @FXML
    private Button okButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

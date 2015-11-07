/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Empresa;
import chilexplox.classes.Encomienda;
import chilexplox.classes.Ingreso;
import chilexplox.classes.Pedido;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Thomas Pryce Jones
 */
public class FXMLVentanaPagoController implements Initializable {
    Empresa emp;
    Pedido pedido;
    Stage AgregarEncomiendaWindow;
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
    @FXML
    private Stage parentStage;
    
    private int valor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nada :)
    }    
    
    @FXML
    private void btnPagar(MouseEvent event) throws IOException 
    {
        for(Encomienda en: pedido.getencomiendas())
        {
            emp.getsucursalactual().getencomiendasalmacenadas().add(en);
            emp.getencomiendas().add(en);
        }
        
        Calendar cal = Calendar.getInstance();
        emp.addIngreso(new Ingreso(this.valor, cal.getTime()));
        
        Stage stage = (Stage) okButton.getScene().getWindow();
        this.parentStage.close();
        stage.close();
    }
    
    @FXML
    private void btnCancelar(MouseEvent event) throws IOException 
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    void setPedido(Pedido pedido) {
        this.pedido = pedido;
        valor = pedido.CalcularValor();
        String precio= Integer.toString(valor);
        MontoTotal.setText("$"+precio);
    }

    void setParentWindow(Stage stage) {
        this.parentStage = stage;
    }

    void setEmpresa(Empresa emp) {
        this.emp = emp;
    }
    
}

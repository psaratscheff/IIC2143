/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Cliente;
import chilexplox.classes.Empresa;
import chilexplox.classes.Encomienda;
import chilexplox.classes.Ingreso;
import chilexplox.classes.Pedido;
import chilexplox.classes.Sucursal;
import com.firebase.client.Firebase;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxType;

/**
 * FXML Controller class
 *
 * @author Thomas Pryce Jones
 */
public class FXMLVentanaPagoClienteController implements Initializable {
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
    private FXMLSucursalController sucursalController;
    private FXMLClienteController clienteController;
    Firebase postRef;
    Firebase newPostRef;
    private Cliente cliente;
    Empresa emp;
    Pedido pedido;
    Stage AgregarEncomiendaWindow;
    Sucursal suc = null;

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
        Platform.runLater(new Runnable() { // Evitar problemas con el "Not on FX Thread"
            @Override
            public void run(){
                // Agrego encomiendas
                for(Encomienda en: pedido.getEncomiendas())
                {
                    String id = en.getId();
                    en.setEmpleado("Ingresado por Cliente");
                    en.setClienteRut(cliente.getRut());
                    //----Agrego encomienda en empresa---
                    postRef = emp.fbRef().child("encomiendas");
                    newPostRef = postRef.push(); String id1 = newPostRef.getKey(); en.setId(id1); newPostRef.setValue(en);
                    for (Sucursal s: emp.getsucursales())
                    {
                        if (s.getDireccion().equals(en.getSucursalOrigen()))
                        {
                            suc = s;
                            suc.getEncomiendasAlmacenadas().add(en);
                        }
                    }
                    MessageBox mb = new MessageBox("Número de encomienda "+id+" es "+en.getId(), MessageBoxType.OK_ONLY);
                    mb.showAndWait();
                }
                //----Agrego el ingreso a la empresa---
                Calendar cal = Calendar.getInstance();
                Ingreso i = new Ingreso(valor, cal.getTime(), "Ingresado por Cliente", "Ingresado por Cliente");
                emp.addIngreso(i);
                postRef = emp.fbRef().child("ingresos");
                newPostRef = postRef.push(); i.setId(newPostRef.getKey()); newPostRef.setValue(i);
                //----Actualizo la sucursal con las nuevas encomiendas e ingreso---
                postRef = emp.fbRef().child("sucursales");
                newPostRef = postRef.child(suc.getDireccion()); newPostRef.setValue(suc);
                //----Agrego el pedido a la empresa---
                postRef = emp.fbRef().child("pedidos");
                newPostRef = postRef.push(); pedido.setId(newPostRef.getKey()); newPostRef.setValue(pedido);
            }
        });
        
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
    
    void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    void setSucursalController(FXMLSucursalController aThis) {
        this.sucursalController = aThis;
    }
    void setClienteController(FXMLClienteController aThis) {
        this.clienteController = aThis;
    }
}

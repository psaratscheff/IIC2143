/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Empresa;
import chilexplox.classes.Encomienda;
import chilexplox.classes.Pedido;
import chilexplox.classes.Sucursal;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Thomas Pryce Jones
 */
public class FXMLModificarPedidoController implements Initializable {

    @FXML
    private TextField CNombre;
    @FXML
    private TextField CApellido;
    @FXML
    private TextField CDireccion;
    @FXML
    private TextField CRut;
    @FXML
    private Button Editar;
    @FXML
    private TextField EAncho;
    @FXML
    private ChoiceBox<String> EPrioridad;
    @FXML
    private TextField EPeso;
    @FXML
    private TextField ELargo;
    @FXML
    private TextField EDireccion;
    @FXML
    private ChoiceBox<String> EDestino;
    @FXML
    private Label EOrigen;
    
    private FXMLSucursalController sucursalController;

    Empresa emp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emp = Empresa.getInstance();
        EPrioridad.getItems().add("Urgente");
        EPrioridad.getItems().add("Normal");
        EPrioridad.getItems().add("Express");
        for (Sucursal s: emp.getsucursales()) 
        {
            EDestino.getItems().add(s.getDireccion());
        }
        EOrigen.setText(emp.getsucursalactual().getDireccion());
        EAncho.setText(Integer.toString(emp.getencomiendatemporal().getAncho()));
        ELargo.setText(Integer.toString(emp.getencomiendatemporal().getLargo()));
        EPeso.setText(Integer.toString(emp.getencomiendatemporal().getPeso()));
        EDireccion.setText(emp.getencomiendatemporal().getDireccionDestino());
    } 
    
    @FXML
    private void EditarAction() throws IOException{
        int tamaño = Integer.parseInt(EPeso.getText())*Integer.parseInt(ELargo.getText())*Integer.parseInt(EAncho.getText());
        String prioridad = EPrioridad.getValue();
        String destino = EDestino.getValue();
        for(Sucursal s: emp.getsucursales())
            {
                if (s.getDireccion() == destino) 
                {
                    emp.getencomiendatemporal().setdestino(s.getDireccion());
                    emp.getencomiendatemporal().setprioridad(prioridad);
                    emp.getencomiendatemporal().settamaño(tamaño);
                    emp.getencomiendatemporal().setancho(Integer.parseInt(EAncho.getText()));
                    emp.getencomiendatemporal().setlargo(Integer.parseInt(ELargo.getText()));
                    emp.getencomiendatemporal().setpeso(Integer.parseInt(EPeso.getText()));
                    emp.getencomiendatemporal().setdirecciondestino(EDireccion.getText());
                }
            }
        this.sucursalController.UpdateConSucursal();
        Stage stage = (Stage) Editar.getScene().getWindow();
        stage.close();
    }
    
    void setSucursalController(FXMLSucursalController aThis) {
        this.sucursalController = aThis;
    }
    
}

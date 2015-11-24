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
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxType;

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
    @FXML
    private TextField EEstado;
    
    private FXMLSucursalController sucursalController;

    Empresa emp;
    Firebase postRef;
    Firebase newPostRef;
    Cliente cliente;
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
        EDestino.getSelectionModel().select(emp.getencomiendatemporal().getSucursalDestino());
        EOrigen.setText(emp.getencomiendatemporal().getSucursalOrigen());
        EAncho.setText(Integer.toString(emp.getencomiendatemporal().getAncho()));
        ELargo.setText(Integer.toString(emp.getencomiendatemporal().getLargo()));
        EPeso.setText(Integer.toString(emp.getencomiendatemporal().getPeso()));
        EDireccion.setText(emp.getencomiendatemporal().getDireccionDestino());
        EPrioridad.getSelectionModel().select(emp.getencomiendatemporal().getPrioridad());
        EEstado.setText(emp.getencomiendatemporal().getEstado());
        
        String cRut = emp.getencomiendatemporal().getClienteRut();
        for (Cliente c: emp.getclientes())
        {
            if (c.getRut().equals(cRut))
            {
                cliente = c;
            }
        }
        CNombre.setText(cliente.getNombre());
        CApellido.setText(cliente.getApellido());
        CDireccion.setText(cliente.getDireccion());
        CRut.setText(cRut);
    } 
    public void disableModificar()
    {
        Editar.disableProperty().set(true);
        Editar.visibleProperty().set(false);
        EDestino.getItems().add(emp.getencomiendatemporal().getSucursalDestino());
        EDestino.getSelectionModel().select(emp.getencomiendatemporal().getSucursalDestino());
        CNombre.disableProperty().set(true);
        CApellido.disableProperty().set(true);
        CDireccion.disableProperty().set(true);
        CRut.disableProperty().set(true);
        EDestino.disableProperty().set(true);
        EOrigen.disableProperty().set(true);
        EAncho.disableProperty().set(true);
        ELargo.disableProperty().set(true);
        EPeso.disableProperty().set(true);
        EDireccion.disableProperty().set(true);
        EPrioridad.disableProperty().set(true);
        EEstado.disableProperty().set(true);
    }
    @FXML
    private void EditarAction() throws IOException
    {
        if (EAncho.getText().equals("") || ELargo.getText().equals("") || EPeso.getText().equals("") || EPrioridad.getValue().equals("") || EDestino.getValue().equals("") || EDireccion.getText().equals("") || CNombre.getText().equals("") || CApellido.getText().equals("") || CDireccion.getText().equals("") || CRut.getText().equals("") )
        {
            MessageBox mb = new MessageBox("No se pueden dejar campos en blanco", MessageBoxType.OK_ONLY);
            mb.showAndWait();
            return;
        }
        Platform.runLater(new Runnable() { // Evitar problemas con el "Not on FX Thread"
            @Override
            public void run(){
                
                Encomienda enco = emp.getencomiendabyid(emp.getencomiendatemporal().getId());
                
                int tamaño = Integer.parseInt(EPeso.getText())*Integer.parseInt(ELargo.getText())*Integer.parseInt(EAncho.getText());
                String prioridad = EPrioridad.getValue();
                String destino = EDestino.getValue();
                enco.setdestino(destino);
                enco.setprioridad(prioridad);
                enco.settamaño(tamaño);
                enco.setancho(Integer.parseInt(EAncho.getText()));
                enco.setlargo(Integer.parseInt(ELargo.getText()));
                enco.setpeso(Integer.parseInt(EPeso.getText()));
                enco.setdirecciondestino(EDireccion.getText());
                enco.setEmpleado(emp.getempleadoactual().getUsername());
                
                Encomienda temp = null;
                for(Encomienda e: emp.getsucursalactual().getEncomiendasAlmacenadas())
                {
                    if (e.getId().equals(enco.getId()))
                    {
                        temp = e;
                        e = enco;
                    }
                }
                emp.getsucursalactual().getEncomiendasAlmacenadas().remove(temp);
                emp.getsucursalactual().getEncomiendasAlmacenadas().add(enco);
                
                
                //----Actualizo la sucursal---
                postRef = emp.fbRef().child("sucursales");
                newPostRef = postRef.child(emp.getsucursalactual().getDireccion()); newPostRef.setValue(emp.getsucursalactual());
                
                //----Actualizo la encomienda---
                postRef = emp.fbRef().child("encomiendas");
                newPostRef = postRef.child(enco.getId()); newPostRef.setValue(enco);
                
                // Agrego/Actualizo información cliente
                cliente.setnNombre(CNombre.getText());
                cliente.setApellido(CApellido.getText());
                cliente.setDireccion(CDireccion.getText());
                cliente.setRut(CRut.getText());
                Cliente c = null;
                for (Cliente c2: emp.getclientes())
                {
                    if (c2.getRut().equals(cliente.getRut()))
                    {
                        String password = c2.getPassword();
                        cliente.setPassword(password); // Mantener la contraseña original, sino queda con el nombre como contraseña
                    }
                }
                emp.fbRef().child("clientes").child(cliente.getRut()).setValue(cliente);
            }
        });
        
        Stage stage = (Stage) Editar.getScene().getWindow();
        stage.close();
    }
    
    void setSucursalController(FXMLSucursalController aThis) {
        this.sucursalController = aThis;
    }
}

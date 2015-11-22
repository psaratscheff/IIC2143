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
    Firebase postRef;
    Firebase newPostRef;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emp = Empresa.getInstance();
        EPrioridad.getItems().add("Urgente");
        EPrioridad.getItems().add("Normal");
        EPrioridad.getItems().add("Express");
        
        LoadSucursales();
        
        EOrigen.setText(emp.getsucursalactual().getDireccion());
        EAncho.setText(Integer.toString(emp.getencomiendatemporal().getAncho()));
        ELargo.setText(Integer.toString(emp.getencomiendatemporal().getLargo()));
        EPeso.setText(Integer.toString(emp.getencomiendatemporal().getPeso()));
        EDireccion.setText(emp.getencomiendatemporal().getDireccionDestino());
    } 
    
    @FXML
    private void EditarAction() throws IOException{
        
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
                
            }
        });
        
        Stage stage = (Stage) Editar.getScene().getWindow();
        stage.close();
    }
    
    void setSucursalController(FXMLSucursalController aThis) {
        this.sucursalController = aThis;
    }
    
    private void LoadSucursales()
    {
        Firebase sucursalesRef = emp.fbRef().child("sucursales");
        sucursalesRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot ds, String previousChildKey) {
                Sucursal s = ds.getValue(Sucursal.class);
                EDestino.getItems().add(s.getDireccion());
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                //Hay que hacer algo por si cambia nombre?
            }
            @Override
            public void onChildRemoved(DataSnapshot ds) {
                Sucursal s = ds.getValue(Sucursal.class);
                System.out.println("Sucursal REMOVIDA:" + s.toString());
                EDestino.getItems().remove(s.getDireccion());
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                // Who cares... (No se requiere hacer nada)
            }
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println("ERROR FB-101:" + fe.getMessage());
            }
        });/**/
    }
}

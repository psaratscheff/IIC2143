/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import chilexplox.classes.Empresa;
import chilexplox.classes.Cliente;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxResult;
import se.mbaeumer.fxmessagebox.MessageBoxType;


/**
 * FXML Controller class
 *
 * @author Felix
 */
public class FXMLRegistraseClienteController implements Initializable {

  
    @FXML
    private TextField CNombre;
    
    @FXML
    private TextField CApellido;
   
    @FXML
    private TextField CDireccion;
   
    @FXML
    private TextField CRut;
   
    @FXML
    private PasswordField CClave;
    
    @FXML
    private PasswordField CClave2;
    
    Empresa emp = Empresa.getInstance();
    List<Cliente> clientes= new ArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //TODO
    } 
    public void btnRegistrarmeAction(MouseEvent event) throws IOException 
    {
        String nombre= CNombre.getText();
        String apellido= CApellido.getText();
        String direccion= CDireccion.getText();
        String rut= CRut.getText();
        String clave= CClave.getText();
        String clave2= CClave2.getText();
        if (nombre.equals("") || apellido.equals("") || direccion.equals("") || rut.equals("") || clave.equals("") || clave2.equals("") )
        {
                MessageBox mb = new MessageBox("Por favor ingrese todos los campos", MessageBoxType.OK_ONLY);
                mb.showAndWait();
                if (mb.getMessageBoxResult() == MessageBoxResult.OK)
                {
                    System.out.println("OK");
                }
                return; // Evito registrar un usuario con campos en blanco (Fallaría el JSON format!)
        }
        for(Cliente c:clientes)
        {
            if(c.getRut().equals(CRut.getText()))
            {
                MessageBox mb = new MessageBox("El nombre de usuario ya esta ocupado.", MessageBoxType.OK_ONLY);
                mb.showAndWait();
                if (mb.getMessageBoxResult() == MessageBoxResult.OK)
                {
                    System.out.println("OK");
                }
                return; // Evito sobreescribir un usuario ya registrado
            }
        }
        if(CClave.getText().equals(CClave2.getText()))
        {
            Firebase postRef;
            Cliente c1 = new Cliente(nombre, apellido, direccion, rut, clave);
            postRef = emp.fbRef().child("clientes");
            postRef.child(c1.getRut()).setValue(c1);
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
        else
        {
            MessageBox mb = new MessageBox("Las claves no concuerdan.", MessageBoxType.OK_ONLY);
            mb.showAndWait();
            if (mb.getMessageBoxResult() == MessageBoxResult.OK)
            {
                System.out.println("OK");
            }
        }
    }
}

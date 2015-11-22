/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import com.firebase.client.Firebase;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxResult;
import se.mbaeumer.fxmessagebox.MessageBoxType;

/**
 * FXML Controller class
 *
 * @author Felix
 */
public class FXMLAgregarEmpleadoController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField CNombre;
    
    @FXML
    private TextField CApellido;
   
    @FXML
    private TextField CSucursal;
   
    @FXML
    private TextField CUsername;
   
    @FXML
    private PasswordField CClave;
    
    @FXML
    private PasswordField CClave2;
    
    chilexplox.classes.Empresa emp = chilexplox.classes.Empresa.getInstance();
    List<chilexplox.classes.Empleado> empleados= new ArrayList();
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
        String sucursal= CSucursal.getText();
        String username= CUsername.getText();
        String clave= CClave.getText();
        String clave2= CClave2.getText();
        if (nombre.equals("") || apellido.equals("") || sucursal.equals("") || username.equals("") || clave.equals("") || clave2.equals("") )
        {
                MessageBox mb = new MessageBox("Por favor ingrese todos los campos", MessageBoxType.OK_ONLY);
                mb.showAndWait();
                if (mb.getMessageBoxResult() == MessageBoxResult.OK)
                {
                    System.out.println("OK");
                }
                return; // Evito registrar un usuario con campos en blanco (Fallaría el JSON format!)
        }
        for(chilexplox.classes.Empleado c:empleados)
        {
            if(c.getUsername().equals(CUsername.getText()))
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
            chilexplox.classes.Empleado c1 = new chilexplox.classes.Empleado(nombre, apellido,username,clave,sucursal);
            postRef = emp.fbRef().child("empleados");
            postRef.child(c1.getUsername()).setValue(c1);
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

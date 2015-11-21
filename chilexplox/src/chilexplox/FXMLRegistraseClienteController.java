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
    private TextField CUsername;
   
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
        Firebase postRef;
        Firebase newPostRef;
        postRef = emp.fbRef().child("clientes");
        postRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                  //System.out.println(snapshot);
                  Cliente post = snapshot.getValue(chilexplox.classes.Cliente.class);
                  clientes.add(post);
       }
            @Override
            public void onChildChanged(DataSnapshot ds, String string) {throw new UnsupportedOperationException("Not supported yet.");}
            @Override
            public void onChildRemoved(DataSnapshot ds) {throw new UnsupportedOperationException("Not supported yet.");}
            @Override
            public void onChildMoved(DataSnapshot ds, String string) {throw new UnsupportedOperationException("Not supported yet.");}
            @Override
            public void onCancelled(FirebaseError fe) {throw new UnsupportedOperationException("Not supported yet."); }
        });
    } 
    public void btnRegistrarmeAction(MouseEvent event) throws IOException 
    {
         for(Cliente c:clientes)
         {
                  if(c.getUsuario().equals(CUsername.getText()))
                  {
                  MessageBox mb = new MessageBox("El nombre de usuario ya esta ocupado.", MessageBoxType.OK_ONLY);
                    mb.showAndWait();
                    if (mb.getMessageBoxResult() == MessageBoxResult.OK)
                    {
                        System.out.println("OK");
                    }
            
                
                  }
                  else
                  {
                  if(CClave.getText().equals(CClave2.getText()))
                  {
                      //if(CClave.getText().equals(""))
                      //{//mensaje de que tiene que tener clave
                      //}
                          //else
                          {//se registra
                              
                              Firebase postRef;
                              Firebase newPostRef;
                              String nombre= CNombre.getText();
                              String apellido= CApellido.getText();
                              String rut= CRut.getText();
                              String direccion= CDireccion.getText();
                              String username= CUsername.getText();
                              String clave= CClave.getText();
                              Cliente c1 = new Cliente(nombre,apellido,direccion,username,clave);
                              postRef = emp.fbRef().child("clientes");
                              newPostRef = postRef.child(c1.getUsuario()); newPostRef.setValue(c1);
                              ((Node)(event.getSource())).getScene().getWindow().hide();
                          }
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
                  //System.out.println("Mensaje:" + post.toString());
            
         }
    }
    
    
}

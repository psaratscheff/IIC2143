/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Camion;
import chilexplox.classes.Cliente;
import chilexplox.classes.Boss;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import chilexplox.classes.Empresa;
import chilexplox.classes.Empleado;
import chilexplox.classes.Sucursal;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Thomas Pryce Jones
 */
public class FXMLLoginController implements Initializable {
    
    Empresa emp = Empresa.getInstance();
    @FXML
    private Button login;
    @FXML
    private PasswordField password;
    @FXML
    private TextField username;
    @FXML
    private Label ErrorLabel;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        Firebase postRef;
        Firebase newPostRef;
        postRef = emp.fbRef().child("empleados");
        postRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                  //System.out.println(snapshot);
                  Empleado post = snapshot.getValue(Empleado.class);
                  System.out.println("Mensaje:" + post.toString());
                  emp.AddEmpleado(post);
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String previousChildKey)
            {
                Empleado new_usr = ds.getValue(Empleado.class);
                Empleado old_usr = null;
                for (Empleado usr: emp.getempleados())
                {
                    if (usr.getUsername().equals(new_usr.getUsername()))
                    {
                        old_usr = usr;
                    }
                }
                if (old_usr == null) { throw new UnsupportedOperationException("¡¡Empleado modificado no existe!!"); }
                emp.getempleados().remove(old_usr);
                emp.getempleados().add(new_usr);
            }
            @Override
            public void onChildRemoved(DataSnapshot ds)
            {
                Empleado old_usr = ds.getValue(Empleado.class);
                emp.getjefes().remove(old_usr);
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string)
            {
                // No importa, no se hace nada
            }
            @Override
            public void onCancelled(FirebaseError fe)
            {
                System.out.println("ERROR FB-102:" + fe.getMessage());
            }
        });
        postRef = emp.fbRef().child("jefes");
        postRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                  //System.out.println(snapshot);
                  Boss post = snapshot.getValue(Boss.class);
                  //System.out.println("Mensaje:" + post.toString());
                  emp.AddJefe(post);
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String previousChildKey)
            {
                Boss new_usr = ds.getValue(Boss.class);
                Boss old_usr = null;
                for (Boss usr: emp.getjefes())
                {
                    if (usr.getUsername().equals(new_usr.getUsername()))
                    {
                        old_usr = usr;
                    }
                }
                if (old_usr == null) { throw new UnsupportedOperationException("¡¡Jefe modificado no existe!!"); }
                emp.getjefes().remove(old_usr);
                emp.getjefes().add(new_usr);
            }
            @Override
            public void onChildRemoved(DataSnapshot ds)
            {
                Boss old_usr = ds.getValue(Boss.class);
                emp.getjefes().remove(old_usr);
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string)
            {
                // No importa, no se hace nada
            }
            @Override
            public void onCancelled(FirebaseError fe)
            {
                System.out.println("ERROR FB-102:" + fe.getMessage());
            }
        });
        
        postRef = emp.fbRef().child("clientes");
        postRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                  Cliente post = snapshot.getValue(Cliente.class);
                  emp.AddCliente(post);
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String previousChildKey)
            {
                Cliente new_usr = ds.getValue(Cliente.class);
                Cliente old_usr = null;
                for (Cliente usr: emp.getclientes())
                {
                    if (usr.getUsuario().equals(new_usr.getUsuario()))
                    {
                        old_usr = usr;
                    }
                }
                if (old_usr == null) { throw new UnsupportedOperationException("¡¡Cliente modificado no existe!!"); }
                emp.getclientes().remove(old_usr);
                emp.getclientes().add(new_usr);
            }
            @Override
            public void onChildRemoved(DataSnapshot ds)
            {
                Cliente old_usr = ds.getValue(Cliente.class);
                emp.getclientes().remove(old_usr);
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string)
            {
                // No importa, no se hace nada
            }
            @Override
            public void onCancelled(FirebaseError fe)
            {
                System.out.println("ERROR FB-102:" + fe.getMessage());
            }
        });
    }    
//tengo que hacer array de usuario en el initialice desde sucursla controler 
    @FXML
    private void btnLoginAction(MouseEvent event) throws IOException 
    {
        String pass = password.getText();
        String user = username.getText();
        for (Empleado e: emp.getempleados()) 
        {
            if (pass.equals(e.getPassword()) & user.equals(e.getUsername())) 
            {
                
                emp.setempleadoactual(e) ; //Seteo el usuario que se logró loguear
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLSucursal.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));  
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide(); // hide es IDENTICO a close()
            } 
            
        }
        for (Cliente c: emp.getclientes())
        {
         if (pass.equals(c.getPassword()) & user.equals(c.getUsuario())) 
            {
                
                emp.setclienteactual(c) ; //Seteo el usuario que se logró loguear
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLCliente.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));  
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide(); // hide es IDENTICO a close()
            } 
        }
        
        for (Boss b: emp.getjefes())
        {
            
         if (pass.equals(b.getPassword()) & user.equals(b.getUsername())) 
            {
                
                emp.setjefeactual(b) ; //Seteo el usuario que se logró loguear
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLBoss.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));  
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide(); // hide es IDENTICO a close()
            } 
        }
        ErrorLabel.setText("Usuario o contraseña incorrectos");
    }
    @FXML
    private void btnRegistrarseAction(MouseEvent event) throws IOException 
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLRegistraseCliente.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));  
        stage.show();
       
    }
    
}

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

    List<Empleado> empleados= new ArrayList();
    List<Cliente> clientes= new ArrayList();
    List<Boss> jefes= new ArrayList();
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
                  empleados.add(post);
                  emp.AddEmpleado(post);
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
        postRef = emp.fbRef().child("jefes");
        postRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                  //System.out.println(snapshot);
                  Boss post = snapshot.getValue(Boss.class);
                  //System.out.println("Mensaje:" + post.toString());
                  jefes.add(post);
                  emp.AddJefe(post);
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
        
        postRef = emp.fbRef().child("clientes");
        postRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                  //System.out.println(snapshot);
                  Cliente post = snapshot.getValue(Cliente.class);
                  //System.out.println("Mensaje:" + post.toString());
                  clientes.add(post);
                  emp.AddCliente(post);
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
//tengo que hacer array de usuario en el initialice desde sucursla controler 
    @FXML
    private void btnLoginAction(MouseEvent event) throws IOException 
    {
        String pass = password.getText();
        String user = username.getText();
        for (Empleado e: empleados) 
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
        for (Cliente c: clientes)
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
        
        for (Boss b: jefes)
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

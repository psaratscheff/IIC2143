/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Empresa;
import chilexplox.classes.Mensaje;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author Thomas Pryce Jones
 */
public class FXMLErroresController implements Initializable {
    @FXML
    private ListView<String> ListaErrores;

    ArrayList<Mensaje> Mensajes;
    
    Empresa emp = Empresa.getInstance();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoadMensajes();
    }    
    
    private void LoadMensajes()
    {
        Firebase sucursalesRef = emp.fbRef().child("errores");
        sucursalesRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot ds, String previousChildKey) {
                Mensaje error = ds.getValue(Mensaje.class);
                ListaErrores.getItems().add(error.getContenido());
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String previousChildKey) {
               
            }
            @Override
            public void onChildRemoved(DataSnapshot ds) {
               
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                
            }
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println("ERROR FB-101:" + fe.getMessage());
            }
        });/**/
    }
}

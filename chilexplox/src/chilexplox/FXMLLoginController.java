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
import java.util.concurrent.TimeUnit;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxType;

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
        ErrorLabel.setText("");
    }
    
    @FXML
    private void btnLoginAction(ActionEvent event) throws IOException, InterruptedException 
    {
        int counter = 0;
        while (emp.getempleados().isEmpty() && emp.getclientes().isEmpty() && emp.getjefes().isEmpty())
        {
            if (counter >= 10)
            {
                MessageBox mb = new MessageBox("Error al conectar con el servidor.\n Por favor verifique su conexión a internet\n y que hayan usuarios registrados.", MessageBoxType.OK_ONLY);
                mb.showAndWait();
                ErrorLabel.setText("Error al conectar con el servidor, pruebe nuevamente.");
                return;
            }
            Thread.sleep(1500); // Esperamos a que cargue la información
            counter++;
        }
        ErrorLabel.setText("");
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
            else if (user.equals(e.getUsername()))
            {
                ErrorLabel.setText("La contraseña ingresada es incorrecta.");
                return;
            }
        }
        for (Cliente c: emp.getclientes())
        {
            if (pass.equals(c.getPassword()) & user.equals(c.getRut())) 
            {
                emp.setclienteactual(c) ; //Seteo el usuario que se logró loguear
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLCliente.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                FXMLClienteController controller = fxmlLoader.<FXMLClienteController>getController();
                controller.setCliente(c);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));  
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide(); // hide es IDENTICO a close()
            } 
            else if (user.equals(c.getRut()))
            {
                ErrorLabel.setText("La contraseña ingresada es incorrecta.");
                return;
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
            else if (user.equals(b.getUsername()))
            {
                ErrorLabel.setText("La contraseña ingresada es incorrecta.");
                return;
            }
        }
        if (ErrorLabel.getText().equals(""))
        {
            ErrorLabel.setText("El Usuario/Rut ingresado no existe");
        }
    }
    @FXML
    private void btnRegistrarseAction(ActionEvent event) throws IOException 
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLRegistraseCliente.fxml"));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));  
        stage.show();
       
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Camion;
import chilexplox.classes.Cliente;
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
    }    

    @FXML
    private void btnLoginAction(MouseEvent event) throws IOException 
    {
        String pass = password.getText();
        String user = username.getText();
        for (Empleado e: emp.getempleados()) 
        {
            if (pass.equals(e.getpassword()) & user.equals(e.getusername())) 
            {
                
                emp.setempleadoactual(e) ; //Seteo el usuario que se logró loguear
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLfbSucursal.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));  
                stage.show();
                ((Node)(event.getSource())).getScene().getWindow().hide(); // hide es IDENTICO a close()
            } 
            
        }
        for (Cliente c: emp.getclientes())
        {
         if (pass.equals(c.getpasswordcliente()) & user.equals(c.getusercliente())) 
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
        ErrorLabel.setText("Usuario o contraseña incorrectos");
    }
    
}

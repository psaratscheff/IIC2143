/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import chilexplox.Empresa;

/**
 * FXML Controller class
 *
 * @author Thomas Pryce Jones
 */
public class FXMLLoginController implements Initializable {
    
    Empresa emp = new Empresa();
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
    private void btnLoginAction(MouseEvent event) 
    {
        String pass = password.getText();
        String user = username.getText();
        for (Empleado e: emp.empleados) 
        {
            if (pass.equals("test") & user.equals("test")) 
            {
                ErrorLabel.setText("OK!");
            }
            else
            {
                ErrorLabel.setText("Invalid username or password!");
            }          
        }
        
        
    }
    
}

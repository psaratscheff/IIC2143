/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Camion;
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
        //Creacion de empresa inicial
        List<Integer> h = new ArrayList(); h.add(8); h.add(13); h.add(14); h.add(17);
        Sucursal s = new Sucursal("Valdivia", 1000);
        emp.sucursales.add(s);
        emp.sucursales.add(new Sucursal("La Serena",50));
        emp.sucursales.add(new Sucursal("Santiago",150));
        emp.sucursales.add(new Sucursal("Arica",250));
        Empleado e = new Empleado("Thomas", "Pryce Jones", "1", "1", h, s);
        emp.AddEmpleado(e);
        Camion c1 = new Camion("Charlie", 1000, true);
        Camion c2 = new Camion("CharlieII", 2000, true);
        Camion c3 = new Camion("Arnold", 500, true);
        emp.camiones.add(c1);
        emp.camiones.add(c2);
        emp.camiones.add(c3);
    }    

    @FXML
    private void btnLoginAction(MouseEvent event) throws IOException 
    {
        String pass = password.getText();
        String user = username.getText();
        for (Empleado e: emp.empleados) 
        {
            if (pass.equals(e.password) & user.equals(e.username)) 
            {
                emp.empleadoActual = e; //Seteo el usuario que se logró loguear
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLSucursal.fxml"));
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

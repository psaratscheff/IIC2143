/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Cliente;
import chilexplox.classes.Empresa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxType;

/**
 * FXML Controller class
 *
 * @author psara
 */
public class FXMLCambiarContraseñaController implements Initializable {
    
    @FXML
    private Button btnCambiarContraseña;
    @FXML
    private TextField pass1;
    @FXML
    private TextField pass2;
    private Cliente cliente;
    Empresa emp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emp = Empresa.getInstance();
    }    
    @FXML
    private void CambiarContraseñaAction()
    {
        System.out.println("ASDJ HASDLhIKFBDF");
        if (pass1.getText().equals(pass2.getText()))
        {
            cliente.setPassword(pass1.getText());
            // Actualizar cliente
            emp.fbRef().child("clientes").child(cliente.getRut()).setValue(cliente);
            // Confirmar cambio de clave
            MessageBox mb = new MessageBox("Contraseña modificada exitosamente", MessageBoxType.OK_ONLY);
            mb.showAndWait();
            //Cerrar ventana
            Stage stage = (Stage) pass1.getScene().getWindow();
            stage.close();
        }
        else
        {
            MessageBox mb = new MessageBox("Las contraseña no coinciden", MessageBoxType.OK_ONLY);
            mb.showAndWait();
        }
    }
    public void setCliente(Cliente c)
    {
        this.cliente = c;
    }
}

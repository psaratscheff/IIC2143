/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Empleado;
import chilexplox.classes.Empresa;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxResult;
import se.mbaeumer.fxmessagebox.MessageBoxType;

/**
 * FXML Controller class
 *
 * @author psara
 */
public class FXMLEliminarEmpleadoController implements Initializable {
    
    /**
     * Initializes the controller class.
     */
    @FXML
    private Label LabelAdvertencia;
    
    @FXML
    private Label LabelEmpleado;
    
    @FXML
    private ChoiceBox<Empleado> ChoiceBoxEmpleado;
    
    @FXML
    private Button btnDelete;
    
    Empresa emp;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emp = Empresa.getInstance();
        ChoiceBoxEmpleado.getItems().addAll(emp.getempleados());
    }
    
    @FXML
    private void btnEliminarEmpleadoAction()
    {
        Empleado e = ChoiceBoxEmpleado.getSelectionModel().getSelectedItem();
        if (e == null)
        {
            MessageBox mb = new MessageBox("Debe seleccionar un empleado a eliminar", MessageBoxType.OK_ONLY);
            mb.showAndWait();
            return;
        }
        MessageBox mb = new MessageBox("Seguro que desea eliminar al empleado "+e.getNombre()+" "+e.getApellido(), MessageBoxType.OK_CANCEL);
        mb.showAndWait();
        if (mb.getMessageBoxResult() == MessageBoxResult.OK)
        {
            emp.fbRef().child("empleados").child(e.getUsername()).removeValue();
            MessageBox mb2 = new MessageBox("Empleado eliminado satisfactoriamente", MessageBoxType.OK_ONLY);
            mb2.showAndWait();
            ChoiceBoxEmpleado.getItems().clear();
            ChoiceBoxEmpleado.getItems().addAll(emp.getempleados());
        }
    }
}

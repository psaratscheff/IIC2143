/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Camion;
import chilexplox.classes.Empresa;
import chilexplox.classes.Ingreso;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author psara
 */
public class FXMLVerInformeController implements Initializable {
    
    @FXML
    private ComboBox<Integer> ComboBoxPeriodos;
    @FXML
    private TextField NetValue;

    private Empresa emp;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fillComboBox();
    }    

    void setEmpresa(Empresa emp) {
        this.emp = emp;
    }
    
    void fillComboBox()
    {
        // OPTIMIZAR
        //Cambiar por metodo con mes y año!!
        ComboBoxPeriodos.getItems().add(10); //Añado Noviembre
        ComboBoxPeriodos.getItems().add(11); //Añado Diciembre
    }
    
    @FXML
    void CargarPeriodo()
    {
        if (ComboBoxPeriodos.getValue() != null)
        {
            int m = ComboBoxPeriodos.getValue();
            int neto = 0;
            Calendar cal = Calendar.getInstance();
            for (Ingreso i: emp.ingresos())
            {
                cal.setTime(i.Fecha());
                int month = cal.get(Calendar.MONTH);
                if (month == m)
                {
                    neto += i.Valor();
                }
            }
            NetValue.setText("$"+neto);
        }
    }
}

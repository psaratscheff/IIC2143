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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author psara
 */
public class FXMLVerInformeController implements Initializable {
    
    @FXML
    private ComboBox<String> CBPeriodos;
    @FXML
    private ComboBox<String> CBOrdenarPor;
    @FXML
    private ComboBox<String> CBMostrar;
    @FXML
    private TextField NetValue;
     @FXML
    private Label labeltextfield;

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
        {
        CBPeriodos.getItems().add("00");
        CBPeriodos.getItems().add("01");
        CBPeriodos.getItems().add("02");
        CBPeriodos.getItems().add("03");
        CBPeriodos.getItems().add("04");
        CBPeriodos.getItems().add("05");
        CBPeriodos.getItems().add("06");
        CBPeriodos.getItems().add("07");
        CBPeriodos.getItems().add("08");
        CBPeriodos.getItems().add("09");
        CBPeriodos.getItems().add("10");
        CBPeriodos.getItems().add("11");
        
        
       


//aniado los emses del anio
    }
       
       
    }
    
    @FXML
    void CargarPeriodo()
    {
        if (CBPeriodos.getValue() != null)
        {
            int valor=0;
            int contador=0;
            String m = CBPeriodos.getValue();
            int neto = 0;
            
            Calendar cal = Calendar.getInstance();
            
            for (Ingreso i: emp.ingresos())
            {
                cal.setTime(i.getFecha());
                String month = Integer.toString(cal.get(Calendar.MONTH));
                
                if (m.equals(month))
                {
                    
                        contador+=1;
                        neto += i.getValor();
                    //System.out.print("llegue dentro del if");
                }
            }
             if(CBMostrar.getValue().equals("Promedio"))
                    {
                        if(contador!=0)
                            
                        {
                       valor = neto/contador;
                        }
                    }
            else if(CBMostrar.getValue().equals("Total"))
                   {
                        valor= neto;
                   }
            NetValue.setText("$"+valor);
        }
    }
    @FXML
    void Mostrar()
    {
     if(CBMostrar.getValue().equals("Promedio"))
                    {
                       labeltextfield.setText("Ingreso promedio periodo:");
                    }
            else if(CBMostrar.getValue().equals("Ingreso neto periodo:"))
                   {
                        
                   }
     
     if (CBPeriodos.getValue() != null)
        {
            int valor=0;
            int contador=0;
            String m = CBPeriodos.getValue();
            int neto = 0;
            
            Calendar cal = Calendar.getInstance();
            
            for (Ingreso i: emp.ingresos())
            {
                cal.setTime(i.getFecha());
                String month = Integer.toString(cal.get(Calendar.MONTH));
                
                if (m.equals(month))
                {
                    
                        contador+=1;
                        neto += i.getValor();
                    //System.out.print("llegue dentro del if");
                }
            }
             if(CBMostrar.getValue().equals("Promedio"))
                    {
                        if(contador!=0)
                            
                        {
                       valor = neto/contador;
                        }
                    }
            else if(CBMostrar.getValue().equals("Total"))
                   {
                        valor= neto;
                   }
            NetValue.setText("$"+valor);
        }
    }
    @FXML
    void OrdenarPor()
    {
    
       
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Camion;
import chilexplox.classes.Empresa;
import chilexplox.classes.Ingreso;
import chilexplox.classes.Sucursal;
import chilexplox.classes.Empleado;
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
import javafx.scene.control.ListView;
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
      @FXML
    private ListView mostrador;

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
    {String m = CBPeriodos.getValue();
            int neto = 0;
            
            Calendar cal = Calendar.getInstance();
        if(CBOrdenarPor.getValue().equals("Sucursal"))
        {
        List valoresxsuc=new ArrayList();
    for(Sucursal s:emp.getsucursales())
    {
        int valor=0;
        int contador=0;
        int valortot=0;
        List aux=new ArrayList();
        aux.add(s.getDireccion());
        valoresxsuc.add(s.getDireccion());
        for(Ingreso i:emp.ingresos())
        { 
            cal.setTime(i.getFecha());
                String month = Integer.toString(cal.get(Calendar.MONTH));
                
                if (m.equals(month))
               {
        if(s.getDireccion().equals(i.getSucursal()))
        {
            contador+=1;
            System.out.print("puedo comparar sucursales \n");
            valortot+=i.getValor();
        }}
        }
        if(CBMostrar.getValue().equals("Promedio"))
                    {
                         System.out.print("comparopromedio \n");
                        if(contador!=0)
                            
                        {
                       valor = valortot/contador;
                        }
                    }
            else if(CBMostrar.getValue().equals("Total"))
                   {
                       System.out.print("comparototal \n");
                        valor= valortot;
                   }
        valoresxsuc.add(Integer.toString(valortot));
       mostrador.getItems().clear();
       
    }
    int cont=1;
    String meter="";
    for(Object s: valoresxsuc)
       {
           
                         System.out.print(s +" \n");
           if(cont%2==1)
           {
                
               meter= s+" :";
           }
           else if(cont%2==0)
           {
               meter+=s;
               mostrador.getItems().add(meter);
           }
           cont+=1;
       }
        }
        else if(CBOrdenarPor.getValue().equals("Empleado"))
        {
        List valoresxsuc=new ArrayList();
    for(Empleado e:emp.getempleados())
    {
        int valor=0;
        int contador=0;
        int valortot=0;
        List aux=new ArrayList();
        aux.add(e.getUsername());
        valoresxsuc.add(e.getUsername());
        for(Ingreso i:emp.ingresos())
        {
            
        if(e.getUsername().equals(i.getEmpleado()))
        {
            System.out.print("puedo comparar sucursales \n");
            valortot+=i.getValor();
            contador+=1;
        }
        }
        if(CBMostrar.getValue().equals("Promedio"))
                    {
                         System.out.print("comparopromedio \n");
                        if(contador!=0)
                            
                        {
                       valor = valortot/contador;
                        }
                    }
            else if(CBMostrar.getValue().equals("Total"))
                   {
                       System.out.print("comparototal \n");
                        valor= valortot;
                   }
        valoresxsuc.add(Integer.toString(valortot));
       mostrador.getItems().clear();
       
    }
    int cont=1;
    String meter="";
    for(Object s: valoresxsuc)
       {
           
                         System.out.print(s +" \n");
           if(cont%2==1)
           {
                
               meter= s+" :";
           }
           else if(cont%2==0)
           {
               meter+=s;
               mostrador.getItems().add(meter);
           }
           cont+=1;
       }
    }
    if (CBPeriodos.getValue() != null)
        {
            int valor=0;
            int contador=0;
           
            
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
}

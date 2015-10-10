/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author peter
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //----- CREACIONES-----
        //-----Empresa
        Empresa emp = new Empresa();
        //-----Sucursales
        Sucursal s1 = new Sucursal("Apoquindo 4333", 1000);
        Sucursal s2 = new Sucursal("Apoquindo 120", 800);
        emp.sucursales.add(s1);
        emp.sucursales.add(s2);
        //-----Empleados
        List<Integer> h1 = new ArrayList(); h1.add(8); h1.add(13); h1.add(14); h1.add(17);
        Empleado e1 = new Empleado("Felix", "Schiegg", "fshiegg", "miclavesecreta", h1);
        List<Integer> h2 = new ArrayList(); h2.add(10); h2.add(16);
        Empleado e2 = new Empleado("Pedro", "Saratscheff", "psaratscheff", "mIcl4vESuP3rS3creTa", h2);
        //-----Camiones
        Camion c1 = new Camion(20, true);
        Camion c2 = new Camion(20, true);
        //-----Cliente
        
        
        
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import chilexplox.classes.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pedro
 */
public class Main extends Application {
    Empresa emp = Empresa.getInstance();
    
    @Override
    public void start(Stage stage) throws Exception {
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
        s.camionesEstacionados.add(c1);
        s.camionesEstacionados.add(c2);
        s.camionesEstacionados.add(c3);
        
        //Creacion grafica del login
        Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    
}

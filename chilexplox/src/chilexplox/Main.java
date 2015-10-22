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
    
    
    @Override
    public void start(Stage stage) throws Exception {
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
        
        //Creacion de empresa inicial
        Empresa E = new Empresa();
        List<Integer> h = new ArrayList(); h.add(8); h.add(13); h.add(14); h.add(17);
        Sucursal s = new Sucursal("Apoquindo 4333", 1000);
        Empleado e = new Empleado("Minombre", "Miapellido", "1", "1", h, s);
        E.AddEmpleado(e);
        //List<Empleado> emplds = E.empleados;
        // Empresa.getInstance().empleados.add(e); //*/
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import chilexplox.classes.Empresa;
import chilexplox.classes.Encomienda;
import chilexplox.classes.Pedido;
import chilexplox.classes.Sucursal;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxResult;
import se.mbaeumer.fxmessagebox.MessageBoxType;

/**
 * FXML Controller class
 *
 * @author Thomas Pryce Jones
 */
public class FXMLIngresoPedidoController implements Initializable {
    @FXML
    private TextField CNombre;
    @FXML
    private TextField CApellido;
    @FXML
    private TextField CDireccion;
    @FXML
    private TextField CRut;
    @FXML
    private Button Pagar;
    @FXML
    private Button AgregarEncomienda;
    @FXML
    private Button EditarEncomienda;
    @FXML
    private TextField EAncho;
    @FXML
    private ChoiceBox<String> EPrioridad;
    @FXML
    private ChoiceBox<String> ETipo;
    @FXML
    private TextField EPeso;
    @FXML
    private TextField ELargo;
    @FXML
    private ChoiceBox<String> EDestino;
    @FXML
    private Label EOrigen;
    @FXML
    private Label TotalPedido;
    @FXML
    private Label EditarID;
    @FXML
    private ListView<String> ListEncomiendas;

    Empresa emp;
    Pedido pedido;
    Boolean editando;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emp = Empresa.getInstance();
        pedido = new Pedido(emp.AsignarIDPedido());
        EPrioridad.getItems().add("Urgente");
        EPrioridad.getItems().add("Normal");
        EPrioridad.getItems().add("Express");
        for (Sucursal s: emp.sucursales) 
        {
            EDestino.getItems().add(s.direccion);
        }
        EOrigen.setText(emp.sucursalActual.direccion);
        editando=false;
        
    }    

    @FXML
    private void btnPagar(MouseEvent event) throws IOException 
    {
        if (CNombre.getText() != null & CApellido.getText() != null & CDireccion.getText()!= null) 
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLVentanaPago.fxml"));
            Parent root = (Parent) fxmlLoader.load();        
            FXMLVentanaPagoController controller = fxmlLoader.<FXMLVentanaPagoController>getController();
            controller.setPedido(pedido);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));  
            stage.show();
        }
    }

    @FXML
    private void btnAgregarEncomienda(MouseEvent event) 
    {
        try
        {
            if(editando == false)
            {
                int tamaño = Integer.parseInt(EPeso.getText())*Integer.parseInt(ELargo.getText())*Integer.parseInt(EAncho.getText());
                String prioridad = EPrioridad.getValue();
                String destino = EDestino.getValue();
                for(Sucursal s: emp.sucursales)
                    {
                        if (s.direccion == destino) 
                        {
                            Encomienda en = new Encomienda("Ingresado", prioridad, tamaño, emp.AsignarIDEnco(), s, emp.sucursalActual,"Normal");
                            pedido.encomiendas.add(en);
                            ListEncomiendas.getItems().add("ID: "+"#"+en.id+"#"+" Destino: "+en.destino.direccion);
                            int asd = pedido.CalcularValor();
                            String precio= Integer.toString(asd);
                            TotalPedido.setText("Total: $"+precio);
                        }
                    }
            }
            
            if (editando == true) 
            {
                int id = Integer.parseInt(EditarID.getText().split("#")[1]);
                for (Encomienda en: pedido.encomiendas) 
                {
                    if (en.id == id) 
                    {
                        pedido.encomiendas.remove(en);
                        int tamaño = Integer.parseInt(EPeso.getText())*Integer.parseInt(ELargo.getText())*Integer.parseInt(EAncho.getText());
                        String prioridad = EPrioridad.getValue();
                        String destino = EDestino.getValue();
                        String tipo= ETipo.getValue();
                        for(Sucursal s: emp.sucursales)
                            {
                                if (s.direccion == destino) 
                                {
                                    Encomienda wn = new Encomienda("Ingresado", prioridad, tamaño, id, s, emp.sucursalActual,tipo);
                                    pedido.encomiendas.add(wn);
                                    ListEncomiendas.getItems().add("ID: "+"#"+wn.id+"#"+" Destino: "+wn.destino.direccion);
                                    int asd = pedido.CalcularValor();
                                    String precio= Integer.toString(asd);
                                    TotalPedido.setText("Total: $"+precio);
                                }
                            }
                    }
                }
                EditarID.setText(null);
                editando = false;
            }
            
        }
        catch(Exception e)
        {
            System.out.print("Error en el sistema");
        }     
        
    }

    
    @FXML
    private void btnEditar(MouseEvent event) 
    {
        MessageBox mb = new MessageBox("OK or cancel?", MessageBoxType.OK_CANCEL);
        mb.showAndWait();
        if (mb.getMessageBoxResult() == MessageBoxResult.OK)
        {
                System.out.println("OK");
                try
                {    
                    editando = true;
                    String encomiendaID = ListEncomiendas.getSelectionModel().getSelectedItem().split("#")[1]; //
                    EditarID.setText("ID: "+"#"+encomiendaID+"#");
                    int selectedIdx = ListEncomiendas.getSelectionModel().getSelectedIndex();
                    ListEncomiendas.getItems().remove(selectedIdx);

                }
                catch(Exception e)
                {
                    System.out.print("Error en el sistema");
                }
        }
        else
        {
                System.out.println("Cancel");
        }
    }
    
}

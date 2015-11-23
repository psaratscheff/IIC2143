/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import chilexplox.classes.Camion;
import chilexplox.classes.Empresa;
import chilexplox.classes.Encomienda;
import chilexplox.classes.Mensaje;
import chilexplox.classes.Pedido;
import chilexplox.classes.Sucursal;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ciruman.EllipsisListCell;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.util.Iterator;
import java.util.List;
import javafx.application.Platform;
import javafx.util.Callback;

//Listener
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import se.mbaeumer.fxmessagebox.MessageBox;
import se.mbaeumer.fxmessagebox.MessageBoxResult;
import se.mbaeumer.fxmessagebox.MessageBoxType;

/**
 * FXML Controller class
 *
 * @author Felix
 */
public class FXMLIngresoPedidoClienteController implements Initializable {

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
    private ChoiceBox<String> EOrigen;
    @FXML
    private TextField EDireccion;
    @FXML
    private Label TotalPedido;
    @FXML
    private Label EditarID;
    @FXML
    private ListView<String> ListEncomiendas;

    private Empresa emp;
    private Pedido pedido;
    private Boolean editando;
    private FXMLClienteController clienteController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        Platform.runLater(new Runnable() { // Evitar problemas con el "Not on FX Thread"
            @Override
            public void run() {
                LoadSucursales();
            }
        });
        emp = Empresa.getInstance();
        pedido = new Pedido(emp.AsignarIDPedido());
        EPrioridad.getItems().add("Urgente");
        EPrioridad.getItems().add("Normal");
        EPrioridad.getItems().add("Express");
        ETipo.getItems().add("Normal");
        ETipo.getItems().add("Refrigerado");
        ETipo.getItems().add("Radioactivo");
        editando=false;
    } 
     public void btnPagar(MouseEvent event) throws IOException 
    {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLVentanaPago.fxml"));
        Parent root = (Parent) fxmlLoader.load();        
        FXMLVentanaPagoController controller = fxmlLoader.<FXMLVentanaPagoController>getController();
        controller.setPedido(pedido);
        controller.setEmpresa(emp);
        controller.setParentWindow((Stage)Pagar.getScene().getWindow());
        controller.setClienteController(this.clienteController);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));  
        stage.show();
        
        emp.setsucursalactual(emp.getSucursalConDireccion(EOrigen.getValue()));
        
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
                String  origen = EOrigen.getValue();
                String tipo= ETipo.getValue();
                Sucursal sucursalorigen= emp.getSucursalConDireccion(origen);
                for(Sucursal s: emp.getsucursales())
                    {
                        if (s.getDireccion().equals(destino)) 
                        {
                            Encomienda en = new Encomienda("Ingresado", prioridad, tamaño, emp.AsignarIDEnco(), sucursalorigen.getDireccion(), s.getDireccion(), tipo, "SIN CLIENTE");
                            en.setancho(Integer.parseInt(EAncho.getText()));
                            en.setlargo(Integer.parseInt(ELargo.getText()));
                            en.setpeso(Integer.parseInt(EPeso.getText()));
                            en.setdirecciondestino(EDireccion.getText());
                            pedido.addencomienda(en);
                            ListEncomiendas.getItems().add("ID: "+"#"+en.getId()+"#"+" Destino: "+emp.getSucursalConDireccion(en.getSucursalDestino()).getDireccion());
                            int asd = pedido.CalcularValor();
                            String precio= Integer.toString(asd);
                            TotalPedido.setText("Total: $"+precio);
                        }
                    }
            }
            
            if (editando == true) 
            {
                String id = EditarID.getText().split("#")[1];
                for (Encomienda en: pedido.getEncomiendas()) 
                {
                    if (en.getId().equals(id)) 
                    {
                        pedido.removeencomienda(en);
                        int tamaño = Integer.parseInt(EPeso.getText())*Integer.parseInt(ELargo.getText())*Integer.parseInt(EAncho.getText());
                        String prioridad = EPrioridad.getValue();
                        String destino = EDestino.getValue();
                        String tipo= ETipo.getValue();
                        for(Sucursal s: emp.getsucursales())
                        {
                            if (s.getDireccion().equals(destino)) 
                            {
                                Encomienda wn = new Encomienda("Ingresado", prioridad, tamaño, id, emp.getsucursalactual().getDireccion(), s.getDireccion(), tipo, "SIN CLIENTE");
                                wn.setancho(Integer.parseInt(EAncho.getText()));
                                wn.setlargo(Integer.parseInt(ELargo.getText()));
                                wn.setpeso(Integer.parseInt(EPeso.getText()));
                                wn.setdirecciondestino(EDireccion.getText());
                                pedido.addencomienda(wn);
                                ListEncomiendas.getItems().add("ID: "+"#"+wn.getId()+"#"+" Destino: "+emp.getSucursalConDireccion(wn.getSucursalDestino()).getDireccion());
                                int asd = pedido.CalcularValor();
                                String precio= Integer.toString(asd);
                                TotalPedido.setText("Total: $"+precio);
                                EditarID.setText(null);
                                editando = false;
                            }
                        }
                    }
                }
                
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
        MessageBox mb = new MessageBox("¿Seguro que desea editar la encomienda?", MessageBoxType.OK_CANCEL);
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
                    Encomienda temp = pedido.getencomiendabyid(encomiendaID);
                    EPeso.setText(Integer.toString(temp.getPeso()));
                    EAncho.setText(Integer.toString(temp.getAncho()));
                    ELargo.setText(Integer.toString(temp.getLargo()));
                    EDireccion.setText(temp.getDireccionDestino());
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
    
    private void LoadSucursales()
    {
        Firebase sucursalesRef = emp.fbRef().child("sucursales");
        sucursalesRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot ds, String previousChildKey) {
                Sucursal s = ds.getValue(Sucursal.class);
                AddSucursal(s);
                emp.getsucursales().add(s);
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                
            }
            @Override
            public void onChildRemoved(DataSnapshot ds) {
                Sucursal s = ds.getValue(Sucursal.class);
                RemoveSucursal(s);
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                // Who cares... (No se requiere hacer nada)
            }
            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println("ERROR FB-101:" + fe.getMessage());
            }
        });/**/
    }
    private void AddSucursal(final Sucursal s)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                EDestino.getItems().add(s.getDireccion());
                EOrigen.getItems().add(s.getDireccion());
            }
        });
    }
    private void RemoveSucursal(final Sucursal s)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                EDestino.getItems().remove(s.getDireccion());
                EOrigen.getItems().remove(s.getDireccion());
            }
        });
    }

    void setClienteController(FXMLClienteController aThis) {
        this.clienteController = aThis;
    }
    
}

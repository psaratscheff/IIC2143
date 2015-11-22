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
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
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
    private TextField EDireccion;
    @FXML
    private Label EOrigen;
    @FXML
    private Label TotalPedido;
    @FXML
    private Label EditarID;
    @FXML
    private ListView<String> ListEncomiendas;

    private Empresa emp;
    private Pedido pedido;
    private Boolean editando;
    private FXMLSucursalController sucursalController;
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
        ETipo.getItems().add("Normal");
        ETipo.getItems().add("Refrigerado");
        ETipo.getItems().add("Radioactivo");
        
        LoadSucursales();
        
        EOrigen.setText(emp.getsucursalactual().getDireccion());
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
            controller.setEmpresa(emp);
            controller.setParentWindow((Stage)Pagar.getScene().getWindow());
            controller.setSucursalController(this.sucursalController);
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
                String tipo= ETipo.getValue();
                for(Sucursal s: emp.getsucursales())
                    {
                        if (s.getDireccion().equals(destino)) 
                        {
                            Encomienda en = new Encomienda("Ingresado", prioridad, tamaño, emp.AsignarIDEnco(), emp.getsucursalactual().getDireccion(), s.getDireccion(), tipo);
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
                                Encomienda wn = new Encomienda("Ingresado", prioridad, tamaño, id, emp.getsucursalactual().getDireccion(), s.getDireccion(), tipo);
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

    void setSucursalController(FXMLSucursalController aThis) {
        this.sucursalController = aThis;
    }
    
    private void LoadSucursales()
    {
        Firebase sucursalesRef = emp.fbRef().child("sucursales");
        sucursalesRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot ds, String previousChildKey) {
                Sucursal s = ds.getValue(Sucursal.class);
                EDestino.getItems().add(s.getDireccion());
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                //Hay que hacer algo por si cambia nombre?
            }
            @Override
            public void onChildRemoved(DataSnapshot ds) {
                Sucursal s = ds.getValue(Sucursal.class);
                System.out.println("Sucursal REMOVIDA:" + s.toString());
                EDestino.getItems().remove(s.getDireccion());
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
}

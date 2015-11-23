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
import chilexplox.classes.Empleado;
import chilexplox.classes.Cliente;
import chilexplox.classes.Boss;
import chilexplox.classes.Empresa;
import chilexplox.classes.Sucursal;
import chilexplox.classes.Camion;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import chilexplox.classes.Encomienda;
import chilexplox.classes.Ingreso;
import chilexplox.classes.Mensaje;
import chilexplox.classes.Pedido;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Pedro
 */
public class Main extends Application {
    Empresa emp = Empresa.getInstance();
    Firebase postRef;
    Firebase newPostRef;
    @Override
    public void start(Stage stage) throws Exception {
        // Mantener la lista de encomiendas actualizadas
        postRef = emp.fbRef().child("encomiendas");
        postRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot ds, String previousChildKey) {
                  //System.out.println(snapshot);
                  Encomienda enc = ds.getValue(Encomienda.class);
                  emp.getencomiendas().add(enc);
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String previousChildKey)
            {
                Encomienda new_enc = ds.getValue(Encomienda.class);
                Encomienda old_enc = null;
                for (Encomienda e: emp.getencomiendas())
                {
                    if (e.getId().equals(new_enc.getId()))
                    {
                        old_enc = e;
                    }
                }
                if (old_enc == null) { throw new UnsupportedOperationException("¡¡Encomienda modificada no existe!!"); }
                emp.getencomiendas().remove(old_enc);
                emp.getencomiendas().add(new_enc);
            }
            @Override
            public void onChildRemoved(DataSnapshot ds)
            {
                Encomienda new_enc = ds.getValue(Encomienda.class);
                Encomienda old_enc = null;
                for (Encomienda usr: emp.getencomiendas())
                {
                    if (usr.getId().equals(new_enc.getId()))
                    {
                        old_enc = usr;
                    }
                }
                if (old_enc == null) { throw new UnsupportedOperationException("¡¡Encomienda eliminada no existe!!"); }
                emp.getencomiendas().remove(old_enc);
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string)
            {
                // No importa, no se hace nada
            }
            @Override
            public void onCancelled(FirebaseError fe)
            {
                System.out.println("ERROR FB-102:" + fe.getMessage());
            }
        });
        //Mantener los usuarios sincronizados
        postRef = emp.fbRef().child("empleados");
        postRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                  //System.out.println(snapshot);
                  Empleado post = snapshot.getValue(Empleado.class);
                  emp.AddEmpleado(post);
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String previousChildKey)
            {
                Empleado new_usr = ds.getValue(Empleado.class);
                Empleado old_usr = null;
                for (Empleado usr: emp.getempleados())
                {
                    if (usr.getUsername().equals(new_usr.getUsername()))
                    {
                        old_usr = usr;
                    }
                }
                if (old_usr == null) { throw new UnsupportedOperationException("¡¡Empleado modificado no existe!!"); }
                emp.getempleados().remove(old_usr);
                emp.getempleados().add(new_usr);
            }
            @Override
            public void onChildRemoved(DataSnapshot ds)
            {
                Empleado new_usr = ds.getValue(Empleado.class);
                Empleado old_usr = null;
                for (Empleado usr: emp.getempleados())
                {
                    if (usr.getUsername().equals(new_usr.getUsername()))
                    {
                        old_usr = usr;
                    }
                }
                if (old_usr == null) { throw new UnsupportedOperationException("¡¡Empleado eliminado no existe!!"); }
                emp.getempleados().remove(old_usr);
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string)
            {
                // No importa, no se hace nada
            }
            @Override
            public void onCancelled(FirebaseError fe)
            {
                System.out.println("ERROR FB-102:" + fe.getMessage());
            }
        });
        postRef = emp.fbRef().child("jefes");
        postRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                  Boss post = snapshot.getValue(Boss.class);
                  emp.AddJefe(post);
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String previousChildKey)
            {
                Boss new_usr = ds.getValue(Boss.class);
                Boss old_usr = null;
                for (Boss usr: emp.getjefes())
                {
                    if (usr.getUsername().equals(new_usr.getUsername()))
                    {
                        old_usr = usr;
                    }
                }
                if (old_usr == null) { throw new UnsupportedOperationException("¡¡Jefe modificado no existe!!"); }
                emp.getjefes().remove(old_usr);
                emp.getjefes().add(new_usr);
            }
            @Override
            public void onChildRemoved(DataSnapshot ds)
            {
                Boss new_usr = ds.getValue(Boss.class);
                Boss old_usr = null;
                for (Boss usr: emp.getjefes())
                {
                    if (usr.getUsername().equals(new_usr.getUsername()))
                    {
                        old_usr = usr;
                    }
                }
                if (old_usr == null) { throw new UnsupportedOperationException("¡¡Jefe eliminado no existe!!"); }
                emp.getjefes().remove(old_usr);
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string)
            {
                // No importa, no se hace nada
            }
            @Override
            public void onCancelled(FirebaseError fe)
            {
                System.out.println("ERROR FB-102:" + fe.getMessage());
            }
        });
        
        postRef = emp.fbRef().child("clientes");
        postRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                  Cliente post = snapshot.getValue(Cliente.class);
                  emp.AddCliente(post);
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String previousChildKey)
            {
                Cliente new_usr = ds.getValue(Cliente.class);
                Cliente old_usr = null;
                for (Cliente usr: emp.getclientes())
                {
                    if (usr.getRut().equals(new_usr.getRut()))
                    {
                        old_usr = usr;
                    }
                }
                if (old_usr == null) { throw new UnsupportedOperationException("¡¡Cliente modificado no existe!!"); }
                emp.getclientes().remove(old_usr);
                emp.getclientes().add(new_usr);
            }
            @Override
            public void onChildRemoved(DataSnapshot ds)
            {
                Cliente old_usr = ds.getValue(Cliente.class);
                emp.getclientes().remove(old_usr);
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string)
            {
                // No importa, no se hace nada
            }
            @Override
            public void onCancelled(FirebaseError fe)
            {
                System.out.println("ERROR FB-102:" + fe.getMessage());
            }
        });
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

        /*Sucursal s1 = new Sucursal("Valdivia", 1000);
        Empleado e;
        e = new Empleado("Thomas", "Pryce Jones", "1", "1", s1.getDireccion());
        
        postRef = emp.fbRef().child("empleados");
        newPostRef = postRef.child(e.getUsername()); newPostRef.setValue(e);*/
        /*
        //Creacion de empresa inicial
        List<Integer> h = new ArrayList(); h.add(8); h.add(13); h.add(14); h.add(17);
        Sucursal s1 = new Sucursal("Valdivia", 1000);
        Sucursal s2 = new Sucursal("La Serena",50);
        Sucursal s3 = new Sucursal("Santiago",150);
        Sucursal s4 = new Sucursal("Arica",250);
        
        
        Empleado e = new Empleado("Thomas", "Pryce Jones", "1", "1", h, s1.getDireccion());
        //emp.AddEmpleado(e);
        
        Cliente c = new Cliente("Pedro","S","Lejos","2","2");
        //emp.AddCliente(c);
        
        Boss b= new Boss("Berni","Ljubetic","3","3");
        //emp.getjefes().add(b);
        
        Encomienda enc1 = new Encomienda("Normal","Urgente",1,s2.getDireccion(),s3.getDireccion(),"Normal");
        Encomienda enc2 = new Encomienda("Normal","Urgente",1,s2.getDireccion(),s4.getDireccion(),"Normal");
        s2.getEncomiendasAlmacenadas().add(enc1);
        s2.getEncomiendasAlmacenadas().add(enc2);
        
        Pedido p1 = new Pedido(null);
        p1.getEncomiendas().add(enc1);
        p1.getEncomiendas().add(enc2);
        
        Camion c1 = new Camion("Charlie", 10, true,"Normal");
        Camion c2 = new Camion("CharlieII", 20, true,"Normal");
        Camion c3 = new Camion("Arnold", 5, true,"Refrigerado");
        Camion c4 = new Camion("Aleph", 5, true,"Radioactivo");
        s2.getCamionesEstacionados().add(c1);
        s2.getCamionesEstacionados().add(c2);
        s2.getCamionesEstacionados().add(c3);
        s2.getCamionesEstacionados().add(c4);
        
        Mensaje m1 = new Mensaje("AAAAAA", true);
        s2.getMensajesRecibidos().add(m1);
        s1.getMensajesRecibidos().add(m1);
        s4.getMensajesRecibidos().add(m1);
        s3.getMensajesRecibidos().add(m1);
        // Cargar información a la base de datos
        Firebase postRef;
        Firebase newPostRef;
        
       
        postRef = emp.fbRef().child("encomiendas");
        newPostRef = postRef.push(); String id1 = newPostRef.getKey(); enc1.setId(id1); newPostRef.setValue(enc1);
        newPostRef = postRef.push(); String id2 = newPostRef.getKey(); enc2.setId(id2); newPostRef.setValue(enc2);
        
        postRef = emp.fbRef().child("sucursales");
        newPostRef = postRef.child(s1.getDireccion()); newPostRef.setValue(s1);
        newPostRef = postRef.child(s2.getDireccion()); newPostRef.setValue(s2);
        newPostRef = postRef.child(s3.getDireccion()); newPostRef.setValue(s3);
        newPostRef = postRef.child(s4.getDireccion()); newPostRef.setValue(s4);
        
        Ingreso i1 = new Ingreso(100, new Date(1448078729000L)); // Timestamp universal en milisegundos, L al final para definir que es un long
        postRef = emp.fbRef().child("ingresos");
        newPostRef = postRef.push(); newPostRef.setValue(i1);
        
        postRef = emp.fbRef().child("clientes");
        newPostRef = postRef.child(c.getUsuario()); newPostRef.setValue(c);
        
        postRef = emp.fbRef().child("jefes");
        newPostRef = postRef.child(b.getUsername()); newPostRef.setValue(b);
        
        postRef = emp.fbRef().child("empleados");
        newPostRef = postRef.child(e.getUsername()); newPostRef.setValue(e);
        
        postRef = emp.fbRef().child("pedidos");
        newPostRef = postRef.push(); p1.setId(newPostRef.getKey()); newPostRef.setValue(p1);
        
        // Para verificar escrituras válidas (Leíbles por código)
        postRef = emp.fbRef().child("empleados");
        postRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                  //System.out.println(snapshot);
                  Empleado post = snapshot.getValue(Empleado.class);
                  //System.out.println("Mensaje:" + post.toString());
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String string) {throw new UnsupportedOperationException("Not supported yet.");}
            @Override
            public void onChildRemoved(DataSnapshot ds) {throw new UnsupportedOperationException("Not supported yet.");}
            @Override
            public void onChildMoved(DataSnapshot ds, String string) {throw new UnsupportedOperationException("Not supported yet.");}
            @Override
            public void onCancelled(FirebaseError fe) {throw new UnsupportedOperationException("Not supported yet."); }
        });
        /**/
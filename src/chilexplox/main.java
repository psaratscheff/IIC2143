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

        //----- CREACIONES_INICIALES -----
        //-----Empresa
        int contadorid=0;
        int contadoridpedidos=0; // para despues cuando automatizemos la creacion de ids para encomiendas y pedidos
        Empresa emp = new Empresa();
        //-----Sucursales
        Sucursal s1 = new Sucursal("Apoquindo 4333", 1000);
        Sucursal s2 = new Sucursal("Apoquindo 120", 800);
        emp.sucursales.add(s1);
        emp.sucursales.add(s2);
        //-----Empleados
        List<Integer> h1 = new ArrayList(); h1.add(8); h1.add(13); h1.add(14); h1.add(17);
        Empleado e1 = new Empleado("Felix", "Schiegg", "fschiegg", "miclavesecreta", h1, s1);
        List<Integer> h2 = new ArrayList(); h2.add(10); h2.add(16);
        Empleado e2 = new Empleado("Pedro", "Saratscheff", "psaratscheff", "mIcl4vESuP3rS3creTa", h2, s2);
        //-----Camiones
        Camion c1 = new Camion(20, true);
        Camion c2 = new Camion(20, true);

        //----- LLEGA_CLIENTE y deja dos encomeniendas -----
        //-----Cliente
        Cliente cl = new Cliente("Thomas", "Pryce", "Manquehue Sur 520 Oficina 320");
            //Usuario entrega rut en sucursal 1 y queda registrado en el sistema
        cl.rut = "12.345.678-9";
        cl.sucursalRegistrada = s1;
        emp.clientes.add(cl);
        //-----Destinatario
        Cliente dest = new Cliente("Pepe","El Mago", "Chuchunco 123");
        dest.sucursalRegistrada = s2;
        //-----Encomiendas
        Encomienda enc1 = new Encomienda("En Sucursal de Origen","normal", 2, 1, s2, s1, dest);
        Encomienda enc2 = new Encomienda("En Sucursal de Origen","normal", 1, 2, s2, s1, dest);
        //-----Pedido
        Pedido ped = new Pedido(1);
        ped.encomiendas.add(enc1);
        ped.encomiendas.add(enc2);
        //-----Almacenamiento encomiendas a sucursal despues de verificar que tenga espacio disponible
        int espacio=s1.EspacioDisponible();
        if(espacio >=1)
        {
        s1.encomiendasAlmacenadas.add(enc1);
        }
        espacio=s1.EspacioDisponible();
         if(espacio >=1)
        {
        s1.encomiendasAlmacenadas.add(enc2);
        }
        
         //busca un camion vacio

         Camion camion= emp.EntregarCamion();
        //----- Llega camion por las encomiendas -----
        
        
        //----- Cargar camion
         //
        if(camion!=null)
        {
        s1.CargarCamion(camion, s1.encomiendasAlmacenadas);
        for(Encomienda e : s1.encomiendasAlmacenadas)
        {
            emp.CambiarEstadoEncomienda(e.id, "transito");
        }
        s1.encomiendasAlmacenadas.clear();
        s2.RecibirCamion(camion);
        }
        
        e1.EnviarMensaje(s2, "holi", true);
        
        
        
        
    }

}

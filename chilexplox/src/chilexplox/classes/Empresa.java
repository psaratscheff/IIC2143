/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox.classes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author peter
 */
public class Empresa {
    
    //SINGLETON!! Clase maestra
    private final static Empresa instance = new Empresa();

    /**
     * Retorna la única instancia (singleton) de Empresa
     * @return
     */
    public static Empresa getInstance() {
        return instance;
    }
    
    private List<Sucursal> sucursales;
    private List<Camion> camiones;
    private List<Cliente> clientes;
    private List<Encomienda> encomiendas;
    private List<Empleado> empleados;
    private Empleado empleadoActual;
    private Sucursal sucursalActual;
    private int IDEncomienda = 0;
    private int IDPedido = 0;
    private Pedido pedidotemp;
    private Encomienda EncomiendaTemporal;
    
    public Empresa()
    {
        //Inicializo los arrays para poder agregar valores
        this.sucursales = new ArrayList();
        this.camiones = new ArrayList();
        this.clientes = new ArrayList();
        this.encomiendas = new ArrayList();
        this.empleados = new ArrayList();
    }
    public List<Sucursal> getsucursales()
    {return sucursales;}
    public Sucursal getsucursalactual()
    {return sucursalActual;}
    public void setsucursalactual(Sucursal sucursal)
    {sucursalActual=sucursal;}
    public Pedido getpedidotemp()
    {return pedidotemp;}
    public void setpedidotemp(Pedido pedido)
    {pedidotemp=pedido;}
    public List<Empleado> getempleados()
    {return empleados;}
    public Empleado getempleadoactual()
    {return empleadoActual;}
    public void setempleadoactual(Empleado empleado)
    {empleadoActual=empleado;}
    public Encomienda getencomiendatemporal()
    {return EncomiendaTemporal;}
    public void setencomiendatemporal(Encomienda encomiendatemp)
    {EncomiendaTemporal=encomiendatemp;}
    public List<Encomienda> getencomiendas()
    {return encomiendas;}
    public List<Camion> getcamiones()
    {return camiones;}
    
    public List<String> getDireccionSucursales()
    {
        List<String> array = new ArrayList();
        for (Sucursal s: sucursales) 
        {
            array.add(s.getdireccion());
        }
        return array;
    }
    public void AddEmpleado(Empleado e)
    {
        this.empleados.add(e);
    }
    public Camion EntregarCamion()
    {
        for(Camion c: camiones)
        {
            if (c.getdisponibilidad()==true)
            {return c;}
        }
        return null;
    }
    
    public Cliente BuscarPersona(String rut)
    {
        for(Cliente c: clientes)
        {
        if (c.getrut()==rut)
        {
        return c;
        }
        }
        return null;
    }
    
    public void CambiarEstadoEncomienda(int id, String estado)
    {
        for(Encomienda e: encomiendas)
        {
            if(e.getid()==id)
            {
                e.setestado(estado);
            }
        }
    }
    
    public String VerEstadoEncomienda(int id)
    {
        for(Encomienda e: encomiendas)
        {
            if(e.getid()==id)
            {
                String estado=e.getestado();
                return estado;
            }
        }
        return null;   
    }
    
    public int AsignarIDEnco()
    {
        int temp = IDEncomienda;
        IDEncomienda += 1;
        return temp;
    }
    
    public int AsignarIDPedido()
    {
        int temp = IDPedido;
        IDPedido += 1;
        return temp;
    }
   /* public int buscarmenoridencomienda()
    {
        int largo=encomiendas.size();
        int menorid=0;
        boolean esta=true;
        for(int i=0; i<largo;i++)
        {
        for(Encomienda e: encomiendas)
        {
           
        }
        }
    }*/

}

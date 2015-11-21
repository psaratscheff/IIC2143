/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox.classes;

import com.firebase.client.Firebase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private DateFormat dateFormat;
    private List<Sucursal> sucursales;
    private List<Camion> camiones;
    private List<Cliente> clientes;
    private List<Encomienda> encomiendas;
    private List<Empleado> empleados;
    private Empleado empleadoActual;
    private Cliente clienteActual;
    private Sucursal sucursalActual;
    private Encomienda encomiendaTemporal;
    private int IDEncomienda = 0;
    private int IDPedido = 0;
    private Pedido pedidotemp;
    private List<Ingreso> ingresos;
    
    public Empresa()
    {
        //Inicializo los arrays para poder agregar valores
        this.sucursales = new ArrayList();
        this.camiones = new ArrayList();
        this.clientes = new ArrayList();
        this.encomiendas = new ArrayList();
        this.empleados = new ArrayList();
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.ingresos = new ArrayList();
    }
    
    /**
     * Para obtener el formato de fecha.
     * Uso:
     * dateFormat.format(date) 
     * Entrega un string con la fecha en formato "yyyy/MM/dd HH:mm:ss"
     * @return DateFormat
     */
    public List<Ingreso> ingresos()
    {
        return this.ingresos;
    }
    public DateFormat dateFormat()
    {return this.dateFormat;}
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
    public List<Cliente> getclientes()
    {return clientes;}
    public Empleado getempleadoactual()
    {return empleadoActual;}
    public Cliente getclienteactual()
    {return clienteActual;}
    public void setempleadoactual(Empleado empleado)
    {empleadoActual=empleado;}
    public void setclienteactual(Cliente cliente)
    {clienteActual=cliente;}
    public List<Encomienda> getencomiendas()
    {return encomiendas;}
    public List<Camion> getcamiones()
    {return camiones;}
    public Encomienda getencomiendatemporal()
    {return encomiendaTemporal;}
    public void setencomiendatemporal(Encomienda encotempo)
    {encomiendaTemporal=encotempo;}
    public Encomienda getencomiendabyid(String id)
    {
        Encomienda temp = null;
        for(Encomienda en: encomiendas)
        {
            if (en.getId() == id) 
            {
                temp =en;
            }
        } 
        return temp;
    }
    public Sucursal getsucursalcondir(String direccion)
    {
        for (Sucursal s: sucursales) 
        {
            if(s.getDireccion()==direccion)
            {return s;}
        }
        return sucursales.get(0);
    }
    
    public List<String> getDireccionSucursales()
    {
        List<String> array = new ArrayList();
        for (Sucursal s: sucursales) 
        {
            array.add(s.getDireccion());
        }
        return array;
    }
    public void AddEmpleado(Empleado e)
    {
        this.empleados.add(e);
    }
     public void AddCliente(Cliente c)
    {
        this.clientes.add(c);
    }
    public void addIngreso(Ingreso i)
    {
        this.ingresos.add(i);
    }
    public Camion EntregarCamion()
    {
        for(Camion c: camiones)
        {
            if (c.getDisponibilidad()==true)
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
    
    public void CambiarEstadoEncomienda(String id, String estado)
    {
        for(Encomienda e: encomiendas)
        {
            if(e.getId()==id)
            {
                e.setestado(estado);
            }
        }
    }
    
    public String VerEstadoEncomienda(String id)
    {
        for(Encomienda e: encomiendas)
        {
            if(e.getId()==id)
            {
                String estado=e.getEstado();
                return estado;
            }
        }
        return null;   
    }
    
    public String AsignarIDEnco()
    {
        int temp = IDEncomienda;
        IDEncomienda += 1;
        return Integer.toString(temp);
    }
    
    public String AsignarIDPedido()
    {
        int temp = IDPedido;
        IDPedido += 1;
        return Integer.toString(temp);
    }
    public Firebase fbRef()
    {
        Firebase myFirebaseRef = new Firebase("https://chilexplox.firebaseio.com/");
        return myFirebaseRef;
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

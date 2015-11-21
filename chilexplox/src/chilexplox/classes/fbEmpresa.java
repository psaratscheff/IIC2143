package chilexplox.classes;

import com.firebase.client.Firebase;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class fbEmpresa {
    
    //SINGLETON!! Clase maestra
    private final static fbEmpresa instance = new fbEmpresa();

    /**
     * Retorna la única instancia (singleton) de Empresa
     * @return
     */
    public static fbEmpresa getInstance() {
        return instance;
    }
    //private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    //private List<Sucursal> sucursales;
    //private List<Camion> camiones;
    //private List<Cliente> clientes;
    //private List<Encomienda> encomiendas;
    //private List<Empleado> empleados;
    private String empleadoActual; //Empleado
    private String clienteActual; //Cliente
    private String sucursalActual; //Sucursal
    private String encomiendaTemp; //Encomienda
    private String pedidoTemp; //Pedido
    //private List<Ingreso> ingresos;
    
    public fbEmpresa()
    {
    // Empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
    
    public String getEmpleadoActual()
    { return empleadoActual; }
    public String getClienteActual()
    { return clienteActual; }
    public String getSucursalActual()
    { return sucursalActual; }
    public String getEncomiendaTemporal()
    { return encomiendaTemp; }
    public String getPedidoTemp()
    { return pedidoTemp; }
    
    /*public void setpedidotemp(Pedido pedido)
    {pedidotemp=pedido;}
    public List<Empleado> getempleados()
    {return empleados;}
    public List<Cliente> getclientes()
    {return clientes;}
    public void setempleadoactual(Empleado empleado)
    {empleadoActual=empleado;}
    public void setclienteactual(Cliente cliente)
    {clienteActual=cliente;}
    public List<Encomienda> getencomiendas()
    {return encomiendas;}
    public List<Camion> getcamiones()
    {return camiones;}
    public void setencomiendatemporal(Encomienda encotempo)
    {encomiendaTemporal=encotempo;}
    public Encomienda getencomiendabyid(int id)
    {
        Encomienda temp = null;
        for(Encomienda en: encomiendas)
        {
            if (en.getid() == id) 
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
            if(s.getdireccion()==direccion)
            {return s;}
        }
        return sucursales.get(0);
    }
    
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
    

    /**
     * Retorna default toString()
     * @return String
     */
    @Override
    public String toString() {
        return this.toString();
    }
    
    /**
     * Para obtener el formato de fecha.
     * Uso:
     * dateFormat.format(date) 
     * Entrega un string con la fecha en formato "yyyy/MM/dd HH:mm:ss"
     * @return DateFormat
     */
    public DateFormat dateFormat()
    { return (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")); }
}

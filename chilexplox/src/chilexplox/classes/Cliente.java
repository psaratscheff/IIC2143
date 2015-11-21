package chilexplox.classes;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    // RUT Opcional, la persona puede no querer quedar registrada en el sistema
    // Por eso no se agrega al constructor y debe ser modificado posteriormente
    private String rut;
    private String usuario;
    private String password;
    private String nombre;
    private String apellido;
    private String direccion;
    private String sucursalRegistrada;
    private List<Pedido> pedidosRealizados;

    public Cliente() {} // Constructor vacío para Firebase

    public Cliente(String nombre, String apellido, String direccion, String user, String pass)
    {
      this.nombre = nombre;
      this.usuario=user;
      this.password=pass;
      this.apellido = apellido;
      this.direccion = direccion;
      //Inicializo los arrays para poder agregar valores
      this.pedidosRealizados = new ArrayList();
    }
    public String getRut()
    {return rut;}
    public String getUsuario()
    {return usuario;}
    public String getPassword()
    {return password;}
    public String getNombre()
    {return nombre;}
     public String getApellido()
    {return apellido;}
     public String getDireccion()
    {return direccion;}
     public String getSucursalRegistrada()
    {return sucursalRegistrada;}
    public List<Pedido> getPedidosRealizados()
    {return pedidosRealizados;}

    public void setRut(String Rut)
    {rut=Rut;}
    public void setnNombre(String Nombre)
    {nombre=Nombre;}
    public void setApellido(String Apellido)
    {apellido=Apellido;}
    public void setDireccion(String Direccion)
    {direccion=Direccion;}
    public void setSucursalRegistrada(String sucursal)
    {sucursalRegistrada=sucursal;}
    public void setPedidosRealizados(List<Pedido> pedidos)
    {pedidosRealizados=pedidos;}
    
    /**
     * Retorna el nombre completo del cliente
     * @return String
     */
    @Override
    public String toString() {
        return this.nombre + " " + this.apellido;
    }
}

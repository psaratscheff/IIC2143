package chilexplox.classes;


import java.util.ArrayList;
import java.util.List;

public class Cliente {
  // RUT Opcional, la persona puede no querer quedar registrada en el sistema
  // Por eso no se agrega al constructor y debe ser modificado posteriormente
  private String rut;
  private String nombre;
  private String apellido;
  private String direccion;
  //Relacionados
  private Sucursal sucursalRegistrada;
  private List<Pedido> pedidosRealizados;

  public Cliente(String nombre, String apellido, String direccion)
  {
    this.nombre = nombre;
    this.apellido = apellido;
    this.direccion = direccion;
    //Inicializo los arrays para poder agregar valores
    this.pedidosRealizados = new ArrayList();
  }
 
  public String getrut()
  {return rut;}
  public void setrut(String Rut)
  {rut=Rut;}
  public String getnombre()
  {return nombre;}
  public void setnombre(String Nombre)
  {nombre=Nombre;}
   public String getapellido()
  {return apellido;}
  public void setapellido(String Apellido)
  {apellido=Apellido;}
   public String getdireccion()
  {return direccion;}
  public void setdireccion(String Direccion)
  {direccion=Direccion;}
   public Sucursal getsucursalregistrada()
  {return sucursalRegistrada;}
  public void setsucursalregistrada(Sucursal sucursal)
  {sucursalRegistrada=sucursal;}
  public List<Pedido> getpedidosRealizados()
  {return pedidosRealizados;}
  public void setpedidosRealizados(List<Pedido> pedidos)
  {pedidosRealizados=pedidos;}
}

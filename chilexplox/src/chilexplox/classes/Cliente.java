package chilexplox.classes;


import java.util.ArrayList;
import java.util.List;

public class Cliente {
  // RUT Opcional, la persona puede no querer quedar registrada en el sistema
  // Por eso no se agrega al constructor y debe ser modificado posteriormente
  private String rut;
  public String nombre;
  public String apellido;
  public String direccion;
  //Relacionados
  public Sucursal sucursalRegistrada;
  public List<Pedido> pedidosRealizados;

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
}

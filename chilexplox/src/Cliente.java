
import java.util.List;

public class Cliente {
  public String nombre;
  public String apellido;
  public String direccion;
  //Relacionados
  public List<Sucursal> sucursalesRegistradas;
  public List<Pedido> pedidosRealizados;

  public Cliente(String nombre, String apellido, String direccion)
  {
    this.nombre = nombre;
    this.apellido = apellido;
    this.direccion = direccion;
  }
}

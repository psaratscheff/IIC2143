
public class Cliente {
  public String name;
  public String apellido;
  public String direccion;
  //Relacionados
  public List<Sucursal> sucursalesRegistradas;
  public List<Pedido> pedidosRealizados;

  public Cliente(String nombre, String apellido, String direccion)
  {
    this.name = name;
    this.apellido = apellido;
    this.direccion = direccion;
  }
}

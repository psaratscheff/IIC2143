
public class Empleado {
  public string nombre;
  public string apellido;
  public string username;
  private string password;
  public tuple<int,int> horario;
  //Relacionados
  public Sucursal sucursalDondeTrabaja;

  public Empleado(string nombre, string apellido, string username, string password, tuple<int,int> horario)
  {
    this.name = name;
    this.apellido = apellido;
    this.username = username;
    this.password = password;
    this.horario = horario
  }

  public void EnviarMensaje(Sucursal sucursal, String mensaje, boolean urgente)
  {
    // Falta implementar
  }
}

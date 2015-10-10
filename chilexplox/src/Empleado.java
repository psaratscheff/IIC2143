
import java.util.List;

public class Empleado {
  public String nombre;
  public String apellido;
  public String username;
  private String password;
  public List<Integer> horario;
  //Relacionados
  public Sucursal sucursalDondeTrabaja;

  public Empleado(String nombre, String apellido, String username, String password, List<Integer> horario)
  {
    this.nombre = nombre;
    this.apellido = apellido;
    this.username = username;
    this.password = password;
    this.horario = horario;
  }

  public void EnviarMensaje(Sucursal sucursal, String mensaje, boolean urgente)
  {
    // Falta implementar
  }
}

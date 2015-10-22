package chilexplox.classes;


import java.util.List;

public class Empleado {
  public String nombre;
  public String apellido;
  public String username;
  private String password;
  public List<Integer> horario;
  //Relacionados
  public Sucursal sucursalDondeTrabaja;

  public Empleado(String nombre, String apellido, String username, String password, List<Integer> horario, Sucursal sucursal)
  {
    this.nombre = nombre;
    this.apellido = apellido;
    this.username = username;
    this.password = password;
    this.horario = horario;
    this.sucursalDondeTrabaja = sucursal;
  }

  public void EnviarMensaje(Sucursal sucursal, String mensaje, boolean urgente)
  {
      Mensaje mensaje1 = new Mensaje(mensaje, urgente);
      sucursal.mensajesRecibidos.add(mensaje1);
      
  }
  
  public void ModificarDireccionEncomienda(Encomienda e, Sucursal destino) //Busco la encomienda o el id de la encomienda? - Thom
  {
      if (e.origen == this.sucursalDondeTrabaja) 
      {
          e.destino = destino;
          EnviarMensaje(destino, "Destino encomienda "+ e.id + " actualizado", true);
      }
  }
}

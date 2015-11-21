package chilexplox.classes;


import java.util.List;

public class Empleado {
  private String nombre;
  private String apellido;
  private String username;
  private String password;
  private List<Integer> horario;
  //Relacionados
  private Sucursal sucursalDondeTrabaja;

  public Empleado(String nombre, String apellido, String username, String password, List<Integer> horario, Sucursal sucursal)
  {
    this.nombre = nombre;
    this.apellido = apellido;
    this.username = username;
    this.password = password;
    this.horario = horario;
    this.sucursalDondeTrabaja = sucursal;
  }
public String getnombre()
{return nombre;}
public String getapellido()
{return apellido;}
public String getusername()
{return username;}
public String getpassword()
{return password;}
public List<Integer> gethorario()
{return horario;}
public Sucursal getsucursaldondetrabaja()
{return sucursalDondeTrabaja;}

  public void EnviarMensaje(Sucursal sucursal, String mensaje, boolean urgente)
  {
      Mensaje mensaje1 = new Mensaje(mensaje, urgente);
      sucursal.getMensajesRecibidos().add(mensaje1);
      
  }
  
  public void ModificarDireccionEncomienda(Encomienda e, Sucursal destino) //Busco la encomienda o el id de la encomienda? - Thom
  {
      if (e.getSucursalOrigen() == this.sucursalDondeTrabaja.getDireccion()) 
      {
          e.setdestino(destino.getDireccion());
          EnviarMensaje(destino, "Destino encomienda "+ e.getId() + " actualizado", true);
      }
  }
}

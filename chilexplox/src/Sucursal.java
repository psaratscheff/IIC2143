
public class Sucursal {
  public String direccion;
  public int capacidad;
  //Relacionados
  public List<Mensaje> mensajesRecibidos;
  public List<Empleado> trabajadores;
  public List<Camion> camionesEstacionados;
  public List<Cliente> clientesRegistrados;

  public Sucursal(String direccion, int capacidad)
  {
    this.direccion = direccion;
    this.capacidad = capacidad;
  }

  public int EspacioDisponible()
  {
    // Falta implementar
  }

  public void CargarCamion(Camion camion, List<Encomienda> encomiendas)
  {
    // Falta implementar
  }

  public void RecibirCamion(Camion camion)
  {
    // Falta implementar
  }
}

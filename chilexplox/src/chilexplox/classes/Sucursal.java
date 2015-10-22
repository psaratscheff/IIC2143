package chilexplox.classes;


import java.util.ArrayList;
import java.util.List;

public class Sucursal {
  public String direccion;
  public int capacidad;
  //Relacionados
  public List<Mensaje> mensajesRecibidos;
  public List<Empleado> trabajadores;
  public List<Camion> camionesEstacionados;
  public List<Cliente> clientesRegistrados;
  //Faltantes en el UML
  public List<Encomienda> encomiendasAlmacenadas;

  public Sucursal(String direccion, int capacidad)
  {
    this.direccion = direccion;
    this.capacidad = capacidad;
    //Inicializo los arrays para poder agregar valores
    this.encomiendasAlmacenadas = new ArrayList();
    this.mensajesRecibidos = new ArrayList();
    this.trabajadores = new ArrayList();
    this.camionesEstacionados = new ArrayList();
    this.clientesRegistrados = new ArrayList();
  }

  public int EspacioDisponible()
  {
    int espacio= this.capacidad- this.encomiendasAlmacenadas.size();
    return espacio;
  }

  public void CargarCamion(Camion camion, List<Encomienda> encomiendas)
  {
    // Falta validar espacio disponible
      camion.encomiendas.addAll(encomiendas); //Agrega todas las encomiendas de la lista
      camion.disponibilidad=false;  // deja de estar disponible el camion
  }

  public List<Encomienda> RecibirCamion(Camion camion)
  {
    // Falta validar espacio disponible
      List<Encomienda> temporal = camion.encomiendas;
      camion.encomiendas.clear(); //Vacío el camión
      camion.disponibilidad=true;  //vuelve a estar disponible el camion
      for(Encomienda e : temporal) // cambia estado de la encomienda
        {
            e.estado="destino";
        }
      return temporal;
  }
}

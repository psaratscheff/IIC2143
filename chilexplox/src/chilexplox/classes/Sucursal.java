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
  public List<Encomienda> encomiendasRecibidas;

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
    this.encomiendasRecibidas = new ArrayList();
  }

  public int EspacioDisponible()
  {
    int espacio= this.capacidad- this.encomiendasAlmacenadas.size();
    return espacio;
  }

  
  public Encomienda getEncomienda(int id)
  {
      for (Encomienda e: encomiendasAlmacenadas)
      {
          if (e.id == id)
          {
              return e;
          }
      }
      for (Encomienda e: encomiendasRecibidas)
      {
          if (e.id == id)
          {
              return e;
          }
      }
      return null;
  }
  public List<Encomienda> RecibirCamion(Camion camion)
  {
    // Falta validar espacio disponible
      List<Encomienda> temporal = camion.getlistencomiendas();
      camion.borrarencomiendas(); //Vacío el camión
      camion.disponibilidad=true;  //vuelve a estar disponible el camion
      for(Encomienda e : temporal) // cambia estado de la encomienda
        {
            e.estado="destino";
        }
      return temporal;
  }
}

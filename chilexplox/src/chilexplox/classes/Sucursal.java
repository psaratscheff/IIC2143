package chilexplox.classes;


import java.util.ArrayList;
import java.util.List;

public class Sucursal {
    private String direccion;
    private int capacidad;
    //Relacionados
    private List<Mensaje> mensajesRecibidos;
    private List<Empleado> trabajadores;
    private List<Camion> camionesEstacionados;
    private List<Cliente> clientesRegistrados;
    //Faltantes en el UML
    private List<Encomienda> encomiendasAlmacenadas;
    private List<Encomienda> encomiendasRecibidas;

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

    public List<Camion> getcamionesestacionados()
    {return camionesEstacionados;}

    public List<Encomienda> getencomiendasrecibidas()
    {return encomiendasRecibidas;}
    public List<Encomienda> getencomiendasalmacenadas()
    {return encomiendasAlmacenadas;}
    public String getdireccion()
    {return direccion;}

    public List<Mensaje> getmensajesrecibidos()
    {return mensajesRecibidos;}

    public int EspacioDisponible()
    {
      int espacio= this.capacidad- this.encomiendasAlmacenadas.size();
      return espacio;
    }

  
    public Encomienda getEncomienda(int id)
    {
        for (Encomienda e: encomiendasAlmacenadas)
        {
            if (e.getid() == id)
            {
                return e;
            }
        }
        for (Encomienda e: encomiendasRecibidas)
        {
            if (e.getid() == id)
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
        camion.setdisponibilidad(true);  //vuelve a estar disponible el camion
        for(Encomienda e : temporal) // cambia estado de la encomienda
        {
            e.setestado("destino");
        }
        return temporal;
    }
  
    /**
     * Retorna el nombre de la sucursal (Su direccion)
     * @return String
     */
    @Override
    public String toString() {
        return this.direccion;
    }
}

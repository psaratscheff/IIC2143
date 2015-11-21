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
    
    public Sucursal() {} // Constructor vacío para FireBase

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
    
    // No modificar nombres, necesarios para FireBase!!!
    public String getDireccion()
    { return this.direccion; }
    public int getCapacidad()
    { return this.capacidad; }
    public List<Mensaje> getMensajesRecibidos()
    { return this.mensajesRecibidos; }
    public List<Empleado> getTrabajadores()
    { return this.trabajadores; }
    public List<Camion> getCamionesEstacionados()
    { return this.camionesEstacionados; }
    public List<Cliente> getClientesRegistrados()
    { return this.clientesRegistrados; }
    public List<Encomienda> getEncomiendasAlmacenadas()
    { return this.encomiendasAlmacenadas; }
    public List<Encomienda> getEncomiendasRecibidas()
    { return this.encomiendasRecibidas; }
    
    // Otro métodos en adelante:
    
    public int EspacioDisponible()
    {
      int espacio= this.capacidad- this.encomiendasAlmacenadas.size();
      return espacio;
    }

  
    public Encomienda getEncomienda(String id)
    {
        for (Encomienda e: encomiendasAlmacenadas)
        {
            if (e.getId() == id)
            {
                return e;
            }
        }
        for (Encomienda e: encomiendasRecibidas)
        {
            if (e.getId() == id)
            {
                return e;
            }
        }
        return null;
    }
    public List<Encomienda> RecibirCamion(Camion camion)
    {
        // Falta validar espacio disponible
        List<Encomienda> temporal = camion.getEncomiendas();
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

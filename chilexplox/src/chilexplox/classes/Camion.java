package chilexplox.classes;


import java.util.ArrayList;
import java.util.List;


public class Camion {
    private int capacidad;
    private String name;
    private String tipo;
    private List<Encomienda> encomiendas;
    public boolean disponibilidad;
    
    public Camion(String n, int cap, boolean disp,String tip)
            {
               capacidad = cap;
               disponibilidad = disp;
               name = n;
               tipo=tip;
               //Inicializo los arrays para poder agregar valores
               this.encomiendas = new ArrayList();
            }
    /**
     * retorna el nombre del camion
     * @return 
     */
    public String Nombre()
    {return name;}
    public void borrarencomienda(Encomienda encomienda)
    {encomiendas.remove(encomienda);}
    public void borrarencomiendas()
    {encomiendas.clear();}
    /**
     * carga el camion con la lista de encomiendas
     * @param encomiendas 
     */
    public void addencomienda(Encomienda encomienda)
    {encomiendas.add(encomienda);}
    public void CargarCamion( List<Encomienda> encomiendas)
  {
    // Falta validar espacio disponible
      encomiendas.addAll(encomiendas); //Agrega todas las encomiendas de la lista
      disponibilidad=false;  // deja de estar disponible el camion
  }
    /**
     * retorna nombre-capacidad-tipo del camion en un solo string
     * @return 
     */
    public String getNombre()
    {
        return name + '-' + capacidad;
    }
    /**
     * retorna la lista de encomiendas dentro del camion
     * @return 
     */
    public List<Encomienda> getlistencomiendas()
    {
        return encomiendas;
    }
    /**
     * retorna el tipo del camion(normal,radioactivo,refrigerado)
     * @return 
     */
    public String Tipo()
    {return tipo;}
    public int EspacioDisponible()
    {
        int espacio= this.capacidad- this.encomiendas.size();
        return espacio;
    }
    
    /**
     * Retornar porcentaje de la capacidad disponible en un double 0.0 - 1.0 para la progressBar
     * @return double
     */
    public double PorcentajeDisponible()
    {
        double size = this.encomiendas.size();
        double cap = this.capacidad;
        double porcentaje = size / cap;
        return porcentaje;
    }
    
    /**
     * Retornar nombre del camion con su tamaño
     * @return String
     */
    @Override
    public String toString() {
        return this.name+"-"+this.capacidad;
    }
}

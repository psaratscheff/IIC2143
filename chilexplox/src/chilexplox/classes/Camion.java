package chilexplox.classes;


import java.util.ArrayList;
import java.util.List;


public class Camion {
    private int capacidad;
    private String name;
    private String tipo;
    private List<Encomienda> encomiendas;
    private boolean disponibilidad;
    
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
     * claramente retorna la disponibilidad del camion(si esta ocupado o no)
     * @return 
     */
    public boolean getdisponibilidad()
    {return disponibilidad;}
    /**
     * cambia el estado de disponibilidad del camion al booleano "disp"
     * @param disp 
     */
    public void setdisponibilidad(boolean disp)
    {disponibilidad=disp;}
    /**
     * retorna el nombre del camion
     * @return 
     */
    public String Nombre()
    {return name;}
    /**
     * borra una encomienda "encomienda" de la lista de encomiendas del camion
     * @param encomienda 
     */
    public void borrarencomienda(Encomienda encomienda)
    {encomiendas.remove(encomienda);}
    public void borrarencomiendas()
    {encomiendas.clear();}
    
    /**
     * carga una encomienda en el camion
     * @param encomienda 
     */
    public void addencomienda(Encomienda encomienda)
    {encomiendas.add(encomienda);}
    
    /**
     * carga el camion con la lista de encomiendas
     * @param encomiendas 
     */
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
     * @return
     */
    public double PorcentajeDisponible()
    {
        double size = this.encomiendas.size();
        double cap = this.capacidad;
        double porcentaje = size / cap;
        return porcentaje;
    }
}

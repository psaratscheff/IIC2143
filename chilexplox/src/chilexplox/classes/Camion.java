package chilexplox.classes;


import java.util.ArrayList;
import java.util.List;


public class Camion {
    private int capacidad;
    private String nombre;
    private String tipo;
    private List<Encomienda> encomiendas;
    private boolean disponibilidad;

    public Camion() // Constructor vacío para Firebase
    {
        //Inicializo los arrays para poder agregar valores
        this.encomiendas = new ArrayList();
    }

    public Camion(String n, int cap, boolean disp,String tip)
    {
        capacidad = cap;
        disponibilidad = disp;
        nombre = n;
        tipo=tip;
        //Inicializo los arrays para poder agregar valores
        this.encomiendas = new ArrayList();
    }
    
    // No modificar nombres, necesarios para FireBase!!!
    public int getCapacidad()
    {return this.capacidad;}
    public String getNombre()
    {return this.nombre;}
    public String getTipo()
    {return this.tipo;}
    public List<Encomienda> getEncomiendas()
    {return this.encomiendas;}
    public Boolean getDisponibilidad()
    {return this.disponibilidad;}
    
    // Otro métodos en adelante:
    
    /**
     * cambia el estado de getDisponibilidad del camion al booleano "disp"
     * @param disp 
     */
    public void setdisponibilidad(boolean disp)
    {disponibilidad=disp;}
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
     * Retorna nombre-capacidad-tipo (Sin tipo si es normal)
     * @return String
     */
    @Override
    public String toString() {
        String t = "";
        if (this.tipo != "Normal")
        {
            t="-"+this.tipo;
        }
        return this.nombre+"-"+this.capacidad+t;
    }
}

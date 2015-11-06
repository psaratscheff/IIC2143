package chilexplox.classes;


import java.util.ArrayList;
import java.util.List;


public class Camion {
    private int capacidad;
    public String name;
    public String tipo;
    public List<Encomienda> encomiendas;
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
    
    public String getNombre()
    {
        return name + '-' + capacidad;
    }
    
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

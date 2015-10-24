package chilexplox.classes;


import java.util.ArrayList;
import java.util.List;


public class Camion {
    private int capacidad;
    private String name;
    public List<Encomienda> encomiendas;
    public boolean disponibilidad;
    
    public Camion(String n, int cap, boolean disp)
            {
               capacidad = cap;
               disponibilidad = disp;
               name = n;
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
        double porcentaje = this.encomiendas.size() / this.capacidad;
        return porcentaje;
    }
}

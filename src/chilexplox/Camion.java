package chilexplox;


import java.util.ArrayList;
import java.util.List;


public class Camion {
    public int capacidad;
    public List<Encomienda> encomiendas;
    public boolean disponibilidad;
    
    public Camion(int cap, boolean disp)
            {
               capacidad=cap;
               disponibilidad= disp;
               //Inicializo los arrays para poder agregar valores
               this.encomiendas = new ArrayList();
            }
    
    public int EspacioDisponible()
    {
        int espacio= this.capacidad- this.encomiendas.size();
        return espacio;
    }
}

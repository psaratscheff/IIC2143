
import java.util.List;


public class Camion {
    public int capacidad;
    public List<Encomienda> encomiendas;
    public boolean disponibilidad;
public Camion(int cap, boolean disp)
        {
           capacidad=cap;
           disponibilidad= disp;
           
        }
public int EspacioDisponible()
{
    int espacio= this.capacidad- this.encomiendas.size();
    return espacio;
}
}

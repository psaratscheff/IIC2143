package chilexplox.classes;


import java.util.ArrayList;
import java.util.List;


public class fbCamion {
    private String capacidad;
    private String nombre;
    private String tipo;
    private String disponibilidad; // true or false
    
    public fbCamion()
    {
    // Empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
    
    public String getCapacidad()
    { return this.capacidad; }
    public String getName()
    { return this.nombre; }
    public String getTipo()
    { return this.tipo; }
    public String getDisponibilidad()
    { return this.disponibilidad; }
    
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

package chilexplox.classes;


import java.util.ArrayList;
import java.util.List;

public class fbSucursal {
    private String direccion;
    private String capacidad;

    public fbSucursal()
    {
    // Empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
    
    public String getDireccion()
    {return direccion;}
    public String getCapacidad()
    {return capacidad;}
    
    /**
     * Retorna el nombre de la sucursal (Su direccion)
     * @return String
     */
    @Override
    public String toString() {
        return this.direccion;
    }
}

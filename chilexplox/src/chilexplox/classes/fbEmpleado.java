package chilexplox.classes;

public class fbEmpleado {
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private String horario;
    private String sucursalDondeTrabaja;

    public fbEmpleado()
    {
      // Empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public String getNombre()
    { return nombre; }
    public String getApellido()
    { return apellido; }
    public String getUsername()
    { return username; }
    public String getPassword()
    { return password; }
    public String gethorario()
    { return horario; }
    public String getSucursalDondeTrabaja()
    { return sucursalDondeTrabaja; }
    
    /**
     * Retorna nombre completo emplado
     * @return String
     */
    @Override
    public String toString() {
        return this.nombre+" "+this.apellido;
    }
}

package chilexplox.classes;

public class fbCliente {
    private String nombre;
    private String apellido;
    private String rut;
    private String usuario;
    private String password;
    private String direccion;
    private String sucursalRegistrada;

    public fbCliente()
    {
    // Empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public String getNombre()
    { return nombre; }
    public String getApellido()
    { return apellido; }
    public String getRut()
    { return rut; }
    public String getUsuario()
    { return usuario; }
    public String getPassword()
    { return password; }
    public String getDireccion()
    { return direccion; }
    public String getSucursalRegistrada()
    { return sucursalRegistrada; }
    
    /**
     * Retorna nombre completo cliente
     * @return String
     */
    @Override
    public String toString() {
        return this.nombre+" "+this.apellido;
    }
}

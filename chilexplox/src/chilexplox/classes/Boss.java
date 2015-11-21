package chilexplox.classes;

public class Boss {
    private String nombre;
    private String apellido;
    private String username;
    private String password;

    public Boss() {} // Constructor vacío para Firebase

    public Boss(String nombre, String apellido, String username, String password)
    {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = password;
    }
    public String getNombre()
    {return nombre;}
    public String getApellido()
    {return apellido;}
    public String getUsername()
    {return username;}
    public String getPassword()
    {return password;}
    
    /**
     * Retorna el nombre completo del cliente
     * @return String
     */
    @Override
    public String toString() {
        return this.nombre + " " + this.apellido;
    }
}
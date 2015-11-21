package chilexplox.classes;


import java.util.List;

public class Empleado {
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private List<Integer> horario;
    private String sucursalDondeTrabaja;
    
    public Empleado() {} // Constructor vacío para Firebase

    public Empleado(String nombre, String apellido, String username, String password, List<Integer> horario, String sucursal)
    {
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.password = password;
        this.horario = horario;
        this.sucursalDondeTrabaja = sucursal;
    }

    public String getNombre()
    {return nombre;}
    public String getApellido()
    {return apellido;}
    public String getUsername()
    {return username;}
    public String getPassword()
    {return password;}
    public List<Integer> getHorario()
    {return horario;}
    public String getSucursalDondeTrabaja()
    {return sucursalDondeTrabaja;}

    public void EnviarMensaje(Sucursal sucursal, String mensaje, boolean urgente)
    {
        Mensaje mensaje1 = new Mensaje(mensaje, urgente);
        sucursal.getMensajesRecibidos().add(mensaje1);

    }

    public void ModificarDireccionEncomienda(Encomienda e, Sucursal destino) //Busco la encomienda o el id de la encomienda? - Thom
    {
        if (e.getSucursalOrigen() == this.sucursalDondeTrabaja) 
        {
            e.setdestino(destino.getDireccion());
            EnviarMensaje(destino, "Destino encomienda "+ e.getId() + " actualizado", true);
        }
    }
    
    /**
     * Retorna el nombre completo del cliente
     * @return String
     */
    @Override
    public String toString() {
        return this.nombre + " " + this.apellido;
    }
}

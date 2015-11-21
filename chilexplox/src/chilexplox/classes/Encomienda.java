package chilexplox.classes;


public class Encomienda {
    private String estado;
    private String prioridad;
    private String direccionDestino;
    private int tamaño;
    private String id;
    private int largo;
    private int ancho;
    private int peso;
    private String sucursalDestino; //id - firebase
    private String sucursalOrigen; //id - firebase
    private String destinatario; //id - firebase
    private String tipo;

    public Encomienda() {} // Constructor vacío para Firebase
    
    public Encomienda(String estado, String prioridad, int tamaño, String id, String sucursalOrigen, String sucursalDestino, String tipo)
    {
        this.estado = estado;
        this.prioridad = prioridad;
        this.tamaño = tamaño;
        this.id = id;
        this.sucursalDestino = sucursalDestino;
        this.sucursalOrigen = sucursalOrigen;
        this.tipo=tipo;
    }
    
    // No modificar nombres, necesarios para FireBase!!!
    public String getEstado()
    {return estado;}
    public String getPrioridad()
    {return prioridad;}
    public String getDireccionDestino()
    {return direccionDestino;}
    public int getTamaño()
    {return tamaño;}
    public String getId()
    {return id;}
    public int getLargo()
    {return largo;}
    public int getAncho()
    {return ancho;}
    public int getPeso()
    {return peso;}
    public String getDestino()
    {return sucursalDestino;}
    public String getOrigen()
    {return sucursalOrigen;}
    public String getDestinatario()
    {return destinatario;}
    public String getTipo()
    {return tipo;}
    
    public void setdestino(String sucursal)
    {sucursalDestino=sucursal;}
    public void settamaño(Integer tam)
    {tamaño=tam;}
    public void setprioridad(String prior)
    {prioridad=prior;}
    public String setdirecciondestino(String direcciondest)
    {return direccionDestino = direcciondest;}
    public void setestado(String est)
    {estado=est;}
    public int setlargo(int lar)
    {return largo = lar;}
    public int setancho(int an)
    {return ancho = an;}
    public int setpeso(int pes)
    {return peso = pes;}

}

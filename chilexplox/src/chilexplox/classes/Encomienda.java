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
  private Sucursal destino;
  private Sucursal origen;
  private Cliente destinatario;
  private String tipo;

  public Encomienda(String estado, String prioridad, int tamaño, String id, Sucursal destino, Sucursal origen,String tipo)
  {
    this.estado = estado;
    this.prioridad = prioridad;
    this.tamaño = tamaño;
    this.id = id;
    this.destino = destino;
    this.origen = origen;
    this.tipo=tipo;
  }
  public String getdirorigen()
  {return origen.getdireccion();}
    public String gettipo()
    {return tipo;}
    public String getid()
    {return id;}
    public Sucursal getdestino()
    {return destino;}
    public void setdestino(Sucursal sucursal)
    {destino=sucursal;}
    public void settamaño(Integer tam)
    {tamaño=tam;}
    public void setprioridad(String prior)
    {prioridad=prior;}
    public String getprioridad()
    {return prioridad;}
    public String getestado()
    {return estado;}
    public Sucursal getorigen()
    {return origen;}
    public int gettamaño()
    {return tamaño;}
    public String getdirecciondestino()
    {return direccionDestino;}
    public String setdirecciondestino(String direcciondest)
    {return direccionDestino = direcciondest;}
    public void setestado(String est)
    {estado=est;}
    public int getlargo()
    {return largo;}
    public int getancho()
    {return ancho;}
    public int getpeso()
    {return peso;}
    public int setlargo(int lar)
    {return largo = lar;}
    public int setancho(int an)
    {return ancho = an;}
    public int setpeso(int pes)
    {return peso = pes;}

}

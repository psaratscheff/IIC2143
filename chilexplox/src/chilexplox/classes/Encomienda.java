package chilexplox.classes;


public class Encomienda {
  private String estado;
  private String prioridad;
  private int tamaño;
  private int id;
  private Sucursal destino;
  private Sucursal origen;
  private Cliente destinatario;
  private String tipo;

  public Encomienda(String estado, String prioridad, int tamaño, int id, Sucursal destino, Sucursal origen,String tipo)
  {
    this.estado = estado;
    this.prioridad = prioridad;
    this.tamaño = tamaño;
    this.id = id;
    this.destino = destino;
    this.origen = origen;
    this.tipo=tipo;
  }
  public String gettipo()
  {return tipo;}
  public Integer getid()
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

    public void setestado(String est)
  {estado=est;}

}

package chilexplox;


public class Encomienda {
  public String estado;
  public String prioridad;
  public int tamaño;
  public int id;
  public Sucursal destino;
  public Cliente destinatario;

  public Encomienda(String estado, String prioridad, int tamaño, int id, Sucursal destino, Cliente destinatario)
  {
    this.estado = estado;
    this.prioridad = prioridad;
    this.tamaño = tamaño;
    this.id = id;
    this.destino = destino;
    this.destinatario = destinatario;
  }

}

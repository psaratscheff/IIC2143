package chilexplox;


public class Mensaje {
  public String contenido;
  public boolean urgente;
  //Relacionados
  //No se si colocar sucursal a la que se dirige, ya que esta contiene sus mensajes - Pedro

  public Mensaje(String contenido, boolean urgente)
  {
    this.contenido = contenido;
    this.urgente = urgente;
  }
}

package chilexplox.classes;


public class Mensaje {
  private String contenido;
  private boolean urgente;
  //Relacionados
  

  public Mensaje(String contenido, boolean urgente)
  {
    this.contenido = contenido;
    this.urgente = urgente;
  }
  public boolean geturgencia()
  {return urgente;}
  public String getcontenido()
  {return contenido;}
}

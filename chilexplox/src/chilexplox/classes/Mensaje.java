package chilexplox.classes;


public class Mensaje {
  private String contenido;
  private boolean urgente;
  //Relacionados
  
  public Mensaje() {} // Constructor vacío para Firebase
  
  public Mensaje(String contenido, boolean urgente)
  {
    this.contenido = contenido;
    this.urgente = urgente;
  }
    
    public Boolean getUrgente()
    {return urgente;}
    public String getContenido()
    {return contenido;}
}

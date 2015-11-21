package chilexplox.classes;


public class Mensaje {
    private String contenido;
    private boolean urgente;

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
    
    /**
     * Retorna contenido del menseje con [URGENTE] al principio si es urgente
     * @return String
     */
    @Override
    public String toString() {
        String t = "";
        if (this.urgente)
        {
            t="[URGENTE]";
        }
        return t+this.contenido;
    }
}

package chilexplox.classes;

public class fbMensaje {
    private String contenido;
    private String urgente;

    public fbMensaje()
    {
      // Empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public String getUrgente()
    { return urgente; }
    public String getContenido()
    { return contenido; }
    
    /**
     * Retorna el mensaje con [URGENTE] al inicio en caso de ser urgente
     * @return String
     */
    @Override
    public String toString() {
        String urg = "";
        if (this.urgente.equals("true")){
            urg= "[Urgente]";
        }
        return urg+this.contenido;
    }
}

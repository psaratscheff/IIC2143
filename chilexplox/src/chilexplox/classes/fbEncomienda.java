package chilexplox.classes;


public class fbEncomienda {
    private String estado;
    private String prioridad;
    private String direccionDestino;
    private String tamaño;
    private String id;
    private String largo;
    private String ancho;
    private String peso;
    private String destino;
    private String origen;
    private String destinatario;
    private String tipo;

    public fbEncomienda()
    {
    // Empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
    
    public String getEstado()
    { return this.estado; }
    public String getPrioridad()
    { return this.prioridad; }
    public String getDireccionDestino()
    { return this.direccionDestino; }
    public String getTamaño()
    { return this.tamaño; }
    public String getId()
    { return this.id; }
    public String getLargo()
    { return this.largo; }
    public String getAncho()
    { return this.ancho; }
    public String getPeso()
    { return this.peso; }
    public String getDestino()
    { return this.destino; }
    public String getOrigen()
    { return this.origen; }
    public String getDestinatario()
    { return this.destinatario; }
    public String getTipo()
    { return this.tipo; }
    
    /**
     * Retorna "encomienda"+id
     * @return String
     */
    @Override
    public String toString() {
        return "encomienda"+this.id;
    }
}

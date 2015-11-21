package chilexplox.classes;

public class fbIngreso {
    private String valor;
    private String fecha;
    
    public fbIngreso()
    {
      // Empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
    
    public String getValor()
    { return this.valor; }
    public String getFecha()
    { return this.fecha; }
    
    /**
     * Retorna fecha_valor
     * @return String
     */
    @Override
    public String toString() {
        return this.fecha+"_"+this.valor;
    }
}

package chilexplox.classes;

public class fbPedido {
    private String id;
    
    public fbPedido()
    {
    // Empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
    public String getId() 
    { return id; }
    
    /**
     * Retorna String pedido junto al id del pedido
     * @return String
     */
    @Override
    public String toString() {
        return "pedido"+this.id;
    }
}

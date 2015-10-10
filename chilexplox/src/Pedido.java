
import java.util.List;


public class Pedido {
    public List<Encomienda> encomiendas;
    public int id;
public Pedido(int identificador)
{
    id=identificador;
    encomiendas= new List<Encomienda>() {};
}
public int Contar()
{
    return encomiendas.size();
}
public int CalcularValor()
{
    int tamano=0;
    
foreach(Encomienda e in this.encomiendas)
{
    tamano+= e.tamano;
    
}
int valor= tamano*5;
        return valor;
{

}
}
}

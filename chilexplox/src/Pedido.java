
import java.util.ArrayList;
import java.util.List;


public class Pedido {
    public List<Encomienda> encomiendas;
    public int id;
public Pedido(int identificador)
{
    id=identificador;
    encomiendas= new ArrayList<Encomienda>() {};
}
public int Contar()
{
    return encomiendas.size();
}
public int CalcularValor()
{
    int tamano=0;
    
for(Encomienda e :encomiendas)
{
    tamano+= e.tamano;
    
}
int valor= tamano*5;
        return valor;
}
}

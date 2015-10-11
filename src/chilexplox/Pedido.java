package chilexplox;


import java.util.ArrayList;
import java.util.List;


public class Pedido {
    public List<Encomienda> encomiendas;
    public int id;
    public Pedido(int identificador)
    {
        id=identificador;
        //Inicializo los arrays para poder agregar valores
        encomiendas= new ArrayList<Encomienda>() {};
    }
    public int Contar()
    {
        return encomiendas.size();
    }
    public int CalcularValor()
    {
        int tamano=0;
        int factor=0;
    for(Encomienda e :encomiendas)
    {
        if(e.prioridad=="urgente")
        {factor=3;}
         if(e.prioridad=="normal")
        {factor=1;}
          if(e.prioridad=="express")
        {factor=2;}
        tamano+= (e.tamaño*factor);

    }
    int valor= tamano*5;
            return valor;
    }
}

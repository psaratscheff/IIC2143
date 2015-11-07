package chilexplox.classes;


import java.util.ArrayList;
import java.util.List;


public class Pedido {
    private List<Encomienda> encomiendas;
    private int id;
    
    public Pedido(int identificador)
    {
        id=identificador;
        //Inicializo los arrays para poder agregar valores
        encomiendas= new ArrayList<Encomienda>() {};
    }
    public List<Encomienda> getencomiendas()
    {return encomiendas;}
    public void addencomienda(Encomienda encomienda)
    { encomiendas.add(encomienda);}
    public void removeencomienda(Encomienda encomienda)
    { encomiendas.remove(encomienda);}
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
            if(e.getprioridad()=="Urgente")
            {factor=3;}
             if(e.getprioridad()=="Normal")
            {factor=1;}
              if(e.getprioridad()=="Express")
            {factor=2;}
            tamano+= (e.gettamaño()*factor);

        }
        int valor= tamano*5;
        return valor;
    }
}

package chilexplox.classes;


import java.util.ArrayList;
import java.util.List;


public class Pedido {
    private List<Encomienda> encomiendas;
    private String id;
    
    public Pedido(String identificador)
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
    public Encomienda getencomiendabyid(String id)
    {
        Encomienda temp = null;
        for(Encomienda en: encomiendas)
        {
            if (en.getId() == id) 
            {
                temp =en;
            }
        } 
        return temp;
    }
    public int CalcularValor()
    {
        int tamano=0;
        int factor=0;
        for(Encomienda e :encomiendas)
        {
            if(e.getPrioridad()=="Urgente")
            {factor=3;}
             if(e.getPrioridad()=="Normal")
            {factor=1;}
              if(e.getPrioridad()=="Express")
            {factor=2;}
            tamano+= (e.getTamaño()*factor);

        }
        int valor= tamano*5;
        return valor;
    }
}

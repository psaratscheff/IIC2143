/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author peter
 */
public class Empresa {
    public List<Sucursal> sucursales;
    public List<Camion> camiones;
    public List<Cliente> clientes;
    public List<Encomienda> encomiendas;
    
    public Empresa()
    {
        this.sucursales = new ArrayList();
        this.camiones = new ArrayList();
        this.clientes = new ArrayList();
        this.encomiendas = new ArrayList();
    }
    public Camion EntregarCamion()
    {
        for(Camion c: camiones)
        {
            if (c.disponibilidad==true)
            {return c;}
        }
        return null;
    }
    
    public Cliente buscarpersona(String rut)
    {
    for(Cliente c: clientes)
    {
    if (c.rut==rut)
    {
    return c;
    }
    }
    return null;
    }
    
    public void cambiarestadoencomienda(int id, String estado)
    {
        for(Encomienda e: encomiendas)
        {
            if(e.id==id)
            {
                e.estado=estado;
            }
        }
    }
    
    public String verestadoencomienda(int id)
    {
        for(Encomienda e: encomiendas)
        {
            if(e.id==id)
            {
                String estado=e.estado;
                return estado;
            }
        }
        return null;
        
    }
   /* public int buscarmenoridencomienda()
    {
        int largo=encomiendas.size();
        int menorid=0;
        boolean esta=true;
        for(int i=0; i<largo;i++)
        {
        for(Encomienda e: encomiendas)
        {
           
        }
        }
    }*/
    
    
}

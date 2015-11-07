/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox.classes;

import java.util.Date;


/**
* Representa un ingreso  único con fecha
*/
public class Ingreso {
    private int valor;
    private Date fecha;
    
    public Ingreso(int valor, Date fecha)
    {
        this.valor = valor;
        this.fecha = fecha;
    }
    public int Valor()
    { return this.valor; }
    
    public Date Fecha()
    { return this.fecha; }
}

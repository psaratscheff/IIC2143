/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chilexplox.classes;

/**
 *
 * @author Felix
*/
public class Boss {
  private String nombre;
  private String apellido;
  private String username;
  private String password;
  
  //Relacionados
  

  public Boss(String nombre, String apellido, String username, String password)
  {
    this.nombre = nombre;
    this.apellido = apellido;
    this.username = username;
    this.password = password;
  }
  public String getnombre()
{return nombre;}
public String getapellido()
{return apellido;}
public String getusername()
{return username;}
public String getpassword()
{return password;}
  
}
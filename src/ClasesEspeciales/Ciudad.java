/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClasesEspeciales;

/**
 *
 * @author romangattas
 */
public class Ciudad {
    private String nombre;
    private String provincia;
        
    //builder
    
    public Ciudad(String c, String p){
        nombre = c;
        provincia = p;
    }
    
    //getters

    public String getNombre(){
        return nombre;
    }

    public String getProvincia(){
        return provincia;
    }

    
    //setters

    public void setNombre(String nom){
        nombre = nom;
    }

    public void setProvincia(String prov){
        provincia = prov;
    }
    
    public String toString(){
        return "Ciudad: " +this.getNombre()+ ", Provincia: " +this.getProvincia();
    }

}

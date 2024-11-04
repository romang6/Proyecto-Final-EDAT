package ClasesEspeciales;

/**
 *
 * @author romangattas
 */
public class Cliente {
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    
    
    public Cliente(String nom, String ap, String tel, String em){
        nombre = nom;
        apellido = ap;
        telefono = tel;
        email = em;
    }
    
    
    // getters


    public String getNombre() {
        return nombre;
    }

    
    public String getApellido() {
        return apellido;
    }

    
    public String getTelefono() {
        return telefono;
    }

    
    public String getEmail() {
        return email;
    }

    
    // setters

    
    public void setNombre(String nom) {
        nombre = nom;
    }

    
    public void setApellido(String ap) {
        apellido = ap;
    }

    
    public void setTelefono(String tel) {
        telefono = tel;
    }

    
    public void setEmail(String em) {
        email = em;
    }
    
    //visualizadores
    
    public String toString() {
        return "Nombre: " +nombre+ ", apellido: " +apellido+ ", telfono: " +telefono+ "y mail: " +email;
    }
    
    
}

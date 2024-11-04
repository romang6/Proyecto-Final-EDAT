package ListaSolicitudes;

import ClasesEspeciales.Solicitud;

public class Lista {
    private Nodo cabecera;
    private int longitud;
    
    
    public Lista(){
        this.cabecera = null;
        this.longitud = 0;
    }
    
    public boolean insertar(Solicitud soli, int pos){
        boolean exito = false;
        //Chequea que la posición sea valida
        if(pos >= 1 && pos <= this.longitud+1){
            exito = true;
            //Si es el primer elemento, se agrega a cabecera
            if(pos == 1){
                Nodo nuevo = new Nodo(soli, this.cabecera);
                this.cabecera = nuevo;
            }//Si la posición no es la 1, se procede a agregarlo con aux
            else{
                //Guarda cabecera en temporal
                Nodo aux = this.cabecera;
                //itera modificando aux hasta llegar
                //al elemento anterior de la posicion deseada
                //para agregar
                int i = 1;
                while (i < pos-1) {
                    aux = aux.getEnlace();
                    i++;
                }
                Nodo nuevo = new Nodo(soli, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
            this.longitud++;
        }
        return exito;      
    }
    
    public boolean eliminar(int pos){
        boolean exito = false;
        if(pos >= 1 && pos <= this.longitud){ 
            exito = true;
           //Si es el primer elemento, se elimina la cabecera
           if(pos == 1){
               this.cabecera = this.cabecera.getEnlace();
           }//Si la posición no es la 1, se apunta a la cabecera con aux
           //y se itera hasta llegar al elemento que se va a eliminar
           else{
               Nodo aux = this.cabecera;
               int i = 1;
               while (i < pos-1) {
                   aux = aux.getEnlace();
                   i++;
               }
               //Desconecta el enlace referenciado a la posición que 
               //queremos eliminar, y reenlaza con el elemento siguiente          
               aux.setEnlace(aux.getEnlace().getEnlace());
           }
           this.longitud--;
        }
        return exito;
    }
    
    public Solicitud recuperar(int pos){
        Solicitud recuperado = null;
        //Verifica si la posicion es valida
        if(pos >= 1 && pos <= this.longitud){
            Nodo aux = this.cabecera;
            //itera desde el primer elemento hasta el elemento en la posicion 
            //que buscamos
            int i = 1;
            while (i<pos) {
                aux = aux.getEnlace();
                i++;
            }
            recuperado = aux.getSolicitud();          
        }
        return recuperado;
    }
    
    public int localizar(Solicitud soli){
        int posLocalizada = -1;
        int pos = 1;
        if(this.cabecera != null){
            Nodo aux = this.cabecera;
            while(aux.getEnlace() != null && !(aux.getSolicitud().equals(soli))){
                aux = aux.getEnlace();
                pos++;
            }
            if(aux.getSolicitud() == soli){
                posLocalizada = pos;
            }
        } 
        return posLocalizada;
    }
    
    public boolean esVacia(){
        return cabecera == null;
    }
    
    public void vaciar(){
        cabecera = null;
    }
    
    public int longitud(){
        return longitud;
    }
    
    public String toString(){
        String msg = "";
        Nodo aux = this.cabecera;
        int i = 1; 
        while(aux != null){
            msg += "Solicitud numero " +i+ ":\n"; 
            msg += aux.getSolicitud().toString() + "\n";
            msg += "~~~~~~" + "\n";
            aux = aux.getEnlace();
            i++;
        }
        return msg;
    }
    
    public Lista clone(){
        Lista clon = new Lista();
        // Si la lista no es vacia empiezo a clonar
        if(!this.esVacia()){
            // Creo la cabecera de clon con un nuevo nodo que va a tener
            // el elemento de la lista original con un enlace nulo
            clon.cabecera = new Nodo(this.cabecera.getSolicitud(), null);
            // aux1 va a apuntar a la lista original
            Nodo aux1 = this.cabecera;
            //aux2 va a apuntar a la lista clonada
            Nodo aux2 = clon.cabecera;
            // apunto aux1 a la siguiente posicion para empezar a clonar los
            // siguientes elementos de la lista
            aux1 = aux1.getEnlace();
            // mientras aux no sea nulo se va a seguir clonando
            while(aux1 != null){
                // al enlace  de aux2 le asigno el siguiente nodo con: 
                // el elemento del nodo al que apunta aux1 y enlace nulo 
                // (ya que aux1 va una posicion adelantada)
                aux2.setEnlace(new Nodo(aux1.getSolicitud(), null));
                // itero ambos aux para seguir clonando
                aux1 = aux1.getEnlace();
                aux2 = aux2.getEnlace();
            }
        }
        return clon;
    }
    
    //metodos propios
    
    //localiza una solicitud asociada a un cliente
    
    public int localizarSolicitudAsociada(String id, int nroID){
        int posLocalizada = -1;
        int pos = 1;
        if(this.cabecera != null){
            Nodo aux = this.cabecera;
            while(aux.getEnlace() != null && !(aux.getSolicitud().esCliente(id, nroID))){
                aux = aux.getEnlace();
                pos++;
            }
            if(aux.getSolicitud().esCliente(id, nroID)){
                posLocalizada = pos;
            }
        } 
        return posLocalizada;
    }
    
    
    //calcula el espacio necesario en un camion para todos los pedidos
    
    public double calculaEspacio(){
        
        double total = 0;
        if(!this.esVacia()){
            //obtiene los m3 de cada soli y los suma
            Nodo aux = this.cabecera;
            while(aux!=null){
                total += aux.getSolicitud().getM3();
                aux=aux.getEnlace();
            }
        
        }
        return total;
    }
    
}


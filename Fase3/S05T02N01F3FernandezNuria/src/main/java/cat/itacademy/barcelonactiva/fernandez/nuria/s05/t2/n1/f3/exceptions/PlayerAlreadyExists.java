package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.exceptions;

public class PlayerAlreadyExists extends RuntimeException {
    public PlayerAlreadyExists(String mensaje){
        super(mensaje);
    }

}

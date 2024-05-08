package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.exceptions;

public class PlayerNotExists extends RuntimeException {
    public PlayerNotExists(String mensaje){
        super(mensaje);
    }
}

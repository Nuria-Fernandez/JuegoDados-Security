package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.exceptions;

public class GameNotFound extends RuntimeException{
    public GameNotFound(String mensaje){
        super(mensaje);
    }
}

package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.exceptions;

public class PlayersNotFound extends RuntimeException{
    public PlayersNotFound(String mensaje){
        super(mensaje);
    }
}

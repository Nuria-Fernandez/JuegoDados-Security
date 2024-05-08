package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.exceptions;

public class UserNotFound extends RuntimeException{
    public UserNotFound(String mensaje){
        super(mensaje);
    }
}

package cat.itacademy.barcelonactiva.fernandez.nuria.s05.t2.n1.f3.exceptions;

public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists(String mensaje){
        super(mensaje);
    }
}

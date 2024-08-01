package ticketing.ticket.exception;

public class DuplicationReservationException extends RuntimeException{
    public DuplicationReservationException(String message){
        super(message);
    }
}

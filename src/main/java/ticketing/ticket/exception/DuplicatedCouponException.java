package ticketing.ticket.exception;

public class DuplicatedCouponException extends RuntimeException {
    public DuplicatedCouponException(String message) {
        super(message);
    }
}

package de.needfulapps.exceptions;

public class InsufficientCreditsException extends RuntimeException {
    public InsufficientCreditsException() {
        super("Insufficient credits");
    }
}

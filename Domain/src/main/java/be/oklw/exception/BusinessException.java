package be.oklw.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class BusinessException extends Exception {

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

}

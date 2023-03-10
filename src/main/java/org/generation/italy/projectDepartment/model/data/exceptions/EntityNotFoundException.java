package org.generation.italy.projectDepartment.model.data.exceptions;

public class EntityNotFoundException extends Exception{
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

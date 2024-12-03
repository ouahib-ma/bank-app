package com.cacib.partenaireservice.exceptions;

/**
 * @author Ouahib
 * @Date 01/12/2024
 */
public class PartenaireAlreadyExistsException extends RuntimeException {
    public PartenaireAlreadyExistsException(String message) {
        super(message);
    }
}

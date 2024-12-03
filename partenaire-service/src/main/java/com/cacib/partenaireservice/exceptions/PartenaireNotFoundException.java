package com.cacib.partenaireservice.exceptions;

/**
 * @author Ouahib
 * @Date 01/12/2024
 */
public class PartenaireNotFoundException extends RuntimeException {
    public PartenaireNotFoundException(String message) {
        super(message);
    }
}

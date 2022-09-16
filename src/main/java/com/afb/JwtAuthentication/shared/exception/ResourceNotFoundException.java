package com.afb.JwtAuthentication.shared.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String username){
        super(String.format("Username %s is already registered", username));
    }
    public ResourceNotFoundException(Long id){
        super(String.format("User with id %s doesn't exist", id));
    }
    public ResourceNotFoundException(){
        super("El usuario no existe / Credenciales invalidas.");
    }
}

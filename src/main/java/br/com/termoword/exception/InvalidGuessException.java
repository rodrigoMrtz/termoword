package br.com.termoword.exception;

public class InvalidGuessException extends RuntimeException {
    
    public InvalidGuessException(String word){
        super("A palavra '"+ word + "' não é uma tentativa válida.");
    }
}
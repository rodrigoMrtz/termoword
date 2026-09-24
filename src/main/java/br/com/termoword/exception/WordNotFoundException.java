package br.com.termoword.exception;

public class WordNotFoundException extends RuntimeException {
    public WordNotFoundException(Long id){
        super("Palavra nao encontrada com o id:" + id);
    }
}

package br.com.termoword.dto.word;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateWordRequest(
        @NotBlank (message = "A palavra é obrigatória")
        @Size (min = 5, max = 50, message = "A palavra deve ter entre 5 e 50 caracteres")
        String word,

        @NotBlank (message = "O idioma é obrigatório")
        String language,
        String category,
        String difficulty
){
    
}

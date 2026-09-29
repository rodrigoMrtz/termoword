package br.com.termoword.service;

import br.com.termoword.entity.Word;
import br.com.termoword.repository.WordRepository;
import br.com.termoword.exception.InvalidGuessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith (MockitoExtension.class)
public class GuessValidatorTest {
    
    @Mock 
    private WordRepository wordRepository;

    @Mock 
    private WordNormalizer wordNormalizer;

    @InjectMocks
    private GuessValidator guessValidator;

    @Test 
    void shouldValidateExistingActiveWord(){

        Word word = new Word();
        word.setWord("CASAS");
        word.setLanguage("pt-BR");
        word.setCategory("geral");
        word.setDifficulty("EASY");
        word.setActive(true);

        when(wordNormalizer.normalize(word.getWord()))
            .thenReturn(word.getWord());

        when(wordRepository.findByWordIgnoreCaseAndActiveTrue(word.getWord()))
            .thenReturn(Optional.of(word));
        Word result = guessValidator.validate(word.getWord());

        assertThat(result).isSameAs(word);

        verify(wordNormalizer).normalize(word.getWord());

        verify(wordRepository)
            .findByWordIgnoreCaseAndActiveTrue(word.getWord());
    }

    @Test 
    void shouldThrowExceptionWhenWordNotExist(){

        when(wordNormalizer.normalize("abcde"))
            .thenReturn("abcde");
        
        when(wordRepository.findByWordIgnoreCaseAndActiveTrue("abcde"))
            .thenReturn(Optional.empty());

        assertThatThrownBy(() -> guessValidator.validate("abcde"))
            .isInstanceOf(InvalidGuessException.class)
            .hasMessage("A palavra 'abcde' não é uma tentativa válida.");

        verify(wordNormalizer).normalize("abcde");
        
        verify(wordRepository).findByWordIgnoreCaseAndActiveTrue("abcde");
    }

    @Test 
    void shouldThrowExceptionWhenWordIsInactive(){

        when(wordNormalizer.normalize("CARRO"))
            .thenReturn("CARRO");
        
        when(wordRepository.findByWordIgnoreCaseAndActiveTrue("CARRO"))
            .thenReturn(Optional.empty());

        assertThatThrownBy(() -> guessValidator.validate(("CARRO")))
            .isInstanceOf(InvalidGuessException.class);
        
        verify(wordNormalizer).normalize("CARRO");
        verify(wordRepository).findByWordIgnoreCaseAndActiveTrue("CARRO");
    }
}

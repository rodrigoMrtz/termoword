package br.com.termoword.service;

import br.com.termoword.entity.Word;
import br.com.termoword.dto.word.CreateWordRequest;
import br.com.termoword.dto.word.WordResponse;
import br.com.termoword.repository.WordRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class WordServiceTest {
    @Mock
    private WordRepository wordRepository;

    @InjectMocks 
    private WordService wordService;

    @Test
    void shouldCreateWord() {
        // Arrange (Prepare the test data and mock behavior)
        CreateWordRequest request = new CreateWordRequest(
            "LIVRO",
            "pt-BR",
            "geral",
            "EASY"
            );
        Word savedWord = new Word();
        savedWord.setWord("LIVRO");
        savedWord.setLanguage("pt-BR");
        savedWord.setCategory("geral");
        savedWord.setDifficulty("EASY");
        
        // quando o método save do repositório for chamado com qualquer objeto Word, retorne o savedWord
        when(wordRepository.save(any(Word.class)))
            .thenReturn(savedWord);
        
        // Act (chama o metodo que esta sendo testado)
        WordResponse response = wordService.create(request);
        
        // Assert (verifica se o resultado é o esperado)
        assertThat(response.word()).isEqualTo(savedWord.getWord());
        assertThat(response.language()).isEqualTo(savedWord.getLanguage());
        assertThat(response.category()).isEqualTo(savedWord.getCategory());
        assertThat(response.difficulty()).isEqualTo(savedWord.getDifficulty());

        verify(wordRepository).save(any(Word.class));
    }

    @Test 
    void shouldFindWordById() {
        
        // Arrange
        Word word = new Word();
        word.setWord("LIVRO");
        word.setLanguage("pt-BR");
        word.setCategory("geral");
        word.setDifficulty("EASY");

         
        when(wordRepository.findById(1L))
            .thenReturn(java.util.Optional.of(word));

        WordResponse response = wordService.findById(1L);

        assertThat(response.word()).isEqualTo(word.getWord());

        verify(wordRepository).findById(1L);
    }

    @Test 
    void shouldThrowExceptionWhenWordNotFound() {

        when(wordRepository.findById(999L))
            .thenReturn(java.util.Optional.empty());

        assertThatThrownBy(
            () -> wordService.findById(999L)
        )
            .isInstanceOf(br.com.termoword.exception.WordNotFoundException.class)
            .hasMessage("Palavra nao encontrada com o id:999");
        
        verify(wordRepository).findById(999L);
    }
}
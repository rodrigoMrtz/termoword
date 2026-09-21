package br.com.termoword.service;

import br.com.termoword.entity.Word;
import br.com.termoword.dto.word.WordResponse;
import br.com.termoword.dto.word.CreateWordRequest;
import br.com.termoword.repository.WordRepository;

import org.springframework.stereotype.Service;

@Service 
public class WordService {

    private final WordRepository wordrepository;

    public WordService(WordRepository wordrepository) {
        this.wordrepository = wordrepository;
    }

    public WordResponse create(CreateWordRequest request) {
        Word word = new Word();
        word.setWord(request.word());
        word.setLanguage(request.language());
        word.setCategory(request.category());
        word.setDifficulty(request.difficulty());
        
        Word savedWord = wordrepository.save(word);

        return WordResponse.fromEntity(savedWord);
    }
}

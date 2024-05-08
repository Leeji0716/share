package com.example.ms1.note.notebook;

import com.example.ms1.note.MainDataDto;
import com.example.ms1.note.note.Note;
import com.example.ms1.note.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotebookService {
    private final NotebookRepository notebookRepository;
    public List<Notebook> getNotebookList(){
        List<Notebook> notebookList = notebookRepository.findAll();
        return notebookList;
    }

    public Notebook getNotebook(long id){
        Notebook notebook = notebookRepository.findById(id).get();
        return notebook;
    }

    public void save(Notebook notebook) {
        notebookRepository.save(notebook);
    }
}

package com.example.ms1.note.notebook;

import com.example.ms1.note.MainDataDto;
import com.example.ms1.note.note.Note;
import com.example.ms1.note.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Notebook save(Notebook notebook) {
        return notebookRepository.save(notebook);
    }

    public void delete(Notebook notebook) {
        notebookRepository.delete(notebook);
    }

    public void update(Long id, String targetNotebookName) {
        Notebook notebook = getNotebook(id);
        notebook.setName(targetNotebookName);
        notebookRepository.save(notebook);
    }

    public List<Notebook> getTopNotebookList() {
        List<Notebook> notebookList = notebookRepository.findAll();
        List<Notebook> topNotebookList = new ArrayList<>();
        for (Notebook notebook : notebookList){
            if(notebook.getParent() == null){
                topNotebookList.add(notebook);
            }
        }
        return topNotebookList;
    }

    public void move(Long id, Long moveTarget) {
        Notebook notebook = getNotebook(id);
        Notebook targetNote = getNotebook(moveTarget);

        notebook.setParent(targetNote);
        notebookRepository.save(notebook);
    }
}

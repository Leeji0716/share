package com.example.ms1.note.notebook;

import com.example.ms1.note.MainDataDto;
import com.example.ms1.note.note.Note;
import com.example.ms1.note.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Note> getNoteList(Notebook notebook, String sort) {
        List<Note> noteList = notebook.getNoteList();
        if ("Date".equals(sort)) {
            noteList = noteList.stream()
                    .sorted(Comparator.comparing(Note::getCreateDate).reversed())
                    .collect(Collectors.toList());
        }else if ("Title".equals(sort)){
            noteList = noteList.stream()
                    .sorted(Comparator.comparing(Note::getTitle))
                    .collect(Collectors.toList());
        }
        return noteList;
    }

    public List<Notebook> getNotebookListByKeyword(String keyword) {
        return notebookRepository.findByNameContaining(keyword);
    }
}

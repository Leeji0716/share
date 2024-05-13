package com.example.ms1.note;

import com.example.ms1.note.note.Note;
import com.example.ms1.note.note.NoteService;
import com.example.ms1.note.notebook.Notebook;
import com.example.ms1.note.notebook.NotebookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final NoteService noteService;
    private final NotebookService notebookService;

    public MainDataDto defaultMainDataDto(String keyword){
        List<Notebook> notebookList = notebookService.getTopNotebookList();
        if (notebookList.isEmpty()) {
            notebookList.add(this.saveDefaultNotebook());
        }
        Notebook targetNotebook = notebookList.get(0);
        List<Note> noteList = targetNotebook.getNoteList();
        Note targetNote = noteList.get(0);

        List<Notebook> searchedNotebookList = notebookService.getNotebookListByKeyword(keyword);
        List<Note> searchedNoteList = noteService.getNoteListByKeyword(keyword);

        MainDataDto mainDataDto = new MainDataDto(notebookList, targetNotebook, noteList, targetNote, searchedNotebookList, searchedNoteList);
        return mainDataDto;
    }

    public MainDataDto mainDataDto(Long notebookId, Long id, String keyword, String sort){
        MainDataDto mainDataDto = this.defaultMainDataDto(keyword);
        Notebook targetNotebook = notebookService.getNotebook(notebookId);
        Note targetNote = noteService.getNote(id);

        mainDataDto.setTargetNotebook(targetNotebook);
        mainDataDto.setTargetNote(targetNote);

        List<Note> sortedNoteList = new ArrayList<>();
        if(sort.equals("Date")){
            sortedNoteList = noteService.getSortedListByCreateDate(targetNotebook);
        }else{
            sortedNoteList = noteService.getSortedListByTitle(targetNotebook);
        }
        mainDataDto.setNoteList(sortedNoteList);

        return mainDataDto;
    }

    public Notebook getNotebook(long id){
        Notebook notebook = notebookService.getNotebook(id);
        return notebook;
    }

    public List<Notebook> getNotebookList(){
        List<Notebook> notebookList = notebookService.getNotebookList();
        return notebookList;
    }

    public Notebook saveDefaultNotebook(){
        Notebook notebook = new Notebook();
        notebook.setName("새노트북");

        Note note = noteService.saveDefaultNote();
        notebook.addNote(note);
        notebookService.save(notebook);

        return notebook;
    }

    public void saveGroupNotebook(long notebookId){
        Notebook parent = notebookService.getNotebook(notebookId);

        Notebook child = this.saveDefaultNotebook();

        parent.addChild(child);
        notebookService.save(parent);
    }
    public Notebook addNotebook(long notebookId){
        Notebook notebook = this.notebookService.getNotebook(notebookId);
        Note note = noteService.saveDefaultNote();
        notebook.addNote(note);

        return notebookService.save(notebook);
    }
}

package com.example.ms1.note;

import com.example.ms1.note.note.Note;
import com.example.ms1.note.note.NoteService;
import com.example.ms1.note.notebook.Notebook;
import com.example.ms1.note.notebook.NotebookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {
    private final NoteService noteService;
    private final NotebookService notebookService;

    public MainDataDto defaultMainDataDto(){
        List<Notebook> notebookList = this.getNotebookList();
        if (notebookList.isEmpty()) {
            notebookList.add(this.saveDefaultNotebook());
        }
        Notebook targetNotebook = notebookList.get(0);
        List<Note> noteList = targetNotebook.getNoteList();
        Note targetNote = noteList.get(0);

        MainDataDto mainDataDto = new MainDataDto(notebookList, targetNotebook, noteList, targetNote);
        return mainDataDto;
    }

    public MainDataDto mainDataDto(Long notebookId, Long id){
        MainDataDto mainDataDto = this.defaultMainDataDto();
        Notebook targetNotebook = notebookService.getNotebook(notebookId);
        Note targetNote = noteService.getNote(id);
        List<Note> noteList = targetNotebook.getNoteList();

        mainDataDto.setTargetNotebook(targetNotebook);
        mainDataDto.setTargetNote(targetNote);
        mainDataDto.setNoteList(noteList);

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

//    public void delete(Notebook notebook){
//        List<Note> noteList = notebook.getNoteList();
//        for (Note note : noteList){
//            noteService.delete(note);
//        }
//        notebookService.delete(notebook);
//    }
}

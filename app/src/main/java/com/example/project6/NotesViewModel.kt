package com.example.project6;
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NotesViewModel(val dao: NoteDao) : ViewModel() {
    var newNoteName = ""
    val notes = dao.getAll()
    private val _navigateToNote = MutableLiveData<Long?>()
    val navigateToTask: LiveData<Long?>
        get() = _navigateToNote

    fun addNote() {
        viewModelScope.launch {
            val note = Note()
            note.noteTitle = newNoteName
            _navigateToNote.value = note.noteId
            dao.insert(note)
        }
    }

    fun onNoteClicked(noteId: Long) {
        _navigateToNote.value = noteId
    }

    fun onNoteNavigated() {
        _navigateToNote.value = null
    }

    fun deleteNote(noteId: Long) {
        viewModelScope.launch {
            dao.delete(dao.get(noteId).value!!)
        }
    }
}
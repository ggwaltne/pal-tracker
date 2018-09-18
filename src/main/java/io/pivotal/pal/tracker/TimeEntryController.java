package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {


    private TimeEntryRepository inMemoryTimeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        inMemoryTimeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
       return new ResponseEntity<TimeEntry>(inMemoryTimeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable long id) {
        TimeEntry expected = inMemoryTimeEntryRepository.find(id);

        if(expected == null) {
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<TimeEntry>(expected, HttpStatus.OK);
        }
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable long id,@RequestBody TimeEntry timeEntry) {
        TimeEntry expected = inMemoryTimeEntryRepository.update(id, timeEntry);

        if(expected == null) {
            return new ResponseEntity( HttpStatus.NOT_FOUND);
        }
        else
        {
            return new ResponseEntity<TimeEntry>(expected, HttpStatus.OK);
        }
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable long id) {

        inMemoryTimeEntryRepository.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);


    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {

        return new ResponseEntity<List<TimeEntry>>(inMemoryTimeEntryRepository.list(), HttpStatus.OK);
    }

}

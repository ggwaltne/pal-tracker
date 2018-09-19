package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    private final CounterService counter;
    private final GaugeService gage;
    private TimeEntryRepository inMemoryTimeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, CounterService counter, GaugeService gage) {
        this.inMemoryTimeEntryRepository = timeEntryRepository;
        this.counter = counter;
        this.gage = gage;
    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntryToCreate) {
        counter.increment("TimeEntry.created");
        gage.submit("timeEntries.count", inMemoryTimeEntryRepository.list().size());
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
            counter.increment("TimeEntry.found");
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
            counter.increment("TimeEntry.updated");
            return new ResponseEntity<TimeEntry>(expected, HttpStatus.OK);
        }
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity delete(@PathVariable long id) {
        inMemoryTimeEntryRepository.delete(id);
        counter.increment("TimeEntry.deleted");
        gage.submit("timeEntries.count", inMemoryTimeEntryRepository.list().size());
        return new ResponseEntity(HttpStatus.NO_CONTENT);


    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() {
        counter.increment("TimeEntry.listed");
        return new ResponseEntity<List<TimeEntry>>(inMemoryTimeEntryRepository.list(), HttpStatus.OK);
    }

}

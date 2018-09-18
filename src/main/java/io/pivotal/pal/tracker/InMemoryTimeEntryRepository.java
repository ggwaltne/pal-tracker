package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository  implements TimeEntryRepository{

    Map<Long, TimeEntry> repo = new HashMap<Long, TimeEntry>();


    public TimeEntry create(TimeEntry timeEntry) {

        timeEntry.setId(Long.valueOf(repo.size() +1));
        repo.put(timeEntry.getId(),timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return repo.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<TimeEntry>(repo.values());

    }

    @Override
    public TimeEntry update(long id, TimeEntry any) {
        if(repo.containsKey(id)) {
            any.setId(id);
            repo.put(id, any);
            return any;
        }
        else
        {
            return null;
        }

    }

    @Override
    public void delete(long id) {
        repo.remove(id);
    }


}

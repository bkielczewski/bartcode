package uk.co.bartcode.service.filesystem;

import java.nio.file.WatchEvent;

import static java.nio.file.StandardWatchEventKinds.*;

public enum EventType {
    CREATE, DELETE, IGNORE, MODIFY;

    public static EventType valueOf(WatchEvent.Kind<?> kind) {
        if (kind == ENTRY_CREATE) {
            return CREATE;
        } else if (kind == ENTRY_MODIFY) {
            return MODIFY;
        } else if (kind == ENTRY_DELETE) {
            return DELETE;
        } else {
            return IGNORE;
        }
    }
}


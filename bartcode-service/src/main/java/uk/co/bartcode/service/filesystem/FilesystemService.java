package uk.co.bartcode.service.filesystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.nio.file.WatchEvent;

import static java.nio.file.StandardWatchEventKinds.*;
import static uk.co.bartcode.service.filesystem.FilesystemChangeEvent.Type.*;

@Service
class FilesystemService {

    private static final String EXTENSION = "md";
    private final FilesystemSeekerService seekerService;
    private final FilesystemWatcherService watcherService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${application.data-path}")
    private String dataPath;

    @Autowired
    FilesystemService(FilesystemSeekerService seekerService,
                      FilesystemWatcherService watcherService,
                      ApplicationEventPublisher eventPublisher) {
        this.seekerService = seekerService;
        this.watcherService = watcherService;
        this.eventPublisher = eventPublisher;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onContextRefreshedEvent() {
        scanExistingFiles();
        doWatchDirectoryForChanges();
    }

    private void doWatchDirectoryForChanges() {
        watcherService.startWatchThread(dataPath, (target, kind) -> eventPublisher.publishEvent(
                new FilesystemChangeEvent(this, target, getType(kind))));
    }

    private void scanExistingFiles() {
        seekerService.seekFilesByExtension(EXTENSION, dataPath)
                .forEach(file -> eventPublisher.publishEvent(
                        new FilesystemChangeEvent(this, file, CREATE)));
    }

    private FilesystemChangeEvent.Type getType(WatchEvent.Kind<?> kind) {
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

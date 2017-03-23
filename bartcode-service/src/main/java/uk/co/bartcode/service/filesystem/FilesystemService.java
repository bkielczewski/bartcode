package uk.co.bartcode.service.filesystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import static uk.co.bartcode.service.filesystem.EventType.CREATE;

@Service
class FilesystemService {

    private static final String EXTENSION = "md";
    private final FilesystemSeekerService seekerService;
    private final FilesystemWatcherService watcherService;
    private final ApplicationEventPublisher eventPublisher;
    private final String dataPath;

    @Autowired
    FilesystemService(FilesystemSeekerService seekerService,
                      FilesystemWatcherService watcherService,
                      ApplicationEventPublisher eventPublisher,
                      @Value("${application.data-path}") String dataPath) {
        this.seekerService = seekerService;
        this.watcherService = watcherService;
        this.eventPublisher = eventPublisher;
        this.dataPath = dataPath;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onContextRefreshedEvent() {
        scanExistingFiles();
        doWatchDirectoryForChanges();
    }

    private void doWatchDirectoryForChanges() {
        watcherService.startWatchThread(dataPath, this::handleWatchEvent);
    }

    private void handleWatchEvent(FilesystemChangeEvent event) {
        if (event.isDirectory() && event.getType().equals(CREATE)) {
            seekerService.seekFilesByExtension(EXTENSION, event.getPath(), eventPublisher::publishEvent);
        }
        eventPublisher.publishEvent(event);
    }

    private void scanExistingFiles() {
        seekerService.seekFilesByExtension(EXTENSION, dataPath, eventPublisher::publishEvent);
    }

}

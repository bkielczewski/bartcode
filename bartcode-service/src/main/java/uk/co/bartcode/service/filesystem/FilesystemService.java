package uk.co.bartcode.service.filesystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

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
        boolean isDirectory = Files.isDirectory(Paths.get(event.getPath()), LinkOption.NOFOLLOW_LINKS);
        if (isDirectory && event.getType().equals(CREATE)) {
            seekerService.seekFilesByExtension(EXTENSION, event.getPath(), eventPublisher::publishEvent);
        }
        eventPublisher.publishEvent(event);
    }

    private void scanExistingFiles() {
        seekerService.seekFilesByExtension(EXTENSION, dataPath, eventPublisher::publishEvent);
    }

}

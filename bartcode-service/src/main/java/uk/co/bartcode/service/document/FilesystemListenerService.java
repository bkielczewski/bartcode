package uk.co.bartcode.service.document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import uk.co.bartcode.service.filesystem.FilesystemChangeEvent;

import java.util.Collection;

import static uk.co.bartcode.service.filesystem.EventType.*;

@Service
class FilesystemListenerService {

    private static final Logger logger = LoggerFactory.getLogger(FilesystemListenerService.class);

    private final Collection<DocumentFactory> documentFactories;
    private final DocumentRepository documentRepository;

    @Autowired
    FilesystemListenerService(Collection<DocumentFactory> documentFactories,
                              DocumentRepository documentRepository) {
        this.documentFactories = documentFactories;
        this.documentRepository = documentRepository;
    }

    @EventListener(FilesystemChangeEvent.class)
    void onFilesystemChangeEvent(FilesystemChangeEvent event) {
        logger.debug("Handling, event={}", event);
        if (event.isDirectory() && event.getType().equals(DELETE)) {
            logger.trace("Deleting, directory={}", event.getPath());
            documentRepository.deleteByFileStartingWith(event.getPath());
        } else if (event.getType().equals(CREATE) || event.getType().equals(MODIFY)) {
            documentRepository.getByFile(event.getPath()).ifPresent(document -> {
                logger.trace("Deleting, id={}", document.getId());
                documentRepository.delete(document);
            });
            doImport(event.getPath());
        }
    }

    private void doImport(String file) {
        DocumentFactory documentFactory = documentFactories.stream()
                .filter(factory -> factory.supports(file))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No appropriate factory found, file=" + file));
        documentRepository.save(documentFactory.create(file));
    }

}
package uk.co.bartcode.service.filesystem

import org.springframework.context.event.EventListener

internal interface FilesystemEventHandler {

    @EventListener(DirectoryDeletedEvent::class)
    fun handleDirectoryDeletedEvent(event: DirectoryDeletedEvent)

    @EventListener(FileCreatedEvent::class)
    fun handleFileCreatedEvent(event: FileCreatedEvent)

    @EventListener(FileModifiedEvent::class)
    fun handleFileModifiedEvent(event: FileModifiedEvent)

    @EventListener(FileDeletedEvent::class)
    fun handleFileDeletedEvent(event: FileDeletedEvent)

}
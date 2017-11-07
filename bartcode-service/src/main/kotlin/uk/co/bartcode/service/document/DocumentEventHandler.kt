package uk.co.bartcode.service.document

import org.springframework.context.event.EventListener
import uk.co.bartcode.service.filesystem.DirectoryDeletedEvent
import uk.co.bartcode.service.filesystem.FileCreatedEvent
import uk.co.bartcode.service.filesystem.FileDeletedEvent
import uk.co.bartcode.service.filesystem.FileModifiedEvent

internal interface DocumentEventHandler {

    @EventListener(DirectoryDeletedEvent::class)
    fun handleDirectoryDeletedEvent(event: DirectoryDeletedEvent)

    @EventListener(FileCreatedEvent::class)
    fun handleFileCreatedEvent(event: FileCreatedEvent)

    @EventListener(FileModifiedEvent::class)
    fun handleFileModifiedEvent(event: FileModifiedEvent)

    @EventListener(FileDeletedEvent::class)
    fun handleFileDeletedEvent(event: FileDeletedEvent)

}
package uk.co.bartcode.service.filesystem

import org.springframework.context.ApplicationEvent

sealed class FilesystemEvent(source: Any, val path: String) : ApplicationEvent(source) {

    override fun toString(): String {
        return "FilesystemEvent(path='$path')"
    }

}

class FileCreatedEvent(source: Any, path: String) : FilesystemEvent(source, path)
class FileModifiedEvent(source: Any, path: String) : FilesystemEvent(source, path)
class FileDeletedEvent(source: Any, path: String) : FilesystemEvent(source, path)
class DirectoryDeletedEvent(source: Any, path: String) : FilesystemEvent(source, path)
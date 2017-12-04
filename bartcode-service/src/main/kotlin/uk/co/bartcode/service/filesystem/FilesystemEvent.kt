package uk.co.bartcode.service.filesystem

import org.springframework.context.ApplicationEvent

sealed class FilesystemEvent(source: Any, val path: String) : ApplicationEvent(source)

class FileCreatedEvent(source: Any, path: String) : FilesystemEvent(source, path) {
    override fun toString(): String {
        return "FileCreatedEvent(path='$path')"
    }
}

class FileModifiedEvent(source: Any, path: String) : FilesystemEvent(source, path) {
    override fun toString(): String {
        return "FileModifiedEvent(path='$path')"
    }
}

class FileDeletedEvent(source: Any, path: String) : FilesystemEvent(source, path) {
    override fun toString(): String {
        return "FileDeletedEvent(path='$path')"
    }
}

class DirectoryDeletedEvent(source: Any, path: String) : FilesystemEvent(source, path) {
    override fun toString(): String {
        return "DirectoryDeletedEvent(path='$path')"
    }
}

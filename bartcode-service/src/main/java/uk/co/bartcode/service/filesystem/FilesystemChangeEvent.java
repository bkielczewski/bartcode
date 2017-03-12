package uk.co.bartcode.service.filesystem;

import com.google.common.base.MoreObjects;
import org.springframework.context.ApplicationEvent;

public class FilesystemChangeEvent extends ApplicationEvent {

    private final String path;
    private final boolean isDirectory;
    private final EventType type;

    FilesystemChangeEvent(Object source, String path, boolean isDirectory, EventType type) {
        super(source);
        this.path = path;
        this.isDirectory = isDirectory;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public EventType getType() {
        return type;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("target", path)
                .add("isDirectory", isDirectory)
                .add("type", type)
                .toString();
    }

}
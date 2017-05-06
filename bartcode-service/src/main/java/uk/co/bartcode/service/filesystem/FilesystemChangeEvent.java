package uk.co.bartcode.service.filesystem;

import com.google.common.base.MoreObjects;
import org.springframework.context.ApplicationEvent;

public class FilesystemChangeEvent extends ApplicationEvent {

    private final String path;
    private final EventType type;

    FilesystemChangeEvent(Object source, String path, EventType type) {
        super(source);
        this.path = path;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public EventType getType() {
        return type;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("target", path)
                .add("type", type)
                .toString();
    }

}
package uk.co.bartcode.service.filesystem;

import com.google.common.base.MoreObjects;
import org.springframework.context.ApplicationEvent;

public class FilesystemChangeEvent extends ApplicationEvent {

    private final String target;
    private final Type type;

    FilesystemChangeEvent(Object source, String target, Type type) {
        super(source);
        this.target = target;
        this.type = type;
    }

    public String getTarget() {
        return target;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("target", target)
                .add("type", type)
                .toString();
    }

    public enum Type {
        CREATE, DELETE, IGNORE, MODIFY
    }

}
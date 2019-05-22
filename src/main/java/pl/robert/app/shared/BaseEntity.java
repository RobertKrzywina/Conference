package pl.robert.app.shared;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.UUID;
import java.util.Objects;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    String uuid = UUID.randomUUID().toString();

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public boolean equals(Object that) {
        return this == that || that instanceof BaseEntity
                    && Objects.equals(uuid, ((BaseEntity) that).uuid);
    }
}

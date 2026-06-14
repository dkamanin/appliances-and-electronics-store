package io.hexlet.maconi.shared.identifiers;

import io.hexlet.maconi.shared.exceptions.DomainValidationException;
import org.jspecify.annotations.NonNull;

import java.util.UUID;

public record ImageId(UUID value) implements DomainIdentifier<UUID> {
    public ImageId {
        if (value == null) {
            throw new DomainValidationException("Image identifier must not be null");
        }
    }

    public static ImageId generate() {
        return new ImageId(UUID.randomUUID());
    }

    public static ImageId of(@NonNull String value) {
        if (value.isBlank()) {
            throw new DomainValidationException("Image identifier must not be blank");
        }
        try {
            return new ImageId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new DomainValidationException("Malformed UUID: " + value, e);
        }
    }
}

package com.fdlv.gmd.api.dto;

import com.fdlv.gmd.api.domain.Event;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.Theme} entity.
 */
public class ThemeDTO implements Serializable {

    private Long id;

    @NotNull
    private String label;

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    private Set<Event> events = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ThemeDTO)) {
            return false;
        }

        ThemeDTO themeDTO = (ThemeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, themeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ThemeDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            "}";
    }
}

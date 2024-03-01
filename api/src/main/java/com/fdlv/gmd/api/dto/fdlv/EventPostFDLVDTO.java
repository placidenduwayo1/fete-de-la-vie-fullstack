package com.fdlv.gmd.api.dto.fdlv;

import com.fdlv.gmd.api.domain.fdlv.ScenarioEtape;
import com.fdlv.gmd.api.dto.EventDTO;

import java.util.List;
import java.util.Objects;

/**
 * DTO to manage api /event/add from FDLV website
 */

public class EventPostFDLVDTO {
    private EventFDLVDTO event;
    private List<ScenarioEtapeDTO> stage;

    public EventFDLVDTO getEvent() {
        return event;
    }

    public void setEvent(EventFDLVDTO event) {
        this.event = event;
    }

    public List<ScenarioEtapeDTO> getStage() {
        return stage;
    }

    public void setStage(List<ScenarioEtapeDTO> stage) {
        this.stage = stage;
    }

    @Override
    public String toString() {
        return "EventPostFDLVDTO{" +
                "event=" + event +
                ", stage=" + stage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventPostFDLVDTO that = (EventPostFDLVDTO) o;
        return Objects.equals(event, that.event) && Objects.equals(stage, that.stage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, stage);
    }
}

package com.fdlv.gmd.api.dto;

import com.fdlv.gmd.api.dto.fdlv.ListeVideosThemeDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.fdlv.gmd.api.domain.Stage} entity.
 */

public class StageCustomDTO implements Serializable {

    private Long id;

    @NotNull
    private String label;


    public ListeVideosThemeDTO getVideo() {
        return video;
    }

    public void setVideo(ListeVideosThemeDTO video) {
        this.video = video;
    }

    private ListeVideosThemeDTO video;


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

}

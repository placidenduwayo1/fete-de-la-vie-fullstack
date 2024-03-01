package com.fdlv.gmd.api.web.rest;

import com.fdlv.gmd.api.dto.fdlv.FdlvVideoDTO;
import com.fdlv.gmd.api.dto.fdlv.ListeVideosThemeDTO;
import com.fdlv.gmd.api.service.FtpService;
import com.fdlv.gmd.api.service.fdlv.ListeVideosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.fdlv.ListeVideos} with the same adress as NodeJS API.
 */
@RestController
@RequestMapping("/api/fdlvVideo")
public class FdlvVideoResource {

    private final Logger log = LoggerFactory.getLogger(ListeVideosResource.class);

    private static final String ENTITY_NAME = "listeVideos";

    private final ListeVideosService listeVideosService;
    private final FtpService ftpService;


    public FdlvVideoResource(ListeVideosService listeVideosService, FtpService ftpService) {
        this.listeVideosService = listeVideosService;
        this.ftpService = ftpService;
    }

    /**
     * {@code GET  /fdlvVideo/displayable} : get the displayable list of Video.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listeVideoss in body.
     */
    @GetMapping("/displayable")
    public ResponseEntity<List<FdlvVideoDTO>>getAllListeVideoss() {
        log.debug("REST request to get all ListeVideoss");
        return ResponseEntity.ok(this.listeVideosService.findDisplayable());
    }
}

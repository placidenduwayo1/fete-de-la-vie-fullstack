package com.fdlv.gmd.api.web.rest;

import com.fdlv.gmd.api.dto.MediaResponse;
import com.fdlv.gmd.api.dto.fdlv.ListeVideosThemeDTO;
import com.fdlv.gmd.api.service.FtpService;
import com.fdlv.gmd.api.service.fdlv.ListeVideosService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.fdlv.ListeVideos}.
 */
@RestController
@RequestMapping("/api/liste-videos")
public class ListeVideosResource {

    private final Logger log = LoggerFactory.getLogger(ListeVideosResource.class);

    private static final String ENTITY_NAME = "listeVideos";

    private final ListeVideosService listeVideosService;
    private final FtpService ftpService;


    public ListeVideosResource(ListeVideosService listeVideosService, FtpService ftpService) {
        this.listeVideosService = listeVideosService;
        this.ftpService = ftpService;
    }

    /**
     * {@code POST  /liste-videos} : Create a new listeVideos.
     *
     * @param listeVideosDTO the listeVideosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new listeVideosDTO, or with status {@code 400 (Bad Request)} if the listeVideos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<ListeVideosThemeDTO> createListeVideos(@Valid @RequestBody ListeVideosThemeDTO listeVideosDTO) throws URISyntaxException, MalformedURLException {
        log.debug("REST request to save ListeVideos : {}", listeVideosDTO);

        ListeVideosThemeDTO inter = listeVideosService.save(listeVideosDTO);

        String originalVideoURL = inter.getUrlVideo();
        String newVideoUrl = modifyUrl(originalVideoURL, inter.getId().toString());

        String originalImageURL = inter.getUrlImage();
        String newImageUrl = modifyUrl(originalImageURL, inter.getId().toString());

        inter.setUrlVideo(newVideoUrl);
        inter.setUrlImage(newImageUrl);

        ListeVideosThemeDTO result = listeVideosService.partialUpdate(inter).orElseThrow(RuntimeException::new);

        modifyUrlAndRenameFile(originalVideoURL, newVideoUrl);
        modifyUrlAndRenameFile(originalImageURL, newImageUrl);

        return ResponseEntity
                .created(new URI("/api/liste-videos/" + result.getId()))
                .body(result);
    }

    private void modifyUrlAndRenameFile(String originalUrl, String newUrl) throws MalformedURLException {
        String fromPath = new URL(originalUrl).getPath();
        String toPath = new URL(newUrl).getPath();

        ftpService.renameFile(fromPath, toPath);
    }

    public String modifyUrl(String url, String valueToAdd) {
        int index = url.lastIndexOf("/");
        return url.substring(0, index + 1) + valueToAdd + "_" + url.substring(index + 1);
    }

    /**
     * {@code PUT  /:id} : Updates an existing listeVideos.
     *
     * @param id the id of the listeVideosDTO to save.
     * @param listeVideosDTO the listeVideosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listeVideosDTO,
     * or with status {@code 400 (Bad Request)} if the listeVideosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the listeVideosDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ListeVideosThemeDTO> updateListeVideos(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ListeVideosThemeDTO listeVideosDTO
    ) {
        log.debug("REST request to update ListeVideos : {}, {}", id, listeVideosDTO);
        if (listeVideosDTO.getId() == null
                || !Objects.equals(id, listeVideosDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!listeVideosService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        ListeVideosThemeDTO result = listeVideosService.save(listeVideosDTO);
        return ResponseEntity
            .ok()
            .body(result);
    }

    /**
     * {@code PATCH  /liste-videos/:id} : Partial updates given fields of an existing listeVideos, field will ignore if it is null
     *
     * @param id the id of the listeVideosDTO to save.
     * @param listeVideosDTO the listeVideosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated listeVideosDTO,
     * or with status {@code 400 (Bad Request)} if the listeVideosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the listeVideosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the listeVideosDTO couldn't be updated.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ListeVideosThemeDTO> partialUpdateListeVideos(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ListeVideosThemeDTO listeVideosDTO
    ) {
        log.debug("REST request to partial update ListeVideos partially : {}, {}", id, listeVideosDTO);
        if (listeVideosDTO.getId() == null
                || !Objects.equals(id, listeVideosDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!listeVideosService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<ListeVideosThemeDTO> result = listeVideosService.partialUpdate(listeVideosDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /liste-videos} : get all the listeVideoss.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of listeVideoss in body.
     */
    @GetMapping()
    public List<ListeVideosThemeDTO> getAllListeVideoss() {
        log.debug("REST request to get all ListeVideoss");
        return listeVideosService.findAll();
    }

    /**
     * {@code GET  /liste-videos/:id} : get the "id" listeVideos.
     *
     * @param id the id of the listeVideosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the listeVideosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ListeVideosThemeDTO> getListeVideos(@PathVariable Long id) {
        log.debug("REST request to get ListeVideos : {}", id);
        Optional<ListeVideosThemeDTO> listeVideosDTO = listeVideosService.findOne(id);
        log.debug("DATA_INFOS {}", listeVideosDTO);
        return HttpUtils.wrapOrNotFound(listeVideosDTO);

    }

    /**
     * {@code DELETE  /liste-videos/:id} : delete the "id" listeVideos.
     *
     * @param id the id of the listeVideosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hardDeleteListeVideos(@PathVariable Long id) {
        log.debug("REST request to delete ListeVideos : {}", id);
        Optional<ListeVideosThemeDTO> opt = listeVideosService.findOne(id);
        if (opt.isPresent()) {
            ListeVideosThemeDTO video = opt.get();
            if (video.getUrlVideo() != null) {
                this.ftpService.deleteFromServer(video.getUrlVideo());
            }
            if (video.getUrlImage() != null) {
                this.ftpService.deleteFromServer(video.getUrlImage());
            }
        }
        listeVideosService.hardDelete(id);
        return ResponseEntity
            .noContent() 
            .build();
    }

    /**
     * {@code DELETE  /liste-videos/:id} : delete the "id" listeVideos.
     *
     * @param id the id of the listeVideosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/soft/{id}")
    public ResponseEntity<Void> softDeleteListeVideos(@PathVariable Long id) {
        log.debug("REST request to delete ListeVideos : {}", id);
        listeVideosService.softDelete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    /**
     * {@code PUT  /:id} : Updates an existing listeVideos.
     *
     * @param id the id of the quizzIDlist to save.
     * @param quizzIDlist list of id of quizz.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quizzIDlist,
     * or with status {@code 400 (Bad Request)} if the quizzIDlist is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quizzIDlist couldn't be updated.
     */
    @PutMapping("/{id}/quizz")
    public ResponseEntity<ListeVideosThemeDTO> updateVideoQuizz(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody List<Long> quizzIDlist 
    ) {
        log.debug("REST request to update VideoQuizz : {}, {}", id, quizzIDlist);
    if(quizzIDlist != null){
        log.debug("Different");
    }
        ListeVideosThemeDTO result = listeVideosService.updateVideoQuizz(id, quizzIDlist);
        return ResponseEntity
            .ok()
            .body(result);
    }

    @PostMapping("/media/")
    public ResponseEntity<MediaResponse> postMediaOnServer(@RequestParam(value = "video") MultipartFile video, @RequestParam(value = "image") MultipartFile image) {
        log.debug("REST request to upload video / image in /api/liste-video/media");
        MediaResponse result = new MediaResponse();
        result.setVideoUrl(ftpService.putMediaListeVideo(video));
        result.setImageUrl(ftpService.putThumbnailListeVideo(image));
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /videos/by-url/:url} : get the video with the given URL.
     *
     * @param body contains the URL of the video to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the video, or with status {@code 404 (Not Found)}.
     */
    @PostMapping("/findByUrl")
    public ResponseEntity<ListeVideosThemeDTO> getVideoByUrl(@RequestBody Map<String, String> body) {
        String url = body.get("url");
        log.debug("REST request to get video by URL: {}", url);
        Optional<ListeVideosThemeDTO> video = listeVideosService.findByUrl(url);
        return HttpUtils.wrapOrNotFound(video);
    }

    /**
     * {@code GET /liste-videos/max-id} : get the maximum media ID.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the maximum media ID, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/max-id")
    public ResponseEntity<Long> getMaxMediaId() {
        Long maxId = listeVideosService.getMaxMediaId();
        return ResponseEntity.ok().body(maxId);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Boolean> isVideoUsed(@PathVariable Long id) {
//        log.debug("REST request to Check if Video is Used ListeVideos : {}", id);
//        return ResponseEntity.ok().body(listeVideosService.isVideoUsed(id));
//    }
}

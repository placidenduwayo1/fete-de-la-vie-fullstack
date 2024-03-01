package com.fdlv.gmd.api.web.rest;


import com.fdlv.gmd.api.dto.MediaResponse;
import com.fdlv.gmd.api.service.FtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/medias")
@RequiredArgsConstructor
@Slf4j
public class MediaResource {

    private final FtpService ftpService;

    @PostMapping
    public ResponseEntity<MediaResponse> addMedia(@RequestParam(value = "media") MultipartFile media,
                                                  @RequestParam(value = "name") String name,
                                                  @RequestParam(value = "path") String path) {
        log.debug("REST request to upload video / image in /api/medias");
        return ResponseEntity.ok(MediaResponse.builder()
                .mediaUrl(ftpService.uploadFileToFTPGivenFullPath(media, path, name))
                .build()
        );
    }
}

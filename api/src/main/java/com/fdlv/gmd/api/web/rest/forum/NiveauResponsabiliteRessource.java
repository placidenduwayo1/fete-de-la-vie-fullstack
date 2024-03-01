package com.fdlv.gmd.api.web.rest.forum;

import com.fdlv.gmd.api.dto.forum.NiveauResponsabiliteDTO;
import com.fdlv.gmd.api.service.forum.NiveauResponsabiliteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/niveauResponsabilites")
public class NiveauResponsabiliteRessource {
    private final NiveauResponsabiliteService niveauResponsabiliteService;
    @GetMapping()
    public List<NiveauResponsabiliteDTO> getAllResponsibilities(){
        return niveauResponsabiliteService.getAllResponsibilityLevel();
    }
    @GetMapping("/{level}")
    public List<NiveauResponsabiliteDTO> getNiveauResponsabiliteByLevel(@PathVariable(name = "level") char level) {
        return niveauResponsabiliteService.getNiveauResponsabiliteByLevel(level);
    }
}

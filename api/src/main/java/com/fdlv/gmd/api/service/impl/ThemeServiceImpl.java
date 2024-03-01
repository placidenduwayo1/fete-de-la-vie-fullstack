package com.fdlv.gmd.api.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fdlv.gmd.api.domain.Theme;
import com.fdlv.gmd.api.dto.ThemeDTO;
import com.fdlv.gmd.api.mapper.ThemeMapper;
import com.fdlv.gmd.api.repository.ThemeRepository;
import com.fdlv.gmd.api.service.ThemeService;

/**
 * Service Implementation for managing {@link Theme}.
 */
@Service
@Transactional
public class ThemeServiceImpl implements ThemeService {

    private final Logger log = LoggerFactory.getLogger(ThemeServiceImpl.class);

    private final ThemeRepository themeRepository;

    private final ThemeMapper themeMapper;

    public ThemeServiceImpl(ThemeRepository themeRepository, ThemeMapper themeMapper) {
        this.themeRepository = themeRepository;
        this.themeMapper = themeMapper;
    }

    @Override
    public ThemeDTO save(ThemeDTO themeDTO) {
        log.debug("Request to save Theme : {}", themeDTO);
        Theme theme = themeMapper.toEntity(themeDTO);
        theme = themeRepository.save(theme);
        return themeMapper.toDto(theme);
    }

    @Override
    public Optional<ThemeDTO> partialUpdate(ThemeDTO themeDTO) {
        log.debug("Request to partially update Theme : {}", themeDTO);

        return themeRepository
            .findById(themeDTO.getId())
            .map(
                existingTheme -> {
                    themeMapper.partialUpdate(existingTheme, themeDTO);
                    return existingTheme;
                }
            )
            .map(themeRepository::save)
            .map(themeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThemeDTO> findAll() {
        log.debug("Request to get all Themes");
        return themeRepository.findAll().stream().map(themeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThemeDTO> findOne(Long id) {
        log.debug("Request to get Theme : {}", id);
        return themeRepository.findById(id).map(themeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return themeRepository.existsById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Theme : {}", id);
        themeRepository.deleteById(id);
    }
}

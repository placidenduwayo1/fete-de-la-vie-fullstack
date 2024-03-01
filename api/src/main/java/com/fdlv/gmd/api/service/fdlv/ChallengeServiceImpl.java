package com.fdlv.gmd.api.service.fdlv;

import com.fdlv.gmd.api.domain.fdlv.Challenge;
import com.fdlv.gmd.api.repository.fdlv.ChallengeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChallengeServiceImpl implements ChallengeService{

    private final ChallengeRepository challengeRepository;

    public ChallengeServiceImpl(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    @Override
    public Challenge save(Challenge challenge) {
        return challengeRepository.save(challenge);
    }
}

package com.fdlv.gmd.api.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fdlv.gmd.api.config.AdminAuthenticationProvider;
import com.fdlv.gmd.api.domain.User;
import com.fdlv.gmd.api.domain.enumeration.Sexe;
import com.fdlv.gmd.api.domain.enumeration.TrancheAge;
import com.fdlv.gmd.api.dto.mobile.MobileAdminLoginDTO;
import com.fdlv.gmd.api.dto.mobile.MobileTypeUtilisationDTO;
import com.fdlv.gmd.api.security.DomainUserDetailsService;
import com.fdlv.gmd.api.security.jwt.TokenProvider;
import com.fdlv.gmd.api.service.InvalidPasswordException;
import com.fdlv.gmd.api.service.UserService;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fdlv.gmd.api.dto.mobile.MobileUserDTO;
import com.fdlv.gmd.api.service.mobile.MobileUserService;
import com.fdlv.gmd.api.utils.HttpUtils;
import com.fdlv.gmd.api.web.rest.errors.CreateIdException;
import com.fdlv.gmd.api.web.rest.errors.EntityNotFoundException;
import com.fdlv.gmd.api.web.rest.errors.InvalidIdException;

/**
 * REST controller for managing {@link com.fdlv.gmd.api.domain.mobile.MobileUser}.
 */
@RestController
@RequestMapping("/api/mobile-user")
public class MobileUserResource {

    private final Logger log = LoggerFactory.getLogger(MobileUserResource.class);

    private static final String ENTITY_NAME = "mobileUser";

    private final MobileUserService mobileUserService;
    private final UserService userService;



    public MobileUserResource(MobileUserService mobileUserService, UserService userService) {
        this.mobileUserService = mobileUserService;
        this.userService = userService;

    }

    /**
     * {@code POST  /mobile-user} : Create a new mobileUser.
     *
     * @param mobileUserDTO the mobileUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mobileUserDTO, or with status {@code 400 (Bad Request)} if the mobileUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping()
    public ResponseEntity<?> createMobileUser(@Valid @RequestBody MobileUserDTO mobileUserDTO) throws URISyntaxException {
        log.debug("REST request to save MobileUser : {}", mobileUserDTO);
        System.out.println("In the create mobile-user\n\n\n\n");
       if (mobileUserDTO.getId() != null) {
            throw new CreateIdException(ENTITY_NAME);
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        Long id = Long.parseLong(dateFormat.format(date));
        mobileUserDTO.setId(id);
        if(mobileUserDTO.getMus_id_user()!=null){
            mobileUserDTO.setPseudo(mobileUserDTO.getMus_pseudo());
            mobileUserDTO.setSexe(Sexe.getSexeFromValue(mobileUserDTO.getMus_sexe()));
            mobileUserDTO.setTrancheAge(TrancheAge.getTrancheAgeFromValue(mobileUserDTO.getMus_tranche_age()));
            mobileUserDTO.setId(mobileUserDTO.getMus_id_user());
        }

        MobileUserDTO result = mobileUserService.save(mobileUserDTO);
        if(mobileUserDTO.getMus_id_user()!=null){
            System.out.println("IN MOBILE \n\n\n\n");
            HashMap<String,String> s = new HashMap<>();
            s.put("message","Utilisateur mobile créé");
            return ResponseEntity.ok().body(s);
        }
        return ResponseEntity
            .created(new URI("/api/mobile-user/" + result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /:id} : Updates an existing mobileUser.
     *
     * @param id the id of the mobileUserDTO to save.
     * @param mobileUserDTO the mobileUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobileUserDTO,
     * or with status {@code 400 (Bad Request)} if the mobileUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mobileUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateMobileUser(
        @PathVariable(value = "id", required = false) final Long id,
         @RequestBody MobileUserDTO mobileUserDTO
    ) throws URISyntaxException {
        System.out.println(mobileUserDTO);
        System.out.println("In the modif mobile-user\n\n\n\n");
        if(mobileUserDTO.getMus_pseudo() != null){
            System.out.println(mobileUserDTO.getMus_pseudo());
            mobileUserDTO.setPseudo(mobileUserDTO.getMus_pseudo());
            mobileUserDTO.setSexe(Sexe.getSexeFromValue(mobileUserDTO.getMus_sexe()));
            mobileUserDTO.setTrancheAge(TrancheAge.getTrancheAgeFromValue(mobileUserDTO.getMus_tranche_age()));
            mobileUserDTO.setId(id);
        }

            log.debug("REST request to update MobileUser : {}, {}", id, mobileUserDTO);
            if (mobileUserDTO.getId() == null
                    || !Objects.equals(id, mobileUserDTO.getId())) {
                throw new InvalidIdException(ENTITY_NAME);
            }

            if (!mobileUserService.existsById(id)) {
                throw new EntityNotFoundException(ENTITY_NAME);
            }

            MobileUserDTO result = mobileUserService.save(mobileUserDTO);
            if(mobileUserDTO.getMus_pseudo()!=null){
                HashMap<String,String> s = new HashMap<>();
                s.put("message","Utilisateur mobile modifié");
                return ResponseEntity.ok().body(s);
            }
            return ResponseEntity
                    .ok()
                    .body(result);

    }



    /**
     * {@code PATCH  /mobile-user/:id} : Partial updates given fields of an existing mobileUser, field will ignore if it is null
     *
     * @param id the id of the mobileUserDTO to save.
     * @param mobileUserDTO the mobileUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mobileUserDTO,
     * or with status {@code 400 (Bad Request)} if the mobileUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mobileUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mobileUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MobileUserDTO> partialUpdateMobileUser(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MobileUserDTO mobileUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MobileUser partially : {}, {}", id, mobileUserDTO);
        if (mobileUserDTO.getId() == null
                || !Objects.equals(id, mobileUserDTO.getId())) {
            throw new InvalidIdException(ENTITY_NAME);
        }

        if (!mobileUserService.existsById(id)) {
            throw new EntityNotFoundException(ENTITY_NAME);
        }

        Optional<MobileUserDTO> result = mobileUserService.partialUpdate(mobileUserDTO);

        return HttpUtils.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /mobile-user} : get all the mobileUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mobileUsers in body.
     */
    @GetMapping()
    public List<MobileUserDTO> getAllMobileUsers() {
        log.debug("REST request to get all MobileUsers");
        return mobileUserService.findAll();
    }

    /**
     * {@code GET  /mobile-user/:id} : get the "id" mobileUser.
     *
     * @param id the id of the mobileUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mobileUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MobileUserDTO> getMobileUser(@PathVariable Long id) {
        log.debug("REST request to get MobileUser : {}", id);
        Optional<MobileUserDTO> mobileUserDTO = mobileUserService.findOne(id);
        return HttpUtils.wrapOrNotFound(mobileUserDTO);
    }

    /**
     * {@code DELETE  /mobile-user/:id} : delete the "id" mobileUser.
     *
     * @param id the id of the mobileUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMobileUser(@PathVariable Long id) {

        System.out.println("In the delete mobile-user\n\n\n\n");
        log.debug("REST request to delete MobileUser : {}", id);
        mobileUserService.delete(id);
        System.out.println("IN MOBILE \n\n\n\n");
        HashMap<String,String> s = new HashMap<>();
        s.put("message","Utilisateur supprimé");
        return ResponseEntity.ok().body(s);

    }


    /**
     * {@code PUT  /mobile-user/delete/:id} : close the mobile-user account by updating the date cpte ferme.
     *
     * @param id the id of the mobileUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteMobileUserFromFlutter(@PathVariable Long id) {

        System.out.println("In the delete mobile-user\n\n\n\n");
        log.debug("REST request to close account MobileUser : {}", id);
        mobileUserService.closeAccount(id);
        HashMap<String,String> s = new HashMap<>();
        s.put("message","Utilisateur mobile supprimé (date compte fermé)");
        return ResponseEntity.ok().body(s);

    }

    /** Get the mobile type utilisation in database
     *
     * @return the mobileTypeUtilisation
     */
    @GetMapping("/mobtype")
    public ResponseEntity<MobileTypeUtilisationDTO> getMobType(){
        System.out.println("In the mobtype mobile-user\n\n\n\n");

        return ResponseEntity.ok(mobileUserService.getTypeUse());
    }

    /**
     * Allow an admin to connect with the flutter app
     * @param mobileAdminLoginDTO the admin login info (login password)
     * @return Response entity correspoding to NodeJS response (message + token and userinfo if the connexion work)
     */
    @PostMapping("/login")
    public ResponseEntity<?> getAdminLogin(@RequestBody MobileAdminLoginDTO mobileAdminLoginDTO){
        System.out.println("In the admin/login mobile-user\n\n\n\n");
        HashMap<String,Object> result = new HashMap<>();
        try {
            User user = userService.checkIfMobileAdmin(mobileAdminLoginDTO.getLogin(), mobileAdminLoginDTO.getPassword());
            Date date = new Date();
            date.setTime(date.getTime()+(3600*1000));
            String jwt = Jwts.builder().setId(user.getId().toString()).setSubject("the-super-strong-secrect").setExpiration(date).compact();
            HashMap<String,String> userInfo = new HashMap<>();
            userInfo.put("login",user.getLogin());
            userInfo.put("password_hash",user.getPassword());
            result.put("message","Logged in!");
            result.put("token",jwt);
            result.put("user",userInfo);

                return ResponseEntity.ok(result);
        }
        catch (InvalidPasswordException e){
            System.out.println("Mauvais MDP\n\n\n");
            result.put("message","'Login ou mot de passe incorrect!'");
            return ResponseEntity.status(401).body(result);

        }
        catch (UsernameNotFoundException e){
            System.out.println("Mauvais Username\n\n\n");
            result.put("message","'Login ou mot de passe incorrect!'");
            return ResponseEntity.status(401).body(result);

        }
    }

}

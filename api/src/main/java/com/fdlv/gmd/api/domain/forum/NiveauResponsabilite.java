package com.fdlv.gmd.api.domain.forum;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name="forum_niv_resp_str")
public class NiveauResponsabilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fnr_id")
    private Long id;
    @Column(name = "fnr_niv_resp_str")
    private char responsibilityLevel;
    @Column(name = "fnr_libelle")
    private String libelle;
}

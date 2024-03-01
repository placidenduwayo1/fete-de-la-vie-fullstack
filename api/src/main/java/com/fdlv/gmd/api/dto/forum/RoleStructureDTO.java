package com.fdlv.gmd.api.dto.forum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleStructureDTO implements Serializable {

    private Long froId;
    private String froCode;
    private String froRole;

}
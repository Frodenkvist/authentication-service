package com.authenticationservice.controller.model;

import com.authenticationservice.common.model.PermissionName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDTO {
    @NotBlank
    private PermissionName permission;
    @NotBlank
    private String personnummer;
}

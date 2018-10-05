package com.authenticationservice.service;

import com.authenticationservice.common.exception.PermissionMissingException;
import com.authenticationservice.common.exception.PersonMissingException;
import com.authenticationservice.common.model.Permission;
import com.authenticationservice.common.model.PermissionName;
import com.authenticationservice.common.model.Person;
import com.authenticationservice.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PersonService personService;

    public Permission createPermission(String personnummer, PermissionName permissionName) throws PersonMissingException {
        Person person = personService.getPerson(personnummer);

        Permission permission = new Permission();
        permission.setName(permissionName);
        permission.setPerson(person);

        return permissionRepository.save(permission);
    }

    public void deletePermission(String personnummer, PermissionName permissionName) throws PermissionMissingException {
        int rowsDeleted = permissionRepository.deletePermissionByPersonnummerAndName(personnummer, permissionName);

        if(rowsDeleted < 1) {
            throw new PermissionMissingException("Unable to find permisison: " + permissionName.name());
        }
    }
}

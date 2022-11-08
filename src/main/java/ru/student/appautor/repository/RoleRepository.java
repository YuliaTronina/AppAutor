package ru.student.appautor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.student.appautor.entity.Role;


public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName (String name);
}

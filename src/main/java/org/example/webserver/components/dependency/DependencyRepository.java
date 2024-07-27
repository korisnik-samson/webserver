package org.example.webserver.components.dependency;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DependencyRepository extends JpaRepository<DependencyModel, Integer> {

}

package org.example.webserver.components.dependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DependencyController {

    private final DependencyService dependencyService;

    @Autowired
    public DependencyController(DependencyService dependencyService) {
        this.dependencyService = dependencyService;
    }

}

/*
 * Copyright 2016-2017 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.openshift.booster.database.service;

import io.openshift.booster.database.exception.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/freelancers")
public class FreelancerController {

    private final FreelancerRepository repository;

    public FreelancerController(FreelancerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Freelancer get(@PathVariable("id") Integer id) {
        Freelancer freelancer = repository.findOne(id);

        if(freelancer == null ){
            throw new NotFoundException(String.format("Freelancer with id=%d was not found", id));
        }

        return freelancer;
    }

    @GetMapping
    public List<Freelancer> getAll() {
        Spliterator<Freelancer> freelancers = repository.findAll().spliterator();
        return StreamSupport.stream(freelancers, false).collect(Collectors.toList());
    }


}

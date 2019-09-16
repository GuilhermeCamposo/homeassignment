package io.openshift.booster.database.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = FreelancerController.class)
public class FreelancerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private FreelancerRepository repository;

    @Test
    public void testGetAll() throws Exception{
        given(repository.findAll()).willReturn(generateFreelancer());

        mvc.perform( get("/freelancers").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    private List<Freelancer> generateFreelancer(){
        Skill skill = new Skill(1, "Java");

        Set<Skill> skills = new HashSet<>();
        skills.add(skill);

        Freelancer freelancer = new Freelancer(1,"Test","teste","test@gmail.com");
        freelancer.setSkill(skills);

        List<Freelancer> freelancers = new ArrayList<>();
        freelancers.add(freelancer);

        return  freelancers;
    }

}

package com.example.LearningService.service;

import com.example.LearningService.controller.CourseController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableCaching
@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private CourseService courseService;
    @MockitoBean
    private ValidService validService;

    @Test
    void getCoursePathVariableTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses/abc"))
                .andExpect(status().isBadRequest());;
    }
}

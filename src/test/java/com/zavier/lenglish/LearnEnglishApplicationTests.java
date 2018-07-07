package com.zavier.lenglish;

import com.alibaba.fastjson.JSON;
import com.zavier.lenglish.pojo.Knowledge;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LearnEnglishApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testMvc() throws Exception {
        String content = mvc.perform(get("/test").contentType(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        System.out.println("===============");
        System.out.println(content);
    }

}

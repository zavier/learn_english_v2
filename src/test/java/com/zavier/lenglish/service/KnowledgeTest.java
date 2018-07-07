package com.zavier.lenglish.service;

import com.alibaba.fastjson.JSON;
import com.zavier.lenglish.pojo.Knowledge;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class KnowledgeTest {
    @Autowired
    private KnowledgeService knowledgeService;

    @Test
    public void testAddKnowledge() {
        Knowledge knowledge = new Knowledge();
        knowledge.setChinese("你好");
        knowledge.setEnglish("hello");
        knowledge.setCreator(9);
        knowledge.setModifier(9);
        Knowledge add = knowledgeService.add(knowledge);
        System.out.println(knowledge);
    }

    @Test
    public void testGetKnowledge() {
        Knowledge knowledge = knowledgeService.get(1);
        System.out.println(JSON.toJSONString(knowledge));
    }

    @Test
    public void testUpdateKnowledge() {
        Knowledge knowledge = new Knowledge();
        knowledge.setId(1);
        knowledge.setChinese("测试");
        knowledge.setEnglish("test");
        knowledge.setModifier(9);
        knowledgeService.update(knowledge);
    }

    @Test
    public void testDeleteKnowledge() {
        Knowledge knowledge = new Knowledge();
        knowledge.setId(1);
        knowledge.setModifier(9);
        knowledgeService.delete(knowledge);
    }
}

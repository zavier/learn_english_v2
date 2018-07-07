package com.zavier.lenglish.web;

import com.zavier.lenglish.common.util.ValidatorUtil;
import com.zavier.lenglish.pojo.Knowledge;
import com.zavier.lenglish.pojo.Users;
import com.zavier.lenglish.service.KnowledgeService;
import com.zavier.lenglish.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {
    @Autowired
    private KnowledgeService knowledgeService;
    @Autowired
    private UserService userService;
    @Autowired
    private ValidatorUtil validatorUtil;

    @PostMapping("add")
    public Knowledge add(@RequestBody Knowledge knowledge) {
        validatorUtil.validate(knowledge);
        Users userInfo = userService.getCurrentUser();
        Integer userId = userInfo.getId();
        knowledge.setCreator(userId);
        knowledge.setModifier(userId);
        return knowledgeService.add(knowledge);
    }

    @PostMapping("update")
    public void update(@RequestBody Knowledge knowledge) {
        Users currentUser = userService.getCurrentUser();
        knowledge.setModifier(currentUser.getId());
        knowledgeService.update(knowledge);
    }

    @GetMapping("delete")
    public void delete(@RequestParam Integer id) {
        Users currentUser = userService.getCurrentUser();
        Knowledge knowledge = new Knowledge();
        knowledge.setId(id);
        knowledge.setModifier(currentUser.getId());
        knowledgeService.delete(knowledge);
    }

    @GetMapping("get")
    public Knowledge get(@RequestParam Integer id) {
        return knowledgeService.get(id);
    }
}

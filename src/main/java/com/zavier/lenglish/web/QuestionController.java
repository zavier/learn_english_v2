package com.zavier.lenglish.web;

import com.zavier.lenglish.common.BusinessProcessException;
import com.zavier.lenglish.common.ResultBean;
import com.zavier.lenglish.pojo.Knowledge;
import com.zavier.lenglish.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    private KnowledgeService knowledgeService;

    /**
     * 随机获取最多size条知识点
     * @param needEnglish 是否需要保留英文 1：保留英文，0：保留中文
     * @param size 获取的最多个数
     * @return
     */
    @GetMapping("/get")
    public ResultBean<List<Knowledge>> getQuestion(@RequestParam("needEnglish") int needEnglish, @RequestParam("size") int size) {
        if ((needEnglish != 0 && needEnglish != 1) && size <= 0) {
            throw new BusinessProcessException("参数错误");
        }
        List<Knowledge> questions = knowledgeService.getRandomKnowledge(size);
        knowledgeService.filterOtherLanguage(needEnglish == 1 ? true : false, questions);
        return ResultBean.createBySuccess(questions);
    }
}

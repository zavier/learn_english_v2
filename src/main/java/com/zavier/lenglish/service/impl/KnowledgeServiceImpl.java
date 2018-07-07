package com.zavier.lenglish.service.impl;

import com.alibaba.fastjson.JSON;
import com.zavier.lenglish.common.BusinessProcessException;
import com.zavier.lenglish.dao.KnowledgeMapper;
import com.zavier.lenglish.pojo.Knowledge;
import com.zavier.lenglish.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    @Autowired
    private KnowledgeMapper knowledgeMapper;

    @Override
    public Knowledge add(Knowledge knowledge) {
        knowledgeMapper.insertSelective(knowledge);
        return knowledge;
    }

    @Override
    public void update(Knowledge knowledge) {
        int i = knowledgeMapper.updateByPrimaryKeySelective(knowledge);
        if (i != 1) {
            throw new BusinessProcessException("更新失败，knowledge:" + JSON.toJSONString(knowledge));
        }
    }

    @Override
    public void delete(Knowledge knowledge) {
        int i = knowledgeMapper.deleteByPrimaryKey(knowledge);
        if (i != 1) {
            throw new BusinessProcessException("删除失败, knowledge:" + JSON.toJSONString(knowledge));
        }
    }

    @Override
    public Knowledge get(Integer id) {
        return knowledgeMapper.selectByPrimaryKey(id);
    }
}

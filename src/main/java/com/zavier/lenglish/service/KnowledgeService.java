package com.zavier.lenglish.service;

import com.zavier.lenglish.pojo.Knowledge;

public interface KnowledgeService {
    Knowledge add(Knowledge knowledge);

    void update(Knowledge knowledge);

    void delete(Knowledge knowledge);

    Knowledge get(Integer id);

    void publish(Knowledge knowledge);
}

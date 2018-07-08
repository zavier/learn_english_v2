package com.zavier.lenglish.service;

import com.zavier.lenglish.pojo.Knowledge;
import org.apache.poi.ss.usermodel.Workbook;

public interface KnowledgeService {
    Workbook exportExcel(String ids);

    Workbook exportExcelTemplate();

    Knowledge add(Knowledge knowledge);

    void update(Knowledge knowledge);

    void delete(Knowledge knowledge);

    Knowledge get(Integer id);

    void publish(Knowledge knowledge);
}

package com.zavier.lenglish.service;

import com.zavier.lenglish.common.ResultBean;
import com.zavier.lenglish.param.KnowledgeSearchParam;
import com.zavier.lenglish.pojo.Knowledge;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface KnowledgeService {
    Workbook exportExcel(String ids);

    Workbook exportExcelTemplate();

    void importExcel(Integer userId, MultipartFile file) throws IOException, InvalidFormatException;

    Knowledge add(Knowledge knowledge);

    void update(Knowledge knowledge);

    void delete(Knowledge knowledge);

    ResultBean search(KnowledgeSearchParam param);

    Knowledge get(Integer id);

    void publish(Knowledge knowledge);
}

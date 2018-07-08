package com.zavier.lenglish.service;

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

    Knowledge get(Integer id);

    void publish(Knowledge knowledge);
}

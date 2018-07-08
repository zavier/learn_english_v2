package com.zavier.lenglish.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Splitter;
import com.zavier.lenglish.common.BusinessProcessException;
import com.zavier.lenglish.common.constants.SQLConstants;
import com.zavier.lenglish.common.enums.KnowledgeDifficultyEnum;
import com.zavier.lenglish.common.enums.KnowledgeSourceEnum;
import com.zavier.lenglish.dao.KnowledgeMapper;
import com.zavier.lenglish.pojo.Knowledge;
import com.zavier.lenglish.service.KnowledgeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    @Autowired
    private KnowledgeMapper knowledgeMapper;

    @Override
    public Workbook exportExcel(String ids) {
        List<Knowledge> knowledges = getKnowledgesByIds(ids);
        if (CollectionUtils.isEmpty(knowledges)) {
            throw new BusinessProcessException("无所需的导出数据, ids:" + ids);
        }
        return generateExcel(knowledges);
    }

    private Workbook generateExcel(List<Knowledge> knowledges) {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet 1");
        wrapExcelHead(sheet);
        wrapContent(sheet, knowledges);
        return wb;
    }

    private void wrapContent(Sheet sheet, List<Knowledge> knowledges) {
        // 第0行为表头
        int startRow = 1;
        for (int i = 0; i < knowledges.size(); i++) {
            Row row = sheet.createRow(startRow++);
            Knowledge knowledge = knowledges.get(i);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(knowledge.getEnglish());
            row.createCell(2).setCellValue(knowledge.getChinese());
            row.createCell(3).setCellValue(KnowledgeDifficultyEnum.getText(knowledge.getDifficultyDegree()));
            row.createCell(4).setCellValue(knowledge.getIsPublished() == (byte) 1 ? "是" : "否");
            row.createCell(5).setCellValue(KnowledgeSourceEnum.getText(knowledge.getSource()));
        }
    }

    private void wrapExcelHead(Sheet sheet) {
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("序号");
        row.createCell(1).setCellValue("英文");
        row.createCell(2).setCellValue("中文");
        row.createCell(3).setCellValue("难易度");
        row.createCell(4).setCellValue("是否已发布");
        row.createCell(5).setCellValue("来源");
    }

    private List<Knowledge> getKnowledgesByIds(String ids) {
        if (StringUtils.isBlank(ids)) {
            return null;
        }
        List<String> idList = Splitter.on(",").splitToList(ids);
        if (idList.size() > SQLConstants.SQL_SELECT_IN_MAX_SIZE) {
            throw new BusinessProcessException("一次导出的数据不能超过" + SQLConstants.SQL_SELECT_IN_MAX_SIZE + "条");
        }
        Set<Integer> idSet = idList.stream().map(e -> Integer.valueOf(e)).collect(Collectors.toSet());
        return knowledgeMapper.selectByIds(idSet);
    }

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

    @Override
    public void publish(Knowledge knowledge) {
        int i = knowledgeMapper.updateByPrimaryKeySelective(knowledge);
        if (i != 1) {
            throw new BusinessProcessException("发布失败, knowledge:" + JSON.toJSONString(knowledge));
        }
    }
}

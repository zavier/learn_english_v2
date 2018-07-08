package com.zavier.lenglish.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Splitter;
import com.zavier.lenglish.common.BusinessProcessException;
import com.zavier.lenglish.common.ResultBean;
import com.zavier.lenglish.common.constants.SQLConstants;
import com.zavier.lenglish.common.enums.KnowledgeDifficultyEnum;
import com.zavier.lenglish.common.enums.KnowledgeSourceEnum;
import com.zavier.lenglish.dao.KnowledgeMapper;
import com.zavier.lenglish.param.KnowledgeSearchParam;
import com.zavier.lenglish.pojo.Knowledge;
import com.zavier.lenglish.service.KnowledgeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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

    @Override
    public Workbook exportExcelTemplate() {
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet 1");
        wrapExcelHead(sheet);
        // 设置字体为红色样式
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setColor(IndexedColors.RED.index);
        style.setFont(font);

        Cell englishCell = sheet.getRow(0).getCell(1);
        englishCell.setCellStyle(style);
        Cell chineseCell = sheet.getRow(0).getCell(2);
        chineseCell.setCellStyle(style);
        return wb;
    }

    @Override
    public void importExcel(Integer userId, MultipartFile file) throws IOException, InvalidFormatException {
        String originalFilename = file.getOriginalFilename();
        if (! originalFilename.endsWith(".xlsx")) {
            throw new BusinessProcessException("文件格式错误，应为 xlsx 格式文件");
        }
        InputStream inputStream = file.getInputStream();
        Workbook wb = WorkbookFactory.create(inputStream);
        Sheet sheet = wb.getSheetAt(0);
        checkHead(sheet);
        List<Knowledge> knowledges = converExcel2Knowledge(userId, sheet);
        if (CollectionUtils.isEmpty(knowledges)) {
            return;
        }
        log.info("批量导入的knowledge为：{}", JSON.toJSONString(knowledges));
        knowledgeMapper.insertBatch(knowledges);
    }

    private List<Knowledge> converExcel2Knowledge(Integer userId, Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        log.info("导入的Excel最大行数为:{}", lastRowNum);
        List<Knowledge> knowledges = new ArrayList<>();
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            String english = row.getCell(1).getStringCellValue();
            String chinese = row.getCell(2).getStringCellValue();
            String difficulty = row.getCell(3).getStringCellValue();
            String isPublished = row.getCell(4).getStringCellValue();
            String source = row.getCell(5).getStringCellValue();
            Knowledge knowledge = new Knowledge();
            knowledge.setCreator(userId);
            knowledge.setModifier(userId);
            knowledge.setEnglish(english);
            knowledge.setChinese(chinese);
            knowledge.setDifficultyDegree(KnowledgeDifficultyEnum.getCode(difficulty).byteValue());
            knowledge.setIsPublished(StringUtils.equals("是", isPublished) ? (byte) 1 : (byte) 0);
            knowledge.setSource(KnowledgeSourceEnum.getCode(source).byteValue());
            knowledge.setRemark("");

            knowledges.add(knowledge);
        }
        return knowledges;
    }

    private void checkHead(Sheet sheet) {
        Row row = sheet.getRow(0);
        String cell0 = row.getCell(0).getStringCellValue();
        if (! StringUtils.equals(cell0, "序号")) {
            log.error("expect:{}, actual:{}", cell0, "序号");
            throw new BusinessProcessException("Excel内容格式错误");
        }
        String cell1 = row.getCell(1).getStringCellValue();
        if (! StringUtils.equals(cell1, "英文")) {
            log.error("expect:{}, actual:{}", cell1, "英文");
            throw new BusinessProcessException("Excel内容格式错误");
        }
        String cell2 = row.getCell(2).getStringCellValue();
        if (! StringUtils.equals(cell2, "中文")) {
            log.error("expect:{}, actual:{}", cell2, "中文");
            throw new BusinessProcessException("Excel内容格式错误");
        }
        String cell3 = row.getCell(3).getStringCellValue();
        if (! StringUtils.equals(cell3, "难易度")) {
            log.error("expect:{}, actual:{}", cell3, "难易度");
            throw new BusinessProcessException("Excel内容格式错误");
        }
        String cell4 = row.getCell(4).getStringCellValue();
        if (! StringUtils.equals(cell4, "是否已发布")) {
            log.error("expect:{}, actual:{}", cell4, "是否已发布");
            throw new BusinessProcessException("Excel内容格式错误");
        }
        String cell5 = row.getCell(5).getStringCellValue();
        if (! StringUtils.equals(cell5, "来源")) {
            log.error("expect:{}, actual:{}", cell5, "来源");
            throw new BusinessProcessException("Excel内容格式错误");
        }
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
    public ResultBean search(KnowledgeSearchParam param) {
        if (param == null) {
            return ResultBean.createByErrorMessage("查询参数不能为空");
        }
        // 需要分页查询
        if (param.getPage() != null) {
            PageHelper.startPage(param.getPage(), param.getSize());
            List<Knowledge> knowledges = knowledgeMapper.searchByParam(param);
            PageInfo<Knowledge> customerPageInfo = new PageInfo<>(knowledges);
            return ResultBean.createBySuccess(customerPageInfo);
        }
        List<Knowledge> knowledges = knowledgeMapper.searchByParam(param);
        return ResultBean.createBySuccess(knowledges);
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

package com.zavier.lenglish.web;

import com.zavier.lenglish.common.BusinessProcessException;
import com.zavier.lenglish.common.ResultBean;
import com.zavier.lenglish.common.util.ValidatorUtil;
import com.zavier.lenglish.pojo.Knowledge;
import com.zavier.lenglish.pojo.Users;
import com.zavier.lenglish.service.KnowledgeService;
import com.zavier.lenglish.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
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
    public ResultBean<Knowledge> add(@RequestBody Knowledge knowledge) {
        validatorUtil.validate(knowledge);
        Users userInfo = userService.getCurrentUser();
        Integer userId = userInfo.getId();
        knowledge.setCreator(userId);
        knowledge.setModifier(userId);
        Knowledge res = knowledgeService.add(knowledge);
        return ResultBean.createBySuccess(res);
    }

    @PostMapping("update")
    public ResultBean<String> update(@RequestBody Knowledge knowledge) {
        Users currentUser = userService.getCurrentUser();
        knowledge.setModifier(currentUser.getId());
        knowledgeService.update(knowledge);
        return ResultBean.createBySuccess();
    }

    @GetMapping("delete")
    public ResultBean<String> delete(@RequestParam Integer id) {
        Users currentUser = userService.getCurrentUser();
        Knowledge knowledge = new Knowledge();
        knowledge.setId(id);
        knowledge.setModifier(currentUser.getId());
        knowledgeService.delete(knowledge);
        return ResultBean.createBySuccess();
    }

    @GetMapping("get")
    public ResultBean<Knowledge> get(@RequestParam Integer id) {
        Knowledge knowledge = knowledgeService.get(id);
        return ResultBean.createBySuccess(knowledge);
    }

    @GetMapping("publish")
    public ResultBean<String> publish(@RequestParam Integer id) {
        Users currentUser = userService.getCurrentUser();
        Knowledge knowledge = new Knowledge();
        knowledge.setModifier(currentUser.getId());
        knowledge.setId(id);
        knowledge.setIsPublished((byte) 1);
        knowledgeService.update(knowledge);
        return ResultBean.createBySuccess();
    }

    @PostMapping("import-excel")
    public ResultBean<String> importExcel(@RequestParam("file")MultipartFile file) {
        Users currentUser = userService.getCurrentUser();
        try {
            knowledgeService.importExcel(currentUser.getId(), file);
        } catch (IOException e) {
            log.error("上传文件处理失败", e);
            throw new BusinessProcessException("上传文件处理失败");
        } catch (InvalidFormatException e) {
            log.error("上传文件处理失败", e);
            throw new BusinessProcessException("上传文件处理失败");
        }
        return ResultBean.createBySuccess();
    }

    @GetMapping("excel")
    public void exportExcel(HttpServletResponse response, @RequestParam String knowledgeIds) {
        if (StringUtils.isBlank(knowledgeIds)) {
            return;
        }
        Workbook workbook = knowledgeService.exportExcel(knowledgeIds);
        write2OutputStream(response, workbook, "Knowledge.xlsx");
    }

    @GetMapping("excel-template")
    public void exportExcelTemplate(HttpServletResponse response) {
        Workbook workbook = knowledgeService.exportExcelTemplate();
        write2OutputStream(response, workbook, "KnowledgeTemplate.xlsx");
    }

    private void write2OutputStream(HttpServletResponse response, Workbook workbook, String filename) {
        try (ServletOutputStream outputStream = response.getOutputStream();) {
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("导出excel失败", e);
            throw new BusinessProcessException("导出Excel失败");
        }
    }
}

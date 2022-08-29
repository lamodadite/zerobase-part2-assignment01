package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.course.controller.BaseController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/banner")
public class AdminBannerController extends BaseController {

    private final BannerService bannerService;
    @GetMapping("/lists.do")
    public String getLists(Model model, BannerParam bannerParam){
        bannerParam.init();
        List<BannerDto> bannerDtoList = bannerService.list(bannerParam);

        long totalCount = 0;
        if (!CollectionUtils.isEmpty(bannerDtoList)) {
            totalCount = bannerDtoList.get(0).getTotalCount();
        }
        String queryString = bannerParam.getQueryString();
        String pagerHtml = getPaperHtml(totalCount, bannerParam.getPageSize(), bannerParam.getPageIndex(), queryString);

        model.addAttribute("list", bannerDtoList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "admin/banner/list";
    }

    @GetMapping(value = {"/add.do","/edit.do"})
    public String getAdd(Model model,
                         HttpServletRequest req,
                         BannerInput bannerInput){

        boolean editMode = req.getRequestURI().contains("/edit.do");
        BannerDto detail = new BannerDto();

        if(editMode){
            long id = bannerInput.getId();
            BannerDto existBanner = bannerService.getById(id);
            if (existBanner == null) {
                // error 처리
                model.addAttribute("message", "배너정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = existBanner;
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);

        return "admin/banner/add";
    }
    @PostMapping(value = {"/add.do","/edit.do"})
    public String postAdd(
            Model model,
            MultipartFile file,
            HttpServletRequest req,
            BannerInput bannerInput){

        boolean editMode = req.getRequestURI().contains("/edit.do");
        String saveFilename = "";
        String urlFilename = "";

        if(file != null && !file.getOriginalFilename().equals("")){
            String originalFilename = file.getOriginalFilename();

            File abPath = new File("");
            String baseLocalPath = abPath.getAbsolutePath()+"/files";
            String baseUrlPath = "/files";

            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);

            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1];

            try {
                File newFile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                log.info("############################ - 1");
                log.info(e.getMessage());
            }
            bannerInput.setFilePath(saveFilename);
            bannerInput.setLinkPath(urlFilename);
        }
        if(editMode){
            long id = bannerInput.getId();
            BannerDto existBanner = bannerService.getById(id);
            if (existBanner == null) {
                // error 처리
                model.addAttribute("message", "배너정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = bannerService.update(bannerInput);
        }else{
            boolean result = bannerService.add(bannerInput);
        }

        return "redirect:/admin/banner/lists.do";
    }

    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {

        LocalDate now = LocalDate.now();

        String[] dirs = {
                String.format("%s/%d/", baseLocalPath,now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(),now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        for(String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFilename = String.format("%s%s", dirs[2], uuid);
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            newFilename += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }

        return new String[]{newFilename, newUrlFilename};
    }

    @PostMapping("/delete.do")
    public String delete(BannerInput input){
        bannerService.delete(input.getIdList());
        return "redirect:/admin/banner/lists.do";
    }
}
package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.mapper.BannerMapper;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService{

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;

    @Override
    public boolean add(BannerInput bannerInput) {
        Banner b = Banner.builder()
                .name(bannerInput.getName())
                .filePath(bannerInput.getFilePath())
                .linkPath(bannerInput.getLinkPath())
                .openMethod(bannerInput.getOpenMethod())
                .orderNumber(bannerInput.getOrderNumber())
                .createdAt(LocalDateTime.now())
                .isPublic(bannerInput.getIsPublic().equals("true"))
                .build();

        bannerRepository.save(b);
        return true;
    }

    @Override
    public boolean update(BannerInput bannerInput) {
        Optional<Banner> oBanner= bannerRepository.findById(bannerInput.getId());
        if(oBanner.isPresent()){
            Banner b = oBanner.get();
            b.setName(bannerInput.getName());
            b.setFilePath(bannerInput.getFilePath());
            b.setLinkPath(bannerInput.getLinkPath());
            b.setOpenMethod(bannerInput.getOpenMethod());
            b.setOrderNumber(bannerInput.getOrderNumber());
            b.setCreatedAt(LocalDateTime.now());
            b.setPublic(bannerInput.getIsPublic().equals("true"));
            bannerRepository.save(b);
        }
        return true;
    }

    @Override
    public boolean delete(String idList) {
        if (idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");
            for (String i : ids) {
                Long id = Long.parseLong(i);
                bannerRepository.deleteById(id);
            }
        }
        return true;
    }

    @Override
    public List<BannerDto> list(BannerParam bannerParam) {
        long totalCount = bannerMapper.selectListCount(bannerParam);
        List<BannerDto> list = bannerMapper.selectList(bannerParam);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (BannerDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - bannerParam.getPageStart() - i);
                i++;
            }
        }

        return list;
    }

    @Override
    public BannerDto getById(long id) {
        Banner banner = bannerRepository.getById(id);
        return BannerDto.of(banner);
    }

    @Override
    public List<BannerDto> getIndexBanner() {
        List<Banner> banners = bannerRepository.getIndexBanner();
        List<BannerDto> list = new ArrayList<>();
        for(Banner b : banners){
            list.add(BannerDto.of(b));
        }
        return list;
    }
}
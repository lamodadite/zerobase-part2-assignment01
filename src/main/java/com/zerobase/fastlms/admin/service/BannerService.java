package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;

import java.util.List;

public interface BannerService {
    boolean add(BannerInput bannerInput);
    boolean update(BannerInput bannerInput);
    boolean delete(String id);

    List<BannerDto> list(BannerParam bannerParam);

    BannerDto getById(long id);

    List<BannerDto> getIndexBanner();
}
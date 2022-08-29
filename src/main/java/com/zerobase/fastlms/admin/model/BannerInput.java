package com.zerobase.fastlms.admin.model;

import com.zerobase.fastlms.admin.entity.BannerOpenMethod;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerInput {
    private Long id;

    private String name;
    private String filePath;
    private String linkPath;
    private BannerOpenMethod openMethod;
    private int orderNumber;
    //need to handle later for boolean type
    private String isPublic;

    private String idList;
}
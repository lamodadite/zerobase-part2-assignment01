package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.entity.BannerOpenMethod;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BannerDto {
    Long id;
    String name;
    String filePath;
    String linkPath;
    int orderNumber;
    boolean isPublic;
    BannerOpenMethod openMethod;
    LocalDateTime createdAt;

    long totalCount;
    long seq;

    public static BannerDto of(Banner b){
        if( b == null) return null;
        return BannerDto.builder()
                .id(b.getId())
                .name(b.getName())
                .filePath(b.getFilePath())
                .linkPath(b.getLinkPath())
                .orderNumber(b.getOrderNumber())
                .openMethod(b.getOpenMethod())
                .isPublic(b.isPublic())
                .createdAt(b.getCreatedAt())
                .build();
    }

    public static List<BannerDto> of(List<Banner> xList) {
        if (xList == null) {
            return null;
        }

        List<BannerDto> bannerList = new ArrayList<>();
        for(Banner x : xList) {
            bannerList.add(BannerDto.of(x));
        }
        return bannerList;
    }

    public String getBannerCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return createdAt != null ? createdAt.format(formatter) : "";
    }
}
package com.zerobase.fastlms.admin.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Banner {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String filePath;
    private String linkPath;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private BannerOpenMethod openMethod;
    private int orderNumber;
    private boolean isPublic;
}
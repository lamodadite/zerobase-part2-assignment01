package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.MemberLoginHistory;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MemberLoginHistoryDto {

    private Long id;
    private String userId;
    private LocalDateTime loginDate;
    private String loginIp;
    private String userAgent;

    public static MemberLoginHistoryDto of(MemberLoginHistory m){
        return MemberLoginHistoryDto.builder()
                .id(m.getId())
                .userId(m.getUserId())
                .loginDate(m.getLoginDate())
                .loginIp(m.getLoginIp())
                .userAgent(m.getUserAgent())
                .build();
    }

    public String getLastLoginAtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return loginDate != null ? loginDate.format(formatter) : "";
    }
}
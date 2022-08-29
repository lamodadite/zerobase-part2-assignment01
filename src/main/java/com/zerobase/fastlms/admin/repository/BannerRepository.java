package com.zerobase.fastlms.admin.repository;

import com.zerobase.fastlms.admin.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner,Long> {
    @Query(nativeQuery = true,
            value = "select * from banner " +
                    "where is_public = 1 " +
                    "order by order_number " +
                    "limit 3"
    )
    List<Banner> getIndexBanner();
}
package com.zerobase.fastlms.member.repository;

import com.zerobase.fastlms.member.entity.MemberLoginHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberLoginHistoryRepository extends JpaRepository<MemberLoginHistory,String> {
    @Query(nativeQuery = true,
            value = "select * from member_login_history " +
                    "where user_id = :userId " +
                    "order by id desc " +
                    "limit 5"
    )
    List<MemberLoginHistory> findByIdLimit(@Param("userId")String userId);
}
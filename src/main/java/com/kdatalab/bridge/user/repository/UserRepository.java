package com.kdatalab.bridge.user.repository;

import com.kdatalab.bridge.mypage.model.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<RootEntity, Integer> {

    @Transactional
    @Modifying
    @Query(value = "{call update_account_info(:username, :newPassword, :newEmail)}", nativeQuery = true)
    void updateAccountInfo(String username, String newPassword, String newEmail);

    @Modifying
    @Transactional
    @Query(value = "update TB_USER set STATUS = :status, MOD_DT = now(), MOD_USER = :user where LOGIN_ID = :name", nativeQuery = true)
    void updateStatus(String name, char status, String user);

    @Query(value = "select QC_CHK from TB_USER where LOGIN_ID = :username", nativeQuery = true)
    String getQcChk(String username);
}
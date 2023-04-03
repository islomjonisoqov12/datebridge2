package com.kdatalab.bridge.point;

import com.kdatalab.bridge.mypage.model.RootEntity;
import com.kdatalab.bridge.point.model.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PointRepository extends JpaRepository<RootEntity, Long> {

    @Query(value = "select m.EDU_TYPE projectType, m.SUBJECT projectName, e.QC_DT givenDate, e.COMPTPOINT point " +
                     "from TB_EDU_RESULT e " +
                     "join TB_EDU_MST m " +
                       "on e.EDU_SEQ = m.EDU_SEQ " +
                    "where e.LOGIN_ID = :loginId " +
                      "and e.COMPTPOINT > 0", nativeQuery = true)
    List<PointHistory> findByLoginId(String loginId);

    @Query(value = "select IFNULL(sum(e.COMPTPOINT), 0) " +
                     "from TB_EDU_RESULT e " +
                    "where e.LOGIN_ID = :loginId ", nativeQuery = true)
    int getTotalPointByLoginId(String loginId);
}

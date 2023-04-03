package com.kdatalab.bridge.board.repository;

import com.kdatalab.bridge.board.dto.BoardDto;
import com.kdatalab.bridge.mypage.model.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface BoardRepository  extends JpaRepository<RootEntity, Integer> {
	@Query(nativeQuery = true, value = "select seq, " +
			"bbs_id, " +
			"title, " +
			"noticeat, " +
			"atchmnfl, " +
			"cn, " +
			"reg_user, " +
			"reg_dt, " +
			"mod_user, " +
			"mod_dt " +
			"from BBS_DTL " +
			"order by reg_dt desc " +
			"LIMIT " +
			":firstRecordIndex, :recordsPerPage")
	List<BoardDto> selectBoardList(int firstRecordIndex, int recordsPerPage) throws Exception;

	@Query(nativeQuery = true, value = "select count(*) from BBS_DTL")
	int selectBoardTotalCount(BoardDto params) throws Exception;

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "insert into BBS_DTL( " +
			"seq, " +
			"bbs_id, " +
			"title, " +
			"noticeat, " +
			"atchmnfl, " +
			"cn, " +
			"reg_user,  " +
			"reg_dt, " +
			"mod_user, " +
			"mod_dt) " +
			"values( " +
			":seq, " +
			":bbsId, " +
			":title, " +
			":noticeat, " +
			":atchmnfl, " +
			":cn, " +
			":regUser, " +
			"now(), " +
			":modUser, " +
			"now() " +
			") " +
			"on DUPLICATE KEY UPDATE  " +
			"bbs_id = :bbsId, " +
			"title = :title, " +
			"noticeat = :noticeat, " +
			"atchmnfl = :atchmnfl, " +
			"cn = :cn, " +
			"mod_user = :modUser, " +
			"mod_dt = now()")
	boolean insertBoard(Integer seq, Integer bbsId, String title, String noticeat, String atchmnfl, String cn, String regUser,  String modUser) throws Exception;

	@Query(nativeQuery = true, value = "select seq, " +
			"bbs_id, " +
			"title, " +
			"noticeat, " +
			"atchmnfl, " +
			"cn, " +
			"reg_user, " +
			"reg_dt, " +
			"mod_user, " +
			"mod_dt " +
			"from BBS_DTL " +
			"where seq = :seq")
	BoardDto selectBoard(int seq) throws Exception;

	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "delete from BBS_DTL where seq = :seq")
	boolean deleteBoard(int seq) throws Exception;
}

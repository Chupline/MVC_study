package kr.board.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.board.entity.AuthVO;
import kr.board.entity.Member;

@Mapper // Mybatis API
public interface MemberMapper {
	
	// 아이디 중복 체크
	public Member registerIDCheck(String memID);
	
	// 회원등록 (성공 1, 실패 0)
	public int register(Member m); 
	
	// 로그인 체크
	public Member memLogin(Member mvo);
	
	// 회원 정보 수정 (성공 1, 실패 0)
	public int memUpdate(Member mvo);
	
	// 회원 정보를 가져오기
	public Member getMember(String memID);
	
	// 회원 사진 업데이트
	public void memProfileUpdate(Member mvo);

	// 회원 권한 넣기
	public void authInsert(AuthVO saveVO);

	// 회원 정보수정시 권한 삭제 
	public void authDelete(String memID);
}

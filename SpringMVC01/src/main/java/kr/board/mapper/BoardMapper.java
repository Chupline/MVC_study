package kr.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import kr.board.entity.Board;

@Mapper // Mybatis API
public interface BoardMapper {
	/* 같은 패키지안에 똑같은 이름으로 
	인터페이스의 메소드와 xml파일의 id가 동일하거나
	java 어노테이션을 사용한다면 id는 지워야한다*/
	
	// 전체리스트
	public List<Board> getLists();
	
	// 게시글 작성
	public void boardInsert(Board vo);
	
	// 제목 눌러 내용 상세 보기
	public Board boardContent(int idx);
	
	/* 쿼리문을 xml파일 대신 해당 어노테이션에 맞게 사용할 수 있다. - mybatis API*/
	/* 주의사항 : 어노테이션과 xml에 쿼리문 작성과 같이 동시에 사용은 불가하다.*/
	
	// 글 삭제
	//@Delete("") 
	public void boardDelete(int idx);
	
	// 글 수정
	//@Update("")
	public void boardUpdate(Board vo);
	
	// 조회수 
	@Update("update myboard set count=count+1 where idx=#{idx}")
	public void boardCount(int idx);
	
	
}

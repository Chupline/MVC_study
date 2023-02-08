package kr.board.mapper;

import java.util.List;

import kr.board.entity.Board;

//@Mapper - Mybatis API
public interface BoardMapper {
	
	public List<Board> getLists(); // 전체리스트
	
}

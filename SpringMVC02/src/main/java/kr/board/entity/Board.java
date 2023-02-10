   package kr.board.entity;

import lombok.Data;

@Data // Lombok API
public class Board {
	private int idx; 		// 번호
	private String title; 	// 제목
	private String content;	// 내용
	private String writer;  // 작성자
	private String indate;  // 작성일
	private int count;		// 조회수
	
	// getter, setter -> lombok API 추가해서 사용할 수 있다. (outline에서 확인)

}	
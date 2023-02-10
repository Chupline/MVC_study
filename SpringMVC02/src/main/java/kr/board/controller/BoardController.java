package kr.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;

@Controller
public class BoardController {
	
	/* 기존 컨트롤러는 클라이언트가 서버 요청을 하면 Ajax통신을해서 데이터를 내보낸다.
	 * ex) @ResponseBody 등 */
	
//	@Autowired
//	BoardMapper boardMapper;
	
	@RequestMapping("/")
	public String main() {
		return "main";
	}
	/*
	// @ResponseBody -> jackson-databind동작(객체를 JSON 데이터 포맷으로 변환)
	@RequestMapping("/boardList.do")
	public @ResponseBody List<Board> boardList() {
		List<Board> list = boardMapper.getLists();
		return list; // 객체를 리턴? => JSON 데이터 형식으로 변환(API)해서 리턴(응답)하겠다.
	}
	
	@RequestMapping("/boardInsert.do")
	public @ResponseBody void boardInsert(Board vo) {
		boardMapper.boardInsert(vo); // 등록성공
	}
	
	@RequestMapping("/boardDelete.do")
	public @ResponseBody void boardDelete(@RequestParam("idx") int idx) {
		boardMapper.boardDelete(idx);
	}
	
	@RequestMapping("/boardUpdate.do")
	public @ResponseBody void boardUpdate(Board vo) {
		boardMapper.boardUpdate(vo);
	}
	
	@RequestMapping("/boardContent.do")
	public @ResponseBody Board boardContent(int idx) { //@RequestParam 생략 가능
		Board vo = boardMapper.boardContent(idx);
		return vo; // vo -> JSON
	}
	
	@RequestMapping("/boardCount.do")
	public @ResponseBody Board boardCount(int idx) {
		boardMapper.boardCount(idx);
		Board vo = boardMapper.boardContent(idx);
		return vo;
	}
	*/
	
}


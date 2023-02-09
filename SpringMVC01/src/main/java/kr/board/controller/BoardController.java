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

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;

@Controller
//@Component (구시대의 산물)
public class BoardController {
	// DI : 의존성 주입 (BoardController <-- BoardMapper(구현체))
	@Autowired
	private BoardMapper mapper; 
	
	// 핸들러 매핑 : 클라이언트가 /boardList.do 요청
	@RequestMapping("/boardList.do")
	public String boardList(Model model) {
		// 객체 바인딩 : controller가 가지고있는 데이터를 jsp로 forward해서 
		// 뷰페이지에 보여주기위해 메모리에 적재 -> 모델 객체 필요
		// list -> model -> jsp에서 request객체로 받는다.
		List<Board> list = mapper.getLists();
		model.addAttribute("list", list);
		// 뷰 페이지 리턴(스프링 내부 리졸버 동작) : /WEB-INF/views/ + 리턴값 + .jsp -> forward
		return "boardList"; //  /WEB-INF/views/boardList.jsp ->forward
	}
	
	// 클라이언트에서 요청을 받을때 처리 (a태그, @RequestMapping 가능)
	@GetMapping("/boardForm.do")
	public String boardForm() {
		return "boardForm"; //  /WEB-INF/views/boardForm.jsp ->forward
	}
	
	// 작성글은 post방식으로 받기 때문에 @PostMapping을 사용
	@PostMapping("/boardInsert.do")
	// writer, title, content ==> 파라미터 수집(Board) : 엔티티의 필드와 jsp의 name이 동일해야한다.
	public String boardInsert(Board vo) { 
		mapper.boardInsert(vo); //게시글 등록 -> Mapper.java(메소드 생성) -> Mapper.xml(쿼리문작성) -> DB저장
		return "redirect:/boardList.do"; // 작성 후 목록으로 돌아가기 위해 redirect
	}
	
	// 작성 글 상세보기 get방식으로 넘김 방법1 : 클라이언트에서 해당 변수 지정 
	@GetMapping("/boardContent.do")
	// ?idx=${vo.idx}처럼 파라미터와 메소드의 매개변수값이 동일하면 어노테이션 생략가능
	public String boardContent(@RequestParam("idx") int idx, Model model) { // ?idx= 파라미터로 받는다
		/* 값을 한개만 받을때 @RequestParam */
		/* @값을 여러개 받을때 vo로 받는다 */
		Board vo = mapper.boardContent(idx); //값을 Board객체에 담아서 가져오기
		// 조회수 증가
		mapper.boardCount(idx);
		model.addAttribute("vo",vo); // ${vo.idx}
		return "boardContent"; // boardContent.jsp
	}
	
	// 작성 글 삭제 get방식으로 넘김 방법2 : "?idx=" 변수 없이 서버에서 지정
	@GetMapping("/boardDelete.do/{idx}")
	public String boardDelete(@PathVariable("idx") int idx) {
		mapper.boardDelete(idx); //삭제
		return "redirect:/boardList.do";
	}
	
	// 작성 글 수정페이지 진입
	@GetMapping("/boardUpdateForm.do/{idx}")
	public String boardUpdateForm(@PathVariable("idx") int idx, Model model) {
		Board vo = mapper.boardContent(idx); //수정할 값을 Board객체에 담아서 가져오기
		model.addAttribute("vo", vo); 
		return "boardUpdate"; //boardUpdate.jsp
	} 
	
	// 글 수정 후 상세페이지로 가기
	@PostMapping("/boardUpdate.do")
	public String boardUpdate(Board vo) { // idx, title, content
		mapper.boardUpdate(vo); //수정
		return "redirect:/boardList .do";
	}
	
}


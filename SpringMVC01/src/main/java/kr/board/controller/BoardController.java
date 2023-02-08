package kr.board.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.board.entity.Board;

@Controller
//@Component (구시대의 산물)
public class BoardController {
	
	// 핸들러 매핑 : 클라이언트가 /boardList.do 요청
	@RequestMapping("/boardList.do")
	public String boardList(Model model) {
		
		
		
		Board vo = new Board();
		vo.setIdx(1);
		vo.setTitle("게시판 실습");
		vo.setContent("박매일");
		vo.setIndate("2023-02-08");
		vo.setCount(0);
		
		List<Board> list = new ArrayList<>();
		list.add(vo);
		list.add(vo);
		list.add(vo);
		
		// 객체 바인딩 : controller가 가지고있는 데이터를 jsp로 
		// forward해서 뷰페이지에 보여주기위해 메모리에 적재 -> 모델 객체 필요
		// list -> model -> jsp에서 request객체로 받는다.
		
		model.addAttribute("list", list);
		
		return "boardList"; 
		// 뷰 페이지 리턴(스프링 내부 리졸버 동작) : /WEB-INF/views/ + 리턴값 + .jsp -> forward
	}
	
	
	
}


package kr.board.controller;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.board.entity.Member;
import kr.board.mapper.MemberMapper;

@Controller
public class MemberController {
	
	@Autowired
	MemberMapper memberMapper;
	
	// 회원가입 화면으로 넘기기
	@RequestMapping("/memJoin.do") 
	public String memJoin() {
		return "member/join";
	}
	
	// 아이디 중복 확인
	@RequestMapping("/memRegisterCheck.do")
	public @ResponseBody int memRegisterCheck(@RequestParam("memID") String memID) {
		Member m = memberMapper.registerIDCheck(memID);
		if(m != null || memID.equals("")) { 
			return 0; // m!=null : 이미 존재, equals("") : 입력값이 없거나
		} 
		return 1; // 사용 가능한 아이디
	}
	
	// 회원가입 처리
	@RequestMapping("/memRegister.do")
	public String memRegister(Member m, String memPassword1,String memPassword2, RedirectAttributes rttr, HttpSession session) {
		// 누락 체킹
		if(m.getMemID() == null || m.getMemID().equals("") || 
		   memPassword1==null || memPassword1.equals("")||
		   memPassword2==null || memPassword2.equals("")||
		   m.getMemName() == null || m.getMemName().equals("") ||
		   m.getMemAge() == 0 || 
		   m.getMemGender() == null || m.getMemGender().equals("") ||
		   m.getMemEmail() == null || m.getMemEmail().equals("") ) {
			// 누락메세지를 가지고 가기? => 객체바인딩(Model, HttpServletRequest, HttpSession) 불가 => RedirectAttributes 활용
			rttr.addFlashAttribute("msgType", "실패 메세지"); 
			rttr.addFlashAttribute("msg", "모든 내용을 입력하세요.");  // ${msgType}, ${msg}로 값을 받아서 처리 가능
			return "redirect:/memJoin.do";
		}
		// memPassword1과 memPassword2 비교
		if(!memPassword1.equals(memPassword2)) {
			rttr.addFlashAttribute("msgType", "실패 메세지"); 
			rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다."); 
			return "redirect:/memJoin.do";
		}
		m.setMemProfile(""); // 사진이미지는 없다는 의미 "", null과 다름
		// 회원 테이블에 저장
		int result = memberMapper.register(m);
		if(result == 1) { // 회원가입 성공
			rttr.addFlashAttribute("msgType", "성공 메세지");
			rttr.addFlashAttribute("msg", "회원가입 축하합니다.");
			// 회원가입 성공 => 로그인 처리 => Session 필요
			session.setAttribute("mvo", m); // ${!empty m} : jsp에서 회원가입 여부 확인 가능
			return "redirect:/"; // 회원가입후 메인페이지로 이동
		} else { // 회원가입 실패
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "이미 존재하는 회원입니다.");
			return "redirect:/memJoin.do";
		}
	}
	
	// 로그아웃 처리
	@RequestMapping("/memLogout.do")
	public String memLogout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	// 로그인 화면으로 이동
	@RequestMapping("/memLoginForm.do")
	public String memLoginForm() {
		return "member/memLoginForm"; // memLoginForm.jsp
	}
	
	// 로그인 기능 구현
	@RequestMapping("/memLogin.do")
	public String memLogin(Member m, RedirectAttributes rttr, HttpSession session) {
		// 로그인 정보 누락시
		if(m.getMemID()==null || m.getMemID().equals("") ||
		   m.getMemPassword()==null || m.getMemPassword().equals("")) {
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "모든 내용을 입력해주세요.");
			return "redirect:/memLoginForm.do";
		}
		// 로그인 정보 입력시
		Member mvo = memberMapper.memLogin(m);
		if(mvo!=null) { // 로그인 성공
			rttr.addFlashAttribute("msgType", "성공 메세지");
			rttr.addFlashAttribute("msg", "로그인에 성공했습니다.");
			session.setAttribute("mvo", mvo); // ${empty mvo}
			return "redirect:/";
		} else { // 로그인 실패
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "다시 로그인 해주세요.");
			return "redirect:/memLoginForm.do";
		}
	}
	
	// 회원정보수정 화면으로 이동
	@RequestMapping("/memUpdateForm.do")
	public String memUpdateForm() {
		return "member/memUpdateForm"; 
	}
	 
	// 회원정보수정
	@RequestMapping("/memUpdate.do")
	public String memUpdate(Member m, RedirectAttributes rttr, String memPassword1, String memPassword2, HttpSession session) {
		// 누락 체킹
		if(m.getMemID() == null || m.getMemID().equals("") || 
		   memPassword1==null || memPassword1.equals("")||
		   memPassword2==null || memPassword2.equals("")||
		   m.getMemName() == null || m.getMemName().equals("") ||
		   m.getMemAge() == 0 || 
		   m.getMemGender() == null || m.getMemGender().equals("") ||
		   m.getMemEmail() == null || m.getMemEmail().equals("") ) {
			// 누락메세지를 가지고 가기? => 객체바인딩(Model, HttpServletRequest, HttpSession) 불가 => RedirectAttributes 활용
			rttr.addFlashAttribute("msgType", "실패 메세지"); 
			rttr.addFlashAttribute("msg", "모든 내용을 입력하세요.");  // ${msgType}, ${msg}로 값을 받아서 처리 가능
			return "redirect:/memUpdateForm.do";
		}
		// memPassword1과 memPassword2 비교
		if(!memPassword1.equals(memPassword2)) {
			rttr.addFlashAttribute("msgType", "실패 메세지"); 
			rttr.addFlashAttribute("msg", "비밀번호가 서로 다릅니다."); 
			return "redirect:/memUpdateForm.do";
		}
		// 회원 테이블 수정
		int result = memberMapper.memUpdate(m);
		if(result == 1) { // 회원 정보 수정 성공
			rttr.addFlashAttribute("msgType", "성공  메세지");
			rttr.addFlashAttribute("msg", "회원정보 수정에 성공했습니다.");
			// 회원 정보 수정 성공 => 로그인 처리 => Session 필요
			session.setAttribute("mvo", m); // ${!empty m} : jsp에서 회원가입 여부 확인 가능
			return "redirect:/"; // 회원정부 수정 후 메인페이지로 이동
		} else { // 회원 정보 수정 실패
			rttr.addFlashAttribute("msgType", "실패 메세지");
			rttr.addFlashAttribute("msg", "회원정보 수정에 실패했습니다.");
			return "redirect:/memUpdateForm.do";
		}
	}
	
	// 사진 등록 화면으로 이동
	@RequestMapping("/memImageForm.do")
	public String memImageForm() {
		return "member/memImageForm";
	}
	
	// 회원사진 이미지 업로드(DB 저장)
	@RequestMapping("/memImageUpdate.do")
	public String memImageUpdate(HttpServletRequest request, RedirectAttributes rttr, HttpSession session) {
		// 파일 업로드 API(cos.jar, 등 3가지)
		MultipartRequest multi = null;
		int fileMaxSize = 40*1024*1024; // 10MB
// 위의 경로는 작업자가 만든 경로 이지만, 아래 경로는 이클립스에서 프로젝트를 최종 관리하는 디렉토리이다. => 웹에서 업로드를 하게되면 RealPath에 저장되게 된다.
// C:/eGovFrame-4.0.0/workspace.edu/SpringMVC03src/main/webapp/resources 가 아닌 실제 RealPath는 아래와 같다.
// C:/eGovFrame-4.0.0/workspace.edu/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/SpringMVC03/resources/upload
		String savePath=request.getRealPath("resources/upload"); // 실제 업로드할 경로
		try {
			// 이미지 업로드
			// DefaultFileRenamePolicy : 파일 이름이 중복되지 않게 저장해주는 객체
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy()); 
		} catch (Exception e) {
			e.printStackTrace();
// 톰캣 대용량 파일을 보낼때 톰캣이 자체로 연결을 끊어버리는 현상 발생 => servers/server.xml 에서 63번줄 maxSwallowSize="-1" 추가해야 한다.
			rttr.addFlashAttribute("msgType", "실패 메세지");  
			rttr.addFlashAttribute("msg", "파일의 크기는 10MB를 넘을 수 없습니다.");
			return "redirect:/memImageForm.do";
		}
		// 데이터베이스 테이블에 회원이미지를 업데이트
		String memID = multi.getParameter("memID");
		String newProfile="";
		File file = multi.getFile("memProfile"); // 파일 객체 <- 파일정보를 전달
		if(file != null) { // 업로드 된 상태(png, jpg, gif) 
			// 이미지파일 체크 -> 이미지 파일이 아니면 삭제
			String ext = file.getName().substring(file.getName().lastIndexOf(".")+1); // 확장자를 알 수 있다.
			ext = ext.toUpperCase(); //문자로 PNG, GIF, JPG
			if(ext.equals("PNG") || ext.equals("GIF") || ext.equals("JPG")) {
				// 새로 업로드된 이미지(new), 현재 DB에 있는이미지(old)
				String oldProfile = memberMapper.getMember(memID).getMemProfile();
				File oldFile = new File(savePath+"/"+oldProfile);
				if(oldFile.exists()) {
					oldFile.delete();
				}
				newProfile = file.getName();
				
			} else { // 이미지 파일이 아니면
				if(file.exists()) {
					file.delete(); // 삭제
				}
				rttr.addFlashAttribute("msgType", "실패 메세지");  
				rttr.addFlashAttribute("msg", "이미지 파일만 업로드 가능합니다.");
				return "redirect:/memImageForm.do";
			}
		}
		// 새로운 이미지를 테이블에 업데이트
		Member mvo = new Member();
		mvo.setMemID(memID);
		mvo.setMemProfile(newProfile);
		memberMapper.memProfileUpdate(mvo); // 이미지 업데이트 성공
		Member m = memberMapper.getMember(memID);
		// 프로필이 업로드된 새로운 세션을 생성한다.
		session.setAttribute("mvo", mvo);
		rttr.addFlashAttribute("msgType", "성공 메세지");  
		rttr.addFlashAttribute("msg", "이미지 업로드 성공헀습니다.");
		return "redirect:/";
		
	}
	
	
}



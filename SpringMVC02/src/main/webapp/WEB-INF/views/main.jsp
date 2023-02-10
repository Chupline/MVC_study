<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang=ko">
<head>
<title>Spring MVC02</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		loadList();
	});
	function loadList() {
		// 서버와 통신: 게시판 리스트 가져오기
		$.ajax({
			/* RestController */
			url : "board/all", // 기존 Controller : "boardList.do" // 요청할 URL
			type : "get", // "post" or "get"
			dataType : "json", // 서버에서 전송받을 데이터 형식
			success : makeView, // 정상처리 : 응답된 데이터를 받아서 처리 -> 콜백함수
			error : function() { alert("error"); } // 오류 발생시 처리
		});
	}
	function makeView(data) { // data=[{ }, { }, { }, ...]
		var listHtml = "<table class='table table-bordered'>";
		listHtml += "<tr>";
		listHtml += "<td>번호</td>";
		listHtml += "<td>제목</td>";
		listHtml += "<td>작성자</td>";
		listHtml += "<td>작성일</td>";
		listHtml += "<td>조회수</td>";
		listHtml += "</tr>";

		$.each(data, function(index, obj) {
			listHtml += "<tr>";
			listHtml += "<td>" + obj.idx + "</td>";
			listHtml += "<td id='t"+obj.idx+"'><a href='javascript:goContent("+obj.idx+")'>"+obj.title+"</a></td>";
			listHtml += "<td>" + obj.writer + "</td>";
			listHtml += "<td>" + obj.indate.split(' ')[0] + "</td>";
			listHtml += "<td id='cnt"+obj.idx+"'>" + obj.count + "</td>";
			listHtml += "</tr>";
			
			// title을 클릭시 상세내용 보이기
			listHtml += "<tr id='c"+obj.idx+"' style='display:none'>";
			listHtml += "<td>내용</td>";
			listHtml += "<td colspan='4'>";
			listHtml += "<textarea id='ta"+obj.idx+"' readonly rows='7' class='form-control'></textarea>"
			listHtml += "<br>";
			listHtml += "<span id='ub"+obj.idx+"'><botton class='btn btn-success btn-sm' onclick='goUpdateForm("+obj.idx+")'>수정</botton></span>&nbsp;";
			listHtml += "<botton class='btn btn-warning btn-sm' onclick='goDelete("+obj.idx+")'>삭제</botton>";
			listHtml += "</td>";
			listHtml += "</tr>";
		});
		
		// 글쓰기 버튼 
		listHtml += "<tr>";
		listHtml += "<td colspan='5'>";
		listHtml += "<button class='btn btn-primary btn' onclick='goForm()'>작성</button>";
		listHtml += "</td>";
		listHtml += "</tr>";
		listHtml += "</table>";
		
		$("#view").html(listHtml);

		$("#view").css("display","block");	// 작성 폼 감추기
		$("#wform").css("display","none");	// 리스트 보이기
	}
	function goForm() { // 등록 클릭시
		$("#view").css("display","none");	// 리스트 감추기
		$("#wform").css("display","block");	// 작성 폼 보이기
	}
	function goList() { // 등록에서 목록 클릭시
		$("#view").css("display","block");	// 작성 폼 감추기
		$("#wform").css("display","none");	// 리스트 보이기
	}
	// 게시판 - 등록
	function goInsert() {
		/* 하나씩 가져오기*/
/*  	var title = $("#title").val();	   //val(): 작성한 title
		var contetn = $("#content").val(); //val(): 작성한 content
		var writer = $("#writer").val();   //val(): 작성한 writer 	
			
		/* 한번에 가져오는 방법 */
		/* serialize(): form의 값을 직렬화해서 한번에 가져올 수 있다.
		title=작성제목&content=작성내용&writer=작성자 <-같이 직렬화	  */
		var fData=$("#frm").serialize(); 
		$.ajax({
			/* RestController */
			url : "board/new", // 기존 Controller : "boardInsert.do",
			type : "post",
			data : fData,
			success : loadList,
			error : function(){ alert("error"); }
		});
		// 폼초기화
/* 		$("#title").val("");	 
		$("#content").val("");
		$("#writer").val("");	 */
		$("#fclear").trigger("click");  // trigger("click"): 프로그램이 자동으로 클릭 -> 자동 폼 초기화
	}
	
	// title을 클릭시 상세내용 보이기
	function goContent(idx) {
		if($("#c"+idx).css("display") == "none"){
			// 서버에서 상세보기 내용을 가져 오기
			$.ajax({
				url : "board/"+idx, //"boardContent.do",
				type : "get",
				data : {"idx" : idx},
				dataType : "json",
				success : function(data){
					$("#ta"+idx).val(data.content);
				},
				error : function() {alert("error"); }
			});
			
			$("#c"+idx).css("display", "table-row"); // 보이기
			$("#ta"+idx).attr("readonly", true); // 글 수정 중 제목눌러서 닫고 다시 열 때 항상 readonly
		} else {
			$("#c"+idx).css("display","none"); // 감추기
			// 상세보기 닫을때 조회수++
			$.ajax({
				url : "board/count/"+idx,
				type : "put",
				data : {"idx":idx},
				dataType : "json",
				success : function(data) {
					$("#cnt"+idx).text(data.count);
				},
				error : function() {alert("error");}
			});
		}
	}
	
	// 상세내용에서 글 삭제
	function goDelete(idx) {
		$.ajax({
			url : "board/"+idx, // "boardDelete.do",
			type : "delete",
			data : {"idx":idx},
			success : loadList,
			error : function() { alert("error"); }
		});
	}
	
	// 상세내용에서 글 수정형태
	function goUpdateForm(idx) {
		$("#ta"+idx).attr("readonly", false); // readonly 해제
		var title = $("#t"+idx).text(); // 글 수정시 제목 수정도 가능하게하지만 제목은 사라져있지 않게 title값 남기기
		var newInput = "<input type='text' id='nt"+idx+"' class='form-control' value='"+title+"'/>"; // 글 수정시 제목 수정도 가능하게 하지만 제목은 사라져있음
		$("#t"+idx).html(newInput); // td와td사이에 태그삽입
		// 버튼 색상 변경
		var newBotton="<button class='btn btn-primary btn-sm' onclick='goUpdate("+idx+")'>수정</button>";
		$("#ub"+idx).html(newBotton); 
	}
	
	// 상세내용에서 글 수정제출
	function goUpdate(idx) {
		var title = $("#nt"+idx).val(); // 제목 새로 입력한 값
		var content = $("#ta"+idx).val(); // 내용 새로 입력한 값
		$.ajax({
			url : "board/update", // "boardUpdate.do",
			type : "put",
			// JSON으로 변환, 타입 포맷 해서 전달한다.
			contentType:'application/json;charset=utf-8',
			data : JSON.stringify({"idx":idx, "title":title, "content":content}),
			success : loadList,
			error : function() { alert("error"); }
		});
	}
</script>
</head>

<body>

	<div class="container">
		<h2>Spring MVC02</h2>
		<div class="panel panel-default">
			<div class="panel-heading">Board</div>
			<div class="panel-body" id="view">게시판 불러오는중...</div>
			<div class="panel-body" id="wform" style="display: none">
				<form id="frm">
					<table class="table">
						<tr>
							<td>제목</td>		<!-- id: jquery에서 주로 사용 / name: 파라미터로 주로 사용  -->
							<td><input type="text" class="form-control" id="title " name="title" /></td>
						</tr>
						<tr>
							<td>내용</td>
							<td><textarea rows="7" class="form-control" id="content" name="content"></textarea></td>
						</tr>
						<tr>
							<td>작성자</td>
							<td><input type="text" class="form-control" id="writer" name="writer" /></td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<button type="button" class="btn btn-success btn-sm" onclick="goInsert()">등록</button>
								<button type="reset" class="btn btn-warning btn-sm" id="fclear" >취소</button>
								<button type="button" class="btn btn-info btn-sm" onclick="goList()">목록</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div class="panel-footer">MVC_02</div>
		</div>
	</div>

</body>
</html>

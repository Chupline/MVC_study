<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
		if(${!empty msgType}) { // 값이 존재하면
			$("#messageType").attr("class", "modal-content panel-warning");
			$("#myMessage").modal("show");
		}
	});
  </script>
</head>
<body>
<div class="container">
  <jsp:include page="../common/header.jsp"/>
  <h2>Spring MVC03</h2>
  <div class="panel panel-default">
    <div class="panel-heading">회원 사진 등록</div>
      <div class="panel-body">
        <!-- 로그인 폼 -->
        <!-- file을 서버로 넘길때 바이너리 타입으로 보내야 하기때문에 enctype="multipart/form-data" 사용 -->
        <!-- 스프링 보안에서 enctype(파일저장)타입의 경우 url뒤 파라미터(이름,토큰)를 작성해야 한다. -->
        <form action="${contextPath}/memImageUpdate.do?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
          <input type="hidden" name="memID" value="${mvo.memID}" />
          <table class="table table-bordered" style="text-align : center; border: 1px solid #dddddd;">
            <tr>
              <td style="width: 110px; vertical-align: middle;">아이디</td>
              <td>${mvo.memID}</td>
            </tr>
            <tr>
              <td style="width: 110px; vertical-align: middle;">사진 업로드</td>
                <td>
                  <span class="btn btn-default">
                    이미지를 업로드하세요.<input type="file" name="memProfile" />
                  </span>
              </td>
            </tr>
            <tr>
              <td colspan="2" style="text-align: center; margin: 0 auto;">
              <input type="submit" class="btn btn-primary btn-sm" value="등록" /></td>
            </tr>
          </table>
        </form>
      </div>
      <!-- 실패 메세지를 출력(Modal) -->
      <div id="myMessage" class="modal fade" role="dialog">
        <div class="modal-dialog">
          <!-- Modal content-->
          <div id="messageType" class="modal-content panel-info">
            <div class="modal-header panel-heading">
              <button type="button" class="close" data-dismiss="modal">&times;</button>
              <h4 class="modal-title">${msgType}</h4>
            </div>
            <div class="modal-body">
              <p>${msg}</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
          </div> 
        </div>
      </div>
    <div class="panel-footer">Spring_MVC03(추경현)</div>
  </div>
</div>

</body>
</html>

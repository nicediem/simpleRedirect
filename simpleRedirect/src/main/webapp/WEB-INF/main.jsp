<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<html>
<head>
	<title>Service</title>
<style>
</style>	
</head>
<body>
	<div style="text-align:center;">
		<div class="mainTitleDiv" style="background:black; width:450px; display:inline-block; margin:50px 0px 30px 50px;">
			<h1 style="text-align:center; color:white;">단축URL 서비스</h1>
		</div><br>
		<div class="mainCtnDiv" style="width:450px; display:inline-block; margin-left:50px;">
			<input type="text" style="height:40px; width:375px" id="textUrl" placeholder="http:// or https://, URL을 입력하세요.">
			<input type="button" style="height:40px;" id="chgUrlBtn" value="URL변환">
		</div><br>
		<div class="mainChgDiv" style="width:450px; display:inline-block; margin:30px 0px 0px 50px;">
			<strong>단축 URL : </strong><input type="text" style="height:40px; width:291px" id="chgUrl">
			<input type="button" style="height:40px;" id="copyBtn" value="URL복사">
		</div>
	</div>
</body>
<script src="//code.jquery.com/jquery.min.js"></script>
<script>
var dupChk = 0;
$("#textUrl").keydown(function(key) {
    if (key.keyCode == 13) {
        $('#chgUrlBtn').trigger('click');
    }
});

$('#chgUrlBtn').click(function(){
	// 1. 단축URL NULL 값 체크
	if(!chkUrl($("#textUrl").val())){
		alert("URL이 올바르지 않습니다.");
		return ;
	}

	if( dupChk != 0){
		return ;
	}
	dupChk = 1;

	var param = {"textUrl":$("#textUrl").val()};
	$.ajax({
		url:'./changeUrl.do'
	    ,type:'post'
	    ,data:param
	    ,dataType:'json'
	    ,async:true
	    ,success:function(data){
	    	dupChk = 0;
		    if(data.err == "true"){
		    	alert(data.msg);
		    	$('#chgUrl').val('');
		    	return ;
			}
		    alert(data.msg);
		    $('#chgUrl').val(data.url);
		    
	   },error:function(request,error){
		   dupChk = 0;
		   alert("단축URL 변경 실패");
		   console.log("code=" + request.status + " error="+error);
	   }
	});
});

$('#copyBtn').click(function(){
	if(isEmpty($("#chgUrl").val())){
		alert("단축URL을 생성해 주세요.");
		return ;
	}
	var copyText = document.getElementById("chgUrl");
	copyText.select();
	document.execCommand('Copy');
});
function chkUrl(strUrl){
	var expUrl = /^http[s]?\:\/\//i;
    return expUrl.test(strUrl);
}

function isEmpty(str){
	if(typeof str == "undefined" || str == null || str == ""){
		return true;
	}else{
		return false;
	}
}
</script>
</html>

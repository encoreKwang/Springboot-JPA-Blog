let index = {
	init: function(){
		$("#btn-save").on("click", () => {
			this.save();
		});
	},
	
	save:function(){
		//alert("user의 save함수 호출됨");
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		//console.log(data);
		
		//ajax호출시 default가 비동기 호출
		///ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		$.ajax({
			type: "POST",
			url: "/blog/api/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8", //body데이터가 어떤 타입인지(MIME)
			dataType: "json" //서버로부터 응답이 왔을 때,기본적으로 모든 것이 문자열인데 생긴 게 json이라면 JS오브젝트로 변경시켜줌
			//서버 응답값이 json이면 자바스크립트 오브젝트로 파싱해라는 뜻이에요. json이 아니라면 파싱안하고 그대로 읽는다
		}).done(function(resp){
			alert("회원가입 완료");
			//alert(resp);
			//location.href = "/blog";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
	
}

index.init();
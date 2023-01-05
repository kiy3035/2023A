<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script>
        $(function () {
            $("#findBtn").click(function () {

                let param = {
                    userid: $('#id').val(),
                    password: $("#password").val()
                };

        var boolean = true;

                        if ($('#password').val() == null || $('#password').val() == "") {
                            alert('비밀번호를 입력하세요');
                            boolean = false;
                            return;
                        }

                        if ($('#password2').val() == null || $('#password2').val() == "") {
                            alert('비밀번호 확인란을 입력하세요');
                            boolean = false;
                            return;
                        }

                        if ($('#password').val() != $('#password2').val()) {
                            alert('비밀번호가 서로 일치하지 않습니다');
                            boolean = false;
                            return;
                        }

                $.ajax({
                    type: "POST",
                    url: '${pageContext.request.contextPath}/changePw',
                    data: JSON.stringify(param),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (result) {
                        console.log(result);
                        if (result.result == "success") {
                            alert('비밀번호가 변경되었습니다.');
                            location.href = "${pageContext.request.contextPath}/loginForm";
                        }
                    },
                })
            });
        })
    </script>


    <style type="text/css">
        .mybtn {
            width: 150px;
            height: 40px;
            padding: 0;
            display: inline;
            border-radius: 4px;
            background: #212529;
            color: #fff;
            margin-top: 20px;
            border: solid 2px #212529;
            transition: all 0.5s ease-in-out 0s;
        }

        .mybtn:hover .mybtn:focus {
            background: white;
            color: #212529;
            text-decoration: none;
        }
    </style>
    <title>새 비밀번호로 변경</title>
</head>
<body>
<div class="w3-content w3-container w3-margin-top">
    <div class="w3-container w3-card-4 w3-auto" style="width: 382px;height: 456.3px;">
        <div class="w3-center w3-large w3-margin-top">
            <h3>새 비밀번호로 변경</h3>
        </div>
        <div>
            <p>
                <label>새 비밀번호</label>
                <input class="w3-input" type="hidden" id="id" name="id" value="${userid}">
                <input class="w3-input" type="password" id="password" name="password" placeholder="새 비밀번호를 입력하세요" required>
            </p>
            <p>
                <label>비밀번호 확인</label>
                <input class="w3-input" type="password" id="password2" name="password2" placeholder="새 비밀번호를 입력하세요"
                       required>
            </p>
            <p class="w3-center">
                <button type="button" id="findBtn"
                        class="w3-button w3-hover-white w3-ripple w3-margin-top w3-round mybtn">비밀번호 변경
                </button>
                <button type="button" onclick="location.href = '/loginForm'"
                        class="w3-button w3-hover-white w3-ripple w3-margin-top w3-round mybtn">로그인 화면
                </button>
            </p>
        </div>
    </div>
</div>
</body>
</html>
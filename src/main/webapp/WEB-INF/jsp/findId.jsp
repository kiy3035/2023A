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

                var boolean = true;

                if ($('#username').val() == null || $('#username').val() == "") {
                    alert('이름을 입력하세요');
                    boolean = false;
                    return;
                }

                if ($('#email').val() == null || $('#email').val() == "") {
                    alert('이메일을 입력하세요');
                    boolean = false;
                    return;
                }

                if (boolean) {
                    let param = {
                        username: $('#username').val(),
                        email: $("#email").val(),
                    };

                    console.log(param);
                    $.ajax({
                        type: "POST",
                        url: '${pageContext.request.contextPath}/searchId',
                        data: JSON.stringify(param),
                        contentType: "application/json; charset=utf-8",
                        dataType: "json",
                        success: function (result) {

                            if (result.result_userid == "0") {
                                alert("일치하는 정보가 없습니다");
                            } else {
                                alert("회원님의 아이디는 " + result.result_userid + " 입니다." );
                            }
                        },
                    })
                }

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
    <title>아이디 찾기</title>
</head>
<body>
<div class="w3-content w3-container w3-margin-top">
    <div class="w3-container w3-card-4 w3-auto" style="width: 382px;height: 456.3px;">
        <div class="w3-center w3-large w3-margin-top">
            <h3>아이디 찾기</h3>
        </div>
        <div>
            <p>
                <label>이름</label>
                <input class="w3-input" type="text" id="username" name="username" placeholder="회원가입한 이름을 입력하세요"
                       required>
            </p>
            <p>
                <label>이메일</label>
                <input class="w3-input" type="text" id="email" name="email" placeholder="회원가입한 이메일주소를 입력하세요" required>
            </p>
            <p class="w3-center">
                <button type="button" id="findBtn"
                        class="w3-button w3-hover-white w3-ripple w3-margin-top w3-round mybtn">찾기
                </button>
                <button type="button" onclick="history.go(-1);"
                        class="w3-button w3-hover-white w3-ripple w3-margin-top w3-round mybtn">이전으로
                </button>
            </p>
        </div>
    </div>
</div>
</body>


<script>


</script>

</html>
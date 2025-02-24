<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        table{
            width: 500px;
            border-collapse: collapse;
        }
        table th, table td{
            border: 1px solid black;
            padding: 4px;
        }
        table caption{
            text-indent: -9999px;
        }
    </style>
</head>
<body>
    <div id="wrap">
        <header>
            <h1>사원목록</h1>
        </header>
        <article>
            <table id="t1">
                <caption>사원목록테이블</caption>
                <colgroup>
                    <col width="80px"/>
                    <col width="*"/>
                    <col width="150px"/>
                    <col width="80px"/>
                </colgroup>
                <thead>
                    <tr>
                        <td colspan="4" class="no-border">
                            <form action="search" method="post">
                                <select name="type">
                                    <option value="0">사번</option>
                                    <option value="1">이름</option>
                                    <option value="2">직종</option>
                                    <option value="3">부서</option>
                                </select>
                                <input type="text" name="value"/>
                                <button type="button"
                                onclick="exe(this.form)">검색</button>
                            </form>
                        </td>
                    </tr>
                    <tr>
                        <th>사번</th>
                        <th>이름</th>
                        <th>직종</th>
                        <th>부서</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="vo" items="${ar}">
                    <tr>
                        <td>${vo.empno}</td>
                        <td>${vo.ename}</td>
                        <td>${vo.job}</td>
                        <td>${vo.deptno}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </article>
    </div>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script>

        function exe(frm){
            //유효성 검사
            let v = frm.value.value;
            let type = frm.type.value;

            if(v.trim().length < 1){
                alert("검색어를 입력하세요");
                frm.value.focus();
                return;
            }
            //frm.submit();
            // 비동기 통신을 위한 준비
            $.ajax({
                url: "search",
                type: "post",
                //data: "type="+encodeURIComponent(type)+"&value="+encodeURIComponent(v),
                data:{
                    "type":type,
                    "value":v
                },
                dataType: "json"
            }).done(function(res){
                //서버로부터 전달된 내용이 도착하는 곳!
                //console.log(res);
                let tbody = $("#t1 tbody");

                //tbody 초기화
                tbody.empty();
                for(let i=0; i<res.length; i++){
                    // tr생성
                    // <tr><td>7788</td><td>Smith</td><td>DEV</td><td>10</td></tr>
                    let tr = document.createElement("tr");
                    let td1 = document.createElement("td");
                    let td2 = document.createElement("td");
                    let td3 = document.createElement("td");
                    let td4 = document.createElement("td");

                    td1.innerText = res.ar[i].empno;//사번
                    td2.innerText = res.ar[i].ename;//이름
                    td3.innerText = res.ar[i].job;//직종
                    td4.innerText = res.ar[i].deptno;//부서코드

                    //td1~td4를 tr에 자식요소로 추가
                    tr.appendChild(td1);
                    tr.appendChild(td2);
                    tr.appendChild(td3);
                    tr.appendChild(td4);

                    //tr을 tbody에 자식요소로 추가
                    tbody.append(tr);
                }
            });
        }
    </script>
</body>
</html>

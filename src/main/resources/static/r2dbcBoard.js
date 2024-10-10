document.addEventListener('DOMContentLoaded', () => {
    const apiUrl = 'https://3.36.90.253:8090/r2dbc/mongoDB'; // API URL
    const linkList = document.getElementById('data-list'); // HTML의 <ul> 요소
    var strurl = window.location.search;

    // REST API에서 데이터 가져오기
    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // JSON으로 파싱
        })
        .then(data => {
            console.log(data);
            // 데이터가 배열 형태로 가정
            data.forEach(item => {
                // 각 아이템을 <li>로 만들어서 <ul>에 추가
                const listItem = document.createElement('li');
                const link = document.createElement('a');
                const line = document.createElement('a');
                line.textContent = '-------------------------------------------';
                link.href = "https://3.36.90.253:8080/r2dbc/" + item.id; // 링크의 URL
                link.textContent = item.title; // 링크의 텍스트
                // link.target = '_blank'; // 새 탭에서 열리도록 설정
                listItem.appendChild(link);
                linkList.appendChild(listItem);
                linkList.appendChild(line);
            });
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });

    document.getElementById("submit").addEventListener('click', new_data);

    function new_data() {
        const title = document.getElementById("title").value;
        const data = document.getElementById("data").value;
        let formData = new FormData();
        formData.append('title', title)
        formData.append('data', data);
        fetch(apiUrl, {
            method: "POST",
            credentials: 'include',
            body: formData
        })
            .then(res => {
                // location.reload()
            });
    }
});


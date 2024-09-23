document.addEventListener('DOMContentLoaded', () => {
    const id = document.getElementById('id').value;
    const apiUrl = 'https://3.36.90.253:8090/r2dbc/mongoDB/'; // API URL
    const childList = document.getElementById('child-list'); // HTML의 <ul> 요소
    // REST API에서 데이터 가져오기
    fetch(apiUrl + id)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // JSON으로 파싱
        })
        .then(data => {
            console.log(data);
            const title = document.getElementById("title");
            title.textContent = "title: " + data.title;

            const data_text = document.getElementById("data");
            data_text.textContent = "data: " + data.data;

            const user = document.getElementById("user");
            user.textContent = "user: " + data.user;
            console.log(data.user);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });


    document.getElementById("update_bt").addEventListener('click', update_data);

    function update_data() {
        const title = document.getElementById("update_title").value;
        const data = document.getElementById("update_data").value;
        console.log(title);
        console.log(data);
        let formData = new FormData();
        formData.append('title', title)
        formData.append('data', data);
        fetch(apiUrl + id, {
            method: "POST",
            body: formData,
            credentials: 'include',
        })
            .then(res => {
                location.reload()
            });
    }

    fetch(apiUrl + 'child/' + id)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // JSON으로 파싱
        })
        .then(data => {
            // 데이터가 배열 형태로 가정
            data.forEach(item => {
                // 각 아이템을 <li>로 만들어서 <ul>에 추가
                const listItem = document.createElement('li');
                const child = document.createElement('a');
                child.textContent = item.data;
                listItem.appendChild(child);
                childList.appendChild(listItem);
            });
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });

    document.getElementById("delete_bt").addEventListener('click', delete_data);

    function delete_data() {
        fetch(apiUrl + id, {
            method: "DELETE",
            credentials: 'include',
        })
            .then(res => {
                location.replace("https://3.36.90.253:8080/r2dbc");
            });
    }
});


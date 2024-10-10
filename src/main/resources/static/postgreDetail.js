document.addEventListener('DOMContentLoaded', () => {
    // REST API에서 데이터 가져오기
    const apiUrl = window.location.origin + window.location.pathname;
    const csrfToken = document.querySelector('meta[name="_csrf"]').content; // CSRF 토큰을 메타 태그에서 가져옴
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content; // CSRF 헤더 이름
    const csrf_name = document.getElementById("csrf").name;
    const csrf_value = document.getElementById("csrf").value;
    console.log(apiUrl);
    document.getElementById("update_bt").addEventListener('click', update_data);

    function update_data() {
        const title = document.getElementById("update_title").value;
        const data = document.getElementById("update_data").value;
        let formData = new FormData();
        
        formData.append('title', title)
        formData.append('data', data);
        fetch(apiUrl, {
            body: formData,
            method:'POST',
            credentials: 'include',
        })
            .then(res => {
                location.reload()
            });
    }

    document.getElementById("delete_bt").addEventListener('click', delete_data);

    function delete_data() {
        console.log("in delete");
        fetch(apiUrl, {
            method: "DELETE",
            credentials: 'include',
        })
            .then(res => {
                location.replace(window.location.origin + "/postgre");
            });
    }
});


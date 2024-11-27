document.addEventListener('DOMContentLoaded', () => {
    const prevButton = document.getElementById('preBt');
    const nextButton = document.getElementById('nextBt');

    function getParamsFromUrl() {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams; // URLSearchParams 객체 반환
    }

    // 페이지 이동 함수
    function goToPage(newPage) {
        const baseUrl = window.location.pathname; // 현재 URL의 경로 부분
        const params = getParamsFromUrl(); // 현재 쿼리 매개변수 가져오기
        params.set('page', newPage); // page 값을 새 값으로 설정

        // 새 URL 생성
        const newUrl = `${baseUrl}?${params.toString()}`;
        window.location.href = newUrl; // 새 URL로 이동
    }

    // 이벤트 리스너: 이전 페이지로 이동
    if(prevButton) {
        prevButton.addEventListener('click', function() {
            const params = getParamsFromUrl();
            const currentPage = parseInt(params.get('page')) || 0; // 현재 페이지 값 가져오기
            if (currentPage > 0) {
                goToPage(currentPage - 1); // 현재 페이지에서 1 감소
            }
        });
    }

    // 이벤트 리스너: 다음 페이지로 이동
    if(nextButton) {
        nextButton.addEventListener('click', function() {
            const params = getParamsFromUrl();
            const currentPage = parseInt(params.get('page')) || 0; // 현재 페이지 값 가져오기
            console.log(currentPage);
            goToPage(currentPage + 1); // 현재 페이지에서 1 증가
        });
    }
    const sort = document.getElementById("sort");
    sort.addEventListener('change', function() {
        const baseUrl = window.location.pathname; // 현재 URL의 경로 부분
        const params = getParamsFromUrl(); // 현재 쿼리 매개변수 가져오기
        if(sort.value == 'Title') {
            params.set('sort', 'title');
        }
        else if(sort.value == 'Writer') {
            params.set('sort', 'writer');
        }
        // 새 URL 생성
        const newUrl = `${baseUrl}?${params.toString()}`;
        window.location.href = newUrl; // 새 URL로 이동
    });

    const new_data = document.getElementById("submit")
    new_data.addEventListener('click', create_Data);

    function create_Data() {
        const title = document.getElementById("title").value;
        const data = document.getElementById("data").value;
        let formData = new FormData();
        formData.append('title', title)
        formData.append('data', data);
        const urlWithoutQuery = window.location.origin + window.location.pathname;

        fetch(urlWithoutQuery, {
            method: "POST",
            credentials: 'include',
            body: formData
        })
            .then(res => {
                location.reload()
            });
    }

    const search_data = document.getElementById("search");

    search_data.addEventListener('click', data_search);

    function data_search() {
        const title = document.getElementById("search_title").value;
        const baseUrl = window.location.pathname; // 현재 URL의 경로 부분
        const params = getParamsFromUrl(); // 현재 쿼리 매개변수 가져오기
        params.set('title', title);
        params.set('page', 0);
        // 새 URL 생성
        const newUrl = `${baseUrl}?${params.toString()}`;
        window.location.href = newUrl; // 새 URL로 이동
    }

    const reset_bt = document.getElementById("reset");
    reset_bt.addEventListener('click', reset_page);

    function reset_page() {
        window.location.href = window.location.origin + "/postgre";
    }
});


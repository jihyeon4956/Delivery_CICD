const getCookieValue = (key) => {
    const cookies = document.cookie.split('; ').reduce((acc, cookie) => {
        const [k, v] = cookie.split('=');
        acc[k] = v;
        return acc;
    }, {});

    return cookies[key] || null;
};
const authorization = getCookieValue('Authorization');
console.log(authorization)

if (authorization) {
    const eventSource = new EventSource('http://localhost:8080/test/subscribe/'+authorization);
    //1 대신 cookie의 헤더 정보를 가져와서 userid를 집어넣어준다.
    eventSource.addEventListener('sse', event => {
        let jobj = JSON.parse(event.data).content
        if(jobj != null) alert(jobj)
    });
}
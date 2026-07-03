function getToken() {
    return sessionStorage.getItem('token');
}

function verifySession() {
    var currentPath = window.location.pathname;

    var publicPages = [
        '/', '/map', '/products', '/harvests', '/fairs',
        '/login', '/register', '/how-it-works', '/contact'
    ];

    var isPublic = false;
    for (var i = 0; i < publicPages.length; i++) {
        if (currentPath === publicPages[i] || currentPath.startsWith('/fairs/')) {
            isPublic = true;
            break;
        }
    }

    if (isPublic) {
        return;
    }

    var token = getToken();
    if (!token) {
        return;
    }

    fetch('/api/auth/verify', {
        headers: { 'Authorization': 'Bearer ' + token }
    })
    .then(function(response) {
        if (!response.ok) {
            sessionStorage.clear();
        }
    })
    .catch(function() {
    });
}
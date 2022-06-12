function logout() {
    $.ajax({
        url: "/api/members/logout",
        method: "GET",
        success: function (response) {
            let messageAndRedirection = response.messageAndRedirection;
            alert(messageAndRedirection.message);
            location.href = messageAndRedirection.url;
        }
    })
}

function session_validation() {
    $.ajax({
        url: "/api/members/session-validation",
        method: "GET",
        success: function (response) {
            let messageAndRedirection = response.messageAndRedirection;
            let url = messageAndRedirection.url;
            if (url) {
                alert(messageAndRedirection.message);
                location.href = url;
            }
        }
    })
}
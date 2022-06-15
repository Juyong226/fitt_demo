    var time;

    window.onload = function () {
        time = $('#session-time').text();
        sessionTimeCount(Number(time));
    }

    function sessionTimeCount(time) {
        console.log("sessionTimeCount() has started.");
        console.log("session is valid for " + (time / 60) + " min.");
        if (isNaN(time) == true) {
            return;
        }
        let interval = setInterval(function () {
            if (time <= 0) {
                console.log("sessionTimeCount() is finishing.");
                clearInterval(interval);
                requestDeleteSession();
                return;
            }
            time -= 600;
            let min = time / 60;
            console.log("session is valid for " + min + " min.");
        }, 600000);
    }

    function requestDeleteSession() {
        console.log("requesting deleting session.");
        $.ajax({
            url: "/api/deleteSession",
            method: "GET",
            success: function (response) {
                let messageAndRedirection = response.messageAndRedirection;
                console.log(messageAndRedirection.message);
                location.href = messageAndRedirection.url;
            }
        })
    }

    function extendSessionTime(sessionInterval) {
        console.log("session has extended, it is valid for the next "
            + (sessionInterval / 60) + " min.");
        time = sessionInterval;
    }

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
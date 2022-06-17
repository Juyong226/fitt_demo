    var time;
    var sessionInterval;
    window.onload = function () {
        sessionInterval = Number($('#session-time').text());
        time = sessionInterval;
        sessionTimeCount(time);
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

    function extendSessionTime() {
        console.log("session has extended, it is valid for the next "
            + (sessionInterval / 60) + " min.");
        time = sessionInterval;
    }

    function login() {
        let formData = $('#login-form').serialize();
        $.ajax({
            url: "/api/members/login",
            data: formData,
            method: "POST",
            success: function (response) {
                let exception = response.exception;
                let messageAndRedirection = response.messageAndRedirection;
                if (exception.hasException) {
                    alert(exception.message);
                } else {
                    location.href = messageAndRedirection.url;
                }
            }
        })
    }

    function checkInput() {
        let nickname = $('#nickname').val();
        let password = $('#password').val();

        if (nickname === "" || nickname === undefined
            || password === "" || password === undefined) {
            alert("닉네임과 비밀번호를 모두 입력하세요.");
            return;
        }
        login();
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
var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#message").html("Welcome, have a great time on chat!");
}

function connect() {
    var socket = new SockJS('/web-socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/message', function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.send("/app/chat", {}, JSON.stringify({'content': $("#sendingContent").val()}));
    document.getElementById("sendingContent").value = "";
}

function showMessage(message) {
    $("#message").append("<tr><td>" +
        "<div id='username_app_js'>" + message.username + "</div>" +
        "<div id='time_app_js'>" + message.time + "</div>" +
        "<div id='clear_both_app_js'>" +
        message.content + "</td></tr>"
    );
}

function checkConnectedUsers() {
    $.getJSON('http://localhost:8080/users/connected', function (data) {
        $("#connectedUsers").html("");
        $.each(data, function (index, value) {
            $("#connectedUsers").append(value.username + "<br><br>");
        });
        if (data.length === 2) {
            $("#table_user_no1").show();
            $("#table_user_no2").show();
            $("#start").show();
            $("#total").show();
            $("#label_statistics").show();
            $("#message_connected_users").empty();
        } else {
            $("#table_user_no1").hide();
            $("#table_user_no2").hide();
            $("#start").hide();
            $("#total").hide();
            $("#label_statistics").hide();
            $("#message_connected_users").html("The statistics cannot be displayed before all users connected");
        }
    })
};

setInterval(function () {
    checkConnectedUsers()
}, 1000);

function getVerbs() {
    $.getJSON('http://localhost:8080/verbs', function (data) {
        $("#verb").html("");
        $.each(data, function (index, value) {
            $("#verb").append("<tr><td>" + value.verbBaseForm + "</td></tr>");
        });
    })
};

setInterval(function () {
    getVerbs()
}, 1000);

function postStart() {
    $.get('http://localhost:8080/statistics/start');
}

function postTotal() {
    $.get('http://localhost:8080/statistics/total');
}

function getStatistics() {
    $.getJSON('http://localhost:8080/statistics', function (data) {

        $("#username_no1").html(data.usernameNo1);
        $("#statistics_body_no1").html("");
        $.each(data.verbOccurrenceNo1, function (index, value) {
            $("#statistics_body_no1").append("<tr><td class='body_table_app_js'>" + value + "</td></tr>");
        });

        $("#username_no2").html(data.usernameNo2);
        $("#statistics_body_no2").html("");
        $.each(data.verbOccurrenceNo2, function (index, value) {
            $("#statistics_body_no2").append("<tr><td class='body_table_app_js'>" + value + "</td></tr>");
        });
    })
};

setInterval(function () {
    getStatistics()
}, 1000);

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendMessage();
    });
    $("#start").click(function () {
        postStart();
    });
    $("#total").click(function () {
        postTotal();
    });
    $("#table_user_no1").hide();
    $("#table_user_no2").hide();
    $("#label_statistics").hide();
    $("#start").hide();
    $("#total").hide();
});
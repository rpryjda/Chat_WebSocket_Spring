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
    $.getJSON('http://localhost:8080/connected-users', function (data) {
        $("#connectedUsers").html("");
        $.each(data, function (index, value) {
            $("#connectedUsers").append(value + "<br><br>");
        });
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
});
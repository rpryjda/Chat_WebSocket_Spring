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
        stompClient.subscribe('/topic/connected-users', function (message) {
            showConnectedUsers(JSON.parse(message.body));
        });
        stompClient.subscribe('/topic/verbs', function (message) {
            showVerbs(JSON.parse(message.body));
        });
        stompClient.subscribe('/topic/statistics', function (message) {
            showStatistics(JSON.parse(message.body));
        });
        initSendingConnectedUsers();
        initSendingVerbs();
        initSendingStatistics()
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
    showDisconnected();
    ifNotBothConnected();
}

function initSendingConnectedUsers() {
    stompClient.send("/app/connected", {}, JSON.stringify({}));
}

function initSendingVerbs() {
    stompClient.send("/app/verbs", {}, JSON.stringify({}));
}

function initSendingStatistics() {
    stompClient.send("/app/statistics", {}, JSON.stringify({}));
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

function showConnectedUsers(message) {
    $("#connectedUsers").html("");
    $.each(message.connectedUsers, function (index, value) {
        $("#connectedUsers").append(value + "<br><br>");
    });
    if (message.connectedUsers.length === 2) {
        ifBothConnected()
    } else {
        ifNotBothConnected()
    }
}

function showDisconnected() {
    $("#connectedUsers").html("disconnected");
}

function ifBothConnected() {
    $("#first-row-empty").show();
    $("#table_user_no1").show();
    $("#table_user_no2").show();
    $("#statistic_navigation_page_changer").show();
    $("#statistics_navigation_time_changer").show();
    $("#verbs-table").show();
    $("#label_statistics").show();
    $("#message_connected_users").empty();
}

function ifNotBothConnected() {
    $("#first-row-empty").hide();
    $("#table_user_no1").hide();
    $("#table_user_no2").hide();
    $("#statistic_navigation_page_changer").hide();
    $("#statistics_navigation_time_changer").hide();
    $("#verbs-table").hide();
    $("#label_statistics").hide();
    $("#message_connected_users").html("The statistics cannot be displayed before both users connected");
}

function showVerbs(message) {
    $("#verb").html("");
    $.each(message.verbs, function (index, value) {
        $("#verb").append("<tr><td>" + value.verbBaseForm + "</td></tr>");
    });
    $('#page-number').html(message.currentPage);
    $('#page-size').html(message.size);
}

function postStart() {
    $.get('http://localhost:8080/statistics/start');
}

function postToday() {
    $.get('http://localhost:8080/statistics/today');
}

function postTotal() {
    $.get('http://localhost:8080/statistics/total');
}

function showStatistics(message) {
    $("#username_no1").html(message.usernameNo1);
    $("#statistics_body_no1").html("");
    $.each(message.verbOccurrenceNo1, function (index, value) {
        $("#statistics_body_no1").append("<tr><td class='body_table_app_js'>" + value + "</td></tr>");
    });

    $("#username_no2").html(message.usernameNo2);
    $("#statistics_body_no2").html("");
    $.each(message.verbOccurrenceNo2, function (index, value) {
        $("#statistics_body_no2").append("<tr><td class='body_table_app_js'>" + value + "</td></tr>");
    });
}

function leftClick() {
    $.getJSON('http://localhost:8080/page', function (pageData) {
        var pageNumber = Number(pageData.currentPage);
        pageNumber--;
        if (pageNumber >= 0) {
            $.get('http://localhost:8080/page/' + pageNumber.toString() + '/size/' + pageData.size.toString());
        }
    });
}

function rightClick() {
    $.getJSON('http://localhost:8080/page', function (pageData) {
        var pageNumber = Number(pageData.currentPage);
        pageNumber++;
        if (pageData.maxPage >= pageNumber) {
            $.get('http://localhost:8080/page/' + pageNumber.toString() + '/size/' + pageData.size.toString());
        } else {
            $.get('http://localhost:8080/page/' + pageData.maxPage.toString() + '/size/' + pageData.size.toString());
        }
    });
}

function leftClickPageSize() {
    $.getJSON('http://localhost:8080/page', function (pageData) {
        var pageSize = Number(pageData.size);
        pageSize--;
        if (pageSize >= 1) {
            $.get('http://localhost:8080/page/' + pageData.currentPage.toString() + '/size/' + pageSize.toString());
        }
    });
}

function rightClickPageSize() {
    $.getJSON('http://localhost:8080/page', function (pageData) {
        var pageSize = Number(pageData.size);
        pageSize++;
        if (10 >= pageSize) {
            $.get('http://localhost:8080/page/' + pageData.currentPage.toString() + '/size/' + pageSize.toString());
        }
    });
}

function displayMaxPageInfo() {
    $.getJSON('http://localhost:8080/page', function (pageData) {
        $('#max-page-info').html("min: 0, max: " + pageData.maxPage);
    });
}

function notDisplayMaxPageInfo() {
    $('#max-page-info').html("");
}

function displaySizeInfo() {
    $('#size-info').html("range: 1 to 10");
}

function notDisplaySizeInfo() {
    $('#size-info').html("");
}

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
    $("#today").click(function () {
        postToday();
    });
    $("#total").click(function () {
        postTotal();
    });
    $("#first-row-empty").hide();
    $("#table_user_no1").hide();
    $("#table_user_no2").hide();
    $("#label_statistics").hide();
    $("#statistic_navigation_page_changer").hide();
    $("#statistics_navigation_time_changer").hide();
    $("#verbs-table").hide();
    $("#left_arrow").click(function () {
        leftClick();
    });
    $("#right_arrow").click(function () {
        rightClick();
    });
    $("#left_arrow_size").click(function () {
        leftClickPageSize();
    });
    $("#right_arrow_size").click(function () {
        rightClickPageSize();
    });
    showDisconnected();
    ifNotBothConnected();
    $("#page_label").mouseover(function () {
        displayMaxPageInfo();
    });
    $("#page_label").mouseout(function () {
        notDisplayMaxPageInfo();
    });
    $("#size_label").mouseover(function () {
        displaySizeInfo();
    });
    $("#size_label").mouseout(function () {
        notDisplaySizeInfo();
    });
});
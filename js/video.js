// ==UserScript==
// @name         topic
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  try to take over the world!
// @author       You
// @match        https://www.tampermonkey.net/index.php?version=4.16&ext=dhdg&updated=true
// @icon         https://www.google.com/s2/favicons?sz=64&domain=tampermonkey.net
// @require      https://code.jquery.com/jquery-3.0.0.min.js
// @grant        none
// ==/UserScript==

var videoDom = null;
var video = null;

function begin() {
    //说明没有找到video标签,当前页面没有视频需要看
    if ($(".button.small.add-note.ng-scope").html() == undefined) {
        reload();
    } else {
        video = $("video[class='vjs-tech']");
        $(video).attr("id", "wdnmdStart");
        $(video).prop("muted", true);
        //点击开始按钮
         videoDom = document.getElementById("wdnmdStart");
         videoDom.play();

        $(".mvp-toggle-play.mvp-first-btn-margin").click();
        //视频的结束时间
        let endTime = $(".duration").text();

        let timeIng = setInterval(function () {
            videoDom.play();
            let currentTime = $($(".duration").prev()).text();
            console.log(currentTime + " == " + endTime);
            console.log(currentTime == endTime);
            if (currentTime == endTime) {
                reload();
                clearInterval(timeIng);
            }
        }, 3000);
    }

}

function reload() {
    video == null;
    videoDom == null;

    $("#wdnmd").click();
    setTimeout(function () {
        begin();

    }, 4000);
}


$(document).ready(function () {
    console.log("JAME!")
    setTimeout(function () {
        begin();
        $("a[ng-if='next']").css("background-color", "#8cff5f")
        $("a[ng-if='next']").append("<span id='wdnmd'>gogogo!</span>")
    }, 10000);

})


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


$(document).ready(function () {
    setTimeout(function () {
        if (confirm("开始吗?")) {
            begin();
        }
    }, 5000);

})

function begin() {
    for (let i = 0; i < ("li.subject.ng-scope.single_selection").length; i++) {
        console.log("第" + (i + 1) + "题");
        let liw = $("li.subject.ng-scope.single_selection").eq(i);
        let div1 = $(liw).children().eq(0);
        let div2 = $(div1).children().eq(0);
        let div3 = $(div2).children().eq(0);
        let span1 = $(div3).children().eq(1);
        let p1 = $(span1).children().eq(0);
        //题目
        let topic = $.trim($(p1).text());

        let topicTypeDiv=$(div2).children(1);
        //题目类型
        let topicTypeText=$($(topicTypeDiv).children(0)).text();
        let topicTypeInt=-1;
        if(topicTypeText=="单选题"){
            topicTypeInt=1;
        }




        let answers= [];
        for (let k = 0; k < $(ol1).children().length; k++) {

            let jtm = $(ol1).children().eq(k);
            let lable1 = $(jtm).children().eq(0);
            let tmDiv = $(lable1).children().eq(1);
            let tmTextSpan = $(tmDiv).children().eq(0);
            //具体答案
            let answer = {
                "index": k,
                "content": $($(tmTextSpan).children().eq(0)).text()
            }
            answers[k] = answer;

            // if (text == correctAnswer) {
            //     let spanRadio = $(lable1).children().eq(0);
            //     $($(spanRadio).children().eq(0)).click();
            //     return true;
            // }
        }

        let jsonData = {
            "name": topic,
            "startLine": 0,
            "answers": answers,
            "type":topicTypeInt
        }


        sendRequest(jsonData)
        // $(tm).css("background-color","#031a60")


        if (!fill(div1, correctAnswer)) {
            for (let r = 1; r <= 5; r++) {
                console.log("重试次数:" + r);
                let RjsonData = {
                    "name": topic,
                    "startLine": r
                }
                let correctAnswer1 = $.trim(sendRequest(RjsonData));
                if (fill(div1, correctAnswer1)) {
                    break;
                }
            }

        }

    }
}

function fill(div1, correctAnswer) {
    let tm = $(div1).children().eq(1);
    let ol1 = $(tm).children().eq(0);

    for (let k = 0; k < $(ol1).children().length; k++) {

        let jtm = $(ol1).children().eq(k);
        let lable1 = $(jtm).children().eq(0);
        let tmDiv = $(lable1).children().eq(1);
        let tmTextSpan = $(tmDiv).children().eq(0);
        let text = $($(tmTextSpan).children().eq(0)).text();

        // if (text == correctAnswer) {
        //     let spanRadio = $(lable1).children().eq(0);
        //     $($(spanRadio).children().eq(0)).click();
        //     return true;
        // }
    }
}

function sendRequest(jsonData) {
    let s;
    $.ajax({
        type: "post",
        url: "http://localhost:9897/getAnswer",
        async: false,
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(jsonData),
        dataType: "json",
        success: function (response) {
            if (response.code === 200) {
                s = response.data;
                console.log("后端返回"+s)
            } else {
                console.log("后端出问题..")
                s = null;
            }
        }
    });
    return s;
}

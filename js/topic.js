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

//存放没有答案的题目,用于最后显示
var noneAnswer = "";
//已经解析过得数量
var parsedCount = 1;
$(document).ready(function () {
    setTimeout(function () {
        if (confirm("开始吗?")) {
            begin();
        }
    }, 5000);

})

function begin() {
    console.log("==============单选题==================")
    universal($("li.subject.ng-scope.single_selection"));
    console.log("==============判断题==================")
    universal($("li.subject.ng-scope.true_or_false"));
    console.log("没有答案的题目:" + noneAnswer);
}

// .css("background-color","#8cff5f")

//单选和判断通用
function universal(roots) {
    for (let i = 0; i < $(roots).length; i++) {
        let liw = $(roots).eq(i);
        let div1 = $(liw).children().eq(0);
        let div2 = $(div1).children().eq(0);
        let div3 = $(div2).children().eq(0);
        let span1 = $(div3).children().eq(1);
        let p1 = $(span1).children().eq(0);

        //题目
        let topic = $.trim($(p1).text());

        if (topic == undefined || topic == "") {
            continue;
        }
        console.log("第" + (i + 1) + "题");


        let tm = $(div1).children().eq(1);
        let ol1 = $(tm).children().eq(0);

        //题目类型
        let topicTypeDiv1 = $(div2).children().eq(1);
        let topicTypeText = $($(topicTypeDiv1).children().eq(0)).text();
        let topicTypeInt = -1;
        if (topicTypeText == "单选题") {
            topicTypeInt = 1;
        } else if (topicTypeText == "判断题") {
            topicTypeInt = 3;
        }

        let answers = [];

        for (let k = 0; k < $(ol1).children().length; k++) {

            if (topicTypeText == "单选题" || topicTypeText == "判断题") {
                let jtm = $(ol1).children().eq(k);
                let lable1 = $(jtm).children().eq(0);
                let tmDiv = $(lable1).children().eq(1);
                let tmTextSpan = $(tmDiv).children().eq(0);

                let answer = {};
                if (topicTypeText == "判断题") {
                    answer = {
                        "index": k,
                        "content": $(tmTextSpan).text()
                    }
                } else {
                    //具体选择答案
                    answer = {
                        "index": k,
                        "content": $($(tmTextSpan).children().eq(0)).text()
                    }
                }
                answers[k] = answer;
            }
        }

        let jsonData = {
            "name": topic,
            "answers": answers,
            "type": topicTypeInt
        }

        let result = sendRequest(jsonData);
        disposeResult(result, topicTypeInt, ol1);
    }
}


//处理返回结果 ol1 这个选项的外层ol标签
function disposeResult(result, topicTypeInt, ol1) {
    if (result != null) {
        if (topicTypeInt == 1 || topicTypeInt == 3) {
            let resultCount = result.answers.length;
            for (let j = 0; j < resultCount; j++) {
                let jtm = $(ol1).children().eq(result.answers[j].index);
                let lable1 = $(jtm).children().eq(0);
                let spanRadio = $(lable1).children().eq(0);
                $($(spanRadio).children().eq(0)).click();
            }
        } else if (topicTypeInt == 2) {

        }
    } else {
        noneAnswer += "[" + (parsedCount) + "] ";
    }
    parsedCount++;
}

//发送请求
function sendRequest(jsonData) {
    let result = null;
    $.ajax({
        type: "post",
        url: "http://localhost:9897/getAnswer",
        async: false,
        contentType: "application/json;charset=UTF-8",
        data: JSON.stringify(jsonData),
        dataType: "json",
        success: function (response) {
            if (response.code == 200) {
                console.log("获取成功!  尝试次数:" + response.tryAcquireCount)
                result = response;
            } else {
                console.log(response.message+" 尝试次数"+response.tryAcquireCount);
            }
        }
    });
    return result;
}


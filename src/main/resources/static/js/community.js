/**
 * 回复问题功能
 */
function post() {
    let questionId = $("#question_id").val();
    let content = $("#comment_content").val();
    comment2Target(questionId, 1, content);
}

function comment2Target(targetId, type, content) {
    if (content === "" || content === null || content === " ") {
        alert("回复内容不能为空");
    } else {
        $.ajax({
            type: "POST",
            url: "/community/comment/",
            contentType: 'application/json',
            data: JSON.stringify({
                "parentId": targetId,
                "content": content,
                "type": type
            }),
            success: function (response) {
                if (response.code === 200) {
                    $("#comment_content").val("");
                    window.location.reload();
                } else {
                    if (response.code === 2003) {
                        let isAccepted = confirm(response.message);
                        //未登录
                        if (isAccepted) {
                            window.open("https://github.com/login/oauth/authorize?client_id=eff356635bf6f6d97aba&redirect_uri=http://localhost:8081/community/callback&scope=user&state=1");
                            window.localStorage.setItem("closable", true);
                        } else {
                            alert(response.message);
                        }
                    }
                }
            },
            dataType: "json"
        });
    }
}

function comment(e) {
    let commentId = e.getAttribute("data-id");
    let content = $("#input-" + commentId).val();
    comment2Target(commentId, 2, content);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    let id = e.getAttribute("data-id");
    let comment = $("#comment-" + id);
    //该项是否已经展开,true则删除in
    if (comment.hasClass("in")) {
        //折叠二级评论
        comment.removeClass("in");
    } else {
        let subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length !== 1) {
            comment.addClass("in");
        } else {
            //获得json数据并插入html
            $.getJSON("/community/comment/" + id, function (data) {
                let subCommentContainer = $("#comment-" + id);
                $.each(data.data, function (index, comment) {
                    subCommentContainer.prepend(
                        " <div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments sub-comment-margin\">\n" +
                        "                            <div class=\"media\">\n" +
                        "                                <div class=\"media-left\">\n" +
                        "                                    <img class=\"media-object img-rounded main-avatar\"\n" +
                        "                                         src=\" " + comment.user.avatarUrl + "  \">\n" +
                        "                                </div>\n" +
                        "                                <div class=\"media-body\"><h5 class=\"media-heading\">" + comment.user.name + "</h5>\n" +
                        "                                    <div>" + comment.content + "</div>\n" +
                        "                                    <div class=\"menu\"><span class=\"pull-right\">" + fmtDate(comment.gmtCreate) + "</span></div>\n" +
                        "                                </div>\n" +
                        "                            </div>\n" +
                        "                        </div>")
                });
            });
        }
        comment.addClass("in");
    }
}

function showSelectTag() {
    $("#select-tag").show();
}

function hideSelectTag() {
    $("#select-tag").hide();
}

function selectTag(e) {
    let value = e.getAttribute("data-tag");
    let previous = $("#tag").val();
    if (previous.indexOf(value) === -1) {
        if (previous) {
            $("#tag").val(previous + "," + value);
        } else {
            $("#tag").val(value);
        }
    }
}

/**
 * 时间格式化
 */
function fmtDate(time) {
    let data = new Date(time);
    let year = data.getFullYear();  //获取年
    let month = data.getMonth() + 1;    //获取月
    let day = data.getDate(); //获取日
    let hours = data.getHours();
    let minutes = data.getMinutes();
    time = year + "-" + month + "-" + day + "-" + " " + hours + ":" + minutes;
    return time;
}
/**
 * 用于显示提示信息
 *
 * @param sty 提示风格
 * @param msg 提示信息
 **/
function tip(sty, msg) {
    var _tip = $("<div class='alert " + sty + " alert-dismissible' style='display: none'  role='alert'>" +
        "  <strong>" + msg + "</strong>" +
        "</div>");
    $('#web-title').after(_tip);
    _tip.fadeToggle("slow");
    setTimeout(function () {
        _tip.fadeToggle("slow", function () {
            _tip.remove();
        });
    }, 3000);
}

/**
 * 创建输入框组，并返回
 *
 * @param type 输入框的 type 属性, 默认 text
 * @param name 输入框的 name 属性
 * @param placeholder 输入框的 placeholder 属性
 * @param value 输入框的 value 属性
 */
function createInput(type, name, placeholder, value) {
    if (type == null) {
        type = "text";
    }
    if (name == null) {
        name = "";
    }
    if (placeholder == null) {
        placeholder = "请输入...";
    }
    if (value == null) {
        value = "";
    }
    return $("<input class='form-control' placeholder='" + placeholder + "' required='' type='" + type + "' " +
        "value='" + value + "' name='" + name + "'>")
}


/**
 * 添加事件
 *
 * @param event 事件
 */
$('#add').click(function (event) {
    /* 创建输入框组 */
    var stuNo = createInput("text", "add-stuNo", "Student No", "");
    var name = createInput("text", "add-name", "Name", "");
    var phone = createInput("tel", "add-phone", "phone", "");
    var date = createInput("date", "add-birthday", "birthday", "");
    var addr = createInput("text", "add-addr", "addr", "");
    var form = $('<div class="web-add"></div>');
    form.append(stuNo).append(name).append(phone).append(date).append(addr);
    /* 提示输入 */
    bootbox.confirm({
        title: " ",
        message: form,
        buttons: {
            confirm: {
                label: 'Add',
                className: 'btn-success'
            },
            cancel: {
                label: 'Cancel',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
            /* 如果选择 add */
            if (result === true) {
                /* json 对象封装 */
                var obj = {};
                obj.stuNo = stuNo.val();
                obj.name = name.val();
                obj.phone = phone.val();
                obj.birthday = date.val();
                obj.addr = addr.val();
                /* ajax 提交*/
                $.ajax({
                    type: "PUT",
                    url: "/api/student",
                    data: obj,
                    dataType: "json",
                    success: function (res) {
                        if (res.code === 200) {
                            /*请求成功，提示，并自动跳转*/
                            tip("alert-success", "Update success！");
                            setTimeout(function () {
                                window.location.href = "/";
                            }, 2000);
                        } else {
                            tip("alert-danger", "Update failed！");
                        }
                    },
                    error: function (res) {
                        tip("alert-danger", "Update failed！");
                        console.log(res);
                    }
                });
            }
        }
    })
});

/**
 * 删除时间
 *
 * @param event 事件
 **/
$('.delete').on("click", function (event) {
    /* 提示框 */
    bootbox.confirm({
        title: " ",
        message: "You will delete a row, please confirm your choice.",
        buttons: {
            confirm: {
                label: 'Yes',
                className: 'btn-success'
            },
            cancel: {
                label: 'No',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
            /*获取id*/
            var list = [];
            list.push(event.target.dataset.id);
            if (result === true) {
                /*如果删除，调用删除函数*/
                deleteStudent(list);
            }
        }
    })
});

/**
 * 更新操作
 *
 * @param event 事件
 */
$('.update').on("click", function (event) {
    /* 获取当前更新的id */
    var id = event.target.dataset.id;
    /* 获取当前行 */
    var tr = $("tr[data-id=" + id + "]");
    /* 创建输入框组 */
    var stuNo = createInput("text", "stuNo", "Student No", tr.children().eq(2).text());
    var name = createInput("text", "name", "Name", tr.children().eq(3).text());
    var phone = createInput("tel", "phone", "Phone", tr.children().eq(4).text());
    var birthday = createInput("date", "birthday", "Birthday", tr.children().eq(5).text());
    var addr = createInput("text", "addr", "Addr", tr.children().eq(6).text());
    /* 使用输入框组替换掉文字 */
    tr.children().eq(2).html(stuNo);
    tr.children().eq(3).html(name);
    tr.children().eq(4).html(phone);
    tr.children().eq(5).html(birthday);
    tr.children().eq(6).html(addr);
    /* 显示保存按钮，隐藏更新删除按钮 */
    $(this).fadeOut("slow", function () {
        $(this).next().fadeIn("slow");
    });
    $(this).next().next().fadeOut("slow");

});

/**
 *  保存事件
 *
 *  @param event 事件
 */
$('.save').on("click", function (event) {
    var id = event.target.dataset.id;
    var tr = $("tr[data-id=" + id + "]");
    /* 数据对象封装 */
    var obj = {};
    var save = $(this);
    obj.id = id;
    obj.stuNo = tr.find("input[name='stuNo']").val();
    obj.name = tr.find("input[name='name']").val();
    obj.phone = tr.find("input[name='phone']").val();
    obj.birthday = tr.find("input[name='birthday']").val();
    obj.addr = tr.find("input[name='addr']").val();
    /* ajax 请求 */
    $.ajax({
        type: "PUT",
        url: "/api/student",
        data: obj,
        dataType: "json",
        success: function (res) {
            if (res.code === 200) {
                /* 请求成功，删除输入框，填充内容 */
                tr.children().eq(2).html(obj.stuNo);
                tr.children().eq(3).html(obj.name);
                tr.children().eq(4).html(obj.phone);
                tr.children().eq(5).html(obj.birthday);
                tr.children().eq(6).html(obj.addr);
                /* 隐藏保存按钮，先是更新和删除 */
                save.fadeOut("slow", function () {
                    save.next().fadeIn("slow");
                    save.prev().fadeIn("slow");
                });
                tip("alert-success", "Update success！");
            } else {
                tip("alert-danger", "Update failed！");
            }
        },
        error: function (res) {
            tip("alert-danger", "Update failed！");
            console.log(res);
        }
    })
});

/**
 * 删除已经选择的行的事件
 */
$('#delete').click(function () {
    /* 获取 已选择的行 进行数据封装 */
    var list = [];
    $("input:checked[name='checkItem']").each(function (index, ele) {
        list.push(ele.parentElement.parentElement.dataset.id);
    });
    if (list.length <= 0) {
        tip("alert-info", "Nothing to do. Please select one row or more rows.");
        return;
    }
    /* 确认提示 */
    bootbox.confirm({
        title: " ",
        message: "You will delete " + list.length + " row, please confirm your choice.",
        buttons: {
            confirm: {
                label: 'Yes',
                className: 'btn-success'
            },
            cancel: {
                label: 'No',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
            if (result === true) {
                /*确认后删除*/
                deleteStudent(list);
            }
        }
    });
});

/*删除学生事件*/
function deleteStudent(ids) {
    /*url 拼接*/
    var url = "/api/student/";
    var data = ids.join(",");
    url = url + data;

    /*ajax 请求*/
    $.ajax({
        type: "DELETE",
        url: url,
        dataType: "json",
        success: function (res) {
            if (res.code === 200) {
                /*删除行*/
                ids.forEach(function (value) {
                    $("tr[data-id=" + value + "]").fadeOut("slow", function () {
                        $(this).remove();
                    })
                });
                tip("alert-success", "Delete " + ids.length + " students success！");
            } else {
                tip("alert-danger", "Delete " + ids.length + " students failed！");
            }
        },
        error: function (res) {
            tip("alert-danger", "Delete failed！");
            console.log(res);
        }
    })
}

/*初始化表格第一列的多选框*/
function initTableCheckbox() {
    var $thr = $('table thead tr');
    var $checkAllTh = $('<th><input type="checkbox" id="checkAll" name="checkAll" /></th>');
    /*将全选/反选复选框添加到表头最前，即增加一列*/
    $thr.prepend($checkAllTh);
    /*“全选/反选”复选框*/
    var $checkAll = $thr.find('input:checkbox');
    $checkAll.click(function (event) {
        /*将所有行的选中状态设成全选框的选中状态*/
        $tbr.find('input:checkbox').prop('checked', $(this).prop('checked'));
        /*并调整所有选中行的CSS样式*/
        if ($(this).prop('checked')) {
            $tbr.find('input:checkbox').parent().parent().addClass('warning');
        } else {
            $tbr.find('input:checkbox').parent().parent().removeClass('warning');
        }
        /*阻止向上冒泡，以防再次触发点击操作*/
        event.stopPropagation();
    });
    /*点击全选框所在单元格时也触发全选框的点击操作*/
    $checkAllTh.click(function () {
        $(this).find('input:checkbox').click();
    });
    var $tbr = $('table tbody tr');
    var $checkItemTd = $('<td><input type="checkbox" name="checkItem" /></td>');
    /*每一行都在最前面插入一个选中复选框的单元格*/
    $tbr.prepend($checkItemTd);
    /*点击每一行的选中复选框时*/
    $tbr.find('input:checkbox').click(function (event) {
        /*调整选中行的CSS样式*/
        $(this).parent().parent().toggleClass('warning');
        /*如果已经被选中行的行数等于表格的数据行数，将全选框设为选中状态，否则设为未选中状态*/
        $checkAll.prop('checked', $tbr.find('input:checked').length === $tbr.find('input:checkbox').length);
        /*阻止向上冒泡，以防再次触发点击操作*/
        event.stopPropagation();
    });
    /*点击每一行时也触发该行的选中操作*/
    $tbr.click(function () {
        $(this).find('input:checkbox').click();
    });

}

$('#exit').click(function () {
    window.location.href = "/logout";
});

/*自执行函数*/
$(function () {
    initTableCheckbox();
    $(".container").fadeIn("slow");
});
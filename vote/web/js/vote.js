$(document).ready(function () {
    userprogram = {
        //list页：新建或者更新的地址
        listUrl: null,
        //login页：初始化
        loginInit: function () {
            $("#username").focus();
            $("#login").on("click", function () {
                userprogram.login();
            });
        },
        //login页：enter键登录
        enter: function (e) {
            var key = window.event ? e.keyCode : e.which;
            if (key == 13) {
                login();
            }
        },
        //login页：检查表单并登录
        login: function () {
            var username = $.trim($("#username").val());
            var password = $("#password").val();
            if (username == undefined || username == "") {
                general.infoShow('Error', '账号不能为空！');
            } else if (password == undefined || password == "") {
                general.infoShow('Error', '密码不能为空！');
            } else {
                var result = general.sendRequest('../user/login', {
                    username: username,
                    password: password
                });
                if (result.result == 1) {
                    general.infoShow('Success', result.msg);
                    window.location.href = "../html/index.html";
                } else {
                    general.infoShow('Error', result.msg);
                }
            }
        },
        //list页：打开新建用户的窗口，并修改地址
        listNew: function () {
            $('#dlg').dialog('open').dialog('setTitle', '新建用户');
            $('#fm').form('clear');
            $("input[name=identity]").get(0).checked = true;
            userprogram.listUrl = '../user/insert';
        },
        //list页：打开更新用户的窗口，并修改地址
        listUpdate: function () {
            var row = $('#dg').datagrid('getSelected');
            if (row) {
                $('#dlg').dialog('open').dialog('setTitle', '更改用户信息');
                $('#fm').form('load', row);
                userprogram.listUrl = '../user/update?id=' + row.id;
            } else {
                general.infoShow('Error', '请选中一行数据！');
            }
        },
        //list页：保存用户数据
        listSave: function () {
            $('#fm').form('submit', {
                url: userprogram.listUrl,
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.result == 0) {
                        general.infoShow('Error', result.msg);
                    } else {
                        general.infoShow('Success', result.msg);
                        $('#dlg').dialog('close');
                        $('#dg').datagrid('reload');
                    }
                }
            });
        },
        //list页：删除前询问是否确认
        listDestroy: function () {
            var rows = $('#dg').datagrid('getSelections');
            if (rows.length != 0) {
                $.messager.confirm('确认', '删除用户会同时删除与此用户相关的申请和投票，确认删除吗?', function (r) {
                    if (r) {
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (i != 0) ids += ",";
                            ids = ids + rows[i].id;
                        }
                        userprogram.deleteByids(ids);
                    }
                });
            } else {
                general.infoShow('Error', '至少选中一行数据');
            }
        },
        //list页：查询用户
        listSearch: function () {
            $('#dg').datagrid('load', {
                username: $("#username").val(),
                realname: $("#realname").val(),
                number: $("#number").val(),
                identity: $("#identity").combobox('getValue')
            });
        },
        //list页：导出
        listExport: function () {
            $.messager.confirm('确认', '确认导出吗?', function (r) {
                var data = {
                    username: $("#username").val(),
                    realname: $("#realname").val(),
                    number: $("#number").val(),
                    identity: $("#identity").combobox('getValue')
                }
                if (r) {
                    var result = general.sendRequest('../user/export', data);
                    if (result.result == '1') {
                        window.location.href = "../excelfiles/exportuser.xls";
                    }
                    else {
                        general.infoShow('Error', result.msg);
                    }
                }
            });
        },
        //list页：打开上传文件的窗口
        listUpload: function () {
            $('#dlg-upload').dialog('open').dialog('setTitle', '上传文件');
            $('#fm-upload').form('clear');
        },
        listUploadSave: function () {
            $('#fm-upload').form('submit', {
                url: '../user/upload',
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.result == 0) {
                        general.infoShow('Error', result.msg);
                    } else {
                        general.infoShow('Success', result.msg);
                        $('#dlg-upload').dialog('close');
                        $('#dg').datagrid('reload');
                    }
                }
            });
        },
        //online页：初始化
        onlineInit: function () {
            var rows = userprogram.online();
            if (rows == null) return false;
            $("#table-tbody").empty();
            for (var i = 0; i < rows.length; i++) {
                var recordall = "";
                recordall += '<tr>';
                recordall += '<td>' + rows[i].id + '</td>';
                recordall += '<td>' + rows[i].username + '</td>';
                recordall += '<td>' + rows[i].realname + '</td>';
                recordall += '<td>' + rows[i].number + '</td>';
                if (rows[i].identity == "1") {
                    recordall += '<td><span style="color:green;font-family:微软雅黑;">普通用户</span></td>';
                } else {
                    recordall += '<td><span style="color:red;font-family:微软雅黑;">管理员</span></td>';
                }
                recordall += '</tr>';
                $("#table-tbody").append(recordall);
            }
        },
        //获取登录信息
        checkLogin: function () {
            var result = general.sendRequest('../user/check', null);
            if (result.user == null || result.user == undefined) {
                alert("您尚未登录！")
                window.location.href = "../html/login.html";
            } else {
                return result.user;
            }
        },
        //注销用户
        logout: function () {
            if (confirm("确定要注销此账户？")) {
                var result = general.sendRequest('../user/logout', null);
                window.location.href = "../html/login.html";
            } else return false;
        },
        //通过条件查询用户
        selectBycondition: function (data) {
            var result = general.sendRequest('../user/select', data);
            return result.rows;
        },
        //根据ids删除用户，可批量删除
        deleteByids: function (ids) {
            var result = general.sendRequest('../user/delete', {
                ids: ids
            });
            general.infoShow('Success', result.msg);
            $('#dg').datagrid('reload');
        },
        //查询在线用户
        online: function () {
            var result = general.sendRequest('../user/online', null);
            return result.rows;
        }
    };
    voteinfoprogram = {
        //list页：新建或者更新的地址
        listUrl: null,
        //list页：初始化
        listInit: function () {
            $('#dg').datagrid({
                view: detailview,
                detailFormatter: function (index, row) {
                    return '<div style="padding:2px"><table id="ddv-' + index + '"></table></div>';
                },
                onExpandRow: function (index, row) {
                    $('#ddv-' + index).datagrid({
                        url: '../voteinfo/detail?vote_id=' + (row.id),
                        fitColumns: true,
                        columns: [
                            [{
                                field: 'id',
                                title: '用户Id'
                            }, {
                                field: 'username',
                                title: '用户名'
                            }, {
                                field: 'realname',
                                title: '真实姓名'
                            }, {
                                field: 'number',
                                title: '学号'
                            }, {
                                field: 'identity',
                                title: '权限',
                                formatter: formatPriceIdentity
                            }, {
                                field: 'introduction',
                                title: '申请理由'
                            }, {
                                field: 'count',
                                title: '得票总数',
                                formatter: formatPricegettotal
                            },]
                        ],
                        onResize: function () {
                            $('#dg').datagrid('fixDetailRowHeight', index);
                        },
                        onLoadSuccess: function () {
                            setTimeout(function () {
                                $('#dg').datagrid('fixDetailRowHeight', index);
                                $('#dg').datagrid('fixRowHeight', index);
                            }, 0);
                        }
                    });
                    $('#dg').datagrid('fixDetailRowHeight', index);
                }
            });
        },
        //list页：打开新建投票项目的窗口，并修改地址
        listNew: function () {
            $('#dlg').dialog('open').dialog('setTitle', '新建投票项目');
            $('#fm').form('clear');
            $("input[name=state]").get(0).checked = true;
            $("input[name=is_anonymous]").get(0).checked = true;
            voteinfoprogram.listUrl = '../voteinfo/insert';
        },
        //list页：打开更新投票项目的窗口，并修改地址
        listUpdate: function () {
            var row = $('#dg').datagrid('getSelected');
            if (row) {
                $('#dlg').dialog('open').dialog('setTitle', '更改投票项目信息');
                $('#fm').form('load', row);
                voteinfoprogram.listUrl = '../voteinfo/update?id=' + row.id;
            } else {
                general.infoShow('Error', '请选中一行数据');
            }
        },
        //list页：保存投票项目数据
        listSave: function () {
            $('#fm').form('submit', {
                url: voteinfoprogram.listUrl,
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.result == 0) {
                        general.infoShow('Error', result.msg);
                    }
                    else {
                        general.infoShow('Success', result.msg);
                        $('#dlg').dialog('close');
                        $('#dg').datagrid('reload');
                    }
                }
            });
        },
        //list页：删除前询问是否确认
        listDestroy: function () {
            var rows = $('#dg').datagrid('getSelections');
            if (rows.length != 0) {
                $.messager.confirm('确认', '删除项目会同时删除与此项目相关的申请和投票，确认删除吗?', function (r) {
                    if (r) {
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (i != 0) ids += ",";
                            ids = ids + rows[i].id;
                        }
                        voteinfoprogram.deleteByids(ids);
                    }
                });
            }
            else {
                general.infoShow('Error', '至少选中一行数据');
            }
        },
        //list页：查询投票项目
        listSearch: function () {
            $('#dg').datagrid('load', {
                id: $("#id").val(),
                title: $("#title").val(),
                state: $("#state").combobox('getValue'),
                is_anonymous: $("#is_anonymous").combobox('getValue')
            });
        },
        //base页：细节对应的项目id
        baseDetailId: null,
        //base页：初始化
        baseInit: function () {
            var rows = voteinfoprogram.selectBycondition(null);
            if (rows == null) return false;
            $("#voteinfoall").empty();
            var user_id = userprogram.checkLogin().id;
            for (var i = 0; i < rows.length; i++) {
                var voteall = "";
                voteall += '<div class="first">';
                voteall += '<a href="#" class="easyui-linkbutton" iconCls="icon-large-chart" plain="true"';
                voteall += 'onclick="voteinfoprogram.baseDetailOpen(' + rows[i].id + ');return false;">';
                voteall += rows[i].title;
                if (rows[i].is_anonymous == 1) voteall += '<span style="color:green;font-family:微软雅黑;">(匿名)</span>';
                else voteall += '<span style="color:red;font-family:微软雅黑;">(实名)</span>';
                voteall += '</a>';
                voteall += "<pre>" + rows[i].description + "</pre>";
                voteall += '<div id="vote_info">';
                voteall += '<span>申请人数：' + applyprogram.selectBycondition({
                        vote_id: rows[i].id,
                        page: null,
                        rows: null
                    }).length + '</span>';
                voteall += '<span>投票人数：' + recordprogram.selectBycondition({
                        vote_id: rows[i].id,
                        page: null,
                        rows: null
                    }).length + '</span>';
                voteall += '</div>';
                if (rows[i].state == '1') voteall += '<div id="vote_state"><p style="color:orange;font-size: 16px;">正在申请...</p></div>';
                else if (rows[i].state == '2') voteall += '<div id="vote_state"><p style="color:green;font-size: 16px;">正在投票...</p></div>';
                else voteall += '<div id="vote_state"><p style="color:#ccc;font-size: 16px;">已经结束...</p></div>';
                voteall += '<div id="vote_button">';
                var data = {
                    user_id: user_id,
                    vote_id: rows[i].id
                };
                var applylists = applyprogram.selectBycondition(data);
                if (rows[i].state == '1') {
                    if (applylists != null && applylists.length != 0) {
                        voteall += '<input type="button" class="btn btn-success" value="修改"' + 'onclick="applyprogram.baseUpdate(' + rows[i].id + ');return false;">';
                    } else {
                        voteall += '<input type="button" class="btn btn-success" value="申请"' + 'onclick="applyprogram.baseNew(' + rows[i].id + ');return false;">';
                    }
                } else {
                    if (applylists != null && applylists.length != 0) {
                        voteall += '<input type="button" class="btn btn-success" value="修改" disabled="disabled"/>';
                    } else {
                        voteall += '<input type="button" class="btn btn-success" value="申请" disabled="disabled"/>';
                    }
                }
                data = {
                    send_id: user_id,
                    vote_id: rows[i].id
                };
                var recordlists = recordprogram.selectBycondition(data);
                if (rows[i].state == '2') {
                    if (recordlists != null && recordlists.length != 0) {
                        voteall += '<input type="button" class="btn btn-info" value="修改"' + 'onclick="recordprogram.baseUpdate(' + rows[i].id + ');return false;">';
                    } else {
                        voteall += '<input type="button" class="btn btn-info" value="投票"' + 'onclick="recordprogram.baseNew(' + rows[i].id + ');return false;">';
                    }
                } else {
                    if (recordlists != null && recordlists.length != 0) {
                        voteall += '<input type="button" class="btn btn-info" value="修改" disabled="disabled"/>';
                    } else {
                        voteall += '<input type="button" class="btn btn-info" value="投票" disabled="disabled"/>';
                    }
                }
                voteall += '</div>';
                voteall += "</div>";
                $("#voteinfoall").append(voteall);
            }
            $.parser.parse();
        },
        //base页：打开细节的窗口，并修改id
        baseDetailOpen: function (id) {
            var top = window.pageYOffset + 50;
            var left = window.pageXOffset + 50;
            $('#dlg-detail').window({
                top: top,
                left: left,
                maximized: true,
                title: '详细信息'
            });
            voteinfoprogram.baseDetailId = id;
            $('#dlg-detail-tabs').tabs({
                selected: 0,
                onSelect: function (title) {
                    if (title == '竞选列表') voteinfoprogram.baseDetailApply();
                    else if (title == '柱状图') voteinfoprogram.baseDetailTotal();
                    else if (title == '投票记录') voteinfoprogram.baseDetailRecord();
                }
            });
            voteinfoprogram.baseDetailApply();
            $('#dlg-detail').window('open');
        },
        //base页：在细节中显示申请信息
        baseDetailApply: function () {
            var rows = voteinfoprogram.detailBycondition({
                vote_id: voteinfoprogram.baseDetailId
            })
            if (rows == null) return false;
            $("#dlg-detail-tabs-apply-table-tbody").empty();
            if (rows.length == 0) {
                $("#dlg-detail-tabs-apply-table-tbody").append("没有人申请");
                return false;
            }
            for (var i = 0; i < rows.length; i++) {
                var detail = "";
                detail += '<tr>';
                detail += '<td>' + rows[i].id + '</td>';
                detail += '<td>' + rows[i].username + '</td>';
                detail += '<td>' + rows[i].realname + '</td>';
                detail += '<td>' + rows[i].number + '</td>';
                if (rows[i].identity == '1') detail += '<td>' + '<span style="color:green;font-family:微软雅黑;">普通用户</span>' + '</td>';
                else detail += '<td>' + '<span style="color:red;font-family:微软雅黑;">管理员</span>' + '</td>';
                detail += '<td><div class="vote" data-toggle="popover" data-placement="bottom" title="申请理由" ';
                detail += 'data-content="' + rows[i].introduction + '">点我弹出</div></td>';
                if (rows[i].is_accept == '0') detail += '<td>' + '<span style="color:red;font-family:微软雅黑;">' + '未通过' + '</span>' + '</td>';
                else detail += '<td>' + rows[i].count + '</td>';
                $("#dlg-detail-tabs-apply-table-tbody").append(detail);
            }
            $('[data-toggle="popover"]').popover();
        },
        //base页：在细节中显示柱状图
        baseDetailTotal: function () {
            var rows = voteinfoprogram.detailBycondition({
                vote_id: voteinfoprogram.baseDetailId,
                is_accept: 1
            })
            if (rows == null) return false;
            var user_realname = [];
            var user_total = [];
            for (var i = 0; i < rows.length; i++) {
                user_realname.push(rows[i].realname);
                user_total.push(rows[i].count);
            }
            var voteinfo = voteinfoprogram.selectBycondition({
                id: voteinfoprogram.baseDetailId
            })[0];
            var barChart = echarts.init(document.getElementById("dlg-detail-tabs-total"));
            option = {
                title: {
                    x: 'center',
                    text: '柱状图',
                    subtext: voteinfo.title,
                },
                tooltip: {
                    trigger: 'item'
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataView: {
                            show: true,
                            readOnly: false
                        },
                        restore: {
                            show: true
                        },
                        saveAsImage: {
                            show: true
                        }
                    }
                },
                calculable: true,
                grid: {
                    borderWidth: 0,
                    y: 80,
                    y2: 60
                },
                xAxis: [{
                    type: 'category',
                    show: false,
                    data: user_realname
                }],
                yAxis: [{
                    type: 'value',
                    show: false
                }],
                series: [{
                    name: voteinfo.title,
                    type: 'bar',
                    itemStyle: {
                        normal: {
                            color: function (value) {
                                return "#" + ("00000" + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6);
                            },
                            label: {
                                show: true,
                                position: 'top',
                                formatter: '{b}\n{c}'
                            }
                        }
                    },
                    data: user_total
                }]
            };
            barChart.setOption(option);
        },
        //base页：在细节中显示投票记录
        baseDetailRecord: function () {
            var rows = applyprogram.detailBycondition({
                vote_id: voteinfoprogram.baseDetailId
            })
            if (rows == null) return false;
            $("#dlg-detail-tabs-record-table-tbody").empty();
            if (rows.length == 0) {
                $("#dlg-detail-tabs-record-table-tbody").append("没有人投票");
                return false;
            }
            var voteinfo = voteinfoprogram.selectBycondition({
                id: voteinfoprogram.baseDetailId
            })[0];
            if (voteinfo.is_anonymous == '1') {
                $("#dlg-detail-tabs-record-table-tbody").append("此项目匿名，不显示详细信息");
                return false;
            }
            for (var i = 0; i < rows.length; i++) {
                var detail = "";
                detail += '<tr>';
                detail += '<td>' + rows[i].id + '</td>';
                detail += '<td>' + rows[i].username + '</td>';
                detail += '<td>' + rows[i].realname + '</td>';
                detail += '<td>' + rows[i].number + '</td>';
                if (rows[i].identity == '1') detail += '<td>' + '<span style="color:green;font-family:微软雅黑;">普通用户</span>' + '</td>';
                else detail += '<td>' + '<span style="color:red;font-family:微软雅黑;">管理员</span>' + '</td>';
                detail += '<td>' + rows[i].aim_realname + '</td>';
                detail += '<td><div class="vote" data-toggle="popover" data-placement="bottom" title="投票理由" ';
                detail += 'data-content="' + rows[i].introduction + '">点我弹出</div></td>';
                $("#dlg-detail-tabs-record-table-tbody").append(detail);
            }
            $('[data-toggle="popover"]').popover();
        },
        //根据ids删除投票项目，可批量删除
        deleteByids: function (ids) {
            var result = general.sendRequest('../voteinfo/delete', {
                ids: ids
            });
            general.infoShow('Success', result.msg);
            $('#dg').datagrid('reload');
        },
        //通过条件查询投票项目
        selectBycondition: function (data) {
            var result = general.sendRequest('../voteinfo/select', data);
            return result.rows;
        },
        //通过条件查询投票项目细节
        detailBycondition: function (data) {
            var result = general.sendRequest('../voteinfo/detail', data);
            return result;
        }
    };
    applyprogram = {
        //list页：新建或者更新的地址
        listUrl: null,
        //list页：初始化
        listInit: function () {
            $('#dg').datagrid({
                view: detailview,
                detailFormatter: function (index, row) {
                    return '<div style="padding:2px"><table id="ddv-' + index + '"></table></div>';
                },
                onExpandRow: function (index, row) {
                    $('#ddv-' + index).datagrid({
                        url: '../apply/detail?aim_id=' + (row.user_id) + '&vote_id=' + (row.vote_id),
                        fitColumns: true,
                        columns: [
                            [{
                                field: 'id',
                                title: '用户Id'
                            }, {
                                field: 'username',
                                title: '用户名'
                            }, {
                                field: 'realname',
                                title: '真实姓名'
                            }, {
                                field: 'number',
                                title: '学号'
                            }, {
                                field: 'identity',
                                title: '权限',
                                formatter: formatPriceIdentity
                            }, {
                                field: 'introduction',
                                title: '投票理由'
                            },]
                        ],
                        onResize: function () {
                            $('#dg').datagrid('fixDetailRowHeight', index);
                        },
                        onLoadSuccess: function () {
                            setTimeout(function () {
                                $('#dg').datagrid('fixDetailRowHeight', index);
                                $('#dg').datagrid('fixRowHeight', index);
                            }, 0);
                        }
                    });
                    $('#dg').datagrid('fixDetailRowHeight', index);
                }
            });
        },
        //list页：打开新建申请的窗口，并修改地址
        listNew: function () {
            $('#dlg').dialog('open').dialog('setTitle', '新建申请');
            $('#fm').form('clear');
            $("input[name=is_accept]").get(0).checked = true;
            applyprogram.listUrl = '../apply/insert';
        },
        //list页：打开更新申请的窗口，并修改地址
        listUpdate: function () {
            var row = $('#dg').datagrid('getSelected');
            if (row) {
                $('#dlg').dialog('open').dialog('setTitle', '更改申请信息');
                $('#fm').form('load', row);
                applyprogram.listUrl = '../apply/update?id=' + row.id;
            } else {
                general.infoShow('Error', '请选中一行数据');
            }
        },
        //list页：保存申请数据
        listSave: function () {
            $('#fm').form('submit', {
                url: applyprogram.listUrl,
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.result == 0) {
                        general.infoShow('Error', result.msg);
                    } else {
                        general.infoShow('Success', result.msg);
                        $('#dlg').dialog('close');
                        $('#dg').datagrid('reload');
                    }
                }
            });
        },
        //list页：删除前询问是否确认
        listDestroy: function () {
            var rows = $('#dg').datagrid('getSelections');
            if (rows.length != 0) {
                $.messager.confirm('确认', '删除申请会同时删除与此申请相关的投票，确认删除吗?', function (r) {
                    if (r) {
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (i != 0) ids += ",";
                            ids = ids + rows[i].id;
                        }
                        applyprogram.deleteByids(ids);
                    }
                });
            } else {
                general.infoShow('Error', 至少选中一行数据);
            }
        },
        //list页：查询申请
        listSearch: function () {
            $('#dg').datagrid('load', {
                id: $("#id").val(),
                vote_id: $("#vote_id").val(),
                user_id: $("#user_id").val(),
                is_accept: $("#is_accept").combobox('getValue')
            });
        },
        //base页：新建或者更新的地址
        baseUrl: null,
        //base页：新建或者更新保存的数据
        baseData: null,
        //base页：打开新建申请的窗口，并修改地址
        baseNew: function (id) {
            var top = window.pageXOffset + 150;
            var left = window.pageYOffset + 200;
            $('#dlg-apply').window({
                top: top,
                left: left,
                title: '提交申请'
            });
            $('#dlg-apply').form('clear');
            $("#dlg-apply").dialog("open");
            applyprogram.baseUrl = "../apply/insert";
            applyprogram.baseData = {
                vote_id: id,
                user_id: userprogram.checkLogin().id,
                is_accept: 0
            };
        },
        //base页：打开更新申请的窗口，并修改地址
        baseUpdate: function (id) {
            var top = window.pageYOffset + 150;
            var left = window.pageXOffset + 200;
            $('#dlg-apply').window({
                top: top,
                left: left,
                title: '修改申请'
            });
            var applylists = applyprogram.selectBycondition({
                user_id: userprogram.checkLogin().id,
                vote_id: id
            });
            $("#fm-apply").form('load', applylists[0]);
            $("#dlg-apply").dialog("open");
            applyprogram.baseUrl = "../apply/update";
            applyprogram.baseData = {
                id: applylists[0].id,
                vote_id: id,
                user_id: userprogram.checkLogin().id,
                is_accept: applylists[0].is_accept
            };
        },
        //base页：保存申请数据
        baseSave: function () {
            if ($("#fm-apply").form('validate') == false) return false;
            applyprogram.baseData.introduction = $("#introduction_apply").val();
            var result = general.sendRequest(applyprogram.baseUrl, applyprogram.baseData);
            if (result.result == '1') {
                voteinfoprogram.baseInit();
                general.infoShow('Success', result.msg);
                $("#dlg-apply").dialog("close");
            } else {
                general.infoShow('Error', result.msg);
            }
        },
        //base页：初始化
        baseInit: function () {
            var user = userprogram.checkLogin();
            var rows = applyprogram.selectBycondition({
                user_id: user.id
            });
            if (rows == null) return false;
            $("#table-tbody").empty();
            for (var i = 0; i < rows.length; i++) {
                var applyall = "";
                var votenifo = voteinfoprogram.selectBycondition({
                    id: rows[i].vote_id
                })[0];
                applyall += '<tr>';
                applyall += '<td>' + votenifo.title + '</td>';
                if (rows[i].is_accept == '0') applyall += '<td><span style="color:red;font-family:微软雅黑;">未通过</span></td>';
                else applyall += '<td><span style="color:green;font-family:微软雅黑;">已通过</span></td>';
                applyall += '<td><div data-toggle="popover" data-placement="bottom" title="申请理由" ';
                applyall += 'data-content="' + rows[i].introduction + '">点我弹出</div></td>';
                applyall += '<td>' + recordprogram.selectBycondition({
                        vote_id: rows[i].vote_id,
                        aim_id: user.id
                    }).length + '</td>';
                applyall += '</tr>';
                $("#table-tbody").append(applyall);
            }
            $('[data-toggle="popover"]').popover();
        },
        //根据ids删除申请，可批量删除
        deleteByids: function (ids) {
            var result = general.sendRequest('../apply/delete', {
                ids: ids
            });
            general.infoShow('Success', result.msg);
            $('#dg').datagrid('reload');
        },
        //通过条件查询申请
        selectBycondition: function (data) {
            var result = general.sendRequest('../apply/select', data);
            return result.rows;
        },
        //通过条件查询申请细节
        detailBycondition: function (data) {
            var result = general.sendRequest('../apply/detail', data);
            return result.rows;
        }
    };
    recordprogram = {
        //list页：新建或者更新的地址
        listUrl: null,
        //list页：打开新建记录的窗口，并修改地址
        listNew: function () {
            $('#dlg').dialog('open').dialog('setTitle', '新建投票记录');
            $('#fm').form('clear');
            recordprogram.listUrl = '../record/insert';
        },
        //list页：打开更新记录的窗口，并修改地址
        listUpdate: function () {
            var row = $('#dg').datagrid('getSelected');
            if (row) {
                $('#dlg').dialog('open').dialog('setTitle', '更改投票记录');
                $('#fm').form('load', row);
                recordprogram.listUrl = '../record/update?id=' + row.id;
            } else {
                general.infoShow('Error', '请选中一行数据');
            }
        },
        //list页：保存记录数据
        listSave: function () {
            $('#fm').form('submit', {
                url: recordprogram.listUrl,
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.result == 0) {
                        general.infoShow('Error', result.msg);
                    } else {
                        general.infoShow('Success', result.msg);
                        $('#dlg').dialog('close');
                        $('#dg').datagrid('reload');
                    }
                }
            });
        },
        //list页：删除前询问是否确认
        listDestroy: function () {
            var rows = $('#dg').datagrid('getSelections');
            if (rows.length != 0) {
                $.messager.confirm('确定', '请确定删除此投票记录?', function (r) {
                    if (r) {
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (i != 0) ids += ",";
                            ids = ids + rows[i].id;
                        }
                        recordprogram.deleteByids(ids);
                    }
                });
            } else {
                general.infoShow('Error', '至少选中一行数据');
            }
        },
        //list页：查询记录
        listSearch: function () {
            $('#dg').datagrid('load', {
                id: $("#id").val(),
                vote_id: $("#vote_id").val(),
                send_id: $("#send_id").val(),
                aim_id: $("#aim_id").val(),
                introduction: $("#introduction").val()
            });
        },
        //base页：新建或者更新的地址
        baseUrl: null,
        //base页：新建或者更新保存的数据
        baseData: null,
        //base页：打开新建记录的窗口，并修改地址
        baseNew: function (id) {
            var top = window.pageYOffset + 150;
            var left = window.pageXOffset + 200;
            $('#aim_id').combobox({
                url: '../voteinfo/detail?vote_id=' + id + '&is_accept=1',
                valueField: 'id',
                textField: 'realname'
            });
            $('#dlg-record').window({
                top: top,
                left: left,
                title: '进行投票'
            });
            $('#dlg-record').form('clear');
            $("#dlg-record").dialog("open");
            recordprogram.baseUrl = "../record/insert";
            recordprogram.baseData = {
                vote_id: id,
                send_id: userprogram.checkLogin().id
            };
        },
        //base页：打开更新记录的窗口，并修改地址
        baseUpdate: function (id) {
            var top = window.pageYOffset + 150;
            var left = window.pageXOffset + 200;
            $('#aim_id').combobox({
                url: '../voteinfo/detail?vote_id=' + id + '&is_accept=1',
                valueField: 'id',
                textField: 'realname'
            });
            $('#dlg-record').window({
                top: top,
                left: left,
                title: '修改投票'
            });
            var recordlists = recordprogram.selectBycondition({
                send_id: userprogram.checkLogin().id,
                vote_id: id
            });
            $("#fm-record").form('load', recordlists[0]);
            $("#aim_id").combobox('setValues', recordlists[0].aim_id + '');
            $("#dlg-record").dialog("open");
            recordprogram.baseUrl = "../record/update";
            recordprogram.baseData = {
                id: recordlists[0].id,
                vote_id: id,
                send_id: userprogram.checkLogin().id
            };
        },
        //base页：保存记录数据
        baseSave: function () {
            if ($("#fm-record").form('validate') == false) return false;
            recordprogram.baseData.aim_id = $("#aim_id").combobox('getValue');
            recordprogram.baseData.introduction = $("#introduction_record").val();
            var result = general.sendRequest(recordprogram.baseUrl, recordprogram.baseData);
            if (result.result == '1') {
                voteinfoprogram.baseInit();
                general.infoShow('Success', result.msg);
                $("#dlg-record").dialog("close");
            } else {
                general.infoShow('Error', result.msg);
            }
        },
        //base页：初始化
        baseInit: function () {
            var user = userprogram.checkLogin();
            var rows = recordprogram.selectBycondition({
                send_id: user.id
            });
            if (rows == null) return false;
            $("#table-tbody").empty();
            for (var i = 0; i < rows.length; i++) {
                var recordall = "";
                var votenifo = voteinfoprogram.selectBycondition({
                    id: rows[i].vote_id
                })[0];
                recordall += '<tr>';
                recordall += '<td>' + votenifo.title + '</td>';
                var userindex = userprogram.selectBycondition({
                    id: rows[i].aim_id
                })[0];
                recordall += '<td>' + userindex.username + '</td>';
                recordall += '<td>' + userindex.realname + '</td>';
                recordall += '<td>' + userindex.number + '</td>';
                recordall += '<td><div data-toggle="popover" data-placement="bottom" title="投票理由" ';
                recordall += 'data-content="' + rows[i].introduction + '">点我弹出</div></td>';
                recordall += '</tr>';
                $("#table-tbody").append(recordall);
            }
            $('[data-toggle="popover"]').popover();
        },
        //根据ids删除记录，可批量删除
        deleteByids: function (ids) {
            var result = general.sendRequest('../record/delete', {
                ids: ids
            });
            general.infoShow('Success', result.msg);
            $('#dg').datagrid('reload');
        },
        //通过条件查询投票记录
        selectBycondition: function (data) {
            var result = general.sendRequest('../record/select', data);
            return result.rows;
        }
    };
    indexprogram = {
        //初始化
        indexInit: function () {
            var user = userprogram.checkLogin();
            if (user != null) {
                indexprogram.getLogInfo(user);
            }
            $('#body').layout();
            indexprogram.setHeight();
        },
        //填充用户信息
        getLogInfo: function (user) {
            $("#username").append(user.username);
            var role;
            if (user.identity == 1) {
                role = "普通用户";
            } else if (user.identity == 2) {
                role = "管理员";
                $('#aa').accordion('add', {
                    id: 'administrator',
                    title: '管理菜单',
                    iconCls: 'icon-cut',
                    selected: false
                });
                var admin = "";
                admin += '<ul class="easyui-tree" id="admin">';
                admin += '<li><a href="userList.html" target="contentWindow" >用户信息管理</a></li>';
                admin += '<li><a href="voteinfoList.html" target="contentWindow" >投票项目管理</a></li>';
                admin += '<li><a href="applyList.html" target="contentWindow" >竞选申请管理</a></li>';
                admin += '<li><a href="recordList.html" target="contentWindow" >投票记录管理</a></li>';
                admin += '</ul>';
                $("#administrator").append(admin);
                $.parser.parse();
            }
            $("#userrole").append(role);
        },
        //设置高度
        setHeight: function () {
            var c = $('#body');
            var p = c.layout('panel', 'center');
            var oldHeight = p.panel('panel').outerHeight();
            p.panel('resize', {
                height: 'auto'
            });
            //p.panel('resize', {height:'650px'});
            var newHeight = p.panel('panel').outerHeight();
            c.layout('resize', {
                height: (c.height() + newHeight - oldHeight)
            });
        }
    };
    general = {
        //显示一条消息窗口
        infoShow: function (title, msg) {
            $.messager.show({
                title: title,
                msg: msg,
                timeout: 2000
            });
        },
        //向后台发送请求，类型为：同步
        sendRequest: function (url, data) {
            var results = null;
            $.ajax({
                type: "post",
                content: "application/x-www-form-urlencoded;charset=UTF-8",
                url: url,
                dataType: 'json',
                async: false,
                data: data,
                success: function (result) {
                    results = result;
                },
                error: function () {
                    general.infoShow('Error', '发生未知错误！');
                }
            });
            return results;
        }
    };
});

function formatPriceIdentity(value, row, index) {
    if (value == "1") {
        return '<span style="color:green;font-family:微软雅黑;">' + '普通用户' + '</span>';
    } else {
        return '<span style="color:red;font-family:微软雅黑;">' + '管理员' + '</span>';
    }
}

function formatPricestate(value, row, index) {
    if (value == "1") {
        return '<span style="color:orange;font-family:微软雅黑;">' + '正在申请' + '</span>';
    } else if (value == "2") {
        return '<span style="color:green;font-family:微软雅黑;">' + '正在进行' + '</span>';
    } else {
        return '<span style="color:#ccc;font-family:微软雅黑;">' + '已经结束' + '</span>';
    }
}

function formatPriceis_anonymous(value, row, index) {
    if (value == "1") {
        return '<span style="color:green;font-family:微软雅黑;">' + '匿名' + '</span>';
    } else {
        return '<span style="color:red;font-family:微软雅黑;">' + '实名' + '</span>';
    }
}

function formatPricegettotal(value, userrow, index) {
    if (userrow.is_accept == '0') return '<span style="color:red;font-family:微软雅黑;">' + '未通过' + '</span>';
    else return userrow.count;
}

function formatPriceis_accept(value, row, index) {
    if (value == "1") {
        return '<span style="color:green;font-family:微软雅黑;">' + '已通过' + '</span>';
    } else {
        return '<span style="color:red;font-family:微软雅黑;">' + '未通过' + '</span>';
    }
}
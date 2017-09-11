var ajaxUrl = 'ajax/profile/meals/';
var ajaxFilterUri= 'ajax/profile/meals/filter';
var filterData;
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable1").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();
});


function undoFilterTable() {
    $("#filterDetails").find(":input").val("");
    filterData=null;
    newUpdateTable();
}

function newUpdateTable() {
    var form = $("#filterDetails");
    filterData=form.serialize();
    $.get(ajaxFilterUri,filterData, function (data) {
        datatableApi.clear().rows.add(data).draw();
    })
}
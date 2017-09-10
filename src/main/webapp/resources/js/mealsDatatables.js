var ajaxUrl = 'ajax/profile/meals/';
var ajaxFilterUri= 'ajax/profile/meals/filter';
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
    updateTable();
}

function filterDate() {
    var form = $("#filterDetails");
    $.get(ajaxFilterUri,form.serialize(), function (data) {
        datatableApi.clear().rows.add(data).draw();
    })
}
    function requestRecord() {
        let dateOfRecord = getStringDate(today);
        let requestUrl = '/records/' + dateOfRecord;
        $.ajax({
            url: requestUrl,
            data: {dateOfRecord: dateOfRecord},
            dataType: "html",
            method: "GET",
            success: function (response) {
                extendSessionTime();
                let html = $(response);
                let homeContainer = $('.home-container');
                let contentRight = $('.content-right');
                replaceContent(homeContainer, contentRight, html);
            }
        })
    }

    function requestCreateRecord() {
        let dateOfRecord = getStringDate(today);
        let requestUrl = '/records/' + dateOfRecord;
        $.ajax({
            url: requestUrl,
            dataType: "html",
            method: "POST",
            success: function (response) {
                extendSessionTime();
                let html = $(response);
                let homeContainer = $('.home-container');
                let contentRight = $('.content-right');
                replaceContent(homeContainer, contentRight, html);
            }
        })
    }

    function requestUpdateForm() {
        let recordId = $('#recordId').val();
        let requestUrl = '/records/' + recordId + '/update-form';
        console.log(requestUrl);
        $.ajax({
            url: requestUrl,
            dataType: "html",
            method: "GET",
            success: function (response) {
                extendSessionTime();
                let updateForm = $(response);
                let recordFields = $('#record-fields');
                let recordExist = $('.record-exist');
                recordFields.hide();
                recordExist.prepend(updateForm);
            }
        })
    }

    function cancelUpdateRecord() {
        let updateForm = $('#record-update-form');
        let recordFields = $('#record-fields');
        updateForm.remove();
        recordFields.show();
    }

    function requestUpdateRecord() {
        let recordId = $('#recordId').val();
        let requestUrl = '/records/' + recordId;
        let formData = $('#record-update-form').serialize();
        $.ajax({
            url: requestUrl,
            data: formData,
            dataType: "html",
            method: "PUT",
            success: function (response) {
                extendSessionTime();
                let html = $(response);
                let homeContainer = $('.home-container');
                let contentRight = $('.content-right');
                replaceContent(homeContainer, contentRight, html);
            }
        })
    }

    function requestRemoveRecord() {
        let recordId = $('#recordId').val();
        let requestUrl = '/records/' + recordId;
        $.ajax({
            url: requestUrl,
            dataType: "html",
            method: "DELETE",
            success: function (response) {
                extendSessionTime();
                let html = $(response);
                let homeContainer = $('.home-container');
                let contentRight = $('.content-right');
                replaceContent(homeContainer, contentRight, html);
            }
        })
    }

    function replaceContent(parent, target, subs) {
        target.remove();
        parent.append(subs);
    }

    function getStringDate(date) {
        let d_year = date.getFullYear().toString();
        let d_month = date.getMonth() + 1;
        let d_date = date.getDate();
        if (d_month < 10) {
            d_month = "0" + d_month ;
        }
        if (d_date < 10) {
            d_date = "0" + d_date;
        }
        return (d_year + "-" + d_month + "-" + d_date);
    }

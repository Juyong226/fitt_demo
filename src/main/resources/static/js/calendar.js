    var calendarTitle = $('#current-year-month');
    var calendarBody = $('#calendar-body');
    var today = new Date();
    var start = new Date(today.getFullYear(), today.getMonth(), 1);
    var monthList = ['January', 'February', 'March', 'April', 'May', 'June',
        'July', 'August', 'September', 'October', 'November', 'December'];
    var leapYear=[31,29,31,30,31,30,31,31,30,31,30,31];
    var notLeapYear=[31,28,31,30,31,30,31,31,30,31,30,31];
    var pageStart = start;
    var pageYear;

    if (start.getFullYear() % 4 === 0) {
        pageYear = leapYear;
    } else {
        pageYear = notLeapYear;
    }

    var dates = [];
    var targetDate;
    function drawCalendar() {
        let weekCount = 100;
        let dayCount = 1;
        for (let i=0; i<6; i++) {
            let tr = $('<tr>');
            tr.attr('id', weekCount);
            for (let j=0; j<7; j++) {
                if ((i === 0 && j < start.getDay()) || dayCount > pageYear[start.getMonth()]) {
                    let td = $('<td>');
                    tr.append(td);
                } else {
                    let td = $('<td>');
                    td.text(dayCount);
                    td.attr('id', dayCount);
                    td.attr('class', 'date');
                    tr.append(td);
                    dayCount++;
                }
            }
            weekCount++;
            calendarTitle.html(monthList[start.getMonth()] + '&nbsp;&nbsp;&nbsp;&nbsp;' + start.getFullYear());
            calendarBody.append(tr);
            targetDate = $('#' + today.getDate());
            targetDate.addClass('selected');
            addClickEvent();
        }
    }
    drawCalendar();

    function addClickEvent() {
        for (let i=1; i<=pageYear[start.getMonth()]; i++) {
            dates[i] = $('#' + i);
            dates[i].click(getSelected);
        }
    }

    function getSelected(event) {
        for (let i=1; i<=pageYear[start.getMonth()]; i++) {
            if (dates[i].hasClass('selected')) {
                dates[i].removeClass('selected');
            }
        }
        targetDate = $(event.target);
        targetDate.addClass('selected');
        today = new Date(today.getFullYear(), today.getMonth(), targetDate.attr('id'));
    }

    function removeCalendar() {
        let weekCount = 100;
        for (let i = 100; i < 106; i++) {
            let tr = $('#' + weekCount);
            tr.remove();
            weekCount++;
        }
    }

    function prev() {
        if (pageStart.getMonth() === 1) {
            pageStart = new Date(start.getFullYear() - 1, 12, 1);
            start = pageStart;
            if (start.getFullYear() % 4 === 0) {
                pageYear = leapYear;
            } else {
                pageYear = notLeapYear;
            }
        } else {
            pageStart = new Date(start.getFullYear(), start.getMonth() - 1, 1);
            start = pageStart;
        }
        today = new Date(today.getFullYear(), today.getMonth() - 1, today.getDate());
        calendarTitle.html(monthList[start.getMonth()] + '&nbsp;&nbsp;&nbsp;&nbsp;' + start.getFullYear());
        removeCalendar();
        drawCalendar();
        targetDate = $('#' + today.getDate());
        targetDate.addClass('selected');
        addClickEvent();
    }

    function next() {
        if (pageStart.getMonth() === 12) {
            pageStart = new Date(start.getFullYear() + 1, 1, 1);
            start = pageStart;
            if (start.getFullYear() % 4 === 0) {
                pageYear = leapYear;
            } else {
                pageYear = notLeapYear;
            }
        } else {
            pageStart = new Date(start.getFullYear(), start.getMonth() + 1, 1);
            start = pageStart;
        }
        today = new Date(today.getFullYear(), today.getMonth() + 1, today.getDate());
        calendarTitle.html(monthList[start.getMonth()] + '&nbsp;&nbsp;&nbsp;&nbsp;' + start.getFullYear());
        removeCalendar();
        drawCalendar();
        targetDate = $('#' + today.getDate());
        targetDate.addClass('selected');
        addClickEvent();
    }






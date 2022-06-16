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
            /**
             * jQuery 를 통해 DOM 요소에 이벤트 핸들러를 달 때 주의점.
             * - 이벤트 발생 시 호출되는 함수를 재정의(수정이든 뭐든)하면,
             * 해당 요소에 재정의된 함수가 새로운 이벤트 함수로 교체되는 것이 아니라
             * 기존에 있던 이벤트 핸들러와 더불어 재정의된 함수를 호출하는 새로운 이벤트 핸들러를 추가 등록한다.
             * 따라서 이 경우 이벤트 발생 시 함수가 2개 호출된다. (같은 함수 재정의 시 같은 함수가 2번 호출됨)
             *
             * - jQuery off() 를 통해 누적되어 등록되어 있는 이벤트 핸들러들을 먼저 제거하고,
             * 새로운 이벤트 핸들러를 달아줌으로써 함수 중복 호출 문제를 해결할 수 있다.
             * */
            dates[i].off().click(getSelected);
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
        requestRecord();
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







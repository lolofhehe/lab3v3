const MARGIN = 25;

$(document).ready(function () {
    setTime();
    setInterval(setTime, 10000);
});

function setTime() {

    let canvas = $("#time-canvas");
    let context = canvas.get(0).getContext("2d");

    let coords = canvas.get(0).getBoundingClientRect();
    let width = coords.right - coords.left;
    let height = coords.bottom - coords.top;

    let centerX = width / 2;
    let centerY = height / 2;

    let bgColor = "#888685";
    let secondColor = "#780";
    let fgColor = "#000";

    let clockRadius = centerX - MARGIN;
    let hourAngle = Math.PI / 6; // angle(h) = (3 - i) * hourAngle
    let secondAngle = Math.PI / 30; // angle(s) = (15 - i) * secondAngle

    let drawPartOfRadius = function(angle, start, end, color) {
        let radiusX = clockRadius * Math.cos(angle);
        let radiusY = clockRadius * Math.sin(angle);

        context.strokeStyle = color;
        context.beginPath();
        context.moveTo(centerX + radiusX * start, centerY - radiusY * start);
        context.lineTo(centerX + radiusX * end, centerY - radiusY * end);
        context.stroke();
    };

    context.fillStyle = bgColor;
    context.fillRect(0, 0, width, height);

    context.lineWidth = 20;
    context.strokeStyle = fgColor;
    context.beginPath();
    context.arc(centerX, centerY, clockRadius, 0, 2 * Math.PI);
    context.stroke();

    context.lineWidth = 5;
    for (let i = 0; i < 12; i++) {
        drawPartOfRadius(hourAngle * (3 - i), 0.9, 1, fgColor);
    }

    let date = new Date();
    $("#date-span").text(date.toLocaleDateString());

    context.lineWidth = 10;
    drawPartOfRadius(hourAngle * ( 3 - date.getHours() ), 0, 0.5, fgColor);

    context.lineWidth = 5;
    drawPartOfRadius(secondAngle * ( 15 - date.getMinutes() ), 0, 0.7, fgColor);
    drawPartOfRadius(secondAngle * ( 15 - date.getSeconds() ), 0, 0.7, secondColor);

    context.fillStyle = fgColor;
    context.beginPath();
    context.arc(centerX, centerY, 10, 0, 2 * Math.PI);
    context.fill();
}



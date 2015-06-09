//helper functions, it turned out chrome doesn't support Math.sgn()
function signum(x) {
    return (x < 0) ? -1 : 1;
}
function absolute(x) {
    return (x < 0) ? -x : x;
}

function drawPath(svg, path, startX, startY, endX, endY) {
    // get the path's stroke width (if one wanted to be  really precize, one could use half the stroke size)
    var stroke = parseFloat(path.attr("stroke-width"));
    // check if the svg is big enough to draw the path, if not, set heigh/width
    if (svg.attr("height") < endY)                 svg.attr("height", endY);
    if (svg.attr("width") < (startX + stroke))    svg.attr("width", (startX + stroke));
    if (svg.attr("width") < (endX + stroke))    svg.attr("width", (endX + stroke));

    var deltaX = (endX - startX) * 0.15;
    var deltaY = (endY - startY) * 0.15;
    // for further calculations which ever is the shortest distance
    var delta = deltaY < absolute(deltaX) ? deltaY : absolute(deltaX);

    // set sweep-flag (counter/clock-wise)
    // if start element is closer to the left edge,
    // draw the first arc counter-clockwise, and the second one clock-wise
    var arc1 = 0;
    var arc2 = 1;
    if (startX > endX) {
        arc1 = 1;
        arc2 = 0;
    }
    // draw tha pipe-like path
    // 1. move a bit down, 2. arch,  3. move a bit to the right, 4.arch, 5. move down to the end
    path.attr("d", "M" + startX + " " + startY +
        " V" + (startY + delta) +
        " A" + delta + " " + delta + " 0 0 " + arc1 + " " + (startX + delta * signum(deltaX)) + " " + (startY + 2 * delta) +
        " H" + (endX - delta * signum(deltaX)) +
        " A" + delta + " " + delta + " 0 0 " + arc2 + " " + endX + " " + (startY + 3 * delta) +
        " V" + endY);
}

function connectElements(svg, path, startElem, endElem) {
    var svgContainer = $("#svgContainer");

    // if first element is lower than the second, swap!
    if (startElem.offset() != null && endElem.offset() != null && startElem.offset().top > endElem.offset().top) {
        var temp = startElem;
        startElem = endElem;
        endElem = temp;
    }

    // get (top, left) corner coordinates of the svg container
    if (svgContainer.offset() != null) {
        var svgTop = svgContainer.offset().top;
        var svgLeft = svgContainer.offset().left;
    }

    // get (top, left) coordinates for the two elements
    var startCoord = startElem.offset();
    var endCoord = endElem.offset();

    // calculate path's start (x,y)  coords
    // we want the x coordinate to visually result in the element's mid point
    if (startCoord != null) {
        var startX = startCoord.left + 0.5 * startElem.outerWidth() - svgLeft;    // x = left offset + 0.5*width - svg's left offset
        var startY = startCoord.top + startElem.outerHeight() - svgTop;        // y = top offset + height - svg's top offset
    }

    // calculate path's end (x,y) coords
    if (endCoord != null) {
        var endX = endCoord.left + 0.5 * endElem.outerWidth() - svgLeft;
        var endY = endCoord.top - svgTop;
    }

    // call function for drawing the path
    drawPath(svg, path, startX, startY, endX, endY);

}


function connectAll() {
// connect all the paths you want!
    var incr = 1;
    var nginx = $(".column-two #nginxImage");
    var tomcatImage = $(".column-two #tomcatImage");
    var mysqlImage = $(".column-two #mysqlImage");
    var mongodbImage = $(".column-two #mongodbImage");

    if (nginx != null && tomcatImage != null) {
        connectElements($("#svg1"), $("#path" + (incr++)), nginx, tomcatImage);
    }
    if (tomcatImage != null && mysqlImage != null) {
        connectElements($("#svg1"), $("#path" + (incr++)), tomcatImage, mysqlImage);
    }
    if (tomcatImage != null && mongodbImage != null) {
        connectElements($("#svg1"), $("#path" + (incr++)), tomcatImage, mongodbImage);
    }
//    connectElements($("#svg1"), $("#path1"), $("#teal"),   $("#orange"));
    /*var nginx = $(".column-two #nginxImage");
     var tomcatImage1 = $(".column-two #tomcatImage1");
     var tomcatImage2 = $(".column-two #tomcatImage2");
     var mysqlImage1 = $(".column-two #mysqlImage1");
     var mysqlImage2 = $(".column-two #mysqlImage2");

     var mongodbImage = $(".column-two #mongodbImage");
     if (nginx != null && tomcatImage1 != null) {
     connectElements($("#svg1"), $("#path" + (incr++)), nginx, tomcatImage1);
     }

     if (nginx != null && tomcatImage2 != null) {
     connectElements($("#svg1"), $("#path" + (incr++)), nginx, tomcatImage2);
     }
     if (tomcatImage1 != null && mysqlImage1 != null) {
     connectElements($("#svg1"), $("#path" + (incr++)), tomcatImage1, mysqlImage1);
     }
     if (tomcatImage2 != null && mysqlImage1 != null) {
     connectElements($("#svg1"), $("#path" + (incr++)), tomcatImage2, mysqlImage1);
     }
     if (tomcatImage1 != null && mysqlImage2 != null) {
     connectElements($("#svg1"), $("#path" + (incr++)), tomcatImage1, mysqlImage2);
     }

     if (tomcatImage2 != null && mysqlImage2 != null) {
     connectElements($("#svg1"), $("#path" + (incr++)), tomcatImage2, mysqlImage2);
     }
     if (tomcatImage1 != null && mongodbImage != null) {
     connectElements($("#svg1"), $("#path" + (incr++)), tomcatImage1, mongodbImage);
     }
     if (tomcatImage2 != null && mongodbImage != null) {
     connectElements($("#svg1"), $("#path" + (incr++)), tomcatImage2, mongodbImage);
     }*/
    /*connectElements($("#svg1"), $("#path2"), $("#red"),    $("#orange"));
     connectElements($("#svg1"), $("#path3"), $("#teal"),   $("#aqua")  );
     connectElements($("#svg1"), $("#path4"), $("#red"),    $("#aqua")  );
     connectElements($("#svg1"), $("#path5"), $("#purple"), $("#teal")  );
     connectElements($("#svg1"), $("#path6"), $("#orange"), $("#green") );*/

}

$(document).ready(function () {
    // reset svg each time
    $("#svg1").attr("height", "0");
    $("#svg1").attr("width", "0");
    connectAll();
});

$(window).resize(function () {
    // reset svg each time
    $("#svg1").attr("height", "0");
    $("#svg1").attr("width", "0");
    connectAll();
});

var count = 0;
function test(id) {
    count++;
    if (count == 2) {
        connectElements($("#svg1"), $("#path3"), $(".column-two #nginxImage"), $(".column-two #tomcatImage"));
    }
//    connectElements($("#svg1"), $("#path3"), $("#teal"),   $("#orange"));
}

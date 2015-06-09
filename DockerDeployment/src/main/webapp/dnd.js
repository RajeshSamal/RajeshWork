function drag_start(event) {
    var style = window.getComputedStyle(event.target, null);
    var str = (event.currentTarget.clientWidth) + ','
        + (event.srcElement.clientHeight) + ','
        + event.target.id;
    event.dataTransfer.setData("Text", str);
    event.dataTransfer.effectAllowed = "copy";
}
var tomcatCount = 1;
var mysqlCount = 1;
function drop(event) {
    event.preventDefault();
    var offset = event.dataTransfer.getData("Text").split(',');
    var dm = document.getElementById(offset[2]);
    var dup = dm.cloneNode(true);
    /*if (dup.id == "tomcatImage") {
        dup.id = dup.id + "" + tomcatCount;
        tomcatCount++;
    }
    if (dup.id == "mysqlImage") {
        dup.id = dup.id + "" + mysqlCount;
        mysqlCount++;
    }*/
    dup.style.position = 'absolute';
    dup.style.left = (event.clientX - parseInt(offset[0], 10) - 50) + 'px';
    dup.style.top = (event.clientY - event.target.offsetTop - 50) + 'px';

    event.target.appendChild(dup.cloneNode(true));
    connectAll();
}

function drag_over(event) {
    event.preventDefault();
    return false;
}


var w = 1000,
    h = 600

var color = d3.scale.category10();


var rec = rec()

    .size([w, h]);


var vis = d3.select("body").append("svg:svg")
    .attr("width", w)
    .attr("height", h);

d3.json('vis', function(json) {
    rec
        .nodes(json.nodes)
        .links(json.links)
        .size([w, h])
        .start();

    var info = vis.selectAll("text.labels")
        .data(json.others)
        .enter().append("svg:text")
        .attr("class", "labels")

    info .attr("x", 20)
        .attr("y", 20)
        .text(function(d){return "Recommendation query: " + d.keyword;});

    var nodelegend = vis.selectAll("circle")
        .data(json.nodelegend)
        .enter().append("svg:a") ;

    nodelegend.append("circle")

        .attr("class", "circle")
        .attr("cx", 20)
        .attr("cy", function(d, i) { return 40 + i*20; } )
        .attr("stroke-width", ".5")
        .style("fill", function(d) { return color(d.type); })
        //.style("fill", function(d, i) { switch(i){ case 0: return "DarkGreen"; case 1: return "GoldenRod"; default: return "Maroon"; } } ) // Bar fill color
        .attr("r", 5);

    nodelegend.append("svg:text")
        .attr("class", "nodetext")
        .attr("dx", 40)
        .attr("dy", function(d, i) { return 44 + i*20; } )
        .attr("text-anchor", "start") // text-align: right
        .text(function(d) { return d.type });


    var link = vis.selectAll("line.link")
        .data(json.links)
        .enter().append("svg:line")
        .attr("class", "link")
        .attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; });
       // .on("click", function(d){jumpToInfo(d.source.uri + "/" + d.type + "/" +  d.target.uri)});


    var node = vis.selectAll("g.node")
        .data(json.nodes)
        .enter().append("svg:g")
        .attr("class", "node")
    //  .call(rec.drag);



    node.append("circle")
        .attr("class", "circle")
       // .attr("r", function(d) { return ((d.score*50) + 5);})
        .attr("r", function(d) { return 5})
        .style("fill", function(d) { return color(d.type); })
        .attr("cx", function(d) { return d.x; })
        .attr("cy", function(d) { return d.y; })
        .on("mouseover", function(d){return addtooltip(d);})
        .on("mouseout", function(d){return removetooltip();})
        .on("click", function(d){jumpToInfo(d.uri)}) ;


    function jumpToInfo(x)
    {

        window.open(x,'fullscreen,scrollbars');
    }


    node.append("svg:text")
        .attr("class", "nodetext")
       // .attr("dx", function(d) { return d.x + + (d.score*50) + 5; })
        .attr("dx", function(d) { return d.x + 5; })
        .attr("dy",function(d) { return d.y; })
        .text(function(d) { return d.name });



    var addtooltip = function(d){

        // Tooltip box with information
        d3.select("body")
            .append("div")
            .attr("class", "tooltip")
            .style("top", (d3.event.pageY+10) + "px")
            .style("left",(d3.event.pageX+10) + "px")
            .html("<table><tr><td>Type: </td><td align=left>" + d.type +
            "</td></tr> <tr><td>Department: </td><td align=left>" + d.department +
            "</td></tr> <tr><td>Email: </td><td align=left>" + d.email +
            "</td></tr> <tr><td>Url: </td><td align=left>" + d.uri +
            "</td></tr></table>");
    }


    var removetooltip = function(){
        d3.select("div.tooltip")
            .remove();
        d3.select("#highlight").remove();
    }

});




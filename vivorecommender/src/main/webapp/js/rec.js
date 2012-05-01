
rec = function() {
    var rec = {},
        event = d3.dispatch("start", "tick", "end"),
        size = [1, 1],
        drag,
        nodes = [],
        links = [],
        others = [],
        nodelegend = [],
        strengths,
        radius = 250;


    rec.size = function(x) {
        if (!arguments.length) return size;
        size = x;
        return rec;
    };

    rec.nodes = function(x) {
        if (!arguments.length) return nodes;
        nodes = x;
        return rec;
    };

    rec.others = function(x) {
        if (!arguments.length) return others;
        others = x;
        return rec;
    };

    rec.nodelegend = function(x) {
        if (!arguments.length) return nodelegend;
        nodelegend = x;
        return rec;
    };

    /*
     rec.nodeLegends = function() {
     if (!arguments.length) return nodeLegends;
     // nodes = x;
     return rec;
     }; */

    rec.links = function(x) {
        if (!arguments.length) return links;
        links = x;
        return rec;
    };

    Array.prototype.contains = function ( needle ) {
        for (i in this) {
            if (this[i] == needle) return true;
        }
        return false;
    }


    rec.start = function() {

        var i,

            n = nodes.length,
            m = links.length,
            w = size[0],
            h = size[1],

            o;

        function isEven(value){
            return isFinite(+value) && !(+value % 2)
        }


        for (i = 0; i < n; ++i) {
            o = nodes[i];
            if (i==0) {
                o.x =w/2;
                o.y = h/2;
            }  else
            {

                var   angle = (2*Math.PI*i) /(n-1);
                var x = Math.cos(angle)*radius +w/2;
                var y = Math.sin(angle)*radius + h/2;
                o.x = x;
                o.y =y;

            }
            /*   } else if (isFinite(i) && !(i % 2))    // even
             {
             var   angle = (2*Math.PI*i) /(n-1);
             var x = Math.cos(angle)*radius +w/2;
             var y = Math.sin(angle)*radius + h/2;


             o.x = x;
             o.y =y;

             //  alert("odd")   ;
             }
             else
             {

             var   angle = (2*Math.PI*i) /(n-1) + Math.PI ;
             var x = Math.cos(angle)*radius +w/2;
             var y = Math.sin(angle)*radius + h/2;


             o.x = x;
             o.y =y;
             // alert("even")   ;
             } */

        }


        for (i = 0; i < m; ++i) {
            o = links[i];
            if (typeof o.source == "number") o.source = nodes[o.source];
            if (typeof o.target == "number") o.target = nodes[o.target];

        }
    };

    // use `node.call(rec.drag)` to make nodes draggable
    rec.update = function() {
        for (i = 0; i < n; ++i) {
            o = nodes[i];

            o.x = x;
            o.y =y;

        }};


    return d3.rebind(rec, event, "on");
};












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

/*
 * Set the size of the layout
 */
    rec.size = function(x) {
        if (!arguments.length) return size;
        size = x;
        return rec;
    };

    /*
     * Push all the json nodes into nodes array
     */
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
     * Push all the json links into links array
     */
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

/*
 * Fix the coordinates of ego at the center, coordinates of rest of the nodes 
 * around ego in circle and fix source and target of all the links.
 */
    rec.start = function() {

        var i, // loop iterator

            n = nodes.length, // number of nodes
            m = links.length, // number of links
            w = size[0], // width of layout
            h = size[1], // height of layout

            o; // current node

        for (i = 0; i < n; ++i) {
            o = nodes[i];
            if (i==0) { // put the ego at the center
                o.x =w/2;
                o.y = h/2;
            }  else
            { // arrange rest of the nodes in circle around the ego

                var   angle = (2*Math.PI*i) /(n-1);
                var x = Math.cos(angle)*radius +w/2;
                var y = Math.sin(angle)*radius + h/2;
                o.x = x;
                o.y =y;

            }
        }

        // define source and target for the links
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











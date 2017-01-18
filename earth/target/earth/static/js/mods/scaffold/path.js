define(function(require, exports, module) {
    var slice = Array.prototype.slice;
    var concatPath = function(from, to) {
        var uplevelreg = /^\.\.\//;
        var samelevelreg = /^\.\//;

        if (samelevelreg.test(to)) {
            from = from.replace(/[^\/]*$/, '');
            to = to.replace(samelevelreg, '');
            return concatPath(from, to);
        } else if (uplevelreg.test(to)) {
            from = from.slice(0, from.lastIndexOf('/'));
            to = to.replace(uplevelreg, './');
            return concatPath(from, to);
        } else {
            return from + '/' + to;
        }
    };

    var obj = {};

    obj.join = function() {
        var args = slice.call(arguments, 0);
        var res = args.shift();
        while (args.length > 0) {
            res = concatPath(res, args.shift());
        }
        res = res.replace(/\/{2,}/g, '/');
        return res;
    };

    obj.basename = function() {

    };

    obj.extname = function(filename) {
        var i = filename.lastIndexOf('.');
        return i === -1 ? '' : filename.slice(i, filename.length);
    };

    // obj.searchStringify = function (search) {
    //     search = search.split('&');
    //     var obj = {};
    //     search.map(function (item) {
    //         var temp = item.split('=');
    //         obj[temp[0]] = temp[1];
    //     });
    //     return obj;
    // };

    obj.parseQueryString = function(search) {
        var querys = [];
        var res = {};
        if (search[0] === '?') {
            querys = search.slice(1).split('&');
        } else {
            querys = search.split('&');
        }
        querys.forEach(function(item, index, arr) {
            var pairs = item.split('=');
            var value;

            try {
                value = JSON.parse(pairs[1]);
            } catch (err) {
                value = pairs[1];
            }

            res[pairs[0]] = value;
        });
        return res;
    };

    obj.stringifyQueryObject = function(query) {
        var temp = [];
        for (var prop in query) {
            temp.push(prop + '=' + query[prop]);
        }
        return temp.join('&');
    };

    obj.parse = function(url, parseQueryString) {
        var res = {
            hash: null,
            search: null,
            query: null,
            path: null,
            href: url
        };

        // path
        res.path = url.split(/[#\?]/)[0];

        var remains = url.replace(res.path, '');

        // hash, search
        if (remains[0] === '?') {
            var temp = remains.split('#');
            res.search = temp[0];
            res.query = res.search.slice(1);

            if (temp[1]) {
                res.hash = '#' + temp[1];
            }

        } else if (remains[0] === '#') {
            res.hash = remains;
        }

        if (parseQueryString && res.search) {
            res.query = obj.parseQueryString(res.search);
        }

        return res;
    };

    obj.format = function(options) {
        var def = {
            path: '/',
            query: null,
            hash: null
        };
        var nopt = $.extend({}, def, options);
        var res = [nopt.path];
        if (nopt.query) {
            var search = this.stringifyQueryObject(nopt.query);
            res.push('?' + search);
        }
        if (nopt.hash) {
            res.push(nopt.hash[0] === '#' ? nopt.hash : '#' + nopt.hash);
        }

        var resStr = res.join('');
        return resStr.replace(/\/{2, }/g, '/')
            .replace('(?.*)?', function(full, exp1) {
                return exp1 + '&';
            });
    };

    module.exports = obj;
});
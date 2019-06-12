(function() {
    function t(t) {
        return "function" == typeof t || !1
    }
    function r(t) {
        return "string" == typeof t ? t : null == t ? "" : t + ""
    }
    function n(t) {
        return or[t]
    }
    function e(t) {
        return "\\" + ir[t]
    }
    function o(t) {
        return !!t && "object" == typeof t
    }
    function u(t, r) {
        for (var n = -1, e = t.length, o = -1, u = []; ++n < e; )
            t[n] === r && (t[n] = Pt,
            u[++o] = n);
        return u
    }
    function i() {}
    function a() {}
    function l(t, r, n) {
        this.__wrapped__ = t,
        this.__actions__ = n || [],
        this.__chain__ = !!r
    }
    function c(t) {
        this.__wrapped__ = t,
        this.__actions__ = null,
        this.__dir__ = 1,
        this.__dropCount__ = 0,
        this.__filtered__ = !1,
        this.__iteratees__ = null,
        this.__takeCount__ = Cr,
        this.__views__ = null
    }
    function f() {
        this.__data__ = {}
    }
    function p(t) {
        return this.has(t) && delete this.__data__[t]
    }
    function s(t) {
        return "__proto__" == t ? _t : this.__data__[t]
    }
    function h(t) {
        return "__proto__" != t && dr.call(this.__data__, t)
    }
    function g(t, r) {
        return "__proto__" != t && (this.__data__[t] = r),
        this
    }
    function y(t, r) {
        var n = -1
          , e = t.length;
        for (r || (r = Array(e)); ++n < e; )
            r[n] = t[n];
        return r
    }
    function _(t, r) {
        for (var n = -1, e = t.length; ++n < e && r(t[n], n, t) !== !1; )
            ;
        return t
    }
    function v(t, r, n, e) {
        return t !== _t && dr.call(e, n) ? t : r
    }
    function m(t, r, n) {
        for (var e = -1, o = un(r), u = o.length; ++e < u; ) {
            var i = o[e]
              , a = t[i]
              , l = n(a, r[i], i, t, r);
            (l === l ? l === a : a !== a) && (a !== _t || i in t) || (t[i] = l)
        }
        return t
    }
    function w(t, r) {
        return null == r ? t : d(r, un(r), t)
    }
    function d(t, r, n) {
        n || (n = {});
        for (var e = -1, o = r.length; ++e < o; ) {
            var u = r[e];
            n[u] = t[u]
        }
        return n
    }
    function b(t, r, n) {
        if ("function" != typeof t)
            throw new TypeError(Rt);
        return setTimeout(function() {
            t.apply(_t, n)
        }, r)
    }
    function j(t, r, n) {
        for (var e = -1, u = t.length, i = -1, a = []; ++e < u; ) {
            var l = t[e];
            if (o(l) && W(l) && (n || en(l) || ot(l))) {
                r && (l = j(l, r, n));
                for (var c = -1, f = l.length; ++c < f; )
                    a[++i] = l[c]
            } else
                n || (a[++i] = l)
        }
        return a
    }
    function E(t, r) {
        for (var n = -1, e = r.length, o = -1, u = []; ++n < e; ) {
            var i = r[n];
            on(t[i]) && (u[++o] = i)
        }
        return u
    }
    function T(t) {
        return function(r) {
            return null == r ? _t : B(r)[t]
        }
    }
    function x(t, r) {
        for (var n = -1, e = r.length, o = Array(e); ++n < e; )
            o[n] = t[r[n]];
        return o
    }
    function O(t, r, n) {
        for (var e = n.length, o = -1, u = Pr(t.length - e, 0), i = -1, a = r.length, l = Array(u + a); ++i < a; )
            l[i] = r[i];
        for (; ++o < e; )
            l[n[o]] = t[o];
        for (; u--; )
            l[i++] = t[o++];
        return l
    }
    function S(t, r, n) {
        for (var e = -1, o = n.length, u = -1, i = Pr(t.length - o, 0), a = -1, l = r.length, c = Array(i + l); ++u < i; )
            c[u] = t[u];
        for (var f = u; ++a < l; )
            c[f + a] = r[a];
        for (; ++e < o; )
            c[f + n[e]] = t[u++];
        return c
    }
    function A(t, r) {
        function n() {
            var o = this && this !== hr && this instanceof n ? e : t;
            return o.apply(r, arguments)
        }
        var e = R(t);
        return n
    }
    function R(t) {
        return function() {
            var r = arguments;
            switch (r.length) {
            case 0:
                return new t;
            case 1:
                return new t(r[0]);
            case 2:
                return new t(r[0],r[1]);
            case 3:
                return new t(r[0],r[1],r[2]);
            case 4:
                return new t(r[0],r[1],r[2],r[3]);
            case 5:
                return new t(r[0],r[1],r[2],r[3],r[4])
            }
            var n = Ur(t.prototype)
              , e = t.apply(n, r);
            return it(e) ? e : n
        }
    }
    function P(t) {
        function r(n, e, o) {
            o && D(n, e, o) && (e = null);
            var u = F(n, t, null, null, null, null, null, e);
            return u.placeholder = r.placeholder,
            u
        }
        return r
    }
    function $(t) {
        return function() {
            for (var r, n = arguments.length, e = t ? n : -1, o = 0, u = Array(n); t ? e-- : ++e < n; ) {
                var i = u[o++] = arguments[e];
                if ("function" != typeof i)
                    throw new TypeError(Rt);
                !r && l.prototype.thru && "wrapper" == N(i) && (r = new l([]))
            }
            for (e = r ? -1 : n; ++e < n; ) {
                i = u[e];
                var a = N(i)
                  , c = "wrapper" == a ? Mr(i) : null;
                r = c && M(c[0]) && c[1] == (xt | bt | Et | Ot) && !c[4].length && 1 == c[9] ? r[N(c[0])].apply(r, c[3]) : 1 == i.length && M(i) ? r[a]() : r.thru(i)
            }
            return function() {
                var t = arguments;
                if (r && 1 == t.length && en(t[0]))
                    return r.plant(t[0]).value();
                for (var e = 0, o = n ? u[e].apply(this, t) : t[0]; ++e < n; )
                    o = u[e].call(this, o);
                return o
            }
        }
    }
    function I(t) {
        var r = tt(function(n, e) {
            var o = u(e, r.placeholder);
            return F(n, t, null, e, o)
        });
        return r
    }
    function C(t, r, n, e, o, i, a, l, c, f) {
        function p() {
            for (var d = arguments.length, b = d, j = Array(d); b--; )
                j[b] = arguments[b];
            if (e && (j = O(j, e, o)),
            i && (j = S(j, i, a)),
            _ || m) {
                var E = p.placeholder
                  , T = u(j, E);
                if (d -= T.length,
                f > d) {
                    var x = l ? y(l) : null
                      , A = Pr(f - d, 0)
                      , P = _ ? T : null
                      , $ = _ ? null : T
                      , I = _ ? j : null
                      , k = _ ? null : j;
                    r |= _ ? Et : Tt,
                    r &= ~(_ ? Tt : Et),
                    v || (r &= ~(mt | wt));
                    var F = [t, r, n, I, P, k, $, x, c, A]
                      , N = C.apply(_t, F);
                    return M(t) && Vr(N, F),
                    N.placeholder = E,
                    N
                }
            }
            var L = h ? n : this
              , W = g ? L[t] : t;
            return l && (j = q(j, l)),
            s && c < j.length && (j.length = c),
            this && this !== hr && this instanceof p && (W = w || R(t)),
            W.apply(L, j)
        }
        var s = r & xt
          , h = r & mt
          , g = r & wt
          , _ = r & bt
          , v = r & dt
          , m = r & jt
          , w = g ? null : R(t);
        return p
    }
    function k(t, r, n, e) {
        function o() {
            for (var r = -1, a = arguments.length, l = -1, c = e.length, f = Array(a + c); ++l < c; )
                f[l] = e[l];
            for (; a--; )
                f[l++] = arguments[++r];
            var p = this && this !== hr && this instanceof o ? i : t;
            return p.apply(u ? n : this, f)
        }
        var u = r & mt
          , i = R(t);
        return o
    }
    function F(t, r, n, e, o, u, i, a) {
        var l = r & wt;
        if (!l && "function" != typeof t)
            throw new TypeError(Rt);
        var c = e ? e.length : 0;
        if (c || (r &= ~(Et | Tt),
        e = o = null),
        c -= o ? o.length : 0,
        r & Tt) {
            var f = e
              , p = o;
            e = o = null
        }
        var s = l ? null : Mr(t)
          , h = [t, r, n, e, o, f, p, u, i, a];
        if (s && (V(h, s),
        r = h[1],
        a = h[9]),
        h[9] = null == a ? l ? 0 : t.length : Pr(a - c, 0) || 0,
        r == mt)
            var g = A(h[0], h[2]);
        else
            g = r != Et && r != (mt | Et) || h[4].length ? C.apply(_t, h) : k.apply(_t, h);
        var y = s ? Dr : Vr;
        return y(g, h)
    }
    function N(t) {
        for (var r = t.name, n = Nr[r], e = n ? n.length : 0; e--; ) {
            var o = n[e]
              , u = o.func;
            if (null == u || u == t)
                return o.name
        }
        return r
    }
    function L(t, r) {
        var n = null == t ? _t : t[r];
        return at(n) ? n : _t
    }
    function W(t) {
        return null != t && K(Kr(t))
    }
    function U(t, r) {
        return t = "number" == typeof t || Zt.test(t) ? +t : -1,
        r = null == r ? kr : r,
        t > -1 && t % 1 == 0 && r > t
    }
    function D(t, r, n) {
        if (!it(n))
            return !1;
        var e = typeof r;
        if ("number" == e ? W(n) && U(r, n.length) : "string" == e && r in n) {
            var o = n[r];
            return t === t ? t === o : o !== o
        }
        return !1
    }
    function M(t) {
        var r = N(t);
        if (!(r in c.prototype))
            return !1;
        var n = i[r];
        if (t === n)
            return !0;
        var e = Mr(n);
        return !!e && t === e[0]
    }
    function K(t) {
        return "number" == typeof t && t > -1 && t % 1 == 0 && kr >= t
    }
    function V(t, r) {
        var n = t[1]
          , e = r[1]
          , o = n | e
          , i = xt > o
          , a = e == xt && n == bt || e == xt && n == Ot && t[7].length <= r[8] || e == (xt | Ot) && n == bt;
        if (!i && !a)
            return t;
        e & mt && (t[2] = r[2],
        o |= n & mt ? 0 : dt);
        var l = r[3];
        if (l) {
            var c = t[3];
            t[3] = c ? O(c, l, r[4]) : y(l),
            t[4] = c ? u(t[3], Pt) : y(r[4])
        }
        return l = r[5],
        l && (c = t[5],
        t[5] = c ? S(c, l, r[6]) : y(l),
        t[6] = c ? u(t[5], Pt) : y(r[6])),
        l = r[7],
        l && (t[7] = y(l)),
        e & xt && (t[8] = null == t[8] ? r[8] : $r(t[8], r[8])),
        null == t[9] && (t[9] = r[9]),
        t[0] = r[0],
        t[1] = o,
        t
    }
    function q(t, r) {
        for (var n = t.length, e = $r(r.length, n), o = y(t); e--; ) {
            var u = r[e];
            t[e] = U(u, n) ? o[u] : _t
        }
        return t
    }
    function z(t) {
        for (var r = ft(t), n = r.length, e = n && t.length, o = !!e && K(e) && (en(t) || ot(t) || lt(t)), u = -1, i = []; ++u < n; ) {
            var a = r[u];
            (o && U(a, e) || dr.call(t, a)) && i.push(a)
        }
        return i
    }
    function B(t) {
        if (i.support.unindexedChars && lt(t)) {
            for (var r = -1, n = t.length, e = Object(t); ++r < n; )
                e[r] = t.charAt(r);
            return e
        }
        return it(t) ? t : Object(t)
    }
    function Y(t, r) {
        if ("function" != typeof r) {
            if ("function" != typeof t)
                throw new TypeError(Rt);
            var n = t;
            t = r,
            r = n
        }
        return t = Ar(t = +t) ? t : 0,
        function() {
            return --t < 1 ? r.apply(this, arguments) : _t
        }
    }
    function G(t, r, n) {
        return n && D(t, r, n) && (r = null),
        r = t && null == r ? t.length : Pr(+r || 0, 0),
        F(t, xt, null, null, null, null, r)
    }
    function H(t, r) {
        var n;
        if ("function" != typeof r) {
            if ("function" != typeof t)
                throw new TypeError(Rt);
            var e = t;
            t = r,
            r = e
        }
        return function() {
            return --t > 0 && (n = r.apply(this, arguments)),
            t > 1 || (r = null),
            n
        }
    }
    function J(t, r, n) {
        function e() {
            s && clearTimeout(s),
            l && clearTimeout(l),
            l = s = h = _t
        }
        function o() {
            var n = r - (qr() - f);
            if (0 >= n || n > r) {
                l && clearTimeout(l);
                var e = h;
                l = s = h = _t,
                e && (g = qr(),
                c = t.apply(p, a),
                s || l || (a = p = null))
            } else
                s = setTimeout(o, n)
        }
        function u() {
            s && clearTimeout(s),
            l = s = h = _t,
            (_ || y !== r) && (g = qr(),
            c = t.apply(p, a),
            s || l || (a = p = null))
        }
        function i() {
            if (a = arguments,
            f = qr(),
            p = this,
            h = _ && (s || !v),
            y === !1)
                var n = v && !s;
            else {
                l || v || (g = f);
                var e = y - (f - g)
                  , i = 0 >= e || e > y;
                i ? (l && (l = clearTimeout(l)),
                g = f,
                c = t.apply(p, a)) : l || (l = setTimeout(u, e))
            }
            return i && s ? s = clearTimeout(s) : s || r === y || (s = setTimeout(o, r)),
            n && (i = !0,
            c = t.apply(p, a)),
            !i || s || l || (a = p = null),
            c
        }
        var a, l, c, f, p, s, h, g = 0, y = !1, _ = !0;
        if ("function" != typeof t)
            throw new TypeError(Rt);
        if (r = 0 > r ? 0 : +r || 0,
        n === !0) {
            var v = !0;
            _ = !1
        } else
            it(n) && (v = n.leading,
            y = "maxWait"in n && Pr(+n.maxWait || 0, r),
            _ = "trailing"in n ? n.trailing : _);
        return i.cancel = e,
        i
    }
    function Q(t, r) {
        if ("function" != typeof t || r && "function" != typeof r)
            throw new TypeError(Rt);
        var n = function() {
            var e = arguments
              , o = r ? r.apply(this, e) : e[0]
              , u = n.cache;
            if (u.has(o))
                return u.get(o);
            var i = t.apply(this, e);
            return n.cache = u.set(o, i),
            i
        };
        return n.cache = new Q.Cache,
        n
    }
    function X(t) {
        if ("function" != typeof t)
            throw new TypeError(Rt);
        return function() {
            return !t.apply(this, arguments)
        }
    }
    function Z(t) {
        return H(2, t)
    }
    function tt(t, r) {
        if ("function" != typeof t)
            throw new TypeError(Rt);
        return r = Pr(r === _t ? t.length - 1 : +r || 0, 0),
        function() {
            for (var n = arguments, e = -1, o = Pr(n.length - r, 0), u = Array(o); ++e < o; )
                u[e] = n[r + e];
            switch (r) {
            case 0:
                return t.call(this, u);
            case 1:
                return t.call(this, n[0], u);
            case 2:
                return t.call(this, n[0], n[1], u)
            }
            var i = Array(r + 1);
            for (e = -1; ++e < r; )
                i[e] = n[e];
            return i[r] = u,
            t.apply(this, i)
        }
    }
    function rt(t) {
        if ("function" != typeof t)
            throw new TypeError(Rt);
        return function(r) {
            return t.apply(this, r)
        }
    }
    function nt(t, r, n) {
        var e = !0
          , o = !0;
        if ("function" != typeof t)
            throw new TypeError(Rt);
        return n === !1 ? e = !1 : it(n) && (e = "leading"in n ? !!n.leading : e,
        o = "trailing"in n ? !!n.trailing : o),
        er.leading = e,
        er.maxWait = +r,
        er.trailing = o,
        J(t, r, er)
    }
    function et(t, r) {
        return r = null == r ? gt : r,
        F(r, Et, null, [t], [])
    }
    function ot(t) {
        return o(t) && W(t) && br.call(t) == $t
    }
    function ut(t) {
        return o(t) && "string" == typeof t.message && br.call(t) == Ft
    }
    function it(t) {
        var r = typeof t;
        return !!t && ("object" == r || "function" == r)
    }
    function at(t) {
        return null == t ? !1 : br.call(t) == Nt ? jr.test(wr.call(t)) : o(t) && (gr(t) ? jr : Xt).test(t)
    }
    function lt(t) {
        return "string" == typeof t || o(t) && br.call(t) == Dt
    }
    function ct(t) {
        return E(t, ft(t))
    }
    function ft(t) {
        if (null == t)
            return [];
        it(t) || (t = Object(t));
        var r = t.length
          , n = i.support;
        r = r && K(r) && (en(t) || ot(t) || lt(t)) && r || 0;
        for (var e = t.constructor, o = -1, u = on(e) && e.prototype || vr, a = u === t, l = Array(r), c = r > 0, f = n.enumErrorProps && (t === _r || t instanceof Error), p = n.enumPrototypes && on(t); ++o < r; )
            l[o] = o + "";
        for (var s in t)
            p && "prototype" == s || f && ("message" == s || "name" == s) || c && U(s, r) || "constructor" == s && (a || !dr.call(t, s)) || l.push(s);
        if (n.nonEnumShadows && t !== vr) {
            var h = t === mr ? Dt : t === _r ? Ft : br.call(t)
              , g = Lr[h] || Lr[Wt];
            for (h == Wt && (u = vr),
            r = nr.length; r--; ) {
                s = nr[r];
                var y = g[s];
                a && y || (y ? !dr.call(t, s) : t[s] === u[s]) || l.push(s)
            }
        }
        return l
    }
    function pt(t) {
        return t = r(t),
        t && zt.test(t) ? t.replace(qt, n) : t
    }
    function st(t) {
        return t = r(t),
        t && Jt.test(t) ? t.replace(Ht, "\\$&") : t
    }
    function ht(t, n, o) {
        var u = i.templateSettings;
        o && D(t, n, o) && (n = o = null),
        t = r(t),
        n = m(w({}, o || n), u, v);
        var a, l, c = m(w({}, n.imports), u.imports, v), f = un(c), p = x(c, f), s = 0, h = n.interpolate || tr, g = "__p+='", y = RegExp((n.escape || tr).source + "|" + h.source + "|" + (h === Gt ? Qt : tr).source + "|" + (n.evaluate || tr).source + "|$", "g"), _ = "sourceURL"in n ? "//# sourceURL=" + n.sourceURL + "\n" : "";
        t.replace(y, function(r, n, o, u, i, c) {
            return o || (o = u),
            g += t.slice(s, c).replace(rr, e),
            n && (a = !0,
            g += "'+__e(" + n + ")+'"),
            i && (l = !0,
            g += "';" + i + ";\n__p+='"),
            o && (g += "'+((__t=(" + o + "))==null?'':__t)+'"),
            s = c + r.length,
            r
        }),
        g += "';";
        var d = n.variable;
        d || (g = "with(obj){" + g + "}"),
        g = (l ? g.replace(Mt, "") : g).replace(Kt, "$1").replace(Vt, "$1;"),
        g = "function(" + (d || "obj") + "){" + (d ? "" : "obj||(obj={});") + "var __t,__p=''" + (a ? ",__e=_.escape" : "") + (l ? ",__j=Array.prototype.join;function print(){__p+=__j.call(arguments,'')}" : ";") + g + "return __p}";
        var b = an(function() {
            return Function(f, _ + "return " + g).apply(_t, p)
        });
        if (b.source = g,
        ut(b))
            throw b;
        return b
    }
    function gt(t) {
        return t
    }
    function yt() {}
    var _t, vt = "3.9.3", mt = 1, wt = 2, dt = 4, bt = 8, jt = 16, Et = 32, Tt = 64, xt = 128, Ot = 256, St = 150, At = 16, Rt = "Expected a function", Pt = "__lodash_placeholder__", $t = "[object Arguments]", It = "[object Array]", Ct = "[object Boolean]", kt = "[object Date]", Ft = "[object Error]", Nt = "[object Function]", Lt = "[object Number]", Wt = "[object Object]", Ut = "[object RegExp]", Dt = "[object String]", Mt = /\b__p\+='';/g, Kt = /\b(__p\+=)''\+/g, Vt = /(__e\(.*?\)|\b__t\))\+'';/g, qt = /[&<>"'`]/g, zt = RegExp(qt.source), Bt = /<%-([\s\S]+?)%>/g, Yt = /<%([\s\S]+?)%>/g, Gt = /<%=([\s\S]+?)%>/g, Ht = /[.*+?^${}()|[\]\/\\]/g, Jt = RegExp(Ht.source), Qt = /\$\{([^\\}]*(?:\\.[^\\}]*)*)\}/g, Xt = /^\[object .+?Constructor\]$/, Zt = /^\d+$/, tr = /($^)/, rr = /['\n\r\u2028\u2029\\]/g, nr = ["constructor", "hasOwnProperty", "isPrototypeOf", "propertyIsEnumerable", "toLocaleString", "toString", "valueOf"], er = {
        leading: !1,
        maxWait: 0,
        trailing: !1
    }, or = {
        "&": "&amp;",
        "<": "&lt;",
        ">": "&gt;",
        '"': "&quot;",
        "'": "&#39;",
        "`": "&#96;"
    }, ur = {
        "function": !0,
        object: !0
    }, ir = {
        "\\": "\\",
        "'": "'",
        "\n": "n",
        "\r": "r",
        "\u2028": "u2028",
        "\u2029": "u2029"
    }, ar = ur[typeof exports] && exports && !exports.nodeType && exports, lr = ur[typeof module] && module && !module.nodeType && module, cr = ar && lr && "object" == typeof global && global && global.Object && global, fr = ur[typeof self] && self && self.Object && self, pr = ur[typeof window] && window && window.Object && window, sr = lr && lr.exports === ar && ar, hr = cr || pr !== (this && this.window) && pr || fr || this, gr = function() {
        try {
            Object({
                toString: 0
            } + "")
        } catch (t) {
            return function() {
                return !1
            }
        }
        return function(t) {
            return "function" != typeof t.toString && "string" == typeof (t + "")
        }
    }(), yr = Array.prototype, _r = Error.prototype, vr = Object.prototype, mr = String.prototype, wr = Function.prototype.toString, dr = vr.hasOwnProperty, br = vr.toString, jr = RegExp("^" + st(wr.call(dr)).replace(/hasOwnProperty|(function).*?(?=\\\()| for .+?(?=\\\])/g, "$1.*?") + "$"), Er = vr.propertyIsEnumerable, Tr = yr.splice, xr = L(hr, "Uint8Array"), Or = L(hr, "WeakMap"), Sr = L(Array, "isArray"), Ar = hr.isFinite, Rr = L(Object, "keys"), Pr = Math.max, $r = Math.min, Ir = L(Date, "now"), Cr = Number.POSITIVE_INFINITY, kr = 9007199254740991, Fr = Or && new Or, Nr = {}, Lr = {};
    Lr[It] = Lr[kt] = Lr[Lt] = {
        constructor: !0,
        toLocaleString: !0,
        toString: !0,
        valueOf: !0
    },
    Lr[Ct] = Lr[Dt] = {
        constructor: !0,
        toString: !0,
        valueOf: !0
    },
    Lr[Ft] = Lr[Nt] = Lr[Ut] = {
        constructor: !0,
        toString: !0
    },
    Lr[Wt] = {
        constructor: !0
    },
    _(nr, function(t) {
        for (var r in Lr)
            if (dr.call(Lr, r)) {
                var n = Lr[r];
                n[t] = dr.call(n, t)
            }
    });
    var Wr = i.support = {};
    !function(t) {
        var r = function() {
            this.x = t
        }
          , n = {
            0: t,
            length: t
        }
          , e = [];
        r.prototype = {
            valueOf: t,
            y: t
        };
        for (var o in new r)
            e.push(o);
        Wr.argsTag = br.call(arguments) == $t,
        Wr.enumErrorProps = Er.call(_r, "message") || Er.call(_r, "name"),
        Wr.enumPrototypes = Er.call(r, "prototype"),
        Wr.nonEnumShadows = !/valueOf/.test(e),
        Wr.spliceObjects = (Tr.call(n, 0, 1),
        !n[0]),
        Wr.unindexedChars = "x"[0] + Object("x")[0] != "xx"
    }(1, 0),
    i.templateSettings = {
        escape: Bt,
        evaluate: Yt,
        interpolate: Gt,
        variable: "",
        imports: {
            _: i
        }
    };
    var Ur = function() {
        function t() {}
        return function(r) {
            if (it(r)) {
                t.prototype = r;
                var n = new t;
                t.prototype = null
            }
            return n || {}
        }
    }()
      , Dr = Fr ? function(t, r) {
        return Fr.set(t, r),
        t
    }
    : gt
      , Mr = Fr ? function(t) {
        return Fr.get(t)
    }
    : yt
      , Kr = T("length")
      , Vr = function() {
        var t = 0
          , r = 0;
        return function(n, e) {
            var o = qr()
              , u = At - (o - r);
            if (r = o,
            u > 0) {
                if (++t >= St)
                    return n
            } else
                t = 0;
            return Dr(n, e)
        }
    }()
      , qr = Ir || function() {
        return (new Date).getTime()
    }
      , zr = tt(function(t, r, n) {
        var e = mt;
        if (n.length) {
            var o = u(n, zr.placeholder);
            e |= Et
        }
        return F(t, e, r, n, o)
    })
      , Br = tt(function(t, r) {
        r = r.length ? j(r) : ct(t);
        for (var n = -1, e = r.length; ++n < e; ) {
            var o = r[n];
            t[o] = F(t[o], mt, t)
        }
        return t
    })
      , Yr = tt(function(t, r, n) {
        var e = mt | wt;
        if (n.length) {
            var o = u(n, Yr.placeholder);
            e |= Et
        }
        return F(r, e, t, n, o)
    })
      , Gr = P(bt)
      , Hr = P(jt)
      , Jr = tt(function(t, r) {
        return b(t, 1, r)
    })
      , Qr = tt(function(t, r, n) {
        return b(t, r, n)
    })
      , Xr = $()
      , Zr = $(!0)
      , tn = I(Et)
      , rn = I(Tt)
      , nn = tt(function(t, r) {
        return F(t, Ot, null, null, null, j(r))
    });
    Wr.argsTag || (ot = function(t) {
        return o(t) && W(t) && dr.call(t, "callee") && !Er.call(t, "callee")
    }
    );
    var en = Sr || function(t) {
        return o(t) && K(t.length) && br.call(t) == It
    }
      , on = t(/x/) || xr && !t(xr) ? function(t) {
        return br.call(t) == Nt
    }
    : t
      , un = Rr ? function(t) {
        var r = null == t ? null : t.constructor;
        return "function" == typeof r && r.prototype === t || ("function" == typeof t ? i.support.enumPrototypes : W(t)) ? z(t) : it(t) ? Rr(t) : []
    }
    : z
      , an = tt(function(t, r) {
        try {
            return t.apply(_t, r)
        } catch (n) {
            return ut(n) ? n : Error(n)
        }
    });
    l.prototype = Ur(a.prototype),
    l.prototype.constructor = l,
    c.prototype = Ur(a.prototype),
    c.prototype.constructor = c,
    f.prototype["delete"] = p,
    f.prototype.get = s,
    f.prototype.has = h,
    f.prototype.set = g,
    Q.Cache = f,
    i.after = Y,
    i.ary = G,
    i.before = H,
    i.bind = zr,
    i.bindAll = Br,
    i.bindKey = Yr,
    i.curry = Gr,
    i.curryRight = Hr,
    i.debounce = J,
    i.defer = Jr,
    i.delay = Qr,
    i.flow = Xr,
    i.flowRight = Zr,
    i.functions = ct,
    i.keys = un,
    i.keysIn = ft,
    i.memoize = Q,
    i.negate = X,
    i.once = Z,
    i.partial = tn,
    i.partialRight = rn,
    i.rearg = nn,
    i.restParam = tt,
    i.spread = rt,
    i.throttle = nt,
    i.wrap = et,
    i.backflow = Zr,
    i.compose = Zr,
    i.methods = ct,
    i.attempt = an,
    i.escape = pt,
    i.escapeRegExp = st,
    i.identity = gt,
    i.isArguments = ot,
    i.isArray = en,
    i.isError = ut,
    i.isFunction = on,
    i.isNative = at,
    i.isObject = it,
    i.isString = lt,
    i.noop = yt,
    i.now = qr,
    i.template = ht,
    i.VERSION = vt,
    _(["bind", "bindKey", "curry", "curryRight", "partial", "partialRight"], function(t) {
        i[t].placeholder = i
    }),
    "function" == typeof define && "object" == typeof define.amd && define.amd ? (hr._ = i,
    define(function() {
        return i
    })) : ar && lr ? sr ? (lr.exports = i)._ = i : ar._ = i : hr._ = i
}
).call(this);
var Zepto = function() {
    function t(t) {
        return null == t ? String(t) : X[Y.call(t)] || "object"
    }
    function e(e) {
        return "function" == t(e)
    }
    function n(t) {
        return null != t && t == t.window
    }
    function i(t) {
        return null != t && t.nodeType == t.DOCUMENT_NODE
    }
    function r(e) {
        return "object" == t(e)
    }
    function o(t) {
        return r(t) && !n(t) && Object.getPrototypeOf(t) == Object.prototype
    }
    function a(t) {
        return "number" == typeof t.length
    }
    function s(t) {
        return M.call(t, function(t) {
            return null != t
        })
    }
    function u(t) {
        return t.length > 0 ? S.fn.concat.apply([], t) : t
    }
    function c(t) {
        return t.replace(/::/g, "/").replace(/([A-Z]+)([A-Z][a-z])/g, "$1_$2").replace(/([a-z\d])([A-Z])/g, "$1_$2").replace(/_/g, "-").toLowerCase()
    }
    function l(t) {
        return t in L ? L[t] : L[t] = new RegExp("(^|\\s)" + t + "(\\s|$)")
    }
    function f(t, e) {
        return "number" != typeof e || Z[c(t)] ? e : e + "px"
    }
    function h(t) {
        var e, n;
        return D[t] || (e = k.createElement(t),
        k.body.appendChild(e),
        n = getComputedStyle(e, "").getPropertyValue("display"),
        e.parentNode.removeChild(e),
        "none" == n && (n = "block"),
        D[t] = n),
        D[t]
    }
    function p(t) {
        return "children"in t ? A.call(t.children) : S.map(t.childNodes, function(t) {
            return 1 == t.nodeType ? t : void 0
        })
    }
    function d(t, e) {
        var n, i = t ? t.length : 0;
        for (n = 0; i > n; n++)
            this[n] = t[n];
        this.length = i,
        this.selector = e || ""
    }
    function m(t, e, n) {
        for (T in e)
            n && (o(e[T]) || Q(e[T])) ? (o(e[T]) && !o(t[T]) && (t[T] = {}),
            Q(e[T]) && !Q(t[T]) && (t[T] = []),
            m(t[T], e[T], n)) : e[T] !== E && (t[T] = e[T])
    }
    function g(t, e) {
        return null == e ? S(t) : S(t).filter(e)
    }
    function v(t, n, i, r) {
        return e(n) ? n.call(t, i, r) : n
    }
    function y(t, e, n) {
        null == n ? t.removeAttribute(e) : t.setAttribute(e, n)
    }
    function b(t, e) {
        var n = t.className || ""
          , i = n && n.baseVal !== E;
        return e === E ? i ? n.baseVal : n : void (i ? n.baseVal = e : t.className = e)
    }
    function w(t) {
        try {
            return t ? "true" == t || ("false" == t ? !1 : "null" == t ? null : +t + "" == t ? +t : /^[\[\{]/.test(t) ? S.parseJSON(t) : t) : t
        } catch (e) {
            return t
        }
    }
    function x(t, e) {
        e(t);
        for (var n = 0, i = t.childNodes.length; i > n; n++)
            x(t.childNodes[n], e)
    }
    var E, T, S, j, C, P, O = [], N = O.concat, M = O.filter, A = O.slice, k = window.document, D = {}, L = {}, Z = {
        "column-count": 1,
        columns: 1,
        "font-weight": 1,
        "line-height": 1,
        opacity: 1,
        "z-index": 1,
        zoom: 1
    }, F = /^\s*<(\w+|!)[^>]*>/, $ = /^<(\w+)\s*\/?>(?:<\/\1>|)$/, _ = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:]+)[^>]*)\/>/gi, R = /^(?:body|html)$/i, z = /([A-Z])/g, W = ["val", "css", "html", "text", "data", "width", "height", "offset"], I = ["after", "prepend", "before", "append"], q = k.createElement("table"), B = k.createElement("tr"), V = {
        tr: k.createElement("tbody"),
        tbody: q,
        thead: q,
        tfoot: q,
        td: B,
        th: B,
        "*": k.createElement("div")
    }, H = /complete|loaded|interactive/, U = /^[\w-]*$/, X = {}, Y = X.toString, J = {}, G = k.createElement("div"), K = {
        tabindex: "tabIndex",
        readonly: "readOnly",
        "for": "htmlFor",
        "class": "className",
        maxlength: "maxLength",
        cellspacing: "cellSpacing",
        cellpadding: "cellPadding",
        rowspan: "rowSpan",
        colspan: "colSpan",
        usemap: "useMap",
        frameborder: "frameBorder",
        contenteditable: "contentEditable"
    }, Q = Array.isArray || function(t) {
        return t instanceof Array
    }
    ;
    return J.matches = function(t, e) {
        if (!e || !t || 1 !== t.nodeType)
            return !1;
        var n = t.webkitMatchesSelector || t.mozMatchesSelector || t.oMatchesSelector || t.matchesSelector;
        if (n)
            return n.call(t, e);
        var i, r = t.parentNode, o = !r;
        return o && (r = G).appendChild(t),
        i = ~J.qsa(r, e).indexOf(t),
        o && G.removeChild(t),
        i
    }
    ,
    C = function(t) {
        return t.replace(/-+(.)?/g, function(t, e) {
            return e ? e.toUpperCase() : ""
        })
    }
    ,
    P = function(t) {
        return M.call(t, function(e, n) {
            return t.indexOf(e) == n
        })
    }
    ,
    J.fragment = function(t, e, n) {
        var i, r, a;
        return $.test(t) && (i = S(k.createElement(RegExp.$1))),
        i || (t.replace && (t = t.replace(_, "<$1></$2>")),
        e === E && (e = F.test(t) && RegExp.$1),
        e in V || (e = "*"),
        a = V[e],
        a.innerHTML = "" + t,
        i = S.each(A.call(a.childNodes), function() {
            a.removeChild(this)
        })),
        o(n) && (r = S(i),
        S.each(n, function(t, e) {
            W.indexOf(t) > -1 ? r[t](e) : r.attr(t, e)
        })),
        i
    }
    ,
    J.Z = function(t, e) {
        return new d(t,e)
    }
    ,
    J.isZ = function(t) {
        return t instanceof J.Z
    }
    ,
    J.init = function(t, n) {
        var i;
        if (!t)
            return J.Z();
        if ("string" == typeof t)
            if (t = t.trim(),
            "<" == t[0] && F.test(t))
                i = J.fragment(t, RegExp.$1, n),
                t = null;
            else {
                if (n !== E)
                    return S(n).find(t);
                i = J.qsa(k, t)
            }
        else {
            if (e(t))
                return S(k).ready(t);
            if (J.isZ(t))
                return t;
            if (Q(t))
                i = s(t);
            else if (r(t))
                i = [t],
                t = null;
            else if (F.test(t))
                i = J.fragment(t.trim(), RegExp.$1, n),
                t = null;
            else {
                if (n !== E)
                    return S(n).find(t);
                i = J.qsa(k, t)
            }
        }
        return J.Z(i, t)
    }
    ,
    S = function(t, e) {
        return J.init(t, e)
    }
    ,
    S.extend = function(t) {
        var e, n = A.call(arguments, 1);
        return "boolean" == typeof t && (e = t,
        t = n.shift()),
        n.forEach(function(n) {
            m(t, n, e)
        }),
        t
    }
    ,
    J.qsa = function(t, e) {
        var n, i = "#" == e[0], r = !i && "." == e[0], o = i || r ? e.slice(1) : e, a = U.test(o);
        return t.getElementById && a && i ? (n = t.getElementById(o)) ? [n] : [] : 1 !== t.nodeType && 9 !== t.nodeType && 11 !== t.nodeType ? [] : A.call(a && !i && t.getElementsByClassName ? r ? t.getElementsByClassName(o) : t.getElementsByTagName(e) : t.querySelectorAll(e))
    }
    ,
    S.contains = k.documentElement.contains ? function(t, e) {
        return t !== e && t.contains(e)
    }
    : function(t, e) {
        for (; e && (e = e.parentNode); )
            if (e === t)
                return !0;
        return !1
    }
    ,
    S.type = t,
    S.isFunction = e,
    S.isWindow = n,
    S.isArray = Q,
    S.isPlainObject = o,
    S.isEmptyObject = function(t) {
        var e;
        for (e in t)
            return !1;
        return !0
    }
    ,
    S.inArray = function(t, e, n) {
        return O.indexOf.call(e, t, n)
    }
    ,
    S.camelCase = C,
    S.trim = function(t) {
        return null == t ? "" : String.prototype.trim.call(t)
    }
    ,
    S.uuid = 0,
    S.support = {},
    S.expr = {},
    S.noop = function() {}
    ,
    S.map = function(t, e) {
        var n, i, r, o = [];
        if (a(t))
            for (i = 0; i < t.length; i++)
                n = e(t[i], i),
                null != n && o.push(n);
        else
            for (r in t)
                n = e(t[r], r),
                null != n && o.push(n);
        return u(o)
    }
    ,
    S.each = function(t, e) {
        var n, i;
        if (a(t)) {
            for (n = 0; n < t.length; n++)
                if (e.call(t[n], n, t[n]) === !1)
                    return t
        } else
            for (i in t)
                if (e.call(t[i], i, t[i]) === !1)
                    return t;
        return t
    }
    ,
    S.grep = function(t, e) {
        return M.call(t, e)
    }
    ,
    window.JSON && (S.parseJSON = JSON.parse),
    S.each("Boolean Number String Function Array Date RegExp Object Error".split(" "), function(t, e) {
        X["[object " + e + "]"] = e.toLowerCase()
    }),
    S.fn = {
        constructor: J.Z,
        length: 0,
        forEach: O.forEach,
        reduce: O.reduce,
        push: O.push,
        sort: O.sort,
        splice: O.splice,
        indexOf: O.indexOf,
        concat: function() {
            var t, e, n = [];
            for (t = 0; t < arguments.length; t++)
                e = arguments[t],
                n[t] = J.isZ(e) ? e.toArray() : e;
            return N.apply(J.isZ(this) ? this.toArray() : this, n)
        },
        map: function(t) {
            return S(S.map(this, function(e, n) {
                return t.call(e, n, e)
            }))
        },
        slice: function() {
            return S(A.apply(this, arguments))
        },
        ready: function(t) {
            return H.test(k.readyState) && k.body ? t(S) : k.addEventListener("DOMContentLoaded", function() {
                t(S)
            }, !1),
            this
        },
        get: function(t) {
            return t === E ? A.call(this) : this[t >= 0 ? t : t + this.length]
        },
        toArray: function() {
            return this.get()
        },
        size: function() {
            return this.length
        },
        remove: function() {
            return this.each(function() {
                null != this.parentNode && this.parentNode.removeChild(this)
            })
        },
        each: function(t) {
            return O.every.call(this, function(e, n) {
                return t.call(e, n, e) !== !1
            }),
            this
        },
        filter: function(t) {
            return e(t) ? this.not(this.not(t)) : S(M.call(this, function(e) {
                return J.matches(e, t)
            }))
        },
        add: function(t, e) {
            return S(P(this.concat(S(t, e))))
        },
        is: function(t) {
            return this.length > 0 && J.matches(this[0], t)
        },
        not: function(t) {
            var n = [];
            if (e(t) && t.call !== E)
                this.each(function(e) {
                    t.call(this, e) || n.push(this)
                });
            else {
                var i = "string" == typeof t ? this.filter(t) : a(t) && e(t.item) ? A.call(t) : S(t);
                this.forEach(function(t) {
                    i.indexOf(t) < 0 && n.push(t)
                })
            }
            return S(n)
        },
        has: function(t) {
            return this.filter(function() {
                return r(t) ? S.contains(this, t) : S(this).find(t).size()
            })
        },
        eq: function(t) {
            return -1 === t ? this.slice(t) : this.slice(t, +t + 1)
        },
        first: function() {
            var t = this[0];
            return t && !r(t) ? t : S(t)
        },
        last: function() {
            var t = this[this.length - 1];
            return t && !r(t) ? t : S(t)
        },
        find: function(t) {
            var e, n = this;
            return e = t ? "object" == typeof t ? S(t).filter(function() {
                var t = this;
                return O.some.call(n, function(e) {
                    return S.contains(e, t)
                })
            }) : 1 == this.length ? S(J.qsa(this[0], t)) : this.map(function() {
                return J.qsa(this, t)
            }) : S()
        },
        closest: function(t, e) {
            var n = this[0]
              , r = !1;
            for ("object" == typeof t && (r = S(t)); n && !(r ? r.indexOf(n) >= 0 : J.matches(n, t)); )
                n = n !== e && !i(n) && n.parentNode;
            return S(n)
        },
        parents: function(t) {
            for (var e = [], n = this; n.length > 0; )
                n = S.map(n, function(t) {
                    return (t = t.parentNode) && !i(t) && e.indexOf(t) < 0 ? (e.push(t),
                    t) : void 0
                });
            return g(e, t)
        },
        parent: function(t) {
            return g(P(this.pluck("parentNode")), t)
        },
        children: function(t) {
            return g(this.map(function() {
                return p(this)
            }), t)
        },
        contents: function() {
            return this.map(function() {
                return this.contentDocument || A.call(this.childNodes)
            })
        },
        siblings: function(t) {
            return g(this.map(function(t, e) {
                return M.call(p(e.parentNode), function(t) {
                    return t !== e
                })
            }), t)
        },
        empty: function() {
            return this.each(function() {
                this.innerHTML = ""
            })
        },
        pluck: function(t) {
            return S.map(this, function(e) {
                return e[t]
            })
        },
        show: function() {
            return this.each(function() {
                "none" == this.style.display && (this.style.display = ""),
                "none" == getComputedStyle(this, "").getPropertyValue("display") && (this.style.display = h(this.nodeName))
            })
        },
        replaceWith: function(t) {
            return this.before(t).remove()
        },
        wrap: function(t) {
            var n = e(t);
            if (this[0] && !n)
                var i = S(t).get(0)
                  , r = i.parentNode || this.length > 1;
            return this.each(function(e) {
                S(this).wrapAll(n ? t.call(this, e) : r ? i.cloneNode(!0) : i)
            })
        },
        wrapAll: function(t) {
            if (this[0]) {
                S(this[0]).before(t = S(t));
                for (var e; (e = t.children()).length; )
                    t = e.first();
                S(t).append(this)
            }
            return this
        },
        wrapInner: function(t) {
            var n = e(t);
            return this.each(function(e) {
                var i = S(this)
                  , r = i.contents()
                  , o = n ? t.call(this, e) : t;
                r.length ? r.wrapAll(o) : i.append(o)
            })
        },
        unwrap: function() {
            return this.parent().each(function() {
                S(this).replaceWith(S(this).children())
            }),
            this
        },
        clone: function() {
            return this.map(function() {
                return this.cloneNode(!0)
            })
        },
        hide: function() {
            return this.css("display", "none")
        },
        toggle: function(t) {
            return this.each(function() {
                var e = S(this);
                (t === E ? "none" == e.css("display") : t) ? e.show() : e.hide()
            })
        },
        prev: function(t) {
            return S(this.pluck("previousElementSibling")).filter(t || "*")
        },
        next: function(t) {
            return S(this.pluck("nextElementSibling")).filter(t || "*")
        },
        html: function(t) {
            return 0 in arguments ? this.each(function(e) {
                var n = this.innerHTML;
                S(this).empty().append(v(this, t, e, n))
            }) : 0 in this ? this[0].innerHTML : null
        },
        text: function(t) {
            return 0 in arguments ? this.each(function(e) {
                var n = v(this, t, e, this.textContent);
                this.textContent = null == n ? "" : "" + n
            }) : 0 in this ? this[0].textContent : null
        },
        attr: function(t, e) {
            var n;
            return "string" != typeof t || 1 in arguments ? this.each(function(n) {
                if (1 === this.nodeType)
                    if (r(t))
                        for (T in t)
                            y(this, T, t[T]);
                    else
                        y(this, t, v(this, e, n, this.getAttribute(t)))
            }) : this.length && 1 === this[0].nodeType ? !(n = this[0].getAttribute(t)) && t in this[0] ? this[0][t] : n : E
        },
        removeAttr: function(t) {
            return this.each(function() {
                1 === this.nodeType && t.split(" ").forEach(function(t) {
                    y(this, t)
                }, this)
            })
        },
        prop: function(t, e) {
            return t = K[t] || t,
            1 in arguments ? this.each(function(n) {
                this[t] = v(this, e, n, this[t])
            }) : this[0] && this[0][t]
        },
        data: function(t, e) {
            var n = "data-" + t.replace(z, "-$1").toLowerCase()
              , i = 1 in arguments ? this.attr(n, e) : this.attr(n);
            return null !== i ? w(i) : E
        },
        val: function(t) {
            return 0 in arguments ? this.each(function(e) {
                this.value = v(this, t, e, this.value)
            }) : this[0] && (this[0].multiple ? S(this[0]).find("option").filter(function() {
                return this.selected
            }).pluck("value") : this[0].value)
        },
        offset: function(t) {
            if (t)
                return this.each(function(e) {
                    var n = S(this)
                      , i = v(this, t, e, n.offset())
                      , r = n.offsetParent().offset()
                      , o = {
                        top: i.top - r.top,
                        left: i.left - r.left
                    };
                    "static" == n.css("position") && (o.position = "relative"),
                    n.css(o)
                });
            if (!this.length)
                return null;
            if (!S.contains(k.documentElement, this[0]))
                return {
                    top: 0,
                    left: 0
                };
            var e = this[0].getBoundingClientRect();
            return {
                left: e.left + window.pageXOffset,
                top: e.top + window.pageYOffset,
                width: Math.round(e.width),
                height: Math.round(e.height)
            }
        },
        css: function(e, n) {
            if (arguments.length < 2) {
                var i, r = this[0];
                if (!r)
                    return;
                if (i = getComputedStyle(r, ""),
                "string" == typeof e)
                    return r.style[C(e)] || i.getPropertyValue(e);
                if (Q(e)) {
                    var o = {};
                    return S.each(e, function(t, e) {
                        o[e] = r.style[C(e)] || i.getPropertyValue(e)
                    }),
                    o
                }
            }
            var a = "";
            if ("string" == t(e))
                n || 0 === n ? a = c(e) + ":" + f(e, n) : this.each(function() {
                    this.style.removeProperty(c(e))
                });
            else
                for (T in e)
                    e[T] || 0 === e[T] ? a += c(T) + ":" + f(T, e[T]) + ";" : this.each(function() {
                        this.style.removeProperty(c(T))
                    });
            return this.each(function() {
                this.style.cssText += ";" + a
            })
        },
        index: function(t) {
            return t ? this.indexOf(S(t)[0]) : this.parent().children().indexOf(this[0])
        },
        hasClass: function(t) {
            return t ? O.some.call(this, function(t) {
                return this.test(b(t))
            }, l(t)) : !1
        },
        addClass: function(t) {
            return t ? this.each(function(e) {
                if ("className"in this) {
                    j = [];
                    var n = b(this)
                      , i = v(this, t, e, n);
                    i.split(/\s+/g).forEach(function(t) {
                        S(this).hasClass(t) || j.push(t)
                    }, this),
                    j.length && b(this, n + (n ? " " : "") + j.join(" "))
                }
            }) : this
        },
        removeClass: function(t) {
            return this.each(function(e) {
                if ("className"in this) {
                    if (t === E)
                        return b(this, "");
                    j = b(this),
                    v(this, t, e, j).split(/\s+/g).forEach(function(t) {
                        j = j.replace(l(t), " ")
                    }),
                    b(this, j.trim())
                }
            })
        },
        toggleClass: function(t, e) {
            return t ? this.each(function(n) {
                var i = S(this)
                  , r = v(this, t, n, b(this));
                r.split(/\s+/g).forEach(function(t) {
                    (e === E ? !i.hasClass(t) : e) ? i.addClass(t) : i.removeClass(t)
                })
            }) : this
        },
        scrollTop: function(t) {
            if (this.length) {
                var e = "scrollTop"in this[0];
                return t === E ? e ? this[0].scrollTop : this[0].pageYOffset : this.each(e ? function() {
                    this.scrollTop = t
                }
                : function() {
                    this.scrollTo(this.scrollX, t)
                }
                )
            }
        },
        scrollLeft: function(t) {
            if (this.length) {
                var e = "scrollLeft"in this[0];
                return t === E ? e ? this[0].scrollLeft : this[0].pageXOffset : this.each(e ? function() {
                    this.scrollLeft = t
                }
                : function() {
                    this.scrollTo(t, this.scrollY)
                }
                )
            }
        },
        position: function() {
            if (this.length) {
                var t = this[0]
                  , e = this.offsetParent()
                  , n = this.offset()
                  , i = R.test(e[0].nodeName) ? {
                    top: 0,
                    left: 0
                } : e.offset();
                return n.top -= parseFloat(S(t).css("margin-top")) || 0,
                n.left -= parseFloat(S(t).css("margin-left")) || 0,
                i.top += parseFloat(S(e[0]).css("border-top-width")) || 0,
                i.left += parseFloat(S(e[0]).css("border-left-width")) || 0,
                {
                    top: n.top - i.top,
                    left: n.left - i.left
                }
            }
        },
        offsetParent: function() {
            return this.map(function() {
                for (var t = this.offsetParent || k.body; t && !R.test(t.nodeName) && "static" == S(t).css("position"); )
                    t = t.offsetParent;
                return t
            })
        }
    },
    S.fn.detach = S.fn.remove,
    ["width", "height"].forEach(function(t) {
        var e = t.replace(/./, function(t) {
            return t[0].toUpperCase()
        });
        S.fn[t] = function(r) {
            var o, a = this[0];
            return r === E ? n(a) ? a["inner" + e] : i(a) ? a.documentElement["scroll" + e] : (o = this.offset()) && o[t] : this.each(function(e) {
                a = S(this),
                a.css(t, v(this, r, e, a[t]()))
            })
        }
    }),
    I.forEach(function(e, n) {
        var i = n % 2;
        S.fn[e] = function() {
            var e, r, o = S.map(arguments, function(n) {
                return e = t(n),
                "object" == e || "array" == e || null == n ? n : J.fragment(n)
            }), a = this.length > 1;
            return o.length < 1 ? this : this.each(function(t, e) {
                r = i ? e : e.parentNode,
                e = 0 == n ? e.nextSibling : 1 == n ? e.firstChild : 2 == n ? e : null;
                var s = S.contains(k.documentElement, r);
                o.forEach(function(t) {
                    if (a)
                        t = t.cloneNode(!0);
                    else if (!r)
                        return S(t).remove();
                    r.insertBefore(t, e),
                    s && x(t, function(t) {
                        null == t.nodeName || "SCRIPT" !== t.nodeName.toUpperCase() || t.type && "text/javascript" !== t.type || t.src || window.eval.call(window, t.innerHTML)
                    })
                })
            })
        }
        ,
        S.fn[i ? e + "To" : "insert" + (n ? "Before" : "After")] = function(t) {
            return S(t)[e](this),
            this
        }
    }),
    J.Z.prototype = d.prototype = S.fn,
    J.uniq = P,
    J.deserializeValue = w,
    S.zepto = J,
    S
}();
window.Zepto = Zepto,
void 0 === window.$ && (window.$ = Zepto),
function(t) {
    function e(t) {
        return t._zid || (t._zid = h++)
    }
    function n(t, n, o, a) {
        if (n = i(n),
        n.ns)
            var s = r(n.ns);
        return (g[e(t)] || []).filter(function(t) {
            return !(!t || n.e && t.e != n.e || n.ns && !s.test(t.ns) || o && e(t.fn) !== e(o) || a && t.sel != a)
        })
    }
    function i(t) {
        var e = ("" + t).split(".");
        return {
            e: e[0],
            ns: e.slice(1).sort().join(" ")
        }
    }
    function r(t) {
        return new RegExp("(?:^| )" + t.replace(" ", " .* ?") + "(?: |$)")
    }
    function o(t, e) {
        return t.del && !y && t.e in b || !!e
    }
    function a(t) {
        return w[t] || y && b[t] || t
    }
    function s(n, r, s, u, l, h, p) {
        var d = e(n)
          , m = g[d] || (g[d] = []);
        r.split(/\s/).forEach(function(e) {
            if ("ready" == e)
                return t(document).ready(s);
            var r = i(e);
            r.fn = s,
            r.sel = l,
            r.e in w && (s = function(e) {
                var n = e.relatedTarget;
                return !n || n !== this && !t.contains(this, n) ? r.fn.apply(this, arguments) : void 0
            }
            ),
            r.del = h;
            var d = h || s;
            r.proxy = function(t) {
                if (t = c(t),
                !t.isImmediatePropagationStopped()) {
                    t.data = u;
                    var e = d.apply(n, t._args == f ? [t] : [t].concat(t._args));
                    return e === !1 && (t.preventDefault(),
                    t.stopPropagation()),
                    e
                }
            }
            ,
            r.i = m.length,
            m.push(r),
            "addEventListener"in n && n.addEventListener(a(r.e), r.proxy, o(r, p))
        })
    }
    function u(t, i, r, s, u) {
        var c = e(t);
        (i || "").split(/\s/).forEach(function(e) {
            n(t, e, r, s).forEach(function(e) {
                delete g[c][e.i],
                "removeEventListener"in t && t.removeEventListener(a(e.e), e.proxy, o(e, u))
            })
        })
    }
    function c(e, n) {
        return (n || !e.isDefaultPrevented) && (n || (n = e),
        t.each(S, function(t, i) {
            var r = n[t];
            e[t] = function() {
                return this[i] = x,
                r && r.apply(n, arguments)
            }
            ,
            e[i] = E
        }),
        (n.defaultPrevented !== f ? n.defaultPrevented : "returnValue"in n ? n.returnValue === !1 : n.getPreventDefault && n.getPreventDefault()) && (e.isDefaultPrevented = x)),
        e
    }
    function l(t) {
        var e, n = {
            originalEvent: t
        };
        for (e in t)
            T.test(e) || t[e] === f || (n[e] = t[e]);
        return c(n, t)
    }
    var f, h = 1, p = Array.prototype.slice, d = t.isFunction, m = function(t) {
        return "string" == typeof t
    }, g = {}, v = {}, y = "onfocusin"in window, b = {
        focus: "focusin",
        blur: "focusout"
    }, w = {
        mouseenter: "mouseover",
        mouseleave: "mouseout"
    };
    v.click = v.mousedown = v.mouseup = v.mousemove = "MouseEvents",
    t.event = {
        add: s,
        remove: u
    },
    t.proxy = function(n, i) {
        var r = 2 in arguments && p.call(arguments, 2);
        if (d(n)) {
            var o = function() {
                return n.apply(i, r ? r.concat(p.call(arguments)) : arguments)
            };
            return o._zid = e(n),
            o
        }
        if (m(i))
            return r ? (r.unshift(n[i], n),
            t.proxy.apply(null, r)) : t.proxy(n[i], n);
        throw new TypeError("expected function")
    }
    ,
    t.fn.bind = function(t, e, n) {
        return this.on(t, e, n)
    }
    ,
    t.fn.unbind = function(t, e) {
        return this.off(t, e)
    }
    ,
    t.fn.one = function(t, e, n, i) {
        return this.on(t, e, n, i, 1)
    }
    ;
    var x = function() {
        return !0
    }
      , E = function() {
        return !1
    }
      , T = /^([A-Z]|returnValue$|layer[XY]$)/
      , S = {
        preventDefault: "isDefaultPrevented",
        stopImmediatePropagation: "isImmediatePropagationStopped",
        stopPropagation: "isPropagationStopped"
    };
    t.fn.delegate = function(t, e, n) {
        return this.on(e, t, n)
    }
    ,
    t.fn.undelegate = function(t, e, n) {
        return this.off(e, t, n)
    }
    ,
    t.fn.live = function(e, n) {
        return t(document.body).delegate(this.selector, e, n),
        this
    }
    ,
    t.fn.die = function(e, n) {
        return t(document.body).undelegate(this.selector, e, n),
        this
    }
    ,
    t.fn.on = function(e, n, i, r, o) {
        var a, c, h = this;
        return e && !m(e) ? (t.each(e, function(t, e) {
            h.on(t, n, i, e, o)
        }),
        h) : (m(n) || d(r) || r === !1 || (r = i,
        i = n,
        n = f),
        (r === f || i === !1) && (r = i,
        i = f),
        r === !1 && (r = E),
        h.each(function(f, h) {
            o && (a = function(t) {
                return u(h, t.type, r),
                r.apply(this, arguments)
            }
            ),
            n && (c = function(e) {
                var i, o = t(e.target).closest(n, h).get(0);
                return o && o !== h ? (i = t.extend(l(e), {
                    currentTarget: o,
                    liveFired: h
                }),
                (a || r).apply(o, [i].concat(p.call(arguments, 1)))) : void 0
            }
            ),
            s(h, e, r, i, n, c || a)
        }))
    }
    ,
    t.fn.off = function(e, n, i) {
        var r = this;
        return e && !m(e) ? (t.each(e, function(t, e) {
            r.off(t, n, e)
        }),
        r) : (m(n) || d(i) || i === !1 || (i = n,
        n = f),
        i === !1 && (i = E),
        r.each(function() {
            u(this, e, i, n)
        }))
    }
    ,
    t.fn.trigger = function(e, n) {
        return e = m(e) || t.isPlainObject(e) ? t.Event(e) : c(e),
        e._args = n,
        this.each(function() {
            e.type in b && "function" == typeof this[e.type] ? this[e.type]() : "dispatchEvent"in this ? this.dispatchEvent(e) : t(this).triggerHandler(e, n)
        })
    }
    ,
    t.fn.triggerHandler = function(e, i) {
        var r, o;
        return this.each(function(a, s) {
            r = l(m(e) ? t.Event(e) : e),
            r._args = i,
            r.target = s,
            t.each(n(s, e.type || e), function(t, e) {
                return o = e.proxy(r),
                r.isImmediatePropagationStopped() ? !1 : void 0
            })
        }),
        o
    }
    ,
    "focusin focusout focus blur load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select keydown keypress keyup error".split(" ").forEach(function(e) {
        t.fn[e] = function(t) {
            return 0 in arguments ? this.bind(e, t) : this.trigger(e)
        }
    }),
    t.Event = function(t, e) {
        m(t) || (e = t,
        t = e.type);
        var n = document.createEvent(v[t] || "Events")
          , i = !0;
        if (e)
            for (var r in e)
                "bubbles" == r ? i = !!e[r] : n[r] = e[r];
        return n.initEvent(t, i, !0),
        c(n)
    }
}(Zepto),
function(t) {
    function e(e, n, i) {
        var r = t.Event(n);
        return t(e).trigger(r, i),
        !r.isDefaultPrevented()
    }
    function n(t, n, i, r) {
        return t.global ? e(n || y, i, r) : void 0
    }
    function i(e) {
        e.global && 0 === t.active++ && n(e, null, "ajaxStart")
    }
    function r(e) {
        e.global && !--t.active && n(e, null, "ajaxStop")
    }
    function o(t, e) {
        var i = e.context;
        return e.beforeSend.call(i, t, e) === !1 || n(e, i, "ajaxBeforeSend", [t, e]) === !1 ? !1 : void n(e, i, "ajaxSend", [t, e])
    }
    function a(t, e, i, r) {
        var o = i.context
          , a = "success";
        i.success.call(o, t, a, e),
        r && r.resolveWith(o, [t, a, e]),
        n(i, o, "ajaxSuccess", [e, i, t]),
        u(a, e, i)
    }
    function s(t, e, i, r, o) {
        var a = r.context;
        r.error.call(a, i, e, t),
        o && o.rejectWith(a, [i, e, t]),
        n(r, a, "ajaxError", [i, r, t || e]),
        u(e, i, r)
    }
    function u(t, e, i) {
        var o = i.context;
        i.complete.call(o, e, t),
        n(i, o, "ajaxComplete", [e, i]),
        r(i)
    }
    function c() {}
    function l(t) {
        return t && (t = t.split(";", 2)[0]),
        t && (t == T ? "html" : t == E ? "json" : w.test(t) ? "script" : x.test(t) && "xml") || "text"
    }
    function f(t, e) {
        return "" == e ? t : (t + "&" + e).replace(/[&?]{1,2}/, "?")
    }
    function h(e) {
        e.processData && e.data && "string" != t.type(e.data) && (e.data = t.param(e.data, e.traditional)),
        !e.data || e.type && "GET" != e.type.toUpperCase() || (e.url = f(e.url, e.data),
        e.data = void 0)
    }
    function p(e, n, i, r) {
        return t.isFunction(n) && (r = i,
        i = n,
        n = void 0),
        t.isFunction(i) || (r = i,
        i = void 0),
        {
            url: e,
            data: n,
            success: i,
            dataType: r
        }
    }
    function d(e, n, i, r) {
        var o, a = t.isArray(n), s = t.isPlainObject(n);
        t.each(n, function(n, u) {
            o = t.type(u),
            r && (n = i ? r : r + "[" + (s || "object" == o || "array" == o ? n : "") + "]"),
            !r && a ? e.add(u.name, u.value) : "array" == o || !i && "object" == o ? d(e, u, i, n) : e.add(n, u)
        })
    }
    var m, g, v = 0, y = window.document, b = /<script\b[^<]*(?:(?!<\/script>)<[^<]*)*<\/script>/gi, w = /^(?:text|application)\/javascript/i, x = /^(?:text|application)\/xml/i, E = "application/json", T = "text/html", S = /^\s*$/, j = y.createElement("a");
    j.href = window.location.href,
    t.active = 0,
    t.ajaxJSONP = function(e, n) {
        if (!("type"in e))
            return t.ajax(e);
        var i, r, u = e.jsonpCallback, c = (t.isFunction(u) ? u() : u) || "jsonp" + ++v, l = y.createElement("script"), f = window[c], h = function(e) {
            t(l).triggerHandler("error", e || "abort")
        }, p = {
            abort: h
        };
        return n && n.promise(p),
        t(l).on("load error", function(o, u) {
            clearTimeout(r),
            t(l).off().remove(),
            "error" != o.type && i ? a(i[0], p, e, n) : s(null, u || "error", p, e, n),
            window[c] = f,
            i && t.isFunction(f) && f(i[0]),
            f = i = void 0
        }),
        o(p, e) === !1 ? (h("abort"),
        p) : (window[c] = function() {
            i = arguments
        }
        ,
        l.src = e.url.replace(/\?(.+)=\?/, "?$1=" + c),
        y.head.appendChild(l),
        e.timeout > 0 && (r = setTimeout(function() {
            h("timeout")
        }, e.timeout)),
        p)
    }
    ,
    t.ajaxSettings = {
        type: "GET",
        beforeSend: c,
        success: c,
        error: c,
        complete: c,
        context: null,
        global: !0,
        xhr: function() {
            return new window.XMLHttpRequest
        },
        accepts: {
            script: "text/javascript, application/javascript, application/x-javascript",
            json: E,
            xml: "application/xml, text/xml",
            html: T,
            text: "text/plain"
        },
        crossDomain: !1,
        timeout: 0,
        processData: !0,
        cache: !0
    },
    t.ajax = function(e) {
        var n, r, u = t.extend({}, e || {}), p = t.Deferred && t.Deferred();
        for (m in t.ajaxSettings)
            void 0 === u[m] && (u[m] = t.ajaxSettings[m]);
        i(u),
        u.crossDomain || (n = y.createElement("a"),
        n.href = u.url,
        n.href = n.href,
        u.crossDomain = j.protocol + "//" + j.host != n.protocol + "//" + n.host),
        u.url || (u.url = window.location.toString()),
        (r = u.url.indexOf("#")) > -1 && (u.url = u.url.slice(0, r)),
        h(u);
        var d = u.dataType
          , v = /\?.+=\?/.test(u.url);
        if (v && (d = "jsonp"),
        u.cache !== !1 && (e && e.cache === !0 || "script" != d && "jsonp" != d) || (u.url = f(u.url, "_=" + Date.now())),
        "jsonp" == d)
            return v || (u.url = f(u.url, u.jsonp ? u.jsonp + "=?" : u.jsonp === !1 ? "" : "callback=?")),
            t.ajaxJSONP(u, p);
        var b, w = u.accepts[d], x = {}, E = function(t, e) {
            x[t.toLowerCase()] = [t, e]
        }, T = /^([\w-]+:)\/\//.test(u.url) ? RegExp.$1 : window.location.protocol, C = u.xhr(), P = C.setRequestHeader;
        if (p && p.promise(C),
        u.crossDomain || E("X-Requested-With", "XMLHttpRequest"),
        E("Accept", w || "*/*"),
        (w = u.mimeType || w) && (w.indexOf(",") > -1 && (w = w.split(",", 2)[0]),
        C.overrideMimeType && C.overrideMimeType(w)),
        (u.contentType || u.contentType !== !1 && u.data && "GET" != u.type.toUpperCase()) && E("Content-Type", u.contentType || "application/x-www-form-urlencoded"),
        u.headers)
            for (g in u.headers)
                E(g, u.headers[g]);
        if (C.setRequestHeader = E,
        C.onreadystatechange = function() {
            if (4 == C.readyState) {
                C.onreadystatechange = c,
                clearTimeout(b);
                var e, n = !1;
                if (C.status >= 200 && C.status < 300 || 304 == C.status || 0 == C.status && "file:" == T) {
                    d = d || l(u.mimeType || C.getResponseHeader("content-type")),
                    e = C.responseText;
                    try {
                        "script" == d ? (1,
                        eval)(e) : "xml" == d ? e = C.responseXML : "json" == d && (e = S.test(e) ? null : t.parseJSON(e))
                    } catch (i) {
                        n = i
                    }
                    n ? s(n, "parsererror", C, u, p) : a(e, C, u, p)
                } else
                    s(C.statusText || null, C.status ? "error" : "abort", C, u, p)
            }
        }
        ,
        o(C, u) === !1)
            return C.abort(),
            s(null, "abort", C, u, p),
            C;
        if (u.xhrFields)
            for (g in u.xhrFields)
                C[g] = u.xhrFields[g];
        var O = "async"in u ? u.async : !0;
        C.open(u.type, u.url, O, u.username, u.password);
        for (g in x)
            P.apply(C, x[g]);
        return u.timeout > 0 && (b = setTimeout(function() {
            C.onreadystatechange = c,
            C.abort(),
            s(null, "timeout", C, u, p)
        }, u.timeout)),
        C.send(u.data ? u.data : null),
        C
    }
    ,
    t.get = function() {
        return t.ajax(p.apply(null, arguments))
    }
    ,
    t.post = function() {
        var e = p.apply(null, arguments);
        return e.type = "POST",
        t.ajax(e)
    }
    ,
    t.getJSON = function() {
        var e = p.apply(null, arguments);
        return e.dataType = "json",
        t.ajax(e)
    }
    ,
    t.fn.load = function(e, n, i) {
        if (!this.length)
            return this;
        var r, o = this, a = e.split(/\s/), s = p(e, n, i), u = s.success;
        return a.length > 1 && (s.url = a[0],
        r = a[1]),
        s.success = function(e) {
            o.html(r ? t("<div>").html(e.replace(b, "")).find(r) : e),
            u && u.apply(o, arguments)
        }
        ,
        t.ajax(s),
        this
    }
    ;
    var C = encodeURIComponent;
    t.param = function(e, n) {
        var i = [];
        return i.add = function(e, n) {
            t.isFunction(n) && (n = n()),
            null == n && (n = ""),
            this.push(C(e) + "=" + C(n))
        }
        ,
        d(i, e, n),
        i.join("&").replace(/%20/g, "+")
    }
}(Zepto),
function(t) {
    t.fn.serializeArray = function() {
        var e, n, i = [], r = function(t) {
            return t.forEach ? t.forEach(r) : void i.push({
                name: e,
                value: t
            })
        };
        return this[0] && t.each(this[0].elements, function(i, o) {
            n = o.type,
            e = o.name,
            e && "fieldset" != o.nodeName.toLowerCase() && !o.disabled && "submit" != n && "reset" != n && "button" != n && "file" != n && ("radio" != n && "checkbox" != n || o.checked) && r(t(o).val())
        }),
        i
    }
    ,
    t.fn.serialize = function() {
        var t = [];
        return this.serializeArray().forEach(function(e) {
            t.push(encodeURIComponent(e.name) + "=" + encodeURIComponent(e.value))
        }),
        t.join("&")
    }
    ,
    t.fn.submit = function(e) {
        if (0 in arguments)
            this.bind("submit", e);
        else if (this.length) {
            var n = t.Event("submit");
            this.eq(0).trigger(n),
            n.isDefaultPrevented() || this.get(0).submit()
        }
        return this
    }
}(Zepto),
function() {
    try {
        getComputedStyle(void 0)
    } catch (t) {
        var e = getComputedStyle;
        window.getComputedStyle = function(t) {
            try {
                return e(t)
            } catch (n) {
                return null
            }
        }
    }
}(),
function(t) {
    function e(t, e) {
        var n = this.os = {}
          , i = this.browser = {}
          , r = t.match(/Web[kK]it[\/]{0,1}([\d.]+)/)
          , o = t.match(/(Android);?[\s\/]+([\d.]+)?/)
          , a = !!t.match(/\(Macintosh\; Intel /)
          , s = t.match(/(iPad).*OS\s([\d_]+)/)
          , u = t.match(/(iPod)(.*OS\s([\d_]+))?/)
          , c = !s && t.match(/(iPhone\sOS)\s([\d_]+)/)
          , l = t.match(/(webOS|hpwOS)[\s\/]([\d.]+)/)
          , f = /Win\d{2}|Windows/.test(e)
          , h = t.match(/Windows Phone ([\d.]+)/)
          , p = l && t.match(/TouchPad/)
          , d = t.match(/Kindle\/([\d.]+)/)
          , m = t.match(/Silk\/([\d._]+)/)
          , g = t.match(/(BlackBerry).*Version\/([\d.]+)/)
          , v = t.match(/(BB10).*Version\/([\d.]+)/)
          , y = t.match(/(RIM\sTablet\sOS)\s([\d.]+)/)
          , b = t.match(/PlayBook/)
          , w = t.match(/Chrome\/([\d.]+)/) || t.match(/CriOS\/([\d.]+)/)
          , x = t.match(/Firefox\/([\d.]+)/)
          , E = t.match(/\((?:Mobile|Tablet); rv:([\d.]+)\).*Firefox\/[\d.]+/)
          , T = t.match(/MSIE\s([\d.]+)/) || t.match(/Trident\/[\d](?=[^\?]+).*rv:([0-9.].)/)
          , S = !w && t.match(/(iPhone|iPod|iPad).*AppleWebKit(?!.*Safari)/)
          , j = S || t.match(/Version\/([\d.]+)([^S](Safari)|[^M]*(Mobile)[^S]*(Safari))/);
        (i.webkit = !!r) && (i.version = r[1]),
        o && (n.android = !0,
        n.version = o[2]),
        c && !u && (n.ios = n.iphone = !0,
        n.version = c[2].replace(/_/g, ".")),
        s && (n.ios = n.ipad = !0,
        n.version = s[2].replace(/_/g, ".")),
        u && (n.ios = n.ipod = !0,
        n.version = u[3] ? u[3].replace(/_/g, ".") : null),
        h && (n.wp = !0,
        n.version = h[1]),
        l && (n.webos = !0,
        n.version = l[2]),
        p && (n.touchpad = !0),
        g && (n.blackberry = !0,
        n.version = g[2]),
        v && (n.bb10 = !0,
        n.version = v[2]),
        y && (n.rimtabletos = !0,
        n.version = y[2]),
        b && (i.playbook = !0),
        d && (n.kindle = !0,
        n.version = d[1]),
        m && (i.silk = !0,
        i.version = m[1]),
        !m && n.android && t.match(/Kindle Fire/) && (i.silk = !0),
        w && (i.chrome = !0,
        i.version = w[1]),
        x && (i.firefox = !0,
        i.version = x[1]),
        E && (n.firefoxos = !0,
        n.version = E[1]),
        T && (i.ie = !0,
        i.version = T[1]),
        j && (a || n.ios || f) && (i.safari = !0,
        n.ios || (i.version = j[1])),
        S && (i.webview = !0),
        n.tablet = !!(s || b || o && !t.match(/Mobile/) || x && t.match(/Tablet/) || T && !t.match(/Phone/) && t.match(/Touch/)),
        n.phone = !(n.tablet || n.ipod || !(o || c || l || g || v || w && t.match(/Android/) || w && t.match(/CriOS\/([\d.]+)/) || x && t.match(/Mobile/) || T && t.match(/Touch/)))
    }
    e.call(t, navigator.userAgent, navigator.platform),
    t.__detect = e
}(Zepto),
function(t) {
    function e(e, i) {
        var u = e[s]
          , c = u && r[u];
        if (void 0 === i)
            return c || n(e);
        if (c) {
            if (i in c)
                return c[i];
            var l = a(i);
            if (l in c)
                return c[l]
        }
        return o.call(t(e), i)
    }
    function n(e, n, o) {
        var u = e[s] || (e[s] = ++t.uuid)
          , c = r[u] || (r[u] = i(e));
        return void 0 !== n && (c[a(n)] = o),
        c
    }
    function i(e) {
        var n = {};
        return t.each(e.attributes || u, function(e, i) {
            0 == i.name.indexOf("data-") && (n[a(i.name.replace("data-", ""))] = t.zepto.deserializeValue(i.value))
        }),
        n
    }
    var r = {}
      , o = t.fn.data
      , a = t.camelCase
      , s = t.expando = "Zepto" + +new Date
      , u = [];
    t.fn.data = function(i, r) {
        return void 0 === r ? t.isPlainObject(i) ? this.each(function(e, r) {
            t.each(i, function(t, e) {
                n(r, t, e)
            })
        }) : 0 in this ? e(this[0], i) : void 0 : this.each(function() {
            n(this, i, r)
        })
    }
    ,
    t.fn.removeData = function(e) {
        return "string" == typeof e && (e = e.split(/\s+/)),
        this.each(function() {
            var n = this[s]
              , i = n && r[n];
            i && t.each(e || i, function(t) {
                delete i[e ? a(this) : t]
            })
        })
    }
    ,
    ["remove", "empty"].forEach(function(e) {
        var n = t.fn[e];
        t.fn[e] = function() {
            var t = this.find("*");
            return "remove" === e && (t = t.add(this)),
            t.removeData(),
            n.call(this)
        }
    })
}(Zepto),
function(t) {
    function e(n) {
        var i = [["resolve", "done", t.Callbacks({
            once: 1,
            memory: 1
        }), "resolved"], ["reject", "fail", t.Callbacks({
            once: 1,
            memory: 1
        }), "rejected"], ["notify", "progress", t.Callbacks({
            memory: 1
        })]]
          , r = "pending"
          , o = {
            state: function() {
                return r
            },
            always: function() {
                return a.done(arguments).fail(arguments),
                this
            },
            then: function() {
                var n = arguments;
                return e(function(e) {
                    t.each(i, function(i, r) {
                        var s = t.isFunction(n[i]) && n[i];
                        a[r[1]](function() {
                            var n = s && s.apply(this, arguments);
                            if (n && t.isFunction(n.promise))
                                n.promise().done(e.resolve).fail(e.reject).progress(e.notify);
                            else {
                                var i = this === o ? e.promise() : this
                                  , a = s ? [n] : arguments;
                                e[r[0] + "With"](i, a)
                            }
                        })
                    }),
                    n = null
                }).promise()
            },
            promise: function(e) {
                return null != e ? t.extend(e, o) : o
            }
        }
          , a = {};
        return t.each(i, function(t, e) {
            var n = e[2]
              , s = e[3];
            o[e[1]] = n.add,
            s && n.add(function() {
                r = s
            }, i[1 ^ t][2].disable, i[2][2].lock),
            a[e[0]] = function() {
                return a[e[0] + "With"](this === a ? o : this, arguments),
                this
            }
            ,
            a[e[0] + "With"] = n.fireWith
        }),
        o.promise(a),
        n && n.call(a, a),
        a
    }
    var n = Array.prototype.slice;
    t.when = function(i) {
        var r, o, a, s = n.call(arguments), u = s.length, c = 0, l = 1 !== u || i && t.isFunction(i.promise) ? u : 0, f = 1 === l ? i : e(), h = function(t, e, i) {
            return function(o) {
                e[t] = this,
                i[t] = arguments.length > 1 ? n.call(arguments) : o,
                i === r ? f.notifyWith(e, i) : --l || f.resolveWith(e, i)
            }
        };
        if (u > 1)
            for (r = new Array(u),
            o = new Array(u),
            a = new Array(u); u > c; ++c)
                s[c] && t.isFunction(s[c].promise) ? s[c].promise().done(h(c, a, s)).fail(f.reject).progress(h(c, o, r)) : --l;
        return l || f.resolveWith(a, s),
        f.promise()
    }
    ,
    t.Deferred = e
}(Zepto),
function(t) {
    t.Callbacks = function(e) {
        e = t.extend({}, e);
        var n, i, r, o, a, s, u = [], c = !e.once && [], l = function(t) {
            for (n = e.memory && t,
            i = !0,
            s = o || 0,
            o = 0,
            a = u.length,
            r = !0; u && a > s; ++s)
                if (u[s].apply(t[0], t[1]) === !1 && e.stopOnFalse) {
                    n = !1;
                    break
                }
            r = !1,
            u && (c ? c.length && l(c.shift()) : n ? u.length = 0 : f.disable())
        }, f = {
            add: function() {
                if (u) {
                    var i = u.length
                      , s = function(n) {
                        t.each(n, function(t, n) {
                            "function" == typeof n ? e.unique && f.has(n) || u.push(n) : n && n.length && "string" != typeof n && s(n)
                        })
                    };
                    s(arguments),
                    r ? a = u.length : n && (o = i,
                    l(n))
                }
                return this
            },
            remove: function() {
                return u && t.each(arguments, function(e, n) {
                    for (var i; (i = t.inArray(n, u, i)) > -1; )
                        u.splice(i, 1),
                        r && (a >= i && --a,
                        s >= i && --s)
                }),
                this
            },
            has: function(e) {
                return !(!u || !(e ? t.inArray(e, u) > -1 : u.length))
            },
            empty: function() {
                return a = u.length = 0,
                this
            },
            disable: function() {
                return u = c = n = void 0,
                this
            },
            disabled: function() {
                return !u
            },
            lock: function() {
                return c = void 0,
                n || f.disable(),
                this
            },
            locked: function() {
                return !c
            },
            fireWith: function(t, e) {
                return !u || i && !c || (e = e || [],
                e = [t, e.slice ? e.slice() : e],
                r ? c.push(e) : l(e)),
                this
            },
            fire: function() {
                return f.fireWith(this, arguments)
            },
            fired: function() {
                return !!i
            }
        };
        return f
    }
}(Zepto),
function(t) {
    function e(t, e, n, i) {
        return Math.abs(t - e) >= Math.abs(n - i) ? t - e > 0 ? "Left" : "Right" : n - i > 0 ? "Up" : "Down"
    }
    function n() {
        l = null,
        h.last && (h.el.trigger("longTap"),
        h = {})
    }
    function i() {
        l && clearTimeout(l),
        l = null
    }
    function r() {
        s && clearTimeout(s),
        u && clearTimeout(u),
        c && clearTimeout(c),
        l && clearTimeout(l),
        s = u = c = l = null,
        h = {}
    }
    function o(t) {
        return ("touch" == t.pointerType || t.pointerType == t.MSPOINTER_TYPE_TOUCH) && t.isPrimary
    }
    function a(t, e) {
        return t.type == "pointer" + e || t.type.toLowerCase() == "mspointer" + e
    }
    var s, u, c, l, f, h = {}, p = 750;
    t(document).ready(function() {
        var d, m, g, v, y = 0, b = 0;
        "MSGesture"in window && (f = new MSGesture,
        f.target = document.body),
        t(document).bind("MSGestureEnd", function(t) {
            var e = t.velocityX > 1 ? "Right" : t.velocityX < -1 ? "Left" : t.velocityY > 1 ? "Down" : t.velocityY < -1 ? "Up" : null;
            e && (h.el.trigger("swipe"),
            h.el.trigger("swipe" + e))
        }).on("touchstart MSPointerDown pointerdown", function(e) {
            (!(v = a(e, "down")) || o(e)) && (g = v ? e : e.touches[0],
            e.touches && 1 === e.touches.length && h.x2 && (h.x2 = void 0,
            h.y2 = void 0),
            d = Date.now(),
            m = d - (h.last || d),
            h.el = t("tagName"in g.target ? g.target : g.target.parentNode),
            s && clearTimeout(s),
            h.x1 = g.pageX,
            h.y1 = g.pageY,
            m > 0 && 250 >= m && (h.isDoubleTap = !0),
            h.last = d,
            l = setTimeout(n, p),
            f && v && f.addPointer(e.pointerId))
        }).on("touchmove MSPointerMove pointermove", function(t) {
            (!(v = a(t, "move")) || o(t)) && (g = v ? t : t.touches[0],
            i(),
            h.x2 = g.pageX,
            h.y2 = g.pageY,
            y += Math.abs(h.x1 - h.x2),
            b += Math.abs(h.y1 - h.y2))
        }).on("touchend MSPointerUp pointerup", function(n) {
            (!(v = a(n, "up")) || o(n)) && (i(),
            h.x2 && Math.abs(h.x1 - h.x2) > 30 || h.y2 && Math.abs(h.y1 - h.y2) > 30 ? c = setTimeout(function() {
                $(h.el).trigger("swipe"),
                $(h.el).trigger("swipe" + e(h.x1, h.x2, h.y1, h.y2)),
                h = {}
            }, 0) : "last"in h && (30 > y && 30 > b ? u = setTimeout(function() {
                var e = t.Event("tap");
                e.cancelTouch = r,
                $(h.el).trigger(e),
                h.isDoubleTap ? (h.el && h.el.trigger("doubleTap"),
                h = {}) : s = setTimeout(function() {
                    s = null,
                    h.el && h.el.trigger("singleTap"),
                    h = {}
                }, 250)
            }, 0) : h = {}),
            y = b = 0)
        }).on("touchcancel MSPointerCancel pointercancel", r),
        t(window).on("scroll", r)
    }),
    ["swipe", "swipeLeft", "swipeRight", "swipeUp", "swipeDown", "doubleTap", "tap", "singleTap", "longTap"].forEach(function(e) {
        t.fn[e] = function(t) {
            return this.on(e, t)
        }
    })
}(Zepto),
function(t, e) {
    function n(t) {
        return t.replace(/([a-z])([A-Z])/, "$1-$2").toLowerCase()
    }
    function i(t) {
        return r ? r + t : t.toLowerCase()
    }
    var r, o, a, s, u, c, l, f, h, p, d = "", m = {
        Webkit: "webkit",
        Moz: "",
        O: "o"
    }, g = document.createElement("div"), v = /^((translate|rotate|scale)(X|Y|Z|3d)?|matrix(3d)?|perspective|skew(X|Y)?)$/i, y = {};
    t.each(m, function(t, n) {
        return g.style[t + "TransitionProperty"] !== e ? (d = "-" + t.toLowerCase() + "-",
        r = n,
        !1) : void 0
    }),
    o = d + "transform",
    y[a = d + "transition-property"] = y[s = d + "transition-duration"] = y[c = d + "transition-delay"] = y[u = d + "transition-timing-function"] = y[l = d + "animation-name"] = y[f = d + "animation-duration"] = y[p = d + "animation-delay"] = y[h = d + "animation-timing-function"] = "",
    t.fx = {
        off: r === e && g.style.transitionProperty === e,
        speeds: {
            _default: 400,
            fast: 200,
            slow: 600
        },
        cssPrefix: d,
        transitionEnd: i("TransitionEnd"),
        animationEnd: i("AnimationEnd")
    },
    t.fn.animate = function(n, i, r, o, a) {
        return t.isFunction(i) && (o = i,
        r = e,
        i = e),
        t.isFunction(r) && (o = r,
        r = e),
        t.isPlainObject(i) && (r = i.easing,
        o = i.complete,
        a = i.delay,
        i = i.duration),
        i && (i = ("number" == typeof i ? i : t.fx.speeds[i] || t.fx.speeds._default) / 1e3),
        a && (a = parseFloat(a) / 1e3),
        this.anim(n, i, r, o, a)
    }
    ,
    t.fn.anim = function(i, r, d, m, g) {
        var b, w, x, E = {}, T = "", S = this, j = t.fx.transitionEnd, C = !1;
        if (r === e && (r = t.fx.speeds._default / 1e3),
        g === e && (g = 0),
        t.fx.off && (r = 0),
        "string" == typeof i)
            E[l] = i,
            E[f] = r + "s",
            E[p] = g + "s",
            E[h] = d || "linear",
            j = t.fx.animationEnd;
        else {
            w = [];
            for (b in i)
                v.test(b) ? T += b + "(" + i[b] + ") " : (E[b] = i[b],
                w.push(n(b)));
            T && (E[o] = T,
            w.push(o)),
            r > 0 && "object" == typeof i && (E[a] = w.join(", "),
            E[s] = r + "s",
            E[c] = g + "s",
            E[u] = d || "linear")
        }
        return x = function(e) {
            if ("undefined" != typeof e) {
                if (e.target !== e.currentTarget)
                    return;
                t(e.target).unbind(j, x)
            } else
                t(this).unbind(j, x);
            C = !0,
            t(this).css(y),
            m && m.call(this)
        }
        ,
        r > 0 && (this.bind(j, x),
        setTimeout(function() {
            C || x.call(S)
        }, 1e3 * (r + g) + 25)),
        this.size() && this.get(0).clientLeft,
        this.css(E),
        0 >= r && setTimeout(function() {
            S.each(function() {
                x.call(this)
            })
        }, 0),
        this
    }
    ,
    g = null
}(Zepto);

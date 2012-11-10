// ==UserScript==
// @name myUserJS
// @description FacepalmJS
// @author Super hackaton teams
// @license MIT
// @version 1.0
// @require http://localhost:9000/public/javascripts/jquery-1.8.2.js
// @require http://localhost:9000/public/javascripts/jquery.qtip.js
// @resource qtipCSS http://localhost:9000/public/stylesheets/jquery.qtip.css
// @include http://*
// ==/UserScript==
(function (window, undefined) {
    var w;
    if (typeof unsafeWindow != undefined) {
        w = unsafeWindow
    } else {
        w = window;
    }
    if (w.self != w.top) {
        return;
    }

    function like(param){
        alert(param);
    }

    $('img').each(function() {
        $(this).qtip({
            // content: '<a href="#">Edit</a> | <a href="#">Delete</a>', // Give it some content
            content:{
                text: 'Loading...', // The text to use whilst the AJAX request is loading
                ajax: {
                    url: 'http://localhost:9000/count/'+encodeURIComponent(window.location.toString())+"/"+encodeURIComponent($(this).attr("src")), // URL to the local file
                    once: false,
                    type: 'GET', // POST or GET
                    data: {}, // Data to pass along with your request
                    success: function(data, status) {
                        var a = $("<a>").text("Like "+data.count);
                        a.on("click",function(e){
                            alert(this);
                        });
                        this.set('content.text', a);

                    }
                }
            },
            position: 'topRight', // Set its position
            hide: {
                fixed: true // Make it fixed so it can be hovered over
            },
            style: {
                padding: '5px 15px', // Give it some extra padding
                name: 'dark' // And style it with the preset dark theme
            }
        });
    });
    GM_addStyle(".facepalm{text-decoration:  none}");
    GM_addStyle(".facepalm-shown{.facepalm; display:block;}");
    GM_addStyle(".facepalm-hidden{.facepalm; display:none;}");
    var qtipCSS = GM_getResourceText ("qtipCSS");
    GM_addStyle (qtipCSS);
    // load jQuery and execute the main function
    //addJQuery(main);
})(window);

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

    function runCodeOnPage(code){
        var script = document.createElement('script');
        script.textContent = code;
        (document.head||document.documentElement).appendChild(script);
        script.parentNode.removeChild(script);
    }


    function likeImage(imageUrl, counterSpan, mainSpan){
        var success = false;
        $.ajax({
            url: 'http://localhost:9000/like/'+encodeURIComponent(window.location.toString())+"/"+encodeURIComponent(imageUrl),
            type: 'POST',
            async:false,
            dataType: "json",
            success: function(data, status) {
                $(counterSpan).text(data.count);
                success = true;
            },
            error: function(jqXHR, textStatus, errorThrown){
                if (jqXHR.status==403){
                    $(mainSpan).html($("<a>").text("Login").attr("href","#").on("click",function(event){
                        var url = 'http://localhost:9000/miniLogin/'+encodeURIComponent(window.location.toString())+"/"+encodeURIComponent(imageUrl);
                        w.open(url,'loginWindow','height=200,width=300');
                        event.preventDefault ? event.preventDefault() : event.returnValue = false;
                    }));
                }
            }
        });
    }

    function setLikeLinkName(link, liked){
        $(link).text((!liked)?"Like":"Unlike")
    }

    $('img').each(function() {
        var imgSrc = $(this).attr("src");
        $(this).qtip({
            // content: '<a href="#">Edit</a> | <a href="#">Delete</a>', // Give it some content
            content:{
                text: 'Loading...', // The text to use whilst the AJAX request is loading
                ajax: {
                    url: 'http://localhost:9000/count/'+encodeURIComponent(window.location.toString())+"/"+encodeURIComponent(imgSrc), // URL to the local file
                    once: false,
                    cache: false,
                    type: 'GET', // POST or GET
                    data: {}, // Data to pass along with your request
                    success: function(data, status) {
                        var a = $("<a>");
                        var liked = data.wasLiked=="true";
                        setLikeLinkName(a,liked);
                        var span = $("<span>");
                        var counter = $("<span>").text(data.count);

                        a.on("click",function(e){
                            //if (data.loggedIn=="true"){
                            if (true){
                                liked = !liked;
                                setLikeLinkName(a,liked);
                                likeImage(imgSrc, counter, span);
                            }else{
                            }
                        });
                        span.append(a);
                        span.append(" ").append(counter);
                        this.set('content.text', span);

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

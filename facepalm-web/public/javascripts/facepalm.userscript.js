// ==UserScript==
// @name myUserJS
// @description FacepalmJS
// @author Super hackaton teams
// @license MIT
// @version 1.0
// @require http://localhost:9000/public/javascripts/jquery-1.8.2.js
// @require http://localhost:9000/public/javascripts/jquery.qtip.js
// @resource qtipCSS http://localhost:9000/public/stylesheets/jquery.qtip.css
// @resource bootstrapCSS http://localhost:9000/public/bootstrap/css/bootstrap.min.css
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

    function likeImage(imageUrl, link, liked, counterSpan, mainSpan){
        $.ajax({
            url: 'http://localhost:9000/like/'+encodeURIComponent(window.location.toString())+"/"+encodeURIComponent(imageUrl),
            type: 'POST',
            dataType: "json",
            success: function(data, status) {
                $(counterSpan).text(data.count);
                setLikeLinkName(link,liked);
            },
            error: function(jqXHR, textStatus, errorThrown){
                if (jqXHR.status==403){
                    $(mainSpan).html($("<a>").text("Login").attr("href","#").on("click",function(event){
                        var url = 'http://localhost:9000/miniLogin/'+encodeURIComponent(window.location.toString())+"/"+encodeURIComponent(imageUrl);
                        w.open(url,'loginWindow','height=300,width=400,left=200,top=200,scrollbars=0');
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

        var loader = $("<img>").attr("src","http://localhost:9000/public/images/ajax-loader.gif");
        var initialLoader = $("<span>").append(loader).append("          ");

        $(this).qtip({
            // content: '<a href="#">Edit</a> | <a href="#">Delete</a>', // Give it some content
            content:{
                text: initialLoader, // The text to use whilst the AJAX request is loading
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
                                counter.html(loader);
                                liked = !liked;
                                likeImage(imgSrc, a,liked,counter, span);
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
                tip: {
                    corner: false
                },
                classes:"ui-tooltip-tipped",
                width:70,
                padding: '2px 7px'// Give it some extra padding
            }
        });
    });
    var qtipCSS = GM_getResourceText ("qtipCSS");
    GM_addStyle (qtipCSS);
    // GM_addStyle ( GM_getResourceText ("bootstrapCSS"));

    // load jQuery and execute the main function
    //addJQuery(main);
})(window);

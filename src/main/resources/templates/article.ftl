<#import "frame.ftl" as main>
<#assign html_other_script in main>
<script>
    new Vue({
        el: '#wrap',
        data: {
            id: #{article.id!},
            readCount: 0
        },
        mounted: function () {
            var vm = this;
            Vue.http.get("${request.contextPath}/article/readCount/" + vm.id).then(function (res) {
                vm.readCount = res.data.data;
            });
        }
    });

    (function () {
        var appid = 'cysWz2225';
        var conf = '28580395836c063154ed2dbc8342f255';
        var width = window.innerWidth || document.documentElement.clientWidth;
        if (width < 960) {
            window.document.write('<script id="changyan_mobile_js" charset="utf-8" type="text/javascript" src="https://changyan.sohu.com/upload/mobile/wap-js/changyan_mobile.js?client_id=' + appid + '&conf=' + conf + '"><\/script>');
        } else {
            var loadJs = function (d, a) {
                var c = document.getElementsByTagName("head")[0] || document.head || document.documentElement;
                var b = document.createElement("script");
                b.setAttribute("type", "text/javascript");
                b.setAttribute("charset", "UTF-8");
                b.setAttribute("src", d);
                if (typeof a === "function") {
                    if (window.attachEvent) {
                        b.onreadystatechange = function () {
                            var e = b.readyState;
                            if (e === "loaded" || e === "complete") {
                                b.onreadystatechange = null;
                                a()
                            }
                        }
                    } else {
                        b.onload = a
                    }
                }
                c.appendChild(b)
            };
            loadJs("https://changyan.sohu.com/upload/changyan.js", function () {
                window.changyan.api.config({appid: appid, conf: conf})
            });
        }
    })();
</script>
</#assign>
<@main.page title="post">
<style>
    img {
        width: auto;
        height: auto;
        max-height: 100%;
        max-width: 100%;
    }
</style>
<!-- Set your background image for this header on the line below. -->
    <#if article??>
    <div id="wrap">
        <header class="intro-header" style="background-image: url('${request.contextPath}/img/post-bg.jpg')">
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                        <div class="post-heading">
                            <h1>${article.title!''}</h1>
                            <h2 class="subheading">${article.summary!''}</h2>
                            <span class="meta">
                        Posted by
                        <a href="${request.contextPath}/index">${article.author!''}</a>
                        on ${article.createTime?string('MMMM dd,yyyy')!''}
                    </span>
                            <p><span style="font-family: Georgia;">{{readCount}}</span>阅</p>
                        </div>
                    </div>
                </div>
            </div>
        </header>

        <!-- Post Content -->
        <article>
            <div class="container">
                <div class="row">
                    <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                        ${article.content!''}

                    </div>
                    <div id="SOHUCS" sid="#{article.id!}" class="col-md-10 col-md-offset-1"></div>
                </div>
            </div>
        </article>
    </div>
    <#else>
    <blockquote>
        <p class="text-center" style="padding: 50px 0;margin-top: 0;">文章不存在 <a
                href="${request.contextPath}/index">返回首页</a></p>
    </blockquote>
    </#if>
</@main.page>

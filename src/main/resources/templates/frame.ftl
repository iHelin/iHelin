<#macro page title>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content="细心在任何时候都不是多余的"/>
    <meta name="author" content="Ian He"/>
<#--<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>-->
    <title>${title} | Ian He</title>
    <link rel="icon" href="${request.contextPath}/favicon.ico"/>
    <link href="${request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${request.contextPath}/css/my-blog.css" rel="stylesheet">
    <link href="${request.contextPath}/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script>
        var _hmt = _hmt || [];
        (function () {
            var hm = document.createElement("script");
            hm.src = "https://hm.baidu.com/hm.js?23b843d83eae4b63a2240e788fece55f";
            var s = document.getElementsByTagName("script")[0];
            s.parentNode.insertBefore(hm, s);
        })();
    </script>
</head>

<body>
<!-- Navigation -->
<nav class="navbar navbar-default navbar-custom navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header page-scroll">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                菜单 <i class="fa fa-bars"></i>
            </button>
            <a class="navbar-brand" href="${request.contextPath}">Starting</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="${request.contextPath}/index">Index</a>
                </li>
                <li>
                    <a href="${request.contextPath}/about">About</a>
                </li>
                <li>
                    <a href="${request.contextPath}/article/0">Article</a>
                </li>
                <li>
                    <a href="${request.contextPath}/music">Music</a>
                </li>
                <li>
                    <a href="${request.contextPath}/contact">Contact</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
</nav>
    <#nested>
<hr>
<!-- Footer -->
<footer>
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                <ul class="list-inline text-center">
                    <li>
                        <a href="https://twitter.com/helinull">
                            <span class="fa-stack fa-lg">
                                <i class="fa fa-circle fa-stack-2x"></i>
                                <i class="fa fa-twitter fa-stack-1x fa-inverse"></i>
                            </span>
                        </a>
                    </li>
                    <li>
                        <a href="https://www.facebook.com/profile.php?id=100005657280939">
                                <span class="fa-stack fa-lg">
                                    <i class="fa fa-circle fa-stack-2x"></i>
                                    <i class="fa fa-facebook fa-stack-1x fa-inverse"></i>
                                </span>
                        </a>
                    </li>
                    <li>
                        <a href="https://github.com/iHelin">
                            <span class="fa-stack fa-lg">
                                <i class="fa fa-circle fa-stack-2x"></i>
                                <i class="fa fa-github fa-stack-1x fa-inverse"></i>
                            </span>
                        </a>
                    </li>
                    <li>
                        <a href="http://weibo.com/378920717">
                            <span class="fa-stack fa-lg">
                                <i class="fa fa-circle fa-stack-2x"></i>
                                <i class="fa fa-weibo fa-stack-1x fa-inverse"></i>
                            </span>
                        </a>
                    </li>
                </ul>
                <p class="copyright text-muted">Copyright &copy; iHelin ${.now?string('yyyy')}</p>
            </div>
        </div>
    </div>
</footer>
<!-- common js -->
<script src="${request.contextPath}/js/jquery.min.js"></script>
<script src="${request.contextPath}/js/bootstrap.min.js"></script>
<script src="${request.contextPath}/plugins/vue/vue.js"></script>
<script src="${request.contextPath}/plugins/vue/vue-resource.js"></script>
<script>
    const a = ["富强", "民主", "文明", "和谐", "自由", "平等", "公正", "法治", "爱国", "敬业", "诚信", "友善"];
    $(function () {
        $("body").on("input propertychange", ".floating-label-form-group", function (o) {
            $(this).toggleClass("floating-label-form-group-with-value", !!$(o.target).val())
        }).on("focus", ".floating-label-form-group", function () {
            $(this).addClass("floating-label-form-group-with-focus")
        }).on("blur", ".floating-label-form-group", function () {
            $(this).removeClass("floating-label-form-group-with-focus")
        }).click(function (e) {
            const $i = $("<span />").text(a[parseInt(Math.random() * a.length)]);
            const x = e.pageX, y = e.pageY;
            $i.css({
                "z-index": 999999,
                "top": y - 20,
                "left": x,
                "position": "absolute",
                "font-weight": "bold",
                "color": "#ff6651"
            });
            $("body").append($i);
            $i.animate({"top": y - 180, "opacity": 0}, 1500, function () {
                $i.remove();
            });
        });
        const s = 1170;
        if ($(window).width() > s) {
            const i = $(".navbar-custom").height();
            $(window).on("scroll", {previousTop: 0}, function () {
                const s = $(window).scrollTop();
                s < this.previousTop ? s > 0 && $(".navbar-custom").hasClass("is-fixed") ? $(".navbar-custom").addClass("is-visible") : $(".navbar-custom").removeClass("is-visible is-fixed") : s > this.previousTop && ($(".navbar-custom").removeClass("is-visible"), s > i && !$(".navbar-custom").hasClass("is-fixed") && $(".navbar-custom").addClass("is-fixed")), this.previousTop = s
            });
        }
    });

</script>
    ${html_other_script!}
</body>
</html>
</#macro>
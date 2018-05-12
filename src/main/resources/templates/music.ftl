<#import "frame.ftl" as main>
<@main.page title="音乐">
<!-- Page Header -->
<!-- Set your background image for this header on the line below. -->
<header class="intro-header" style="background-image: url('${request.contextPath}/img/music.jpg')">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1 col-xs-12">
                <div class="site-heading">
                    <h2>Music</h2>
                </div>
            </div>
        </div>
    </div>
</header>
<!-- Main Content -->
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div style="margin:0 auto;text-align:center;">
                <iframe frameborder="no" border="0" marginwidth="0" marginheight="0" width="530" height="450"
                        src="https://music.163.com/outchain/player?type=0&amp;id=321809155&amp;auto=1&amp;height=430"></iframe>
            </div>
        </div>
    </div>
</div>
</@main.page>

<#import "frame.ftl" as main>
<@main.page title="index">

<!-- Page Header -->
<!-- Set your background image for this header on the line below. -->
<header class="intro-header" style="background-image: url('${request.contextPath}/img/home-bg.jpg')">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
                <div class="site-heading">
                    <h1>Ian He</h1>
                    <hr class="small">
                    <span class="subheading">Art  Design  Code</span>
                </div>
            </div>
        </div>
    </div>
</header>

<!-- Main Content -->
<div class="container">
    <div class="row">
        <div class="col-lg-8 col-lg-offset-2 col-md-10 col-md-offset-1">
            <#if articles??>
                <#list articles as article>
                    <div class="post-preview">
                        <a href="${request.contextPath}/article/#{article.id!}">
                            <h2 class="post-title">
                            ${article.title!''}
                            </h2>
                            <h3 class="post-subtitle">
                            ${article.summary!''}
                            </h3>
                        </a>
                        <p class="post-meta">Posted by <a href="${request.contextPath}/index">${article.author!''}</a>
                            on ${article.createTime?string('MMMM dd,yyyy')!''}</p>
                    </div>
                    <hr>
                </#list>
            </#if>
            <!-- Pager -->
            <ul class="pager">
                <li class="next">
                    <a href="javascript:void(0);">Older Posts &rarr;</a>
                </li>
            </ul>
        </div>
    </div>
</div>
</@main.page>


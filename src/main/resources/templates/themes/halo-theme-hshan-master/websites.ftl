<#include "module/macro.ftl">
<@layout title="${settings.websites_title!'网站收藏'} | ${blog_title!}">
    <div id="page" class="site ">
        <main class="site-main"  id="main">
            <div class="site-content">
                <header class="bg-cover page-header">
                    <#if settings.websites_patternimg?? && settings.websites_patternimg!=''>
                        <div class="cover-bg">
                            <img src="${settings.websites_patternimg!}" alt="${settings.websites_title!'网站收藏'}">
                        </div>
                    <#else>
                        <div class="default-cover-bg">
                        </div>
                    </#if>
                    <div class="cover-content">
                        <div class="inner">
                            <div class="post-count"></div>
                            <h1 class="page-title" style="font-size: 46px;">${settings.websites_title!'网站收藏'}</h1>
                        </div>
                    </div>
                </header>
                <div class="links-box">
                    <div class="links-items">
                        <@websiteTag method="listTeams">
                            <#list teams as item>
                                <#if item.team?? && item.team!=''>
                                    <h3 style="width: 100%; margin: 10px;">${item.team}</h3>
                                </#if>

                                <#list item.websites?sort_by('priority')?reverse  as website>
                                    <a class="links-item card-item-vel" style="display: block;" href="${website.url}" target="_blank" onfocus="this.blur();">
                                        <div class="media">
                                            <div class="media-left">
                                                <figure class="image is-64x64">
                                                    <#if website.logo?? && website.logo != ''>
                                                        <img src="${website.logo}" alt="${website.name}">
                                                    <#elseif settings.links_placeholder?? && settings.links_placeholder != ''>
                                                        <img src="${settings.links_placeholder}"
                                                             alt="${website.name}">
                                                    <#else>
                                                        <img src="https://cdn.jsdelivr.net/gh/hshanx/static@v1.0.1/placeholder.jpg"
                                                             alt="${website.name}">
                                                    </#if>
                                                </figure>
                                            </div>
                                            <div class="media-content">
                                                <p class="link-title">${website.name}</p>
                                                <p class="link-desc">${website.description}</p>
                                            </div>
                                        </div>
                                    </a>
                                </#list>
                            </#list>
                        </@websiteTag>
                    </div>
                </div>
            </div>

        </main>
    </div>
</@layout>

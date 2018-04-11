<#list doc.book.@@ as attr>
- ${attr?node_name} = ${attr}
</#list>

<#list doc.book.* as c>
- ${c?node_name}
</#list>
<#list doc["book/chapter[title='Ch1']/para"] as p>
<p>${p}
</#list>

<h1>${doc.book.title}</h1>
<h2>${doc.book.chapter[0].title}</h2>
<h2>${doc.book.chapter[1].title}</h2>
<#list doc.book.chapter as chapter>
<h3>${chapter.title}</h3>
</#list>
<#assign book = doc.book>
<h1>${book.title}</h1>
<#list book.chapter as ch>
<h2>${ch.title}</h2>
<#list ch.para as p>
<p>${p}
</#list>
</#list>
-------------------------------
<#list doc.book.chapter.para as p>
<p>${p}
</#list>
-------------------------------
<#list doc.book.chapter as p>
================================
<#list p.para as p2>
<p1>${p2}</p1>
</#list>
</#list>
1================================
<#list doc.book.chapter.@title as t>
${t}
</#list>
2================================
<#list doc.book?children as c>
- ${c?node_type} <#if c?node_type = 'element'>${c?node_name}</#if>
</#list>


<#escape x as x?html>
<#assign book = doc.book>
<h1>${book.title}</h1>
<#list book.chapter as ch>
<h2>${ch.title}</h2>
<#list ch.para as p>
<p>${p}
</#list>
</#list>
</#escape>
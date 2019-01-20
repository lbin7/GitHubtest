<#if page>
/** 定义基础模块(带分页模块) */
var app = angular.module('${projectName}',['pagination']);
<#else>
/** 定义基础模块(不带分页模块) */
var app = angular.module('${projectName}',[]);
</#if>
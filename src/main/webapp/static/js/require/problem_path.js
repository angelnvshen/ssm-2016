/**
 * 当然，除了 data-main 属性，你也可以手动配置 baseUrl ，比如下面例子。需要强调的是：

 如果没有通过 data-main 属性指定 baseUrl ，也没有通过config的方式显示声明 baseUrl ，那么 baseUrl 默认为加载requirejs的那个页面所在的路径
 *
 *
 */

requirejs.config({
    baseUrl: ctx + '/static/js/',
    "paths": {
        /**
         * 没有 ctx baseUrl时
          */
        //"jquery": "jquery-1.11.1.min"             // https://localhost:8443/ssm_2016/static/js/require/jquery-1.11.1.min.js
        //"jquery": "../require/jquery-1.11.1.min"    // https://localhost:8443/ssm_2016/static/js/require/jquery-1.11.1.min.js
        "jquery": "lib/jquery-1.11.1.min"
    }
});

require(['jquery'], function($) {
    alert($().jquery);
});
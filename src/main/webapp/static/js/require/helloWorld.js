requirejs.config({
    "paths": {
        "jquery": "/ssm_2016/static/js/lib/jquery-1.11.1.min"
    }
});

require(['jquery'], function($) {
    alert($().jquery);
});
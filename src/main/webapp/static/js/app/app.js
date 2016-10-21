requirejs.config({
    "baseUrl": ctx + "/static/js/",
    "paths": {
        "app": "app",
        "jquery": "lib/jquery-1.11.1.min",
        "math": "require_util/math"
    },
    urlArgs: "bust=" + (new Date()).getTime() //清楚缓存
   /* "shim": {
        "jquery.alpha": ["lib/jquery-1.11.1.min"],
        "jquery.beta": ["lib/jquery-1.11.1.min"]
    }*/
});

// Load the main app module to start the app
requirejs(["app/main"]);
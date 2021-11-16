// 实际项目中不需要此文件
// 这里只是为了demo可以在不同项目中演示运行
// 因为不同项目注入的android对象不一样，这里只是动态设置android对象而已

// JS获取url参数
function getQueryVariable(variable) {
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
               var pair = vars[i].split("=");
               if(pair[0] == variable){return pair[1];}
       }
       return(false);
}

// 动态加载js文件
var script = document.createElement("script");
script.type="text/javascript";
//script.src= getQueryVariable("js_file");
script.innerHTML = "app = " + getQueryVariable("app_instance_name") + ";";
document.getElementsByTagName("head")[0].appendChild(script);

// 在url后面添加js_file文件
function appendParamToUrl(targetUrl) {
    if(targetUrl.indexOf("?") > 0) {
        return targetUrl + "&app_instance_name=" + getQueryVariable("app_instance_name");
    } else {
        return targetUrl + "?app_instance_name=" + getQueryVariable("app_instance_name");
    }
}
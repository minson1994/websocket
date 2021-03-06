//设置cookies 
function setCookie(name, value) { 
    var exp = new Date(); 
    exp.setTime(exp.getTime() + 12*60 * 60 * 1000); 
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=/"; 
}

//读取cookies 
function getCookie(name) { 
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)"); 
    if (arr = document.cookie.match(reg)) 
        return unescape(arr[2]); 
    else 
        return null; 
}

//删除cookies 
function delCookie(name) { 
    var exp = new Date(); 
    exp.setTime(exp.getTime() - 60 * 60 * 1000); 
    var cval = getCookie(name); 
    if (cval != null) 
        document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString() + ";path=/"; 
}


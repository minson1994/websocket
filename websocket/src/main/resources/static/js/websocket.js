var socket;
console.log(getCookie("userJson"))
var user=JSON.parse(getCookie("userJson"));
var token=getCookie("user_token");
var login_type=getCookie("login_type");

if(user==null||token==null||login_type==null){
	location.href="login.html"
}



if(typeof(WebSocket) == "undefined") {  
    console.log("您的浏览器不支持WebSocket");  
}else{  
    console.log("您的浏览器支持WebSocket"); 
    $.ajax({
    	url:"/websocket/user/getSocketToken",
    	data:{
    		token:token,
    		user_id:user.user_id,
    		login_type:login_type
    	},
    	dataType:"json",
    	success:function(e){
    		if(e.flag==1){
    			socket(e.data);
    		}else{
    			alert(e.msg)
    		}
    	},
    	error:function(e){
    		
    	}
    })
}

function socket(ct){
	//实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接  
    socket = new WebSocket("ws://127.0.0.1:94/websocket/ISsocket/"+user.user_id+"/"+ct+"/"+login_type) ;  
    //打开事件  
    socket.onopen = function() {  
        console.log("Socket 已打开");  
        //socket.send("这是来自客户端的消息" + location.href + new Date());  
    };  
    //获得消息事件  
    socket.onmessage = function(msg) {  
        var m=JSON.parse(msg.data)
        console.log(m);
        if(m.msg_secne=="p2p"){
        	$(".k").append("<p>"+m.send_user==null?"":m.send_user.nickname+":"+m.msg+"</p>")
        }else{
        	$(".k").append("<p>"+m.msg+"</p>")
        }
        
        //发现消息进入    开始处理前端触发逻辑
    };  
    //关闭事件  
    socket.onclose = function() {  
        console.log("Socket已关闭");  
    };  
    //发生了错误事件  
    socket.onerror = function() {  
        alert("Socket发生了错误");  
        //此时可以尝试刷新页面
    }
}


function fasong(){
	if($(".n").val()!=''){
		var t={
			msg:$(".n").val(),
			to_user_id:user.user_id==1?2:1,
			msg_type:"text",
			send_user:user
		}
		socket.send(JSON.stringify(t));
		$(".k").append("<p>"+user.nickname+":"+$(".n").val()+"</p>")
		$(".n").val("");
	}
}
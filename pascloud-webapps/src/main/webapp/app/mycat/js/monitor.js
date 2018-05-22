

var showgraph = function(url) {
		$('#contentGraph').attr('src', url);
	};

var getGraphUrl = function() {
		var url = $('#contentGraph').attr('src');
		var index = url.indexOf('&');
		if (index != -1) {
			url = url.substring(0, index);
		}
		return url;
	};
	
function change(index){
	var url = "";
	if(index==1){
		url= "/graph/D_192.168.0.7_9066/MycatActiveThreadGraph?probe=MycatThreadPool";
	}else if(index==2){
		url= "/graph/D_192.168.0.7_9066/MycatTaskQueueGraph?probe=MycatThreadPool";
	}else if(index==3){
		url= "/graph/D_192.168.0.7_9066/MycatTPSGraph?probe=MycatThreadPool";
	}else if(index==4){
		url= "/graph/D_192.168.0.7_9066/MycatMemoryGraph?probe=MycatMemory";
	}else if(index==5){
		url= "/graph/D_192.168.0.7_9066/MycatFluxGraph?probe=MycatPerfProbe";
	}else if(index==6){
		url= "/graph/D_192.168.0.7_9066/MycatConnectionGraph?probe=MycatPerfProbe";
	}
	$('#contentGraph').attr('src', url);
	
	
}
$.extend($.fn.layout.methods, {
	full : function(jq) {
		return jq.each(function() {
			var layout = $(this);
			var center = layout.layout('panel', 'center');
			
			center.panel('maximize');
			center.parent().css('z-index', 10);
			$(window).on('resize.full', function() {
				layout.layout('unFull').layout('resize');
			});
		});
	},
	unFull : function(jq) {
		return jq.each(function() {
			var center = $(this).layout('panel', 'center');
			center.parent().css('z-index', 'inherit');
			center.panel('restore');
			var width = $(window).width()-150;     
		    var height = $(window).height()-45;  
		    alert(height)
			center.width(width);  
			center.height(height);
			
			$(window).off('resize.full');
		});
	}
});

function initTabTools() {
	$('#mainCenter').tabs({
		tools : [ {
			iconCls : 'icon-arrow_nw_ne_sw_se',
			handler : function() {
				centerMax();
			}
		} ]
	});
}

function centerMax() {
	var max = false;
	
	if(!max){
		var layout = $('#main').layout(); 
		//layout.layout('collapse','west'); 
		//layout.layout('collapse','north'); 
		$('#main').layout("full");
		max = true;
	}else{
		
		$('#main').layout("unFull");
		//layout.layout('expand','north'); 
		//
		max = false;
	}
	
	
}


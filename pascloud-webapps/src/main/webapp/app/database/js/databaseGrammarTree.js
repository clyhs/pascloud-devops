function initDatabaseGrammarTrees(){
    //EasyUILoad('databaseTableTrees');
    $('#sqlGrammarTree').tree({
	    url:'grammarTree.json',
	    cascadeCheck: false,
	    checkbox: false,
		method:'get',
		border:false,
		loadFilter:function(data){
		    //dispalyEasyUILoad( 'databaseTableTrees' );	
		    dispalyEasyUILoad( 'sqlGrammarTree' );
		    //alert(data);
		    return data;
		},
		//onContextMenu: onContextMenu,
		//onClick: onTableClick,
		//onDblClick: onTableDblClick,
		onSelect: function (node) {
        },
        onLoadSuccess: function (node, data) {   
        }
	});
}
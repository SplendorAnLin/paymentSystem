/* =================================================
// jQuery Tabs Plugins 1.0
// author:lp
// =================================================*/

;(function($){
	$.fn.extend({
		Tabs:function(options){
			// 处理参数
			options = $.extend({
				event : 'mouseover',
				timeout : 0,
				auto : 0,
				callback : null
			}, options);
			
			var self = $(this),
				tabBox = self.children( '.w_tabs_con' ),
				menu = self.children( '.tab_menu' ),
				items = menu.find( '.tab_items' ),
				timer;
				
			var tabHandle = function( elem ){
					elem.siblings( '.tab_items' )
						.removeClass( 'select' )
						.end()
						.addClass( 'select' );
						
					tabBox.siblings( '.w_tabs_con' )
						.hide()
						.end()
						.eq( elem.index() )
						.show();
				},
					
				delay = function( elem, time ){
					time ? setTimeout(function(){ tabHandle( elem ); }, time) : tabHandle( elem );
				},
				
				start = function(){
					if( !options.auto ) return;
					timer = setInterval( autoRun, options.auto );
				},
				
				autoRun = function(){
					var current = menu.find( '.tab_items.select' ),
						firstItem = items.eq(0),
						len = items.length,
						index = current.index() + 1,
						item = index === len ? firstItem : current.next( '.tab_items' ),
						i = index === len ? 0 : index;
					
					current.removeClass( 'select' );
					item.addClass( 'select' );
					
					tabBox.siblings( '.w_tabs_con' )
						.hide()
						.end()
						.eq(i)
						.show();
				};
							
			items.bind( options.event, function(){
				delay( $(this), options.timeout );
				if( options.callback ){
					options.callback( self );
				}
			});
			
			if( options.auto ){
				start();
				self.hover(function(){
					clearInterval( timer );
					timer = undefined;
				},function(){
					start();
				});
			}
			
			return this;
		}
	});
})(jQuery);


function AddFavorite(g,j){try{window.external.addFavorite(g,j)}catch(e){try{window.sidebar.addPanel(j,g,"")}catch(k){alert("\u8bf7\u4f7f\u7528Ctrl+D\u6536\u85cf\u672c\u9875!")}}}function SetHomePage(g,j){try{g.style.behavior="url(#default#homepage)",g.setHomePage(j)}catch(e){}}
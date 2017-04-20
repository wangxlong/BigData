<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
    <head>
        <title>公告栏</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

		<script src="http://libs.baidu.com/jquery/1.4.4/jquery.min.js"></script>
		<script src="/Yltysport/js/jquery.easing.1.3.js" type="text/javascript"></script>
		<script src="/Yltysport/js/jquery.booklet.1.1.0.min.js" type="text/javascript"></script>

		<link href="/Yltysport/css/jquery.booklet.1.1.0.css" type="text/css" rel="stylesheet" media="screen" />
		<link rel="stylesheet" href="/Yltysport/css/fanshustyle.css" type="text/css" media="screen"/>

		<script src="/Yltysport/js/cufon-yui.js" type="text/javascript"></script>
		<!-- <script src="/Yltysport/js/ChunkFive_400.font.js" type="text/javascript"></script>  -->
		<!--  <script src="/Yltysport/js/Note_this_400.font.js" type="text/javascript"></script> -->
		<script type="text/javascript">
			Cufon.replace('h1,p,.b-counter');
			Cufon.replace('.book_wrapper a', {hover:true});
			Cufon.replace('.title');
			Cufon.replace('.reference a');
			Cufon.replace('.loading');
		</script>
    </head>
    <body>
		<!--  <h1 class="title">详细公告</h1> -->
		<div class="book_wrapper">
			<!--<a id="next_page_button"></a>
			<a id="prev_page_button"></a>
			-->
			<!--  <div id="loading" class="loading">Loading pages...</div> -->
			<div id="mybook" style="display:none;">
				<div class="b-load">
					<div>
						 <img src="/Yltysport/scollePiction/1.jpg" alt=""/> 
						<h1>${announcementBean.title}</h1>
						<p>
							${announcementBean.content}
						</p>
						
						<a href="/Yltysport/wuser/setAnnouncement" class="demo"/>返回</a>
						
						<!--  <a href="http://www.jq22.com/" target="_blank" class="demo">Demo</a> -->
					</div>
				</div>
			</div>
		</div>
        <div>
           <!--
            <span class="reference">
                <a href=" http://www.jq22.com/">back to the Codrops tutorial</a>
				<a href="http://www.jq22.com" target="_blank">booklet jQuery Plugin</a>
            </span>
            -->
		</div>

        <!-- The JavaScript -->

        <script type="text/javascript">
			$(function() {
				var $mybook 		= $('#mybook');
				var $bttn_next		= $('#next_page_button');
				var $bttn_prev		= $('#prev_page_button');
				var $loading		= $('#loading');
				var $mybook_images	= $mybook.find('img');
				var cnt_images		= $mybook_images.length;
				var loaded			= 0;
				//preload all the images in the book,
				//and then call the booklet plugin

				$mybook_images.each(function(){
					var $img 	= $(this);
					var source	= $img.attr('src');
					$('<img/>').load(function(){
						++loaded;
						if(loaded == cnt_images){
							$loading.hide();
							$bttn_next.show();
							$bttn_prev.show();
							$mybook.show().booklet({
								name:               "",                            // name of the booklet to display in the document title bar
								width:              800,                             // container width
								height:             500,                             // container height
								speed:              600,                             // speed of the transition between pages
								direction:          'LTR',                           // direction of the overall content organization, default LTR, left to right, can be RTL for languages which read right to left
								startingPage:       0,                               // index of the first page to be displayed
								easing:             'easeInOutQuad',                 // easing method for complete transition
								easeIn:             'easeInQuad',                    // easing method for first half of transition
								easeOut:            'easeOutQuad',                   // easing method for second half of transition

								closed:             true,                           // start with the book "closed", will add empty pages to beginning and end of book
								closedFrontTitle:   null,                            // used with "closed", "menu" and "pageSelector", determines title of blank starting page
								closedFrontChapter: null,                            // used with "closed", "menu" and "chapterSelector", determines chapter name of blank starting page
								closedBackTitle:    null,                            // used with "closed", "menu" and "pageSelector", determines chapter name of blank ending page
								closedBackChapter:  null,                            // used with "closed", "menu" and "chapterSelector", determines chapter name of blank ending page
								covers:             false,                           // used with  "closed", makes first and last pages into covers, without page numbers (if enabled)

								pagePadding:        10,                              // padding for each page wrapper
								pageNumbers:        true,                            // display page numbers on each page

								hovers:             false,                            // enables preview pageturn hover animation, shows a small preview of previous or next page on hover
								overlays:           false,                            // enables navigation using a page sized overlay, when enabled links inside the content will not be clickable
								tabs:               false,                           // adds tabs along the top of the pages
								tabWidth:           60,                              // set the width of the tabs
								tabHeight:          20,                              // set the height of the tabs
								arrows:             false,                           // adds arrows overlayed over the book edges
								cursor:             'pointer',                       // cursor css setting for side bar areas

								hash:               false,                           // enables navigation using a hash string, ex: #/page/1 for page 1, will affect all booklets with 'hash' enabled
								keyboard:           true,                            // enables navigation with arrow keys (left: previous, right: next)
								next:               $bttn_next,          			// selector for element to use as click trigger for next page
								prev:               $bttn_prev,          			// selector for element to use as click trigger for previous page

								menu:               null,                            // selector for element to use as the menu area, required for 'pageSelector'
								pageSelector:       false,                           // enables navigation with a dropdown menu of pages, requires 'menu'
								chapterSelector:    false,                           // enables navigation with a dropdown menu of chapters, determined by the "rel" attribute, requires 'menu'

								shadows:            true,                            // display shadows on page animations
								shadowTopFwdWidth:  166,                             // shadow width for top forward anim
								shadowTopBackWidth: 166,                             // shadow width for top back anim
								shadowBtmWidth:     50,                              // shadow width for bottom shadow

								before:             function(){},                    // callback invoked before each page turn animation
								after:              function(){}                     // callback invoked after each page turn animation
							});
							Cufon.refresh();
						}
					}).attr('src',source);
				});
				
			});
        </script>
    </body>
</html>
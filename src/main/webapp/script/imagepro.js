/**
 * 
 */
 
 //Reference: 
//https://www.onextrapixel.com/2012/12/10/how-to-create-a-custom-file-input-with-jquery-css3-and-php/
;(function($) {

    // Browser supports HTML5 multiple file?
    var multipleSupport = typeof $('<input/>')[0].multiple !== 'undefined',
        isIE = /msie/i.test( navigator.userAgent );

    $.fn.customFile = function() {

      return this.each(function() {

        var $file = $(this).addClass('custom-file-upload-hidden'), // the original file input
            $wrap = $('<div class="file-upload-wrapper">'),
            $input = $('<input type="text" class="file-upload-input" />'),
            // Button that will be used in non-IE browsers
            $button = $('<button type="button" class="file-upload-button">Select a File</button>'),
            // Hack for IE
            $label = $('<label class="file-upload-button" for="'+ $file[0].id +'">Select a File</label>');

        // Hide by shifting to the left so we
        // can still trigger events
        $file.css({
          position: 'absolute',
          left: '-9999px'
        });

        $wrap.insertAfter( $file )
          .append( $file, $input, ( isIE ? $label : $button ) );

        // Prevent focus
        $file.attr('tabIndex', -1);
        $button.attr('tabIndex', -1);

        $button.click(function () {
          $file.focus().click(); // Open dialog
        });

        $file.change(function() {

          var files = [], fileArr, filename;

          // If multiple is supported then extract
          // all filenames from the file array
          if ( multipleSupport ) {
            fileArr = $file[0].files;
            for ( var i = 0, len = fileArr.length; i < len; i++ ) {
              files.push( fileArr[i].name );
            }
            filename = files.join(', ');

          // If not supported then just take the value
          // and remove the path to just show the filename
          } else {
            filename = $file.val().split('\\').pop();
          }

          $input.val( filename ) // Set the value
            .attr('title', filename) // Show filename in title tootlip
            .focus(); // Regain focus

        });

        $input.on({
          blur: function() { $file.trigger('blur'); },
          keydown: function( e ) {
            if ( e.which === 13 ) { // Enter
              if ( !isIE ) { $file.trigger('click'); }
            } else if ( e.which === 8 || e.which === 46 ) { // Backspace & Del
              // On some browsers the value is read-only
              // with this trick we remove the old input and add
              // a clean clone with all the original events attached
              $file.replaceWith( $file = $file.clone( true ) );
              $file.trigger('change');
              $input.val('');
            } else if ( e.which === 9 ){ // TAB
              return;
            } else { // All other keys
              return false;
            }
          }
        });

      });

    };

    // Old browser fallback
    if ( !multipleSupport ) {
      $( document ).on('change', 'input.customfile', function() {

        var $this = $(this),
            // Create a unique ID so we
            // can attach the label to the input
            uniqId = 'customfile_'+ (new Date()).getTime(),
            $wrap = $this.parent(),

            // Filter empty input
            $inputs = $wrap.siblings().find('.file-upload-input')
              .filter(function(){ return !this.value }),

            $file = $('<input type="file" id="'+ uniqId +'" name="'+ $this.attr('name') +'"/>');

        // 1ms timeout so it runs after all other events
        // that modify the value have triggered
        setTimeout(function() {
          // Add a new input
          if ( $this.val() ) {
            // Check for empty fields to prevent
            // creating new inputs when changing files
            if ( !$inputs.length ) {
              $wrap.after( $file );
              $file.customFile();
            }
          // Remove and reorganize inputs
          } else {
            $inputs.parent().remove();
            // Move the input so it's always last on the list
            $wrap.appendTo( $wrap.parent() );
            $wrap.find('input').focus();
          }
        }, 1);

      });
    }

}(jQuery));

$('input[type=file]').customFile();


function readURL(input) {
	  if (input.files && input.files[0]) {

	    var reader = new FileReader();

	    reader.onload = function(e) {
	      $('.image-upload-wrap').hide();

	      $('.file-upload-image').attr('src', e.target.result);
	      $('.file-upload-content').show();

	      //$('.image-title').html(input.files[0].name);
	    };
	    
	    var src = $('#sourceImage').prop('src');
	    //alert(src);
	    $('#imagePath').val(src);

	    reader.readAsDataURL(input.files[0]);

	  } else {
	    removeUpload();
	  }
	}

	function removeUpload() {
	  $('.file-upload-input').replaceWith($('.file-upload-input').clone());
	  $('.file-upload-content').hide();
	  $('.image-upload-wrap').show();
	}
	$('.image-upload-wrap').bind('dragover', function () {
	    $('.image-upload-wrap').addClass('image-dropping');
	  });
	  $('.image-upload-wrap').bind('dragleave', function () {
	    $('.image-upload-wrap').removeClass('image-dropping');
	});
	  
	  
	function ocri(){
		document.forms[0].action= "<%=PageHelper.getPageActionUrl(pageid, "ocr", request)%>";
		document.forms[0].submit();
		
	}
	function mirrori(){
		document.forms[0].action= "<%=PageHelper.getPageActionUrl(pageid, "mirrorimage", request)%>";
		document.forms[0].submit();
	}
	function watermarki(){
		document.forms[0].action= "<%=PageHelper.getPageActionUrl(pageid, "watermark", request)%>";
		document.forms[0].submit();
	}
	function orientationi(){
		document.forms[0].action= "<%=PageHelper.getPageActionUrl(pageid, "orientation", request)%>";
		document.forms[0].submit();
	}
	function facedetectioni(){
		document.forms[0].action= "<%=PageHelper.getPageActionUrl(pageid, "facedetection", request)%>";
		document.forms[0].submit();
	}
	function bluri(){
		document.forms[0].action= "<%=PageHelper.getPageActionUrl(pageid, "blurimage", request)%>";
		document.forms[0].submit();
	}
	function negativei(){
		document.forms[0].action= "<%=PageHelper.getPageActionUrl(pageid, "negativeimage", request)%>";
		document.forms[0].submit();
	}
	function greyscalei(){
		document.forms[0].action= "<%=PageHelper.getPageActionUrl(pageid, "greyscaleimage", request)%>";
		document.forms[0].submit();
	}
	function sharpeni(){
		document.forms[0].action= "<%=PageHelper.getPageActionUrl(pageid, "sharpenimage", request)%>";
		document.forms[0].submit();
	}
	function resolutioni(){
		document.forms[0].action= "<%=PageHelper.getPageActionUrl(pageid, "resolution", request)%>";
		document.forms[0].submit();
	}
	function compressi(){
		document.forms[0].action= "<%=PageHelper.getPageActionUrl(pageid, "compress", request)%>";
		document.forms[0].submit();
	}
	function rotateimg(){
		document.forms[0].action= "<%=PageHelper.getPageActionUrl(pageid, "rotateimg", request)%>";
		document.forms[0].submit();
	}


	//Selecting Image To Perform Image Operation
	
	$(document).ready(function(){
	    // add/remove checked class
	    $(".image-radio").each(function(){
	        if($(this).find('input[type="radio"]').first().attr("checked")){
	            $(this).addClass('image-radio-checked');
	        }else{
	            $(this).removeClass('image-radio-checked');
	        }
	    });

	    // sync the input state
	    $(".image-radio").on("click", function(e){
	        $(".image-radio").removeClass('image-radio-checked');
	        $(this).addClass('image-radio-checked');
	        var $radio = $(this).find('input[type="radio"]');
	        $radio.prop("checked",!$radio.prop("checked"));

	        e.preventDefault();
	    });
	});
	
	$(document).ready(function(){
		$("input:radio[name=rotate]").click(function() {   
    		$('.rotateImg').removeClass().addClass('rotateImg');
        	$(this).parent().addClass('selected');
	});
});


        $(document).ready(function () {
            $('input[type="radio"][name=rotate]').click(function () {
                var inputValue = $(this).attr("value");
                var targetBox = $("." + inputValue);
                //alert($(targetBox).attr('value'));
                if (inputValue == "-1") {
                
                    //$(".div1").not(targetBox).show();
                    $(".div2").not(targetBox).hide();
                    $(".div1").show();
                    //$("input[type=radio][name=rotate]").prop('checked', false);
                }
                if (inputValue == "1") {
                    //$(".div1").not(targetBox).hide();
                    $(".div1").not(targetBox).hide();
                    $(".div2").show();
                   // $("input[type=radio][name=rotate]").prop('checked', false);
                }
            });
        });
$(document).ready(function () {
	$('.upload-image').click(function () {
    	var value = $(this).attr('name');
        if(value=="rotate"){
                    $("#rotatepopup").show();
                    $("#watermarkpopup").hide();
        }
        if(value=="watermark"){
                    $("#watermarkpopup").show();
                    $("#rotatepopup").hide();
        }
    });
});
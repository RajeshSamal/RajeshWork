$(document).ready(function() {
	$('input[type="radio"][name="host"]').change(function() {
		switch ($(this).val()) {
		case 'single-host':
			$('#single').css("display", "block");
			$('#multiple').css("display", "none");
			break;
		case 'multiple-host':
			$('#multiple').css("display", "block");
			$('#single').css("display", "none");
			break;
		}
	});

	$(function() {
		$("#singleDiv img").draggable({
			revert : "invalid",
			'helper' : 'clone',
			drag : function(event, ui) {

			}
		});
		$("#multipleDiv img").draggable({
			revert : "invalid",
			'helper' : 'clone',
			drag : function(event, ui) {

			}
		});

		$("#DestDev").droppable({
			drop : function(event, ui) {

				var clone = ui.draggable.clone(); // clone it and hold onto
				var type = 	clone[0].id;	
				if(type === 'multiple'){
					return;
				}

				clone[0].id = "newIdDestDev";
				clone.css("position", "absolute");
				$("#DestDev").append(clone);
				var o = $('#DestDev').offset();
				var h = $('#newIdDestDev').height() - 200;
				var w = $('#newIdDestDev').width() - 200;
				if(type === 'multiple'){
					h= h-30;
					o.top = o.top-10;
				}
				$('#newIdDestDev').css({
					'top' : o.top + 20,
					'left' : o.left + 100,
					'width' : w,
					'height' : h
				});
				$("#DestDev").droppable('disable');
				$.blockUI({message: ''});
				$.ajax({
					url : "dev?type="+type,
					success : function (data) {
						$('#newIdDestDev').css({
							'background-color' : "#c0c0c0"
						});
						          
						$("#popupdev").append(data);
						$('#newIdDestDev').click(function() {
							$("#popupdev").dialog({
								title: "Dev Host Details",
								width: 430,
								height: 250,
								modal: true,
								buttons: {
								Close: function() {
								$(this).dialog('close');
								}
								}
								});
						});
						$.unblockUI();
					},
					error: function() {
						$.unblockUI();
						$('#newIdDestQa').css({
							'background-color' : "red"
						});
				      }
					});
			}
		});
		$("#DestQa").droppable({
			drop : function(event, ui) {

				var clone = ui.draggable.clone(); // clone it and hold onto
				var type = 	clone[0].id;	
				if(type === 'multiple'){
					return;
				}

				clone[0].id = "newIdDestQa";
				clone.css("position", "absolute");
				$("#DestQa").append(clone);
				var o = $('#DestQa').offset();
				var h = $('#newIdDestQa').height() - 200;
				var w = $('#newIdDestQa').width() - 200;
				if(type === 'multiple'){
					h= h-30;
					o.top = o.top-10;
				}
				$('#newIdDestQa').css({
					'top' : o.top + 20,
					'left' : o.left + 100,
					'width' : w,
					'height' : h
				});
				$("#DestQa").droppable('disable');
				$.blockUI({message: ''});
				$.ajax({
					url : "qa?type="+type,
					success : function (data) {
						$('#newIdDestQa').css({
							'background-color' : "#c0c0c0"
						});
						          
						$("#popupqa").append(data);
						$('#newIdDestQa').click(function() {
							$("#popupqa").dialog({
								title: "QA Host Details",
								width: 430,
								height: 250,
								modal: true,
								buttons: {
								Close: function() {
								$(this).dialog('close');
								}
								}
								});
						});
						$.unblockUI();
					},
					error: function() {
						$.unblockUI();
						$('#newIdDestQa').css({
							'background-color' : "red"
						});
				      }
					});
			}
		});
		$("#DestStage").droppable({
			drop : function(event, ui) {

				var clone = ui.draggable.clone(); // clone it and hold onto
				var type = 	clone[0].id;										// the

				clone[0].id = "newIdDestStage";
				clone.css("position", "absolute");
				$("#DestStage").append(clone);
				var o = $('#DestStage').offset();
				var h = $('#newIdDestStage').height() - 200;
				var w = $('#newIdDestStage').width() - 200;
				if(type === 'multiple'){
					h= h-30;
					o.top = o.top-10;
				}
				$('#newIdDestStage').css({
					'top' : o.top + 20,
					'left' : o.left + 100,
					'width' : w,
					'height' : h
				});
				$("#DestStage").droppable('disable');
				$.blockUI({message: ''});
				$.ajax({
					url : "stage?type="+type,
					success : function (data) {
						$('#newIdDestStage').css({
							'background-color' : "#c0c0c0"
						});
						          
						$("#popupstage").append(data);
						$('#newIdDestStage').click(function() {
							$("#popupstage").dialog({
								title: "Staging Host Details",
								width: 430,
								height: 250,
								modal: true,
								buttons: {
								Close: function() {
								$(this).dialog('close');
								}
								}
								});
						});
						$.unblockUI();
					},
					error: function() {
						$.unblockUI();
						$('#newIdDestStage').css({
							'background-color' : "red"
						});
				      }
					});
			}
		});
		$("#DestAWS").droppable({
			drop : function(event, ui) {

				var clone = ui.draggable.clone(); // clone it and hold onto
				var type = 	clone[0].id;										// the
				if(type === 'multiple'){
					return;
				}
				clone[0].id = "newIdDestAWS";
				clone.css("position", "absolute");
				$("#DestAWS").append(clone);
				var o = $('#DestAWS').offset();
				var h = $('#newIdDestAWS').height() - 200;
				var w = $('#newIdDestAWS').width() - 200;
				$('#newIdDestAWS').css({
					'top' : o.top + 20,
					'left' : o.left + 100,
					'width' : w,
					'height' : h
				});
				$("#DestAWS").droppable('disable');
				$.blockUI({message: ''});
				$.ajax({
					url : "aws?type="+type,
					success : function (data) {
						$('#newIdDestAWS').css({
							'background-color' : "#c0c0c0"
						});
						          
						$("#popupaws").append(data);
						$('#newIdDestAWS').click(function() {
							$("#popupaws").dialog({
								title: "AWS Host Details",
								width: 430,
								height: 250,
								modal: true,
								buttons: {
								Close: function() {
								$(this).dialog('close');
								}
								}
								});
						});
						$.unblockUI();
					},
					error: function() {
						$.unblockUI();
						$('#newIdDestAWS').css({
							'background-color' : "red"
						});
				      }
					});
			}
		});

	});
});
	$(".refund").click(function() {
		swal({
				icon: "warning",
	            title: "確定要退票嗎?",
	            	buttons: {
	                  	a: {
	                  		text: "取消",
	                  		value: "cancel",
	                  		visible: true
	                  	},
	                  	danger: {
	                  		text: "確定",
	                  		value: "confirm",
	                  		visible: true
	                  	}
	            }
	            }).then((value) => {
	                if (value === "confirm") {
	                	$(this).prev().submit();
	                }
	            });
			});
$("#orderTotal").text(getTotalPrice());

    function getTotalPrice() {
        let list = $("tr.orderlist");
        let total = 0;
        for (let i = 0; i < list.length; i++) {
            let thisSubTotal = $(list[i]).find("span.subtotal");
            thisSubTotal.text(thisSubTotal.prev().text() * thisSubTotal.prev().prev().text());
            total = total + parseInt(thisSubTotal.text());
        }
        return total;
    };
    
    $(".seatCharts-seat").click(function() {
    	diff = ticTypTotal - sc.find('selected').length
    	if (diff === 0) {
    		$("#nextStep").text("確認訂單");
    		$("#nextStep").prop("disabled",false);
    	} else {
    		$("#nextStep").text("還有 "+ diff + "個座位待劃位");
    		$("#nextStep").prop("disabled", true);
    	}
    })
    
    $("#grade-number").text(getGradeNumber($("#grade-word").text()));

		function getGradeNumber(gradeWord) {
			if ("普遍級" === gradeWord) {
				$("#grade-number").css("background-color","#00A600");
				return "0+";
			} else if ("保護級" === gradeWord) {
				$("#grade-number").css("background-color","#2894FF");
				return "6+";
			} else if ("輔導級" === gradeWord) {
				$("#grade-number").css("background-color","#FFE153");
				return "12+";
			} else {
				$("#grade-number").css("background-color","#EA0000");
				return "18+";
			}
		}
		$(function() {
			$("#tabs").tabs();
		});
		$("#nextStep").click(function() {
			$("#form").submit();
		});

		$("#grade-number").text(getGradeNumber($("#grade-word").text()));

		function getGradeNumber(gradeWord) {
			if ("普遍級" === gradeWord) {
				$("#grade-number").css("background-color", "#00A600");
				return "0+";
			} else if ("保護級" === gradeWord) {
				$("#grade-number").css("background-color", "#2894FF");
				return "6+";
			} else if ("輔導級" === gradeWord) {
				$("#grade-number").css("background-color", "#FFE153");
				return "12+";
			} else {
				$("#grade-number").css("background-color", "#EA0000");
				return "18+";
			}
		}

		$(".ticketCount").change(
				function() {
					//計算此票種的小計
					$(this).parent().next().text(
							$(this).parent().prev()
									.children("span.ticketPrice").text()
									* $(this).val());

					let ticketCountDoms = $(".ticketCount");
					//判斷是否有選票
					let counts = 0;
					for (let i = 0; i < ticketCountDoms.length; i++) {
						counts += parseInt($(ticketCountDoms[i]).val());
					}
					if (counts !== 0) {
						$("#nextStep").text("繼續");
						$("#nextStep").prop("disabled", false);
					} else {
						$("#nextStep").text("請至少選一張票");
						$("#nextStep").prop("disabled", true);
					}

					//判斷還能選多少張票
					let countDiff = 10 - counts;
					for (let i = 0; i < ticketCountDoms.length; i++) {
						let nowSelect = parseInt($(ticketCountDoms[i]).val());
						$(ticketCountDoms[i]).empty();
						for (let j = 0; j <= (countDiff + nowSelect); j++) {
							if (nowSelect === j)
								$(ticketCountDoms[i]).append(
										"<option value=" + j + " selected>" + j
												+ "</option>");
							else
								$(ticketCountDoms[i]).append(
										"<option value=" + j + ">" + j
												+ "</option>");
						}
					}
				});

		$(".ticketCount")
				.change(
						function() {
							let productPrice = $(this).parent().prev()
									.children("span.ticketPrice").text();
							let productCount = parseInt($(this).val());
							let productName = $(this).parent().prev().prev()
									.text();
							let tableStructure = "";

							let idName = "#" + productName;
							if (productCount !== 0) {
								if ($(idName)) {
									$(idName).remove();
								}

								tableStructure += "<tr id='"+productName +"'><td>";
								tableStructure += "<p>" + productName + "</p>";
								tableStructure += "<p class='text-right'>$";
								tableStructure += "<span style='margin:3px;'>"
										+ productPrice + "</span>X";
								tableStructure += "<span style='margin:3px;'>"
										+ productCount + "</span>=";
								tableStructure += "<span class='subtotal' style='margin:3px;'>"
										+ (productPrice * productCount)
										+ "</span>";
								tableStructure += "</p></td></tr>";
							} else {
								$(idName).remove();
							}
							$("#addhere").before(tableStructure);

						});

		$(".foodCount")
				.change(
						function() {
							let productPrice = $(this).prev().children(
									"span.foodPrice").text();
							let productCount = parseInt($(this).val());
							let productName = $(this).prev().prev().text();
							let tableStructure = "";

							let target = $(this).attr("name");
							let idName = "#" + target;
							if (productCount !== 0) {
								if ($(idName)) {
									$(idName).remove();
								}

								tableStructure += "<tr id='"+target +"'><td>";
								tableStructure += "<p>" + productName + "</p>";
								tableStructure += "<p class='text-right'>$";
								tableStructure += "<span style='margin:3px;'>"
										+ productPrice + "</span>X";
								tableStructure += "<span style='margin:3px;'>"
										+ productCount + "</span>=";
								tableStructure += "<span class='subtotal' style='margin:3px;'>"
										+ (productPrice * productCount)
										+ "</span>";
								tableStructure += "</p></td></tr>";

							} else {
								$(idName).remove();
							}
							$("#addhere").before(tableStructure);
						});

		$(".ticketCount,.foodCount").change(function() {
			let orderList = $(".subtotal");
			let totalPrice = 0;

			for (let i = 0; i < orderList.length; i++) {
				totalPrice += parseInt($(orderList[i]).text());
			}
			$("#orderTotal").text(totalPrice);
		})
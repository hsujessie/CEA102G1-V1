            var $cart = $('#selected-seats'),
                $counter = $('#counter'),
                $total = $('#total'),
                sc = $('#seat-map').seatCharts({
                    map: seatMap,
                    seats: {
                        // 可以自訂class利用CSS調整數值
                        t: {
                            price: 100,
                            classes: 'first-class', //your custom CSS class
                            category: 'First Class'
                        },


                    },
                    naming: {
                        // 顯示上方編號
                        // top: false,

                        // 顯示側邊編號
                        // left: false;

                        // 調整上方編號
//                        columns: ['01', '02', '03', '', '04', '05'],
                        columns: columnNo,

                        // 調整側邊編號
//                        rows: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'],
                        rows: rowNo,
                        // 調整格子編號
                        getLabel: function(character, row, column) {
                            return +column;
                        },
                        // 調整ID
                        getId: function(character, row, column) {
                            return row + '' + column;
                        }
                    },
                    // 右側說明欄
                    legend: {
                        node: $('#legend'),
                        items: [
                            ['t', 'available', '可選擇'],
                            ['t', 'selected', '你的座位'],
                            ['f', 'unavailable', '已售出']
                        ]
                    },
                    click: function() {
                        if (this.status() == 'available') {
                            var maxChoose = ticTypTotal;
                            var nowChoose = sc.find('selected').length;
                            if (nowChoose < maxChoose) {
                                //let's create a new <li> which we'll add to the cart items
                            	if (checkSpace(this.settings.id)) {
                            		swal("請重新選擇", "您所選的位置之間不能有空位", "error", {button: "關閉"});
                            		return 'available';
                            	} else {
                            		$('<li>' + this.data().category + ' Seat # ' + this.settings.label + ': <b>$' + this.data().price + '</b> <a href="#" class="cancel-cart-item">[cancel]</a></li>')
                            			.attr('id', 'cart-item-' + this.settings.id)
                            			.data('seatId', this.settings.id)
                            			.appendTo($cart);

                            		/*
                            		 * Lets update the counter and total
                            		 *
                                 	* .find function will not find the current seat, because it will change its stauts only after return
                                 	* 'selected'. This is why we have to add 1 to the length and the current seat price to the total.
                                 	*/
                            		$counter.text(sc.find('selected').length + 1);

                            		return 'selected';
                            	}
                            } else {
                            	let content = "最多只能選 " + ticTypTotal + " 個位置"
                            	swal("不能再選了", content, "error", {button: "關閉"});
                                return 'available';
                            }
                        } else if (this.status() == 'selected') {
                            //update the counter
                            $counter.text(sc.find('selected').length - 1);
                            //and total

                            //remove the item from our cart
                            $('#cart-item-' + this.settings.id).remove();

                            //seat has been vacated
                            return 'available';
                        } else if (this.status() == 'unavailable') {
                            //seat has been already booked
                            return 'unavailable';
                        } else {
                            return this.style();
                        }

                    }
                });

            //this will handle "[cancel]" link clicks
            $('#selected-seats').on('click', '.cancel-cart-item', function() {
                //let's just trigger Click event on the appropriate seat, so we don't have to repeat the logic here
                sc.get($(this).parents('li:first').data('seatId')).click();
            });

            //let's pretend some seats have already been booked
            // sc.get(['1_2', '4_1', '7_1', '7_2']).status('unavailable');

            // 將所有e改為已被選擇
            sc.find('f').status('unavailable')
            
            $("#nextStep").click(function() {
            	var str = "";
            	
     			sc.find("selected").each(function() {
     				str += this.settings.id;
     			});
     			
     			$("#chooseSeatNo").val(str);
     			$("#form").submit();
    		});
            
            function checkSpace(thisID) {
            	let id = "#" + thisID;
            	prevClass = $(id).prev().attr("class");
            	nextClass = $(id).next().attr("class");
            	
            	if (prevClass) { //看下一格是不是在邊邊
            		let index = prevClass.indexOf("space");
            		if (index === -1) { // -1  上一格不是走道
            			if ($(id).prev().prev().attr("class")) {//看下下格式不是在邊邊
            				let index = $(id).prev().prev().attr("class").indexOf("selected");
            				let index2 = $(id).prev().attr("class").indexOf("selected");
            				if (index2 === -1) { //下一格不是被選
            					if (index !== -1) { //  下下一格是被選
                					return true;
                				}
            				}
            			} 
            		}
            			
            		
            		
            	}
            	if (nextClass) {
            		let index = nextClass.indexOf("space");
            		if (index === -1) { // -1  上一格不是走道
            			if ($(id).next().next().attr("class")) {//看下下格式不是在邊邊
            				let index = $(id).next().next().attr("class").indexOf("selected");
            				let index2 = $(id).next().attr("class").indexOf("selected");
            				if (index2 === -1) { //下一格不是被選
            					if (index !== -1) { //  下下一格是被選
                					return true;
                				}
            				}
            			} 
            				
            		}
            		
            	}
            	return false;
            }

        
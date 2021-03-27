		for (let i = 0; i < 20; i++) {
			let begin = i * 20;
			let end = (i + 1) * 20;
			let str = sesSeatStatus.substring(begin, end);

			str = str.replace(new RegExp('9', 'g'), "_");
			str = str.replace(new RegExp('3', 'g'), "_");
			str = str.replace(new RegExp('0', 'g'), "t");
			str = str.replace(new RegExp('1', 'g'), "f");
			seatMap.push(str);
		}

		let count = 0;
		for (let i = 0; i < seatMap.length; i++) {
			if (seatMap[i] === "____________________") {
				rowNo.push("");
				continue;
			}
			count++;
			rowNo.push(count);
		}

		count = 0;
		out: for (let j = 0; j < seatMap.length; j++) {
			let str = "";
			for (let i = 0; i < seatMap.length; i++) {
				str = str + seatMap[i].charAt(j);
			}
			if (str === "____________________") {
				columnNo.push("");
				continue out;
			}
			count++;
			columnNo.push(count);
		}

		for (let i = 0; i < columnNo.length; i++) {
			if (columnNo[i] !== "" && columnNo[i] < 10) {
				columnNo[i] = "0" + columnNo[i];
			}
		}
		
		rowNo = replaceNo(rowNo);
		
		function replaceNo(arr) {
			let charArr = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];
			let index = 0;
			for (let i = 0; i < arr.length; i++) {
				if (arr[i] !== "") {
					arr[i] = charArr[index];
					index++;
				}
			}
			return arr;
		}
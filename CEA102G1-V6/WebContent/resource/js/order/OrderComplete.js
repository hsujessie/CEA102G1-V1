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

$("#chooseSeatNo").text(addComma(chooseSeatNo));

function addComma(chooseSeatNo) {
	let result = "";
	for (let i = 0; i < chooseSeatNo.length; i += 3) {
		let subStr = chooseSeatNo.substring(i, i + 3);
		if (i + 3 !== chooseSeatNo.length) {
			result = result + subStr + ", ";
		} else {
			result = result + subStr;
		}
	}
	return result;
}
package theaterSeat;

public class seatNoTransform {

	
	public static String seatNoTran(String str) {
	String seat = str.replaceAll("\\s+", "");
	System.out.println(seat);
	int aisleIndex = 0; 
	String seatNo = "";
	int seatIndex =1;
	char seatHeader = '\u0040';
	for( int i=0;i<seat.length();i++) {
		String thisSeat = "";

		if(aisleIndex==20) {
			seatIndex = 1;
			aisleIndex=0;
		} else if(i%20 ==0) {
			seatHeader++;
			seatIndex = 1;
			aisleIndex=0;
		}
		
		switch(seat.charAt(i)) {
		case '0':
			thisSeat+=seatHeader;
			if(seatIndex<10) {
				thisSeat+="0";
				thisSeat+=seatIndex;
			} else {
				thisSeat+=seatIndex;
			}
			seatIndex++;
			break;
		case '3':
			thisSeat+="333";
			aisleIndex++;
			break;
		case '9':
			thisSeat+="999";
			seatIndex++;
			break;
		}
		seatNo+=thisSeat;
	}
	return seatNo;
	}
	
}
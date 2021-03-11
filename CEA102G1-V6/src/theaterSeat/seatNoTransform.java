package theaterSeat;

public class seatNoTransform {

	public static void main(String[] args) {

		String str = "99993300000000339999\r\n" + 
				"99993300000000339999\r\n" + 
				"99993300000000339999\r\n" + 
				"99993300000000339999\r\n" + 
				"00003300000000330000\r\n" + 
				"00003300000000330000\r\n" + 
				"00003300000000330000\r\n" + 
				"00003300000000330000\r\n" + 
				"00003300000000330000\r\n" + 
				"00003300000000330000\r\n" + 
				"00003300000000330000\r\n" + 
				"33333333333333333333\r\n" + 
				"00003300000000330000\r\n" + 
				"00003300000000330000\r\n" + 
				"00003300000000330000\r\n" + 
				"00003300000000330000\r\n" + 
				"00003300000000330000\r\n" + 
				"00003300000000330000\r\n" + 
				"00003300000000330000\r\n" + 
				"00003300000000330000";
		
		System.out.println(seatNoTran(str));
		

}
	
	public static String seatNoTran(String str) {
	String seat = str.replaceAll("\\s+", "");
	System.out.println(seat);
	String seatNo = "";
	int seatIndex =1;
	char seatHeader = '\u0040';
	for( int i=0;i<seat.length();i++) {
		String thisSeat = "";
		if (i%20 ==0) {
			seatHeader++;
			seatIndex = 1;
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
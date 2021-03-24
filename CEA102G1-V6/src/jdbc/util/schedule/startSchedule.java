package jdbc.util.schedule;

import java.util.Timer;
import java.util.TimerTask;

import com.session.model.SesService;

public class startSchedule {
	public static Timer start(Integer delay, Integer sesNo, String chooseSeatNo) {
		Timer timer = new Timer();
		
		TimerTask task = new TimerTask(){ 
	        public void run() {
	        	SesService sesSvc = new SesService();
	        	sesSvc.updateSeatStatus(chooseSeatNo, sesNo, "recovery_seat");
	        } 
      };
      
      timer.schedule(task, delay);
      return timer;
	}

}

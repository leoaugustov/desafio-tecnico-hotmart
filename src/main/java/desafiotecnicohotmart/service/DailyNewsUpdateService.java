package desafiotecnicohotmart.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class DailyNewsUpdateService {

	private static final String EVERYDAY_4_TIMES = "0 0 3,9,15,20 * * *";
	
	private final NewsCountService newsCountService;
	
	@Scheduled(cron = EVERYDAY_4_TIMES)
	public void updateNewsCount() {
		try {
			newsCountService.countAndUpdateNews();
			log.info("Daily news count update successfully finished");
		}catch(Exception e) {
			log.error("Fatal error during news count update", e);
		}
	}
	
}

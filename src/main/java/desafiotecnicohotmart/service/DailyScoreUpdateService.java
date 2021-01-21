package desafiotecnicohotmart.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class DailyScoreUpdateService {

	private static final String EVERYDAY_AT_MIDNIGHT = "10 0 0 * * *";
	private final ProductScoreCalculationService productScoreCalculationService;
	
	@Scheduled(cron = EVERYDAY_AT_MIDNIGHT)
	public void updateScores() {
		try {
			productScoreCalculationService.calculateAndPersistScores();
			log.info("Daily score update successfully completed");
		}catch(Exception e) {
			log.error("Error during calculation of products scores", e);
		}
	}
	
	
}

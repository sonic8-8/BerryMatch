package com.gongcha.berrymatch.match.Service;

import com.gongcha.berrymatch.match.Repository.MatchUserRepository;
import com.gongcha.berrymatch.match.Repository.MatchingQueueRepository;
import com.gongcha.berrymatch.match.domain.MatchQueueStatus;
import com.gongcha.berrymatch.match.domain.MatchUser;
import com.gongcha.berrymatch.match.domain.MatchingQueue;
import com.gongcha.berrymatch.user.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchingQueueUpdateService {

    private final MatchingQueueRepository matchingQueueRepository;
    private final MatchUserRepository matchUserRepository;

    @Autowired
    public MatchingQueueUpdateService(MatchingQueueRepository matchingQueueRepository, MatchUserRepository matchUserRepository) {
        this.matchingQueueRepository = matchingQueueRepository;
        this.matchUserRepository = matchUserRepository;
    }

    @Async("queueStatusTaskExecutor")
    @Scheduled(fixedDelay = 500000)
    @Transactional
    public void updateQueueStatuses() {
        List<MatchUser> matchedUsers = matchUserRepository.findAll();

        for (MatchUser matchUser : matchedUsers) {
            User user = matchUser.getUser();
            Optional<MatchingQueue> pendingEntryOptional = matchingQueueRepository.findByUserAndStatus(user, MatchQueueStatus.PENDING);

            pendingEntryOptional.ifPresent(queueEntry -> {
                // Use optimistic locking and let the database handle concurrency
                queueEntry.setStatus(MatchQueueStatus.MATCHED);
                matchingQueueRepository.save(queueEntry);
            });
        }
    }
}

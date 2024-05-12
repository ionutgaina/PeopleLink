package app.Link.service;

import app.Link.model.User;
import app.Link.model.Blacklist;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.Link.repository.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BlacklistRepository blacklistRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void saveBlacklist(Blacklist blacklist) {
        blacklistRepository.save(blacklist);
    }

    public void deleteBlacklistById(Long id) {
        blacklistRepository.deleteById(id);
    }

    public void blockUser(Long user1Id, Long user2Id) {
        try {
            User user1 = getUserById(user1Id);
            User user2 = getUserById(user2Id);
            if (user1 == null || user2 == null) {
                throw new Exception("Block user: User not found");
            }
            Blacklist blacklistUser1Sender = Blacklist.builder()
                    .sender(user1)
                    .receiver(user2)
                    .build();
            Blacklist blacklistUser2Sender = Blacklist.builder()
                    .sender(user2)
                    .receiver(user1)
                    .build();
            user1.getBlackListsSender().add(blacklistUser1Sender);
            user1.getBlacklistsReceiver().add(blacklistUser2Sender);
            user2.getBlackListsSender().add(blacklistUser2Sender);
            user2.getBlacklistsReceiver().add(blacklistUser1Sender);
            saveBlacklist(blacklistUser1Sender);
            saveBlacklist(blacklistUser2Sender);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

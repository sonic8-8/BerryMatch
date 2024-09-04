package com.gongcha.berrymatch.chatRoom;

import com.gongcha.berrymatch.chatMessage.ChatMessageRepository;
import com.gongcha.berrymatch.match.MatchStatus;
import com.gongcha.berrymatch.match.Repository.MatchingQueueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    //  매칭 완료된 경기에 대한 모든 정보를 가져와서 저장해야함
    public MatchCompletionRequest createChatRoom(MatchStatus status) {

        return

    }

    // 해당하는 채팅방의 정보를 불러옴
    public ChatRoom loadChatRoomInfo(Long roomId){
        return chatRoomRepository.findAllById(roomId);
    }

}

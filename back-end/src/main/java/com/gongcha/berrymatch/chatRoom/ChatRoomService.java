package com.gongcha.berrymatch.chatRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    // Node 통해 받은 매칭 완료된 경기에 대한 모든 정보를 가져와서 저장해야함
    public ChatRoom transferMatchResultsToChat(ChatRoomDTO chatRoom){
        ChatRoom newChatRoom = ChatRoom.builder()
                .chatName(chatRoom.getChatName())
                .users(chatRoom.getUsers())
                .build();
        return chatRoomRepository.save(newChatRoom);
    }

    // 해당하는 채팅방의 정보를 불러옴
    public ChatRoom loadChatRoomInfo(Long roomId){
        return chatRoomRepository.findAllById(roomId);
    }

}

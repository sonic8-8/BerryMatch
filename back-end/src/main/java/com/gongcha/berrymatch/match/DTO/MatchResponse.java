package com.gongcha.berrymatch.match.DTO;

import com.gongcha.berrymatch.match.domain.MatchStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MatchResponse {

    private MatchStatus status;  // 매칭 상태 (성공, 실패 등)

    //노드로 보내주고있어서 이쪽은 쓰지않고있음
    private List<UserDetail> matchedUserDetails;  // 매칭된 유저의 상세 정보 목록

    @Data
    @NoArgsConstructor
    public static class UserDetail {
        private Long id;
        private String nickname;
        private String email;

        public UserDetail(Long id, String nickname, String email) {
            this.id = id;
            this.nickname = nickname;
            this.email = email;
        }
    }
}

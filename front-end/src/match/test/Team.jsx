import React from 'react';
import './Team.css';
import defaultUserImg from './User.png'; 

const Team = ({ teamLabel, users, teamLogo, onButtonClick, currentUserId, allUsersReady }) => (
    <div className="team">
        <div className="glitch-effect">
            <img 
                src={teamLogo} 
                alt={`${teamLabel} Logo`} 
                className="team-logo" 
            />
        </div>
        <div className="team-label">{teamLabel}</div>

        <div className="user-list">
            {users.length > 0 ? (
                users.map((user) => (
                    <div className="user" key={user.id}>
                        <img 
                            src={user.profileImageUrl || defaultUserImg} 
                            alt={user.nickname} 
                            onError={(e) => e.target.src = defaultUserImg}  
                        />
                        <div className="user-info">
                            <div className="nickname">{user.nickname}</div>
                        </div>
                    </div>
                ))
            ) : (
                <div>No users available</div>
            )}

            {showButton && (
                <div className="action-button">
                    <button onClick={onLeaveMatch}>
                        매칭 나가기
                    </button>

                    <button onClick={onReady}> {/* onReady 핸들러가 연결되어 있는지 확인 */}
                        준비
                    </button>

                    <button onClick={onWaiting}> {/* onReady 핸들러가 연결되어 있는지 확인 */}
                        준비해제
                    </button>

                </div>
            )}
        </div>
    </div>
);

export default Team;

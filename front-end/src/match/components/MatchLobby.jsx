import styles from './MatchLobby.module.css';
import useUserInfo from '../../user/useUserInfo';
import Cookies from 'js-cookie';
import axios from 'axios';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import { useState } from 'react';

function MatchLobby() {

    const [messages, setMessages] = useState([]);
    const [inputValue, setInputValue] = useState('');
    const [headerClicked, setHeaderClicked] = useState(false);

    const handleSendMessage = () => {
        if (inputValue.trim() !== '') {
            const newMessages = [...messages, { sender: 'me', text: inputValue }];
            setMessages(newMessages);
            setInputValue('');

            // 상대방의 메시지를 1초 후에 추가
            setTimeout(() => {
                const responseMessages = [...newMessages, { sender: 'other', text: '상대방의 응답입니다.' }];
                setMessages(responseMessages);
            }, 1000);
        }
    };

    const handleInputChange = (e) => {
        setInputValue(e.target.value);
    };

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleSendMessage();
        }
    };

    const handleHeaderClick = () => {
        setHeaderClicked(prevState => !prevState);
    };

    // ---

    const { userInfo, loading, error } = useUserInfo();

    if (loading) {
        return <div>로딩중...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (
        <div className={styles.dashboard_container}>
            <div className={styles.dashboard_top}>
                            
                <div className={styles.dashboard_top_logo_container}>
                    <img className={styles.dashboard_top_logo} src='https://thank-you-berrymatch-bucket-0.s3.ap-northeast-2.amazonaws.com/design/logo.png'/>
                    <div className={styles.dashboard_top_logo_title}>BerryMatch</div>
                </div>
                <div className={styles.dashboard_top_identifier}>환영합니다! {userInfo ? userInfo.nickname : '' }님</div>
                <Link to="/logout" className={styles.dashboard_top_logout}>
                        로그아웃
                </Link>

            </div>

            <div className={styles.dashboard_middle}>
                <div className={styles.dashboard_middle_team}>
                    <div>
                        <img src='' />
                    </div>
                    <div>
                        <img src='' />
                        <div>유저1</div>
                        <div>준비상태</div>
                    </div>
                    <div>
                        <img src='' />
                        <div>유저1</div>
                        <div>준비상태</div>
                    </div>
                    <div>
                        <img src='' />
                        <div>유저3</div>
                        <div>준비상태</div>
                    </div>
                    <div>
                        <img src='' />
                        <div>유저4</div>
                        <div>준비상태</div>
                    </div>
                    <div>
                        <img src='' />
                        <div>유저5</div>
                        <div>준비상태</div>
                    </div>
                    <div>
                        <img src='' />
                        <div>유저6</div>
                        <div>준비상태</div>
                    </div>
                </div>
                <div className={styles.dashboard_middle_chatting}>
                    
                    <div>Ready</div>

                    <div className={styles.chatbox}>
                    {messages.map((msg, index) => (
                        <div
                            key={index}
                            className={`chat-message-container ${msg.sender === 'me' ? 'me-container' : 'other-container'}`}
                        >
                        <div className={`chat-message ${msg.sender}`}>
                            {msg.text}
                        </div>
                    </div>
                    ))}
                </div>
                <div className={styles.input_container}>
                    <input
                        type="text"
                        placeholder="메시지를 입력하세요..."
                        value={inputValue}
                        onChange={handleInputChange}
                        onKeyPress={handleKeyPress}
                        className="chatbox-input"
                    />
                    <button className="send-button" onClick={handleSendMessage}>
                        전송
                    </button>
                    </div>
                </div>
                <div className={styles.dashboard_middle_team}>
                <div>
                        <img src='' />
                    </div>
                    <div>
                        <img src='' />
                        <div>유저1</div>
                        <div>준비상태</div>
                    </div>
                    <div>
                        <img src='' />
                        <div>유저1</div>
                        <div>준비상태</div>
                    </div>
                    <div>
                        <img src='' />
                        <div>유저3</div>
                        <div>준비상태</div>
                    </div>
                    <div>
                        <img src='' />
                        <div>유저4</div>
                        <div>준비상태</div>
                    </div>
                    <div>
                        <img src='' />
                        <div>유저5</div>
                        <div>준비상태</div>
                    </div>
                    <div>
                        <img src='' />
                        <div>유저6</div>
                        <div>준비상태</div>
                    </div>
                </div>
            </div>

            <div className={styles.dashboard_bottom}>
                <Link to="/" className={styles.dashboard_bottom_menu}>Home</Link>
                <Link to="/alert" className={styles.dashboard_bottom_menu}>알림</Link>
                <Link to="/mypage" className={styles.dashboard_bottom_menu}>마이페이지</Link>
                <div className={styles.dashboard_bottom_menu}>그룹정보</div>
            </div>
        </div>
    );
}

export default MatchLobby;
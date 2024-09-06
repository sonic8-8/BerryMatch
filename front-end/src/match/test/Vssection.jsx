import React, { useState } from 'react';
import './VSSection.css';

const VSSection = () => {
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

    return (
        <div className="vs">
           
           
            <button
                className={`vs-header ${headerClicked ? 'clicked' : ''}`}
                onClick={handleHeaderClick}
                aria-label="Ready Button"
            >
                Ready
            </button>



            <div className="chatbox">
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
            <div className="input-container">
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
    );
};

export default VSSection;

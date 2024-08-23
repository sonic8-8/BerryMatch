import React from "react";
import Styles from './ChatMessage.module.css';

const ChatMessage = ({ message, time, author }) => {
    return (
      <div className={Styles.chat_message_container}>
        <div className={Styles.chat_message_header}>{time} / {author} :</div>
        <div className={Styles.chat_message_content}>{message}</div> 
      </div>
    );
};

export default ChatMessage;
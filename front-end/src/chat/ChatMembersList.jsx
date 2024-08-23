import React from "react";
import ChatMember from './ChatMember';
import Styles from './ChatMembersList.module.css';

const ChatMembersList = () => {
    return (
        <div className={Styles.chat_members_list}>
            <ChatMember/>
        </div>
    );
};

export default ChatMembersList;
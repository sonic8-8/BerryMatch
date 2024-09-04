import React from "react";
import Styles from "./ChatMember.module.css";

const ChatMember = ({memberImg, memberNick, memberState, memberRecord}) => {

    return (
        <div className={Styles.chat_member}>
            <div className={Styles.chat_member_img}>
                {memberImg}
            </div>
            <div className={Styles.chat_member_nick}>
                {memberNick}
            </div>
            <div className={Styles.chat_member_status}>
                {memberState}
            </div>
            <div className={Styles.chat_member_record}>
                {memberRecord}
            </div>
        </div>
    );
};

export default ChatMember;
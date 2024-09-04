import React, { useEffect, useState } from "react";
import ChatMember from "./ChatMember";
import Styles from "./ChatMembersList.module.css";
import { io } from "socket.io-client";

const ChatMembersList = () => {
    let roomId 

    const socket = io.connect("http://localhost:8085/chat/"+roomId,{
        cors:{
            origin:"*"
        }
    });
    socket.connect();

    const [membersArr, setMembersArr] = useState([]);

    useEffect(()=>{
        socket.on("matching info to client", (match)=>{
            setMembersArr((membersArr)=> membersArr.concat(match.members))
        });
        socket.on("user ready on",(id,newState)=>{
            // 해당하는 사용자의 준비 상태가 on으로 바뀜
            setMembersArr(membersArr.map(member => member.id === id? {...member,state:newState}:member))            
        });
        socket.on("user ready off",(id, newState)=>{
            // 해당하는 사용자의 준비 상태가 off으로 바뀜
            setMembersArr(membersArr.map(member => member.id === id? {...member,state:newState}:member))
        });

    });

    return (
        <div className={Styles.chat_members_list}>
            {membersArr.map((membersInfo) => {
                return(
                    <div key={membersInfo.id}>
                        <ChatMember
                            memberImg={membersInfo.img}
                            memberId = {membersInfo.id}
                            memberState = {membersInfo.state}
                            memberRecord = {membersInfo.record}
                        />
                    </div>
                )
            })}
        </div>
    );
};

export default ChatMembersList;
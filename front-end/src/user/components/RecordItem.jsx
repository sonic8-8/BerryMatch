import React, { useEffect, useState } from 'react';
import Styles from "./RecordItem.module.css";
import Modal from '../../Modal';
import axios from 'axios';
import RecordVote from './RecordVote';
import GameRecord from './GameRecord';


const RecordItem = (userId, gameId, gameDate, gameRecord) => {

    const [modalOpen,setModalOpen] = useState(false);
    const [readyInput, setReadyInput] = useState(true);
    const [readyVote, setReadyVote] = useState(true);
    const [readyPost,setReadyPost] = useState(true);

    const [isRecordModalOpen, setRecordModalOpen] = useState(false);
    const [isVoteModalOpen, setVoteModalOpen] = useState(false);

    const openRecordModal = () => setRecordModalOpen(true);
    const closeRecordModal = () => setRecordModalOpen(false);

    const openVoteModal = () => setVoteModalOpen(true);
    const closeVoteModal = () => setVoteModalOpen(false);

    useEffect(() => {
        // 경기 종료 상태
        axios.get(`http://localhost:8085/api/check-ready-input/${gameId}`)
        .then(response => {
            if (response.data.ready) {
                setReadyInput(false);
            }
        })
        .catch(error => console.error('Error checking input readiness:', error));
    
    // 경기 투표 상태 확인을 위한 API 요청
    axios.get(`http://localhost:8085/api/check-ready-vote/${gameId}`)
        .then(response => {
            if (response.data.ready) {
                setReadyVote(false);
            }
        })
        .catch(error => console.error('Error checking vote readiness:', error));
    
    // 게시물 작성 상태 확인을 위한 API 요청
    axios.get(`http://localhost:8085/api/check-ready-post/${gameId}`)
        .then(response => {
            if (response.data.ready) {
                setReadyPost(false);
            } 
        })
        .catch(error => console.error('Error checking post readiness:', error));
}, []); 

    const inputRecord = () => {
        openRecordModal();
    };

    const voteRecord = () => {
        openVoteModal();
    };

    const postHilight = () => {
        axios.get('http://localhost:8085/api/check-post')
        .then(response => {
            if (response.data.exists) {
                // 기존 게시물 수정 페이지로 이동

            } else {
                // 새로운 게시물 작성 페이지로 이동
            }
        })
        .catch(error => console.error('Error checking post:', error));
    };


    return (
    <div className={Styles.record_container}>
        <div className={Styles.game_info}>
            <div className={Styles.game_date}>
                {gameDate}이게 언제 날짜일까요
            </div>
            <div className={Styles.game_record}>
                {gameRecord}경기 기록 없음
            </div>
        </div>
        <div classname={Styles.record_btns}>
            <button className={Styles.record_input} onClick={inputRecord} disabled={readyInput}>기록입력</button>
            <button className={Styles.record_votes} onClick={voteRecord} disabled={readyVote}>기록투표</button>
            <button className={Styles.record_post} onClick={postHilight} disabled={readyPost}>게시물작성</button>
        </div>

        <Modal 
            isOpen={isRecordModalOpen} 
            onClose={closeRecordModal} 
            title="경기 기록" 
            children={GameRecord} 
            gameId={gameId}
            setReadyInput= {setReadyInput}
            closeRecordModal={closeRecordModal}
        />
            
        <Modal 
            isOpen={isVoteModalOpen} 
            onClose={closeVoteModal} 
            title="기록 투표" 
            children={RecordVote} 
            gameId={gameId}
            setReadyVote={setReadyVote}
            closeVoteModal={closeVoteModal}
        />
    </div>
  )
}

export default RecordItem
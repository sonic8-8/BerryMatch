import React, { useEffect, useState } from 'react';
import Styles from "./Record.module.css";
import Modal from '../Modal';
import axios from 'axios';


const Record = (gameDate, gameRecord) => {

    const [modalOpen,setModalOpen] = useState(false);
    const [readyInput, setReadyInput] = useState(false);
    const [readyVote, setReadyVote] = useState(false);
    const [readyPost,setReadyPost] = useState(false);

    const [isRecordModalOpen, setRecordModalOpen] = useState(false);
    const [isVoteModalOpen, setVoteModalOpen] = useState(false);

    const openRecordModal = () => setRecordModalOpen(true);
    const closeRecordModal = () => setRecordModalOpen(false);

    const openVoteModal = () => setVoteModalOpen(true);
    const closeVoteModal = () => setVoteModalOpen(false);

    useEffect(() => {
        axios.get('/api/check-ready-input')
        .then(response => {
            if (response.data.ready) {
                setReadyInput(true);
            } else {
                setReadyInput(false);
            }
        })
        .catch(error => console.error('Error checking input readiness:', error));
    
    // 경기 투표 상태 확인을 위한 API 요청
    axios.get('/api/check-ready-vote')
        .then(response => {
            if (response.data.ready) {
                setReadyVote(true);
            } else {
                setReadyVote(false);
            }
        })
        .catch(error => console.error('Error checking vote readiness:', error));
    
    // 게시물 작성 상태 확인을 위한 API 요청
    axios.get('/api/check-ready-post')
        .then(response => {
            if (response.data.ready) {
                setReadyPost(true);
            } else {
                setReadyPost(false);
            }
        })
        .catch(error => console.error('Error checking post readiness:', error));
}, []); 

    const submitRecord = (e) => {
        e.preventDefault();
        axios.post('/api/submit-record', input)
            .then(response => {
                console.log('경기 기록 제출 완료:', response.data);
                setReadyInput(false); // 제출 후 상태 업데이트
                closeRecordModal();
            })
            .catch(error => console.error('Error submitting record:', error));
    };

    // 투표 제출 함수
    const submitVote = (e) => {
        e.preventDefault();
        axios.post('/api/submit-vote', { vote: input.vote }) // 'vote'는 input 상태에서 가져오도록 추가 필요
            .then(response => {
                console.log('투표 제출 완료:', response.data);
                setReadyVote(false); // 제출 후 상태 업데이트
                closeVoteModal();
            })
            .catch(error => console.error('Error submitting vote:', error));
    };

    const inputRecord = () => {
        openRecordModal();
    };

    const voteRecord = () => {
        openRecordModal();
    };

    const postHilight = () => {
        axios.get('/api/check-post')
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
                {gameDate}
            </div>
            <div className={Styles.game_record}>
                {gameRecord}
            </div>
        </div>
        <div classname={Styles.record_btns}>
            <button className={Styles.record_input} onClick={inputRecord} disabled={readyInput}>기록입력</button>
            <button className={Styles.record_votes} onClick={voteRecord} disabled={readyVote}>기록투표</button>
            <button className={Styles.record_post} onClick={postHilight} disabled={readyPost}>게시물작성</button>
        </div>

        <Modal isOpen={isRecordModalOpen} onClose={closeRecordModal} title="경기 기록">
            <form>
                <label>
                    A팀
                    <input type="number"></input>
                </label>
                <label>
                    B팀
                    <input type="number"></input>
                </label>
                <button onClick={submitRecord}>기록 제출</button>
            </form>
        </Modal>

        <Modal isOpen={isVoteModalOpen} onClose={closeVoteModal} title="기록 투표">
            <form>
                <label></label>
                <input type="radio" name="vote"></input>
                <button onClick={submitVote}>투표 제출</button>
            </form>
        </Modal>
    </div>
  )
}

export default Record
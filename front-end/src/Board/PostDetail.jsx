import React, { useState } from 'react'
import styles from './PostDetail.module.css';

// 외부에서 좋아요 버튼 디자인 갖고옴
import { AiOutlineLike } from "react-icons/ai";
import { AiFillLike } from "react-icons/ai";

// redux
import { useDispatch, useSelector } from 'react-redux';
import { setModalSwitch, setLikeSwitch } from '../store';
import axios from 'axios';


function PostDetail(props) {
  // store.js로 요청 보내주는 함수
  let dispatch = useDispatch();
  // store에 있는 state 가지고 오기
  let storeState = useSelector((state) => {return state});
  // 꺼내서 사용
  let likeSwitch = storeState.likeSwitch;

  
  
  console.log("넘어온 props :", props.postList.title)

  function modalClose() {
    // modarSwitch 값 false로 바꿔주기
    dispatch(setModalSwitch());
  }



  /**
   * 게시글 접속 시 해당 게시글의 좋아요 버튼 상태
   */
  axios.get('http://localhost:8085/api/postlike/check', {
    params: {
      "postId" : props.postList.postId,
      "userId" : 1
    }
  })
  .then(
    response=>{
      console.log("조아요 판별 성공", response.data);
    }
  )
  .catch(
    error=>{
      console.log("게시글 접속 시 좋아요 판별 오류남 ", error);
    }
  )



  /**
   * "좋아요" 버튼 클릭 시 실행되는 로직
   */
  function handleLikeClick() {
    dispatch(setLikeSwitch());
    console.log("좋아요 값 ?", likeSwitch);
    console.log("좋아요 누른 게시물 아이디 : ", props.postList.postId)

    const postLikeData = {
      "postId" : props.postList.postId,
      "userId" : 1
    }

    axios.post('http://localhost:8085/api/postlike/update', postLikeData)
    .then(
      response=>{
        
        console.log("좋아요 응답 데이터 : ", response);

      }
    )
    .catch(
      error=>{
        console.log("좋아요눌렀는데 오류가 왜나냐고", error);
      }
    )
  }

  

  return (
    <div className={styles.modal_container}>


      
<button className={styles.close_btn} onClick={ modalClose }>X</button>


        <div className={styles.post_header}>
          <div>{props.postList.title}</div>
          <div>{props.postList.createAt}</div>
        </div>




      <video className={styles.highlight_file} controls autoPlay><source src={props.postList.fileUrl}></source></video>
      


      <div className={styles.post_footer}>
        <div>{props.postList.content}</div>
        <div onClick={ handleLikeClick }>
        {
          likeSwitch ? <AiFillLike className={styles.like_button}/> : <AiOutlineLike className={styles.like_button}/>
        }
        </div>
      </div>

    </div>
  )
}

export default PostDetail
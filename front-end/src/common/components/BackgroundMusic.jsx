import React, { useState, useRef, useEffect } from 'react';
import styles from './BackgroundMusic.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlay, faPause } from '@fortawesome/free-solid-svg-icons';

const BackgroundMusic = () => {
  const audioRef = useRef(null);
  const [isPlaying, setIsPlaying] = useState(false); // 기본값을 false로 설정
  const [currentTrack, setCurrentTrack] = useState(0);

  // 여러 음악 리스트
  const tracks = [
    {
      title: 'Track 1',
      src: 'https://thank-you-berrymatch-bucket-0.s3.ap-northeast-2.amazonaws.com/music/MainBgm1.mp3',
    },
    {
      title: 'Track 2',
      src: 'https://thank-you-berrymatch-bucket-0.s3.ap-northeast-2.amazonaws.com/music/MainBgm2.mp3',
    },
    {
      title: 'Track 3',
      src: 'https://thank-you-berrymatch-bucket-0.s3.ap-northeast-2.amazonaws.com/music/MainBgm3.mp3',
    }
  ];

  // 음악이 끝났을 때 다음 트랙으로 이동
  const handleEnded = () => {
    setCurrentTrack((prevTrack) => (prevTrack + 1) % tracks.length);
    setIsPlaying(true); // 음악이 자동으로 재생되도록 설정
  };

  useEffect(() => {
    const audioElement = audioRef.current;

    if (audioElement) {
      audioElement.addEventListener('ended', handleEnded);

      // 클린업 함수에서 이벤트 리스너 제거
      return () => {
        audioElement.removeEventListener('ended', handleEnded);
      };
    }
  }, [audioRef.current]); // 의존성 배열에 audioRef.current 추가

  useEffect(() => {
    if (audioRef.current) {
      // 트랙이 변경될 때마다 새로 로드
      audioRef.current.load();
      if (isPlaying) {
        audioRef.current.play().catch(error => {
          console.error("음악 재생 중 오류:", error);
        });
      }
    }
  }, [currentTrack]); // currentTrack이 변경될 때마다 useEffect가 실행됩니다.

  const togglePlayPause = () => {
    const audioElement = audioRef.current;
    if (audioElement) {
      if (isPlaying) {
        audioElement.pause();
      } else {
        audioElement.play().catch(error => {
          console.error("음악 재생 중 오류:", error);
        });
      }
      setIsPlaying(!isPlaying);
    }
  };

  const handleTrackChange = (index) => {
    setCurrentTrack(index);
    setIsPlaying(true); // 사용자가 트랙을 변경하면 음악을 재생
  };

  return (
    <div className={styles.music_container}>
      <div className={styles.music_playing}>
        <h2>현재 재생 중: {tracks[currentTrack].title}</h2>
        <button onClick={togglePlayPause}>
          {isPlaying ? (
            <FontAwesomeIcon icon={faPause} />
          ) : (
            <FontAwesomeIcon icon={faPlay} />
          )}
        </button>
      </div>

      <audio ref={audioRef} key={tracks[currentTrack].src} loop>
        <source src={tracks[currentTrack].src} type="audio/mpeg" />
        Your browser does not support the audio element.
      </audio>

      <div className={styles.music_playlist}>
        <h3>다른 트랙 선택:</h3>
        {tracks.map((track, index) => (
          <button key={index} onClick={() => handleTrackChange(index)}>
            {track.title}
          </button>
        ))}
      </div>
    </div>
  );
};

export default BackgroundMusic;

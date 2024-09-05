import styles from './ProfileEditSubPage.module.css';
import React, { useState, useEffect } from 'react';
import useProfileUpdate from '../useProfileUpdate';
import useUserInfo from '../useUserInfo';
import Modal from 'react-modal'; // 모달을 위한 라이브러리 추가

Modal.setAppElement('#root'); // 모달 접근성을 위한 설정

function ProfileEditSubPage() {
    const { userInfo, loading, error } = useUserInfo();
    const {
        profileImage,
        handleProfileImageChange,
        handleUpdate,
        status,
        message,
        data,
        code,
        introduction,
        setIntroduction
    } = useProfileUpdate(userInfo || {});

    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isEditingProfileImage, setIsEditingProfileImage] = useState(false);

    // 모달 열기
    const openModal = (isEditingImage) => {
        setIsEditingProfileImage(isEditingImage);
        setIsModalOpen(true);
    };

    // 모달 닫기
    const closeModal = () => {
        setIsModalOpen(false);
    };

    if (loading) {
        return <div>로딩중...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (
        <div className={styles.profileEdit_container}>
            <div className={styles.profileEdit_image_container}>
                <div className={styles.profileEdit_image_title}>프로필 사진</div>
                {
                    profileImage == null ? 
                    '프로필 사진이 없습니다.' : 
                    <img src={userInfo.profileImageUrl} className={styles.profileEdit_image} />
                }
                
                {/* 연필 모양 버튼 */}
                <button className={styles.profileEdit_edit_button} onClick={() => openModal(true)}>
                    ✏️
                </button>
            </div>
            <br />
            <div className={styles.profileEdit_introduction_container}>
                <div className={styles.profileEdit_introduction_title}>
                    자기소개 : {userInfo.introduction == '' ? '자기소개가 없습니다.' : userInfo.introduction}
                </div>
                
                {/* 연필 모양 버튼 */}
                <button className={styles.profileEdit_edit_button} onClick={() => openModal(false)}>
                    ✏️
                </button>
            </div>

            {/* 모달 */}
            <Modal
                isOpen={isModalOpen}
                onRequestClose={closeModal}
                className={styles.modal}
                overlayClassName={styles.modal_overlay}
            >
                <h2>{isEditingProfileImage ? '프로필 사진 수정' : '자기소개 수정'}</h2>
                {
                    isEditingProfileImage ? (
                        <div>
                            <input type="file" onChange={handleProfileImageChange} className={styles.profileEdit_image_input} />
                        </div>
                    ) : (
                        <div>
                            <input className={styles.profileEdit_introduction_input}
                                type="text"
                                value={introduction}
                                onChange={(e) => setIntroduction(e.target.value)}
                            />
                        </div>
                    )
                }
                <div className={styles.modal_buttons}>
                    <button className={styles.profileEdit_update_button} onClick={handleUpdate}>수정하기</button>
                    <button className={styles.modal_close_button} onClick={closeModal}>닫기</button>
                </div>
            </Modal>
        </div>
    );
}

export default ProfileEditSubPage;

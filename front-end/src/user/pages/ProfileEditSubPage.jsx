import useUserInfo from '../useUserInfo';
import styles from './ProfileEditSubPage.module.css';

function ProfileEditSubPage() {

    const { userInfo, loading, error } = useUserInfo();

    if (loading) {
        return <div>로딩중...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (
        <div className={styles.profile}>

            <div className={styles.profile_info}>
                닉네임 : {userInfo.nickname}
                도시 : {userInfo.city}
                구 : {userInfo.district}
                성별 : {userInfo.gender}
            </div>
            
        </div>
    )
}

export default ProfileEditSubPage;
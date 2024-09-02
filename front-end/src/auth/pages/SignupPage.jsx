import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import axios from "axios";
import styles from './SignupPage.module.css';
import { useParams } from "react-router-dom";
import Cookies from 'js-cookie';

function SignupPage() {
    const location = useLocation();
    const [formData, setFormData] = useState({
        identifier: '',
        nickname: '',
        city: '',
        district: '',
        gender: '',
        age: '',
        phoneNumber: '',
        profileImageUrl: '',
        introduction: ''
    });

    
    useEffect(() => {
        // URL의 쿼리 파라미터에서 identifier 추출
        const params = new URLSearchParams(location.search);
        const identifier = params.get('identifier');

        setFormData((prevData) => ({
            ...prevData,
            identifier: identifier || ''
        }));
    }, [location]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            // 1. 회원가입 요청
            const signupResponse = await axios.post("http://localhost:8085/api/auth/signup", formData, {
                headers: {
                    "Content-Type": "application/json"
                }
            });

            const signupApiResponse = signupResponse.data;
            const signupData = signupApiResponse.data;
            const signupMessage = signupApiResponse.message;
            const signupCode = signupApiResponse.code;
            const signupStatus = signupApiResponse.status;

            if (signupCode === 200) {
                console.log('회원가입 성공:', signupMessage);

                // 2. 로그인 요청 및 액세스 토큰, 리프레시 토큰 처리
                const loginResponse = await axios.post("http://localhost:8085/api/auth", { identifier: signupData.identifier }, {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + localStorage.getItem('accessToken'), // 현재 액세스 토큰 (없을 수도 있음)
                        "token-reissued": "False"
                    },
                    withCredentials: true // 쿠키를 포함하여 전송 (리프레시 토큰)
                });

                const loginApiResponse = loginResponse.data;
                const loginData = loginApiResponse.data;
                const loginMessage = loginApiResponse.message;
                const loginCode = loginApiResponse.code;
                const loginStatus = loginApiResponse.status;

                if (loginCode === 200) {
                    // 3. 액세스 토큰 저장

                    const authorizationHeader = loginResponse.headers['authorization'];

                    const accessToken = authorizationHeader.split(' ')[1];
                    
                    Cookies.set('accessToken', accessToken, { path: '/' });

                    console.log(loginData.role);
                    console.log(accessToken);

                    // 4. 사용자 역할에 따라 리다이렉션 처리
                    if (loginData.role == "USER") {
                        window.location.href = "http://localhost:3000/";
                    } else {
                        window.location.href = "http://localhost:3000/login";
                    }
                } else {
                    console.error('로그인 실패:', loginMessage);
                    window.location.href = "http://localhost:3000/login";
                }
            } else {
                console.error('회원가입 실패:', signupMessage);
                window.location.href = "http://localhost:3000/login";
            }
        } catch (error) {
            console.error("Error:", error);
        }
    };

    return (
        <div className={styles.layout}>
            <div className={styles.layout_header}>
                <h1>{formData.identifier}님 환영합니다!</h1>
            </div>
    
            <div className={styles.layout_content}>

                <form onSubmit={handleSubmit} className={styles.signupForm}>
                    <label className={styles.signup_input_container}>
                        <input className={styles.signup_input}
                            type="hidden" 
                            name="identifier" 
                            value={formData.identifier}
                        />
                    </label>

                    <label className={styles.signup_input_container}>
                        Nickname:
                        <input className={styles.signup_input}
                            type="text" 
                            name="nickname" 
                            value={formData.nickname} 
                            onChange={handleChange} 
                            required 
                        />
                    </label>

                    <label className={styles.signup_input_container}>
                        City:
                        <input className={styles.signup_input}
                            type="text" 
                            name="city" 
                            value={formData.city} 
                            onChange={handleChange} 
                            required 
                        />
                    </label>

                    <label className={styles.signup_input_container}>
                        District:
                        <input className={styles.signup_input}
                            type="text" 
                            name="district" 
                            value={formData.district} 
                            onChange={handleChange} 
                            required 
                        />
                    </label>

                    <label className={styles.signup_input_container}>
                        Gender:
                        <select className={styles.signup_input}
                            name="gender" 
                            value={formData.gender} 
                            onChange={handleChange} 
                            required
                        >
                            <option value="">Select Gender</option>
                            <option value="MALE">MALE</option>
                            <option value="FEMALE">FEMALE</option>
                        </select>
                    </label>

                    <label className={styles.signup_input_container}>
                        Age:
                        <input className={styles.signup_input}
                            type="number" 
                            name="age" 
                            value={formData.age} 
                            onChange={handleChange} 
                            required 
                        />
                    </label>

                    <label className={styles.signup_input_container}>
                        Phone Number:
                        <input className={styles.signup_input}
                            type="tel" 
                            name="phoneNumber" 
                            value={formData.phoneNumber} 
                            onChange={handleChange} 
                            required 
                        />
                    </label>

                    <label className={styles.signup_input_container}>
                        Profile Image URL:
                        <input className={styles.signup_input}
                            type="url" 
                            name="profileImageUrl" 
                            value={formData.profileImageUrl} 
                            onChange={handleChange} 
                            required 
                        />
                    </label>

                    <label className={styles.signup_input_container}>
                        Introduction:
                        <textarea className={styles.signup_input}
                            name="introduction" 
                            value={formData.introduction} 
                            onChange={handleChange} 
                            required 
                        />
                    </label>

                    <button className={styles.signupForm_button} type="submit">가입</button>
                </form>
            </div>
            
            <div className={styles.layout_footer}>
                
            </div>
        </div>
    );
}

export default SignupPage;

import React, { useEffect, useState } from 'react'
import axios from "axios";
import Styles from "./RecordPage.module.css"
import Record from './Record'

const RecordPage = (user) => {
    const [recordList, setRecordList] = useState([]);
 useEffect(() => {
    // 해당하는 유저의 모든 경기 기록을 가져옴
    axios.get("http://localhost:8085/api/record",{
        params:{
            id:user
        }
     })
     .then(res => 
        setRecordList(res.data.recordList)
     )
     .catch(error => 
        console.error('Error fetching data:', error)
     );
    // 
 
 });

  return (
    <div className={Styles.recordPage}>
        <div className={Styles.record}>
            {recordList.map((record,index)=>(
                <Record
                    key={index}
                    gameDate={record.gameDate}
                    gameInfo={record.gameInfo}
                    resultState={record.resultState}
                />
            ))}
        </div>
    </div>
  )
}

export default RecordPage
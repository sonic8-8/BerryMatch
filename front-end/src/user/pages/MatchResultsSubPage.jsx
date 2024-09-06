import React, { useEffect, useState } from 'react'
import axios from "axios";
import Styles from "./MatchResultsSubPage.module.css"
import RecordItem from '../components/RecordItem'

const MatchResultsSubPage = (userId) => {
    const [recordList, setRecordList] = useState([]);
 useEffect(() => {
    // 해당하는 유저의 모든 경기 기록을 가져옴
    axios.get(`http://localhost:8085/api/record/${userId}`)
     .then(res => 
        setRecordList(preList => [...preList,res.data.recordList])
     )
     .catch(error => 
        console.error('Error fetching data:', error)
     );
 });

  return (
    <div className={Styles.recordPage}>
        <div className={Styles.record}>
            {recordList.map((record,index)=>(
                <RecordItem
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

export default MatchResultsSubPage
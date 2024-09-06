import Notification from "./Notification";

function MatchStatus() {
    // useEffect(() => {
    //     let eventSource: EventSource;
    //     if (requestId.length) {
    //         // 서버와 연결
    //         eventSource = new EventSource(defaultURL + "/connect/" + requestId);
    //         // 이벤트 수신 시 수행할 작업들
    //         eventSource.addEventListener("message", handleEventMessage);
    //         eventSource.addEventListener("error", handleEventError);
    //     }
    
    //     return (() => {
    //         if (requestId) {
    //             // 페이지가 종료될 때 설정한 이벤트들도 제거
    //             eventSource.removeEventListener("message", handleEventMessage);
    //             eventSource.removeEventListener("error", handleEventError);
    //         }
    //     })
    // }, [requestId]);

    return (
        <div>
            <Notification/>
        </div>
    )
}

export default MatchStatus;
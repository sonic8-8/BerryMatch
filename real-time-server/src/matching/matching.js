function handleLeave(io, groupMembers, req, res) {
    const groupCord = req.params.groupCord;
    const { members } = req.body;
    console.log("Request body:", req.body);
    console.log(`Received leave request for group ${groupCord}`);

    if (!members || members.length === 0) {
        return res.status(400).send({ error: "Members data is required" });
    }

    members.forEach(member => {
        const { id } = member;
        console.log(`Processing user ${id} for group ${groupCord}`);

        if (groupMembers[groupCord]) {
            groupMembers[groupCord] = groupMembers[groupCord].filter(m => m.id !== id);
            
            // 실시간으로 클라이언트들에게 누가 나갔는지 정보 전송
            io.emit("user left", { groupCord, userId: id });

            if (groupMembers[groupCord].length === 0) {
                delete groupMembers[groupCord];
            }
        }
    });

    res.send({ status: "ok" });
}

module.exports = {
    handleLeave
};

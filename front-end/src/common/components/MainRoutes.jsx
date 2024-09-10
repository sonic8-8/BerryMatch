import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Dashboard from '../Dashboard';
import RankPage from '../../rank/RankPage';
import Mypage from '../../user/pages/MyPage';
import MatchPage from '../../match/pages/MatchPage';
import MatchComparison from '../../match/test/MatchComparison';
import BoardPage from '../../board/BoardPage';
import PostList from '../../board/PostList';
import PostWritePage from '../../board/pages/PostWritePage';
import GroupCreatePage from '../../group/GroupCreatePage';
import GroupSearchPage from '../../group/GroupSearchPage';
import Map from '../Map';

function MainRoutes() {
  return (
    <Routes>
      <Route path="/" element={<Dashboard />} />
      
      <Route path="/rank" element={<RankPage />} />
      
      <Route path="/mypage" element={<Mypage />} />
      
      <Route path="/match" element={<MatchPage />} />
      
      <Route path="/match/lobby" element={<MatchComparison />} />
      
      <Route path="/board" element={<BoardPage />}>
        <Route path=":currentPage" element={<PostList />} />
        <Route path="post/write" element={<PostWritePage />} />
      </Route>
      
      <Route path="/group/create" element={<GroupCreatePage />} />
      <Route path="/group/search" element={<GroupSearchPage />} />
      
      <Route path="/map" element={<Map />} />
    </Routes>
  );
}

export default MainRoutes;

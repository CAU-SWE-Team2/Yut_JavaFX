package com.yut.controller.view_interfaces;

import com.yut.ui.javaFX.ClickableNodeFX;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.Deque;
import java.util.Map;

public interface GameScreenInterface {

    // 말 이동 미리보기 원 추가
    void showMovePreview(int nodeID, int playerID);

    // 미리보기 제거
    void deleteMovePreview();

    // 말 그리기
    void drawPiece(int nodeID, int playerID, int pieceNumber);

    // 말 제거
    void deletePiece(int nodeID);

    // 플레이어 캔버스 업데이트
    void updatePlayerCanvas(int playerID, int pieceCount);

    // 현재 차례인 플레이어 강조
    void highlightCurrentPlayer(int playerId);

    // 말 선택 사각형 표시
    void select(int nodeID);

    // 윷 결과(도/개/걸/윷/모 등) 반영
    void updateRandomResult(int yut[]);

    // 현재 덱 상태 출력
    void printDeckContents(Deque<int[]> deck);

    // 노드 상태 반환 (힌트/빈칸/말 있는 칸)
    int getNodeState(int nodeID);

    // 랜덤 던지기 버튼 리스너
    void addRandomThrowButtonListener(EventHandler<ActionEvent> listener);

    // 선택 던지기 버튼 리스너 (여러 개일 경우 index로 구분)
    void addSelectedThrowButtonListener(int index, EventHandler<ActionEvent> listener);

    // 뒤로가기 버튼 리스너
    void addBackButtonListener(EventHandler<ActionEvent> listener);

    // 노드 클릭 이벤트 연결
    void addNodeClickListener(ClickableNodeFX node, EventHandler<MouseEvent> listener);

    // 새 말 내보내기 버튼 리스너
    void addMoveNewPieceButtonListener(EventHandler<ActionEvent> listener);

    // 골 버튼 리스너
    void addGoalButtonListener(EventHandler<ActionEvent> listener);

    // 골 버튼 보이기/숨기기
    void setGoalButtonVisible(boolean visible);

    // 노드 맵 제공
    Map<Integer, ClickableNodeFX> getNodeMap();
}
